package com.ss.android.vesdk.graphics;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.os.Build;

public class TESurfaceTexture extends SurfaceTexture {
    private boolean mAbandoned;

    public TESurfaceTexture(int i) {
        super(i);
    }

    @TargetApi(19)
    public TESurfaceTexture(int i, boolean z) {
        super(i, z);
    }

    @TargetApi(26)
    public TESurfaceTexture(boolean z) {
        super(z);
    }

    public synchronized void attachToGLContext(int i) {
        if (!isReleased()) {
            super.attachToGLContext(i);
        }
    }

    public synchronized void detachFromGLContext() {
        if (!isReleased()) {
            super.detachFromGLContext();
        }
    }

    public synchronized long getTimestamp() {
        if (isReleased()) {
            return -1;
        }
        return super.getTimestamp();
    }

    public synchronized void getTransformMatrix(float[] fArr) {
        if (!isReleased()) {
            super.getTransformMatrix(fArr);
        }
    }

    public boolean isReleased() {
        return Build.VERSION.SDK_INT >= 26 ? super.isReleased() : this.mAbandoned;
    }

    public synchronized void release() {
        if (!isReleased()) {
            super.release();
            this.mAbandoned = true;
        }
    }

    public synchronized void releaseTexImage() {
        if (!isReleased()) {
            super.releaseTexImage();
        }
    }

    public synchronized void setDefaultBufferSize(int i, int i2) {
        if (!isReleased()) {
            super.setDefaultBufferSize(i, i2);
        }
    }

    public synchronized void updateTexImage() {
        if (!isReleased()) {
            super.updateTexImage();
        }
    }
}
