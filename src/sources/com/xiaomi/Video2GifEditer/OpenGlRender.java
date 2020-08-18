package com.xiaomi.Video2GifEditer;

import android.util.Log;

public class OpenGlRender {
    private static String TAG = "OpenGlRender";
    private int mAttribtexture;
    private int mAttribvertex;
    private int mTextureUniformU;
    private int mTextureUniformV;
    private int mTextureUniformY;
    private byte[] mTextureVertices_buffer;
    private int mTexture_u;
    private int mTexture_v;
    private int mTexture_y;
    private byte[] mVertexVertices_buffer;

    public OpenGlRender() {
        Log.d(TAG, "construction");
    }

    private static native void RenderFrameJni();

    private static native void SetCurrentGLContextJni();

    private static native void SetOpengGlRenderParamsJni(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr, byte[] bArr2);

    public void RenderFrame() {
        RenderFrameJni();
    }

    public void SetOpengGlRenderParams(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr, byte[] bArr2) {
        Log.d(TAG, "SetOpengGlRenderParams");
        SetOpengGlRenderParamsJni(i, i2, i3, i4, i5, i6, i7, i8, bArr, bArr2);
    }
}
