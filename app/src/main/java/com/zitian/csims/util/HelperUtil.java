package com.zitian.csims.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class HelperUtil {
    public static boolean isInteger(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (!str.matches("^[0-9]*$")) {
            return false;
        }
        return true;
    }
}
