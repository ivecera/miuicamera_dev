package com.android.camera.scene;

import android.util.SparseArray;

public class MiScene {
    private float lastResult;
    private boolean mEnable;
    public int type;
    public SparseArray<Integer> valueArray = new SparseArray<>();

    public MiScene() {
        this.valueArray.put(0, -1111111);
    }

    public static MiScene create() {
        return new MiScene();
    }

    public boolean isChange(float f2) {
        boolean z = this.lastResult != f2;
        this.lastResult = f2;
        return z;
    }

    public boolean isEnable() {
        return this.mEnable;
    }

    public void setEnable(boolean z) {
        this.mEnable = z;
    }
}
