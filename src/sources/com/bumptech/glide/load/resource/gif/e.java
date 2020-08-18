package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.j;
import com.bumptech.glide.load.resource.bitmap.f;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;

/* compiled from: GifDrawableTransformation */
public class e implements j<b> {
    private final j<Bitmap> Qf;

    public e(j<Bitmap> jVar) {
        i.checkNotNull(jVar);
        this.Qf = jVar;
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (obj instanceof e) {
            return this.Qf.equals(((e) obj).Qf);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        return this.Qf.hashCode();
    }

    @Override // com.bumptech.glide.load.j
    @NonNull
    public A<b> transform(@NonNull Context context, @NonNull A<b> a2, int i, int i2) {
        b bVar = a2.get();
        A<Bitmap> fVar = new f(bVar.ya(), c.get(context).Fi());
        A<Bitmap> transform = this.Qf.transform(context, fVar, i, i2);
        if (!fVar.equals(transform)) {
            fVar.recycle();
        }
        bVar.a(this.Qf, transform.get());
        return a2;
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.Qf.updateDiskCacheKey(messageDigest);
    }
}
