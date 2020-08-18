package com.miui.filtersdk.filter;

import com.miui.filtersdk.beauty.BeautyProcessor;
import com.miui.filtersdk.filter.base.BaseBeautyFilter;
import com.miui.filtersdk.filter.base.GPUImageFilter;
import com.miui.filtersdk.utils.OpenGlUtils;
import java.nio.FloatBuffer;

public class BeautificationFilter extends BaseBeautyFilter {
    private static final String TAG = "BeautificationFilter";
    private int mBeautyInputHeight;
    private int mBeautyInputWidth;
    private int[] mOutTexture = {-1};

    private void initBeauty() {
    }

    @Override // com.miui.filtersdk.filter.base.BaseBeautyFilter
    public void initBeautyProcessor(int i, int i2) {
        BeautyProcessor beautyProcessor = ((BaseBeautyFilter) this).mBeautyProcessor;
        if (beautyProcessor != null) {
            this.mBeautyInputWidth = i;
            this.mBeautyInputHeight = i2;
            beautyProcessor.init(i, i2);
        }
    }

    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public int onDrawFrame(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        ((GPUImageFilter) this).mTextureId = onDrawToTexture(i);
        return super.onDrawFrame(((GPUImageFilter) this).mTextureId, floatBuffer, floatBuffer2);
    }

    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public int onDrawFrame(FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        return -1;
    }

    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public int onDrawToTexture(int i) {
        OpenGlUtils.initEffectTexture(this.mBeautyInputWidth, this.mBeautyInputHeight, this.mOutTexture, 3553);
        ((BaseBeautyFilter) this).mBeautyProcessor.beautify(i, this.mBeautyInputWidth, this.mBeautyInputHeight, this.mOutTexture[0]);
        return this.mOutTexture[0];
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void onInit() {
        super.onInit();
        initBeauty();
    }
}
