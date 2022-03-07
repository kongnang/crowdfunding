package com.util;

import org.junit.jupiter.api.Test;

/**
 * @author qiu
 * @create 2021-12-27 16:30
 */
public class UtilTest {
    @Test
    public void encryptTest(){
        String encrypt = CrowFundingUtil.encrypt("123");
        System.out.println(encrypt);
    }
}
