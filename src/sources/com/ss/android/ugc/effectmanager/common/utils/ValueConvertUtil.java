package com.ss.android.ugc.effectmanager.common.utils;

public class ValueConvertUtil {
    public static long ConvertStringToLong(String str, long j) {
        if (str == null || str.isEmpty()) {
            return j;
        }
        try {
            return Long.valueOf(str).longValue();
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return j;
        }
    }
}
