package com.miui.filtersdk.beauty;

import android.graphics.Bitmap;
import com.miui.filtersdk.BeautyArcsoftJni;
import java.util.Map;

public class ArcsoftBeautyProcessor extends IntelligentBeautyProcessor {
    private static final int BEAUTY_PARAMETER_SIZE = 11;
    private static final float EXTRA_SPAN = 50.0f;
    private int[] mBeautyParameters = new int[11];
    private int mBrightEyeRatio;
    private int mDeblemish;
    private int mDepouchRatio;
    private int mEnlargeEyeRatio;
    private int mIrisShineRatio;
    private int mLipBeautyRatio;
    private int mRelightingRatio;
    private int mRuddyRatio;
    private int mShrinkFaceRatio;
    private int mShrinkNooseRatio;
    private int mSmoothStrength;
    private int mWhiteStrength;

    /* renamed from: com.miui.filtersdk.beauty.ArcsoftBeautyProcessor$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType = new int[BeautyParameterType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|(3:23|24|26)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(26:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|26) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x007a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0086 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.ENLARGE_EYE_RATIO.ordinal()] = 1;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.SHRINK_FACE_RATIO.ordinal()] = 2;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.WHITEN_STRENGTH.ordinal()] = 3;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.SMOOTH_STRENGTH.ordinal()] = 4;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.BRIGHT_EYE_RATIO.ordinal()] = 5;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.RUDDY_STRENGTH.ordinal()] = 6;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.RELIGHTING_RATIO.ordinal()] = 7;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.LIP_BEAUTY_RATIO.ordinal()] = 8;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.DEPOUCH_RATIO.ordinal()] = 9;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.IRIS_SHINE_RATIO.ordinal()] = 10;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.SHRINK_NOSE_RATIO.ordinal()] = 11;
            try {
                $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.DEBLEMISH.ordinal()] = 12;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    ArcsoftBeautyProcessor() {
        for (int i = 0; i < 11; i++) {
            this.mBeautyParameters[i] = 0;
        }
        setExtraSpan(EXTRA_SPAN);
        ((IntelligentBeautyProcessor) this).mLevelParameters = new float[][]{new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, new float[]{10.0f, 20.0f, 10.0f, 30.0f, 15.0f, 10.0f, 1.0f, 70.0f, 0.0f, 25.0f, 10.0f, 0.0f}, new float[]{10.0f, 28.0f, 10.0f, 30.0f, 15.0f, 10.0f, 1.0f, 70.0f, 0.0f, 25.0f, 10.0f, 0.0f}, new float[]{10.0f, 35.0f, 10.0f, 30.0f, 15.0f, 10.0f, 1.0f, 70.0f, 0.0f, 25.0f, 10.0f, 0.0f}, new float[]{10.0f, 42.0f, 10.0f, 30.0f, 15.0f, 15.0f, 1.0f, 70.0f, 0.0f, 25.0f, 10.0f, 0.0f}, new float[]{10.0f, EXTRA_SPAN, 10.0f, 30.0f, 15.0f, 15.0f, 1.0f, 70.0f, 0.0f, 25.0f, 10.0f, 0.0f}};
    }

    private boolean isParametersEmpty() {
        return this.mBrightEyeRatio == 0 && this.mSmoothStrength == 0 && this.mWhiteStrength == 0 && this.mShrinkFaceRatio == 0 && this.mEnlargeEyeRatio == 0 && this.mDeblemish == 0 && this.mDepouchRatio == 0 && this.mIrisShineRatio == 0 && this.mLipBeautyRatio == 0 && this.mRelightingRatio == 0 && this.mRuddyRatio == 0 && this.mShrinkNooseRatio == 0;
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public int beautify(byte[] bArr, int i, int i2) {
        if (isParametersEmpty()) {
            return 0;
        }
        BeautyArcsoftJni.beautifyProcess(bArr, i, i2, this.mWhiteStrength, this.mSmoothStrength, this.mEnlargeEyeRatio, this.mShrinkFaceRatio, this.mBrightEyeRatio, this.mDeblemish, this.mDepouchRatio, this.mIrisShineRatio, this.mLipBeautyRatio, this.mRelightingRatio, this.mRuddyRatio, this.mShrinkNooseRatio);
        return 0;
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void beautify(int i, int i2, int i3, int i4) {
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void beautify(Bitmap bitmap, int i, int i2) {
        if (!isParametersEmpty()) {
            BeautyArcsoftJni.beautifyProcessBitmap(bitmap, i, i2, this.mWhiteStrength, this.mSmoothStrength, this.mEnlargeEyeRatio, this.mShrinkFaceRatio, this.mBrightEyeRatio, this.mDeblemish, this.mDepouchRatio, this.mIrisShineRatio, this.mLipBeautyRatio, this.mRelightingRatio, this.mRuddyRatio, this.mShrinkNooseRatio);
        }
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void beautify(byte[] bArr, int i, int i2, int i3, byte[] bArr2, int i4) {
        if (!isParametersEmpty()) {
            BeautyArcsoftJni.beautifyProcess(bArr, i2, i3, this.mWhiteStrength, this.mSmoothStrength, this.mEnlargeEyeRatio, this.mShrinkFaceRatio, this.mBrightEyeRatio, this.mDeblemish, this.mDepouchRatio, this.mIrisShineRatio, this.mLipBeautyRatio, this.mRelightingRatio, this.mRuddyRatio, this.mShrinkNooseRatio);
        }
    }

    @Override // com.miui.filtersdk.beauty.IntelligentBeautyProcessor
    public void clearBeautyParameters() {
        this.mBrightEyeRatio = 0;
        this.mSmoothStrength = 0;
        this.mWhiteStrength = 0;
        this.mShrinkFaceRatio = 0;
        this.mEnlargeEyeRatio = 0;
        this.mIrisShineRatio = 0;
        this.mDeblemish = 0;
        this.mDepouchRatio = 0;
        this.mRelightingRatio = 0;
        this.mLipBeautyRatio = 0;
        this.mRuddyRatio = 0;
        this.mShrinkNooseRatio = 0;
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public BeautyParameterType[] getSupportedBeautyParamTypes() {
        return new BeautyParameterType[]{BeautyParameterType.WHITEN_STRENGTH, BeautyParameterType.SMOOTH_STRENGTH, BeautyParameterType.ENLARGE_EYE_RATIO, BeautyParameterType.SHRINK_FACE_RATIO, BeautyParameterType.BRIGHT_EYE_RATIO, BeautyParameterType.IRIS_SHINE_RATIO, BeautyParameterType.DEBLEMISH, BeautyParameterType.DEPOUCH_RATIO, BeautyParameterType.RELIGHTING_RATIO, BeautyParameterType.LIP_BEAUTY_RATIO, BeautyParameterType.RUDDY_STRENGTH, BeautyParameterType.SHRINK_NOSE_RATIO};
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public float[] getSupportedParamRange(BeautyParameterType beautyParameterType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[beautyParameterType.ordinal()]) {
            case 1:
                return new float[]{0.0f, 40.0f};
            case 2:
                return new float[]{0.0f, 100.0f};
            case 3:
                return new float[]{0.0f, 70.0f};
            case 4:
                return new float[]{0.0f, 70.0f};
            case 5:
                return new float[]{0.0f, 40.0f};
            case 6:
                return new float[]{0.0f, 80.0f};
            case 7:
                return new float[]{0.0f, 60.0f};
            case 8:
                return new float[]{0.0f, 100.0f};
            case 9:
                return new float[]{0.0f, 100.0f};
            case 10:
                return new float[]{0.0f, 40.0f};
            case 11:
                return new float[]{0.0f, 100.0f};
            case 12:
                return new float[]{0.0f, 1.0f};
            default:
                return new float[0];
        }
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
        switch (AnonymousClass1.$SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[beautyParameterType.ordinal()]) {
            case 1:
                this.mEnlargeEyeRatio = Math.round(f2.floatValue());
                return;
            case 2:
                this.mShrinkFaceRatio = Math.round(f2.floatValue());
                return;
            case 3:
                this.mWhiteStrength = Math.round(f2.floatValue());
                return;
            case 4:
                this.mSmoothStrength = Math.round(f2.floatValue());
                return;
            case 5:
                this.mBrightEyeRatio = Math.round(f2.floatValue());
                return;
            case 6:
                this.mRuddyRatio = Math.round(f2.floatValue());
                return;
            case 7:
                this.mRelightingRatio = Math.round(f2.floatValue());
                return;
            case 8:
                this.mLipBeautyRatio = Math.round(f2.floatValue());
                return;
            case 9:
                this.mDepouchRatio = Math.round(f2.floatValue());
                return;
            case 10:
                this.mIrisShineRatio = Math.round(f2.floatValue());
                return;
            case 11:
                this.mShrinkNooseRatio = Math.round(f2.floatValue());
                return;
            case 12:
                this.mDeblemish = Math.round(f2.floatValue());
                return;
            default:
                return;
        }
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void setBeautyParamsDegree(Map<BeautyParameterType, Float> map) {
        for (Map.Entry<BeautyParameterType, Float> entry : map.entrySet()) {
            setBeautyParamDegree(entry.getKey(), entry.getValue());
        }
    }
}
