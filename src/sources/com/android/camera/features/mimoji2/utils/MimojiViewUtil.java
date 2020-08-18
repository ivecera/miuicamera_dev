package com.android.camera.features.mimoji2.utils;

import android.util.Log;
import android.view.View;

public class MimojiViewUtil {
    public static final String TAG = MimojiViewUtil.class.getClass().getSimpleName();
    public static boolean isDebug = false;

    public static boolean getViewIsVisible(View view) {
        if (view == null) {
            return false;
        }
        boolean z = view.getVisibility() == 0;
        boolean z2 = view.getAlpha() == 1.0f;
        if (isDebug) {
            String str = TAG;
            Log.d(str, "mimoji boolean getViewIsVisible[view] " + view.getId() + " : " + z + "  " + z2);
        }
        return z && z2;
    }

    public static boolean setViewVisible(View view, boolean z) {
        int i = 0;
        if (view == null) {
            return false;
        }
        if (isDebug) {
            String str = TAG;
            com.android.camera.log.Log.d(str, "mimoji void setViewVisible[view, isVisible]" + view.getId() + view.getId() + " : " + z);
        }
        if (!z) {
            i = 8;
        }
        view.setVisibility(i);
        if (!z) {
            return true;
        }
        view.setAlpha(1.0f);
        return true;
    }
}
