package com.android.camera.effect.draw_mode;

public class FillRectAttribute extends DrawAttribute {
    public int mColor;
    public float mHeight;
    public float mWidth;
    public float mX;
    public float mY;

    public FillRectAttribute(float f2, float f3, float f4, float f5, int i) {
        this.mX = f2;
        this.mY = f3;
        this.mWidth = f4;
        this.mHeight = f5;
        this.mColor = i;
        ((DrawAttribute) this).mTarget = 4;
    }
}
