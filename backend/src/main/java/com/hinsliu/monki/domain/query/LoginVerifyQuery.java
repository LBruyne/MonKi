package com.hinsliu.monki.domain.query;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:34
 * @description: 验证用户登录信息表单
 */
@Data
public class LoginVerifyQuery {

    @NotNull(message = "验证码不能为空")
    @Length(min = 4, max = 4, message = "验证码长度不符")
    private String verifyCode;

    @NotNull(message = "输入邮箱不能为空")
    @Email(message = "输入邮箱格式错误")
    private String email;

}
