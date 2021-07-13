package com.hinsliu.monki.domain.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuxuanming
 * @date 2021/7/13 10:56
 * @description: 取景地视图对象
 */
@Data
@ApiModel
public class LocationDTO {

    @ApiModelProperty(value = "配图链接列表")
    private List<String> images;

    @ApiModelProperty(value = "文章介绍")
    private String introduction;

    @ApiModelProperty(value = "景点集合")
    private List<String> visit;

}
