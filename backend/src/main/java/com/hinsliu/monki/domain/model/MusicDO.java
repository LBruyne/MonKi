package com.hinsliu.monki.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuxuanming
 * @date 2021/7/13 10:12
 * @description: 音乐对象
 */
@Data
public class MusicDO {

    private String name;

    private List<String> composer;
}
