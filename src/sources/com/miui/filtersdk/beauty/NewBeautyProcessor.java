package com.miui.filtersdk.beauty;

import android.graphics.Bitmap;
import com.miui.filtersdk.BeautifyJni;
import java.util.Map;

class NewBeautyProcessor extends IntelligentBeautyProcessor {
    private static final int BEAUTY_PARAMETER_SIZE = 6;
    private static final float EXTRA_SPAN = 50.0f;
    private static final int NEW_BEAUTIFY_ENLARGE_EYE_RATIO = 2;
    private static final int NEW_BEAUTIFY_ILLEGAL_PARAMETER = -1;
    private static final int NEW_BEAUTIFY_RUDDY_STRENGTH = 4;
    private static final int NEW_BEAUTIFY_SHRINK_FACE_RATIO = 3;
    private static final int NEW_BEAUTIFY_SMOOTH_STRENGTH = 1;
    private static final int NEW_BEAUTIFY_WHITEN_STRENGTH = 0;
    private int[] mBeautyParameters = new int[6];

    /* renamed from: com.miui.filtersdk.beauty.NewBeautyProcessor$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType = new int[BeautyParameterType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.ENLARGE_EYE_RATIO.ordinal()] = 1;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.SHRINK_FACE_RATIO.ordinal()] = 2;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.WHITEN_STRENGTH.ordinal()] = 3;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.SMOOTH_STRENGTH.ordinal()] = 4;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.RUDDY_STRENGTH.ordinal()] = 5;
        }
    }

    NewBeautyProcessor() {
        for (int i = 0; i < 6; i++) {
            this.mBeautyParameters[i] = 0;
        }
        setExtraSpan(EXTRA_SPAN);
        ((IntelligentBeautyProcessor) this).mLevelParameters = new float[][]{new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, new float[]{10.0f, 10.0f, 10.0f, 10.0f, 0.0f}, new float[]{20.0f, 20.0f, 20.0f, 20.0f, 0.0f}, new float[]{30.0f, 30.0f, 30.0f, 30.0f, 0.0f}, new float[]{EXTRA_SPAN, EXTRA_SPAN, EXTRA_SPAN, EXTRA_SPAN, 0.0f}, new float[]{60.0f, 60.0f, 60.0f, 60.0f, 0.0f}};
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public int beautify(byte[] bArr, int i, int i2) {
        BeautifyJni.beautifyFaceUpdateIntensity(this.mBeautyParameters);
        int[] iArr = new int[1];
        BeautifyJni.beautifyFaceYUVPreviewGPU(bArr, i, i2, ((BeautyProcessor) this).mRotation, iArr);
        return iArr[0];
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
        for (int i = 0; i < 6; i++) {
            this.mBeautyParameters[i] = 0;
        }
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public BeautyParameterType[] getSupportedBeautyParamTypes() {
        return new BeautyParameterType[]{BeautyParameterType.WHITEN_STRENGTH, BeautyParameterType.SMOOTH_STRENGTH, BeautyParameterType.ENLARGE_EYE_RATIO, BeautyParameterType.SHRINK_FACE_RATIO};
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public float[] getSupportedParamRange(BeautyParameterType beautyParameterType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[beautyParameterType.ordinal()];
        return (i == 1 || i == 2 || i == 3 || i == 4) ? new float[]{0.0f, 100.0f} : new float[0];
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void init(int i, int i2) {
        BeautifyJni.Initbeautify();
        BeautifyJni.beautifyFaceSetScreenSize(i, i2);
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void onDisplaySizeChanged(int i, int i2) {
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void release() {
        BeautifyJni.Cleanbeautify();
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void setBeautyParamDegree(BeautyParameterType beautyParameterType, Float f2) {
        int i = AnonymousClass1.$SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[beautyParameterType.ordinal()];
        char c2 = i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? (char) 65535 : 4 : 1 : 0 : 3 : 2;
        if (65535 != c2) {
            this.mBeautyParameters[c2] = Math.round(f2.floatValue());
            BeautifyJni.beautifyFaceUpdateIntensity(this.mBeautyParameters);
        }
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void setBeautyParamsDegree(Map<BeautyParameterType, Float> map) {
        for (Map.Entry<BeautyParameterType, Float> entry : map.entrySet()) {
            setBeautyParamDegree(entry.getKey(), entry.getValue());
        }
    }
}
