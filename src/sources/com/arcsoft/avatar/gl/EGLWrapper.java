package com.arcsoft.avatar.gl;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.view.Surface;

public class EGLWrapper {

    /* renamed from: a  reason: collision with root package name */
    private static final String f102a = "Arc_EGLWrapper";

    /* renamed from: b  reason: collision with root package name */
    private static final int f103b = 12610;

    /* renamed from: c  reason: collision with root package name */
    private EGLContext f104c;

    /* renamed from: d  reason: collision with root package name */
    private EGLDisplay f105d;

    /* renamed from: e  reason: collision with root package name */
    private EGLSurface f106e;

    /* renamed from: f  reason: collision with root package name */
    private EGLConfig[] f107f;
    private EGLContext g;
    private boolean h;
    private Surface i;
    private int j;
    private int k;

    public EGLWrapper(int i2, int i3) {
        this.f104c = EGL14.EGL_NO_CONTEXT;
        this.f105d = EGL14.EGL_NO_DISPLAY;
        this.f106e = EGL14.EGL_NO_SURFACE;
        this.f107f = new EGLConfig[1];
        this.g = EGL14.EGL_NO_CONTEXT;
        this.h = false;
        this.h = true;
        this.j = i2;
        this.k = i3;
        c();
    }

    public EGLWrapper(int i2, int i3, EGLContext eGLContext) {
        this.f104c = EGL14.EGL_NO_CONTEXT;
        this.f105d = EGL14.EGL_NO_DISPLAY;
        this.f106e = EGL14.EGL_NO_SURFACE;
        this.f107f = new EGLConfig[1];
        this.g = EGL14.EGL_NO_CONTEXT;
        this.h = false;
        this.h = true;
        this.j = i2;
        this.k = i3;
        this.g = eGLContext;
        c();
    }

    public EGLWrapper(Surface surface) {
        this.f104c = EGL14.EGL_NO_CONTEXT;
        this.f105d = EGL14.EGL_NO_DISPLAY;
        this.f106e = EGL14.EGL_NO_SURFACE;
        this.f107f = new EGLConfig[1];
        this.g = EGL14.EGL_NO_CONTEXT;
        this.h = false;
        if (surface != null) {
            this.i = surface;
            c();
            return;
        }
        throw new NullPointerException();
    }

    public EGLWrapper(Surface surface, EGLContext eGLContext) {
        this.f104c = EGL14.EGL_NO_CONTEXT;
        this.f105d = EGL14.EGL_NO_DISPLAY;
        this.f106e = EGL14.EGL_NO_SURFACE;
        this.f107f = new EGLConfig[1];
        this.g = EGL14.EGL_NO_CONTEXT;
        this.h = false;
        if (surface != null) {
            this.i = surface;
            this.g = eGLContext;
            c();
            return;
        }
        throw new NullPointerException();
    }

    private void a() {
        this.f106e = EGL14.eglCreateWindowSurface(this.f105d, this.f107f[0], this.i, new int[]{12344}, 0);
        a("eglCreateWindowSurface");
        if (this.f106e == null) {
            throw new RuntimeException("surface == null");
        }
    }

    private void a(String str) {
        int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            new Exception("NOT_ERROR_JUST_SEE_CALL_STACK").printStackTrace();
            throw new RuntimeException(str + ": EGL_ERROR_CODE: 0x" + Integer.toHexString(eglGetError));
        }
    }

    private void b() {
        this.f106e = EGL14.eglCreatePbufferSurface(this.f105d, this.f107f[0], new int[]{12375, this.j, 12374, this.k, 12344}, 0);
        a("createEGLPbufferSurface");
        if (this.f106e == null) {
            throw new RuntimeException("surface == null");
        }
    }

    private void c() {
        this.f105d = EGL14.eglGetDisplay(0);
        EGLDisplay eGLDisplay = this.f105d;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            int[] iArr = new int[2];
            if (EGL14.eglInitialize(eGLDisplay, iArr, 0, iArr, 1)) {
                int[] iArr2 = this.h ? new int[]{12339, 1, 12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 8, 12610, 1, 12344} : new int[]{12339, 4, 12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 8, 12610, 1, 12344};
                EGLDisplay eGLDisplay2 = this.f105d;
                EGLConfig[] eGLConfigArr = this.f107f;
                if (EGL14.eglChooseConfig(eGLDisplay2, iArr2, 0, eGLConfigArr, 0, eGLConfigArr.length, new int[1], 0)) {
                    this.f104c = EGL14.eglCreateContext(this.f105d, this.f107f[0], this.g, new int[]{12440, 2, 12344}, 0);
                    a("eglCreateContext");
                    if (this.f104c != null) {
                        if (this.h) {
                            b();
                        } else {
                            a();
                        }
                        this.j = getWidth();
                        this.k = getHeight();
                        return;
                    }
                    throw new RuntimeException("eglCreateContext == null");
                }
                throw new RuntimeException("eglChooseConfig [RGBA888 + recordable] ES2 EGL_config_fail...");
            }
            this.f105d = null;
            throw new RuntimeException("EGL14.eglInitialize fail...");
        }
        throw new RuntimeException("EGL14.eglGetDisplay fail...");
    }

    private void d() {
        EGLDisplay eGLDisplay = this.f105d;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGL14.eglDestroySurface(eGLDisplay, this.f106e);
            this.f106e = EGL14.EGL_NO_SURFACE;
        }
    }

    public int getHeight() {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.f105d, this.f106e, 12374, iArr, 0);
        return iArr[0];
    }

    public Surface getSurface() {
        return this.i;
    }

    public int getWidth() {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.f105d, this.f106e, 12375, iArr, 0);
        return iArr[0];
    }

    public boolean makeCurrent() {
        EGLSurface eGLSurface;
        EGLDisplay eGLDisplay = this.f105d;
        if (eGLDisplay == null || (eGLSurface = this.f106e) == null) {
            return false;
        }
        boolean eglMakeCurrent = EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f104c);
        if (!eglMakeCurrent) {
            a("makeCurrent");
        }
        return eglMakeCurrent;
    }

    public void makeUnCurrent() {
        EGLDisplay eGLDisplay = this.f105d;
        EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
        if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT)) {
            a("makeUnCurrent");
        }
    }

    public void release() {
        EGLDisplay eGLDisplay = this.f105d;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGL14.eglDestroySurface(eGLDisplay, this.f106e);
            EGL14.eglDestroyContext(this.f105d, this.f104c);
            EGL14.eglTerminate(this.f105d);
        }
        this.f105d = EGL14.EGL_NO_DISPLAY;
        this.f104c = EGL14.EGL_NO_CONTEXT;
        this.f106e = EGL14.EGL_NO_SURFACE;
        this.g = EGL14.EGL_NO_CONTEXT;
        try {
            if (this.i != null) {
                this.i.release();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.i = null;
    }

    public void setPresentationTime(long j2) {
        EGLExt.eglPresentationTimeANDROID(this.f105d, this.f106e, j2);
        a("eglPresentationTimeANDROID");
    }

    public boolean swapBuffers() {
        EGLSurface eGLSurface;
        EGLDisplay eGLDisplay = this.f105d;
        if (eGLDisplay == null || (eGLSurface = this.f106e) == null) {
            return false;
        }
        boolean eglSwapBuffers = EGL14.eglSwapBuffers(eGLDisplay, eGLSurface);
        if (!eglSwapBuffers) {
            a("makeCurrent");
        }
        return eglSwapBuffers;
    }

    public void updateSize(int i2, int i3) {
        if (i2 != this.j || i3 != this.k) {
            d();
            a();
            this.j = getWidth();
            this.k = getHeight();
        }
    }
}
