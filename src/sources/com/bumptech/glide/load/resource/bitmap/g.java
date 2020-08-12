package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.j;
import com.bumptech.glide.util.l;

/* compiled from: BitmapTransformation */
public abstract class g implements j<Bitmap> {
    /* access modifiers changed from: protected */
    public abstract Bitmap transform(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2);

    @Override // com.bumptech.glide.load.j
    @NonNull
    public final A<Bitmap> transform(@NonNull Context context, @NonNull A<Bitmap> a2, int i, int i2) {
        if (l.o(i, i2)) {
            d Fi = c.get(context).Fi();
            Bitmap bitmap = a2.get();
            if (i == Integer.MIN_VALUE) {
                i = bitmap.getWidth();
            }
            if (i2 == Integer.MIN_VALUE) {
                i2 = bitmap.getHeight();
            }
            Bitmap transform = transform(Fi, bitmap, i, i2);
            return bitmap.equals(transform) ? a2 : f.a(transform, Fi);
        }
        throw new IllegalArgumentException("Cannot apply transformation on width: " + i + " or height: " + i2 + " less than or equal to zero and not Target.SIZE_ORIGINAL");
    }
}
