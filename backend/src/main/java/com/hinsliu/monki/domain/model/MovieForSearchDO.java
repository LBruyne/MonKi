package com.hinsliu.monki.domain.model;

import lombok.Data;

import java.util.List;

/**
 * @author liuxuanming
 * @date 2021/7/16 11:14
 * @description: 用于搜索的电影信息
 */
@Data
public class MovieForSearchDO {

    private String id;

    private String name;

    private String musics;

    private String visits;

    private String genre;

    private Double rating;

    private Integer popularity;

}
