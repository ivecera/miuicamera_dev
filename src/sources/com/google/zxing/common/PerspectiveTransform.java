package com.google.zxing.common;

public final class PerspectiveTransform {
    private final float a11;
    private final float a12;
    private final float a13;
    private final float a21;
    private final float a22;
    private final float a23;
    private final float a31;
    private final float a32;
    private final float a33;

    private PerspectiveTransform(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        this.a11 = f2;
        this.a12 = f5;
        this.a13 = f8;
        this.a21 = f3;
        this.a22 = f6;
        this.a23 = f9;
        this.a31 = f4;
        this.a32 = f7;
        this.a33 = f10;
    }

    public static PerspectiveTransform quadrilateralToQuadrilateral(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17) {
        return squareToQuadrilateral(f10, f11, f12, f13, f14, f15, f16, f17).times(quadrilateralToSquare(f2, f3, f4, f5, f6, f7, f8, f9));
    }

    public static PerspectiveTransform quadrilateralToSquare(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        return squareToQuadrilateral(f2, f3, f4, f5, f6, f7, f8, f9).buildAdjoint();
    }

    public static PerspectiveTransform squareToQuadrilateral(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float f10 = ((f2 - f4) + f6) - f8;
        float f11 = ((f3 - f5) + f7) - f9;
        if (f10 == 0.0f && f11 == 0.0f) {
            return new PerspectiveTransform(f4 - f2, f6 - f4, f2, f5 - f3, f7 - f5, f3, 0.0f, 0.0f, 1.0f);
        }
        float f12 = f4 - f6;
        float f13 = f8 - f6;
        float f14 = f5 - f7;
        float f15 = f9 - f7;
        float f16 = (f12 * f15) - (f13 * f14);
        float f17 = ((f15 * f10) - (f13 * f11)) / f16;
        float f18 = ((f12 * f11) - (f10 * f14)) / f16;
        return new PerspectiveTransform((f17 * f4) + (f4 - f2), (f18 * f8) + (f8 - f2), f2, (f5 - f3) + (f17 * f5), (f9 - f3) + (f18 * f9), f3, f17, f18, 1.0f);
    }

    /* access modifiers changed from: package-private */
    public PerspectiveTransform buildAdjoint() {
        float f2 = this.a22;
        float f3 = this.a33;
        float f4 = this.a23;
        float f5 = this.a32;
        float f6 = (f2 * f3) - (f4 * f5);
        float f7 = this.a31;
        float f8 = this.a21;
        float f9 = (f4 * f7) - (f8 * f3);
        float f10 = (f8 * f5) - (f2 * f7);
        float f11 = this.a13;
        float f12 = this.a12;
        float f13 = this.a11;
        return new PerspectiveTransform(f6, f9, f10, (f11 * f5) - (f12 * f3), (f3 * f13) - (f11 * f7), (f7 * f12) - (f5 * f13), (f12 * f4) - (f11 * f2), (f11 * f8) - (f4 * f13), (f13 * f2) - (f12 * f8));
    }

    /* access modifiers changed from: package-private */
    public PerspectiveTransform times(PerspectiveTransform perspectiveTransform) {
        float f2 = this.a11;
        float f3 = perspectiveTransform.a11;
        float f4 = this.a21;
        float f5 = perspectiveTransform.a12;
        float f6 = this.a31;
        float f7 = perspectiveTransform.a13;
        float f8 = (f2 * f3) + (f4 * f5) + (f6 * f7);
        float f9 = perspectiveTransform.a21;
        float f10 = perspectiveTransform.a22;
        float f11 = perspectiveTransform.a23;
        float f12 = (f2 * f9) + (f4 * f10) + (f6 * f11);
        float f13 = perspectiveTransform.a31;
        float f14 = perspectiveTransform.a32;
        float f15 = perspectiveTransform.a33;
        float f16 = (f2 * f13) + (f4 * f14) + (f6 * f15);
        float f17 = this.a12;
        float f18 = this.a22;
        float f19 = this.a32;
        float f20 = (f19 * f15) + (f17 * f13) + (f18 * f14);
        float f21 = this.a13;
        float f22 = this.a23;
        float f23 = this.a33;
        return new PerspectiveTransform(f8, f12, f16, (f17 * f3) + (f18 * f5) + (f19 * f7), (f17 * f9) + (f18 * f10) + (f19 * f11), f20, (f7 * f23) + (f3 * f21) + (f5 * f22), (f9 * f21) + (f10 * f22) + (f11 * f23), (f21 * f13) + (f22 * f14) + (f23 * f15));
    }

    public void transformPoints(float[] fArr) {
        float f2 = this.a11;
        float f3 = this.a12;
        float f4 = this.a13;
        float f5 = this.a21;
        float f6 = this.a22;
        float f7 = this.a23;
        float f8 = this.a31;
        float f9 = this.a32;
        float f10 = this.a33;
        int length = fArr.length - 1;
        for (int i = 0; i < length; i += 2) {
            float f11 = fArr[i];
            int i2 = i + 1;
            float f12 = fArr[i2];
            float f13 = (f4 * f11) + (f7 * f12) + f10;
            fArr[i] = (((f2 * f11) + (f5 * f12)) + f8) / f13;
            fArr[i2] = (((f11 * f3) + (f12 * f6)) + f9) / f13;
        }
    }

    public void transformPoints(float[] fArr, float[] fArr2) {
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            float f2 = fArr[i];
            float f3 = fArr2[i];
            float f4 = (this.a13 * f2) + (this.a23 * f3) + this.a33;
            fArr[i] = (((this.a11 * f2) + (this.a21 * f3)) + this.a31) / f4;
            fArr2[i] = (((this.a12 * f2) + (this.a22 * f3)) + this.a32) / f4;
        }
    }
}
