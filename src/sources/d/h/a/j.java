package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: CubicEaseInInterpolator */
public class j implements Interpolator {
    public float getInterpolation(float f2) {
        return f2 * f2 * f2;
    }
}
