package com.summer.tools.common;

import com.summer.tools.common.model.Person;
import com.summer.tools.common.model.PlainBean;
import com.summer.tools.common.model.Source;
import com.summer.tools.common.model.Target;
import com.summer.tools.common.services.ICloudStorageService;
import com.summer.tools.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@SpringBootTest
public class BaseToolsTest {
    @Resource
    private ICloudStorageService storageService;

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

    @Test
    public void dateUtilTest() {
        System.out.println(DateUtil.format(LocalDateTime.now()));
        System.out.println(DateUtil.format(LocalDateTime.now(), DateUtil.YEAR_MONTH_DATE));

        System.out.println(DateUtil.parse("2020-10-27 18:22:08"));
        System.out.println(DateUtil.parse("20201027 182208", "yyyyMMdd HHmmss"));

        System.out.println(DateUtil.parseLocalDate("2020-10-27"));
        System.out.println(DateUtil.parseLocalDate("20201027", "yyyyMMdd"));

        System.out.println(DateUtil.parseLocalTime("18:22:08"));
        System.out.println(DateUtil.parseLocalTime("182208", "HHmmss"));

        System.out.println(DateUtil.toDate(LocalDateTime.now()));
        System.out.println(DateUtil.toDate(LocalDate.now()));
        System.out.println(DateUtil.toLocalDateTime(new Date()));

        System.out.println(DateUtil.startTimeOfDay());
        System.out.println(DateUtil.startDayOfMonth(2020, 10));
        System.out.println(DateUtil.startDayOfMonth());
        System.out.println(DateUtil.startDayOfYear());

        System.out.println(DateUtil.weekIndex());
        System.out.println(DateUtil.startDayOfWeek());

        System.out.println(DateUtil.diff(LocalDateTime.now(), LocalDateTime.now().plusHours(3), ChronoUnit.SECONDS));

        System.out.println(DateUtil.plus(LocalDateTime.now(), 3, ChronoUnit.SECONDS));
        System.out.println(LocalDateTime.now());
        System.out.println(DateUtil.minus(LocalDateTime.now(), 3, ChronoUnit.SECONDS));
    }

    @Test
    public void rsaUtilTest() {
        String privateFilePath = "D:\\data\\cert\\private.key";
        String publicFilePath = "D:\\data\\cert\\public.key";

        RsaUtil.generateKey(publicFilePath, privateFilePath, "123");
        log.info(new String(Objects.requireNonNull(RsaUtil.getPublicKey(publicFilePath)).getEncoded()));
        log.info(new String(Objects.requireNonNull(RsaUtil.getPrivateKey(privateFilePath)).getEncoded()));
    }

    @Test
    public void beanUtilTest() {
        Source source = new Source();
        PlainBean bean = new PlainBean();
        bean.setPro1("beanPro2").setPro2("beanPro2");
        source.setPro1(null).setPro2("sPro2").setPro3(bean);

        Target target = new Target().setPro1("haveValue");
        // 期望target 的pro1值不会被source的pro1(null)覆盖,并且可以把source的值拷贝过来(包括source的子对象)
        BeanUtil.copyProperties(source, target, null);
        System.out.println(source);
        System.out.println(target);
    }

    @Test
    public void ftpUploadTest() {
        String serverPath = "/root/temp";
        File clientFile = new File("D:\\data\\flowable\\approve.bpmn20.xml");
        FtpUtil.upload(serverPath,clientFile);
    }

    @Test
    public void ftpDownloadTest() {
        String serverFile = "/root/temp/server.conf";
        File clientPath = new File("D:\\data\\flowable");
        FtpUtil.download(serverFile,clientPath);
    }

    @Test
    public void sftpUploadTest() {
        String serverPath = "/root/temp/";
        File clientFile = new File("D:\\data\\flowable\\approve.bpmn20.xml");
        SFtpUtil.upload(serverPath,clientFile);
    }

    @Test
    public void sftpDownloadTest() {
        String serverFile = "/root/temp/server.conf";
        File clientPath = new File("D:\\data\\flowable\\");
        SFtpUtil.download(serverFile,clientPath);
    }

    @Test
    public void azureListTest() {
        List<String> fileNames = storageService.listBlobs("flowable");
        fileNames.forEach(log::info);
    }

    /**
     * flowable 前面不要加反斜杠
     */
    @Test
    public void azureDownloadTest() {
        storageService.download("flowable/测试.bpmn20.xml","D:\\data\\azure\\");
    }

    @Test
    public void azureUploadTest() {
        storageService.uploadFile(new File("D:\\data\\azure\\flowable\\test.bpmn20.xml"),"flowable");
    }
}
