package com.android.camera.effect.renders;

import android.graphics.Bitmap;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.BitmapTexture;

class AgeGenderAndMagicMirrorWaterMark extends WaterMark {
    private int mCenterX;
    private int mCenterY;
    private int mHeight;
    private BitmapTexture mImageTexture;
    private int mPaddingX;
    private int mPaddingY;
    private int mWidth;

    public AgeGenderAndMagicMirrorWaterMark(Bitmap bitmap, int i, int i2, int i3, int i4, int i5, float f2, float f3) {
        super(i, i2, i3);
        float min = (float) (Math.min(i, i2) / Math.min(i4, i5));
        this.mHeight = Math.max(i, i2);
        this.mWidth = Math.min(i, i2);
        this.mPaddingX = Math.round(f2 * min);
        this.mPaddingY = Math.round(f3 * min);
        this.mImageTexture = new BitmapTexture(bitmap);
        this.mImageTexture.setOpaque(false);
        calcCenterAxis();
    }

    private void calcCenterAxis() {
        this.mCenterX = this.mPaddingY + (getHeight() / 2);
        this.mCenterY = this.mPaddingX + (getWidth() / 2);
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
    public int getLeft() {
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
    public int getTop() {
        return super.getTop();
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getWidth() {
        return this.mWidth;
    }
}
