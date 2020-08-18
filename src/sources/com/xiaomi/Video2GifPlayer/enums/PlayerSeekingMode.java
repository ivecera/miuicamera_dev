package com.xiaomi.Video2GifPlayer.enums;

public enum PlayerSeekingMode {
    PlayerSeekingNormalMode(0),
    PlayerSeekingFastMode(1),
    PlayerSeekingPreciseMode(2);
    
    private int nCode;

    private PlayerSeekingMode(int i) {
        this.nCode = i;
    }

    public static PlayerSeekingMode int2enum(int i) {
        PlayerSeekingMode playerSeekingMode = PlayerSeekingNormalMode;
        PlayerSeekingMode[] values = values();
        for (PlayerSeekingMode playerSeekingMode2 : values) {
            if (playerSeekingMode2.ordinal() == i) {
                playerSeekingMode = playerSeekingMode2;
            }
        }
        return playerSeekingMode;
    }

    public String toString() {
        return String.valueOf(this.nCode);
    }
}
