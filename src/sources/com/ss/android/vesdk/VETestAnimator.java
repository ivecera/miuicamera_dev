package com.ss.android.vesdk;

public class VETestAnimator implements VEStickerAnimator {
    @Override // com.ss.android.vesdk.VEStickerAnimator
    public float getDegree(int i) {
        VELogUtil.d("VETestAnimator", "timestamp: " + i);
        return (((float) i) / 1000.0f) * 36.0f;
    }

    @Override // com.ss.android.vesdk.VEStickerAnimator
    public float getScaleX(int i) {
        return ((((float) i) / 1000.0f) * 0.1f) + 1.0f;
    }

    @Override // com.ss.android.vesdk.VEStickerAnimator
    public float getScaleY(int i) {
        return ((((float) i) / 1000.0f) * 0.1f) + 1.0f;
    }

    @Override // com.ss.android.vesdk.VEStickerAnimator
    public float getTransX(int i) {
        return (((float) i) / 1000.0f) * 0.1f;
    }

    @Override // com.ss.android.vesdk.VEStickerAnimator
    public float getTransY(int i) {
        return (((float) i) / 1000.0f) * 0.1f;
    }
}
