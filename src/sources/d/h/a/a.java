package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: BackEaseInInterpolator */
public class a implements Interpolator {
    private final float cy;

    public a() {
        this(0.0f);
    }

    public a(float f2) {
        this.cy = f2;
    }

    public float getInterpolation(float f2) {
        float f3 = this.cy;
        if (f3 == 0.0f) {
            f3 = 1.70158f;
        }
        return f2 * f2 * (((1.0f + f3) * f2) - f3);
    }
}
