package com.ss.android.ttve.nativePort;

public class NativeCallbacks {

    public interface IEncoderDataCallback {
        int onCompressBuffer(byte[] bArr, int i, int i2, boolean z);
    }

    public interface IEncoderInitCallback {
        int onEncoderCreate(int i);
    }

    public interface IGetImageCallback {
        int onImageData(byte[] bArr, int i, int i2, int i3);
    }

    public interface INativeCreateCallback {
        int onNativeCreate(int i);
    }

    public interface IOpenGLCallback {
        int onOpenGLCreate(int i);

        int onOpenGLDestroy(int i);

        int onOpenGLDrawAfter(int i, double d2);

        int onOpenGLDrawBefore(int i, double d2);

        int onPreviewSurface(int i);
    }
}
