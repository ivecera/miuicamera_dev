package com.bumptech.glide.load.b.d;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.resource.bitmap.u;
import com.bumptech.glide.util.i;

/* compiled from: BitmapDrawableTranscoder */
public class b implements e<Bitmap, BitmapDrawable> {
    private final Resources resources;

    public b(@NonNull Context context) {
        this(context.getResources());
    }

    public b(@NonNull Resources resources2) {
        i.checkNotNull(resources2);
        this.resources = resources2;
    }

    @Deprecated
    public b(@NonNull Resources resources2, d dVar) {
        this(resources2);
    }

    @Override // com.bumptech.glide.load.b.d.e
    @Nullable
    public A<BitmapDrawable> a(@NonNull A<Bitmap> a2, @NonNull g gVar) {
        return u.a(this.resources, a2);
    }
}
