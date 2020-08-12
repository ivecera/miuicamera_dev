package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

/* compiled from: ThumbnailImageViewTarget */
public abstract class p<T> extends h<T> {
    public p(ImageView imageView) {
        super(imageView);
    }

    @Deprecated
    public p(ImageView imageView, boolean z) {
        super(imageView, z);
    }

    /* access modifiers changed from: protected */
    @Override // com.bumptech.glide.request.target.h
    public void o(@Nullable T t) {
        int i;
        int i2;
        ViewGroup.LayoutParams layoutParams = ((ViewTarget) this).view.getLayoutParams();
        Drawable p = p(t);
        if (layoutParams != null && (i = layoutParams.width) > 0 && (i2 = layoutParams.height) > 0) {
            p = new g(p, i, i2);
        }
        ((ViewTarget) this).view.setImageDrawable(p);
    }

    /* access modifiers changed from: protected */
    public abstract Drawable p(T t);
}
