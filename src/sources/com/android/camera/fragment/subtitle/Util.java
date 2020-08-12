package com.android.camera.fragment.subtitle;

import com.android.camera.log.Log;

public class Util {
    private static String TAG = "SubtitleUtil";

    static {
        try {
            System.loadLibrary("subtitle_jni");
        } catch (Throwable th) {
            Log.e(TAG, "load subtitle_jni.so failed.", th);
        }
    }

    private static native byte[] genAccessAppID();

    private static native byte[] genAccessAppKey();

    private static native byte[] genAccessAppSecret();

    public static String getAccessAppID() {
        return new String(genAccessAppID());
    }

    public static String getAccessAppKey() {
        return new String(genAccessAppKey());
    }

    public static String getAccessAppSecret() {
        return new String(genAccessAppSecret());
    }
}
