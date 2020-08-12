package com.arcsoft.avatar.util;

import com.arcsoft.avatar.AvatarConfig;

public class AvatarConfigUtils {
    public static int getCurrentConfigIdWithType(int i, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        switch (i) {
            case 1:
                return aSAvatarConfigValue.configHairStyleID;
            case 2:
                return aSAvatarConfigValue.configHairColorID;
            case 3:
                return aSAvatarConfigValue.configFaceColorID;
            case 4:
                return aSAvatarConfigValue.configEyeColorID;
            case 5:
                return aSAvatarConfigValue.configLipColorID;
            case 6:
                return aSAvatarConfigValue.configHairHighlightColorID;
            case 7:
                return aSAvatarConfigValue.configFrecklesID;
            case 8:
                return aSAvatarConfigValue.configNevusID;
            case 9:
                return aSAvatarConfigValue.configEyewearStyleID;
            case 10:
                return aSAvatarConfigValue.configEyewearFrameID;
            case 11:
                return aSAvatarConfigValue.configEyewearLensesID;
            case 12:
                return aSAvatarConfigValue.configHeadwearStyleID;
            case 13:
                return aSAvatarConfigValue.configHeadwearColorID;
            case 14:
                return aSAvatarConfigValue.configBeardStyleID;
            case 15:
                return aSAvatarConfigValue.configBeardColorID;
            case 16:
                return aSAvatarConfigValue.configEarringStyleID;
            case 17:
                return aSAvatarConfigValue.configEarringColorID;
            case 18:
                return aSAvatarConfigValue.configEyelashStyleID;
            case 19:
                return aSAvatarConfigValue.configEyebrowColorID;
            case 20:
                return aSAvatarConfigValue.configFaceShapeID;
            case 21:
                return aSAvatarConfigValue.configEyeShapeID;
            case 22:
                return aSAvatarConfigValue.configMouthShapeID;
            case 23:
                return aSAvatarConfigValue.configNoseShapeID;
            case 24:
                return aSAvatarConfigValue.configEarShapeID;
            case 25:
                return aSAvatarConfigValue.configEyebrowShapeID;
            default:
                return -1;
        }
    }

    public static float getCurrentContinuousValueWithType(int i, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        switch (i) {
            case 2:
                return aSAvatarConfigValue.configHairColorValue;
            case 3:
                return aSAvatarConfigValue.configFaceColorValue;
            case 4:
                return aSAvatarConfigValue.configEyeColorValue;
            case 5:
                return aSAvatarConfigValue.configLipColorValue;
            case 6:
                return aSAvatarConfigValue.configHairHighlightColorValue;
            case 7:
            case 8:
            case 9:
            case 12:
            case 14:
            case 16:
            case 18:
            default:
                return -1.0f;
            case 10:
                return aSAvatarConfigValue.configEyewearFrameValue;
            case 11:
                return aSAvatarConfigValue.configEyewearLensesValue;
            case 13:
                return aSAvatarConfigValue.configHeadwearColorValue;
            case 15:
                return aSAvatarConfigValue.configBeardColorValue;
            case 17:
                return aSAvatarConfigValue.configEarringColorValue;
            case 19:
                return aSAvatarConfigValue.configEyebrowColorValue;
            case 20:
                return aSAvatarConfigValue.configFaceShapeValue;
            case 21:
                return aSAvatarConfigValue.configEyeShapeValue;
            case 22:
                return aSAvatarConfigValue.configMouthShapeValue;
            case 23:
                return aSAvatarConfigValue.configNoseShapeValue;
            case 24:
                return aSAvatarConfigValue.configEarShapeValue;
            case 25:
                return aSAvatarConfigValue.configEyebrowShapeValue;
        }
    }

    public static int getMatchConfigType(int i) {
        if (i == 1) {
            return 2;
        }
        if (i == 12) {
            return 13;
        }
        if (i == 14) {
            return 15;
        }
        if (i == 16) {
            return 17;
        }
        if (i == 25) {
            return 19;
        }
        switch (i) {
            case 20:
                return 3;
            case 21:
                return 4;
            case 22:
                return 5;
            default:
                return 0;
        }
    }

    public static boolean isColorConfigComponentType(int i) {
        return i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 13 || i == 15 || i == 17 || i == 19 || i == 10 || i == 11;
    }

    public static boolean isSupportContinuousConfigInfo(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo) {
        int i = aSAvatarConfigInfo.configType;
        if (i == 20 || i == 22 || i == 23 || i == 21 || i == 10 || i == 11) {
            return false;
        }
        return aSAvatarConfigInfo.isSupportContinuous;
    }

    public static void updateConfigID(int i, int i2, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        switch (i) {
            case 1:
                aSAvatarConfigValue.configHairStyleID = i2;
                return;
            case 2:
                aSAvatarConfigValue.configHairColorID = i2;
                return;
            case 3:
                aSAvatarConfigValue.configFaceColorID = i2;
                return;
            case 4:
                aSAvatarConfigValue.configEyeColorID = i2;
                return;
            case 5:
                aSAvatarConfigValue.configLipColorID = i2;
                return;
            case 6:
                aSAvatarConfigValue.configHairHighlightColorID = i2;
                return;
            case 7:
                aSAvatarConfigValue.configFrecklesID = i2;
                return;
            case 8:
                aSAvatarConfigValue.configNevusID = i2;
                return;
            case 9:
                aSAvatarConfigValue.configEyewearStyleID = i2;
                return;
            case 10:
                aSAvatarConfigValue.configEyewearFrameID = i2;
                return;
            case 11:
                aSAvatarConfigValue.configEyewearLensesID = i2;
                return;
            case 12:
                aSAvatarConfigValue.configHeadwearStyleID = i2;
                return;
            case 13:
                aSAvatarConfigValue.configHeadwearColorID = i2;
                return;
            case 14:
                aSAvatarConfigValue.configBeardStyleID = i2;
                return;
            case 15:
                aSAvatarConfigValue.configBeardColorID = i2;
                return;
            case 16:
                aSAvatarConfigValue.configEarringStyleID = i2;
                return;
            case 17:
                aSAvatarConfigValue.configEarringColorID = i2;
                return;
            case 18:
                aSAvatarConfigValue.configEyelashStyleID = i2;
                return;
            case 19:
                aSAvatarConfigValue.configEyebrowColorID = i2;
                return;
            case 20:
                aSAvatarConfigValue.configFaceShapeID = i2;
                return;
            case 21:
                aSAvatarConfigValue.configEyeShapeID = i2;
                return;
            case 22:
                aSAvatarConfigValue.configMouthShapeID = i2;
                return;
            case 23:
                aSAvatarConfigValue.configNoseShapeID = i2;
                return;
            case 24:
                aSAvatarConfigValue.configEarShapeID = i2;
                return;
            case 25:
                aSAvatarConfigValue.configEyebrowShapeID = i2;
                return;
            case 26:
                aSAvatarConfigValue.configGenderID = i2;
                return;
            case 27:
                aSAvatarConfigValue.configCustomExpressionID = i2;
                return;
            default:
                return;
        }
    }

    public static void updateConfigValue(int i, float f2, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        switch (i) {
            case 2:
                aSAvatarConfigValue.configHairColorValue = f2;
                return;
            case 3:
                aSAvatarConfigValue.configFaceColorValue = f2;
                return;
            case 4:
                aSAvatarConfigValue.configEyeColorValue = f2;
                return;
            case 5:
                aSAvatarConfigValue.configLipColorValue = f2;
                return;
            case 6:
                aSAvatarConfigValue.configHairHighlightColorValue = f2;
                return;
            case 7:
            case 8:
            case 9:
            case 12:
            case 14:
            case 16:
            case 18:
            default:
                return;
            case 10:
                aSAvatarConfigValue.configEyewearFrameValue = f2;
                return;
            case 11:
                aSAvatarConfigValue.configEyewearLensesValue = f2;
                return;
            case 13:
                aSAvatarConfigValue.configHeadwearColorValue = f2;
                return;
            case 15:
                aSAvatarConfigValue.configBeardColorValue = f2;
                return;
            case 17:
                aSAvatarConfigValue.configEarringColorValue = f2;
                return;
            case 19:
                aSAvatarConfigValue.configEyebrowColorValue = f2;
                return;
            case 20:
                aSAvatarConfigValue.configFaceShapeValue = f2;
                return;
            case 21:
                aSAvatarConfigValue.configEyeShapeValue = f2;
                return;
            case 22:
                aSAvatarConfigValue.configMouthShapeValue = f2;
                return;
            case 23:
                aSAvatarConfigValue.configNoseShapeValue = f2;
                return;
            case 24:
                aSAvatarConfigValue.configEarShapeValue = f2;
                return;
            case 25:
                aSAvatarConfigValue.configEyebrowShapeValue = f2;
                return;
        }
    }
}
