package com.android.camera.groupshot;

import android.media.Image;
import com.android.camera.log.Log;
import java.io.FileDescriptor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Locale;

public class GroupShot {
    private static final String TAG = "GroupShot";
    private int mHeight;
    private int mMaxImageNum;
    private long mNative = 0;
    private boolean mStart;
    private int mWidth;

    static {
        try {
            System.loadLibrary("morpho_groupshot");
        } catch (UnsatisfiedLinkError e2) {
            String str = TAG;
            Log.e(str, "can't loadLibrary, " + e2.getMessage());
        }
    }

    private final native int addTargetRect(long j, int[] iArr);

    private final native int attach(long j, byte[] bArr);

    private final native int attachYuv(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i, int i2);

    private final native int changeMaxNum(long j, int i, int i2);

    private final native int clearImages(long j);

    private final native long createNativeObject();

    private final native void deleteNativeObject(long j);

    private final native int end(long j);

    private static int getFD(FileDescriptor fileDescriptor) {
        try {
            Field declaredField = FileDescriptor.class.getDeclaredField("descriptor");
            declaredField.setAccessible(true);
            return declaredField.getInt(fileDescriptor);
        } catch (IllegalAccessException | NoSuchFieldException unused) {
            return -1;
        }
    }

    private final native int getImage(long j, byte[] bArr);

    private final native int getImageAndSaveJpeg(long j, String str, int i);

    private final native int getImageNum(long j);

    private final native int getJpegData(long j, int i, byte[] bArr);

    private final native int getJpegSize(long j, int i);

    private final native int getPreviewImage(long j, int i, int i2, int i3, byte[] bArr);

    private final native int getRawData(long j, int i, byte[] bArr);

    private final native int getRecommendedImageIndex(long j, int[] iArr, int i);

    private final native int getRecommendedRect(long j, int i, int i2, int[] iArr);

    private final native int getScaledImageAndSaveJpeg(long j, int i, int i2, String str);

    private final native int getScaledOutputImage(long j, int i, int i2, byte[] bArr);

    private final native int getTargetImage(long j, int i, int i2, int i3, int i4, byte[] bArr);

    private final native int getTargetNum(long j);

    private final native int getTargetRects(long j, int[] iArr);

    private final native int getYuvImage(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i, int i2);

    private final native int initializeNativeObject(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7);

    private final native int saveInputImages(long j, String str);

    private final native int selectTarget(long j, int i, int i2);

    private final native int setBaseImage(long j, int i);

    private final native int setBestFace(long j);

    private final native int start(long j, int i);

    private final native int updateTargetRect(long j, int i, int[] iArr);

    public int addTargetRect(int[] iArr) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return addTargetRect(j, iArr);
    }

    public int attach(Image image) {
        Log.v(TAG, String.format("GroupShot attach mNative=%x", Long.valueOf(this.mNative)));
        if (this.mNative == 0) {
            return -1;
        }
        if (35 == image.getFormat()) {
            Image.Plane[] planes = image.getPlanes();
            Log.d(TAG, String.format(Locale.ENGLISH, "attach: size=%dx%d stride=%dx%d", Integer.valueOf(image.getWidth()), Integer.valueOf(image.getHeight()), Integer.valueOf(planes[0].getRowStride()), Integer.valueOf(planes[1].getRowStride())));
            return attachYuv(this.mNative, planes[0].getBuffer(), planes[1].getBuffer(), planes[0].getRowStride(), planes[1].getRowStride());
        }
        String str = TAG;
        Log.e(str, "unexpected image format " + image.getFormat());
        return -1;
    }

    public int attach(byte[] bArr) {
        Log.v(TAG, String.format("GroupShot attach mNative=%x", Long.valueOf(this.mNative)));
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return attach(j, bArr);
    }

    public int attach_end() {
        Log.v(TAG, String.format("GroupShot attach end, mNative=%x", Long.valueOf(this.mNative)));
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return end(j);
    }

    public int attach_start(int i) {
        Log.v(TAG, String.format("GroupShot attach start mNative=%x", Long.valueOf(this.mNative)));
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        this.mStart = true;
        return start(j, i);
    }

    public int changeMaxNum(int i, int i2) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return changeMaxNum(j, i, i2);
    }

    public int clearImages() {
        Log.v(TAG, String.format("clearImages mNative=%x", Long.valueOf(this.mNative)));
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return clearImages(j);
    }

    public void finish() {
        long j = this.mNative;
        if (j != 0) {
            Log.v(TAG, String.format("finish mNative=%x", Long.valueOf(j)));
            deleteNativeObject(this.mNative);
            this.mWidth = 0;
            this.mHeight = 0;
            this.mMaxImageNum = 0;
            this.mStart = false;
            this.mNative = 0;
        }
    }

    public int getImageAndSaveJpeg(FileDescriptor fileDescriptor) {
        if (this.mNative == 0) {
            return -1;
        }
        int fd = getFD(fileDescriptor);
        Log.v(TAG, String.format("getImageAndSaveJpeg: mNative=%x fd=%s", Long.valueOf(this.mNative), Integer.valueOf(fd)));
        if (fd < 0) {
            return -1;
        }
        return getImageAndSaveJpeg(this.mNative, null, fd);
    }

    public int getImageAndSaveJpeg(String str) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        Log.v(TAG, String.format("getImageAndSaveJpeg: mNative=%x filename=%s", Long.valueOf(j), str));
        return getImageAndSaveJpeg(this.mNative, str, -1);
    }

    public int getImageData(int i, byte[] bArr) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return getRawData(j, i, bArr);
    }

    public int getJpegData(int i, byte[] bArr) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return getJpegData(j, i, bArr);
    }

    public int getJpegSize(int i) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return getJpegSize(j, i);
    }

    public int getPreviewImage(int i, int i2, int i3, byte[] bArr) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return getPreviewImage(j, i, i2, i3, bArr);
    }

    public int getRecommendedImageIndex(int[] iArr, int i) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return getRecommendedImageIndex(j, iArr, i);
    }

    public int getRecommendedRect(int i, int i2, int[] iArr) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return getRecommendedRect(j, i, i2, iArr);
    }

    public int getScaledImageAndSaveJpeg(int i, int i2, String str) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        Log.v(TAG, String.format("GroupShot getScaledImageAndSaveJpeg, mNative=%x filename=%s", Long.valueOf(j), str));
        return getScaledImageAndSaveJpeg(this.mNative, i, i2, str);
    }

    public int getScaledOutputImage(int i, int i2, byte[] bArr) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return getScaledOutputImage(j, i, i2, bArr);
    }

    public int getTargetImage(int i, int i2, int i3, int i4, byte[] bArr) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return getTargetImage(j, i, i2, i3, i4, bArr);
    }

    public int getTargetNum() {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return getTargetNum(j);
    }

    public int getTargetRects(int[] iArr) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return getTargetRects(j, iArr);
    }

    public final native String getVersion();

    public int getYuvImage(Image image) {
        if (image == null || 35 != image.getFormat()) {
            String str = TAG;
            Log.e(str, "getYuvImage: invalid image " + image);
            return -1;
        }
        Image.Plane[] planes = image.getPlanes();
        int yuvImage = getYuvImage(this.mNative, planes[0].getBuffer(), planes[1].getBuffer(), planes[0].getRowStride(), planes[1].getRowStride());
        String str2 = TAG;
        Log.d(str2, "getYuvImage: result=" + yuvImage);
        return yuvImage;
    }

    public int initialize(int i, int i2, int i3, int i4, int i5, int i6) {
        if (!this.mStart && this.mWidth == i3 && this.mHeight == i4 && this.mMaxImageNum == i) {
            return 0;
        }
        if (this.mStart && !(this.mWidth == 0 && this.mHeight == 0 && this.mMaxImageNum == 0)) {
            clearImages();
            finish();
        }
        if (this.mNative == 0) {
            this.mNative = createNativeObject();
            if (this.mNative == 0) {
                return -1;
            }
        }
        Log.v(TAG, String.format(Locale.ENGLISH, "initialize imageNum=%d, width=%d, height=%d, mStart=%b, mWidth=%d, mHeight=%d, mMaxImageNum=%d", Integer.valueOf(i), Integer.valueOf(i3), Integer.valueOf(i4), Boolean.valueOf(this.mStart), Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mMaxImageNum)));
        initializeNativeObject(this.mNative, i, i2, i3, i4, i5, i6, 0);
        this.mMaxImageNum = i;
        this.mWidth = i3;
        this.mHeight = i4;
        this.mStart = false;
        return 0;
    }

    public boolean isUsed() {
        return this.mStart;
    }

    public int saveInputImages(String str) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return saveInputImages(j, str);
    }

    public int selectImage(int i, int i2) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return selectTarget(j, i, i2);
    }

    public int setBaseImage(int i) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return setBaseImage(j, i);
    }

    public int setBestFace() {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return setBestFace(j);
    }

    public int updateTargetRect(int i, int[] iArr) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return updateTargetRect(j, i, iArr);
    }
}
