package com.bumptech.glide.load.engine.a;

import android.util.Log;
import com.bumptech.glide.a.b;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.a.a;
import java.io.File;
import java.io.IOException;

/* compiled from: DiskLruCacheWrapper */
public class g implements a {
    private static final int APP_VERSION = 1;
    private static final int Ko = 1;
    private static g Lo = null;
    private static final String TAG = "DiskLruCacheWrapper";
    private final s Ho;
    private final c Io = new c();
    private b Jo;
    private final File directory;
    private final long maxSize;

    @Deprecated
    protected g(File file, long j) {
        this.directory = file;
        this.maxSize = j;
        this.Ho = new s();
    }

    private synchronized b H() throws IOException {
        if (this.Jo == null) {
            this.Jo = b.a(this.directory, 1, 1, this.maxSize);
        }
        return this.Jo;
    }

    @Deprecated
    public static synchronized a a(File file, long j) {
        g gVar;
        synchronized (g.class) {
            if (Lo == null) {
                Lo = new g(file, j);
            }
            gVar = Lo;
        }
        return gVar;
    }

    public static a create(File file, long j) {
        return new g(file, j);
    }

    private synchronized void xn() {
        this.Jo = null;
    }

    @Override // com.bumptech.glide.load.engine.a.a
    public void a(c cVar, a.b bVar) {
        String f2 = this.Ho.f(cVar);
        this.Io.B(f2);
        try {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Put: Obtained: " + f2 + " for for Key: " + cVar);
            }
            try {
                b H = H();
                if (H.get(f2) == null) {
                    b.C0005b edit = H.edit(f2);
                    if (edit != null) {
                        try {
                            if (bVar.c(edit.w(0))) {
                                edit.commit();
                            }
                            this.Io.C(f2);
                        } finally {
                            edit.abortUnlessCommitted();
                        }
                    } else {
                        throw new IllegalStateException("Had two simultaneous puts for: " + f2);
                    }
                }
            } catch (IOException e2) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Unable to put to disk cache", e2);
                }
            }
        } finally {
            this.Io.C(f2);
        }
    }

    @Override // com.bumptech.glide.load.engine.a.a
    public File b(c cVar) {
        String f2 = this.Ho.f(cVar);
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "Get: Obtained: " + f2 + " for for Key: " + cVar);
        }
        try {
            b.d dVar = H().get(f2);
            if (dVar != null) {
                return dVar.w(0);
            }
            return null;
        } catch (IOException e2) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            Log.w(TAG, "Unable to get from disk cache", e2);
            return null;
        }
    }

    @Override // com.bumptech.glide.load.engine.a.a
    public void c(c cVar) {
        try {
            H().remove(this.Ho.f(cVar));
        } catch (IOException e2) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to delete from disk cache", e2);
            }
        }
    }

    @Override // com.bumptech.glide.load.engine.a.a
    public synchronized void clear() {
        try {
            H().delete();
        } catch (IOException e2) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to clear disk cache or disk cache cleared externally", e2);
            }
        } catch (Throwable th) {
            xn();
            throw th;
        }
        xn();
    }
}
