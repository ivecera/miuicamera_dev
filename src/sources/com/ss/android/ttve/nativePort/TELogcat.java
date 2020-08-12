package com.ss.android.ttve.nativePort;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;

@Keep
public class TELogcat {
    static {
        TENativeLibsLoader.loadLibrary();
    }

    public static void Log(byte b2, @NonNull String str, @NonNull String str2) {
        nativeLog(b2, str, str2);
    }

    private static native void nativeLog(byte b2, String str, String str2);

    private static native void nativeSetLogLevel(byte b2);

    public static void setLogLevel(byte b2) {
        nativeSetLogLevel(b2);
    }
}
