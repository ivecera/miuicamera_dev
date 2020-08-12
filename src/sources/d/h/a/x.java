package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: QuarticEaseInInterpolator */
public class x implements Interpolator {
    public float getInterpolation(float f2) {
        return f2 * f2 * f2 * f2;
    }
}
