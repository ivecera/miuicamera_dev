package com.xiaomi.recordplayer.enums;

public enum PlayerWorkingMode {
    PlayerWorkingLipSyncMode(0),
    PlayerWorkingLowVideoDelayMode(1),
    PlayerWorkingVideoSmoothMode(2),
    PlayerWorkingDisableAudioDeviceMode(3);
    
    private int nCode;

    private PlayerWorkingMode(int i) {
        this.nCode = i;
    }

    public static PlayerWorkingMode int2enum(int i) {
        PlayerWorkingMode playerWorkingMode = PlayerWorkingLipSyncMode;
        PlayerWorkingMode[] values = values();
        for (PlayerWorkingMode playerWorkingMode2 : values) {
            if (playerWorkingMode2.ordinal() == i) {
                playerWorkingMode = playerWorkingMode2;
            }
        }
        return playerWorkingMode;
    }

    public String toString() {
        return String.valueOf(this.nCode);
    }
}
