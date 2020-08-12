package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.b.b.e;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;

/* compiled from: ResourceBitmapDecoder */
public class v implements h<Uri, Bitmap> {
    private final d Xi;
    private final e tr;

    public v(e eVar, d dVar) {
        this.tr = eVar;
        this.Xi = dVar;
    }

    public boolean a(@NonNull Uri uri, @NonNull g gVar) {
        return "android.resource".equals(uri.getScheme());
    }

    @Nullable
    public A<Bitmap> b(@NonNull Uri uri, int i, int i2, @NonNull g gVar) {
        A<Drawable> b2 = this.tr.b(uri, i, i2, gVar);
        if (b2 == null) {
            return null;
        }
        return q.a(this.Xi, b2.get(), i, i2);
    }
}
