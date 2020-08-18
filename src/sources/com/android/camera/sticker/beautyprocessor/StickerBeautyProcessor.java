package com.android.camera.sticker.beautyprocessor;

import android.graphics.Bitmap;
import com.miui.filtersdk.beauty.BeautyParameterType;
import com.miui.filtersdk.beauty.IntelligentBeautyProcessor;
import com.sensetime.stmobile.STBeautifyNative;
import java.util.Map;

public class StickerBeautyProcessor extends IntelligentBeautyProcessor {
    private static final int ST_BEAUTIFY_CONTRAST_STRENGTH = 1;
    private static final int ST_BEAUTIFY_ENLARGE_EYE_RATIO = 5;
    private static final int ST_BEAUTIFY_ILLEGAL_PARAMETER = -1;
    private static final int ST_BEAUTIFY_SHRINK_FACE_RATIO = 6;
    private static final int ST_BEAUTIFY_SHRINK_JAW_RATIO = 7;
    private static final int ST_BEAUTIFY_SMOOTH_STRENGTH = 3;
    private static final int ST_BEAUTIFY_WHITEN_STRENGTH = 4;
    private STBeautifyNative mStBeautifyNative;

    /* renamed from: com.android.camera.sticker.beautyprocessor.StickerBeautyProcessor$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType = new int[BeautyParameterType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|(3:11|12|14)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.CONTRAST_STRENGTH.ordinal()] = 1;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.ENLARGE_EYE_RATIO.ordinal()] = 2;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.SHRINK_FACE_RATIO.ordinal()] = 3;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.SHRINK_JAW_RATIO.ordinal()] = 4;
            $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.WHITEN_STRENGTH.ordinal()] = 5;
            try {
                $SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[BeautyParameterType.SMOOTH_STRENGTH.ordinal()] = 6;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public StickerBeautyProcessor(STBeautifyNative sTBeautifyNative) {
        this.mStBeautifyNative = sTBeautifyNative;
        ((IntelligentBeautyProcessor) this).mLevelParameters = new float[][]{new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, new float[]{0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f}, new float[]{0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f}, new float[]{0.3f, 0.3f, 0.3f, 0.3f, 0.3f, 0.3f}, new float[]{0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f}, new float[]{0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f}};
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public int beautify(byte[] bArr, int i, int i2) {
        return 0;
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void beautify(int i, int i2, int i3, int i4) {
        this.mStBeautifyNative.processTexture(i, i2, i3, null, i4, null);
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void beautify(Bitmap bitmap, int i, int i2) {
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void beautify(byte[] bArr, int i, int i2, int i3, byte[] bArr2, int i4) {
        this.mStBeautifyNative.processBufferNotInGLContext(bArr, i, i2, i3, null, bArr2, i4, null);
    }

    @Override // com.miui.filtersdk.beauty.IntelligentBeautyProcessor
    public void clearBeautyParameters() {
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public BeautyParameterType[] getSupportedBeautyParamTypes() {
        return new BeautyParameterType[]{BeautyParameterType.CONTRAST_STRENGTH, BeautyParameterType.ENLARGE_EYE_RATIO, BeautyParameterType.SHRINK_FACE_RATIO, BeautyParameterType.SHRINK_JAW_RATIO, BeautyParameterType.WHITEN_STRENGTH, BeautyParameterType.SMOOTH_STRENGTH};
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public float[] getSupportedParamRange(BeautyParameterType beautyParameterType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[beautyParameterType.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return new float[]{0.0f, 1.0f};
            default:
                return new float[0];
        }
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void init(int i, int i2) {
        this.mStBeautifyNative.createInstance(i, i2);
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void onDisplaySizeChanged(int i, int i2) {
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void release() {
        this.mStBeautifyNative.destroyBeautify();
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void setBeautyParamDegree(BeautyParameterType beautyParameterType, Float f2) {
        int i;
        switch (AnonymousClass1.$SwitchMap$com$miui$filtersdk$beauty$BeautyParameterType[beautyParameterType.ordinal()]) {
            case 1:
                i = 1;
                break;
            case 2:
                i = 5;
                break;
            case 3:
                i = 6;
                break;
            case 4:
                i = 7;
                break;
            case 5:
                i = 4;
                break;
            case 6:
                i = 3;
                break;
            default:
                i = -1;
                break;
        }
        if (-1 != i) {
            this.mStBeautifyNative.setParam(i, f2.floatValue());
        }
    }

    @Override // com.miui.filtersdk.beauty.BeautyProcessor
    public void setBeautyParamsDegree(Map<BeautyParameterType, Float> map) {
        for (Map.Entry<BeautyParameterType, Float> entry : map.entrySet()) {
            setBeautyParamDegree(entry.getKey(), entry.getValue());
        }
    }
}
