package com.android.camera.effect.draw_mode;

import com.android.gallery3d.ui.BasicTexture;

public class DrawMeshAttribute extends DrawAttribute {
    public BasicTexture mBasicTexture;
    public float mHeight;
    public int mIndexBuffer;
    public int mIndexCount;
    public int mUVBuffer;
    public float mWidth;
    public float mX;
    public int mXYBuffer;
    public float mY;

    public DrawMeshAttribute(BasicTexture basicTexture, float f2, float f3, int i, int i2, int i3, int i4) {
        this.mX = f2;
        this.mY = f3;
        this.mXYBuffer = i;
        this.mUVBuffer = i2;
        this.mIndexBuffer = i3;
        this.mBasicTexture = basicTexture;
        this.mIndexCount = i4;
        ((DrawAttribute) this).mTarget = 2;
    }
}
