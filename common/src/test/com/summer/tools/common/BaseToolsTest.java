package com.summer.tools.common;

import com.summer.tools.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@Slf4j
@SpringBootTest
public class BaseToolsTest {

    /**
     * 可以通过修改application-dev.yml中的加密参数测试加密效果
     */
    @Test
    public void aesUtilTest() {
        log.info(AesUtil.encrypt("123"));
        log.info(AesUtil.decrypt(AesUtil.encrypt("123")));
    }

    @Test
    public void fileUtilTest() {
        String fileName = "E:\\temp\\vm_doc\\backup_db.bat";
        log.info(FileUtil.readFileToString(new File(fileName), "UTF-8"));
    }

    @Test
    public void JsonUtilTest() {
        log.info(JsonUtil.stringify(Person.getPerson()));
        System.out.println(JsonUtil.parseMap(JsonUtil.stringify(Person.getPerson()), Object.class));
    }

    @Test
    public void shellUtilTest() {
        ShellUtil.exec("ping baidu.com", true);
    }

    @Test
    public void signUtilTest() {
        log.info(SignUtil.signWithSha("abc"));
        log.info(SignUtil.signWithMd5("abc"));
    }

    @Test
    public void xmlUtilTest() {
        log.info(XmlUtil.toXmlStr(Person.getPerson(), Person.class));
        System.out.println(XmlUtil.toJavaBean(XmlUtil.toXmlStr(Person.getPerson(), Person.class), Person.class));
    }
}
