package com.hinsliu.monki.domain.view;

import com.hinsliu.monki.domain.model.LocationDO;
import com.hinsliu.monki.domain.model.MusicDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuxuanming
 * @date 2021/7/13 10:02
 * @description: 电影核心信息视图
 */
@Data
@ApiModel
public class MovieMetaDTO {

    @ApiModelProperty(value = "电影id，是一个字符串")
    private String id;

    @ApiModelProperty(value = "电影名字")
    private String name;

    @ApiModelProperty(value = "电影导演")
    private List<String> director;

    @ApiModelProperty(value = "电影打分")
    private Double rating;

    @ApiModelProperty(value = "电影分类")
    private List<String> genre;

    @ApiModelProperty(value = "电影海报链接")
    private String post;

    @ApiModelProperty(value = "电影出品年份")
    private String year;

    @ApiModelProperty(value = "配乐信息")
    private List<MusicDTO> music;

    @ApiModelProperty(value = "取景地信息")
    private LocationDTO location;

}
