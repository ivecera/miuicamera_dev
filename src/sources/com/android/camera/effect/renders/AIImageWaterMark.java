package com.android.camera.effect.renders;

import android.graphics.Bitmap;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.BitmapTexture;

public class AIImageWaterMark extends WaterMark {
    private int mCenterX = 0;
    private int mCenterY = 0;
    private int mHeight = 0;
    private BitmapTexture mImageTexture;
    private int mPaddingX = 0;
    private int mPaddingY = 0;
    private int[] mRange = null;
    private int mWidth = 0;

    public AIImageWaterMark(Bitmap bitmap, int i, int i2, int i3, int[] iArr, float f2) {
        super(i, i2, i3);
        this.mRange = iArr;
        this.mWidth = ((int) Math.ceil((double) (((float) bitmap.getWidth()) * f2))) & -2;
        this.mHeight = ((int) Math.ceil((double) (((float) bitmap.getHeight()) * f2))) & -2;
        this.mImageTexture = new BitmapTexture(bitmap);
        this.mImageTexture.setOpaque(false);
        calcCenterAxis();
    }

    private void calcCenterAxis() {
        int[] iArr = this.mRange;
        this.mCenterX = iArr[0] + (iArr[2] / 2);
        this.mCenterY = iArr[1] + (iArr[3] / 2);
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getCenterX() {
        return this.mCenterX;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getCenterY() {
        return this.mCenterY;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getHeight() {
        return this.mHeight;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public /* bridge */ /* synthetic */ int getLeft() {
        return super.getLeft();
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getPaddingX() {
        return this.mPaddingX;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getPaddingY() {
        return this.mPaddingY;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public BasicTexture getTexture() {
        return this.mImageTexture;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public /* bridge */ /* synthetic */ int getTop() {
        return super.getTop();
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getWidth() {
        return this.mWidth;
    }
}
