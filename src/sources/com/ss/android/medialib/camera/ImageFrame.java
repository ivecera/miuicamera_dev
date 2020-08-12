package com.ss.android.medialib.camera;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.Image;

public class ImageFrame {
    public static final int BMP = 2;
    public static final int JPEG = 1;
    public static final int NV21 = -3;
    public static final int PNG = 0;
    public static final int UNKNOW = -1;
    public static final int YUV_888 = -2;
    Bitmap bitmap;
    int format;
    int height;
    byte[] mBuf;
    Image.Plane[] mPlans;
    int width;

    public ImageFrame(Bitmap bitmap2, int i) {
        this.bitmap = bitmap2;
        this.format = i;
    }

    public ImageFrame(byte[] bArr, int i, int i2, int i3) {
        this.mBuf = bArr;
        this.format = i;
        this.width = i2;
        this.height = i3;
    }

    @TargetApi(19)
    public ImageFrame(Image.Plane[] planeArr, int i, int i2, int i3) {
        this.mPlans = planeArr;
        this.format = i;
        this.width = i2;
        this.height = i3;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public byte[] getBuf() {
        return this.mBuf;
    }

    public int getFormat() {
        return this.format;
    }

    public int getHeight() {
        return this.height;
    }

    public Image.Plane[] getPlans() {
        return this.mPlans;
    }

    public int getWidth() {
        return this.width;
    }

    public void setBuf(byte[] bArr) {
        this.mBuf = bArr;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public void setWidth(int i) {
        this.width = i;
    }
}
