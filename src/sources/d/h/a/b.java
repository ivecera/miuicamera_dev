package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: BackEaseInOutInterpolator */
public class b implements Interpolator {
    private final float cy;

    public b() {
        this(0.0f);
    }

    public b(float f2) {
        this.cy = f2;
    }

    public float getInterpolation(float f2) {
        float f3 = this.cy;
        if (f3 == 0.0f) {
            f3 = 1.70158f;
        }
        float f4 = f2 * 2.0f;
        if (f4 < 1.0f) {
            float f5 = (float) (((double) f3) * 1.525d);
            return f4 * f4 * (((1.0f + f5) * f4) - f5) * 0.5f;
        }
        float f6 = f4 - 2.0f;
        float f7 = (float) (((double) f3) * 1.525d);
        return ((f6 * f6 * (((1.0f + f7) * f6) + f7)) + 2.0f) * 0.5f;
    }
}
