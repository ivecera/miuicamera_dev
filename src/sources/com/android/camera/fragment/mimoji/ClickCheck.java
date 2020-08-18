package com.android.camera.fragment.mimoji;

public class ClickCheck {
    private static final long CLICK_TIME = 100;
    private volatile boolean isForceDisabled = false;
    private long mLastClickTime = 0;

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
