package com.ss.android.vesdk;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class VEBeautyParam {
    public static final int BEAUTY_BLUSHER = 18;
    public static final int BEAUTY_LIPSTICK = 17;
    public static final int BEAUTY_NASOLABIAL = 19;
    public static final int BEAUTY_POUCH = 20;
    public static final int BEAUTY_RESHAPE_CHEEK = 5;
    public static final int BEAUTY_RESHAPE_EYE = 4;
    public static final int BEAUTY_SHARP = 9;
    public static final int BEAUTY_SMOOTH = 2;
    public static final int BEAUTY_WHITE = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VEBeautyType {
    }

    public static String describle(int i) {
        if (i == 1) {
            return "beauty_white";
        }
        if (i == 2) {
            return "beauty_smooth";
        }
        if (i == 4) {
            return "reshape_eye";
        }
        if (i == 5) {
            return "reshape cheek";
        }
        if (i == 9) {
            return "sharp";
        }
        switch (i) {
            case 17:
                return "beauty_lipstick";
            case 18:
                return "beauty_blusher";
            case 19:
                return "beauty_nasolabial";
            case 20:
                return "beauty_pouch";
            default:
                return "";
        }
    }
}
