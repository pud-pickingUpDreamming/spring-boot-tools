package com.summer.tools.common;

import com.summer.tools.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j

public class BaseWebToolsTest {

    @Test
    public void aesUtilTest() {
        log.info(AesUtil.encrypt("123"));
        log.info(AesUtil.decrypt(AesUtil.encrypt("123")));
    }
}
