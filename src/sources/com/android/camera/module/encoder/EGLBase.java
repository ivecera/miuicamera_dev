package com.android.camera.module.encoder;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.os.Build;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.android.camera.log.Log;

@TargetApi(18)
public class EGLBase {
    private static final int EGL_RECORDABLE_ANDROID = 12610;
    /* access modifiers changed from: private */
    public static final String TAG = "EGLBase";
    private EGLContext mDefaultContext = EGL14.EGL_NO_CONTEXT;
    private EGLConfig mEglConfig = null;
    private EGLContext mEglContext = EGL14.EGL_NO_CONTEXT;
    private EGLDisplay mEglDisplay = EGL14.EGL_NO_DISPLAY;

    public static class EglSurface {
        private final EGLBase mEgl;
        private EGLSurface mEglSurface = EGL14.EGL_NO_SURFACE;
        private final int mHeight;
        private final int mWidth;

        EglSurface(EGLBase eGLBase, int i, int i2) {
            Log.v(EGLBase.TAG, "EglSurface");
            this.mEgl = eGLBase;
            this.mEglSurface = this.mEgl.createOffscreenSurface(i, i2);
            this.mWidth = i;
            this.mHeight = i2;
        }

        EglSurface(EGLBase eGLBase, Object obj) {
            Log.v(EGLBase.TAG, "EglSurface");
            if ((obj instanceof SurfaceView) || (obj instanceof Surface) || (obj instanceof SurfaceHolder) || (obj instanceof SurfaceTexture)) {
                this.mEgl = eGLBase;
                this.mEglSurface = this.mEgl.createWindowSurface(obj);
                this.mWidth = this.mEgl.querySurface(this.mEglSurface, 12375);
                this.mHeight = this.mEgl.querySurface(this.mEglSurface, 12374);
                Log.v(EGLBase.TAG, String.format("EglSurface: size(%d, %d)", Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight)));
                return;
            }
            throw new IllegalArgumentException("unsupported surface");
        }

        public EGLContext getContext() {
            return this.mEgl.getContext();
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public void makeCurrent() {
            boolean unused = this.mEgl.makeCurrent(this.mEglSurface);
        }

        public void release() {
            Log.v(EGLBase.TAG, "EglSurface:release");
            this.mEgl.makeDefault();
            this.mEgl.destroyWindowSurface(this.mEglSurface);
            this.mEglSurface = EGL14.EGL_NO_SURFACE;
        }

        public void swap() {
            int unused = this.mEgl.swap(this.mEglSurface);
        }
    }

    public EGLBase(EGLContext eGLContext, boolean z, boolean z2) {
        Log.v(TAG, TAG);
        init(eGLContext, z, z2);
    }

    private void checkEglError(String str) {
        int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }

    private EGLContext createContext(EGLContext eGLContext) {
        Log.v(TAG, "createContext");
        EGLContext eglCreateContext = EGL14.eglCreateContext(this.mEglDisplay, this.mEglConfig, eGLContext, new int[]{12440, 2, 12344}, 0);
        checkEglError("eglCreateContext");
        return eglCreateContext;
    }

    /* access modifiers changed from: private */
    public EGLSurface createOffscreenSurface(int i, int i2) {
        Log.v(TAG, "createOffscreenSurface");
        int[] iArr = {12375, i, 12374, i2, 12344};
        EGLSurface eGLSurface = null;
        try {
            eGLSurface = EGL14.eglCreatePbufferSurface(this.mEglDisplay, this.mEglConfig, iArr, 0);
            checkEglError("eglCreatePbufferSurface");
            if (eGLSurface != null) {
                return eGLSurface;
            }
            throw new RuntimeException("surface was null");
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "createOffscreenSurface", e2);
        } catch (RuntimeException e3) {
            Log.e(TAG, "createOffscreenSurface", e3);
        }
    }

    /* access modifiers changed from: private */
    public EGLSurface createWindowSurface(Object obj) {
        String str = TAG;
        Log.v(str, "createWindowSurface: nativeWindow=" + obj);
        try {
            return EGL14.eglCreateWindowSurface(this.mEglDisplay, this.mEglConfig, obj, new int[]{12344}, 0);
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "eglCreateWindowSurface", e2);
            return null;
        }
    }

    private void destroyContext() {
        Log.v(TAG, "destroyContext");
        if (!EGL14.eglDestroyContext(this.mEglDisplay, this.mEglContext)) {
            String str = TAG;
            Log.e(str, "destroyContext: display=" + this.mEglDisplay + " context=" + this.mEglContext);
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("destroyContext: err=");
            sb.append(EGL14.eglGetError());
            Log.e(str2, sb.toString());
        }
        EGLContext eGLContext = EGL14.EGL_NO_CONTEXT;
        this.mEglContext = eGLContext;
        EGLContext eGLContext2 = this.mDefaultContext;
        if (eGLContext2 != eGLContext) {
            if (!EGL14.eglDestroyContext(this.mEglDisplay, eGLContext2)) {
                String str3 = TAG;
                Log.e(str3, "destroyDefaultContext: display=" + this.mEglDisplay + " context=" + this.mDefaultContext);
                String str4 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("destroyDefaultContext: err=");
                sb2.append(EGL14.eglGetError());
                Log.e(str4, sb2.toString());
            }
            this.mDefaultContext = EGL14.EGL_NO_CONTEXT;
        }
    }

    /* access modifiers changed from: private */
    public void destroyWindowSurface(EGLSurface eGLSurface) {
        Log.v(TAG, "destroySurface>>>");
        EGLSurface eGLSurface2 = EGL14.EGL_NO_SURFACE;
        if (eGLSurface != eGLSurface2) {
            EGL14.eglMakeCurrent(this.mEglDisplay, eGLSurface2, eGLSurface2, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroySurface(this.mEglDisplay, eGLSurface);
        }
        EGLSurface eGLSurface3 = EGL14.EGL_NO_SURFACE;
        Log.v(TAG, "destroySurface<<<");
    }

    private EGLConfig getConfig(boolean z, boolean z2) {
        int[] iArr = {12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12344, 12344, 12344, 12344, 12344, 12344, 12344};
        int i = 10;
        if (z) {
            iArr[10] = 12325;
            i = 12;
            iArr[11] = 16;
        }
        if (z2 && Build.VERSION.SDK_INT >= 18) {
            int i2 = i + 1;
            iArr[i] = 12610;
            i = i2 + 1;
            iArr[i2] = 1;
        }
        for (int length = iArr.length - 1; length >= i; length--) {
            iArr[length] = 12344;
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (EGL14.eglChooseConfig(this.mEglDisplay, iArr, 0, eGLConfigArr, 0, eGLConfigArr.length, new int[1], 0)) {
            return eGLConfigArr[0];
        }
        Log.w(TAG, "unable to find RGBA8888 /  EGLConfig");
        return null;
    }

    private void init(EGLContext eGLContext, boolean z, boolean z2) {
        Log.v(TAG, "init");
        if (this.mEglDisplay == EGL14.EGL_NO_DISPLAY) {
            this.mEglDisplay = EGL14.eglGetDisplay(0);
            EGLDisplay eGLDisplay = this.mEglDisplay;
            if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
                int[] iArr = new int[2];
                if (EGL14.eglInitialize(eGLDisplay, iArr, 0, iArr, 1)) {
                    if (eGLContext == null) {
                        eGLContext = EGL14.EGL_NO_CONTEXT;
                    }
                    if (this.mEglContext == EGL14.EGL_NO_CONTEXT) {
                        this.mEglConfig = getConfig(z, z2);
                        if (this.mEglConfig != null) {
                            this.mEglContext = createContext(eGLContext);
                        } else {
                            throw new RuntimeException("chooseConfig failed");
                        }
                    }
                    int[] iArr2 = new int[1];
                    EGL14.eglQueryContext(this.mEglDisplay, this.mEglContext, 12440, iArr2, 0);
                    String str = TAG;
                    Log.d(str, "EGLContext created, client version " + iArr2[0]);
                    makeDefault();
                    return;
                }
                this.mEglDisplay = null;
                throw new RuntimeException("eglInitialize failed");
            }
            throw new RuntimeException("eglGetDisplay failed");
        }
        throw new RuntimeException("EGL already set up");
    }

    /* access modifiers changed from: private */
    public boolean makeCurrent(EGLSurface eGLSurface) {
        if (this.mEglDisplay == null) {
            Log.e(TAG, "makeCurrent: eglDisplay not initialized");
        }
        if (eGLSurface == null || eGLSurface == EGL14.EGL_NO_SURFACE) {
            if (EGL14.eglGetError() == 12299) {
                Log.e(TAG, "makeCurrent: returned EGL_BAD_NATIVE_WINDOW.");
            }
            return false;
        } else if (EGL14.eglMakeCurrent(this.mEglDisplay, eGLSurface, eGLSurface, this.mEglContext)) {
            return true;
        } else {
            String str = TAG;
            Log.e(str, "eglMakeCurrent: err=" + EGL14.eglGetError());
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void makeDefault() {
        Log.v(TAG, "makeDefault");
        EGLDisplay eGLDisplay = this.mEglDisplay;
        EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
        if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT)) {
            String str = TAG;
            Log.w(str, "makeDefault: err=" + EGL14.eglGetError());
        }
    }

    /* access modifiers changed from: private */
    public int swap(EGLSurface eGLSurface) {
        if (EGL14.eglSwapBuffers(this.mEglDisplay, eGLSurface)) {
            return 12288;
        }
        int eglGetError = EGL14.eglGetError();
        String str = TAG;
        Log.w(str, "swap: err=" + eglGetError);
        return eglGetError;
    }

    public EglSurface createFromSurface(Object obj) {
        Log.v(TAG, "createFromSurface");
        EglSurface eglSurface = new EglSurface(this, obj);
        eglSurface.makeCurrent();
        return eglSurface;
    }

    public EglSurface createOffscreen(int i, int i2) {
        Log.v(TAG, "createOffscreen");
        EglSurface eglSurface = new EglSurface(this, i, i2);
        eglSurface.makeCurrent();
        return eglSurface;
    }

    public EGLContext getContext() {
        return this.mEglContext;
    }

    public int querySurface(EGLSurface eGLSurface, int i) {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.mEglDisplay, eGLSurface, i, iArr, 0);
        return iArr[0];
    }

    public void release() {
        Log.v(TAG, "release");
        if (this.mEglDisplay != EGL14.EGL_NO_DISPLAY) {
            destroyContext();
            EGL14.eglTerminate(this.mEglDisplay);
            EGL14.eglReleaseThread();
        }
        this.mEglDisplay = EGL14.EGL_NO_DISPLAY;
        this.mEglContext = EGL14.EGL_NO_CONTEXT;
    }
}
