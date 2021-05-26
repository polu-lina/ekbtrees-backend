package ru.naumen.ectmauth.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class CryptoUtils {
    public static String sha256WithSalt(String inputString) {
        return DigestUtils.sha256Hex(inputString);
    }
}
