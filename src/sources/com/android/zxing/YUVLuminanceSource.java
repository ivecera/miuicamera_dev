package com.android.zxing;

import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import com.google.zxing.LuminanceSource;

final class YUVLuminanceSource extends LuminanceSource {
    private final int mDataHeight;
    private final int mDataWidth;
    private final int mLeft;
    private final int mTop;
    private final byte[] mYUVData;

    public YUVLuminanceSource(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6) {
        super(i5, i6);
        if (i5 + i3 > i || i6 + i4 > i2) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        this.mYUVData = bArr;
        this.mDataWidth = i;
        this.mDataHeight = i2;
        this.mLeft = i3;
        this.mTop = i4;
    }

    public int getDataHeight() {
        return this.mDataHeight;
    }

    public int getDataWidth() {
        return this.mDataWidth;
    }

    @Override // com.google.zxing.LuminanceSource
    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        if (width == this.mDataWidth && height == this.mDataHeight) {
            return this.mYUVData;
        }
        int i = width * height;
        byte[] bArr = new byte[i];
        int i2 = this.mTop;
        int i3 = this.mDataWidth;
        int i4 = (i2 * i3) + this.mLeft;
        if (width == i3) {
            System.arraycopy(this.mYUVData, i4, bArr, 0, i);
            return bArr;
        }
        byte[] bArr2 = this.mYUVData;
        for (int i5 = 0; i5 < height; i5++) {
            System.arraycopy(bArr2, i4, bArr, i5 * width, width);
            i4 += this.mDataWidth;
        }
        return bArr;
    }

    @Override // com.google.zxing.LuminanceSource
    public byte[] getRow(int i, byte[] bArr) {
        if (i < 0 || i >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + i);
        }
        int width = getWidth();
        if (bArr == null || bArr.length < width) {
            bArr = new byte[width];
        }
        System.arraycopy(this.mYUVData, ((i + this.mTop) * this.mDataWidth) + this.mLeft, bArr, 0, width);
        return bArr;
    }

    @Override // com.google.zxing.LuminanceSource
    public boolean isCropSupported() {
        return true;
    }

    public Bitmap renderCroppedGreyscaleBitmap() {
        int width = getWidth();
        int height = getHeight();
        int[] iArr = new int[(width * height)];
        byte[] bArr = this.mYUVData;
        int i = (this.mTop * this.mDataWidth) + this.mLeft;
        for (int i2 = 0; i2 < height; i2++) {
            int i3 = i2 * width;
            for (int i4 = 0; i4 < width; i4++) {
                iArr[i3 + i4] = ((bArr[i + i4] & 255) * 65793) | ViewCompat.MEASURED_STATE_MASK;
            }
            i += this.mDataWidth;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }
}
