import numpy as np
import torch
from nltk import tokenize
from transformers import BertTokenizer, BertModel
import pymongo
import pymysql


def saveToDB(ls):
    db = pymysql.connect(
        host='www.neohugh.art',
        user='monki', password='monki',
        database='monki',
        charset='utf8')
    cursor = db.cursor()
    for movie in ls:
        sql = "INSERT INTO monki.t_movie_feature (mongoID, feature) VALUES ('" + movie['mid'] + "','" + movie[
            'feature'] + "');"
        cursor.execute(sql)
    db.commit()
    db.close()


def saveRerank(uid, ls, cursor, db):
    if not ls or not cursor:
        return
    d = f'delete from t_recommend_result where uid= {uid}'
    cursor.execute(d)
    for recomm in ls:
        sql = f'insert into t_recommend_result(t_recommend_result.uid,t_recommend_result.from,t_recommend_result.to,t_recommend_result.correlation) values({uid},' + f'''"{recomm['from']}", "{recomm['to']}", {recomm['value']}); '''
        cursor.execute(sql)
    db.commit()


def init(real=False):
    if real:
        tokenizer = BertTokenizer.from_pretrained('bert-base-cased')
        model = BertModel.from_pretrained('bert-base-cased', output_hidden_states=True)
        model.eval()
        return tokenizer, model
    else:
        return None, None


def getMovieList():
    myclient = pymongo.MongoClient("mongodb://www.neohugh.art:27017/")
    mydb = myclient["monki"]
    mycol = mydb["monki_movies"]
    # print(mycol)
    return [
        {'description': each.get("description") if each.get("description") else [""], 'director': each['director'],
         'genre': each['genre'], 'name': each['name'], 'mid': each['_id']}
        for each in mycol.find()]


def getFeatureList(ls, cursor):
    if not ls or not cursor:
        return []
    # res = []
    # for movie in ls:
    #     sql = f'''select feature from t_movie_feature where mongoID = "{movie}"'''
    #     cursor.execute(sql)
    #     feature = cursor.fetchone()[0]
    #     ary = torch.from_numpy(np.array(eval(feature), dtype=np.float32))
    #     res.append({'mid': movie, 'feature': ary.reshape(-1, 1)})

    sql = 'select feature from t_movie_feature where mongoID in %s'
    cursor.execute(sql, (ls,))
    string_features = cursor.fetchall()
    res = [{'mid': ls[i], 'feature': torch.from_numpy(np.array(eval(string[0]), dtype=np.float32))} for i, string in
           enumerate(string_features)]
    return res


def prepareMovieString(movie):
    jg = ['']
    meta = "Directors: " + (", ".join(movie['director']) if movie['director'] != jg else "") + ". Genre: " + (", ".join(
        movie['genre']) if movie['genre'] != jg else "") + ". Name: " + (movie['name'] if movie['name'] != jg else "")
    description = "Description: " + (movie['description'] if movie['description'] != jg else "") + "."
    return meta, description


def paragraph2vec(model, tokenizer, paragraph):
    tokens = tokenizer.tokenize("[CLS] " + paragraph + " [SEP]")
    indexed_sentence = tokenizer.convert_tokens_to_ids(tokens)
    segments = [1] * len(indexed_sentence)
    indexed_sentence_ts = torch.LongTensor([indexed_sentence])
    segment_ts = torch.IntTensor([segments])
    output = model(indexed_sentence_ts, segment_ts)
    token_vec = output.hidden_states[-2][0]
    sentence_embedding = torch.mean(token_vec, dim=0)
    return sentence_embedding.reshape(-1, 1)


def toFile(movies):
    with open(r'./features.csv', 'w', encoding='utf8') as f:
        for movie in movies:
            f.write(movie['mid'] + "| " + movie['feature'] + "\n")


def cal_feature():
    res = []
    tokenizer, model = init(True)
    movieList = getMovieList()
    with torch.no_grad():
        for index, movie in enumerate(movieList):
            meta, text = prepareMovieString(movie)
            sens = []

            # cal mean value for text
            for sentence in tokenize.sent_tokenize(text):
                sens.append(paragraph2vec(model, tokenizer, sentence))
            all_sentence = torch.stack(sens, dim=1).squeeze(dim=2)
            para_vec = torch.mean(all_sentence, dim=1)

            # cal mean value for meta
            sens.clear()
            for sentence in tokenize.sent_tokenize(meta):
                sens.append(paragraph2vec(model, tokenizer, sentence))
            all_sentence = torch.stack(sens, dim=1).squeeze(dim=2)
            meta_vec = torch.mean(all_sentence, dim=1)

            feature = (para_vec * 0.7 + meta_vec * 0.3).reshape(-1, 1)
            divide = torch.norm(feature, 2, 0)
            feature /= divide

            feature = str(feature.numpy().tolist())
            res.append({'mid': movie['mid'], 'feature': feature})
            print(f"Finish Index:{index}\tMovie ID: {movie['mid']}")
    toFile(res)
    return res


# cal_feature()

def rerank():
    db = pymysql.connect(
        host='www.neohugh.art',
        user='monki', password='monki',
        database='monki',
        charset='utf8')
    cursor = db.cursor()
    sql = 'select id from t_user order by id asc '
    cursor.execute(sql)
    users = [i[0] for i in cursor.fetchall()]
    for userID in users:
        select = f'''select movies from t_recommend where uid = {userID}'''
        cursor.execute(select)
        recall = cursor.fetchone()[0].split(";")

        select = f'''select mid from t_click_action where uid = {userID} order by time desc limit 0,10'''
        cursor.execute(select)
        clicks = [i[0] for i in cursor.fetchall()]
        if recall and clicks:
            movieNames = recall
            recall_movies = getFeatureList(movieNames, cursor)
            recall_features = [i['feature'] for i in recall_movies]
            recall_features = torch.stack(recall_features, dim=0).squeeze(dim=2)

            click_movies = getFeatureList(clicks, cursor)
            click_features = [i['feature'] for i in click_movies]
            click_features = torch.stack(click_features, dim=0).squeeze(dim=2).transpose(0, 1)

            recomm = torch.mm(recall_features, click_features).transpose(0, 1)
            finalList = []
            for i, tsor in enumerate(recomm):
                for j, value in enumerate(tsor):
                    if click_movies[i]['mid'] != recall_movies[j]['mid'] and recall_movies[j]['mid'] not in clicks:
                        finalList.append(
                            {'value': value, 'from': click_movies[i]['mid'], 'to': recall_movies[j]['mid']})
            finalList.sort(key=lambda obj: obj['value'])
            finalList.reverse()
            save = []
            st = set()
            for each in finalList:
                if each['to'] not in st:
                    st.add(each['to'])
                    save.append(each)

            saveRerank(userID, save, cursor, db)
    db.close()

cal_feature()