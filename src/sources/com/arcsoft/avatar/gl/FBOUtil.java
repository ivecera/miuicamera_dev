package com.arcsoft.avatar.gl;

import android.opengl.GLES20;

public class FBOUtil {

    /* renamed from: f  reason: collision with root package name */
    private static final String f108f = "FBOUtil";
    private static final String j = "/sdcard/Pictures/readfbo/";

    /* renamed from: a  reason: collision with root package name */
    int[] f109a = new int[1];

    /* renamed from: b  reason: collision with root package name */
    int[] f110b = new int[1];

    /* renamed from: c  reason: collision with root package name */
    int[] f111c = new int[1];

    /* renamed from: d  reason: collision with root package name */
    int[] f112d = new int[1];

    /* renamed from: e  reason: collision with root package name */
    boolean f113e;
    private int g;
    private int h;
    private int i = 0;

    public void bindFramebuff() {
        this.f113e = GLES20.glIsEnabled(2929);
        GLES20.glEnable(2929);
        GLES20.glGetIntegerv(36006, this.f109a, 0);
        GLES20.glBindFramebuffer(36160, this.f110b[0]);
        GLES20.glClear(16640);
    }

    public int getHeight() {
        return this.h;
    }

    public int getTextureId() {
        return this.f111c[0];
    }

    public int getWidth() {
        return this.g;
    }

    public void initFramebuff(int i2, int i3) {
        this.g = i2;
        this.h = i3;
        int[] iArr = this.f109a;
        iArr[0] = 0;
        this.f110b[0] = 0;
        this.f111c[0] = 0;
        GLES20.glGetIntegerv(36006, iArr, 0);
        GLES20.glGenFramebuffers(1, this.f110b, 0);
        GLES20.glBindFramebuffer(36160, this.f110b[0]);
        GLES20.glPixelStorei(3317, 1);
        GLES20.glGenTextures(1, this.f111c, 0);
        GLES20.glBindTexture(3553, this.f111c[0]);
        GLES20.glTexParameteri(3553, 10241, 9728);
        GLES20.glTexParameteri(3553, 10240, 9728);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexImage2D(3553, 0, 6408, i2, i3, 0, 6408, 5121, null);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.f111c[0], 0);
        GLES20.glGenRenderbuffers(1, this.f112d, 0);
        GLES20.glBindRenderbuffer(36161, this.f112d[0]);
        GLES20.glRenderbufferStorage(36161, 33190, i2, i3);
        GLES20.glFramebufferRenderbuffer(36160, 36096, 36161, this.f112d[0]);
        GLES20.glCheckFramebufferStatus(36160);
        GLES20.glBindFramebuffer(36160, this.f109a[0]);
        GLES20.glBindTexture(3553, 0);
    }

    public void unBindFramebuff() {
        GLES20.glFinish();
        if (this.f113e) {
            GLES20.glEnable(2929);
        } else {
            GLES20.glDisable(2929);
        }
        GLES20.glBindFramebuffer(36160, this.f109a[0]);
    }

    public void uninitFramebuff() {
        GLES20.glDeleteTextures(1, this.f111c, 0);
        this.f111c[0] = 0;
        GLES20.glDeleteFramebuffers(1, this.f110b, 0);
        this.f110b[0] = 0;
        GLES20.glDeleteRenderbuffers(1, this.f112d, 0);
        this.f112d[0] = 0;
    }
}
