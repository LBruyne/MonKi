package com.hinsliu.monki.common;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: 常量
 * @author: liuxuanming
 * @date: 2021/05/25 4:25 下午
 */
@Component
public class UtilConstant {

    // 默认连接符
    public static final String DEFAULT_DELIMITER = ";";

    // 分页查询系统最大页大小
    public static final Integer MAX_PAGE_SIZE = 50;

    // 分页查询系统默认页大小
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    // 分页查询系统默认页
    public static final Integer DEFAULT_PAGE = 1;

    // 请求头中带有Token的键
    public static final String TOKEN_HEADER = "Authorization";

    // Redis server
    public static String redisServer = "www.neohugh.art";

    // Redis port
    public static Integer redisPort = 6379;

}
