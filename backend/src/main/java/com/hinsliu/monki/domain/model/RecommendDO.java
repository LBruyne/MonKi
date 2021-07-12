package com.hinsliu.monki.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * t_recommend
 * @author 
 */
@ApiModel(value="com.hinsliu.monki.domain.model.RecommendDOrecommend movies for each user")
@Data
public class RecommendDO implements Serializable {
    private Long id;

    /**
     * 用户表主键
     */
    @ApiModelProperty(value="用户表主键")
    private Long uid;

    /**
     * 召回推荐给用户的电影
     */
    @ApiModelProperty(value="召回推荐给用户的电影")
    private String movies;

    private static final long serialVersionUID = 1L;
}