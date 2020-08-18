package com.android.zxing;

import android.media.Image;
import com.android.camera.log.Log;
import com.xiaomi.stat.d;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class PreviewImage {
    public static final int PREVIEW_STATUS_NORMAL = 2;
    public static final int PREVIEW_STATUS_START = 1;
    public static final int PREVIEW_STATUS_STOP = 3;
    public static final int PREVIEW_STATUS_UNKNOWN = 0;
    private static final String TAG = "PreviewImage";
    private int mCameraId;
    private byte[] mData;
    private int mFormat;
    private int mHeight;
    private int mOrientation;
    private int mPreviewStatus = 0;
    private long mTimestamp;
    private int mWidth;

    public PreviewImage(int i, int i2) {
        this.mPreviewStatus = i;
        this.mCameraId = i2;
    }

    public PreviewImage(Image image, int i) {
        long currentTimeMillis = System.currentTimeMillis();
        convertYUV420ToNV21(image);
        this.mOrientation = i;
        this.mPreviewStatus = 2;
        Log.d(TAG, "PreviewDecodeManager convertYUV420ToNV21: cost = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
    }

    private void convertYUV420ToNV21(Image image) {
        if (image != null) {
            try {
                if (image.getFormat() == 35) {
                    this.mTimestamp = image.getTimestamp();
                    this.mWidth = image.getWidth();
                    this.mHeight = image.getHeight();
                    this.mFormat = image.getFormat();
                    int i = 0;
                    int rowStride = image.getPlanes()[0].getRowStride();
                    int rowStride2 = image.getPlanes()[2].getRowStride();
                    ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                    ByteBuffer buffer2 = image.getPlanes()[2].getBuffer();
                    int limit = buffer.limit();
                    int limit2 = buffer2.limit();
                    Log.v(TAG, "convertYUV420888ToNV21: size = " + this.mWidth + "x" + this.mHeight + ", yStride = " + rowStride + ", uvStride = " + rowStride2);
                    this.mData = new byte[Math.max(((this.mWidth * this.mHeight) * 3) / 2, limit + limit2)];
                    buffer.get(this.mData, 0, limit);
                    buffer2.get(this.mData, limit, limit2);
                    byte[] bArr = new byte[(((this.mWidth * this.mHeight) * 3) / 2)];
                    int i2 = 0;
                    int i3 = 0;
                    int i4 = 0;
                    while (i2 < this.mHeight) {
                        System.arraycopy(this.mData, i3, bArr, i4, this.mWidth);
                        i3 = i2 == this.mHeight + -1 ? i3 + this.mWidth : i3 + rowStride;
                        i4 += this.mWidth;
                        i2++;
                    }
                    while (true) {
                        int i5 = this.mHeight;
                        if (i < i5 / 2) {
                            if (i == (i5 / 2) - 1) {
                                System.arraycopy(this.mData, i3, bArr, i4, this.mWidth - 1);
                            } else {
                                System.arraycopy(this.mData, i3, bArr, i4, this.mWidth);
                            }
                            i3 += rowStride2;
                            i4 += this.mWidth;
                            i++;
                        } else {
                            this.mData = bArr;
                            return;
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    public int getCameraId() {
        return this.mCameraId;
    }

    public byte[] getData() {
        return this.mData;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getPreviewStatus() {
        return this.mPreviewStatus;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public String toString() {
        return "PreviewImage{mData=" + Arrays.toString(this.mData) + ", mTimestamp=" + this.mTimestamp + ", mWidth=" + this.mWidth + ", mHeight=" + this.mHeight + ", mFormat=" + this.mFormat + '}';
    }
}
