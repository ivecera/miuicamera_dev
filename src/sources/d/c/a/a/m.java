package d.c.a.a;

import android.view.animation.Interpolator;

/* compiled from: SineEaseInOutInterpolater */
public class m implements Interpolator {
    public float getInterpolation(float f2) {
        return ((float) (Math.cos(((double) f2) * 3.141592653589793d) - 1.0d)) * -0.5f;
    }
}
