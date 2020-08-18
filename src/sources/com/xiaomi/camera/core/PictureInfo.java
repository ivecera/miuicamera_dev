package com.xiaomi.camera.core;

import android.annotation.SuppressLint;
import android.hardware.camera2.params.MeteringRectangle;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.camera.base.RsaUtil;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class PictureInfo {
    public static final String AF_ROI = "afRoi";
    public static final String AISCENE = "AIScene";
    private static final String BABY = "Baby";
    private static final String BEAUTY_LEVEL = "BeautyLevel";
    public static final String EXPOSURE_VALUE = "exposureValue";
    public static final String FACE_RECOGNITION = "FaceRoi";
    private static final String FEMALE = "Female";
    public static final String FILTER = "FilterId";
    private static final String HDR_TYPE = "Hdr";
    public static final String LENS_APERTUES = "LensApertues";
    public static final String LENS_FOCAL = "LensFocal";
    private static final String MALE = "Male";
    private static final float MAX_BABY_AGE = 6.0f;
    private static final float MAX_GENDER_FEMALE = 0.4f;
    private static final float MIN_BABY_AGE = 0.0f;
    private static final float MIN_GENDER_MALE = 0.6f;
    private static final String MIRROR = "mirror";
    private static final String NIGHTSCENE = "NightScene";
    private static final String OPMODE = "OpMode";
    public static final String PREVIEW_SUPER_NIGHT_EXIF = "preview_super_night_exif";
    public static final String SCENE_MANUALLY = "SceneManually";
    public static final String SCENE_PANORAMA = "ScenePanorama";
    public static final String SCENE_PROFESSION = "SceneProfession";
    public static final String SCENE_SHOT_BURST = "SceneShotburst";
    public static final String SENSOR_TYPE = "　sensor_type";
    public static final String SENSOR_TYPE_FRONT = "front";
    public static final String SENSOR_TYPE_REAR = "rear";
    public static final String SENSOR_TYPE_REAR_MACRO = "_RearMacro";
    public static final String SENSOR_TYPE_REAR_TELE = "_RearTele";
    public static final String SENSOR_TYPE_REAR_TELE4x = "_RearTele4x";
    public static final String SENSOR_TYPE_REAR_ULTRA = "_RearUltra";
    public static final String SENSOR_TYPE_REAR_WIDE = "_RearWide";
    private static final String TAG = "PictureInfo";
    public static final String ZOOM_MULTIPLE = "ZoomMultiple";
    private boolean aiEnabled;
    private int aiType;
    private boolean isBokehFrontCamera;
    private boolean isMirror;
    private MeteringRectangle mAfMrRoi;
    private String mAfRoi;
    private String mAlgoExif;
    private String mCaptureResultInfo;
    private int mEvValue;
    private int mFilterId;
    private transient JSONObject mInfo = new JSONObject();
    private String mInfoString;
    private boolean mIsPanorama;
    private boolean mIsProfession;
    private boolean mIsShotBurst;
    private float mLensApertues;
    private String mLensType;
    private float mLensfocal;
    private int mOperateMode;
    private String mPreviewSuperNightExif;
    private String mSensorType = SENSOR_TYPE_REAR;
    private String mSuperNightExif;
    private int mXStart;
    private int mYStart;
    private float mZoomMulti;
    private String mfaceInfo;

    public void end() {
        this.mInfoString = this.mInfo.toString();
        this.mInfo = null;
    }

    public int getAiType() {
        return this.aiType;
    }

    public String getInfoString() {
        return this.mInfoString;
    }

    public int getOperateMode() {
        return this.mOperateMode;
    }

    public String getPreviewSuperNightExif() {
        return this.mPreviewSuperNightExif;
    }

    public byte[] getXpCommentBytes() {
        byte[] bArr = new byte[0];
        StringBuilder sb = new StringBuilder(1024);
        sb.append("　sensor_type:" + this.mLensType + " ");
        sb.append("exposureValue:" + this.mEvValue + " ");
        sb.append("SceneShotburst:" + this.mIsShotBurst + " ");
        sb.append("LensApertues:" + this.mLensApertues + " ");
        sb.append("LensFocal:" + this.mLensfocal + " ");
        sb.append("SceneProfession:" + this.mIsProfession + " ");
        sb.append("ScenePanorama:" + isPanorama() + " ");
        sb.append("ZoomMultiple:" + this.mZoomMulti + " ");
        sb.append("afRoi:" + this.mAfRoi + " ");
        sb.append("FaceRoi:" + this.mfaceInfo + " ");
        sb.append("FilterId:" + this.mFilterId + " ");
        sb.append("AIScene:" + getAiType() + " ");
        sb.append("preview_super_night_exif:" + getPreviewSuperNightExif() + " ");
        String str = this.mCaptureResultInfo;
        if (str != null) {
            sb.append(str);
        }
        String str2 = this.mAlgoExif;
        if (str2 != null) {
            sb.append(str2);
        }
        if (!TextUtils.isEmpty(this.mSuperNightExif)) {
            sb.append(this.mSuperNightExif);
        }
        try {
            return RsaUtil.encryptByPublicKey(sb.toString().getBytes("UTF-16LE"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return bArr;
        } catch (Exception e3) {
            e3.printStackTrace();
            return bArr;
        }
    }

    public boolean isAiEnabled() {
        return this.aiEnabled;
    }

    public boolean isBokehFrontCamera() {
        return this.isBokehFrontCamera;
    }

    public boolean isFrontCamera() {
        return this.mSensorType == "front";
    }

    public boolean isFrontMirror() {
        return this.isMirror;
    }

    public boolean isPanorama() {
        return this.mIsPanorama;
    }

    public String setAfRoi(int i, int i2, int i3) {
        int i4 = i % 360;
        if (i4 < 0) {
            i4 += 360;
        }
        if (this.mXStart == 0 && this.mYStart == 0) {
            this.mAfRoi = "0";
            return this.mAfRoi;
        }
        if (i4 < 90) {
            this.mAfRoi = "[x=" + this.mXStart + ",y=" + this.mYStart + ",width=" + this.mAfMrRoi.getWidth() + ",height=" + this.mAfMrRoi.getHeight() + "]";
        } else if (i4 < 180) {
            this.mAfRoi = "[x=" + (i3 - this.mYStart) + ",y=" + this.mXStart + ",width=" + this.mAfMrRoi.getWidth() + ",height=" + this.mAfMrRoi.getHeight() + "]";
        } else if (i4 < 270) {
            this.mAfRoi = "[x=" + (i2 - this.mXStart) + ",y=" + (i3 - this.mYStart) + ",width=" + this.mAfMrRoi.getWidth() + ",height=" + this.mAfMrRoi.getHeight() + "]";
        } else {
            this.mAfRoi = "[x=" + this.mYStart + ",y=" + (i2 - this.mXStart) + ",width=" + this.mAfMrRoi.getWidth() + ",height=" + this.mAfMrRoi.getHeight() + "]";
        }
        return this.mAfRoi;
    }

    public PictureInfo setAiEnabled(boolean z) {
        this.aiEnabled = z;
        return this;
    }

    public PictureInfo setAiType(int i) {
        this.aiType = i;
        try {
            this.mInfo.put(AISCENE, i);
        } catch (JSONException e2) {
            Log.e(TAG, "setAIScene JSONException occurs ", e2);
        }
        return this;
    }

    public void setAlgoExif(String str) {
        this.mAlgoExif = str;
    }

    public PictureInfo setBaby(float[] fArr) {
        if (fArr != null && fArr.length > 0) {
            float f2 = fArr[0];
            if (f2 > 0.0f && f2 <= 6.0f) {
                try {
                    this.mInfo.put(BABY, (double) f2);
                } catch (JSONException e2) {
                    Log.e(TAG, "setBaby JSONException occurs ", e2);
                }
            }
        }
        return this;
    }

    public PictureInfo setBeautyLevel(String str) {
        try {
            this.mInfo.put(BEAUTY_LEVEL, str);
        } catch (JSONException e2) {
            Log.e(TAG, "setBeautyLevel JSONException occurs ", e2);
        }
        return this;
    }

    public PictureInfo setBokehFrontCamera(boolean z) {
        this.isBokehFrontCamera = z;
        return this;
    }

    public void setCaptureResult(String str) {
        this.mCaptureResultInfo = str;
    }

    public void setEvValue(int i) {
        this.mEvValue = i;
    }

    public void setFaceRoi(String str) {
        this.mfaceInfo = str;
    }

    public void setFilter(int i) {
        this.mFilterId = i;
    }

    public PictureInfo setFrontMirror(boolean z) {
        this.isMirror = z;
        try {
            this.mInfo.put(MIRROR, z);
        } catch (JSONException e2) {
            Log.e(TAG, "setFrontMirror JSONException occurs ", e2);
        }
        return this;
    }

    public PictureInfo setGender(float[] fArr) {
        int i;
        if (fArr != null) {
            try {
                i = fArr.length;
            } catch (JSONException e2) {
                Log.e(TAG, "setGender JSONException occurs ", e2);
            }
        } else {
            i = 0;
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < i; i4++) {
            if (fArr[i4] >= 0.6f) {
                i2++;
            }
            if (fArr[i4] <= 0.4f) {
                i3++;
            }
        }
        this.mInfo.put(MALE, i2);
        this.mInfo.put(FEMALE, i3);
        return this;
    }

    public PictureInfo setHdrType(String str) {
        try {
            this.mInfo.put(HDR_TYPE, str);
        } catch (JSONException e2) {
            Log.e(TAG, "setHdrType JSONException occurs ", e2);
        }
        return this;
    }

    public void setLensApertues(float f2) {
        this.mLensApertues = f2;
    }

    public void setLensType(String str) {
        this.mLensType = str;
    }

    public void setLensfocal(float f2) {
        this.mLensfocal = f2;
    }

    public PictureInfo setNightScene(int i) {
        try {
            this.mInfo.put(NIGHTSCENE, i);
        } catch (JSONException e2) {
            Log.e(TAG, "setNightScene JSONException occurs ", e2);
        }
        return this;
    }

    public PictureInfo setOpMode(int i) {
        try {
            this.mInfo.put(OPMODE, i);
        } catch (JSONException e2) {
            Log.e(TAG, "setOpMode JSONException occurs ", e2);
        }
        return this;
    }

    public void setOperateMode(int i) {
        this.mOperateMode = i;
    }

    public void setPanorama(boolean z) {
        this.mIsPanorama = z;
    }

    public void setPreviewSuperNightExif(String str) {
        this.mPreviewSuperNightExif = str;
    }

    public void setProfession(boolean z) {
        this.mIsProfession = z;
    }

    public PictureInfo setSensorType(boolean z) {
        this.mSensorType = z ? "front" : SENSOR_TYPE_REAR;
        try {
            this.mInfo.put(SENSOR_TYPE, this.mSensorType);
        } catch (JSONException e2) {
            Log.e(TAG, "setSensorType JSONException occurs ", e2);
        }
        return this;
    }

    public void setShotBurst(boolean z) {
        this.mIsShotBurst = z;
    }

    public void setSuperNightExif(String str) {
        this.mSuperNightExif = str;
    }

    @SuppressLint({"NewApi"})
    public void setTouchRoi(MeteringRectangle meteringRectangle) {
        if (meteringRectangle == null) {
            this.mAfRoi = "0";
            return;
        }
        this.mAfMrRoi = meteringRectangle;
        this.mXStart = meteringRectangle.getX();
        this.mYStart = meteringRectangle.getY();
    }

    public void setZoomMulti(float f2) {
        this.mZoomMulti = f2;
    }
}
