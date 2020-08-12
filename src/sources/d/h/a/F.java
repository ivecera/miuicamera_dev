package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: SineEaseOutInterpolater */
public class F implements Interpolator {
    public float getInterpolation(float f2) {
        return (float) Math.sin(((double) f2) * 1.5707963267948966d);
    }
}
