package com.android.camera.effect.draw_mode;

import com.android.gallery3d.ui.BasicTexture;

public final class DrawBlurTexAttribute extends DrawBasicTexAttribute {
    public DrawBlurTexAttribute() {
        ((DrawAttribute) this).mTarget = 10;
    }

    public DrawBlurTexAttribute(BasicTexture basicTexture) {
        init(basicTexture);
        ((DrawAttribute) this).mTarget = 10;
    }

    public DrawBlurTexAttribute(BasicTexture basicTexture, int i, int i2, int i3, int i4) {
        super(basicTexture, i, i2, i3, i4);
        ((DrawAttribute) this).mTarget = 10;
    }
}
