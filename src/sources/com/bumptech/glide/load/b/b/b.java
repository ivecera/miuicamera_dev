package com.bumptech.glide.load.b.b;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.v;
import com.bumptech.glide.util.i;

/* compiled from: DrawableResource */
public abstract class b<T extends Drawable> implements A<T>, v {
    protected final T drawable;

    public b(T t) {
        i.checkNotNull(t);
        this.drawable = t;
    }

    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public final T get() {
        Drawable.ConstantState constantState = this.drawable.getConstantState();
        return constantState == null ? this.drawable : constantState.newDrawable();
    }

    @Override // com.bumptech.glide.load.engine.v
    public void initialize() {
        T t = this.drawable;
        if (t instanceof BitmapDrawable) {
            t.getBitmap().prepareToDraw();
        } else if (t instanceof com.bumptech.glide.load.resource.gif.b) {
            t.ya().prepareToDraw();
        }
    }
}
