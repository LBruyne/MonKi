package com.hinsliu.monki.domain.query;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:02
 * @Description: 用户登录所需信息
 */
@Data
public class UserLoginQuery {

    @NotNull(message = "输入邮箱不能为空")
    @Email(message = "输入邮箱格式错误")
    private String email;

}
