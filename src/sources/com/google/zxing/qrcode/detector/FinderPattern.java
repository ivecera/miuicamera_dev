package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class FinderPattern extends ResultPoint {
    private final int count;
    private final float estimatedModuleSize;

    FinderPattern(float f2, float f3, float f4) {
        this(f2, f3, f4, 1);
    }

    private FinderPattern(float f2, float f3, float f4, int i) {
        super(f2, f3);
        this.estimatedModuleSize = f4;
        this.count = i;
    }

    /* access modifiers changed from: package-private */
    public boolean aboutEquals(float f2, float f3, float f4) {
        if (Math.abs(f3 - getY()) > f2 || Math.abs(f4 - getX()) > f2) {
            return false;
        }
        float abs = Math.abs(f2 - this.estimatedModuleSize);
        return abs <= 1.0f || abs <= this.estimatedModuleSize;
    }

    /* access modifiers changed from: package-private */
    public FinderPattern combineEstimate(float f2, float f3, float f4) {
        int i = this.count;
        int i2 = i + 1;
        float x = (((float) i) * getX()) + f3;
        float f5 = (float) i2;
        return new FinderPattern(x / f5, ((((float) this.count) * getY()) + f2) / f5, ((((float) this.count) * this.estimatedModuleSize) + f4) / f5, i2);
    }

    /* access modifiers changed from: package-private */
    public int getCount() {
        return this.count;
    }

    public float getEstimatedModuleSize() {
        return this.estimatedModuleSize;
    }
}
