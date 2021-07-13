package com.hinsliu.monki.service;

import com.hinsliu.monki.domain.common.UserInfoHolder;

/**
 * @author liuxuanming
 * @date 2021/7/12 19:18
 * @description: 基本服务
 */
public class BaseManager {

    public Long getUserId() {
        return UserInfoHolder.get() != null ? UserInfoHolder.get().getId() : null;
    }

    public String getEmail() {
        return UserInfoHolder.get() != null ? UserInfoHolder.get().getEmail() : null;
    }

}
