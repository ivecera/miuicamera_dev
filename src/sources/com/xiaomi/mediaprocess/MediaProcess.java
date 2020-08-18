package com.xiaomi.mediaprocess;

import android.util.Log;

public class MediaProcess {
    public static final String[] FILTER_SAMPLE_PARAMS = {"com.videofilter.basic", "com.videofilter.sweet", "com.videofilter.crema", "com.videofilter.nashville", "com.videofilter.aden", "com.videofilter.gingham", "com.videofilter.stinson", "com.videofilter.clarendon", "com.videofilter.juno", "com.videofilter.dogpatch", "com.videofilter.gray"};
    private static String TAG = "MediaProcess";

    public interface Callback {
        void OnConvertProgress(int i);
    }

    public static int AddGrayscaleFilter() {
        Log.d(TAG, "AddGrayscaleFilter");
        return AddGrayscaleFilterJni();
    }

    private static native int AddGrayscaleFilterJni();

    public static int AddMotionFlowFilter() {
        Log.d(TAG, "AddMotionFlowFilter");
        return AddMotionFlowFilterJni();
    }

    private static native int AddMotionFlowFilterJni();

    public static int AddMp3MixFilter(String str, long j, long j2, float f2, float f3, long j3) {
        Log.d(TAG, "AddMp3MixFilter");
        return AddMp3MixFilterJni(str, j, j2, f2, f3, j3);
    }

    private static native int AddMp3MixFilterJni(String str, long j, long j2, float f2, float f3, long j3);

    public static int AddPhotoFilter(String str, float f2) {
        Log.d(TAG, "AddPhotoFilter");
        return AddPhotoFilterJni(str, f2);
    }

    private static native int AddPhotoFilterJni(String str, float f2);

    public static int AddPngMixFilter(String str, float f2, float f3, float f4, float f5) {
        Log.d(TAG, "AddPngMixFilter");
        return AddPngMixFilterJni(str, f2, f3, f4, f5);
    }

    private static native int AddPngMixFilterJni(String str, float f2, float f3, float f4, float f5);

    public static int AddRotateFilter(int i) {
        Log.d(TAG, "AddRotateFilter");
        return AddRotateFilterJni(i);
    }

    private static native int AddRotateFilterJni(int i);

    public static int AddScaleFilter(int i, int i2) {
        Log.d(TAG, "AddScaleFilter");
        return AddScaleFilterJni(i, i2);
    }

    private static native int AddScaleFilterJni(int i, int i2);

    public static int AddShakeFilter() {
        Log.d(TAG, "AddShakeFilter");
        return AddShakeFilterJni();
    }

    private static native int AddShakeFilterJni();

    public static int AddSobelEdgeDetectionFilter() {
        Log.d(TAG, "AddSobeEdgeDetectionFilter");
        return AddSobelEdgeDetectionFilterJni();
    }

    private static native int AddSobelEdgeDetectionFilterJni();

    public static int AddSoulFilter() {
        Log.d(TAG, "AddSoulFilter");
        return AddSoulFilterJni();
    }

    private static native int AddSoulFilterJni();

    public static int AddVideoMapFilter(String str, String str2, String str3) {
        Log.d(TAG, "AddVideoMapFilter");
        return AddVideoMapFilterJni(str, str2, str3);
    }

    private static native int AddVideoMapFilterJni(String str, String str2, String str3);

    public static int Convert(String str, long j, String str2, boolean z, int i, int i2, long j2, long j3, float f2, Callback callback) {
        Log.d(TAG, "Convert");
        return ConvertJni(str, j, str2, z, i, i2, j2, j3, f2, callback);
    }

    private static native int ConvertJni(String str, long j, String str2, boolean z, int i, int i2, long j2, long j3, float f2, Callback callback);

    public static int cancelmediaconvert(String str) {
        Log.d(TAG, "cancel mediaconvert ");
        return cancelmediaconvertJni(str);
    }

    private static native int cancelmediaconvertJni(String str);

    public static int mediaconvert(String str, long j, String str2, String str3, float f2, String str4, float f3, float f4, float f5, float f6, boolean z, PngShowInfo[] pngShowInfoArr, double d2, int i, long j2, long j3, float f7, String str5, long j4, long j5, float f8, float f9, Callback callback) {
        Log.d(TAG, "mediaconvert");
        return mediaconvertJni(str, j, str2, str3, f2, str4, f3, f4, f5, f6, z, pngShowInfoArr, d2, i, j2, j3, f7, str5, j4, j5, f8, f9, callback);
    }

    private static native int mediaconvertJni(String str, long j, String str2, String str3, float f2, String str4, float f3, float f4, float f5, float f6, boolean z, PngShowInfo[] pngShowInfoArr, double d2, int i, long j2, long j3, float f7, String str5, long j4, long j5, float f8, float f9, Callback callback);
}
