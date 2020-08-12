package com.ss.android.medialib.common;

import android.support.annotation.Keep;
import com.ss.android.ttve.nativePort.TENativeLibsLoader;

@Keep
public class LogInvoker {
    static {
        TENativeLibsLoader.loadLibrary();
    }

    private static native void nativeSetLogLevel(int i);

    public static void setLogLevel(int i) {
        nativeSetLogLevel(i);
    }
}
