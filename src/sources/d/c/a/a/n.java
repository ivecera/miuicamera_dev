package d.c.a.a;

import android.view.animation.Interpolator;

/* compiled from: SineEaseOutInterpolater */
public class n implements Interpolator {
    public float getInterpolation(float f2) {
        return (float) Math.sin(((double) f2) * 1.5707963267948966d);
    }
}