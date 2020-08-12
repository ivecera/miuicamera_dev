package com.android.camera.effect.draw_mode;

import com.android.gallery3d.ui.GLPaint;

public class DrawLineAttribute extends DrawAttribute {
    public GLPaint mGLPaint;
    public float mX1;
    public float mX2;
    public float mY1;
    public float mY2;

    public DrawLineAttribute(float f2, float f3, float f4, float f5, GLPaint gLPaint) {
        this.mX1 = f2;
        this.mY1 = f3;
        this.mX2 = f4;
        this.mY2 = f5;
        this.mGLPaint = gLPaint;
        ((DrawAttribute) this).mTarget = 0;
    }
}
