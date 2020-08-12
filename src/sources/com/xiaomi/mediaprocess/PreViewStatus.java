package com.xiaomi.mediaprocess;

public enum PreViewStatus {
    PreViewStopped(0),
    PreViewPlaying(1),
    PreViewPaused(2);
    
    private int nCode;

    private PreViewStatus(int i) {
        this.nCode = i;
    }

    public static PreViewStatus int2enum(int i) {
        PreViewStatus preViewStatus = PreViewStopped;
        PreViewStatus[] values = values();
        for (PreViewStatus preViewStatus2 : values) {
            if (preViewStatus2.ordinal() == i) {
                preViewStatus = preViewStatus2;
            }
        }
        return preViewStatus;
    }

    public String toString() {
        return String.valueOf(this.nCode);
    }
}
