package d.h.a;

import android.view.animation.Interpolator;

/* compiled from: BounceEaseInInterpolator */
public class d implements Interpolator {
    public float getInterpolation(float f2) {
        return 1.0f - new f().getInterpolation(1.0f - f2);
    }
}
