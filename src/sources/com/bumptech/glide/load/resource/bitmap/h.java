package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.n;
import com.bumptech.glide.request.a.b;
import com.bumptech.glide.request.a.c;
import com.bumptech.glide.request.a.g;

/* compiled from: BitmapTransitionOptions */
public final class h extends n<h, Bitmap> {
    @NonNull
    public static h aj() {
        return new h()._i();
    }

    @NonNull
    public static h b(@NonNull c.a aVar) {
        return new h().a(aVar);
    }

    @NonNull
    public static h b(@NonNull c cVar) {
        return new h().a(cVar);
    }

    @NonNull
    public static h b(@NonNull g<Bitmap> gVar) {
        return (h) new h().a(gVar);
    }

    @NonNull
    public static h d(@NonNull g<Drawable> gVar) {
        return new h().c(gVar);
    }

    @NonNull
    public static h v(int i) {
        return new h().u(i);
    }

    @NonNull
    public h _i() {
        return a(new c.a());
    }

    @NonNull
    public h a(@NonNull c.a aVar) {
        return c(aVar.build());
    }

    @NonNull
    public h a(@NonNull c cVar) {
        return c(cVar);
    }

    @NonNull
    public h c(@NonNull g<Drawable> gVar) {
        return (h) a(new b(gVar));
    }

    @NonNull
    public h u(int i) {
        return a(new c.a(i));
    }
}
