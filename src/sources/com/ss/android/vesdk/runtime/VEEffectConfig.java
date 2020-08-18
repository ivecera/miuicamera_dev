package com.ss.android.vesdk.runtime;

import android.content.res.AssetManager;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bef.effectsdk.ResourceFinder;
import com.ss.android.ttve.nativePort.TENativeLibsLoader;
import com.ss.android.vesdk.TEEffectCallback;
import com.ss.android.vesdk.VEException;
import com.ss.android.vesdk.VELogUtil;

@Keep
public class VEEffectConfig {
    private static final String TAG = VEEffectConfig.class.getSimpleName();
    private static AssetManager sEffectAssetManager = null;
    private static ResourceFinder sFinder = null;

    static {
        TENativeLibsLoader.loadLibrary();
    }

    public static void configEffect(@Nullable String str, @NonNull String str2) {
        nativeConfigEffect(sFinder != null, sEffectAssetManager, str, str2);
    }

    public static boolean enableAssetManager(@NonNull AssetManager assetManager) {
        sEffectAssetManager = assetManager;
        return true;
    }

    public static void enableEffectAmazingForEditor(boolean z) {
        nativeEnableEffectAmazingForEditor(z);
    }

    public static long getNativeFinder(long j) {
        if (j == 0) {
            VELogUtil.e(TAG, "getNativeFinder effectHandler is null");
            return 0;
        }
        ResourceFinder resourceFinder = sFinder;
        if (resourceFinder != null) {
            return resourceFinder.createNativeResourceFinder(j);
        }
        throw new VEException(-1, "错误调用finder相关接口");
    }

    private static native void nativeConfigEffect(boolean z, AssetManager assetManager, String str, String str2);

    private static native void nativeEnableEffectAmazingForEditor(boolean z);

    private static native void nativeSetEffectCallback(TEEffectCallback tEEffectCallback);

    private static native void nativeSetShareDir(String str);

    public static void releaseNativeFinder(long j) {
        if (j == 0) {
            VELogUtil.e(TAG, "getNativeFinder effectHandler is null");
            return;
        }
        ResourceFinder resourceFinder = sFinder;
        if (resourceFinder != null) {
            resourceFinder.release(j);
            return;
        }
        throw new VEException(-1, "错误调用finder相关接口");
    }

    public static boolean setEffectCallback(@NonNull TEEffectCallback tEEffectCallback) {
        nativeSetEffectCallback(tEEffectCallback);
        return true;
    }

    public static void setResourceFinder(ResourceFinder resourceFinder) {
        sFinder = resourceFinder;
    }

    public static boolean setShareDir(@NonNull String str) {
        nativeSetShareDir(str);
        return true;
    }
}
