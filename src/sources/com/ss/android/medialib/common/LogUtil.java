package com.ss.android.medialib.common;

import android.util.Log;

public class LogUtil {
    private static String APPNAME = "VESDK-";
    private static byte DEBUG_LEVEL = 3;
    public static final byte DEBUG_LEVEL_D = 15;
    public static final byte DEBUG_LEVEL_E = 1;
    public static final byte DEBUG_LEVEL_I = 7;
    public static final byte DEBUG_LEVEL_N = 0;
    public static final byte DEBUG_LEVEL_V = 31;
    public static final byte DEBUG_LEVEL_W = 3;
    private static byte LOGD = 8;
    private static byte LOGE = 1;
    private static byte LOGI = 4;
    private static byte LOGV = 16;
    private static byte LOGW = 2;

    public static void d(Class<?> cls, String str) {
        if ((LOGD & DEBUG_LEVEL) != 0) {
            Log.d(APPNAME + cls.getSimpleName(), str);
        }
    }

    public static void d(String str, String str2) {
        if ((LOGD & DEBUG_LEVEL) != 0) {
            Log.d(APPNAME + str, str2);
        }
    }

    public static void e(Class<?> cls, String str) {
        if ((LOGE & DEBUG_LEVEL) != 0) {
            Log.e(APPNAME + cls.getSimpleName(), str);
        }
    }

    public static void e(Class<?> cls, String str, Throwable th) {
        if ((LOGE & DEBUG_LEVEL) != 0) {
            Log.e(APPNAME + cls.getSimpleName(), str, th);
        }
    }

    public static void e(String str, String str2) {
        if ((LOGE & DEBUG_LEVEL) != 0) {
            Log.e(APPNAME + str, str2);
        }
    }

    public static int getAndroidLogLevel(byte b2) {
        if (b2 == 1) {
            return 6;
        }
        if (b2 == 3) {
            return 5;
        }
        if (b2 == 7) {
            return 4;
        }
        if (b2 != 15) {
            return b2 != 31 ? 0 : 2;
        }
        return 3;
    }

    public static byte getLogLevel(int i) {
        if (i == 2) {
            return 31;
        }
        if (i == 3) {
            return 15;
        }
        if (i == 4) {
            return 7;
        }
        if (i != 5) {
            return i != 6 ? (byte) 0 : 1;
        }
        return 3;
    }

    public static void i(String str, String str2) {
        if ((LOGI & DEBUG_LEVEL) != 0) {
            Log.i(APPNAME + str, str2);
        }
    }

    public static void setUp(String str, byte b2) {
        if (str != null && str.length() > 0) {
            APPNAME = str + "-";
        }
        DEBUG_LEVEL = b2;
    }

    public static void setUp(String str, int i) {
        setUp(str, getLogLevel(i));
    }

    public static void v(Class<?> cls, String str) {
        if ((LOGV & DEBUG_LEVEL) != 0) {
            Log.v(APPNAME + cls.getSimpleName(), str);
        }
    }

    public static void v(String str, String str2) {
        if ((LOGV & DEBUG_LEVEL) != 0) {
            Log.v(APPNAME + str, str2);
        }
    }

    public static void w(Class<?> cls, String str) {
        if ((LOGW & DEBUG_LEVEL) != 0) {
            Log.w(APPNAME + cls.getSimpleName(), str);
        }
    }

    public static void w(Class<?> cls, String str, Throwable th) {
        if ((LOGW & DEBUG_LEVEL) != 0) {
            Log.w(APPNAME + cls.getSimpleName(), str, th);
        }
    }

    public static void w(String str, String str2) {
        if ((LOGW & DEBUG_LEVEL) != 0) {
            Log.w(APPNAME + str, str2);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        if ((LOGW & DEBUG_LEVEL) != 0) {
            Log.w(APPNAME + str, str2, th);
        }
    }
}
