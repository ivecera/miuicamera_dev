package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: CubicEaseOutInterpolater */
public class l implements Interpolator {
    public float getInterpolation(float f2) {
        float f3 = f2 - 1.0f;
        return (f3 * f3 * f3) + 1.0f;
    }
}
