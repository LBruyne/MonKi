package com.hinsliu.monki.common.utils.mailer;

import com.hinsliu.monki.common.enums.ErrorCodeEnum;
import com.hinsliu.monki.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

/**
 * @author liuxuanming
 * @date 2021/7/12 21:11
 * @description: 邮件发送工具类
 */
@Slf4j
@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    private final static String subject = "MonKi Movie Travelling Login Verification";

    /**
     * 发送简单文本文件
     */
    public void sendSimpleEmail(String target, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(target);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            log.info("发送邮件：收件人->{}，内容->{}", target, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送html文本
     */
    public void sendHtmlEmail(String target, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
            messageHelper.setFrom(from);
            messageHelper.setTo(target);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            mailSender.send(message);
            log.info("发送邮件：收件人->{}，内容->{}", target, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
