package com.android.camera.resource;

import com.android.camera.log.Log;

public class RequestHelper {
    private static String TAG = "RequestHelper";

    static {
        try {
            System.loadLibrary("com.xiaomi.camera.requestutil");
        } catch (Throwable th) {
            Log.e(TAG, "load requestutil.so failed.", th);
        }
    }

    private static native byte[] genTMAccessKey();

    private static native byte[] generate(long j, long j2);

    public static String getIdentityID() {
        return "MIUICamera";
    }

    public static String getTMMusicAccessKey() {
        return new String(genTMAccessKey());
    }

    public static String md5(long j, long j2) {
        byte[] generate = generate(j, j2);
        String str = "";
        for (int i = 0; i < generate.length; i++) {
            str = str + Integer.toHexString((generate[i] & 255) | -256).substring(6);
        }
        return str;
    }
}
