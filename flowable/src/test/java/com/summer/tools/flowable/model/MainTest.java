package com.summer.tools.flowable.model;

import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Accessors
public class MainTest {
    public static void main(String[] args) {
//        System.out.println(Integer.parseUnsignedInt("0000", 2));

        List<String> strList =  Arrays.asList("a","b","c");

        strList.forEach(f -> {
            if ("a".equals(f)) {
                return;
            }
            System.out.println(f);
        });

    }
}
