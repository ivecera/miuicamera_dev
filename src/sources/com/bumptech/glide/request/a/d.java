package com.bumptech.glide.request.a;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import com.bumptech.glide.request.a.f;

/* compiled from: DrawableCrossFadeTransition */
public class d implements f<Drawable> {
    private final boolean Yt;
    private final int duration;

    public d(int i, boolean z) {
        this.duration = i;
        this.Yt = z;
    }

    public boolean a(Drawable drawable, f.a aVar) {
        Drawable currentDrawable = aVar.getCurrentDrawable();
        if (currentDrawable == null) {
            currentDrawable = new ColorDrawable(0);
        }
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{currentDrawable, drawable});
        transitionDrawable.setCrossFadeEnabled(this.Yt);
        transitionDrawable.startTransition(this.duration);
        aVar.setDrawable(transitionDrawable);
        return true;
    }
}
