package com.summer.tools.test.controller;

import com.summer.tools.test.service.IMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "邮件测试controller")
@RestController
@RequestMapping("/mail")
public class MailTestController {

    @Resource
    private IMailService mailService;


    @ApiOperation("发送邮件, 多个发送人用',' 分割")
    @GetMapping("/send")
    public boolean send(@ApiParam(example = "wangfenlei@sina.cn") @RequestParam String to) {

        this.mailService.sendMail("测试", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "测试", to);
        return true;
    }

    @ApiOperation("发送邮件-带附件, 多个发送人用',' 分割")
    @PostMapping("/sendFile")
    public boolean send(@ApiParam(example = "wangfenlei@sina.cn") @RequestParam(required = false) String to, MultipartFile file) throws IOException{

        String defaultDir = System.getProperty("user.dir");

        List<File> files = new ArrayList<>(1);

        File temFile = new File(defaultDir + File.separator + file.getOriginalFilename());
        file.transferTo(temFile);

        files.add(temFile);

        this.mailService.sendMail("测试", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "测试带文件", files, to);

        return temFile.delete();
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir") + File.separator + "tmp");
    }
}
