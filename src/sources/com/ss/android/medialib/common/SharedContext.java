package com.ss.android.medialib.common;

import android.annotation.SuppressLint;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.os.Build;
import com.ss.android.vesdk.VELogUtil;

@SuppressLint({"InlinedApi"})
public class SharedContext {
    public static final int EGL_RECORDABLE_ANDROID = 12610;
    public static final String LOG_TAG = "SharedContext";
    private static int mBitsA = 8;
    private static int mBitsB = 8;
    private static int mBitsG = 8;
    private static int mBitsR = 8;
    private static int mGLVersion = 3;
    private EGLConfig mConfig;
    private EGLContext mContext;
    private EGLDisplay mDisplay;
    private volatile boolean mInitialized = false;
    private EGLSurface mSurface;

    SharedContext() {
    }

    private void checkEglError(String str) {
        int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            String str2 = LOG_TAG;
            VELogUtil.e(str2, str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }

    public static SharedContext create() {
        return create(EGL14.EGL_NO_CONTEXT, 64, 64, 1, null);
    }

    public static SharedContext create(int i, int i2) {
        return create(EGL14.EGL_NO_CONTEXT, i, i2, 1, null);
    }

    public static SharedContext create(EGLContext eGLContext, int i, int i2) {
        return create(eGLContext, i, i2, 1, null);
    }

    public static SharedContext create(EGLContext eGLContext, int i, int i2, int i3, Object obj) {
        SharedContext sharedContext = new SharedContext();
        if (sharedContext.initEGL(eGLContext, i, i2, i3, obj)) {
            return sharedContext;
        }
        sharedContext.release();
        return null;
    }

    public static SharedContext create(EGLContext eGLContext, int i, Object obj) {
        return create(eGLContext, 64, 64, i, obj);
    }

    private boolean initEGL(EGLContext eGLContext, int i, int i2, int i3, Object obj) {
        int[] iArr;
        EGLContext eGLContext2 = eGLContext == null ? EGL14.EGL_NO_CONTEXT : eGLContext;
        int[] iArr2 = {12440, mGLVersion, 12344};
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        int[] iArr3 = new int[2];
        int[] iArr4 = {12375, i, 12374, i2, 12344};
        EGLDisplay eglGetDisplay = EGL14.eglGetDisplay(0);
        this.mDisplay = eglGetDisplay;
        if (eglGetDisplay == EGL14.EGL_NO_DISPLAY) {
            VELogUtil.e(LOG_TAG, String.format("eglGetDisplay() returned error 0x%x", Integer.valueOf(EGL14.eglGetError())));
            return false;
        } else if (!EGL14.eglInitialize(this.mDisplay, iArr3, 0, iArr3, 1)) {
            VELogUtil.e(LOG_TAG, String.format("eglInitialize() returned error 0x%x", Integer.valueOf(EGL14.eglGetError())));
            return false;
        } else {
            int[] iArr5 = {12352, (mGLVersion < 3 || iArr3[1] < 4) ? 4 : 64, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12339, i3, 12344};
            if (i3 == 12610) {
                iArr5[iArr5.length - 3] = 12610;
                iArr5[iArr5.length - 2] = 1;
            }
            VELogUtil.i(LOG_TAG, String.format("eglInitialize - major: %d, minor: %d", Integer.valueOf(iArr3[0]), Integer.valueOf(iArr3[1])));
            int[] iArr6 = new int[1];
            if (!EGL14.eglChooseConfig(this.mDisplay, iArr5, 0, eGLConfigArr, 0, eGLConfigArr.length, iArr6, 0)) {
                VELogUtil.e(LOG_TAG, String.format("eglChooseConfig() returned error 0x%x", Integer.valueOf(EGL14.eglGetError())));
                return false;
            }
            String str = LOG_TAG;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(iArr6[0]);
            objArr[1] = eGLContext2 == EGL14.EGL_NO_CONTEXT ? "NO" : "YES";
            VELogUtil.i(str, String.format("Config num: %d, has sharedContext: %s", objArr));
            this.mConfig = eGLConfigArr[0];
            this.mContext = EGL14.eglCreateContext(this.mDisplay, this.mConfig, eGLContext2, iArr2, 0);
            if (this.mContext == EGL14.EGL_NO_CONTEXT) {
                VELogUtil.e(LOG_TAG, "eglCreateContext Failed!");
                return false;
            }
            if (i3 != 2) {
                if (i3 == 4) {
                    iArr = iArr4;
                } else if (i3 != 12610) {
                    this.mSurface = EGL14.eglCreatePbufferSurface(this.mDisplay, this.mConfig, iArr4, 0);
                } else {
                    iArr = iArr4;
                    iArr[0] = 12344;
                    iArr[2] = 12344;
                }
                this.mSurface = EGL14.eglCreateWindowSurface(this.mDisplay, this.mConfig, obj, iArr, 0);
            } else {
                this.mSurface = EGL14.eglCreatePixmapSurface(this.mDisplay, this.mConfig, 0, iArr4, 0);
            }
            EGLSurface eGLSurface = this.mSurface;
            if (eGLSurface == EGL14.EGL_NO_SURFACE) {
                checkEglError("eglCreateSurface");
                VELogUtil.e(LOG_TAG, "eglCreateSurface Failed!");
                return false;
            } else if (!EGL14.eglMakeCurrent(this.mDisplay, eGLSurface, eGLSurface, this.mContext)) {
                String str2 = LOG_TAG;
                VELogUtil.e(str2, "eglMakeCurrent failed:" + EGL14.eglGetError());
                return false;
            } else {
                int[] iArr7 = new int[1];
                EGL14.eglQueryContext(this.mDisplay, this.mContext, 12440, iArr7, 0);
                String str3 = LOG_TAG;
                VELogUtil.i(str3, "EGLContext created, client version " + iArr7[0]);
                this.mInitialized = true;
                return true;
            }
        }
    }

    public static void setContextColorBits(int i, int i2, int i3, int i4) {
        mBitsR = i;
        mBitsG = i2;
        mBitsB = i3;
        mBitsA = i4;
    }

    public static void setGLVersion(int i) {
        mGLVersion = i;
    }

    public String getClientVersion() {
        if (!isInitialized()) {
            return "Not initialized";
        }
        int[] iArr = new int[1];
        EGL14.eglQueryContext(this.mDisplay, this.mContext, 12440, iArr, 0);
        String str = LOG_TAG;
        VELogUtil.i(str, "EGLContext created, client version " + iArr[0]);
        return "";
    }

    public String getConfigAttrib(int i) {
        EGLConfig eGLConfig;
        if (!isInitialized() || (eGLConfig = this.mConfig) == null) {
            return "Not initialized";
        }
        int[] iArr = new int[1];
        EGL14.eglGetConfigAttrib(this.mDisplay, eGLConfig, i, iArr, 0);
        return String.valueOf(iArr[0]);
    }

    public EGLContext getContext() {
        return this.mContext;
    }

    public EGLDisplay getDisplay() {
        return this.mDisplay;
    }

    public EGLSurface getSurface() {
        return this.mSurface;
    }

    public boolean isInitialized() {
        return (!this.mInitialized || this.mContext == EGL14.EGL_NO_CONTEXT || this.mDisplay == EGL14.EGL_NO_DISPLAY) ? false : true;
    }

    public void makeCurrent() {
        EGLDisplay eGLDisplay = this.mDisplay;
        EGLSurface eGLSurface = this.mSurface;
        if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.mContext)) {
            String str = LOG_TAG;
            VELogUtil.e(str, "eglMakeCurrent failed:" + EGL14.eglGetError());
        }
    }

    public String queryString(int i) {
        return !isInitialized() ? "Not initialized" : Build.VERSION.SDK_INT >= 17 ? EGL14.eglQueryString(this.mDisplay, i) : "";
    }

    public void release() {
        VELogUtil.i(LOG_TAG, "#### CGESharedGLContext Destroying context... ####");
        EGLDisplay eGLDisplay = this.mDisplay;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroyContext(this.mDisplay, this.mContext);
            EGL14.eglDestroySurface(this.mDisplay, this.mSurface);
            EGL14.eglTerminate(this.mDisplay);
        }
        this.mDisplay = EGL14.EGL_NO_DISPLAY;
        this.mSurface = EGL14.EGL_NO_SURFACE;
        this.mContext = EGL14.EGL_NO_CONTEXT;
        this.mInitialized = false;
    }

    public boolean swapBuffers() {
        return EGL14.eglSwapBuffers(this.mDisplay, this.mSurface);
    }
}
