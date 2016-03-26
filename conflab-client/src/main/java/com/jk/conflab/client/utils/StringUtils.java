package com.jk.conflab.client.utils;

/**
 * Created by jacky.cheng on 2015/11/26.
 */
public class StringUtils {
    private StringUtils() { }

    public static boolean hasText(String str) {
        return str != null && !str.trim().equals("");
    }
}
