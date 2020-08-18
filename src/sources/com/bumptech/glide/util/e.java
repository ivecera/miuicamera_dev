package com.bumptech.glide.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.SystemClock;

/* compiled from: LogTime */
public final class e {
    private static final double ju;

    static {
        double d2 = 1.0d;
        if (Build.VERSION.SDK_INT >= 17) {
            d2 = 1.0d / Math.pow(10.0d, 6.0d);
        }
        ju = d2;
    }

    private e() {
    }

    @TargetApi(17)
    public static long Jk() {
        return Build.VERSION.SDK_INT >= 17 ? SystemClock.elapsedRealtimeNanos() : SystemClock.uptimeMillis();
    }

    public static double e(long j) {
        return ((double) (Jk() - j)) * ju;
    }
}
