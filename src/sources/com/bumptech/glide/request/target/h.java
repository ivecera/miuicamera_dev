package com.bumptech.glide.request.target;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.request.a.f;

/* compiled from: ImageViewTarget */
public abstract class h<Z> extends ViewTarget<ImageView, Z> implements f.a {
    @Nullable
    private Animatable St;

    public h(ImageView imageView) {
        super(imageView);
    }

    @Deprecated
    public h(ImageView imageView, boolean z) {
        super(imageView, z);
    }

    private void y(@Nullable Z z) {
        if (z instanceof Animatable) {
            this.St = z;
            this.St.start();
            return;
        }
        this.St = null;
    }

    private void z(@Nullable Z z) {
        o(z);
        y(z);
    }

    @Override // com.bumptech.glide.request.target.o
    public void a(@NonNull Z z, @Nullable f<? super Z> fVar) {
        if (fVar == null || !fVar.a(z, this)) {
            z(z);
        } else {
            y(z);
        }
    }

    @Override // com.bumptech.glide.request.target.b, com.bumptech.glide.request.target.ViewTarget, com.bumptech.glide.request.target.o
    public void b(@Nullable Drawable drawable) {
        super.b(drawable);
        Animatable animatable = this.St;
        if (animatable != null) {
            animatable.stop();
        }
        z(null);
        setDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.target.b, com.bumptech.glide.request.target.ViewTarget, com.bumptech.glide.request.target.o
    public void c(@Nullable Drawable drawable) {
        super.c(drawable);
        z(null);
        setDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.target.b, com.bumptech.glide.request.target.o
    public void d(@Nullable Drawable drawable) {
        super.d(drawable);
        z(null);
        setDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.a.f.a
    @Nullable
    public Drawable getCurrentDrawable() {
        return ((ViewTarget) this).view.getDrawable();
    }

    /* access modifiers changed from: protected */
    public abstract void o(@Nullable Z z);

    @Override // com.bumptech.glide.request.target.b, com.bumptech.glide.manager.j
    public void onStart() {
        Animatable animatable = this.St;
        if (animatable != null) {
            animatable.start();
        }
    }

    @Override // com.bumptech.glide.request.target.b, com.bumptech.glide.manager.j
    public void onStop() {
        Animatable animatable = this.St;
        if (animatable != null) {
            animatable.stop();
        }
    }

    @Override // com.bumptech.glide.request.a.f.a
    public void setDrawable(Drawable drawable) {
        ((ViewTarget) this).view.setImageDrawable(drawable);
    }
}
