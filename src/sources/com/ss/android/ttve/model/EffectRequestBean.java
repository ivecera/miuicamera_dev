package com.ss.android.ttve.model;

public class EffectRequestBean {
    private int mRequestId;
    private String mResPath;
    private int mStickerId;
    private boolean mUseAmazing;
    private boolean mWithoutFace;

    public EffectRequestBean() {
        this("");
    }

    public EffectRequestBean(String str) {
        this(str, false);
    }

    public EffectRequestBean(String str, boolean z) {
        this(str, z, false);
    }

    public EffectRequestBean(String str, boolean z, boolean z2) {
        this.mResPath = str;
        this.mWithoutFace = z;
        this.mUseAmazing = z2;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public String getResPath() {
        return this.mResPath;
    }

    public int getStickerId() {
        return this.mStickerId;
    }

    public boolean isUseAmazing() {
        return this.mUseAmazing;
    }

    public boolean isWithoutFace() {
        return this.mWithoutFace;
    }

    public void setRequestId(int i) {
        this.mRequestId = i;
    }

    public void setResPath(String str) {
        this.mResPath = str;
    }

    public void setStickerId(int i) {
        this.mStickerId = i;
    }

    public void setUseAmazing(boolean z) {
        this.mUseAmazing = z;
    }

    public void setWithoutFace(boolean z) {
        this.mWithoutFace = z;
    }
}
