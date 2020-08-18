package com.ss.android.ugc.effectmanager.common.utils;

import android.support.annotation.RestrictTo;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import java.io.File;
import java.util.regex.Pattern;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class EffectCacheKeyGenerator {
    private EffectCacheKeyGenerator() {
    }

    public static Pattern generateCategoryCachePattern(String str, String str2, int i, int i2, int i3) {
        return Pattern.compile(EffectConstants.KEY_EFFECT_CHANNEL + str2 + "(.*)");
    }

    public static String generateCategoryEffectKey(String str, String str2, int i, int i2, int i3) {
        return str + File.separator + EffectConstants.KEY_EFFECT_CHANNEL + str2 + i + "_" + i2 + "_" + i3;
    }

    public static String generateCategoryVersionKey(String str, String str2) {
        return str + File.separator + EffectConstants.KEY_CATEGORY_VERSION + str2;
    }

    public static String generatePanelInfoKey(String str, String str2) {
        return str2 + File.separator + EffectConstants.KEY_EFFECT_CHANNEL + str;
    }

    public static String generatePanelInfoVersionKey(String str) {
        return str + File.separator + "effect_version";
    }

    public static String generatePanelKey(String str, String str2) {
        return EffectConstants.KEY_EFFECT_CHANNEL + str2 + str;
    }

    public static Pattern generatePattern(String str) {
        return Pattern.compile(EffectConstants.KEY_EFFECT_CHANNEL + str + "(.*)");
    }
}
