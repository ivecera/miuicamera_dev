package com.android.camera.panorama;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.Image;
import android.text.format.DateFormat;
import com.android.camera.log.Log;
import com.android.camera.panorama.MorphoSensorFusion;
import java.io.FileDescriptor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Locale;

public class MorphoPanoramaGP3 {
    public static final int DIRECTION_AUTO = 0;
    public static final int DIRECTION_HORIZONTAL = 2;
    public static final int DIRECTION_HORIZONTAL_LEFT = 5;
    public static final int DIRECTION_HORIZONTAL_RIGHT = 6;
    public static final int DIRECTION_INVALID = -1;
    public static final int DIRECTION_VERTICAL = 1;
    public static final int DIRECTION_VERTICAL_DOWN = 4;
    public static final int DIRECTION_VERTICAL_UP = 3;
    public static final int END_MODE_CANCEL = 2;
    public static final int END_MODE_MAKE_360 = 0;
    public static final int END_MODE_NOT_MAKE_360 = 1;
    public static final int END_STATUS_CANCEL = 2;
    public static final int END_STATUS_MAKE_360 = 0;
    public static final int END_STATUS_NOT_MAKE_360 = 1;
    public static final int ERROR_INVALID_DIR = -1073741823;
    public static final int FAST_SPEED_THRESHOLD = 4500;
    private static double GAIN_COEF = 1.0d;
    public static final int MODE_PANORAMA = 0;
    public static final int MODE_SCANNER = 1;
    private static final int POINT_INFO_SIZE = 2;
    private static final int POINT_X_OFFSET = 0;
    private static final int POINT_Y_OFFSET = 1;
    private static final int RECT_BOTTOM_OFFSET = 3;
    private static final int RECT_INFO_SIZE = 4;
    private static final int RECT_LEFT_OFFSET = 0;
    private static final int RECT_RIGHT_OFFSET = 2;
    private static final int RECT_TOP_OFFSET = 1;
    public static final String TAG = "MorphoPanoramaGP3";
    private static final boolean USE_STANDARD_DEVIATION = true;
    private long mAttachCount;
    private boolean mAttachEnabled;
    private long mAttachFirstNanoTime;
    private long mAttachLastNanoTime;
    private String mFolderPathInputImages;
    private final GravityParam mGravity;
    private int[] mIndexBase;
    private String mInputImageFormat;
    private ArrayList<Long> mIntervalArray;
    private long mNative;
    private long mNativeOutputInfo;
    private boolean mSaveInputImages;

    public static class GalleryInfoData {
        public int crop_height;
        public int crop_left;
        public int crop_top;
        public int crop_width;
        public int whole_height;
        public int whole_width;

        public String toString() {
            return "GalleryInfoData{whole_width=" + this.whole_width + ", whole_height=" + this.whole_height + ", crop_left=" + this.crop_left + ", crop_top=" + this.crop_top + ", crop_width=" + this.crop_width + ", crop_height=" + this.crop_height + '}';
        }
    }

    public static class GravityParam {
        public double x = 0.0d;
        public double y = 0.0d;
        public double z = 0.0d;

        public GravityParam copyInstance() {
            GravityParam gravityParam = new GravityParam();
            gravityParam.x = this.x;
            gravityParam.y = this.y;
            gravityParam.z = this.z;
            return gravityParam;
        }
    }

    public static class InitParam {
        public double aovx;
        public double aovy;
        public int direction;
        public double goal_angle;
        public String input_format;
        public int input_height;
        public int input_width;
        public String output_format;
        public int output_rotation;
    }

    static {
        try {
            System.loadLibrary("morpho_panorama_gp3");
        } catch (UnsatisfiedLinkError e2) {
            Log.e(TAG, "can't loadLibrary, " + e2.getMessage());
        }
    }

    public MorphoPanoramaGP3() {
        this.mNative = 0;
        this.mNativeOutputInfo = 0;
        this.mSaveInputImages = false;
        this.mIntervalArray = null;
        this.mAttachEnabled = false;
        this.mGravity = new GravityParam();
        this.mIndexBase = new int[4];
        this.mAttachFirstNanoTime = 0;
        this.mAttachLastNanoTime = 0;
        this.mIntervalArray = new ArrayList<>();
    }

    private static String createName(long j) {
        return DateFormat.format("yyyy-MM-dd_kk-mm-ss", j).toString();
    }

    private native long createNativeObject();

    private native long createNativeOutputInfoObject();

    private native void deleteNativeObject(long j);

    private native void deleteNativeOutputInfoObject(long j);

    private static int getFD(FileDescriptor fileDescriptor) {
        try {
            Field declaredField = FileDescriptor.class.getDeclaredField("descriptor");
            declaredField.setAccessible(true);
            return declaredField.getInt(fileDescriptor);
        } catch (IllegalAccessException | NoSuchFieldException unused) {
            return -1;
        }
    }

    public static double getGain(Image image) {
        double[] dArr = new double[1];
        int nativeGetGain = nativeGetGain(image.getPlanes()[0].getBuffer(), image.getPlanes()[1].getBuffer(), image.getPlanes()[2].getBuffer(), image.getPlanes()[0].getRowStride(), image.getPlanes()[1].getRowStride(), image.getPlanes()[2].getRowStride(), image.getPlanes()[0].getPixelStride(), image.getPlanes()[1].getPixelStride(), image.getPlanes()[2].getPixelStride(), PanoramaGP3ImageFormat.getImageFormat(image), image.getWidth(), image.getHeight(), dArr);
        if (nativeGetGain != 0) {
            Log.e(TAG, "nativeGetGain error. ret=" + nativeGetGain);
            return 1.0d;
        }
        dArr[0] = GAIN_COEF * dArr[0];
        return dArr[0];
    }

    public static String getVersion() {
        return nativeGetVersion();
    }

    private native int nativeAttach(long j, byte[] bArr);

    private native int nativeAttachYuv(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6, String str, double[] dArr);

    private native int nativeCreateOutputImage(long j, int i, int i2, int i3, int i4);

    private native int nativeEnd(long j, int i, double d2);

    private native int nativeFinish(long j);

    private native int nativeGetClippingRect(long j, int[] iArr);

    private native int nativeGetDirection(long j, int[] iArr);

    private native int nativeGetEndStatus(long j);

    private static native int nativeGetGain(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6, String str, int i7, int i8, double[] dArr);

    private native int nativeGetImage(long j, byte[] bArr, int i, int i2, int i3, int i4);

    private native int nativeGetOutputImage(long j, byte[] bArr, int i, int i2, int i3, int i4);

    private native int nativeGetOutputSize(long j, int[] iArr);

    private native int nativeGetRotatedSmallImage(long j, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6);

    private static native String nativeGetVersion();

    private native int nativeInitialize(long j, InitParam initParam, long j2);

    private native int nativePreparePanorama360(long j, int i, int i2, String str, String str2, boolean z, GalleryInfoData galleryInfoData);

    private static native int nativeRenderByteArray(byte[] bArr, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6, String str, int i7, int i8);

    private static native int nativeRenderByteArrayRaw(byte[] bArr, byte[] bArr2, String str, int i, int i2);

    private static native int nativeRenderByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, ByteBuffer byteBuffer4, int i, int i2, int i3, int i4, int i5, int i6, String str, int i7, int i8);

    private static native int nativeRenderByteBufferRaw(ByteBuffer byteBuffer, byte[] bArr, String str, int i, int i2);

    private native int nativeSaveLog(long j, String str, String str2);

    private static native int nativeSaveNotPanorama(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, ByteBuffer byteBuffer4, int i, int i2, int i3, int i4, int i5, int i6, String str, int i7, int i8, String str2, int i9);

    private native int nativeSavePanorama360(long j, int i, int i2, String str, int i3, int i4, String str2, String str3, boolean z, GalleryInfoData galleryInfoData, boolean z2);

    private native int nativeSavePanorama360Delay(long j, String str, int i, boolean z, int i2, boolean z2);

    private native int nativeSaveYuv(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6, String str);

    private native int nativeSetAovGain(long j, double d2);

    private native int nativeSetCalcseamPixnum(long j, int i);

    private native int nativeSetDistortionCorrectionParam(long j, double d2, double d3, double d4, double d5);

    private native int nativeSetDrawThreshold(long j, double d2);

    private native int nativeSetGyroscopeData(long j, MorphoSensorFusion.SensorData[] sensorDataArr);

    private native int nativeSetImageFormat(long j, String str);

    private native int nativeSetInitialRotationByGravity(long j, double d2, double d3, double d4);

    private native int nativeSetMotionDetectionMode(long j, int i);

    private native int nativeSetNoiseReductionParam(long j, int i);

    private native int nativeSetPreviewImage(long j, int i, int i2);

    private native int nativeSetProjectionMode(long j, int i);

    private native int nativeSetRotationRatio(long j, double d2);

    private native int nativeSetRotationVector(long j, double[] dArr);

    private native int nativeSetSeamsearchRatio(long j, double d2);

    private native int nativeSetSensorUseMode(long j, int i);

    private native int nativeSetShrinkRatio(long j, double d2);

    private native int nativeSetUnsharpStrength(long j, int i);

    private native int nativeSetUseDeform(long j, int i);

    private native int nativeSetUseLuminanceCorrection(long j, int i);

    private native int nativeSetZrotationCoeff(long j, double d2);

    private native int nativeStart(long j, int i, int i2);

    private native int nativeUpdatePreviewImage(long j, Bitmap bitmap);

    private static native int nativeYuv2Bitmap8888(byte[] bArr, int i, int i2, Bitmap bitmap, int i3, int i4);

    public static int saveNotPanorama(CaptureImage captureImage, FileDescriptor fileDescriptor) {
        int fd = getFD(fileDescriptor);
        if (fd >= 0) {
            return saveNotPanorama_sub(captureImage, null, fd);
        }
        return -2147483632;
    }

    public static int saveNotPanorama(CaptureImage captureImage, String str) {
        return saveNotPanorama_sub(captureImage, str, -1);
    }

    public static int saveNotPanorama_sub(CaptureImage captureImage, String str, int i) {
        int i2;
        Image image = captureImage.image();
        if (image != null) {
            i2 = nativeSaveNotPanorama(null, image.getPlanes()[0].getBuffer(), image.getPlanes()[1].getBuffer(), image.getPlanes()[2].getBuffer(), image.getPlanes()[0].getRowStride(), image.getPlanes()[1].getRowStride(), image.getPlanes()[2].getRowStride(), image.getPlanes()[0].getPixelStride(), image.getPlanes()[1].getPixelStride(), image.getPlanes()[2].getPixelStride(), captureImage.getImageFormat(), captureImage.getWidth(), captureImage.getHeight(), str, i);
        } else {
            Camera1Image camera1Image = (Camera1Image) captureImage;
            String imageFormat = camera1Image.getImageFormat();
            int width = camera1Image.getWidth();
            int height = camera1Image.getHeight();
            byte[] raw = camera1Image.raw();
            ByteBuffer allocateBuffer = NativeMemoryAllocator.allocateBuffer(raw.length);
            allocateBuffer.put(raw);
            allocateBuffer.clear();
            int nativeSaveNotPanorama = nativeSaveNotPanorama(allocateBuffer, null, null, null, 0, 0, 0, 0, 0, 0, imageFormat, width, height, str, i);
            NativeMemoryAllocator.freeBuffer(allocateBuffer);
            i2 = nativeSaveNotPanorama;
        }
        if (i2 != 0) {
            Log.e(TAG, "nativeSaveNotPanorama error. ret=" + i2);
        }
        return i2;
    }

    public static int yuv2Bitmap8888(byte[] bArr, int i, int i2, Bitmap bitmap, int i3) {
        return nativeYuv2Bitmap8888(bArr, i, i2, bitmap, i3, 1);
    }

    public static int yvu2Bitmap8888(byte[] bArr, int i, int i2, Bitmap bitmap, int i3) {
        return nativeYuv2Bitmap8888(bArr, i, i2, bitmap, i3, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0110  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x015a  */
    public int attach(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6, SensorInfoManager sensorInfoManager, double[] dArr, Context context) {
        String str;
        long j;
        int i7;
        int i8;
        if (this.mNative == 0) {
            return -2147483646;
        }
        if (this.mAttachCount == 0) {
            this.mAttachFirstNanoTime = System.nanoTime();
        }
        long nanoTime = System.nanoTime();
        String str2 = null;
        if (this.mSaveInputImages) {
            if (sensorInfoManager != null) {
                String createName = createName(sensorInfoManager.timeMillis);
                str2 = String.format(Locale.US, "%s/%06d_", this.mFolderPathInputImages, Long.valueOf(this.mAttachCount)) + createName + String.format(Locale.US, "_sg%05d_sr%05d_sa%05d.yuv", Integer.valueOf(sensorInfoManager.g_ix + this.mIndexBase[0]), Integer.valueOf(sensorInfoManager.r_ix + this.mIndexBase[3]), Integer.valueOf(sensorInfoManager.a_ix + this.mIndexBase[1]));
                int[] iArr = this.mIndexBase;
                iArr[0] = iArr[0] + sensorInfoManager.sensorData[0].size();
                int[] iArr2 = this.mIndexBase;
                iArr2[3] = iArr2[3] + sensorInfoManager.sensorData[3].size();
                int[] iArr3 = this.mIndexBase;
                iArr3[1] = iArr3[1] + sensorInfoManager.sensorData[1].size();
            } else {
                String createName2 = createName(System.currentTimeMillis());
                str = String.format(Locale.US, "%s/%06d_", this.mFolderPathInputImages, Long.valueOf(this.mAttachCount)) + createName2 + ".yuv";
                if (!this.mAttachEnabled) {
                    j = nanoTime;
                    i7 = 1;
                    i8 = nativeAttachYuv(this.mNative, byteBuffer, byteBuffer2, byteBuffer3, i, i2, i3, i4, i5, i6, str, dArr);
                } else {
                    j = nanoTime;
                    i7 = 1;
                    i8 = str != null ? nativeSaveYuv(this.mNative, byteBuffer, byteBuffer2, byteBuffer3, i, i2, i3, i4, i5, i6, str) : 0;
                }
                long nanoTime2 = System.nanoTime();
                Locale locale = Locale.US;
                Object[] objArr = new Object[i7];
                objArr[0] = Long.valueOf(nanoTime2 - j);
                Log.v(TAG, String.format(locale, "Performance.JNI %1$,3d nsec", objArr));
                this.mAttachCount++;
                if (this.mAttachCount > 1) {
                    this.mIntervalArray.add(Long.valueOf(nanoTime2 - this.mAttachLastNanoTime));
                }
                this.mAttachLastNanoTime = nanoTime2;
                return i8;
            }
        }
        str = str2;
        if (!this.mAttachEnabled) {
        }
        long nanoTime22 = System.nanoTime();
        Locale locale2 = Locale.US;
        Object[] objArr2 = new Object[i7];
        objArr2[0] = Long.valueOf(nanoTime22 - j);
        Log.v(TAG, String.format(locale2, "Performance.JNI %1$,3d nsec", objArr2));
        this.mAttachCount++;
        if (this.mAttachCount > 1) {
        }
        this.mAttachLastNanoTime = nanoTime22;
        return i8;
    }

    public int attach(byte[] bArr) {
        long j = this.mNative;
        if (j == 0) {
            return -2147483646;
        }
        int nativeAttach = nativeAttach(j, bArr);
        if (this.mSaveInputImages) {
            String.format(Locale.US, "%06d", Long.valueOf(this.mAttachCount));
        }
        this.mAttachCount++;
        return nativeAttach;
    }

    public int createNativeOutputInfo() {
        this.mNativeOutputInfo = createNativeOutputInfoObject();
        return this.mNativeOutputInfo != 0 ? 0 : -2147483646;
    }

    public int createOutputImage(Rect rect) {
        long j = this.mNative;
        if (j != 0) {
            return nativeCreateOutputImage(j, rect.left, rect.top, rect.right, rect.bottom);
        }
        return -2147483646;
    }

    public int deleteNativeOutputInfo() {
        deleteNativeOutputInfoObject(this.mNativeOutputInfo);
        this.mNativeOutputInfo = 0;
        return 0;
    }

    public int deleteObject() {
        long j = this.mNative;
        if (j == 0) {
            return 0;
        }
        deleteNativeObject(j);
        this.mNative = 0;
        return 0;
    }

    public void disableSaveInputImages() {
        this.mSaveInputImages = false;
    }

    public void enableSaveInputImages(String str) {
        this.mSaveInputImages = true;
        this.mFolderPathInputImages = str;
    }

    public int end(int i, double d2) {
        long j = this.mNative;
        if (j != 0) {
            return nativeEnd(j, i, d2);
        }
        return 0;
    }

    public int finish(boolean z) {
        long j = this.mNative;
        if (j == 0) {
            return -2147483646;
        }
        int nativeFinish = nativeFinish(j);
        if (!z) {
            return nativeFinish;
        }
        deleteNativeObject(this.mNative);
        this.mNative = 0;
        return nativeFinish;
    }

    public long getAttachCount() {
        return this.mAttachCount;
    }

    public int getClippingRect(Rect rect) {
        int i;
        int[] iArr = new int[4];
        long j = this.mNative;
        if (j != 0) {
            i = nativeGetClippingRect(j, iArr);
            if (i == 0) {
                rect.set(iArr[0], iArr[1], iArr[2], iArr[3]);
            }
        } else {
            i = -2147483646;
        }
        if (i != 0) {
            rect.set(0, 0, 0, 0);
        }
        return i;
    }

    public int getDirection() {
        int nativeGetDirection;
        int[] iArr = {-1};
        long j = this.mNative;
        if (!(j == 0 || (nativeGetDirection = nativeGetDirection(j, iArr)) == 0)) {
            Log.e(TAG, "MorphoPanoramaGP3.getDirection error. ret=" + nativeGetDirection);
        }
        return iArr[0];
    }

    public int getEndStatus() {
        long j = this.mNative;
        if (j != 0) {
            return nativeGetEndStatus(j);
        }
        return -1;
    }

    public int getImage(byte[] bArr, Rect rect) {
        long j = this.mNative;
        if (j != 0) {
            return nativeGetImage(j, bArr, rect.left, rect.top, rect.right, rect.bottom);
        }
        return -2147483646;
    }

    public String getInputFolderPath() {
        return this.mFolderPathInputImages;
    }

    public String getInputImageFormat() {
        return this.mInputImageFormat;
    }

    public GravityParam getLastGravity() {
        return this.mGravity.copyInstance();
    }

    public int getOutputImage(byte[] bArr, Rect rect) {
        long j = this.mNative;
        if (j != 0) {
            return nativeGetOutputImage(j, bArr, rect.left, rect.top, rect.right, rect.bottom);
        }
        return -2147483646;
    }

    public int getOutputImageSize(int[] iArr) {
        long j = this.mNative;
        if (j != 0) {
            return nativeGetOutputSize(j, iArr);
        }
        return -2147483646;
    }

    public int getRotatedSmallImage(byte[] bArr, Rect rect, int i, int i2) {
        long j = this.mNative;
        if (j != 0) {
            return nativeGetRotatedSmallImage(j, bArr, rect.left, rect.top, rect.right, rect.bottom, i, i2);
        }
        return -2147483646;
    }

    public int initialize(InitParam initParam) {
        if (this.mNativeOutputInfo == 0) {
            return -2147483646;
        }
        this.mNative = createNativeObject();
        long j = this.mNative;
        if (j != 0) {
            return nativeInitialize(j, initParam, this.mNativeOutputInfo);
        }
        return -2147483644;
    }

    public int inputSave(Image image) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        long currentTimeMillis = System.currentTimeMillis();
        String str = null;
        if (this.mSaveInputImages) {
            str = String.format(Locale.US, "%s/%06d.yuv", this.mFolderPathInputImages, Long.valueOf(this.mAttachCount));
        }
        int nativeSaveYuv = nativeSaveYuv(this.mNative, image.getPlanes()[0].getBuffer(), image.getPlanes()[1].getBuffer(), image.getPlanes()[2].getBuffer(), image.getPlanes()[0].getRowStride(), image.getPlanes()[1].getRowStride(), image.getPlanes()[2].getRowStride(), image.getPlanes()[0].getPixelStride(), image.getPlanes()[1].getPixelStride(), image.getPlanes()[2].getPixelStride(), str);
        long currentTimeMillis2 = System.currentTimeMillis();
        Log.v(TAG, "Performance.JNI " + (currentTimeMillis2 - currentTimeMillis) + " msec");
        this.mAttachCount = this.mAttachCount + 1;
        return nativeSaveYuv;
    }

    public int preparePanorama360(int i, int i2, String str, String str2, boolean z, GalleryInfoData galleryInfoData) {
        long j = this.mNative;
        if (j != 0) {
            return nativePreparePanorama360(j, i, i2, str, str2, z, galleryInfoData);
        }
        return -2147483646;
    }

    public int saveLog(String str, String str2) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSaveLog(j, str, str2);
        }
        return -2147483646;
    }

    public int savePanorama360(int i, int i2, FileDescriptor fileDescriptor, int i3, String str, String str2, boolean z, GalleryInfoData galleryInfoData, boolean z2) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        int fd = getFD(fileDescriptor);
        if (fd >= 0) {
            return nativeSavePanorama360(this.mNative, i, i2, null, fd, i3, str, str2, z, galleryInfoData, z2);
        }
        return -2147483632;
    }

    public int savePanorama360(int i, int i2, String str, int i3, String str2, String str3, boolean z, GalleryInfoData galleryInfoData, boolean z2) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSavePanorama360(j, i, i2, str, 0, i3, str2, str3, z, galleryInfoData, z2);
        }
        return -2147483646;
    }

    public int savePanorama360Delay(FileDescriptor fileDescriptor, boolean z, int i, boolean z2) {
        if (this.mNativeOutputInfo == 0) {
            return -2147483646;
        }
        int fd = getFD(fileDescriptor);
        if (fd >= 0) {
            return nativeSavePanorama360Delay(this.mNativeOutputInfo, null, fd, z, i, z2);
        }
        return -2147483632;
    }

    public int savePanorama360Delay(String str, boolean z, int i, boolean z2) {
        long j = this.mNativeOutputInfo;
        if (j != 0) {
            return nativeSavePanorama360Delay(j, str, 0, z, i, z2);
        }
        return -2147483646;
    }

    public int setAovGain(double d2) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetAovGain(j, d2);
        }
        return -2147483646;
    }

    public void setAttachEnabled(boolean z) {
        this.mAttachEnabled = z;
    }

    public int setCalcseamPixnum(int i) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetCalcseamPixnum(j, i);
        }
        return -2147483646;
    }

    public int setDistortionCorrectionParam(double d2, double d3, double d4, double d5) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetDistortionCorrectionParam(j, d2, d3, d4, d5);
        }
        return -2147483646;
    }

    public int setDrawThreshold(double d2) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetDrawThreshold(j, d2);
        }
        return -2147483646;
    }

    public int setGyroscopeData(MorphoSensorFusion.SensorData[] sensorDataArr) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetGyroscopeData(j, sensorDataArr);
        }
        return -2147483646;
    }

    public int setInitialRotationByGravity(double d2, double d3, double d4) {
        long j = this.mNative;
        if (j == 0) {
            return -2147483646;
        }
        int nativeSetInitialRotationByGravity = nativeSetInitialRotationByGravity(j, d2, d3, d4);
        GravityParam gravityParam = this.mGravity;
        gravityParam.x = d2;
        gravityParam.y = d3;
        gravityParam.z = d4;
        return nativeSetInitialRotationByGravity;
    }

    public int setInputImageFormat(String str) {
        this.mInputImageFormat = "";
        long j = this.mNative;
        if (j == 0) {
            return -2147483646;
        }
        int nativeSetImageFormat = nativeSetImageFormat(j, str);
        if (nativeSetImageFormat != 0) {
            return nativeSetImageFormat;
        }
        this.mInputImageFormat = str;
        return nativeSetImageFormat;
    }

    public int setMotionDetectionMode(int i) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetMotionDetectionMode(j, i);
        }
        return -2147483646;
    }

    public int setNoiseReductionParam(int i) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetNoiseReductionParam(j, i);
        }
        return -2147483646;
    }

    public int setPreviewImage(int i, int i2) {
        long j = this.mNative;
        if (j == 0) {
            return -2147483646;
        }
        return nativeSetPreviewImage(j, i, i2);
    }

    public int setProjectionMode(int i) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetProjectionMode(j, i);
        }
        return -2147483646;
    }

    public int setRotationRatio(double d2) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetRotationRatio(j, d2);
        }
        return -2147483646;
    }

    public int setRotationVector(double[] dArr) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetRotationVector(j, dArr);
        }
        return -2147483646;
    }

    public int setSeamsearchRatio(double d2) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetSeamsearchRatio(j, d2);
        }
        return -2147483646;
    }

    public int setSensorUseMode(int i) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetSensorUseMode(j, i);
        }
        return -2147483646;
    }

    public int setShrinkRatio(double d2) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetShrinkRatio(j, d2);
        }
        return -2147483646;
    }

    public int setUnsharpStrength(int i) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetUnsharpStrength(j, i);
        }
        return -2147483646;
    }

    public int setUseDeform(boolean z) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetUseDeform(j, z ? 1 : 0);
        }
        return -2147483646;
    }

    public int setUseLuminanceCorrection(boolean z) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetUseLuminanceCorrection(j, z ? 1 : 0);
        }
        return -2147483646;
    }

    public int setZrotationCoeff(double d2) {
        long j = this.mNative;
        if (j != 0) {
            return nativeSetZrotationCoeff(j, d2);
        }
        return -2147483646;
    }

    public int start(int i, int i2) {
        long j = this.mNative;
        if (j == 0) {
            return -2147483646;
        }
        int nativeStart = nativeStart(j, i, i2);
        this.mAttachCount = 0;
        return nativeStart;
    }

    public int updatePreviewImage(Bitmap bitmap) {
        long j = this.mNative;
        if (j == 0) {
            return -2147483646;
        }
        return nativeUpdatePreviewImage(j, bitmap);
    }
}
