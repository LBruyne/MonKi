package com.hinsliu.monki.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * t_movie_feature
 * @author 
 */
@ApiModel(value="com.hinsliu.monki.domain.model.MovieFeatureDO电影特征")
@Data
public class MovieFeatureDO implements Serializable {
    private Integer sid;

    private String mongoid;

    private String feature;

    private static final long serialVersionUID = 1L;
}