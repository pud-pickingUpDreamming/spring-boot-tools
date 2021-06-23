package com.summer.tools.common;

import com.summer.tools.common.utils.AesUtil;
import com.summer.tools.common.utils.LocationUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
public class MainTest {
    public static void main(String[] args) {
//        String str = "1234abcd";
//        AesUtil.KEY = "boss567890encode";
//        System.out.println(AesUtil.encrypt(str));
//        System.out.println(AesUtil.decrypt(AesUtil.encrypt(str)));
//        log.info("归属城市:[{}]", LocationUtil.getLocationByIP("1.15.74.240"));
//        System.out.println(MessageFormat.format("pNo=,offset={0},limit={1}", 1, 1));

//        String dateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//                .format(LocalDateTime.ofEpochSecond(1622787084L, 0, ZoneOffset.ofHours(8)));
//        System.out.println(dateStr);
        LocalDateTime now = LocalDateTime.now();
        String descriptionFormat = "[{0}项目],根据里程碑要求,应于[{1}年{2}月{3}日]完成[{4}验收],请及时跟进处理。";
        String test = MessageFormat.format(descriptionFormat, "123", String.valueOf(now.getYear()), now.getMonthValue(), now.getDayOfMonth(),"初验");
        System.out.println(test);

    }
}
