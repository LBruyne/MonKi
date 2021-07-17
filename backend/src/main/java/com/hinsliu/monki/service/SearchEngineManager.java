package com.hinsliu.monki.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hinsliu.monki.common.enums.ErrorCodeEnum;
import com.hinsliu.monki.common.enums.SearchTypeEnum;
import com.hinsliu.monki.common.exception.BusinessException;
import com.hinsliu.monki.common.utils.es.ESUtils;
import com.hinsliu.monki.common.utils.time.DateTimeUtil;
import com.hinsliu.monki.dal.ClickActionDao;
import com.hinsliu.monki.dal.RecommendDao;
import com.hinsliu.monki.dal.SearchActionDao;
import com.hinsliu.monki.dal.UserDao;
import com.hinsliu.monki.domain.common.Page;
import com.hinsliu.monki.domain.common.PageParam;
import com.hinsliu.monki.domain.model.ClickActionDO;
import com.hinsliu.monki.domain.model.RecommendDO;
import com.hinsliu.monki.domain.model.SearchActionDO;
import com.hinsliu.monki.domain.model.UserDO;
import com.hinsliu.monki.domain.query.RecommendQuery;
import com.hinsliu.monki.domain.query.SearchQuery;
import com.hinsliu.monki.domain.view.MovieMetaDTO;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:47
 * @description: 搜索引擎相关服务
 */
@Slf4j
@Service
public class SearchEngineManager extends BaseManager {

    @Autowired
    private RestHighLevelClient highLevelClient;

    @Resource
    private MovieManager movieManager;

    @Resource
    private SearchActionDao searchActionDao;

    @Resource
    private UserDao userDao;

    @Resource
    private RecommendDao recommendDao;

    private static final String METHOD = "GET";

    private static final String ENDPOINT = "/monki/_search";
    @Resource
    private ClickActionDao clickActionDao;


    public Page<MovieMetaDTO> search(SearchQuery query) {
        // 分页验证
        PageParam.verify(query);

        String kw = query.getKeyword();
        SearchTypeEnum type = SearchTypeEnum.getType(query.getType());
        Integer page = query.getPage();
        Integer pageSize = query.getPageSize();
        Integer offset = query.getOffset();

        // 保存用户搜索记录
        if (getUserId() != null) {
            saveSearchAction(kw, type);
            log.info("记录用户{}的搜索行为{}", getUserId(), kw);
        }

        // 通过ElasticSearch进行搜索，
        // 得到排名靠前结果对应的索引集合
        SearchResult result = searchES(kw, type, offset, pageSize);

        // 根据每个索引，找到对应的文档记录并返回;
        return new Page<>(page, pageSize, result.getCount(), searchMongo(result.getResults()));
    }

    private void saveSearchAction(String kw, SearchTypeEnum type) {
        SearchActionDO searchActionDO = new SearchActionDO();
        searchActionDO.setUid(getUserId());
        searchActionDO.setContent(kw);
        searchActionDO.setType(type.getVal());
        searchActionDO.setTime(DateTimeUtil.getCurrentDateTime());

        int affectCount = searchActionDao.insert(searchActionDO);
        if (affectCount > 0) {
            log.info("插入用户搜索记录，搜索关键字{}，类别{}: {}", kw, type.getName(), affectCount);
        } else {
            log.warn("插入用户搜索记录失败，搜索关键字{}，类别{}", kw, type.getName());
            throw new BusinessException(ErrorCodeEnum.FAIL.getCode(), "插入用户搜索记录失败");
        }
    }

    public Page<MovieMetaDTO> recommend(RecommendQuery query) {
        // 分页验证
        PageParam.verify(query);

        return null;
    }

    public void recall() {
//        for eachUser:
//             基于近10次点击记录，每次取前3  =》   《=30条
//             基于近10次搜索记录，每次取前2  =》  《=20条
//             去重复
//             random补至50条
//         存到数据库里去
        List<UserDO> userls = userDao.selectAllUser();
        for (UserDO user : userls) {
//            查找近10次点击记录
            List<ClickActionDO> latest10Clicks = clickActionDao.selectByUserID(user.getId());

//            查找最近10次搜索记录
            List<SearchActionDO> latest10searches = searchActionDao.selectByUserID(user.getId());

            Set<String> recall = new HashSet<>(100);

            latest10Clicks.forEach(keywordToSearch -> recall.addAll(searchES(keywordToSearch.getName(), SearchTypeEnum.MOVIE, 0, 3).getResults()));

            latest10searches.forEach(keywordToSearch -> recall.addAll(searchES(keywordToSearch.getContent(), SearchTypeEnum.getType(keywordToSearch.getType()), 0, 2).getResults()));
            String base = "60ee4fc37526f206a108";
            int id = Integer.parseInt("3182", 16);
            Random generator = new Random();
//            id 的base
            while (recall.size() < 50) {
                int ofs = generator.nextInt(1529);
                recall.add(base + Integer.toString((id + ofs) % id, 16));
            }

            RecommendDO recomm = new RecommendDO();
            recomm.setUid(user.getId());
            recomm.setMovies(String.join(";", recall));
            int affectCount = recommendDao.insert(recomm);
            if (affectCount > 0) {
                log.info("召回算法结束，为用户id{}进行召回，插入{}行记录", user.getId(), affectCount);
            } else {
                log.warn("召回算法结束，为用户id{}进行召回，插入记录失败", user.getId());
                throw new BusinessException(ErrorCodeEnum.FAIL.getCode(), "插入用户召回失败");
            }
        }
    }

    public SearchResult searchES(String kw, SearchTypeEnum type, Integer from, Integer size) {
        List<String> results = new ArrayList<>();
        Integer count = 0;

        // 构建请求体
        Request request = new Request(ESUtils.METHOD, ESUtils.ENDPOINT);
        JSONObject body = new JSONObject();

        // 加入查询部分
        addRequestBody(body, kw, type, from, size);

        // 设置请求体
        request.setJsonEntity(body.toJSONString());

        try {
            // 发送请求并得到搜索结果
            Response response = highLevelClient.getLowLevelClient().performRequest(request);

            // 得到搜索结果
            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));

            // 解析得到每个搜索结果单项
            JSONArray items = jsonObject.getJSONObject("hits").getJSONArray("hits");
            for (int i = 0; i < items.size(); i++) {
                JSONObject item = items.getJSONObject(i).getJSONObject("_source");
                String id = item.get("id").toString();
                results.add(id);
            }

            // 得到总数量
            count = jsonObject.getJSONObject("hits").getJSONObject("total").getInteger("value");

        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            throw new BusinessException(ErrorCodeEnum.FAIL.getCode(), "发送ES查找请求失败");
        }

        return SearchResult.builder()
                .results(results)
                .count(count)
                .build();
    }

    private void addRequestBody(JSONObject body, String kw, SearchTypeEnum type, Integer from, Integer size) {
        Double nameBst = ESUtils.NAME_BOOST, visitBst = ESUtils.VISITS_BOOST, musicBst = ESUtils.MUSICS_BOOST;

        // 获取加权
        if (type == SearchTypeEnum.MOVIE) {
            nameBst = ESUtils.SPECIFIED_BOOST;
        } else if (type == SearchTypeEnum.LOCATION) {
            visitBst = ESUtils.SPECIFIED_BOOST;
        } else if (type == SearchTypeEnum.MUSIC) {
            musicBst = ESUtils.SPECIFIED_BOOST;
        }

        // source
        body.put("_source", "id");

        // from
        body.put("from", from);

        // size
        body.put("size", size);

        // query
        body.put("query", new JSONObject());
        body.getJSONObject("query").put("function_score", new JSONObject());
        body.getJSONObject("query").getJSONObject("function_score").put("query", new JSONObject());
        body.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").put("bool", new JSONObject());
        body.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool").put("should", new JSONArray());
        JSONObject name = new JSONObject().fluentPut("match", new JSONObject().fluentPut("name", new JSONObject().fluentPut("query", kw).fluentPut("fuzziness", "AUTO").fluentPut("boost", nameBst)));
        JSONObject visits = new JSONObject().fluentPut("match", new JSONObject().fluentPut("visits", new JSONObject().fluentPut("query", kw).fluentPut("fuzziness", "AUTO").fluentPut("boost", visitBst)));
        JSONObject musics = new JSONObject().fluentPut("match", new JSONObject().fluentPut("musics", new JSONObject().fluentPut("query", kw).fluentPut("fuzziness", "AUTO").fluentPut("boost", musicBst)));
        body.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool").getJSONArray("should").fluentAdd(name).fluentAdd(visits).fluentAdd(musics);

        body.getJSONObject("query").getJSONObject("function_score").put("functions", new JSONArray());
        JSONObject rating = new JSONObject().fluentPut("field_value_factor", new JSONObject().fluentPut("field", "rating").fluentPut("factor", ESUtils.RATING_FACTOR));
        JSONObject popularity = new JSONObject().fluentPut("field_value_factor", new JSONObject().fluentPut("field", "popularity").fluentPut("factor", ESUtils.POPULARITY_FACTOR).fluentPut("modifier", ESUtils.POPULARITY_MODIFIER));
        body.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions").fluentAdd(rating).fluentAdd(popularity);

        body.getJSONObject("query").getJSONObject("function_score").put("score_mode", "sum");

        body.getJSONObject("query").getJSONObject("function_score").put("boost_mode", "multiply");

        // sort
        body.put("sort", new JSONArray());
        body.getJSONArray("sort").add(new JSONObject());
        body.getJSONArray("sort").getJSONObject(0).put("_score", new JSONObject());
        body.getJSONArray("sort").getJSONObject(0).getJSONObject("_score").put("order", "desc");
    }

    public List<MovieMetaDTO> searchMongo(List<String> ids) {
        return ids.stream().map(id -> movieManager.getMovieMeta(movieManager.getOneMovieDocument(id))).collect(Collectors.toList());
    }

    @Data
    @Builder
    private static class SearchResult {

        private List<String> results;

        private Integer count;

    }

}
