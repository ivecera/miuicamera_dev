package d.c.a.a;

import android.view.animation.Interpolator;

/* compiled from: CubicEaseInInterpolator */
public class b implements Interpolator {
    public float getInterpolation(float f2) {
        return f2 * f2 * f2;
    }
}
