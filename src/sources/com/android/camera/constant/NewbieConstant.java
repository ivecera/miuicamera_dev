package com.android.camera.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class NewbieConstant {
    public static final int NEWBIE_AI_SCENE = 3;
    public static final int NEWBIE_DOCUMENT_MODE = 9;
    public static final int NEWBIE_FRONT_CAM_ROTATE = 2;
    public static final int NEWBIE_ID_CARD_MODE = 8;
    public static final int NEWBIE_MACRO_MODE = 5;
    public static final int NEWBIE_NULL = -1;
    public static final int NEWBIE_PORTRAIT = 1;
    public static final int NEWBIE_ULTRA_TELE = 7;
    public static final int NEWBIE_ULTRA_WIDE = 4;
    public static final int NEWBIE_VV = 6;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NewbieType {
    }
}
