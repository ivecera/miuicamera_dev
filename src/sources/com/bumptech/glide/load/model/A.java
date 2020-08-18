package com.bumptech.glide.load.model;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.io.InputStream;

/* compiled from: StringLoader */
public class A<Data> implements t<String, Data> {
    private final t<Uri, Data> mq;

    /* compiled from: StringLoader */
    public static final class a implements u<String, AssetFileDescriptor> {
        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        public t<String, AssetFileDescriptor> a(x xVar) {
            return new A(xVar.a(Uri.class, AssetFileDescriptor.class));
        }
    }

    /* compiled from: StringLoader */
    public static class b implements u<String, ParcelFileDescriptor> {
        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<String, ParcelFileDescriptor> a(x xVar) {
            return new A(xVar.a(Uri.class, ParcelFileDescriptor.class));
        }
    }

    /* compiled from: StringLoader */
    public static class c implements u<String, InputStream> {
        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<String, InputStream> a(x xVar) {
            return new A(xVar.a(Uri.class, InputStream.class));
        }
    }

    public A(t<Uri, Data> tVar) {
        this.mq = tVar;
    }

    @Nullable
    private static Uri Q(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.charAt(0) == '/') {
            return R(str);
        }
        Uri parse = Uri.parse(str);
        return parse.getScheme() == null ? R(str) : parse;
    }

    private static Uri R(String str) {
        return Uri.fromFile(new File(str));
    }

    /* renamed from: D */
    public boolean c(@NonNull String str) {
        return true;
    }

    public t.a<Data> a(@NonNull String str, int i, int i2, @NonNull g gVar) {
        Uri Q = Q(str);
        if (Q == null) {
            return null;
        }
        return this.mq.a(Q, i, i2, gVar);
    }
}
