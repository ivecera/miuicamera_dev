package com.ss.android.ugc.effectmanager.link.model.blackRoom;

import com.ss.android.ugc.effectmanager.common.utils.LogUtils;

public class BlackRoom {
    private static final String TAG = "BlackRoom";

    private long getLockedTime(int i) {
        int i2 = 5;
        if (i <= 5) {
            i2 = i;
        }
        if (i2 == 1) {
            return 60000;
        }
        return ((long) Math.pow(2.0d, (double) (i2 - 1))) * 60000;
    }

    public boolean checkHostAvailable(BlackRoomItem blackRoomItem) {
        if (blackRoomItem == null) {
            return false;
        }
        if (!blackRoomItem.isInBlackRoom()) {
            LogUtils.d(TAG, blackRoomItem.getItemName() + " is available");
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis() - blackRoomItem.startLockTime();
        if (currentTimeMillis >= getLockedTime(blackRoomItem.getLockedCount())) {
            LogUtils.d(TAG, "unlock " + blackRoomItem.getItemName() + ", locked count = " + blackRoomItem.getLockedCount() + ", should lock " + (getLockedTime(blackRoomItem.getLockedCount()) / 60000) + " min, already locked " + (currentTimeMillis / 60000) + " min");
            blackRoomItem.unlockFromBlackRoom();
            return true;
        }
        LogUtils.e(TAG, blackRoomItem.getItemName() + " is locked, locked count = " + blackRoomItem.getLockedCount() + ", should lock " + (getLockedTime(blackRoomItem.getLockedCount()) / 60000) + " min, already locked " + (currentTimeMillis / 60000) + " min");
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x004b, code lost:
        return;
     */
    public synchronized void lock(BlackRoomItem blackRoomItem) {
        if (blackRoomItem != null) {
            if (blackRoomItem.lockToBlackRoom()) {
                LogUtils.e(TAG, "lock " + blackRoomItem.getItemName() + " " + blackRoomItem.getLockedCount() + " time for " + (getLockedTime(blackRoomItem.getLockedCount()) / 60000) + " min");
            }
        }
    }
}
