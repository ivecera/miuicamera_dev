package com.ss.android.medialib.log;

import android.support.annotation.Keep;
import com.ss.android.ttve.nativePort.TENativeLibsLoader;

@Keep
public class Log2ClientInvoker {
    static {
        TENativeLibsLoader.loadLibrary();
    }

    public static native void nativeInit();

    public static native void nativeSetLog2ClientSwitch(boolean z);

    public static void onNativeCallback_logToLocal(int i, String str) {
        Log2Client.logToLocal(i, str);
    }
}
