package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: BounceEaseInOutInterpolator */
public class e implements Interpolator {
    public float getInterpolation(float f2) {
        return f2 < 0.5f ? new d().getInterpolation(f2 * 2.0f) * 0.5f : (new f().getInterpolation((f2 * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
