package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: SineEaseInInterpolator */
public class D implements Interpolator {
    public float getInterpolation(float f2) {
        return (-((float) Math.cos(((double) f2) * 1.5707963267948966d))) + 1.0f;
    }
}
