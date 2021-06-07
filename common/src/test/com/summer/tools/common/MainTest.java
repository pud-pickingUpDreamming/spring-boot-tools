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

        String dateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .format(LocalDateTime.ofEpochSecond(1622787084L, 0, ZoneOffset.ofHours(8)));
        log.info("归属城市:[{}]", LocationUtil.getLocationByIP("1.15.74.240"));
        System.out.println(dateStr);
    }
}
