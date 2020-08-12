package com.ss.android.ttve.model;

public class EffectBean {
    private String mResPath;
    private int mStickerId;
    private boolean mUseAmazing;
    private boolean mWithoutFace;

    public EffectBean() {
        this("");
    }

    public EffectBean(String str) {
        this(str, false);
    }

    public EffectBean(String str, boolean z) {
        this.mResPath = str;
        this.mWithoutFace = z;
    }

    public String getResPath() {
        return this.mResPath;
    }

    public int getStickerId() {
        return this.mStickerId;
    }

    public boolean isWithoutFace() {
        return this.mWithoutFace;
    }

    public void setResPath(String str) {
        this.mResPath = str;
    }

    public void setStickerId(int i) {
        this.mStickerId = i;
    }

    public void setWithoutFace(boolean z) {
        this.mWithoutFace = z;
    }
}
