import scrapy
import time
from items import MonkiMovieItem, Locations, Music

class ExampleSpider(scrapy.Spider):
    name = 'movie_spider'
    allowed_domains = ['movie-locations.com', 'imdb.com']
    baseURL = "http://movie-locations.com/movies/"
    end = "-movies.php"
    start_urls = ['http://movie-locations.com/movies/0/0-movies.php']
    flag = 0
    mid = ('0', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
           'o', 'p', 'q', 'r', 's', 't', 'u', 'v', ' w', 'x', 'y', 'z')
    names = []
    item = MonkiMovieItem()

    def parse(self, response):
        node_list = response.xpath("//div[@id='multicolumn3']/p")

        for node in node_list:
            if len(node.xpath("./a/@href").extract()):
                if len(node.xpath("./a/@href").extract()[0]) > 1:
                    link = "http://movie-locations.com/movies/" + str(self.mid[self.flag]) + '/'
                    link += str(node.xpath("./a/@href").extract()[0])
                else:
                    link = ""
                # node.xpath("./a/@href").extract()
                try:
                    yield scrapy.Request(url=link, callback=self.details)
                except ValueError:
                    pass
            else:
                pass

        if self.flag < 27:
            self.flag += 1
            url = self.baseURL + str(self.mid[self.flag]) + '/' + str(self.mid[self.flag]) + self.end
            yield scrapy.Request(url, callback=self.parse)

    def details(self, response):
        # item = MonkiMovieItem()
        item_loc = Locations()
        directors = []
        images = []
        words = ''
        visits = []

        name = response.xpath("//*[@id='main']/div[1]/h1")
        di_list = response.xpath("//*[@class='credits']/div[2]/ul/li[position()>1]/a")
        year = response.xpath("//*[@id='main']/div[1]/h1/span")
        letter = response.xpath("//*[@id='leader']/div[1]/p/a[3]/text()").extract()[0]
        im_list = response.xpath("//div[contains(@class,'mainpic') or contains(@id,'illust')]/img/@src").extract()
        wd_list = response.xpath("//*[@class='content']/p[position()>0]").xpath('string(.)').extract()
        vi_list = response.xpath("//*[@class='credits']/div[1]/ul/li[position()>1]/a")

        if len(letter) == 1 and letter.isalpha():
            let = letter.lower()
        else:
            let = '0'
        for di in di_list:
            directors.append(di.xpath("./text()").extract()[0])
        for im in im_list:
            images.append("http://movie-locations.com/movies/" + let + '/' + im)
        for wd in wd_list:
            words += wd
        words = words[:-1]
        for vi in vi_list:
            visits.append(vi.xpath("./text()").extract()[0])

        data = MonkiMovieItem()
        data['name'] = name.xpath("./text()").extract()[0]
        nameItem = name.xpath("./text()").extract()[0]
        self.names.append(nameItem)
        data['director'] = directors
        data['year'] = year.xpath("./text()").extract()[0].split('| ')[1]
        data['post'] = "http://movie-locations.com/movies/" + let + '/' + response.xpath("//*[@class='creditsbox']/img/@src").extract()[0]
        data['update_time'] = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())

        item_loc['images'] = images
        item_loc['visit'] = visits
        item_loc['introduction'] = words
        data['location'] = item_loc

        url = "https://www.imdb.com/find/?q=" + nameItem + "&s=tt&ttype=ft&ref_=fn_ft"
        yield scrapy.Request(url, meta={'data': data}, callback=self.parseImdb)

    def parseImdb(self, response):
        # print("------------------------------------")
        # print(response.meta['year'])
        movieURL = "https://www.imdb.com" + response.xpath('//td[@class="result_text"]/a/@href').extract()[0]
        yield scrapy.Request(movieURL, meta={'data': response.meta["data"]}, callback=self.parseMovie)


    def parseMovie(self, response):
        data=response.meta['data']
        try:
            data['description'] = response.xpath('//div[@class="ipc-html-content ipc-html-content--base"]/div/text()').extract()[0]
            data['rating'] = response.xpath("//span[@class='AggregateRatingButton__RatingScore-sc-1ll29m0-1 iTLWoV']/text()").extract()[0]
            data['popularity'] = response.xpath('//div[@class="TrendingButton__TrendingScore-bb3vt8-1 gfstID"]/text()').extract()[0]
        except IndexError:
            pass

        data['genre'] = response.xpath('//li[@data-testid="storyline-genres"]/div/ul/li/a/text()').extract()
        data['country'] = response.xpath('//li[@data-testid="title-details-origin"]/div/ul/li/a/text()').extract()[0]

        url = response.request.url
        url = url+"soundtrack"

        yield scrapy.Request(url, meta={'data': data}, callback=self.parseMusic)


    def parseMusic(self, response):
        data = response.meta["data"]
        musics = []
        musicList = response.xpath('//div[@id="soundtracks_content"]/div/div')
        for item in musicList:
            item_mus = Music()
            if item.xpath('./text()').extract()[0].startswith('\n'):
                item_mus['name'] = ''
            else:
                item_mus['name'] = item.xpath('./text()').extract()[0]
            item_mus['composer'] = item.xpath('./a/text()').extract()
            musics.append(item_mus)
        data['music'] = musics
        # print("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
        # print(data['year'])
        # print("------------------------------------")
        # print(response.meta['year'])
        yield data