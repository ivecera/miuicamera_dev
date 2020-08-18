package com.ss.android.ugc.effectmanager.common.utils;

import android.util.Log;

public class LogUtils {
    private static boolean isDebug;

    public static void d(String str, String str2) {
        if (isDebug) {
            Log.d("djjowfy:  " + str, str2);
        }
    }

    public static void debug() {
        isDebug = true;
    }

    public static void e(String str, String str2) {
        if (isDebug) {
            Log.e("djjowfy:  " + str, str2);
        }
    }
}
