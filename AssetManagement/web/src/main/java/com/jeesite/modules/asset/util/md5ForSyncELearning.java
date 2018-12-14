package com.jeesite.modules.asset.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//
/**
 * 按照业务要求的方式加密，md5方式，作为sign_参数值，给e-learing平台同步数据用
 * 具体参考调用文档：http://v4.21tb.com/open/platformDoc/index.do#/代码示例
 * 返回要求所有英文字母大写
 * @author thomas
 * @version 2018-11-20
 */

public class md5ForSyncELearning {
    private static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String calculateSign(String appSecrets, String url) {

        String signingText = appSecrets + "|" + url + "|" + appSecrets;
        return md5ForSyncELearning.md5(signingText);
    }

    public static String md5(String input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e1) {
            // impossible to be here.
            e1.printStackTrace();
        }
        try {
            md.update(input.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // impossible to be here.
            e.printStackTrace();
        }
        byte[] byteDigest = md.digest();

        return toHexString(byteDigest);
    }

    public static String toHexString(byte[] byteDigest) {
        char[] chars = new char[byteDigest.length * 2];
        for (int i = 0; i < byteDigest.length; i++) {
            // left is higher.
            chars[i * 2] = HEX_DIGITS[byteDigest[i] >> 4 & 0x0F];
            // right is lower.
            chars[i * 2 + 1] = HEX_DIGITS[byteDigest[i] & 0x0F];
        }
        return new String(chars).toUpperCase();
    }
}
