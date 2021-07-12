package com.hinsliu.monki.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_search_action
 * @author 
 */
@ApiModel(value="com.hinsliu.monki.domain.model.SearchActionDOuser search condition")
@Data
public class SearchActionDO implements Serializable {
    private Long id;

    /**
     * 用户表主键
     */
    @ApiModelProperty(value="用户表主键")
    private Long uid;

    /**
     * 用户搜索内容
     */
    @ApiModelProperty(value="用户搜索内容")
    private String content;

    /**
     * 用户搜索类型
     */
    @ApiModelProperty(value="用户搜索类型")
    private Integer type;

    /**
     * 记录创建时间
     */
    @ApiModelProperty(value="记录创建时间")
    private Date time;

    private static final long serialVersionUID = 1L;
}