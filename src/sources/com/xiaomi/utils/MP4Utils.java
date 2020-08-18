package com.xiaomi.utils;

import android.util.Log;

public class MP4Utils {
    private static final String TAG = "Mp4Utils";

    public static class MP4VideoInfo {
        public int audioBitRate;
        public String audioMediaName;
        public double frameRate;
        public int height;
        public int keyFrameInterval;
        public int samplingRate;
        public int videoBitRate;
        public String videoMediaName;
        public int width;

        public MP4VideoInfo(String str, String str2, int i, double d2, int i2, int i3, int i4, int i5, int i6) {
            this.videoMediaName = str;
            this.audioMediaName = str2;
            this.keyFrameInterval = i;
            this.frameRate = d2;
            this.samplingRate = i2;
            this.videoBitRate = i3;
            this.audioBitRate = i4;
            this.width = i5;
            this.height = i6;
        }
    }

    public static boolean concateMP4Files(String[] strArr, String str) {
        Log.i(TAG, "concate MP4 files");
        if (!concateMP4FilesJni(strArr, str)) {
            Log.e(TAG, "concate MP4 files failed");
            return false;
        }
        Log.i(TAG, " concate MP4 files succeed to: " + str);
        return true;
    }

    private static native boolean concateMP4FilesJni(String[] strArr, String str);

    public static MP4VideoInfo probeMP4VideoInfo(String str) {
        Log.i(TAG, "probe MP4 video information");
        return probeMP4VideoInfoJni(str);
    }

    private static native MP4VideoInfo probeMP4VideoInfoJni(String str);
}
