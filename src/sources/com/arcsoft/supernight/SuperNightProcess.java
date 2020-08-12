package com.arcsoft.supernight;

import android.graphics.Rect;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.media.Image;
import android.os.Environment;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SuperNightProcess implements ProgressCallback {
    public static final int ARC_SN_CAMERA_MODE_UNKNOWN = -1;
    public static final int ARC_SN_CAMERA_MODE_XIAOMI_G80_GW1 = 1793;
    public static final int ARC_SN_CAMERA_MODE_XIAOMI_G90_GW1 = 1793;
    public static final int ARC_SN_CAMERA_MODE_XIAOMI_G90_GW1_INDIA = 1796;
    public static final int ARC_SN_CAMERA_MODE_XIAOMI_J15S_G85_S5KGM1_N = 1819;
    public static final int ARC_SN_CAMERA_MODE_XIAOMI_J15S_G85_S5KGM1_N_INDIA = 1820;
    public static final int ARC_SN_CAMERA_MODE_XIAOMI_SDM855_12MB_IMX586 = 1792;
    public static final int ARC_SN_CAMERA_STATE_HAND = 2;
    public static final int ARC_SN_CAMERA_STATE_UNKNOWN = 0;
    public static final int ARC_SN_MAX_INPUT_IMAGE_NUM = 20;
    public static final int ARC_SN_SCENEMODE_INDOOR = 1;
    public static final int ARC_SN_SCENEMODE_LOWLIGHT = 3;
    public static final int ARC_SN_SCENEMODE_OUTDOOR = 2;
    public static final int ARC_SN_SCENEMODE_PORTRAIT = 4;
    public static final int ARC_SN_SCENEMODE_UNKNOW = 0;
    public static final int ASVL_PAF_NV12 = 2049;
    public static final int ASVL_PAF_NV21 = 2050;
    public static final int ASVL_PAF_RAW10_BGGR_10B = 3332;
    public static final int ASVL_PAF_RAW10_BGGR_16B = 3340;
    public static final int ASVL_PAF_RAW10_BGGR_16B_LSB = 3428;
    public static final int ASVL_PAF_RAW10_BGGR_16B_MSB = 3412;
    public static final int ASVL_PAF_RAW10_GBRG_10B = 3331;
    public static final int ASVL_PAF_RAW10_GBRG_16B = 3339;
    public static final int ASVL_PAF_RAW10_GBRG_16B_LSB = 3427;
    public static final int ASVL_PAF_RAW10_GBRG_16B_MSB = 3411;
    public static final int ASVL_PAF_RAW10_GRAY_10B = 3585;
    public static final int ASVL_PAF_RAW10_GRAY_16B = 3713;
    public static final int ASVL_PAF_RAW10_GRBG_10B = 3330;
    public static final int ASVL_PAF_RAW10_GRBG_16B = 3338;
    public static final int ASVL_PAF_RAW10_GRBG_16B_LSB = 3426;
    public static final int ASVL_PAF_RAW10_GRBG_16B_MSB = 3410;
    public static final int ASVL_PAF_RAW10_RGGB_10B = 3329;
    public static final int ASVL_PAF_RAW10_RGGB_16B = 3337;
    public static final int ASVL_PAF_RAW10_RGGB_16B_LSB = 3425;
    public static final int ASVL_PAF_RAW10_RGGB_16B_MSB = 3409;
    public static final int ASVL_PAF_RAW12_BGGR_12B = 3336;
    public static final int ASVL_PAF_RAW12_BGGR_16B = 3348;
    public static final int ASVL_PAF_RAW12_BGGR_16B_LSB = 3433;
    public static final int ASVL_PAF_RAW12_BGGR_16B_MSB = 3417;
    public static final int ASVL_PAF_RAW12_GBRG_12B = 3335;
    public static final int ASVL_PAF_RAW12_GBRG_16B = 3347;
    public static final int ASVL_PAF_RAW12_GBRG_16B_LSB = 3432;
    public static final int ASVL_PAF_RAW12_GBRG_16B_MSB = 3416;
    public static final int ASVL_PAF_RAW12_GRAY_12B = 3601;
    public static final int ASVL_PAF_RAW12_GRAY_16B = 3729;
    public static final int ASVL_PAF_RAW12_GRBG_12B = 3334;
    public static final int ASVL_PAF_RAW12_GRBG_16B = 3346;
    public static final int ASVL_PAF_RAW12_GRBG_16B_LSB = 3431;
    public static final int ASVL_PAF_RAW12_GRBG_16B_MSB = 3415;
    public static final int ASVL_PAF_RAW12_RGGB_12B = 3333;
    public static final int ASVL_PAF_RAW12_RGGB_16B = 3345;
    public static final int ASVL_PAF_RAW12_RGGB_16B_LSB = 3430;
    public static final int ASVL_PAF_RAW12_RGGB_16B_MSB = 3414;
    public static final int ASVL_PAF_RAW14_BGGR_14B = 3384;
    public static final int ASVL_PAF_RAW14_BGGR_16B = 3396;
    public static final int ASVL_PAF_RAW14_BGGR_16B_LSB = 3437;
    public static final int ASVL_PAF_RAW14_BGGR_16B_MSB = 3421;
    public static final int ASVL_PAF_RAW14_GBRG_14B = 3383;
    public static final int ASVL_PAF_RAW14_GBRG_16B = 3395;
    public static final int ASVL_PAF_RAW14_GBRG_16B_LSB = 3436;
    public static final int ASVL_PAF_RAW14_GBRG_16B_MSB = 3420;
    public static final int ASVL_PAF_RAW14_GRAY_14B = 3617;
    public static final int ASVL_PAF_RAW14_GRAY_16B = 3745;
    public static final int ASVL_PAF_RAW14_GRBG_14B = 3382;
    public static final int ASVL_PAF_RAW14_GRBG_16B = 3394;
    public static final int ASVL_PAF_RAW14_GRBG_16B_LSB = 3435;
    public static final int ASVL_PAF_RAW14_GRBG_16B_MSB = 3419;
    public static final int ASVL_PAF_RAW14_RGGB_14B = 3381;
    public static final int ASVL_PAF_RAW14_RGGB_16B = 3393;
    public static final int ASVL_PAF_RAW14_RGGB_16B_LSB = 3434;
    public static final int ASVL_PAF_RAW14_RGGB_16B_MSB = 3418;
    public static final int ASVL_PAF_RAW16_BGGR_16B = 3364;
    public static final int ASVL_PAF_RAW16_GBRG_16B = 3363;
    public static final int ASVL_PAF_RAW16_GRAY_16B = 3633;
    public static final int ASVL_PAF_RAW16_GRBG_16B = 3362;
    public static final int ASVL_PAF_RAW16_RGGB_16B = 3361;
    private static final String DEBUG_FILE = (Environment.getExternalStorageDirectory().toString() + "/DCIM/arc_super_night/dump_file.txt");
    private static final String DUMP_KEY = "dumpSNImage";
    private static final String HINT_FOR_RAW_REPROCESS_KEY = "com.mediatek.control.capture.hintForRawReprocess";
    private static final String LOG_KEY = "debugSNLog";
    private static final String SUPPERNIGHT_ADRCGAIN_KEY = "com.mediatek.suppernightfeature.fadrcgain";
    private static final String SUPPERNIGHT_BLACKLEVEL_KEY = "com.mediatek.suppernightfeature.blacklevel";
    private static final String SUPPERNIGHT_BRIGHTLEVEL_KEY = "com.mediatek.suppernightfeature.brightlevel";
    private static final String SUPPERNIGHT_EXPINDEX_KEY = "com.mediatek.suppernightfeature.expindex";
    private static final String SUPPERNIGHT_ISPGAIN_KEY = "com.mediatek.suppernightfeature.fispgain";
    private static final String SUPPERNIGHT_LUXINDEX_KEY = "com.mediatek.suppernightfeature.luxindex";
    private static final String SUPPERNIGHT_SENSORGAIN_KEY = "com.mediatek.suppernightfeature.fsensorgain";
    private static final String SUPPERNIGHT_SHUTTER_KEY = "com.mediatek.suppernightfeature.fshutter";
    private static final String SUPPERNIGHT_TOTALGAIN_KEY = "com.mediatek.suppernightfeature.ftotalgain";
    private static final String SUPPERNIGHT_WBGAIN_KEY = "com.mediatek.suppernightfeature.fwbgain";
    private static final String TAG = "SuperNightProcess";
    private CaptureResult.Key<int[]> ADRC_GAIN_RESULT_KEY = null;
    private CaptureResult.Key<int[]> BLACK_LEVEL_RESULT_KEY = null;
    private CaptureResult.Key<int[]> BRIGHT_LEVEL_RESULT_KEY = null;
    private CaptureResult.Key<int[]> EXP_INDEX_RESULT_KEY = null;
    private CaptureResult.Key<int[]> ISP_GAIN_RESULT_KEY = null;
    private CaptureResult.Key<int[]> LUX_INDEX_RESULT_KEY = null;
    private CaptureResult.Key<int[]> SENSOR_GAIN_RESULT_KEY = null;
    private CaptureResult.Key<long[]> SHUTTER_RESULT_KEY = null;
    private CaptureResult.Key<int[]> TOTAL_GAIN_RESULT_KEY = null;
    private CaptureResult.Key<float[]> WB_GAIN_RESULT_KEY = null;
    private Rect mArrayRect = null;
    private CountDownLatch mCountDownLatch;
    private boolean mDumpFile = false;
    private boolean mEnableAdrcGain = true;
    private boolean mEnableBlackLevel = true;
    private boolean mEnableWbGain = true;
    private int mFaceOrientation = 90;
    private volatile boolean mInit = false;
    private volatile boolean mIsCancel = false;
    private TotalCaptureResult mMetdata;
    private SuperNightJni mSuperNightJni = new SuperNightJni();

    public class FaceInfo {
        public int faceNum;
        public int faceOrientation;
        public Rect[] faceRects;

        public FaceInfo() {
        }
    }

    public class InputInfo {
        public int cameraState;
        public int curIndex;
        public int imgNum;
        public int[] inputFd = new int[20];
        public RawImage[] inputImages = new RawImage[20];
        public float[] inputImagesEV = new float[20];

        public InputInfo() {
        }
    }

    public class Param {
        public int curveBrightness;
        public int curveContrast;
        public int curveHighlight;
        public int curveMid;
        public int curveShadow;
        public int edgeSharpIntensity;
        public int noiseLength;
        public int sharpIntensity;

        public Param() {
        }
    }

    public class RawInfo {
        public int[] blackLevel = new int[4];
        public int[] brightLevel = new int[4];
        public int[] evList = new int[20];
        public int expIndex;
        public float fAdrcGain;
        public float fISPGain;
        public float fSensorGain;
        public float fShutter;
        public float fTotalGain;
        public float[] fWbGain = new float[4];
        public int luxIndex;
        public int rawType;

        public RawInfo() {
            for (int i = 0; i < 4; i++) {
                this.fWbGain[i] = 1.0f;
            }
            this.fAdrcGain = 1.0f;
        }
    }

    public SuperNightProcess(Rect rect) {
        readDebugFileValue();
        this.mSuperNightJni.setDumpImageFile(this.mDumpFile);
        this.mArrayRect = rect;
        LOG.d(TAG, "dumpFile = " + this.mDumpFile + " debugLog = " + LOG.DEBUG);
        StringBuilder sb = new StringBuilder();
        sb.append("mArrayRect = ");
        sb.append(this.mArrayRect);
        LOG.d(TAG, sb.toString());
        if (this.mArrayRect != null) {
            LOG.d(TAG, "mArrayRect = " + this.mArrayRect.toString());
        }
        LOG.d("Version", "--01/18--");
    }

    private void conversionCropRect(Rect rect, int i, int i2) {
        Rect rect2;
        if (rect != null && (rect2 = this.mArrayRect) != null && i > 0 && i2 > 0) {
            float width = ((float) rect2.width()) / ((float) i);
            float height = ((float) this.mArrayRect.height()) / ((float) i2);
            LOG.d(TAG, "fMultipleW = " + width + ", fMultipleH = " + height);
            rect.left = (int) (((float) rect.left) * width);
            rect.top = (int) (((float) rect.top) * height);
            Rect rect3 = this.mArrayRect;
            rect.right = rect3.right - ((int) (((float) (i - rect.right)) * width));
            rect.bottom = rect3.bottom - ((int) (((float) (i2 - rect.bottom)) * height));
            int i3 = rect.left;
            if (i3 % 2 != 0) {
                rect.left = i3 + 1;
            }
            int i4 = rect.top;
            if (i4 % 2 != 0) {
                rect.top = i4 + 1;
            }
            int i5 = rect.right;
            if (i5 % 2 != 0) {
                rect.right = i5 - 1;
            }
            int i6 = rect.bottom;
            if (i6 % 2 != 0) {
                rect.bottom = i6 - 1;
            }
            LOG.d(TAG, "conversionCropRect -> cropRect = " + rect.toString());
        }
    }

    private FaceInfo getFaceInfo(TotalCaptureResult totalCaptureResult, int i, int i2) {
        if (totalCaptureResult == null || this.mArrayRect == null || i <= 0 || i2 <= 0) {
            return null;
        }
        Face[] faceArr = (Face[]) totalCaptureResult.get(CaptureResult.STATISTICS_FACES);
        if (faceArr != null) {
            LOG.d(TAG, "face length = " + faceArr.length);
        }
        if (faceArr == null || faceArr.length <= 0) {
            return null;
        }
        float width = ((float) this.mArrayRect.width()) / ((float) i);
        float height = ((float) this.mArrayRect.height()) / ((float) i2);
        LOG.d(TAG, "fMultipleW = " + width + ", fMultipleH = " + height);
        FaceInfo faceInfo = new FaceInfo();
        faceInfo.faceRects = new Rect[faceArr.length];
        faceInfo.faceNum = faceArr.length;
        faceInfo.faceOrientation = this.mFaceOrientation;
        for (int i3 = 0; i3 < faceArr.length; i3++) {
            faceInfo.faceRects[i3] = new Rect(faceArr[i3].getBounds());
            Rect[] rectArr = faceInfo.faceRects;
            rectArr[i3].left = (int) (((float) rectArr[i3].left) / width);
            rectArr[i3].top = (int) (((float) rectArr[i3].top) / height);
            rectArr[i3].right = (int) (((float) rectArr[i3].right) / width);
            rectArr[i3].bottom = (int) (((float) rectArr[i3].bottom) / height);
            LOG.d(TAG, "conversionFaceRect -> faceRect = " + faceInfo.faceRects[i3].toString());
        }
        return faceInfo;
    }

    private RawImage getRawImage(Image image, int i) {
        RawImage rawImage = new RawImage();
        int format = image.getFormat();
        rawImage.mWidth = image.getWidth();
        rawImage.mHeight = image.getHeight();
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        buffer.rewind();
        rawImage.mPitch1 = 0;
        rawImage.mPlane1 = null;
        if (format == 35) {
            rawImage.mPixelArrayFormat = 2050;
            ByteBuffer buffer2 = planes[2].getBuffer();
            buffer2.rewind();
            rawImage.mPitch1 = planes[2].getRowStride();
            rawImage.mPlane1 = buffer2;
        } else if (format == 32) {
            rawImage.mPixelArrayFormat = i;
        }
        rawImage.mPitch0 = planes[0].getRowStride();
        rawImage.mPitch2 = 0;
        rawImage.mPitch3 = 0;
        rawImage.mPlane0 = buffer;
        rawImage.mPlane2 = null;
        rawImage.mPlane3 = null;
        return rawImage;
    }

    private void getVendorTagValue(TotalCaptureResult totalCaptureResult, RawInfo rawInfo) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        int[] iArr5;
        int[] iArr6;
        long[] jArr;
        int[] iArr7;
        int[] iArr8;
        CaptureResult.Key<int[]> key = this.BRIGHT_LEVEL_RESULT_KEY;
        if (!(key == null || (iArr8 = (int[]) totalCaptureResult.get(key)) == null || iArr8.length <= 0)) {
            if (this.mEnableBlackLevel) {
                int i = 0;
                while (true) {
                    int[] iArr9 = rawInfo.brightLevel;
                    if (i >= iArr9.length) {
                        break;
                    }
                    iArr9[i] = iArr8[0] / 1024;
                    LOG.d(TAG, "brightLevel[" + i + "] = " + rawInfo.brightLevel[i]);
                    i++;
                }
            }
            for (int i2 = 0; i2 < iArr8.length; i2++) {
                LOG.d("vendorTag", "bright[" + i2 + "] = " + iArr8[i2]);
            }
        }
        CaptureResult.Key<int[]> key2 = this.BLACK_LEVEL_RESULT_KEY;
        if (!(key2 == null || (iArr7 = (int[]) totalCaptureResult.get(key2)) == null || iArr7.length <= 0)) {
            if (this.mEnableBlackLevel) {
                int i3 = 0;
                while (true) {
                    int[] iArr10 = rawInfo.blackLevel;
                    if (i3 >= iArr10.length) {
                        break;
                    }
                    iArr10[i3] = iArr7[0] / 1024;
                    LOG.d(TAG, "blackLevel[" + i3 + "] = " + rawInfo.blackLevel[i3]);
                    i3++;
                }
            }
            for (int i4 = 0; i4 < iArr7.length; i4++) {
                LOG.d("vendorTag", "black[" + i4 + "] = " + iArr7[i4]);
            }
        }
        CaptureResult.Key<float[]> key3 = this.WB_GAIN_RESULT_KEY;
        if (key3 != null) {
            float[] fArr = (float[]) totalCaptureResult.get(key3);
            LOG.d("vendorTag", "awbGain = " + fArr);
            if (fArr != null && fArr.length > 0) {
                if (this.mEnableWbGain) {
                    float[] fArr2 = rawInfo.fWbGain;
                    fArr2[0] = fArr[0];
                    fArr2[1] = fArr[1];
                    fArr2[2] = fArr[2];
                    fArr2[3] = fArr[3];
                }
                for (int i5 = 0; i5 < fArr.length; i5++) {
                    LOG.d("vendorTag", "wbGain[" + i5 + "] = " + fArr[i5]);
                }
            }
        }
        CaptureResult.Key<long[]> key4 = this.SHUTTER_RESULT_KEY;
        if (!(key4 == null || (jArr = (long[]) totalCaptureResult.get(key4)) == null || jArr.length <= 0)) {
            rawInfo.fShutter = (float) jArr[0];
            LOG.d(TAG, "fShutter = " + rawInfo.fShutter);
            for (int i6 = 0; i6 < jArr.length; i6++) {
                LOG.d("vendorTag", "shutter[" + i6 + "] = " + jArr[i6]);
            }
        }
        CaptureResult.Key<int[]> key5 = this.SENSOR_GAIN_RESULT_KEY;
        if (!(key5 == null || (iArr6 = (int[]) totalCaptureResult.get(key5)) == null || iArr6.length <= 0)) {
            rawInfo.fSensorGain = ((float) iArr6[0]) / 1024.0f;
            LOG.d(TAG, "fSensorGain = " + rawInfo.fSensorGain);
            for (int i7 = 0; i7 < iArr6.length; i7++) {
                LOG.d("vendorTag", "sensorGain[" + i7 + "] = " + iArr6[i7]);
            }
        }
        CaptureResult.Key<int[]> key6 = this.ISP_GAIN_RESULT_KEY;
        if (!(key6 == null || (iArr5 = (int[]) totalCaptureResult.get(key6)) == null || iArr5.length <= 0)) {
            rawInfo.fISPGain = ((float) iArr5[0]) / 1024.0f;
            LOG.d(TAG, "fISPGain = " + rawInfo.fISPGain);
            for (int i8 = 0; i8 < iArr5.length; i8++) {
                LOG.d("vendorTag", "ispGain[" + i8 + "] = " + iArr5[i8]);
            }
        }
        CaptureResult.Key<int[]> key7 = this.LUX_INDEX_RESULT_KEY;
        if (!(key7 == null || (iArr4 = (int[]) totalCaptureResult.get(key7)) == null || iArr4.length <= 0)) {
            rawInfo.luxIndex = iArr4[0] / 10000;
            LOG.d(TAG, "luxIndex = " + rawInfo.luxIndex);
            for (int i9 = 0; i9 < iArr4.length; i9++) {
                LOG.d("vendorTag", "luxIndex[" + i9 + "] = " + iArr4[i9]);
            }
        }
        CaptureResult.Key<int[]> key8 = this.EXP_INDEX_RESULT_KEY;
        if (!(key8 == null || (iArr3 = (int[]) totalCaptureResult.get(key8)) == null || iArr3.length <= 0)) {
            rawInfo.expIndex = iArr3[0];
            LOG.d(TAG, "expIndex = " + rawInfo.expIndex);
            for (int i10 = 0; i10 < iArr3.length; i10++) {
                LOG.d("vendorTag", "expIndex[" + i10 + "] = " + iArr3[i10]);
            }
        }
        CaptureResult.Key<int[]> key9 = this.ADRC_GAIN_RESULT_KEY;
        if (!(key9 == null || (iArr2 = (int[]) totalCaptureResult.get(key9)) == null || iArr2.length <= 0)) {
            if (this.mEnableAdrcGain) {
                rawInfo.fAdrcGain = ((float) iArr2[0]) / 1000.0f;
                LOG.d(TAG, "fAdrcGain = " + rawInfo.fAdrcGain);
            }
            for (int i11 = 0; i11 < iArr2.length; i11++) {
                LOG.d("vendorTag", "adrcGain[" + i11 + "] = " + iArr2[i11]);
            }
        }
        CaptureResult.Key<int[]> key10 = this.TOTAL_GAIN_RESULT_KEY;
        if (key10 != null && (iArr = (int[]) totalCaptureResult.get(key10)) != null && iArr.length > 0) {
            rawInfo.fTotalGain = ((float) iArr[0]) / 100.0f;
            LOG.d(TAG, "fTotalGain = " + rawInfo.fTotalGain);
            for (int i12 = 0; i12 < iArr.length; i12++) {
                LOG.d("vendorTag", "totalGain[" + i12 + "] = " + iArr[i12]);
            }
        }
    }

    private boolean setupSomeVendorTag(TotalCaptureResult totalCaptureResult) {
        LOG.d(TAG, "setupSomeVendorTag");
        if (this.BRIGHT_LEVEL_RESULT_KEY != null && this.BLACK_LEVEL_RESULT_KEY != null && this.WB_GAIN_RESULT_KEY != null && this.SHUTTER_RESULT_KEY != null && this.SENSOR_GAIN_RESULT_KEY != null && this.ISP_GAIN_RESULT_KEY != null && this.LUX_INDEX_RESULT_KEY != null && this.ADRC_GAIN_RESULT_KEY != null && this.TOTAL_GAIN_RESULT_KEY != null && this.EXP_INDEX_RESULT_KEY != null) {
            return true;
        }
        List<CaptureResult.Key<?>> keys = totalCaptureResult.getKeys();
        if (keys == null || keys.size() <= 0) {
            LOG.d(TAG, "List<CaptureResult.Key<?>> is error");
            if (keys == null) {
                return false;
            }
            LOG.d(TAG, "List<CaptureResult.Key<?>> lenth = " + keys.size());
            return false;
        }
        for (CaptureResult.Key<int[]> key : keys) {
            if (SUPPERNIGHT_BRIGHTLEVEL_KEY.equals(key.getName())) {
                LOG.d(TAG, "BRIGHT_LEVEL_RESULT_KEY");
                this.BRIGHT_LEVEL_RESULT_KEY = key;
            }
            if (SUPPERNIGHT_BLACKLEVEL_KEY.equals(key.getName())) {
                LOG.d(TAG, "BLACK_LEVEL_RESULT_KEY");
                this.BLACK_LEVEL_RESULT_KEY = key;
            }
            if (SUPPERNIGHT_WBGAIN_KEY.equals(key.getName())) {
                LOG.d(TAG, "WB_GAIN_RESULT_KEY");
                this.WB_GAIN_RESULT_KEY = key;
            }
            if (SUPPERNIGHT_SHUTTER_KEY.equals(key.getName())) {
                LOG.d(TAG, "SHUTTER_RESULT_KEY");
                this.SHUTTER_RESULT_KEY = key;
            }
            if (SUPPERNIGHT_SENSORGAIN_KEY.equals(key.getName())) {
                LOG.d(TAG, "SENSOR_GAIN_RESULT_KEY");
                this.SENSOR_GAIN_RESULT_KEY = key;
            }
            if (SUPPERNIGHT_ISPGAIN_KEY.equals(key.getName())) {
                LOG.d(TAG, "ISP_GAIN_RESULT_KEY");
                this.ISP_GAIN_RESULT_KEY = key;
            }
            if (SUPPERNIGHT_LUXINDEX_KEY.equals(key.getName())) {
                LOG.d(TAG, "LUX_INDEX_RESULT_KEY");
                this.LUX_INDEX_RESULT_KEY = key;
            }
            if (SUPPERNIGHT_EXPINDEX_KEY.equals(key.getName())) {
                LOG.d(TAG, "EXP_INDEX_RESULT_KEY");
                this.EXP_INDEX_RESULT_KEY = key;
            }
            if (SUPPERNIGHT_ADRCGAIN_KEY.equals(key.getName())) {
                LOG.d(TAG, "ADRC_GAIN_RESULT_KEY");
                this.ADRC_GAIN_RESULT_KEY = key;
            }
            if (SUPPERNIGHT_TOTALGAIN_KEY.equals(key.getName())) {
                LOG.d(TAG, "TOTAL_GAIN_RESULT_KEY");
                this.TOTAL_GAIN_RESULT_KEY = key;
            }
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0199, code lost:
        if (r18.mCountDownLatch != null) goto L_0x019b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x019b, code lost:
        com.arcsoft.supernight.LOG.d(com.arcsoft.supernight.SuperNightProcess.TAG, "mCountDownLatch.countDown() 0");
        r18.mCountDownLatch.countDown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01c4, code lost:
        if (r18.mCountDownLatch != null) goto L_0x019b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01c7, code lost:
        return r10;
     */
    public int addAllInputInfo(ArrayList<Image> arrayList, ArrayList<TotalCaptureResult> arrayList2, int i, Image image, Rect rect) {
        LOG.d(TAG, "-- addAllInputInfoEx --");
        int i2 = -1;
        if (arrayList == null || arrayList.size() <= 0 || arrayList2 == null || arrayList2.size() <= 0 || arrayList.size() != arrayList2.size() || this.mSuperNightJni == null || image == null || rect == null) {
            LOG.d(TAG, "addAllInputInfo - > error invalid param");
            if (this.mCountDownLatch == null) {
                return -1;
            }
            LOG.d(TAG, "mCountDownLatch.countDown() 1");
            this.mCountDownLatch.countDown();
            return -1;
        }
        LOG.d(TAG, " imageList size =  " + arrayList.size());
        int size = arrayList.size();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < arrayList.size()) {
            try {
                if (this.mIsCancel) {
                    try {
                        LOG.d(TAG, "is cancel 0");
                        if (this.mCountDownLatch != null) {
                            LOG.d(TAG, "mCountDownLatch.countDown() 0");
                            this.mCountDownLatch.countDown();
                        }
                        return i2;
                    } catch (IllegalStateException e2) {
                        e = e2;
                        try {
                            LOG.d("Error", "e-> " + e.toString());
                        } catch (Throwable th) {
                            if (this.mCountDownLatch != null) {
                                LOG.d(TAG, "mCountDownLatch.countDown() 0");
                                this.mCountDownLatch.countDown();
                            }
                            throw th;
                        }
                    }
                } else {
                    Image image2 = arrayList.get(i3);
                    TotalCaptureResult totalCaptureResult = arrayList2.get(i3);
                    if (image2 == null || totalCaptureResult == null) {
                        LOG.d(TAG, "TotalCaptureResult - > error invalid param");
                        if (this.mCountDownLatch == null) {
                            return -1;
                        }
                        LOG.d(TAG, "mCountDownLatch.countDown() 0");
                        this.mCountDownLatch.countDown();
                        return -1;
                    }
                    LOG.d("vendorTag", " ------ " + i3);
                    if (!setupSomeVendorTag(totalCaptureResult)) {
                        LOG.d(TAG, "setupSomeVendorTag is error!!");
                        if (this.mCountDownLatch != null) {
                            LOG.d(TAG, "mCountDownLatch.countDown() 0");
                            this.mCountDownLatch.countDown();
                        }
                        return i2;
                    }
                    InputInfo inputInfo = new InputInfo();
                    inputInfo.curIndex = i3;
                    inputInfo.imgNum = size;
                    inputInfo.cameraState = 2;
                    int width = image2.getWidth();
                    int height = image2.getHeight();
                    RawInfo rawInfo = new RawInfo();
                    rawInfo.rawType = 0;
                    getVendorTagValue(totalCaptureResult, rawInfo);
                    Integer num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
                    if (num != null) {
                        rawInfo.evList[0] = num.intValue();
                        inputInfo.inputImagesEV[0] = (float) num.intValue();
                    }
                    inputInfo.inputImages[0] = getRawImage(image2, i);
                    this.mSuperNightJni.addOneInputInfo(rawInfo, inputInfo);
                    i3++;
                    i4 = width;
                    i5 = height;
                    i2 = -1;
                }
            } catch (IllegalStateException e3) {
                e = e3;
                i2 = -1;
                LOG.d("Error", "e-> " + e.toString());
            }
        }
        FaceInfo faceInfo = null;
        Iterator<TotalCaptureResult> it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            TotalCaptureResult next = it.next();
            Integer num2 = (Integer) next.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
            if (num2 != null && num2.intValue() == 0) {
                faceInfo = getFaceInfo(next, i4, i5);
                break;
            }
        }
        if (this.mIsCancel) {
            LOG.d(TAG, "is cancel 1");
            if (this.mCountDownLatch == null) {
                return -1;
            }
            LOG.d(TAG, "mCountDownLatch.countDown() 0");
            this.mCountDownLatch.countDown();
            return -1;
        }
        InputInfo inputInfo2 = new InputInfo();
        inputInfo2.curIndex = 0;
        inputInfo2.imgNum = size;
        inputInfo2.cameraState = 2;
        inputInfo2.inputImages[0] = getRawImage(image, i);
        i2 = this.mSuperNightJni.process(faceInfo, inputInfo2, 3, rect, this);
        LOG.d(TAG, "process = " + i2 + ", cropRect0 = " + rect.toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:58:0x018d, code lost:
        if (r17.mCountDownLatch != null) goto L_0x018f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x018f, code lost:
        com.arcsoft.supernight.LOG.d(com.arcsoft.supernight.SuperNightProcess.TAG, "mCountDownLatch.countDown() 2");
        r17.mCountDownLatch.countDown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01ba, code lost:
        if (r17.mCountDownLatch != null) goto L_0x018f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01bd, code lost:
        return r9;
     */
    public int addAllInputInfoEx(ArrayList<Image> arrayList, ArrayList<TotalCaptureResult> arrayList2, int i, Rect rect) {
        int i2 = -1;
        if (arrayList == null || arrayList.size() <= 0 || arrayList2 == null || arrayList2.size() <= 0 || arrayList.size() != arrayList2.size() || this.mSuperNightJni == null || rect == null) {
            LOG.d(TAG, "addAllInputInfo - > error invalid param");
            if (this.mCountDownLatch == null) {
                return -1;
            }
            LOG.d(TAG, "mCountDownLatch.countDown() 3");
            this.mCountDownLatch.countDown();
            return -1;
        }
        InputInfo inputInfo = new InputInfo();
        LOG.d(TAG, " imageList size =  " + arrayList.size());
        int size = arrayList.size();
        InputInfo inputInfo2 = inputInfo;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < arrayList.size()) {
            try {
                if (this.mIsCancel) {
                    if (this.mCountDownLatch != null) {
                        LOG.d(TAG, "mCountDownLatch.countDown() 2");
                        this.mCountDownLatch.countDown();
                    }
                    return i2;
                }
                Image image = arrayList.get(i3);
                TotalCaptureResult totalCaptureResult = arrayList2.get(i3);
                LOG.d(TAG, "Image format - > " + image.getFormat());
                if (image == null || totalCaptureResult == null) {
                    LOG.d(TAG, "TotalCaptureResult - > error invalid param");
                    if (this.mCountDownLatch == null) {
                        return -1;
                    }
                    LOG.d(TAG, "mCountDownLatch.countDown() 2");
                    this.mCountDownLatch.countDown();
                    return -1;
                }
                LOG.d("vendorTag", " ------ " + i3);
                if (!setupSomeVendorTag(totalCaptureResult)) {
                    try {
                        LOG.d(TAG, "setupSomeVendorTag is error!!");
                        if (this.mCountDownLatch != null) {
                            LOG.d(TAG, "mCountDownLatch.countDown() 2");
                            this.mCountDownLatch.countDown();
                        }
                        return i2;
                    } catch (IllegalStateException e2) {
                        e = e2;
                        try {
                            LOG.d("Error", "e-> " + e.toString());
                        } catch (Throwable th) {
                            if (this.mCountDownLatch != null) {
                                LOG.d(TAG, "mCountDownLatch.countDown() 2");
                                this.mCountDownLatch.countDown();
                            }
                            throw th;
                        }
                    }
                } else {
                    InputInfo inputInfo3 = new InputInfo();
                    inputInfo3.curIndex = i3;
                    inputInfo3.imgNum = size;
                    inputInfo3.cameraState = 2;
                    int width = image.getWidth();
                    int height = image.getHeight();
                    RawInfo rawInfo = new RawInfo();
                    rawInfo.rawType = 0;
                    getVendorTagValue(totalCaptureResult, rawInfo);
                    Integer num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
                    if (num != null) {
                        rawInfo.evList[0] = num.intValue();
                        inputInfo3.inputImagesEV[0] = (float) num.intValue();
                    }
                    inputInfo3.inputImages[0] = getRawImage(image, i);
                    this.mSuperNightJni.addOneInputInfo(rawInfo, inputInfo3);
                    if (i3 == 0) {
                        inputInfo2 = inputInfo3;
                    }
                    i3++;
                    i4 = width;
                    i5 = height;
                    i2 = -1;
                }
            } catch (IllegalStateException e3) {
                e = e3;
                i2 = -1;
                LOG.d("Error", "e-> " + e.toString());
            }
        }
        FaceInfo faceInfo = null;
        Iterator<TotalCaptureResult> it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            TotalCaptureResult next = it.next();
            Integer num2 = (Integer) next.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
            if (num2 != null && num2.intValue() == 0) {
                faceInfo = getFaceInfo(next, i4, i5);
                break;
            }
        }
        if (!this.mIsCancel) {
            i2 = this.mSuperNightJni.process(faceInfo, inputInfo2, 3, rect, this);
            LOG.d(TAG, "process = " + i2 + ", cropRect0 = " + rect.toString());
        } else if (this.mCountDownLatch == null) {
            return -1;
        } else {
            LOG.d(TAG, "mCountDownLatch.countDown() 2");
            this.mCountDownLatch.countDown();
            return -1;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01ee, code lost:
        if (r18.mCountDownLatch != null) goto L_0x01f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01f0, code lost:
        com.arcsoft.supernight.LOG.d(com.arcsoft.supernight.SuperNightProcess.TAG, "mCountDownLatch.countDown() 0");
        r18.mCountDownLatch.countDown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0219, code lost:
        if (r18.mCountDownLatch != null) goto L_0x01f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x021c, code lost:
        return r8;
     */
    public int addAllInputInfo_Fd(ArrayList<Image> arrayList, ArrayList<TotalCaptureResult> arrayList2, ArrayList<Integer> arrayList3, int i, Image image, int i2, Rect rect) {
        ArrayList<Image> arrayList4 = arrayList;
        LOG.d(TAG, "-- addAllInputInfo by fd --");
        int i3 = -1;
        if (arrayList4 == null || arrayList.size() <= 0 || arrayList2 == null || arrayList2.size() <= 0 || arrayList3 == null || arrayList3.size() <= 0 || arrayList.size() != arrayList2.size() || arrayList.size() != arrayList3.size() || this.mSuperNightJni == null || image == null || rect == null) {
            LOG.d(TAG, "addAllInputInfo - > error invalid param");
            if (this.mCountDownLatch == null) {
                return -1;
            }
            LOG.d(TAG, "mCountDownLatch.countDown() 1");
            this.mCountDownLatch.countDown();
            return -1;
        }
        LOG.d(TAG, " imageList size =  " + arrayList.size());
        int size = arrayList.size();
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < arrayList.size()) {
            try {
                if (this.mIsCancel) {
                    try {
                        LOG.d(TAG, "is cancel 0");
                        if (this.mCountDownLatch != null) {
                            LOG.d(TAG, "mCountDownLatch.countDown() 0");
                            this.mCountDownLatch.countDown();
                        }
                        return i3;
                    } catch (IllegalStateException e2) {
                        e = e2;
                        try {
                            LOG.d("Error", "e-> " + e.toString());
                        } catch (Throwable th) {
                            if (this.mCountDownLatch != null) {
                                LOG.d(TAG, "mCountDownLatch.countDown() 0");
                                this.mCountDownLatch.countDown();
                            }
                            throw th;
                        }
                    }
                } else {
                    Image image2 = arrayList4.get(i4);
                    TotalCaptureResult totalCaptureResult = arrayList2.get(i4);
                    if (image2 == null || totalCaptureResult == null) {
                        LOG.d(TAG, "TotalCaptureResult - > error invalid param");
                        if (this.mCountDownLatch == null) {
                            return -1;
                        }
                        LOG.d(TAG, "mCountDownLatch.countDown() 0");
                        this.mCountDownLatch.countDown();
                        return -1;
                    }
                    LOG.d("vendorTag", " ------ " + i4);
                    if (!setupSomeVendorTag(totalCaptureResult)) {
                        LOG.d(TAG, "setupSomeVendorTag is error!!");
                        if (this.mCountDownLatch != null) {
                            LOG.d(TAG, "mCountDownLatch.countDown() 0");
                            this.mCountDownLatch.countDown();
                        }
                        return i3;
                    }
                    InputInfo inputInfo = new InputInfo();
                    inputInfo.curIndex = i4;
                    inputInfo.imgNum = size;
                    inputInfo.cameraState = 2;
                    Integer num = arrayList3.get(i4);
                    if (num != null) {
                        inputInfo.inputFd[0] = num.intValue();
                        LOG.d(TAG, "input fd[" + i4 + "] = " + inputInfo.inputFd[0]);
                    }
                    int width = image2.getWidth();
                    int height = image2.getHeight();
                    RawInfo rawInfo = new RawInfo();
                    rawInfo.rawType = 0;
                    getVendorTagValue(totalCaptureResult, rawInfo);
                    Integer num2 = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
                    if (num2 != null) {
                        rawInfo.evList[0] = num2.intValue();
                        inputInfo.inputImagesEV[0] = (float) num2.intValue();
                    }
                    inputInfo.inputImages[0] = getRawImage(image2, i);
                    this.mSuperNightJni.addOneInputInfo(rawInfo, inputInfo);
                    i4++;
                    arrayList4 = arrayList;
                    i5 = width;
                    i6 = height;
                    i3 = -1;
                }
            } catch (IllegalStateException e3) {
                e = e3;
                i3 = -1;
                LOG.d("Error", "e-> " + e.toString());
            }
        }
        FaceInfo faceInfo = null;
        Iterator<TotalCaptureResult> it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            TotalCaptureResult next = it.next();
            Integer num3 = (Integer) next.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
            if (num3 != null && num3.intValue() == 0) {
                faceInfo = getFaceInfo(next, i5, i6);
                break;
            }
        }
        if (this.mIsCancel) {
            LOG.d(TAG, "is cancel 1");
            if (this.mCountDownLatch == null) {
                return -1;
            }
            LOG.d(TAG, "mCountDownLatch.countDown() 0");
            this.mCountDownLatch.countDown();
            return -1;
        }
        InputInfo inputInfo2 = new InputInfo();
        inputInfo2.curIndex = 0;
        inputInfo2.imgNum = size;
        inputInfo2.cameraState = 2;
        inputInfo2.inputImages[0] = getRawImage(image, i);
        inputInfo2.inputFd[0] = i2;
        i3 = this.mSuperNightJni.process(faceInfo, inputInfo2, 3, rect, this);
        LOG.d(TAG, "process = " + i3 + ", cropRect0 = " + rect.toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0079, code lost:
        if (r7.mCountDownLatch != null) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007b, code lost:
        com.arcsoft.supernight.LOG.d(com.arcsoft.supernight.SuperNightProcess.TAG, "mCountDownLatch.countDown() 5");
        r7.mCountDownLatch.countDown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008c, code lost:
        if (r7.mCountDownLatch == null) goto L_0x008f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008f, code lost:
        return r1;
     */
    public int addOneInputInfo(Image image, TotalCaptureResult totalCaptureResult, int i, int i2, int i3) {
        int i4 = -1;
        if (image == null || totalCaptureResult == null || this.mSuperNightJni == null || i < 0) {
            LOG.d(TAG, "addOneInputInfo - > error invalid param");
            return -1;
        }
        try {
            if (!setupSomeVendorTag(totalCaptureResult)) {
                LOG.d(TAG, "setupSomeVendorTag is error!!");
                if (this.mCountDownLatch != null) {
                    LOG.d(TAG, "mCountDownLatch.countDown() 5");
                    this.mCountDownLatch.countDown();
                }
                return -1;
            }
            InputInfo inputInfo = new InputInfo();
            inputInfo.curIndex = i;
            inputInfo.imgNum = i3;
            inputInfo.cameraState = 2;
            RawInfo rawInfo = new RawInfo();
            rawInfo.rawType = 0;
            getVendorTagValue(totalCaptureResult, rawInfo);
            Integer num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
            if (num != null) {
                rawInfo.evList[0] = num.intValue();
                inputInfo.inputImagesEV[0] = (float) num.intValue();
            }
            inputInfo.inputImages[0] = getRawImage(image, i2);
            i4 = this.mSuperNightJni.addOneInputInfo(rawInfo, inputInfo);
            if (num != null && num.intValue() == 0 && this.mMetdata == null) {
                this.mMetdata = totalCaptureResult;
            }
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            if (this.mCountDownLatch != null) {
                LOG.d(TAG, "mCountDownLatch.countDown() 5");
                this.mCountDownLatch.countDown();
            }
            throw th;
        }
    }

    public void cancelSuperNight() {
        SuperNightJni superNightJni = this.mSuperNightJni;
        if (superNightJni != null) {
            this.mIsCancel = true;
            superNightJni.cancelSuperNight();
            if (!this.mInit) {
                LOG.d(TAG, "mInit is false ,cancelSuperNight return!!!");
                return;
            }
            this.mCountDownLatch = new CountDownLatch(1);
            try {
                this.mCountDownLatch.await();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    public int init(int i, int i2, int i3, int i4) {
        SuperNightJni superNightJni = this.mSuperNightJni;
        if (superNightJni == null) {
            return -1;
        }
        superNightJni.init(i, i2, i3, i4);
        int preProcess = this.mSuperNightJni.preProcess();
        this.mIsCancel = false;
        this.mInit = true;
        LOG.d(TAG, "preprocess = " + preProcess);
        return preProcess;
    }

    @Override // com.arcsoft.supernight.ProgressCallback
    public void onProgress(int i, int i2) {
        LOG.d(TAG, "progress = " + i + " status = " + i2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x006c, code lost:
        if (r10.mCountDownLatch == null) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x006f, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0059, code lost:
        if (r10.mCountDownLatch != null) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x005b, code lost:
        com.arcsoft.supernight.LOG.d(com.arcsoft.supernight.SuperNightProcess.TAG, "mCountDownLatch.countDown() 4");
        r10.mCountDownLatch.countDown();
     */
    public int process(Image image, int i, Rect rect, int i2) {
        int i3 = -1;
        if (image == null || rect == null) {
            return -1;
        }
        try {
            FaceInfo faceInfo = getFaceInfo(this.mMetdata, image.getWidth(), image.getHeight());
            InputInfo inputInfo = new InputInfo();
            inputInfo.curIndex = 0;
            inputInfo.imgNum = i2;
            inputInfo.cameraState = 2;
            inputInfo.inputImages[0] = getRawImage(image, i);
            i3 = this.mSuperNightJni.process(faceInfo, inputInfo, 3, rect, this);
            LOG.d(TAG, "process = " + i3 + ", cropRect0 = " + rect.toString());
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            if (this.mCountDownLatch != null) {
                LOG.d(TAG, "mCountDownLatch.countDown() 4");
                this.mCountDownLatch.countDown();
            }
            throw th;
        }
    }

    public void readDebugFileValue() {
        File file = new File(DEBUG_FILE);
        if (file.exists() || file.isFile()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                int i = 0;
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    LOG.d(TAG, "dump file line = " + readLine);
                    if (!TextUtils.isEmpty(readLine)) {
                        if (i >= 2) {
                            break;
                        }
                        boolean z = true;
                        if (readLine.contains(DUMP_KEY)) {
                            String trim = readLine.trim();
                            String substring = trim.substring(trim.length() - 1);
                            LOG.d(TAG, "dump file value =" + substring);
                            if (Integer.parseInt(substring) != 1) {
                                z = false;
                            }
                            this.mDumpFile = z;
                        } else if (readLine.contains(LOG_KEY)) {
                            String trim2 = readLine.trim();
                            String substring2 = trim2.substring(trim2.length() - 1);
                            LOG.d(TAG, "debug log value =" + substring2);
                            if (Integer.parseInt(substring2) != 1) {
                                z = false;
                            }
                            LOG.DEBUG = z;
                            TimeConsumingUtil.DEBUG = LOG.DEBUG;
                        }
                        i++;
                    }
                }
                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            } catch (NumberFormatException e4) {
                e4.printStackTrace();
            }
        } else {
            LOG.d(TAG, "dump file return false 0");
        }
    }

    public void setEnableAdrcGain(boolean z) {
        this.mEnableAdrcGain = z;
    }

    public void setEnableBlackLevel(boolean z) {
        this.mEnableBlackLevel = z;
    }

    public void setEnableWbGain(boolean z) {
        this.mEnableWbGain = z;
    }

    public void setFaceOrientation(int i) {
        this.mFaceOrientation = i;
    }

    public int unInit() {
        SuperNightJni superNightJni = this.mSuperNightJni;
        if (superNightJni == null) {
            return -1;
        }
        int postProcess = superNightJni.postProcess();
        LOG.d(TAG, "postProcess = " + postProcess);
        int unInit = this.mSuperNightJni.unInit();
        LOG.d(TAG, "unInit = " + unInit);
        this.mMetdata = null;
        CountDownLatch countDownLatch = this.mCountDownLatch;
        if (countDownLatch != null && countDownLatch.getCount() > 0) {
            LOG.d(TAG, "mCountDownLatch.countDown() 6");
            this.mCountDownLatch.countDown();
        }
        this.mInit = false;
        return unInit;
    }
}
