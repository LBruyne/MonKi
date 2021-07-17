package com.hinsliu.monki.common.utils.es;

/**
 * @author liuxuanming
 * @date 2021/7/17 10:06
 * @description: ElasticSearch通用
 */
public class ESUtils {

    public static final Double NAME_BOOST = 0.5;

    public static final Double VISITS_BOOST = 0.5;

    public static final Double MUSICS_BOOST = 0.5;

    public static final Double SPECIFIED_BOOST = 2.0;

    public static final Double RATING_FACTOR = 0.1;

    public static final Double POPULARITY_FACTOR = 0.1;

    public static final String POPULARITY_MODIFIER = "log1p";

    public static final String METHOD = "GET";

    public static final String ENDPOINT = "/monki/_search";

}
