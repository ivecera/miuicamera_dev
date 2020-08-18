package com.android.camera.effect.renders;

import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.StringTexture;

public class NewStyleTextWaterMark extends WaterMark {
    private static final float RATIO = 0.87f;
    private static final String TAG = "NewStyleTextWaterMark";
    public static final int TEXT_COLOR = -1;
    public static final float TEXT_PIXEL_SIZE = 30.079576f;
    private int mCenterX;
    private int mCenterY;
    private int mCharMargin;
    private boolean mIsCinematicAspectRatio;
    private int mPadding;
    private int mPaddingX;
    private int mPaddingY;
    private int mWaterHeight;
    private String mWaterText;
    private BasicTexture mWaterTexture;
    private int mWaterWidth;

    public NewStyleTextWaterMark(String str, int i, int i2, int i3, boolean z) {
        super(i, i2, i3);
        this.mIsCinematicAspectRatio = z;
        float min = ((float) Math.min(i, i2)) / 1080.0f;
        this.mPadding = (int) Math.round(((double) min) * 43.687002653d);
        int i4 = this.mPadding;
        this.mPaddingX = i4 & -2;
        this.mPaddingY = i4 & -2;
        float f2 = 30.079576f;
        if (this.mIsCinematicAspectRatio) {
            int round = Math.round(((float) Util.getCinematicAspectRatioMargin()) * min);
            if (i3 == 90 || i3 == 270) {
                f2 = (float) (((double) 30.079576f) * 0.95d);
                float f3 = 15.0f * min;
                this.mPaddingX = (int) (((float) this.mPaddingX) - f3);
                this.mPaddingY = (int) (((float) this.mPaddingY) - f3);
                this.mPaddingX += round;
            } else {
                this.mPaddingY += round;
            }
        }
        this.mWaterText = str;
        this.mWaterTexture = StringTexture.newInstance(this.mWaterText, f2 * min, -1, 2);
        this.mWaterWidth = this.mWaterTexture.getWidth();
        this.mWaterHeight = this.mWaterTexture.getHeight();
        this.mCharMargin = (int) ((((float) this.mWaterHeight) * 0.13f) / 2.0f);
        if (i3 == 90 || i3 == 270) {
            this.mPaddingX = (this.mPaddingX - this.mCharMargin) & -2;
        } else {
            this.mPaddingY = (this.mPaddingY - this.mCharMargin) & -2;
        }
        calcCenterAxis();
        if (Util.sIsDumpLog) {
            print();
        }
    }

    private void calcCenterAxis() {
        int i = ((WaterMark) this).mOrientation;
        if (i == 0) {
            this.mCenterX = (((WaterMark) this).mPictureWidth - this.mPaddingX) - (this.mWaterWidth / 2);
            this.mCenterY = (((WaterMark) this).mPictureHeight - this.mPaddingY) - (this.mWaterHeight / 2);
        } else if (i == 90) {
            this.mCenterX = (((WaterMark) this).mPictureWidth - this.mPaddingY) - (this.mWaterHeight / 2);
            this.mCenterY = this.mPaddingX + (this.mWaterWidth / 2);
        } else if (i == 180) {
            this.mCenterX = this.mPaddingX + (this.mWaterWidth / 2);
            this.mCenterY = this.mPaddingY + (this.mWaterHeight / 2);
        } else if (i == 270) {
            this.mCenterX = this.mPaddingY + (this.mWaterHeight / 2);
            this.mCenterY = (((WaterMark) this).mPictureHeight - this.mPaddingX) - (this.mWaterWidth / 2);
        }
    }

    private void print() {
        String str = TAG;
        Log.v(str, "WaterMark pictureWidth=" + ((WaterMark) this).mPictureWidth + " pictureHeight =" + ((WaterMark) this).mPictureHeight + " waterText=" + this.mWaterText + " centerX=" + this.mCenterX + " centerY=" + this.mCenterY + " waterWidth=" + this.mWaterWidth + " waterHeight=" + this.mWaterHeight + " padding=" + this.mPadding);
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
        return this.mWaterHeight;
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
        return this.mWaterTexture;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public /* bridge */ /* synthetic */ int getTop() {
        return super.getTop();
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getWidth() {
        return this.mWaterWidth;
    }
}
