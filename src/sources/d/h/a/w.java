package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: QuadraticEaseOutInterpolator */
public class w implements Interpolator {
    public float getInterpolation(float f2) {
        return (-f2) * (f2 - 2.0f);
    }
}
