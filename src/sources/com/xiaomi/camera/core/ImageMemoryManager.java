package com.xiaomi.camera.core;

import android.media.Image;
import android.util.SparseIntArray;
import com.android.camera.log.Log;
import java.util.HashMap;
import java.util.Map;

public class ImageMemoryManager {
    private static final String TAG = "ImageMemoryManager";
    private static final int TOTAL_MAX_MEMORY_USAGE = 1073741824;
    private static int mUsedMemory;
    private SparseIntArray mHoldImageNumArray = new SparseIntArray();
    private Map<Image, ImageInfo> mImagesMap = new HashMap();
    private int mMaxHoldImageNumber;
    private final Object mObjLock = new Object();

    class ImageInfo {
        int owner;
        int size;

        ImageInfo(int i, int i2) {
            this.owner = i;
            this.size = i2;
        }
    }

    public ImageMemoryManager(int i) {
        this.mMaxHoldImageNumber = i;
    }

    private int getImageUsedMemory(Image image) {
        int remaining = image.getPlanes()[0].getBuffer().remaining();
        int format = image.getFormat();
        if (format == 35) {
            return (int) (((double) remaining) * 1.5d);
        }
        if (format != 256) {
            return 0;
        }
        return remaining;
    }

    private int getMaxHoldImageNumber() {
        int i = 0;
        for (int i2 = 0; i2 < this.mHoldImageNumArray.size(); i2++) {
            if (this.mHoldImageNumArray.valueAt(i2) > i) {
                i = this.mHoldImageNumArray.valueAt(i2);
            }
        }
        return i;
    }

    public static boolean isMemoryFull() {
        return mUsedMemory > TOTAL_MAX_MEMORY_USAGE;
    }

    public void holdAnImage(int i, Image image) {
        synchronized (this.mObjLock) {
            this.mHoldImageNumArray.put(i, this.mHoldImageNumArray.get(i) + 1);
            int imageUsedMemory = getImageUsedMemory(image);
            this.mImagesMap.put(image, new ImageInfo(i, imageUsedMemory));
            mUsedMemory += imageUsedMemory;
            Log.d(TAG, "holdAnImage: " + image + ", needCloseImageNum=" + getMaxHoldImageNumber());
        }
    }

    public boolean needWaitImageClose() {
        boolean z;
        synchronized (this.mObjLock) {
            z = true;
            if (getMaxHoldImageNumber() < this.mMaxHoldImageNumber - 1) {
                z = false;
            }
        }
        return z;
    }

    public void releaseAnImage(Image image) {
        synchronized (this.mObjLock) {
            if (this.mImagesMap.containsKey(image)) {
                ImageInfo imageInfo = this.mImagesMap.get(image);
                int i = this.mHoldImageNumArray.get(imageInfo.owner);
                if (i > 0) {
                    this.mHoldImageNumArray.put(imageInfo.owner, i - 1);
                }
                mUsedMemory -= imageInfo.size;
                this.mImagesMap.remove(image);
                this.mObjLock.notifyAll();
                Log.d(TAG, "releaseAnImage: " + image + ", needCloseImageNum=" + getMaxHoldImageNumber());
            } else {
                Log.d(TAG, "releaseAnImage: not hold image " + image);
            }
        }
    }

    public void waitImageCloseIfNeeded() {
        synchronized (this.mObjLock) {
            while (getMaxHoldImageNumber() >= this.mMaxHoldImageNumber - 1) {
                try {
                    Log.d(TAG, "waitImageCloseIfNeeded: wait E");
                    this.mObjLock.wait();
                    Log.d(TAG, "waitImageCloseIfNeeded: wait X");
                } catch (InterruptedException e2) {
                    Log.w(TAG, "waitImageCloseIfNeeded: failed!", e2);
                }
            }
        }
    }
}
