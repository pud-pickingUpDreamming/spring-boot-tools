package com.summer.tools.common;

import com.summer.tools.common.utils.AesUtil;


public class MainTest {
    public static void main(String[] args) {
        String str = "1234abcd";
        AesUtil.KEY = "boss567890encode";
        System.out.println(AesUtil.encrypt(str));
        System.out.println(AesUtil.decrypt(AesUtil.encrypt(str)));
    }
}
