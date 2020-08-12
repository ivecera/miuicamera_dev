package com.xiaomi.player.enums;

public enum AVErrorState {
    AVErrorStreamNotFound(0),
    AVErrorDecoderNotFound(1),
    AVErrorHttpBadRequest(2),
    AVErrorHttpUnauthorized(3),
    AVErrorHttpForbidden(4),
    AVErrorHttpNotFound(5),
    AVErrorTimedOut(6),
    AVErrorNoEntrance(7),
    AVErrorNoMemory(8),
    AVErrorIO(9),
    AVErrorAccess(10),
    AVErrorOther(11);
    
    private int nCode;

    private AVErrorState(int i) {
        this.nCode = i;
    }

    public static AVErrorState int2enum(int i) {
        AVErrorState aVErrorState = AVErrorStreamNotFound;
        AVErrorState[] values = values();
        for (AVErrorState aVErrorState2 : values) {
            if (aVErrorState2.ordinal() == i) {
                aVErrorState = aVErrorState2;
            }
        }
        return aVErrorState;
    }

    public int getCode() {
        return this.nCode;
    }

    public String toString() {
        return String.valueOf(this.nCode);
    }
}
