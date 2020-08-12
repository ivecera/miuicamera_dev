package com.android.camera.effect.framework.utils;

import com.android.camera.log.Log;

public class CounterUtil {
    private static String TAG = "CounterUtil";
    private long mTs;

    public void reset() {
        this.mTs = System.currentTimeMillis();
    }

    public void reset(String str) {
        reset();
        Log.i(TAG, str);
    }

    public void tick(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        String str2 = TAG;
        Log.i(str2, str + ":" + (currentTimeMillis - this.mTs));
        this.mTs = currentTimeMillis;
    }
}
