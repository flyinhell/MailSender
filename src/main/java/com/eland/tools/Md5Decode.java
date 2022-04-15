package com.eland.tools;

import org.apache.log4j.Logger;

import java.security.MessageDigest;

/**
 * Created by aronchen on 2017/3/14.
 */
public class Md5Decode {
    private static Logger log = Logger.getLogger("Log");


    private String getHexString(byte[] b) throws Exception {
        String result = "";
        for (int i = 0; i < b.length; ++i) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public String md5(String decodeString) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(decodeString.getBytes());
            md5 = getHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" - " + e.getMessage());
        }
        return md5;
    }
}
