package com.bumptech.glide.load.a.a;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* compiled from: ThumbnailStreamOpener */
class e {
    private static final String TAG = "ThumbStreamOpener";
    private static final a dm = new a();
    private final b Ll;
    private final ContentResolver Ol;
    private final List<ImageHeaderParser> cm;
    private final d query;
    private final a service;

    e(List<ImageHeaderParser> list, a aVar, d dVar, b bVar, ContentResolver contentResolver) {
        this.service = aVar;
        this.query = dVar;
        this.Ll = bVar;
        this.Ol = contentResolver;
        this.cm = list;
    }

    e(List<ImageHeaderParser> list, d dVar, b bVar, ContentResolver contentResolver) {
        this(list, dm, dVar, bVar, contentResolver);
    }

    private boolean h(File file) {
        return this.service.exists(file) && 0 < this.service.d(file);
    }

    @Nullable
    private String k(@NonNull Uri uri) {
        Cursor b2 = this.query.b(uri);
        if (b2 != null) {
            try {
                if (b2.moveToFirst()) {
                    return b2.getString(0);
                }
            } finally {
                if (b2 != null) {
                    b2.close();
                }
            }
        }
        if (b2 != null) {
            b2.close();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int g(Uri uri) {
        InputStream inputStream = null;
        try {
            inputStream = this.Ol.openInputStream(uri);
            int a2 = com.bumptech.glide.load.b.a(this.cm, inputStream, this.Ll);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused) {
                }
            }
            return a2;
        } catch (IOException | NullPointerException e2) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to open uri: " + uri, e2);
            }
            if (inputStream == null) {
                return -1;
            }
            try {
                inputStream.close();
                return -1;
            } catch (IOException unused2) {
                return -1;
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused3) {
                }
            }
            throw th;
        }
    }

    public InputStream h(Uri uri) throws FileNotFoundException {
        String k = k(uri);
        if (TextUtils.isEmpty(k)) {
            return null;
        }
        File file = this.service.get(k);
        if (!h(file)) {
            return null;
        }
        Uri fromFile = Uri.fromFile(file);
        try {
            return this.Ol.openInputStream(fromFile);
        } catch (NullPointerException e2) {
            throw ((FileNotFoundException) new FileNotFoundException("NPE opening uri: " + uri + " -> " + fromFile).initCause(e2));
        }
    }
}
