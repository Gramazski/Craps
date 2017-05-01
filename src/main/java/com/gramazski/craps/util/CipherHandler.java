package com.gramazski.craps.util;

import org.apache.commons.codec.digest.DigestUtils;

public class CipherHandler {
    public static String encryptString(String value){
        return DigestUtils.md5Hex(value);
    }
}
