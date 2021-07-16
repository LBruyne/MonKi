package com.hinsliu.monki.domain.model;

import lombok.Data;

import java.util.List;

/**
 * @author liuxuanming
 * @date 2021/7/13 10:54
 * @description: 电影数据对象
 */
@Data
public class MovieDO {

    private String id;

    private String name;

    private List<String> director;

    private String country;

    private String year;

    private Double rating;

    private List<String> genre;

    private String post;

    private String popularity;

    private String description;

    private List<MusicDO> music;

    private LocationDO location;

    private String update_time;
}
