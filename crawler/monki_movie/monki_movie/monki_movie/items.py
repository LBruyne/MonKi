# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class MonkiMovieItem(scrapy.Item):
    # define the fields for your item here like:
    name = scrapy.Field()
    director = scrapy.Field()
    country = scrapy.Field()
    year = scrapy.Field()
    genre = scrapy.Field()
    rating = scrapy.Field()
    post = scrapy.Field()
    popularity = scrapy.Field()
    description = scrapy.Field()
    music = scrapy.Field()
    location = scrapy.Field()
    update_time = scrapy.Field()


class Locations(scrapy.Item):
    images = scrapy.Field()
    visit = scrapy.Field()
    introduction = scrapy.Field()


class Music(scrapy.Item):
    name = scrapy.Field()
    composer = scrapy.Field()