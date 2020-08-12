package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: CirclularEaseOutInterpolator */
public class i implements Interpolator {
    public float getInterpolation(float f2) {
        float f3 = f2 - 1.0f;
        return (float) Math.sqrt((double) (1.0f - (f3 * f3)));
    }
}
