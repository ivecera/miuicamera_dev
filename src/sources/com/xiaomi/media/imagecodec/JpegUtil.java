package com.xiaomi.media.imagecodec;

import android.media.Image;
import android.util.Log;

public final class JpegUtil {
    private static final String TAG = "JpegUtil";

    static {
        System.loadLibrary("jni_jpegutil");
        nativeClassInit();
    }

    private JpegUtil() {
    }

    public static Image.Plane[] getPlanesExtra(Image image) {
        Log.d(TAG, "getOriginalByteBuffer");
        if (image == null) {
            throw new IllegalArgumentException("The input image must not be null");
        } else if (image.getClass().getName().equals("android.media.ImageReader$SurfaceImage")) {
            Image.Plane[] nativeGetPlanesExtra = nativeGetPlanesExtra(image);
            String str = TAG;
            Log.d(str, "getOriginalByteBuffer planes:" + nativeGetPlanesExtra);
            return nativeGetPlanesExtra;
        } else {
            throw new IllegalArgumentException("Only images from ImageReader can be fed to JpegUtil, other image source is not supported yet!");
        }
    }

    private static native void nativeClassInit();

    private static native Image.Plane[] nativeGetPlanesExtra(Image image);
}
