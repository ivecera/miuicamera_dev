package com.android.gallery3d.ui;

public class GLPaint {
    private int mColor = 0;
    private float mLineWidth = 1.0f;

    public GLPaint(float f2, int i) {
        this.mLineWidth = f2;
        this.mColor = i;
    }

    public int getColor() {
        return this.mColor;
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public void setColor(int i) {
        this.mColor = i;
    }

    public void setLineWidth(float f2) {
        Utils.assertTrue(f2 >= 0.0f);
        this.mLineWidth = f2;
    }
}
