package com.hinsliu.monki.domain.common;

import com.hinsliu.monki.domain.model.UserDO;

/**
 * @author liuxuanming
 * @date 2021/7/12 19:12
 * @description: 用户信息保存
 */
public class UserInfoHolder {

    private static ThreadLocal<UserDO> userInfoHolder = new ThreadLocal<>();

    /**
     * 赋值
     */
    public static void set(UserDO userDO) {
        userInfoHolder.set(userDO);
    }
    /**
     * 取值
     */
    public static UserDO get() {
        return userInfoHolder.get();
    }
    /**
     * 移除
     */
    public static void remove(){
        userInfoHolder.remove();
    }

}
