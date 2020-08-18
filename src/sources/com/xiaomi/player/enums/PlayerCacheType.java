package com.xiaomi.player.enums;

public enum PlayerCacheType {
    PlayerCacheNo(0),
    PlayerCacheFile(1),
    PlayerCacheMemory(2),
    PlayerCacheAll(3);
    
    private int nCode;

    private PlayerCacheType(int i) {
        this.nCode = i;
    }

    public static PlayerCacheType int2enum(int i) {
        PlayerCacheType playerCacheType = PlayerCacheNo;
        PlayerCacheType[] values = values();
        for (PlayerCacheType playerCacheType2 : values) {
            if (playerCacheType2.ordinal() == i) {
                playerCacheType = playerCacheType2;
            }
        }
        return playerCacheType;
    }

    public String toString() {
        return String.valueOf(this.nCode);
    }
}
