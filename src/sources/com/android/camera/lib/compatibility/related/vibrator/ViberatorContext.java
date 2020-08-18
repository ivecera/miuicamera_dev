package com.android.camera.lib.compatibility.related.vibrator;

import android.content.Context;
import android.util.Log;

public class ViberatorContext {
    private static final String TAG = "ViberatorContext";
    private static volatile ViberatorContext mInstance;
    ViberatorContext mViberator;

    private ViberatorContext(Context context) {
        Log.d(TAG, "ViberatorContext: init legacy class");
    }

    public static ViberatorContext getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ViberatorContext.class) {
                if (mInstance == null) {
                    mInstance = new ViberatorContext(context);
                }
            }
        }
        return mInstance;
    }

    public void performBokehAdjust() {
        Log.d(TAG, "performBokehAdjust: ignore...");
    }

    public void performBurstCapture() {
        Log.d(TAG, "performBurstCapture: ignore...");
    }

    public void performEVChange() {
        Log.d(TAG, "performEVChange: ignore...");
    }

    public void performFocusValueLargeChangedInManual() {
        Log.d(TAG, "performFocusValueLargeChangedInManual: ignore...");
    }

    public void performFocusValueLightChangedInManual() {
        Log.d(TAG, "performFocusValueLightChangedInManual: ignore...");
    }

    public void performModeSwitch() {
        Log.d(TAG, "performModeSwitch: ignore...");
    }

    public void performSelectZoomLight() {
        Log.d(TAG, "performSelectZoomLight: ignore...");
    }

    public void performSelectZoomNormal() {
        Log.d(TAG, "performSelectZoomNormal: ignore...");
    }

    public void performSlideScaleNormal() {
        Log.d(TAG, "performSlideScaleNormal: ignore...");
    }

    public void performSnapClick() {
        Log.d(TAG, "performSnapClick:  ignore...");
    }

    public void setSnapClickVibratorEnable(boolean z) {
        Log.d(TAG, "setSnapClickVibratorEnable: ignore...");
    }
}
