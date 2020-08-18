package com.android.camera.handgesture;

import com.android.camera.log.Log;

public class HandGesture {
    private static final String TAG = "HandGesture";

    static {
        try {
            System.loadLibrary("camera_handgesture_mpbase");
            System.loadLibrary("arc_layer_sgl");
            System.loadLibrary("handengine.arcsoft");
            System.loadLibrary("camera_arcsoft_handgesture");
        } catch (UnsatisfiedLinkError e2) {
            String str = TAG;
            Log.e(str, "can't loadLibrary, " + e2.getMessage());
        }
    }

    public final native int detectGesture(byte[] bArr, int i, int i2, int i3);

    public final native void init(int i);

    public final native void unInit();
}
