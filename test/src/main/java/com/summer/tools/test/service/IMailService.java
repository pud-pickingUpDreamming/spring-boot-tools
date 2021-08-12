package com.summer.tools.test.service;

import java.io.File;
import java.util.List;

public interface IMailService {

    /**
     * @param acceptors 接收人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendMail(String subject, String content, String... acceptors);

    /**
     * @param acceptors 接收人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param files 附件列表
     */
    void sendMail(String subject, String content, List<File> files, String... acceptors);

}
