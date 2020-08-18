package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: CirclularEaseInOutInterpolator */
public class h implements Interpolator {
    public float getInterpolation(float f2) {
        float f3;
        double sqrt;
        float f4 = f2 * 2.0f;
        if (f4 < 1.0f) {
            f3 = -0.5f;
            sqrt = Math.sqrt((double) (1.0f - (f4 * f4))) - 1.0d;
        } else {
            float f5 = f4 - 2.0f;
            f3 = 0.5f;
            sqrt = Math.sqrt((double) (1.0f - (f5 * f5))) + 1.0d;
        }
        return ((float) sqrt) * f3;
    }
}
