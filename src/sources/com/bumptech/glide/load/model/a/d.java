package com.bumptech.glide.load.model.a;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.a.b;
import com.bumptech.glide.load.a.a.c;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import com.bumptech.glide.load.model.u;
import com.bumptech.glide.load.model.x;
import java.io.InputStream;

/* compiled from: MediaStoreImageThumbLoader */
public class d implements t<Uri, InputStream> {
    private final Context context;

    /* compiled from: MediaStoreImageThumbLoader */
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
            return new d(this.context);
        }
    }

    public d(Context context2) {
        this.context = context2.getApplicationContext();
    }

    public t.a<InputStream> a(@NonNull Uri uri, int i, int i2, @NonNull g gVar) {
        if (b.k(i, i2)) {
            return new t.a<>(new com.bumptech.glide.e.d(uri), c.b(this.context, uri));
        }
        return null;
    }

    /* renamed from: i */
    public boolean c(@NonNull Uri uri) {
        return b.d(uri);
    }
}
