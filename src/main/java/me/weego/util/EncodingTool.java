package me.weego.util;

import java.io.UnsupportedEncodingException;

/**
 * @author ln
 */
public class EncodingTool {
    public static String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
