package com.bumptech.glide.load.b.d;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.resource.bitmap.f;
import com.bumptech.glide.load.resource.gif.b;

/* compiled from: DrawableBytesTranscoder */
public final class c implements e<Drawable, byte[]> {
    private final d Xi;
    private final e<Bitmap, byte[]> es;
    private final e<b, byte[]> fs;

    public c(@NonNull d dVar, @NonNull e<Bitmap, byte[]> eVar, @NonNull e<b, byte[]> eVar2) {
        this.Xi = dVar;
        this.es = eVar;
        this.fs = eVar2;
    }

    @NonNull
    private static A<b> l(@NonNull A<Drawable> a2) {
        return a2;
    }

    @Override // com.bumptech.glide.load.b.d.e
    @Nullable
    public A<byte[]> a(@NonNull A<Drawable> a2, @NonNull g gVar) {
        Drawable drawable = a2.get();
        if (drawable instanceof BitmapDrawable) {
            return this.es.a(f.a(((BitmapDrawable) drawable).getBitmap(), this.Xi), gVar);
        }
        if (drawable instanceof b) {
            return this.fs.a(a2, gVar);
        }
        return null;
    }
}
