package com.ss.android.ttve.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class ExtraUIUtil {
    public static final float DEFAULT_SURFACE_SCALE_HEIGHT = 16.0f;
    public static final float DEFAULT_SURFACE_SCALE_WIDTH = 9.0f;
    public static final int SURFACE_LOCATION_HEIGHT_INDEX = 3;
    public static final int SURFACE_LOCATION_WIDTH_INDEX = 2;
    public static final int SURFACE_LOCATION_X_INDEX = 0;
    public static final int SURFACE_LOCATION_Y_INDEX = 1;
    public static final String TAG = "ExtraUIUtil";

    public static float[] calScreenSurfaceLocation(Context context, int i, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        if (i5 == 90 || i5 == 270) {
            i6 = i3;
            i7 = i4;
        } else {
            i7 = i3;
            i6 = i4;
        }
        float f2 = (float) i7;
        float f3 = (float) i6;
        float[] fArr = {0.0f, 0.0f, f2, f3};
        if (context == null) {
            Log.e(TAG, "Context is null while calculating surface location!");
            return fArr;
        }
        if (f2 * 16.0f > f3 * 9.0f) {
            float f4 = (float) i;
            fArr[2] = f4;
            fArr[3] = ((f3 * 1.0f) * f4) / f2;
        } else {
            float f5 = (float) i2;
            fArr[3] = f5;
            fArr[2] = (f5 * 9.0f) / 16.0f;
            float f6 = (float) i;
            fArr[0] = (f6 - fArr[2]) / 2.0f;
            if (fArr[0] > 0.0f) {
                fArr[0] = 0.0f;
                fArr[2] = f6;
                fArr[3] = ((f3 * 1.0f) * f6) / f2;
            }
        }
        Log.d(TAG, String.format("video[%d, %d], max[%d, %d], screen[%d, %d], surface[%f, %f, %f, %f]", Integer.valueOf(i7), Integer.valueOf(i6), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(UIUtils.getScreenWidth(context)), Integer.valueOf(UIUtils.getScreenHeight(context)), Float.valueOf(fArr[0]), Float.valueOf(fArr[1]), Float.valueOf(fArr[2]), Float.valueOf(fArr[3])));
        return fArr;
    }

    public static int getNavigationBarHeight(Context context) {
        int identifier;
        if (context != null && (identifier = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android")) > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    @TargetApi(17)
    public static int getRealDisplayHeight(Context context) {
        if (context == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        } catch (Exception unused) {
            return UIUtils.getScreenHeight(context);
        }
    }
}
