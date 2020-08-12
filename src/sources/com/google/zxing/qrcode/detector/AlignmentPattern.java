package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class AlignmentPattern extends ResultPoint {
    private final float estimatedModuleSize;

    AlignmentPattern(float f2, float f3, float f4) {
        super(f2, f3);
        this.estimatedModuleSize = f4;
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
    public AlignmentPattern combineEstimate(float f2, float f3, float f4) {
        return new AlignmentPattern((getX() + f3) / 2.0f, (getY() + f2) / 2.0f, (this.estimatedModuleSize + f4) / 2.0f);
    }
}
