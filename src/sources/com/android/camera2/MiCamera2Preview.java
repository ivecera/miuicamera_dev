package com.android.camera2;

public abstract class MiCamera2Preview {
    protected int mBogusCameraId;
    protected int mCurrentModule;

    public MiCamera2Preview(int i, int i2) {
        this.mCurrentModule = i;
        this.mBogusCameraId = i2;
    }

    public boolean needForCapture() {
        int i = this.mCurrentModule;
        return i == 163 || i == 165 || i == 167 || i == 173 || i == 175;
    }

    public boolean needForFrontCamera() {
        return this.mBogusCameraId == 1;
    }

    public boolean needForManually() {
        return this.mCurrentModule == 167;
    }

    public boolean needForPortrait() {
        return this.mCurrentModule == 171;
    }

    public boolean needForProVideo() {
        return this.mCurrentModule == 180;
    }

    public boolean needForVideo() {
        int i = this.mCurrentModule;
        return i == 162 || i == 169 || i == 161 || i == 174 || i == 183 || i == 172 || i == 180;
    }
}
