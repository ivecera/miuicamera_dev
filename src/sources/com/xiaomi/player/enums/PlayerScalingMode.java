package com.xiaomi.player.enums;

public enum PlayerScalingMode {
    PlayerScalingModeNone(0),
    PlayerScalingModeAspectFit(1),
    PlayerScalingModeAspectFill(2),
    PlayerScalingModeFill(3);
    
    private int nCode;

    private PlayerScalingMode(int i) {
        this.nCode = i;
    }

    public static PlayerScalingMode int2enum(int i) {
        PlayerScalingMode playerScalingMode = PlayerScalingModeNone;
        PlayerScalingMode[] values = values();
        for (PlayerScalingMode playerScalingMode2 : values) {
            if (playerScalingMode2.ordinal() == i) {
                playerScalingMode = playerScalingMode2;
            }
        }
        return playerScalingMode;
    }

    public String toString() {
        return String.valueOf(this.nCode);
    }
}
