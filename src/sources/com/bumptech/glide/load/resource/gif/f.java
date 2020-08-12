package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.b.a;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;

/* compiled from: GifFrameResourceDecoder */
public final class f implements h<a, Bitmap> {
    private final d Xi;

    public f(d dVar) {
        this.Xi = dVar;
    }

    /* renamed from: a */
    public A<Bitmap> b(@NonNull a aVar, int i, int i2, @NonNull g gVar) {
        return com.bumptech.glide.load.resource.bitmap.f.a(aVar.getNextFrame(), this.Xi);
    }

    public boolean a(@NonNull a aVar, @NonNull g gVar) {
        return true;
    }
}
