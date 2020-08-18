package com.android.camera.effect;

import android.graphics.Bitmap;
import com.android.camera.log.Log;
import com.miui.filtersdk.beauty.BeautyParameterType;
import com.miui.filtersdk.beauty.IntelligentBeautyProcessor;
import java.util.Locale;
import java.util.Map;

public class ArcsoftBeautyProcessor extends IntelligentBeautyProcessor {
    private static final float CAMERA_BEAUTY_PARAM_MAX = 10.0f;
    private static final float EXTRA_SPAN = 2.0f;
    private static final String TAG = "ArcsoftBeautyProcessor";
    private int mEnlargeEyeRatio;
    private int mShrinkFaceRatio;
    private int mSmoothStrength;
    private int mWhiteStrength;

    /* renamed from: com.android.camera.effect.ArcsoftBeautyProcessor$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType = new int[BeautyParameterType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.ENLARGE_EYE_RATIO.ordinal()] = 1;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.SHRINK_FACE_RATIO.ordinal()] = 2;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.WHITEN_STRENGTH.ordinal()] = 3;
            try {
                $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.SMOOTH_STRENGTH.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public ArcsoftBeautyProcessor() {
        setExtraSpan(2.0f);
        ((IntelligentBeautyProcessor) this).mLevelParameters = new float[][]{new float[]{0.0f, 0.0f, 0.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, new float[]{3.0f, 3.0f, 3.0f, 3.0f}, new float[]{5.0f, 5.0f, 5.0f, 5.0f}, new float[]{7.0f, 7.0f, 7.0f, 7.0f}, new float[]{8.0f, 8.0f, 8.0f, 8.0f}};
    }

    private void dumpParams() {
        Log.v(TAG, String.format(Locale.ENGLISH, "beautyParams: shrinkFace=%d largeEye=%d whiteSkin=%d smoothSkin=%d", Integer.valueOf(this.mShrinkFaceRatio), Integer.valueOf(this.mEnlargeEyeRatio), Integer.valueOf(this.mWhiteStrength), Integer.valueOf(this.mSmoothStrength)));
    }

    private int getDegreeByValue(BeautyParameterType beautyParameterType, float f2) {
        float[] supportedParamRange = getSupportedParamRange(beautyParameterType);
        return supportedParamRange.length < 2 ? Math.round(f2) : Math.round(((f2 - supportedParamRange[0]) / supportedParamRange[1]) * 10.0f);
    }

    private void updateBeautyParameter(BeautyParameterType beautyParameterType, Float f2) {
        int degreeByValue = getDegreeByValue(beautyParameterType, f2 != null ? f2.floatValue() : 0.0f);
        int i = AnonymousClass1.$SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[beautyParameterType.ordinal()];
        if (i == 1) {
            this.mEnlargeEyeRatio = degreeByValue;
        } else if (i == 2) {
            this.mShrinkFaceRatio = degreeByValue;
        } else if (i == 3) {
            this.mWhiteStrength = degreeByValue;
        } else if (i == 4) {
            this.mSmoothStrength = degreeByValue;
        }
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public int beautify(byte[] bArr, int i, int i2) {
        return 0;
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void beautify(int i, int i2, int i3, int i4) {
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void beautify(Bitmap bitmap, int i, int i2) {
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void beautify(byte[] bArr, int i, int i2, int i3, byte[] bArr2, int i4) {
    }

    @Override // com.miui.filtersdk.beauty.IntelligentBeautyProcessor
    public void clearBeautyParameters() {
        this.mShrinkFaceRatio = 0;
        this.mEnlargeEyeRatio = 0;
        this.mWhiteStrength = 0;
        this.mSmoothStrength = 0;
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public BeautyParameterType[] getSupportedBeautyParamTypes() {
        return new BeautyParameterType[]{BeautyParameterType.ENLARGE_EYE_RATIO, BeautyParameterType.SHRINK_FACE_RATIO, BeautyParameterType.WHITEN_STRENGTH, BeautyParameterType.SMOOTH_STRENGTH};
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public float[] getSupportedParamRange(BeautyParameterType beautyParameterType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[beautyParameterType.ordinal()];
        return (i == 1 || i == 2 || i == 3 || i == 4) ? new float[]{0.0f, 10.0f} : new float[0];
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void init(int i, int i2) {
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void onDisplaySizeChanged(int i, int i2) {
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void release() {
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void setBeautyParamDegree(BeautyParameterType beautyParameterType, Float f2) {
        updateBeautyParameter(beautyParameterType, Float.valueOf(f2.floatValue()));
        dumpParams();
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void setBeautyParamsDegree(Map<BeautyParameterType, Float> map) {
        for (Map.Entry<BeautyParameterType, Float> entry : map.entrySet()) {
            updateBeautyParameter(entry.getKey(), entry.getValue());
        }
        dumpParams();
    }
}
