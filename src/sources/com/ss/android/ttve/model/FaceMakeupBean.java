package com.ss.android.ttve.model;

public class FaceMakeupBean {
    private float mLipStickIntensity;
    private float mNasolabialIntensity;
    private float mPouchIntensity;
    private String mResPath;
    private float mblusherIntensity;

    public FaceMakeupBean() {
        this("", 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public FaceMakeupBean(String str, float f2, float f3) {
        this.mResPath = str;
        this.mLipStickIntensity = f2;
        this.mblusherIntensity = f3;
    }

    public FaceMakeupBean(String str, float f2, float f3, float f4, float f5) {
        this(str, f2, f3);
        this.mNasolabialIntensity = f4;
        this.mPouchIntensity = f5;
    }

    public float getBlusherIntensity() {
        return this.mblusherIntensity;
    }

    public float getLipStickIntensity() {
        return this.mLipStickIntensity;
    }

    public float getNasolabialIntensity() {
        return this.mNasolabialIntensity;
    }

    public float getPouchIntensity() {
        return this.mPouchIntensity;
    }

    public String getResPath() {
        return this.mResPath;
    }

    public void setBlusherIntensity(float f2) {
        this.mblusherIntensity = f2;
    }

    public void setLipStickIntensity(float f2) {
        this.mLipStickIntensity = f2;
    }

    public void setNasolabialIntensity(float f2) {
        this.mNasolabialIntensity = f2;
    }

    public void setPouchIntensity(float f2) {
        this.mPouchIntensity = f2;
    }

    public void setResPath(String str) {
        this.mResPath = str;
    }
}
