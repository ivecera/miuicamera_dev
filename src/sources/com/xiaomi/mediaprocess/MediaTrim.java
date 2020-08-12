package com.xiaomi.mediaprocess;

import android.util.Log;

public class MediaTrim {
    private static String TAG = "MediaTrim";

    public interface Callback {
        void OnConvertProgress(int i);
    }

    public static int MediaTrim(String str, String str2, long j, long j2, int i, int i2, Callback callback) {
        Log.d(TAG, "MediaTrim");
        return MediaTrimJni(str, str2, j, j2, i, i2, callback);
    }

    private static native int MediaTrimJni(String str, String str2, long j, long j2, int i, int i2, Callback callback);

    public static int cancelMediaTrim(String str) {
        Log.d(TAG, "cancel MediaTrim ");
        return cancelmediatrimJni(str);
    }

    private static native int cancelmediatrimJni(String str);
}
