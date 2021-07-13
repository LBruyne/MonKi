package com.hinsliu.monki.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_click_action
 * @author 
 */
@ApiModel(value="com.hinsliu.monki.domain.ClickActionDOuser click condition")
@Data
public class ClickActionDO implements Serializable {
    private Long id;

    /**
     * 用户表主键
     */
    @ApiModelProperty(value="用户表主键")
    private Long uid;

    /**
     * 用户选择的电影的主键
     */
    @ApiModelProperty(value="用户选择的电影的主键")
    private String mid;

    /**
     * 电影分类
     */
    @ApiModelProperty(value="电影分类")
    private String genre;

    /**
     * 电影名字
     */
    @ApiModelProperty(value="电影名字")
    private String name;

    /**
     * 记录创建时间
     */
    @ApiModelProperty(value="记录创建时间")
    private Date time;

    private static final long serialVersionUID = 1L;
}