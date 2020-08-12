package com.xiaomi.media.imagecodec;

import android.media.Image;
import android.util.Log;
import android.view.Surface;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public final class ImageCodec {
    private static final String TAG = "ImageCodec";
    private final Object mContextLock = new Object();
    private ImageSpec mInputSpec;
    private long mNativeContext;
    private ImageSpec mOutputSpec;

    public static final class ImageSpec {
        public final int format;
        public final int height;
        public final int width;

        private ImageSpec(int i, int i2, int i3) {
            this.width = i;
            this.height = i2;
            this.format = i3;
        }

        public String toString() {
            return String.format(Locale.US, "ImageSpec(%d, %d, %d)", Integer.valueOf(this.width), Integer.valueOf(this.height), Integer.valueOf(this.format));
        }
    }

    static {
        System.loadLibrary("jni_imagecodec");
        nativeClassInit();
    }

    private ImageCodec(ImageSpec imageSpec) {
        String str = TAG;
        Log.d(str, "ctor(): E " + hashCode());
        this.mInputSpec = imageSpec;
        String str2 = TAG;
        Log.d(str2, "ctor(): X " + hashCode());
    }

    public static ImageCodec create(int i, int i2, int i3) {
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException("The image dimensions must be positive");
        } else if (i3 == 35 || i3 == 17) {
            return new ImageCodec(new ImageSpec(i, i2, i3));
        } else {
            throw new IllegalArgumentException("The image format must be YUV_420_888 or NV21");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0016, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0017, code lost:
        android.util.Log.w(com.xiaomi.media.imagecodec.ImageCodec.TAG, "Failed to get the owner of the given image", r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0022, code lost:
        return false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:4:0x0016 A[ExcHandler: IllegalAccessException | NoSuchMethodException | InvocationTargetException (r4v1 'e' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:1:0x0007] */
    private Object getImageOwner(Image image) {
        try {
            Method declaredMethod = Image.class.getDeclaredMethod("getOwner", new Class[0]);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(image, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e2) {
        }
    }

    private boolean isImageValid(Image image) {
        try {
            Field declaredField = Image.class.getDeclaredField("mIsImageValid");
            declaredField.setAccessible(true);
            return declaredField.getBoolean(image);
        } catch (IllegalAccessException | NoSuchFieldException e2) {
            Log.w(TAG, "Failed to check if the given image is valid ot not", e2);
            return false;
        }
    }

    private static native void nativeClassInit();

    private native long nativeCreate(Object obj, int i, int i2, int i3, Surface surface);

    private native void nativeDestroy(long j);

    private native void nativeDrainInputImage(long j, Image image);

    private native void nativeGetOutputSpec(long j, ImageSpec imageSpec);

    private native void nativeSetCodecQuality(long j, int i);

    private native void nativeSetFlipFlag(long j, int i);

    private static void postEventFromNative(Object obj) {
        Log.d(TAG, "postEventFromNative(): E");
        if (((ImageCodec) ((WeakReference) obj).get()) != null) {
            Log.d(TAG, "postEventFromNative(): X");
        }
    }

    public void drainInputImage(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("The input image must not be null");
        } else if (image.getWidth() != this.mInputSpec.width || image.getHeight() != this.mInputSpec.height) {
            throw new IllegalArgumentException("Invalid input image dimensions: " + image.getWidth() + "x" + image.getHeight());
        } else if (image.getFormat() != this.mInputSpec.format) {
            throw new IllegalArgumentException("Invalid input image format: " + image.getFormat());
        } else if (!image.getClass().getName().equals("android.media.ImageReader$SurfaceImage")) {
            throw new IllegalArgumentException("Only images from ImageReader can be fed to ImageCodec, other image source is not supported yet!");
        } else if (isImageValid(image)) {
            synchronized (this.mContextLock) {
                try {
                    if (this.mNativeContext != 0) {
                        nativeDrainInputImage(this.mNativeContext, image);
                    }
                    image.close();
                } catch (Throwable th) {
                    image.close();
                    throw th;
                }
            }
        } else {
            throw new IllegalStateException("Image is already closed");
        }
    }

    public ImageSpec getInputSpec() {
        return this.mInputSpec;
    }

    public ImageSpec getOutputSpec() {
        synchronized (this.mContextLock) {
            if (this.mOutputSpec == null) {
                if (this.mNativeContext != 0) {
                    this.mOutputSpec = new ImageSpec(0, 0, 0);
                    nativeGetOutputSpec(this.mNativeContext, this.mOutputSpec);
                } else {
                    throw new IllegalArgumentException("ImageCodec is not initialized");
                }
            }
        }
        return this.mOutputSpec;
    }

    public void release() {
        String str = TAG;
        Log.d(str, "release(): E " + hashCode());
        synchronized (this.mContextLock) {
            nativeDestroy(this.mNativeContext);
            this.mNativeContext = 0;
        }
        String str2 = TAG;
        Log.d(str2, "release(): X " + hashCode());
    }

    public void setFlip(boolean z) {
        synchronized (this.mContextLock) {
            if (this.mNativeContext != 0) {
                nativeSetFlipFlag(this.mNativeContext, z ? 1 : 0);
            } else {
                throw new IllegalArgumentException("ImageCodec is not initialized");
            }
        }
    }

    public void setOutputSurface(Surface surface) {
        if (surface != null) {
            WeakReference weakReference = new WeakReference(this);
            ImageSpec imageSpec = this.mInputSpec;
            this.mNativeContext = nativeCreate(weakReference, imageSpec.width, imageSpec.height, imageSpec.format, surface);
            return;
        }
        throw new IllegalArgumentException("The given output surface must not be null");
    }

    public void setQuality(int i) {
        if (i < 0 || i > 100) {
            throw new IllegalArgumentException("quality must be 0..100");
        }
        synchronized (this.mContextLock) {
            if (this.mNativeContext != 0) {
                nativeSetCodecQuality(this.mNativeContext, i);
            } else {
                throw new IllegalArgumentException("ImageCodec is not initialized");
            }
        }
    }
}
