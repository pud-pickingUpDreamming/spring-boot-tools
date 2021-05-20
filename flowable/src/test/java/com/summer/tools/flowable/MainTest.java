package com.summer.tools.flowable;

import com.summer.tools.flowable.constants.ProcessConstants;
import lombok.experimental.Accessors;

@Accessors
public class MainTest {
    public static void main(String[] args) {
//        System.out.println(Integer.parseUnsignedInt("0000", 2));

//        List<String> strList =  Arrays.asList("a","b","c");
//
//        strList.forEach(f -> {
//            if ("a".equals(f)) {
//                return;
//            }
//            System.out.println(f);
//        });

        System.out.println(ProcessConstants.ProcessListenerTypeEnum.PROCESS_START.getListener());

    }
}
