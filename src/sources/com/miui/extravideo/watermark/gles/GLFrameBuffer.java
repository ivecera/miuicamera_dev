package com.miui.extravideo.watermark.gles;

import android.opengl.GLES20;
import android.util.Log;

public class GLFrameBuffer {
    private static final String TAG = "GLFrameBuffer";
    private int mFbo;
    private int mHeight;
    private int mTex;
    private int mWidth;

    public GLFrameBuffer(int i, int i2) {
        initFramebuffer(i, i2);
    }

    public int getFrameBufferId() {
        return this.mFbo;
    }

    public int getFrameBufferTextureId() {
        return this.mTex;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void initFramebuffer(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, iArr[0]);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, null);
        this.mTex = iArr[0];
        int[] iArr2 = new int[1];
        GLES20.glGenFramebuffers(1, iArr2, 0);
        GLES20.glBindFramebuffer(36160, iArr2[0]);
        this.mFbo = iArr2[0];
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, iArr[0], 0);
        GLES20.glBindFramebuffer(36160, 0);
        int glCheckFramebufferStatus = GLES20.glCheckFramebufferStatus(36160);
        if (glCheckFramebufferStatus != 36053) {
            String str = TAG;
            Log.e(str, "framebuffer is not ready(" + glCheckFramebufferStatus + ")");
        }
    }

    public void release() {
        GLES20.glDeleteTextures(1, new int[]{this.mTex}, 0);
        GLES20.glDeleteFramebuffers(1, new int[]{this.mFbo}, 0);
        this.mTex = 0;
        this.mFbo = 0;
    }
}
