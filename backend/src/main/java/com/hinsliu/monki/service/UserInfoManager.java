package com.hinsliu.monki.service;

import com.hinsliu.monki.domain.common.RpcResult;
import com.hinsliu.monki.domain.query.LoginVerifyQuery;
import com.hinsliu.monki.domain.query.UserLoginQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:02
 * @Description: 用户信息处理
 */
@Slf4j
@Service
public class UserInfoManager {

    public void login(UserLoginQuery query) {
    }

    public String verify(LoginVerifyQuery query) {
        return null;
    }
}
