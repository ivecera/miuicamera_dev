package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.j;
import java.security.MessageDigest;

/* compiled from: DrawableTransformation */
public class r implements j<Drawable> {
    private final j<Bitmap> Qf;
    private final boolean kr;

    public r(j<Bitmap> jVar, boolean z) {
        this.Qf = jVar;
        this.kr = z;
    }

    private A<Drawable> a(Context context, A<Bitmap> a2) {
        return u.a(context.getResources(), a2);
    }

    public j<BitmapDrawable> Hj() {
        return this;
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (obj instanceof r) {
            return this.Qf.equals(((r) obj).Qf);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        return this.Qf.hashCode();
    }

    @Override // com.bumptech.glide.load.j
    @NonNull
    public A<Drawable> transform(@NonNull Context context, @NonNull A<Drawable> a2, int i, int i2) {
        d Fi = c.get(context).Fi();
        Drawable drawable = a2.get();
        A<Bitmap> a3 = q.a(Fi, drawable, i, i2);
        if (a3 != null) {
            A<Bitmap> transform = this.Qf.transform(context, a3, i, i2);
            if (!transform.equals(a3)) {
                return a(context, transform);
            }
            transform.recycle();
            return a2;
        } else if (!this.kr) {
            return a2;
        } else {
            throw new IllegalArgumentException("Unable to convert " + drawable + " to a Bitmap");
        }
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.Qf.updateDiskCacheKey(messageDigest);
    }
}
