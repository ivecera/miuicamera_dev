package com.ss.android.vesdk;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.ss.android.ttve.nativePort.TELogcat;

public class VELogUtil {
    private static String APPNAME = "VESDK-";
    private static byte DEBUG_LEVEL = -1;
    private static final byte LOGD = 8;
    private static final byte LOGE = 1;
    private static final byte LOGI = 4;
    private static final byte LOGN = 0;
    private static final byte LOGV = 16;
    private static final byte LOGW = 2;
    public static final byte LOG_LEVEL_D = 15;
    public static final byte LOG_LEVEL_E = 1;
    public static final byte LOG_LEVEL_I = 7;
    public static final byte LOG_LEVEL_N = 0;
    public static final byte LOG_LEVEL_V = 31;
    public static final byte LOG_LEVEL_W = 3;

    public static String __FILE__() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return (stackTrace == null || stackTrace.length < 3) ? "unknown file" : Thread.currentThread().getStackTrace()[2].getFileName();
    }

    public static String __FUNCTION__() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return (stackTrace == null || stackTrace.length < 3) ? "unknown function" : Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static int __LINE__() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null || stackTrace.length < 3) {
            return -1;
        }
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

    public static void d(Class<?> cls, String str) {
        if ((DEBUG_LEVEL & LOGD) != 0) {
            TELogcat.Log(LOGD, APPNAME + cls.getSimpleName(), str);
        }
    }

    public static void d(@NonNull String str, @NonNull String str2) {
        if ((DEBUG_LEVEL & LOGD) != 0) {
            TELogcat.Log(LOGD, APPNAME + str, str2);
        }
    }

    public static void e(Class<?> cls, String str) {
        if ((DEBUG_LEVEL & 1) != 0) {
            TELogcat.Log((byte) 1, APPNAME + cls.getSimpleName(), str);
        }
    }

    public static void e(Class<?> cls, String str, Throwable th) {
        if ((DEBUG_LEVEL & 1) != 0) {
            TELogcat.Log((byte) 1, APPNAME + cls.getSimpleName(), str + " Throwable msg is : " + th.getMessage());
        }
    }

    public static void e(String str, String str2) {
        if ((DEBUG_LEVEL & 1) != 0) {
            TELogcat.Log((byte) 1, APPNAME + str, str2);
        }
    }

    public static byte getLogLevel(byte b2) {
        if (b2 == 1) {
            return 1;
        }
        if (b2 == 3) {
            return 2;
        }
        if (b2 == 7) {
            return LOGI;
        }
        if (b2 == 15) {
            return LOGD;
        }
        if (b2 != 31) {
            return 0;
        }
        return LOGV;
    }

    public static void i(Class<?> cls, String str) {
        if ((DEBUG_LEVEL & LOGI) != 0) {
            TELogcat.Log(LOGI, APPNAME + cls.getSimpleName(), str);
        }
    }

    public static void i(@NonNull String str, @NonNull String str2) {
        if ((DEBUG_LEVEL & LOGI) != 0) {
            TELogcat.Log(LOGI, APPNAME + str, str2);
        }
    }

    public static void setUp(@Nullable String str, byte b2) {
        if (!TextUtils.isEmpty(str)) {
            APPNAME += str + "-";
        }
        DEBUG_LEVEL = b2;
        TELogcat.setLogLevel(getLogLevel(b2));
    }

    public static void v(Class<?> cls, String str) {
        if ((DEBUG_LEVEL & LOGV) != 0) {
            TELogcat.Log(LOGV, APPNAME + cls.getSimpleName(), str);
        }
    }

    public static void v(String str, String str2) {
        if ((DEBUG_LEVEL & LOGV) != 0) {
            TELogcat.Log(LOGV, APPNAME + str, str2);
        }
    }

    public static void w(Class<?> cls, String str) {
        if ((DEBUG_LEVEL & 2) != 0) {
            TELogcat.Log((byte) 2, APPNAME + cls.getSimpleName(), str);
        }
    }

    public static void w(Class<?> cls, String str, Throwable th) {
        if ((DEBUG_LEVEL & 2) != 0) {
            TELogcat.Log((byte) 2, APPNAME + cls.getSimpleName(), str + " Throwable msg is : " + th.getMessage());
        }
    }

    public static void w(String str, String str2) {
        if ((DEBUG_LEVEL & 2) != 0) {
            TELogcat.Log((byte) 2, APPNAME + str, str2);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        if ((DEBUG_LEVEL & 2) != 0) {
            TELogcat.Log((byte) 2, APPNAME + str, str2 + " Throwable msg is : " + th.getMessage());
        }
    }
}
