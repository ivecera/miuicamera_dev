package com.bumptech.glide.load.a;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: LocalUriFetcher */
public abstract class m<T> implements d<T> {
    private static final String TAG = "LocalUriFetcher";
    private final ContentResolver Ol;
    private T data;
    private final Uri uri;

    public m(ContentResolver contentResolver, Uri uri2) {
        this.Ol = contentResolver;
        this.uri = uri2;
    }

    @Override // com.bumptech.glide.load.a.d
    @NonNull
    public DataSource L() {
        return DataSource.LOCAL;
    }

    /* access modifiers changed from: protected */
    public abstract T a(Uri uri2, ContentResolver contentResolver) throws FileNotFoundException;

    @Override // com.bumptech.glide.load.a.d
    public final void a(@NonNull Priority priority, @NonNull d.a<? super T> aVar) {
        try {
            this.data = a(this.uri, this.Ol);
            aVar.b(this.data);
        } catch (FileNotFoundException e2) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to open Uri", e2);
            }
            aVar.a(e2);
        }
    }

    @Override // com.bumptech.glide.load.a.d
    public void cancel() {
    }

    @Override // com.bumptech.glide.load.a.d
    public void cleanup() {
        T t = this.data;
        if (t != null) {
            try {
                e(t);
            } catch (IOException unused) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void e(T t) throws IOException;
}
