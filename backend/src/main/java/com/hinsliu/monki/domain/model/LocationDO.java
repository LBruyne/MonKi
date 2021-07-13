package com.hinsliu.monki.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuxuanming
 * @date 2021/7/13 10:11
 * @description: 取景地对象
 */
@Data
public class LocationDO {

    private List<String> images;

    private String introduction;

    private List<String> visit;

}
