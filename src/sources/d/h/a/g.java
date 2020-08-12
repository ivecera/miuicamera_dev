package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: CirclularEaseInInterpolator */
public class g implements Interpolator {
    public float getInterpolation(float f2) {
        return -((float) (Math.sqrt((double) (1.0f - (f2 * f2))) - 1.0d));
    }
}
