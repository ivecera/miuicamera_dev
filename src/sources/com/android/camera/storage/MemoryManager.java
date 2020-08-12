package com.android.camera.storage;

import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.storage.Storage;
import com.mi.config.b;

public class MemoryManager implements Storage.StorageListener {
    private static final int DELAY_SECTION = 100;
    private static final float MAX_MEMORY_LIMIT_RATIO = 0.95f;
    public static final float MTK_SPEED_DOWN_RATIO = 0.66f;
    private static final long ONE_MB = 1048576;
    private static final float PHONE_SAVE_TASK_MEMORY_LIMIT_RATIO = 0.6f;
    private static final float SAVE_QUEUE_MEMORY_RATIO = 1.3f;
    private static final int SDCARD_SAVE_TASK_MEMORY_LIMIT = 62914560;
    private static final float SDCARD_SAVE_TASK_MEMORY_LIMIT_RATIO = 0.5f;
    private static final String TAG = "CameraMemoryManager";
    private long mMaxMemory;
    private int mMaxTotalMemory;
    private Runtime mRuntime = Runtime.getRuntime();
    private int mSaveTaskMemoryLimit;
    private int mSavedQueueMemoryLimit;
    private int mSaverMemoryUse;

    private long getBaseMemory() {
        int i = Util.sWindowWidth;
        if (i == 720) {
            return 20971520;
        }
        if (i == 1080) {
            return 41943040;
        }
        if (i != 1440) {
            return this.mRuntime.totalMemory() - this.mRuntime.freeMemory();
        }
        return 62914560;
    }

    private int getTotalUsedMemory() {
        long j = this.mRuntime.totalMemory();
        long freeMemory = this.mRuntime.freeMemory();
        long j2 = j - freeMemory;
        log("getLeftMemory: maxMemory=" + this.mMaxMemory + ", total=" + j + ", free=" + freeMemory + ", totalUsed=" + j2);
        return (int) j2;
    }

    private void initLimit() {
        long baseMemory = this.mMaxMemory - getBaseMemory();
        if (Storage.isUsePhoneStorage()) {
            this.mSaveTaskMemoryLimit = (int) (((float) baseMemory) * 0.6f);
        } else {
            this.mSaveTaskMemoryLimit = (int) (((float) baseMemory) * 0.5f);
            if (SDCARD_SAVE_TASK_MEMORY_LIMIT < this.mSaveTaskMemoryLimit) {
                this.mSaveTaskMemoryLimit = SDCARD_SAVE_TASK_MEMORY_LIMIT;
            }
        }
        this.mSavedQueueMemoryLimit = (int) (((float) this.mSaveTaskMemoryLimit) * SAVE_QUEUE_MEMORY_RATIO);
        Log.d(TAG, "initLimit: mSavedQueueMemoryLimit = " + this.mSavedQueueMemoryLimit);
    }

    private boolean isReachedMemoryLimit() {
        log("isReachedMemoryLimit: usedMemory=" + this.mSaverMemoryUse);
        return this.mSaverMemoryUse >= this.mSaveTaskMemoryLimit;
    }

    private void log(String str) {
        if (Util.sIsDumpLog) {
            Log.v(TAG, str);
        }
    }

    public void addUsedMemory(int i) {
        this.mSaverMemoryUse += i;
    }

    public int getBurstDelay() {
        int i = 3;
        if (isNeedSlowDown()) {
            int i2 = this.mSaverMemoryUse;
            int i3 = this.mSaveTaskMemoryLimit;
            if (i2 >= (i3 * 7) / 8) {
                i = 8;
            } else if (i2 >= (i3 * 5) / 6) {
                i = 5;
            } else if (i2 >= (i3 * 4) / 5) {
                i = 4;
            } else if (i2 < (i3 * 3) / 4) {
                i = 1;
            }
        } else {
            i = 0;
        }
        log("getBurstDelay: delayMultiple=" + i);
        return i * 100;
    }

    public void initMemory() {
        this.mMaxMemory = this.mRuntime.maxMemory();
        this.mMaxTotalMemory = (int) (((float) this.mMaxMemory) * MAX_MEMORY_LIMIT_RATIO);
        this.mSaverMemoryUse = 0;
        initLimit();
        Storage.setStorageListener(this);
        Log.d(TAG, "initMemory: maxMemory=" + this.mMaxMemory);
    }

    public boolean isNeedSlowDown() {
        boolean z = true;
        if (!b.isMTKPlatform() ? this.mSaverMemoryUse < this.mSaveTaskMemoryLimit / 2 : this.mSaverMemoryUse < (this.mSaveTaskMemoryLimit * 3) / 4) {
            z = false;
        }
        log("isNeedSlowDown: return " + z + " mSaverMemoryUse=" + this.mSaverMemoryUse + " mSaveTaskMemoryLimit=" + this.mSaveTaskMemoryLimit);
        return z;
    }

    public boolean isNeedStopCapture() {
        if (!isReachedMemoryLimit() && this.mMaxTotalMemory > getTotalUsedMemory() && Storage.getLeftSpace() > ((long) this.mSaverMemoryUse)) {
            return false;
        }
        Log.d(TAG, "isNeedStopCapture: needStop=" + true);
        return true;
    }

    public synchronized boolean isSaveQueueFull() {
        boolean z;
        z = this.mSaverMemoryUse >= this.mSavedQueueMemoryLimit;
        log("isSaveQueueFull = " + z + ", usedMemory=" + this.mSaverMemoryUse + ", limit = " + this.mSavedQueueMemoryLimit);
        return z;
    }

    @Override // com.android.camera.storage.Storage.StorageListener
    public void onStoragePathChanged() {
        initMemory();
    }

    public void reduceUsedMemory(int i) {
        this.mSaverMemoryUse -= i;
    }
}
