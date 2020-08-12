package com.bumptech.glide.load.a.a;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.a.h;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ThumbFetcher */
public class c implements d<InputStream> {
    private static final String TAG = "MediaStoreThumbFetcher";
    private final Uri Zl;
    private final e _l;
    private InputStream bm;

    /* compiled from: ThumbFetcher */
    static class a implements d {
        private static final String[] Xl = {"_data"};
        private static final String Yl = "kind = 1 AND image_id = ?";
        private final ContentResolver Ol;

        a(ContentResolver contentResolver) {
            this.Ol = contentResolver;
        }

        @Override // com.bumptech.glide.load.a.a.d
        public Cursor b(Uri uri) {
            String lastPathSegment = uri.getLastPathSegment();
            return this.Ol.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, Xl, Yl, new String[]{lastPathSegment}, null);
        }
    }

    /* compiled from: ThumbFetcher */
    static class b implements d {
        private static final String[] Xl = {"_data"};
        private static final String Yl = "kind = 1 AND video_id = ?";
        private final ContentResolver Ol;

        b(ContentResolver contentResolver) {
            this.Ol = contentResolver;
        }

        @Override // com.bumptech.glide.load.a.a.d
        public Cursor b(Uri uri) {
            String lastPathSegment = uri.getLastPathSegment();
            return this.Ol.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, Xl, Yl, new String[]{lastPathSegment}, null);
        }
    }

    @VisibleForTesting
    c(Uri uri, e eVar) {
        this.Zl = uri;
        this._l = eVar;
    }

    private static c a(Context context, Uri uri, d dVar) {
        return new c(uri, new e(com.bumptech.glide.c.get(context).wa().Ji(), dVar, com.bumptech.glide.c.get(context).sa(), context.getContentResolver()));
    }

    public static c b(Context context, Uri uri) {
        return a(context, uri, new a(context.getContentResolver()));
    }

    public static c c(Context context, Uri uri) {
        return a(context, uri, new b(context.getContentResolver()));
    }

    private InputStream gn() throws FileNotFoundException {
        InputStream h = this._l.h(this.Zl);
        int g = h != null ? this._l.g(this.Zl) : -1;
        return g != -1 ? new h(h, g) : h;
    }

    @Override // com.bumptech.glide.load.a.d
    @NonNull
    public DataSource L() {
        return DataSource.LOCAL;
    }

    @Override // com.bumptech.glide.load.a.d
    public void a(@NonNull Priority priority, @NonNull d.a<? super InputStream> aVar) {
        try {
            this.bm = gn();
            aVar.b(this.bm);
        } catch (FileNotFoundException e2) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to find thumbnail file", e2);
            }
            aVar.a(e2);
        }
    }

    @Override // com.bumptech.glide.load.a.d
    public void cancel() {
    }

    @Override // com.bumptech.glide.load.a.d
    public void cleanup() {
        InputStream inputStream = this.bm;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    @Override // com.bumptech.glide.load.a.d
    @NonNull
    public Class<InputStream> ga() {
        return InputStream.class;
    }
}
