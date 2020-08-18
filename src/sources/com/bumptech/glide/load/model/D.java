package com.bumptech.glide.load.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: UrlUriLoader */
public class D<Data> implements t<Uri, Data> {
    private static final Set<String> SCHEMES = Collections.unmodifiableSet(new HashSet(Arrays.asList("http", "https")));
    private final t<l, Data> nq;

    /* compiled from: UrlUriLoader */
    public static class a implements u<Uri, InputStream> {
        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<Uri, InputStream> a(x xVar) {
            return new D(xVar.a(l.class, InputStream.class));
        }
    }

    public D(t<l, Data> tVar) {
        this.nq = tVar;
    }

    public t.a<Data> a(@NonNull Uri uri, int i, int i2, @NonNull g gVar) {
        return this.nq.a(new l(uri.toString()), i, i2, gVar);
    }

    /* renamed from: i */
    public boolean c(@NonNull Uri uri) {
        return SCHEMES.contains(uri.getScheme());
    }
}
