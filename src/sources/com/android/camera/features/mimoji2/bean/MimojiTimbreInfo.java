package com.android.camera.features.mimoji2.bean;

public class MimojiTimbreInfo {
    public static final int TIMBRE_BABY = 4;
    public static final int TIMBRE_GENTLEMEN = 1;
    public static final int TIMBRE_GIRL = 3;
    public static final int TIMBRE_LADY = 2;
    public static final int TIMBRE_ROBOT = 5;
    public static final int[] timbreTypes = {1, 2, 3, 4, 5};
    private boolean mIsSelected;
    private int mResourceId;
    private int mTimbreId;

    public MimojiTimbreInfo() {
    }

    public MimojiTimbreInfo(int i, int i2) {
        this.mTimbreId = i;
        this.mResourceId = i2;
    }

    public int getResourceId() {
        return this.mResourceId;
    }

    public int getTimbreId() {
        return this.mTimbreId;
    }

    public boolean isSelected() {
        return this.mIsSelected;
    }

    public void setResourceId(int i) {
        this.mResourceId = i;
    }

    public void setSelected(boolean z) {
        this.mIsSelected = z;
    }

    public void setTimbreId(int i) {
        this.mTimbreId = i;
    }
}
