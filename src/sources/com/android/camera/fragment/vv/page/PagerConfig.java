package com.android.camera.fragment.vv.page;

public class PagerConfig {
    private static final String TAG = "PagerGrid";
    private static int sFlingThreshold = 1000;
    private static float sMillisecondsPreInch = 60.0f;
    private static boolean sShowLog;

    public static int getFlingThreshold() {
        return sFlingThreshold;
    }

    public static float getMillisecondsPreInch() {
        return sMillisecondsPreInch;
    }

    public static boolean isShowLog() {
        return sShowLog;
    }

    public static void setFlingThreshold(int i) {
        sFlingThreshold = i;
    }

    public static void setMillisecondsPreInch(float f2) {
        sMillisecondsPreInch = f2;
    }

    public static void setShowLog(boolean z) {
        sShowLog = z;
    }
}
