package com.android.camera.effect.draw_mode;

import com.android.gallery3d.ui.GLPaint;

public class DrawRectAttribute extends DrawAttribute {
    public GLPaint mGLPaint;
    public float mHeight;
    public float mWidth;
    public float mX;
    public float mY;

    public DrawRectAttribute() {
    }

    public DrawRectAttribute(float f2, float f3, float f4, float f5, GLPaint gLPaint) {
        this.mX = f2;
        this.mY = f3;
        this.mWidth = f4;
        this.mHeight = f5;
        this.mGLPaint = gLPaint;
        ((DrawAttribute) this).mTarget = 1;
    }

    public DrawRectAttribute init(float f2, float f3, float f4, float f5, GLPaint gLPaint) {
        this.mX = f2;
        this.mY = f3;
        this.mWidth = f4;
        this.mHeight = f5;
        this.mGLPaint = gLPaint;
        ((DrawAttribute) this).mTarget = 1;
        return this;
    }
}
