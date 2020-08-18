package com.xiaomi.utils;

import android.util.Log;

public class PerformanceDetect {
    private static final String TAG = "PerformanceDetect";

    public static long timeUsedToFilter() {
        Log.i(TAG, "time used to filter");
        return timeUsedToFilterJni();
    }

    private static native long timeUsedToFilterJni();

    public static long timeUsedToSmooth() {
        Log.i(TAG, "time used to smooth");
        return timeUsedToSmoothJni();
    }

    public static long timeUsedToSmoothFilter() {
        Log.i(TAG, "time used to smooth filter");
        return timeUsedToSmoothFilterJni();
    }

    private static native long timeUsedToSmoothFilterJni();

    private static native long timeUsedToSmoothJni();

    public static long timeUsedToSmoothStickerRender() {
        Log.i(TAG, "time used to smooth sticker render");
        return timeUsedToSmoothStickerRenderJni();
    }

    public static long timeUsedToSmoothStickerRenderFilter() {
        Log.i(TAG, "time used to smooth sticker render filter");
        return timeUsedToSmoothStickerRenderFilterJni();
    }

    private static native long timeUsedToSmoothStickerRenderFilterJni();

    private static native long timeUsedToSmoothStickerRenderJni();

    public static long timeUsedToStickerRender() {
        Log.i(TAG, "time used to sticker render");
        return timeUsedToStickerRenderJni();
    }

    public static long timeUsedToStickerRenderFilter() {
        Log.i(TAG, "time used to sticker render filter");
        return timeUsedToStickerRenderFilterJni();
    }

    private static native long timeUsedToStickerRenderFilterJni();

    private static native long timeUsedToStickerRenderJni();
}
