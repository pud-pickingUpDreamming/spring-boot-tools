package com.summer.tools.test.service.impl;

import com.summer.tools.test.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Slf4j
@Component
public class MailServiceImpl implements IMailService {

    @Resource
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String subject, String content, String... acceptors) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(acceptors);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    @Override
    public void sendMail(String subject, String content, List<File> files, String... acceptors) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(from);
            helper.setTo(acceptors);
            helper.setSubject(subject);
            helper.setText(content);

            for (File file:files) {
                helper.addAttachment(file.getName(), file);
            }

            javaMailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            log.error("发送邮件失败:", ex);
        }
    }
}
