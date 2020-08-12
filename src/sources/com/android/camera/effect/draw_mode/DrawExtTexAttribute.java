package com.android.camera.effect.draw_mode;

import com.android.gallery3d.ui.ExtTexture;
import java.util.Arrays;

public class DrawExtTexAttribute extends DrawAttribute {
    public boolean mEffectPopup = false;
    public ExtTexture mExtTexture;
    public int mHeight;
    public float[] mTextureTransform;
    public int mWidth;
    public int mX;
    public int mY;

    public DrawExtTexAttribute() {
        ((DrawAttribute) this).mTarget = 8;
    }

    public DrawExtTexAttribute(ExtTexture extTexture, float[] fArr, int i, int i2, int i3, int i4) {
        this.mX = i;
        this.mY = i2;
        this.mWidth = i3;
        this.mHeight = i4;
        this.mExtTexture = extTexture;
        this.mTextureTransform = fArr;
        ((DrawAttribute) this).mTarget = 8;
    }

    public DrawExtTexAttribute(boolean z) {
        this.mEffectPopup = z;
        ((DrawAttribute) this).mTarget = 8;
    }

    public DrawExtTexAttribute init(ExtTexture extTexture, float[] fArr, int i, int i2, int i3, int i4) {
        this.mX = i;
        this.mY = i2;
        this.mWidth = i3;
        this.mHeight = i4;
        this.mExtTexture = extTexture;
        this.mTextureTransform = fArr;
        return this;
    }

    public String toString() {
        return "DrawExtTexAttribute{mX=" + this.mX + ", mY=" + this.mY + ", mWidth=" + this.mWidth + ", mHeight=" + this.mHeight + ", mTextureTransform=" + Arrays.toString(this.mTextureTransform) + ", mExtTexture=" + this.mExtTexture + ", mEffectPopup=" + this.mEffectPopup + '}';
    }
}
