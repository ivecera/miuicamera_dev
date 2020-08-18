package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.j;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;

@Deprecated
/* compiled from: BitmapDrawableTransformation */
public class d implements j<BitmapDrawable> {
    private final j<Drawable> Qf;

    public d(j<Bitmap> jVar) {
        r rVar = new r(jVar, false);
        i.checkNotNull(rVar);
        this.Qf = rVar;
    }

    private static A<BitmapDrawable> j(A<Drawable> a2) {
        if (a2.get() instanceof BitmapDrawable) {
            return a2;
        }
        throw new IllegalArgumentException("Wrapped transformation unexpectedly returned a non BitmapDrawable resource: " + a2.get());
    }

    private static A<Drawable> k(A<BitmapDrawable> a2) {
        return a2;
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (obj instanceof d) {
            return this.Qf.equals(((d) obj).Qf);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        return this.Qf.hashCode();
    }

    @Override // com.bumptech.glide.load.j
    @NonNull
    public A<BitmapDrawable> transform(@NonNull Context context, @NonNull A<BitmapDrawable> a2, int i, int i2) {
        A transform = this.Qf.transform(context, a2, i, i2);
        j(transform);
        return transform;
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.Qf.updateDiskCacheKey(messageDigest);
    }
}
