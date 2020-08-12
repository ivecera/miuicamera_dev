package com.android.camera.beautyshot;

import android.content.Context;
import android.content.res.AssetManager;
import com.android.camera.log.Log;

public class BeautyShot {
    private static final String TAG = "BeautyShot";
    private AssetManager mAssetsManager = null;

    static {
        try {
            System.loadLibrary("camera_beauty_mpbase");
            System.loadLibrary("camera_arcsoft_beautyshot");
            System.loadLibrary("jni_arcsoft_beautyshot");
        } catch (UnsatisfiedLinkError e2) {
            Log.e(TAG, "can't loadLibrary, " + e2.getMessage());
        }
    }

    public static native void flipYuvHorizontal(byte[] bArr, int i, int i2);

    public static native void flipYuvVertical(byte[] bArr, int i, int i2);

    private native void nativeInit();

    private native void nativeUninit();

    private native int processByBeautyLevel(AssetManager assetManager, byte[] bArr, int i, int i2, int i3, int i4, int i5);

    private native int processBySmoothLevel(AssetManager assetManager, byte[] bArr, int i, int i2, int i3, int i4, int i5);

    public void init(Context context) {
        this.mAssetsManager = context.getAssets();
        nativeInit();
    }

    public int processByBeautyLevel(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        AssetManager assetManager = this.mAssetsManager;
        if (assetManager != null) {
            return processByBeautyLevel(assetManager, bArr, i, i2, i3, i4, i5);
        }
        Log.d(TAG, "BeautyShot is not initialized");
        return 0;
    }

    public int processBySmoothLevel(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        AssetManager assetManager = this.mAssetsManager;
        if (assetManager != null) {
            return processBySmoothLevel(assetManager, bArr, i, i2, i3, i4, i5);
        }
        Log.d(TAG, "BeautyShot is not initialized");
        return 0;
    }

    public void uninit() {
        this.mAssetsManager = null;
        nativeUninit();
    }
}
