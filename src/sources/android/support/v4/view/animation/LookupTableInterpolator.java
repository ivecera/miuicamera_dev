package android.support.v4.view.animation;

import android.view.animation.Interpolator;

abstract class LookupTableInterpolator implements Interpolator {
    private final float mStepSize = (1.0f / ((float) (this.mValues.length - 1)));
    private final float[] mValues;

    protected LookupTableInterpolator(float[] fArr) {
        this.mValues = fArr;
    }

    public float getInterpolation(float f2) {
        if (f2 >= 1.0f) {
            return 1.0f;
        }
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        float[] fArr = this.mValues;
        int min = Math.min((int) (((float) (fArr.length - 1)) * f2), fArr.length - 2);
        float f3 = this.mStepSize;
        float[] fArr2 = this.mValues;
        return fArr2[min] + (((f2 - (((float) min) * f3)) / f3) * (fArr2[min + 1] - fArr2[min]));
    }
}
