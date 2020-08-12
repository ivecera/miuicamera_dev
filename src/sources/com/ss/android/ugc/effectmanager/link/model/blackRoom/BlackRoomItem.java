package com.ss.android.ugc.effectmanager.link.model.blackRoom;

public abstract class BlackRoomItem {
    protected boolean inBlackRoom;
    protected int lockedCount = 0;
    protected long startLockTime;

    public abstract String getItemName();

    public int getLockedCount() {
        return this.lockedCount;
    }

    public boolean isInBlackRoom() {
        return this.inBlackRoom;
    }

    public boolean lockToBlackRoom() {
        if (isInBlackRoom()) {
            return false;
        }
        this.inBlackRoom = true;
        this.lockedCount++;
        this.startLockTime = System.currentTimeMillis();
        return true;
    }

    public void resetStatus() {
        this.inBlackRoom = false;
        this.lockedCount = 0;
    }

    public long startLockTime() {
        return this.startLockTime;
    }

    public String toString() {
        return "BlackRoomItem{name=" + getItemName() + "lockedCount=" + this.lockedCount + ", inBlackRoom=" + this.inBlackRoom + '}';
    }

    public void unlockFromBlackRoom() {
        this.inBlackRoom = false;
    }
}
