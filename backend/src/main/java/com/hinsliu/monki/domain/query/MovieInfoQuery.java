package com.hinsliu.monki.domain.query;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:57
 * @description: 请求电影具体信息
 */
@Data
public class MovieInfoQuery {

    @NotNull(message = "电影索引不能为空")
    private String id;

}
