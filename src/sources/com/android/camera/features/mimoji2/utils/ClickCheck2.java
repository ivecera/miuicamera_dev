package com.android.camera.features.mimoji2.utils;

public class ClickCheck2 {
    private static final long CLICK_TIME = 100;
    private static ClickCheck2 instance;
    private volatile boolean isForceDisabled = false;
    private long mLastClickTime = 0;

    public static ClickCheck2 getInstance() {
        if (instance == null) {
            instance = new ClickCheck2();
        }
        return instance;
    }

    public boolean checkClickable() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastClickTime < CLICK_TIME) {
            return false;
        }
        this.mLastClickTime = currentTimeMillis;
        return !this.isForceDisabled;
    }

    public void setForceDisabled(boolean z) {
        this.isForceDisabled = z;
    }
}
