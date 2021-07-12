package com.hinsliu.monki.web.controller;

import com.hinsliu.monki.domain.common.RpcResult;
import com.hinsliu.monki.domain.query.LoginVerifyQuery;
import com.hinsliu.monki.domain.query.UserLoginQuery;
import com.hinsliu.monki.service.UserInfoManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: controller for login and registry services
 * @author: liuxuanming
 * @date: 2021/07/12 1:39 下午
 */
@Api(value = "用户登录接口")
@Slf4j
@RestController
@RequestMapping("/app")
public class LoginController {

    @Resource
    private UserInfoManager userInfoManager;

    @ApiOperation(value = "发送验证码到指定邮箱")
    @RequestMapping(value = "/login/getVerifyCode", method = {RequestMethod.POST})
    public RpcResult login(@RequestBody @Validated UserLoginQuery query) {
        userInfoManager.login(query);
        return  RpcResult.successResult();
    }

    @ApiOperation(value = "根据提交的验证码进行验证，成功返回Token")
    @RequestMapping(value = "/login/verify", method = {RequestMethod.POST})
    public RpcResult verify(@RequestBody @Validated LoginVerifyQuery query) {
        return RpcResult.successResult(userInfoManager.verify(query));
    }

    @RequestMapping(value = "/callback", method = {RequestMethod.POST,RequestMethod.GET})
    public RpcResult callback() {
        return RpcResult.errorResult("未授权行为");
    }

}
