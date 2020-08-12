package com.ss.android.ttve.nativePort;

import android.support.annotation.Keep;
import com.ss.android.ttve.common.TECommonCallback;
import com.ss.android.ttve.nativePort.NativeCallbacks;

@Keep
public class TENativeServiceBase {
    protected NativeCallbacks.IEncoderDataCallback mEncoderDataCallback = null;
    protected NativeCallbacks.IGetImageCallback mGetImageCallback = null;
    protected TECommonCallback mOnErrorListener = null;
    protected TECommonCallback mOnInfoListener = null;
    protected NativeCallbacks.IOpenGLCallback mOpenGLCallback = null;

    public NativeCallbacks.IEncoderDataCallback getEncoderDataListener() {
        return this.mEncoderDataCallback;
    }

    public TECommonCallback getErrorListener() {
        return this.mOnErrorListener;
    }

    public TECommonCallback getInfoListener() {
        return this.mOnInfoListener;
    }

    public NativeCallbacks.IOpenGLCallback getOpenGLListeners() {
        return this.mOpenGLCallback;
    }

    public void nativeCallback_onCompressBuffer(byte[] bArr, int i, int i2, boolean z) {
        NativeCallbacks.IEncoderDataCallback iEncoderDataCallback = this.mEncoderDataCallback;
        if (iEncoderDataCallback != null) {
            iEncoderDataCallback.onCompressBuffer(bArr, i, i2, z);
        }
    }

    public void nativeCallback_onErrorListener(int i, int i2, float f2, String str) {
        TECommonCallback tECommonCallback = this.mOnErrorListener;
        if (tECommonCallback != null) {
            tECommonCallback.onCallback(i, i2, f2, str);
        }
    }

    public int nativeCallback_onImageData(byte[] bArr, int i, int i2, int i3) {
        NativeCallbacks.IGetImageCallback iGetImageCallback = this.mGetImageCallback;
        if (iGetImageCallback != null) {
            return iGetImageCallback.onImageData(bArr, i, i2, i3);
        }
        return 0;
    }

    public void nativeCallback_onInfoListener(int i, int i2, float f2) {
        TECommonCallback tECommonCallback = this.mOnInfoListener;
        if (tECommonCallback != null) {
            tECommonCallback.onCallback(i, i2, f2, null);
        }
    }

    public void nativeCallback_onOpenGLCreate(int i) {
        NativeCallbacks.IOpenGLCallback iOpenGLCallback = this.mOpenGLCallback;
        if (iOpenGLCallback != null) {
            iOpenGLCallback.onOpenGLCreate(i);
        }
    }

    public void nativeCallback_onOpenGLDestroy(int i) {
        NativeCallbacks.IOpenGLCallback iOpenGLCallback = this.mOpenGLCallback;
        if (iOpenGLCallback != null) {
            iOpenGLCallback.onOpenGLDestroy(i);
        }
    }

    public void nativeCallback_onOpenGLDrawAfter(int i, double d2) {
        NativeCallbacks.IOpenGLCallback iOpenGLCallback = this.mOpenGLCallback;
        if (iOpenGLCallback != null) {
            iOpenGLCallback.onOpenGLDrawAfter(i, d2);
        }
    }

    public void nativeCallback_onOpenGLDrawBefore(int i, double d2) {
        NativeCallbacks.IOpenGLCallback iOpenGLCallback = this.mOpenGLCallback;
        if (iOpenGLCallback != null) {
            iOpenGLCallback.onOpenGLDrawBefore(i, d2);
        }
    }

    public void nativeCallback_onPreviewSurface(int i) {
        NativeCallbacks.IOpenGLCallback iOpenGLCallback = this.mOpenGLCallback;
        if (iOpenGLCallback != null) {
            iOpenGLCallback.onPreviewSurface(i);
        }
    }

    public void setEncoderDataListener(NativeCallbacks.IEncoderDataCallback iEncoderDataCallback) {
        this.mEncoderDataCallback = iEncoderDataCallback;
    }

    public void setErrorListener(TECommonCallback tECommonCallback) {
        this.mOnErrorListener = tECommonCallback;
    }

    public void setGetImageCallback(NativeCallbacks.IGetImageCallback iGetImageCallback) {
        this.mGetImageCallback = iGetImageCallback;
    }

    public void setInfoListener(TECommonCallback tECommonCallback) {
        this.mOnInfoListener = tECommonCallback;
    }

    public void setOpenGLListeners(NativeCallbacks.IOpenGLCallback iOpenGLCallback) {
        this.mOpenGLCallback = iOpenGLCallback;
    }
}
