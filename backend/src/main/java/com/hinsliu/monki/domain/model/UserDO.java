package com.hinsliu.monki.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * t_user
 * @author 
 */
@ApiModel(value="com.hinsliu.monki.domain.model.UserDOuser information")
@Data
public class UserDO implements Serializable {

    private Long id;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value="用户邮箱")
    private String email;

    private static final long serialVersionUID = 1L;
}