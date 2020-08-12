package com.bumptech.glide.load.model.a;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.e.d;
import com.bumptech.glide.load.a.a.b;
import com.bumptech.glide.load.a.a.c;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import com.bumptech.glide.load.model.u;
import com.bumptech.glide.load.model.x;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import java.io.InputStream;

/* compiled from: MediaStoreVideoThumbLoader */
public class e implements t<Uri, InputStream> {
    private final Context context;

    /* compiled from: MediaStoreVideoThumbLoader */
    public static class a implements u<Uri, InputStream> {
        private final Context context;

        public a(Context context2) {
            this.context = context2;
        }

        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<Uri, InputStream> a(x xVar) {
            return new e(this.context);
        }
    }

    public e(Context context2) {
        this.context = context2.getApplicationContext();
    }

    private boolean c(g gVar) {
        Long l = (Long) gVar.a(VideoDecoder.Er);
        return l != null && l.longValue() == -1;
    }

    @Nullable
    public t.a<InputStream> a(@NonNull Uri uri, int i, int i2, @NonNull g gVar) {
        if (!b.k(i, i2) || !c(gVar)) {
            return null;
        }
        return new t.a<>(new d(uri), c.c(this.context, uri));
    }

    /* renamed from: i */
    public boolean c(@NonNull Uri uri) {
        return b.f(uri);
    }
}
