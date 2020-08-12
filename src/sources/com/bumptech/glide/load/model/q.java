package com.bumptech.glide.load.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.io.FileNotFoundException;

/* compiled from: MediaStoreFileLoader */
public final class q implements t<Uri, File> {
    private final Context context;

    /* compiled from: MediaStoreFileLoader */
    public static final class a implements u<Uri, File> {
        private final Context context;

        public a(Context context2) {
            this.context = context2;
        }

        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<Uri, File> a(x xVar) {
            return new q(this.context);
        }
    }

    /* compiled from: MediaStoreFileLoader */
    private static class b implements d<File> {
        private static final String[] bq = {"_data"};
        private final Context context;
        private final Uri uri;

        b(Context context2, Uri uri2) {
            this.context = context2;
            this.uri = uri2;
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public DataSource L() {
            return DataSource.LOCAL;
        }

        @Override // com.bumptech.glide.load.a.d
        public void a(@NonNull Priority priority, @NonNull d.a<? super File> aVar) {
            Cursor query = this.context.getContentResolver().query(this.uri, bq, null, null, null);
            String str = null;
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        str = query.getString(query.getColumnIndexOrThrow("_data"));
                    }
                } finally {
                    query.close();
                }
            }
            if (TextUtils.isEmpty(str)) {
                aVar.a(new FileNotFoundException("Failed to find file path for: " + this.uri));
                return;
            }
            aVar.b(new File(str));
        }

        @Override // com.bumptech.glide.load.a.d
        public void cancel() {
        }

        @Override // com.bumptech.glide.load.a.d
        public void cleanup() {
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public Class<File> ga() {
            return File.class;
        }
    }

    public q(Context context2) {
        this.context = context2;
    }

    public t.a<File> a(@NonNull Uri uri, int i, int i2, @NonNull g gVar) {
        return new t.a<>(new com.bumptech.glide.e.d(uri), new b(this.context, uri));
    }

    /* renamed from: i */
    public boolean c(@NonNull Uri uri) {
        return com.bumptech.glide.load.a.a.b.e(uri);
    }
}
