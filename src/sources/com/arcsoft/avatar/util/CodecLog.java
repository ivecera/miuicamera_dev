package com.arcsoft.avatar.util;

import android.util.Log;

public class CodecLog {

    /* renamed from: a  reason: collision with root package name */
    private static boolean f172a;

    public static void d(String str, String str2) {
        if (f172a) {
            Log.d(str, str2);
        }
    }

    public static void e(String str, String str2) {
        if (f172a) {
            Log.e(str, str2);
        }
    }

    public static void enableLog(boolean z) {
        f172a = z;
    }

    public static void i(String str, String str2) {
        if (f172a) {
            Log.i(str, str2);
        }
    }
}
