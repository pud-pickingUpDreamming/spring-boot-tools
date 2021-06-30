package com.summer.tools.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.AES;
import com.summer.tools.common.utils.AesUtil;

import java.util.UUID;

public class MainTest {
    public static void main(String[] args) {
//        System.out.println(UUID.randomUUID().toString().replace("-", ""));
//        mpw:
        System.out.println(AesUtil.encrypt("jdbc:postgresql://1.15.132.121:7003/postgres?useAffectedRows=true&currentSchema=public", AesUtil.KEY));
        System.out.println(AesUtil.encrypt("postgres", AesUtil.KEY));
        System.out.println(AesUtil.encrypt("admin", AesUtil.KEY));
    }
}
