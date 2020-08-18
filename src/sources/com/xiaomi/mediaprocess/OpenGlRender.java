package com.xiaomi.mediaprocess;

import android.util.Log;

public class OpenGlRender {
    private static String TAG = "OpenGlRender";

    public OpenGlRender() {
        Log.d(TAG, "construction");
    }

    private static native void FrameAvailableJni();

    private static native void RenderFrameJni();

    private static native void SetCurrentGLContextJni(int i);

    private static native void SetOpengGlRenderParamsJni(int i, int i2, int i3, int i4, int i5, byte[] bArr, byte[] bArr2);

    private native void SetWindowSizeJni(int i, int i2, int i3, int i4);

    public void RenderFrame() {
        RenderFrameJni();
    }

    public void SetCurrentGLContext(int i) {
        String str = TAG;
        Log.d(str, "SetCurrentGLContext, surface texture: " + i);
        SetCurrentGLContextJni(i);
    }

    public void SetOpengGlRenderParams(int i, int i2, int i3, int i4, int i5, byte[] bArr, byte[] bArr2) {
        Log.d(TAG, "SetOpengGlRenderParams");
        SetOpengGlRenderParamsJni(i, i2, i3, i4, i5, bArr, bArr2);
    }

    public void SetWindowSize(int i, int i2, int i3, int i4) {
        SetWindowSizeJni(i, i2, i3, i4);
    }

    public void onFrameAvailable() {
        FrameAvailableJni();
    }
}
