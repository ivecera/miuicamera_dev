package com.android.camera.features.mimoji2.bean;

import com.arcsoft.avatar.BackgroundInfo;

public class MimojiBgInfo implements Cloneable {
    private BackgroundInfo mBackgroundInfo;
    private int mFrame;
    private boolean mIsSelected;
    private long mLastRefreshTime;
    private int mResourceId;

    public MimojiBgInfo() {
    }

    public MimojiBgInfo(BackgroundInfo backgroundInfo, int i) {
        this.mBackgroundInfo = backgroundInfo;
        this.mResourceId = i;
    }

    @Override // java.lang.Object
    public MimojiBgInfo clone() {
        MimojiBgInfo mimojiBgInfo = new MimojiBgInfo();
        mimojiBgInfo.setBackgroundInfo(getBackgroundInfo());
        mimojiBgInfo.setResourceId(getResourceId());
        mimojiBgInfo.mFrame = 0;
        return mimojiBgInfo;
    }

    public BackgroundInfo getBackgroundInfo() {
        return this.mBackgroundInfo;
    }

    public boolean getIsNeedRefresh() {
        long currentTimeMillis = System.currentTimeMillis();
        BackgroundInfo backgroundInfo = this.mBackgroundInfo;
        if (backgroundInfo == null) {
            return false;
        }
        if (currentTimeMillis - this.mLastRefreshTime < ((long) backgroundInfo.getDelayMillis()) && this.mFrame != 0) {
            return false;
        }
        this.mLastRefreshTime = currentTimeMillis;
        return true;
    }

    public long getRefreshTime() {
        return this.mLastRefreshTime;
    }

    public int getResourceId() {
        return this.mResourceId;
    }

    public boolean isSelected() {
        return this.mIsSelected;
    }

    public int nextFrame() {
        int i = this.mFrame;
        BackgroundInfo backgroundInfo = this.mBackgroundInfo;
        if (backgroundInfo == null || backgroundInfo.getCount() <= 0) {
            this.mFrame = 0;
        } else {
            if (this.mFrame == this.mBackgroundInfo.getCount() - 1) {
                this.mFrame = -1;
            }
            this.mFrame++;
        }
        return i;
    }

    public void setBackgroundInfo(BackgroundInfo backgroundInfo) {
        this.mBackgroundInfo = backgroundInfo;
    }

    public void setRefreshTime(long j) {
        this.mLastRefreshTime = j;
    }

    public void setResourceId(int i) {
        this.mResourceId = i;
    }

    public void setSelected(boolean z) {
        this.mIsSelected = z;
    }
}
