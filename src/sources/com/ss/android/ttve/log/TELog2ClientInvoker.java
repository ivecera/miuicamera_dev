package com.ss.android.ttve.log;

import android.support.annotation.Keep;
import com.ss.android.ttve.nativePort.TENativeLibsLoader;

@Keep
public class TELog2ClientInvoker {
    static {
        TENativeLibsLoader.loadLibrary();
    }

    public static native void nativeInit();

    public static native void nativeSetLog2ClientSwitch(boolean z);

    public static void onNativeCallback_logToLocal(int i, String str) {
        TELog2Client.logToLocal(i, str);
    }
}
