package com.ss.android.ttve.monitor;

import android.support.annotation.Keep;
import com.ss.android.ttve.nativePort.TENativeLibsLoader;
import java.util.HashMap;
import java.util.Map;

@Keep
public class TEMonitorInvoker {
    static {
        TENativeLibsLoader.loadLibrary();
    }

    public static native Map<String, String> nativeGetMap();

    public static native Map<String, String> nativeGetMap(int i);

    public static native void nativeInit();

    public static native void nativeMonitorPerf(int i);

    public static native void nativeMonitorPerfWithType(int i);

    public static native void nativePerfDouble(int i, String str, double d2);

    public static native void nativePerfDouble(String str, double d2);

    public static native void nativePerfLong(int i, String str, long j);

    public static native void nativePerfLong(String str, long j);

    public static native void nativePerfRational(String str, float f2, float f3);

    public static native void nativePerfString(int i, String str, String str2);

    public static native void nativePerfString(String str, String str2);

    public static native void nativeReset();

    public static native void nativeReset(int i);

    public static void onNativeCallback_onMonitorLogFloat(String str, String str2, float f2) {
        TEMonitor.monitorTELog(str, str2, f2);
    }

    public static void onNativeCallback_onMonitorLogInt(String str, String str2, long j) {
        TEMonitor.monitorTELog(str, str2, j);
    }

    public static void onNativeCallback_onMonitorLogJson(String str, String str2, String str3) {
        TEMonitor.monitorTELogJson(str, str2, str3);
    }

    public static void onNativeCallback_onMonitorLogMap(String str, String str2, HashMap hashMap) {
        TEMonitor.monitorTELog(str, str2, hashMap);
    }
}
