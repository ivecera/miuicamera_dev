package com.android.camera.effect;

import com.android.camera.log.Log;
import java.nio.ByteBuffer;

public class MiYuvImage {
    private static final String TAG = "MiYuvImage";
    public byte[] mData;
    public int mFormat;
    public int mHeight;
    public int mWidth;

    public MiYuvImage(byte[] bArr, int i, int i2, int i3) {
        this.mData = bArr;
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
    }

    public ByteBuffer getUVBuffer() {
        byte[] bArr = this.mData;
        if (bArr == null) {
            Log.e(TAG, "data is null when getUVBuffer");
            return null;
        } else if (this.mFormat == 35) {
            int i = this.mWidth;
            int i2 = this.mHeight;
            byte[] bArr2 = new byte[((i * i2) / 2)];
            System.arraycopy(bArr, i * i2, bArr2, 0, (i * i2) / 2);
            return ByteBuffer.wrap(bArr2);
        } else {
            Log.d(TAG, "Not supprot this format");
            return null;
        }
    }

    public ByteBuffer getVUBuffer() {
        byte[] bArr = this.mData;
        if (bArr == null) {
            Log.e(TAG, "data is null when getUVBuffer");
            return null;
        } else if (this.mFormat == 35) {
            int i = this.mWidth;
            int i2 = this.mHeight;
            byte[] bArr2 = new byte[((i * i2) / 2)];
            System.arraycopy(bArr, (i * i2) + 1, bArr2, 0, ((i * i2) / 2) - 1);
            bArr2[bArr2.length - 1] = bArr2[bArr2.length - 3];
            return ByteBuffer.wrap(bArr2);
        } else {
            Log.d(TAG, "Not supprot this format");
            return null;
        }
    }

    public ByteBuffer getYBuffer() {
        byte[] bArr = this.mData;
        if (bArr == null) {
            Log.e(TAG, "data is null when getYBuffer");
            return null;
        } else if (this.mFormat == 35) {
            int i = this.mWidth;
            int i2 = this.mHeight;
            byte[] bArr2 = new byte[(i * i2)];
            System.arraycopy(bArr, 0, bArr2, 0, i * i2);
            return ByteBuffer.wrap(bArr2);
        } else {
            Log.d(TAG, "Not supprot this format");
            return null;
        }
    }

    public ByteBuffer getYuvBuffer() {
        byte[] bArr = this.mData;
        if (bArr != null) {
            return ByteBuffer.wrap(bArr);
        }
        Log.e(TAG, "data is null when getYuvBuffer");
        return null;
    }
}
