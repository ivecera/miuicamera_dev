package com.android.camera.features.mimoji2.widget.autoselectview;

import java.io.Serializable;

public class SelectItemBean implements Serializable {
    private static final long serialVersionUID = 1;
    private int alpha;
    private float curLength;
    private float curTotalLength;
    private String text;

    public int getAlpha() {
        return this.alpha;
    }

    public float getCurLength() {
        return this.curLength;
    }

    public float getCurTotalLength() {
        return this.curTotalLength;
    }

    public String getText() {
        return this.text;
    }

    public void setAlpha(int i) {
        if (i <= 1 && i >= 0) {
            this.alpha = i;
        }
    }

    public void setCurLength(float f2) {
        this.curLength = f2;
    }

    public void setCurTotalLength(float f2) {
        this.curTotalLength = f2;
    }

    public void setText(String str) {
        this.text = str;
    }
}
