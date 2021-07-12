package com.hinsliu.monki.service;

import com.hinsliu.monki.common.enums.ErrorCodeEnum;
import com.hinsliu.monki.common.exception.BusinessException;
import com.hinsliu.monki.common.utils.mailer.EmailSender;
import com.hinsliu.monki.common.utils.redis.RedisUtil;
import com.hinsliu.monki.common.utils.token.TokenGenerator;
import com.hinsliu.monki.common.utils.token.VerificationCodeGenerator;
import com.hinsliu.monki.dal.UserDao;
import com.hinsliu.monki.domain.model.UserDO;
import com.hinsliu.monki.domain.query.LoginVerifyQuery;
import com.hinsliu.monki.domain.query.UserLoginQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:02
 * @Description: 用户信息处理
 */
@Slf4j
@Service
public class UserInfoManager extends BaseManager {

    @Resource
    private UserDao userDao;

    @Resource
    private EmailSender emailSender;

    /**
     * 发送邮件到用户给出的邮箱
     * @param query
     */
    public void login(UserLoginQuery query) {
        // 生成验证码
        String code = VerificationCodeGenerator.generate();
        String email = query.getEmail();

        // 向邮箱发送验证码
        emailSender.sendSimpleEmail(email, code);

        // 将生成的验证码保存到REDIS中
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.set(email, code);
    }

    public String verify(LoginVerifyQuery query) {
        String inputCode = query.getVerifyCode();
        String email = query.getEmail();

        // 从REDIS中取出真正的code
        RedisUtil redisUtil = RedisUtil.getInstance();
        String verifyCode = redisUtil.get(email);

        // 进行比对，
        // 如果验证成功，生成Token，存储信息到Redis，并携带返回，
        // 如果用户不存在则新建用户，用户存在则查找该用户
        // 如果不成功，返回错误信息
        if(verifyCode == null || verifyCode.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.FAIL.getCode(), "验证码未发送或验证码已经过期，请重新请求发送！");
        } else if(!verifyCode.equals(inputCode)) {
            throw new BusinessException(ErrorCodeEnum.FAIL.getCode(), "验证码错误，请重新输入！");
        } else {
            String token = TokenGenerator.generate();
            // 清除邮箱和验证码
            redisUtil.remove(email);

            // 获取用户信息
            UserDO userDO = createOrGetUserInfo(email);
            // 设置Token
            redisUtil.set(token, userDO.getEmail());

            log.info("设置并返回token: {}", token);
            return token;
        }
    }

    private UserDO createOrGetUserInfo(String email) {
        UserDO userDO = userDao.selectByEmail(email);
        if(userDO == null) {
            userDO = new UserDO();
            userDO.setEmail(email);
            userDao.insert(userDO);
            log.info("新建用户信息：用户{}，邮箱{}", userDO.getId(), userDO.getEmail());
        } else {
            log.info("查找到用户信息：用户{}，邮箱{}", userDO.getId(), userDO.getEmail());
        }
        return userDO;
    }

}
