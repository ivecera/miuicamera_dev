package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: BounceEaseOutInterpolator */
public class f implements Interpolator {
    public float getInterpolation(float f2) {
        double d2 = (double) f2;
        if (d2 < 0.36363636363636365d) {
            return 7.5625f * f2 * f2;
        }
        if (d2 < 0.7272727272727273d) {
            float f3 = (float) (d2 - 0.5454545454545454d);
            return (7.5625f * f3 * f3) + 0.75f;
        } else if (d2 < 0.9090909090909091d) {
            float f4 = (float) (d2 - 0.8181818181818182d);
            return (7.5625f * f4 * f4) + 0.9375f;
        } else {
            float f5 = (float) (d2 - 0.9545454545454546d);
            return (7.5625f * f5 * f5) + 0.984375f;
        }
    }
}
