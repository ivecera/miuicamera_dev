package com.miui.extravideo.watermark.render;

public class YuvTextureJNI {
    static {
        System.loadLibrary("YuvWatermark");
    }

    public static native void merge(int i, int i2, int i3, int i4);

    public static native void setup(int i, int i2, int i3, int i4, int i5, int i6);

    public static native void texture(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);
}
