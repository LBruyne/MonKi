package com.hinsliu.monki.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * t_recommend_result
 * @author 
 */
@ApiModel(value="com.hinsliu.monki.domain.model.RecommendResultDO")
@Data
public class RecommendResultDO implements Serializable {
    private Long id;

    private Long uid;

    private String from;

    private String to;

    private Double correlation;

    private static final long serialVersionUID = 1L;
}