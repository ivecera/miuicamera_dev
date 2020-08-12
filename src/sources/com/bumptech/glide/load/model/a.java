package com.bumptech.glide.load.model;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.a.i;
import com.bumptech.glide.load.a.n;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import java.io.InputStream;

/* compiled from: AssetUriLoader */
public class a<Data> implements t<Uri, Data> {
    private static final String Hp = "android_asset";
    private static final String Ip = "file:///android_asset/";
    private static final int Jp = 22;
    private final AssetManager Cl;
    private final C0011a<Data> factory;

    /* renamed from: com.bumptech.glide.load.model.a$a  reason: collision with other inner class name */
    /* compiled from: AssetUriLoader */
    public interface C0011a<Data> {
        d<Data> a(AssetManager assetManager, String str);
    }

    /* compiled from: AssetUriLoader */
    public static class b implements u<Uri, ParcelFileDescriptor>, C0011a<ParcelFileDescriptor> {
        private final AssetManager Cl;

        public b(AssetManager assetManager) {
            this.Cl = assetManager;
        }

        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.a.C0011a
        public d<ParcelFileDescriptor> a(AssetManager assetManager, String str) {
            return new i(assetManager, str);
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<Uri, ParcelFileDescriptor> a(x xVar) {
            return new a(this.Cl, this);
        }
    }

    /* compiled from: AssetUriLoader */
    public static class c implements u<Uri, InputStream>, C0011a<InputStream> {
        private final AssetManager Cl;

        public c(AssetManager assetManager) {
            this.Cl = assetManager;
        }

        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.a.C0011a
        public d<InputStream> a(AssetManager assetManager, String str) {
            return new n(assetManager, str);
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<Uri, InputStream> a(x xVar) {
            return new a(this.Cl, this);
        }
    }

    public a(AssetManager assetManager, C0011a<Data> aVar) {
        this.Cl = assetManager;
        this.factory = aVar;
    }

    public t.a<Data> a(@NonNull Uri uri, int i, int i2, @NonNull g gVar) {
        return new t.a<>(new com.bumptech.glide.e.d(uri), this.factory.a(this.Cl, uri.toString().substring(Jp)));
    }

    /* renamed from: i */
    public boolean c(@NonNull Uri uri) {
        return ComposerHelper.COMPOSER_PATH.equals(uri.getScheme()) && !uri.getPathSegments().isEmpty() && Hp.equals(uri.getPathSegments().get(0));
    }
}
