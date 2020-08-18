package com.bumptech.glide.load.model;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import java.io.InputStream;

/* compiled from: ResourceLoader */
public class y<Data> implements t<Integer, Data> {
    private static final String TAG = "ResourceLoader";
    private final t<Uri, Data> mq;
    private final Resources resources;

    /* compiled from: ResourceLoader */
    public static final class a implements u<Integer, AssetFileDescriptor> {
        private final Resources resources;

        public a(Resources resources2) {
            this.resources = resources2;
        }

        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        public t<Integer, AssetFileDescriptor> a(x xVar) {
            return new y(this.resources, xVar.a(Uri.class, AssetFileDescriptor.class));
        }
    }

    /* compiled from: ResourceLoader */
    public static class b implements u<Integer, ParcelFileDescriptor> {
        private final Resources resources;

        public b(Resources resources2) {
            this.resources = resources2;
        }

        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<Integer, ParcelFileDescriptor> a(x xVar) {
            return new y(this.resources, xVar.a(Uri.class, ParcelFileDescriptor.class));
        }
    }

    /* compiled from: ResourceLoader */
    public static class c implements u<Integer, InputStream> {
        private final Resources resources;

        public c(Resources resources2) {
            this.resources = resources2;
        }

        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<Integer, InputStream> a(x xVar) {
            return new y(this.resources, xVar.a(Uri.class, InputStream.class));
        }
    }

    /* compiled from: ResourceLoader */
    public static class d implements u<Integer, Uri> {
        private final Resources resources;

        public d(Resources resources2) {
            this.resources = resources2;
        }

        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<Integer, Uri> a(x xVar) {
            return new y(this.resources, B.getInstance());
        }
    }

    public y(Resources resources2, t<Uri, Data> tVar) {
        this.resources = resources2;
        this.mq = tVar;
    }

    @Nullable
    private Uri h(Integer num) {
        try {
            return Uri.parse("android.resource://" + this.resources.getResourcePackageName(num.intValue()) + '/' + this.resources.getResourceTypeName(num.intValue()) + '/' + this.resources.getResourceEntryName(num.intValue()));
        } catch (Resources.NotFoundException e2) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            Log.w(TAG, "Received invalid resource id: " + num, e2);
            return null;
        }
    }

    public t.a<Data> a(@NonNull Integer num, int i, int i2, @NonNull g gVar) {
        Uri h = h(num);
        if (h == null) {
            return null;
        }
        return this.mq.a(h, i, i2, gVar);
    }

    /* renamed from: f */
    public boolean c(@NonNull Integer num) {
        return true;
    }
}
