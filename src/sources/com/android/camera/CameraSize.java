package com.android.camera;

import android.support.annotation.NonNull;
import android.util.Size;

public class CameraSize implements Comparable<CameraSize> {
    public int height;
    public int width;

    public CameraSize() {
    }

    public CameraSize(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public CameraSize(Size size) {
        this.width = size.getWidth();
        this.height = size.getHeight();
    }

    public static CameraSize copyFrom(Size size) {
        return new CameraSize(size.getWidth(), size.getHeight());
    }

    public int area() {
        if (isEmpty()) {
            return 0;
        }
        return this.height * this.width;
    }

    public int compareTo(@NonNull CameraSize cameraSize) {
        return (this.width * this.height) - (cameraSize.width * cameraSize.height);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CameraSize)) {
            return false;
        }
        CameraSize cameraSize = (CameraSize) obj;
        return this.width == cameraSize.width && this.height == cameraSize.height;
    }

    public int getHeight() {
        return this.height;
    }

    public float getRatio() {
        return ((float) this.width) / ((float) this.height);
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        int i = this.height;
        int i2 = this.width;
        return ((i2 >>> 16) | (i2 << 16)) ^ i;
    }

    public boolean isEmpty() {
        return this.width * this.height <= 0;
    }

    public CameraSize parseSize(CameraSize cameraSize) {
        this.width = cameraSize.width;
        this.height = cameraSize.height;
        return this;
    }

    public Size toSizeObject() {
        return new Size(this.width, this.height);
    }

    public String toString() {
        return this.width + "x" + this.height;
    }
}
