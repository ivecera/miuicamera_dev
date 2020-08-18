package com.android.camera.effect.draw_mode;

import com.android.gallery3d.ui.BasicTexture;

public class DrawMixedAttribute extends DrawAttribute {
    public BasicTexture mBasicTexture;
    public float mHeight;
    public float mRatio;
    public int mToColor;
    public float mWidth;
    public float mX;
    public float mY;

    public DrawMixedAttribute(BasicTexture basicTexture, int i, float f2, float f3, float f4, float f5) {
        this.mX = f2;
        this.mY = f3;
        this.mWidth = f4;
        this.mHeight = f5;
        this.mBasicTexture = basicTexture;
        this.mToColor = i;
        ((DrawAttribute) this).mTarget = 3;
    }
}
