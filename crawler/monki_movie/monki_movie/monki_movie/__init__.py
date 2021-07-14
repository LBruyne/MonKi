from scrapy import cmdline

if __name__ == "__main__":
    cmdline.execute("scrapy crawl movie_spider".split())
    # cmdline.execute("scrapy crawl douban_spider -o result.csv".split())
    # cmdline.execute("scrapy crawl douban_spider -o test.json".split())