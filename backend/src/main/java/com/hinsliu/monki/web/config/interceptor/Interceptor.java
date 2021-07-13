package com.hinsliu.monki.web.config.interceptor;

import com.hinsliu.monki.common.UtilConstant;
import com.hinsliu.monki.common.annotation.AuthToken;
import com.hinsliu.monki.common.enums.ErrorCodeEnum;
import com.hinsliu.monki.common.exception.BusinessException;
import com.hinsliu.monki.common.utils.redis.RedisUtil;
import com.hinsliu.monki.dal.UserDao;
import com.hinsliu.monki.domain.common.UserInfoHolder;
import com.hinsliu.monki.domain.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Description: Implementation for Interceptor to pre handle the request.
 * @Function: An interceptor，only when Func:preHandle returns true，
 * can the Func:postHandle and Func:afterCompletion be executed.
 * @author: liuxuanming
 * @date: 2021/07/10 6:32 下午
 */
@Slf4j
@Configuration
public class Interceptor implements HandlerInterceptor {

    @Resource
    private UserDao userDao;

    /**
     * 拦截器，鉴权
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 只有被AuthToken注解过的方法，才会被鉴权
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
            String token = request.getHeader(UtilConstant.TOKEN_HEADER);

            // 验证Token
            RedisUtil redis = RedisUtil.getInstance();
            if (token != null && token.length() != 0) {
                String userId = redis.get(token);
                // 如果Token存在于Redis中，更新Token的存在寿命，将该用户的信息保存到ThreadLocal中
                if (userId != null && !userId.equals("")) {
                    // 更新Token
                    redis.set(token, userId);

                    // 保存该用户信息到ThreadLocal中
                    UserDO userDO = userDao.selectByPrimaryKey(Long.parseLong(userId));
                    if (userDO == null || userDO.getId() == null) {
                        throw new BusinessException(ErrorCodeEnum.FAIL.getCode(), "用户ID在数据库中不存在");
                    } else {
                        UserInfoHolder.set(userDO);
                        log.info("用户{}，邮箱{}已经登录，信息已经保存到本地", userDO.getId(), userDO.getEmail());
                    }

                    return true;
                }
            } else {
                // 否则重定向到处理错误的界面
//                try {
//                    response.sendRedirect("/app/callback");
//                    return false;
//                } catch (Exception e) {
//                    log.warn(e.getMessage(), e);
//                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
