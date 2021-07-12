package com.hinsliu.monki.domain.query;

import com.hinsliu.monki.domain.common.PageParam;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:51
 * @description: 搜索提交的表单
 */
@Data
public class SearchQuery extends PageParam {

    @NotNull(message = "输入关键字不能为空")
    private String keyword;

    @NotNull(message = "查找类别不能为空")
    private Integer type;

}
