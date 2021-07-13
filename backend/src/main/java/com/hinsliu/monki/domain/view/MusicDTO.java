package com.hinsliu.monki.domain.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuxuanming
 * @date 2021/7/13 10:56
 * @description: 音乐视图对象
 */
@Data
@ApiModel
public class MusicDTO {

    @ApiModelProperty(value = "音乐名字")
    private String name;

    @ApiModelProperty(value = "音乐参与制作者")
    private List<String> composer;
}
