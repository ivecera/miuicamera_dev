package com.bumptech.glide.load.engine.a;

import com.bumptech.glide.load.engine.a.a;
import java.io.File;

/* compiled from: DiskLruCacheFactory */
public class f implements a.C0009a {
    private final long Fo;
    private final a Go;

    /* compiled from: DiskLruCacheFactory */
    public interface a {
        File Q();
    }

    public f(a aVar, long j) {
        this.Fo = j;
        this.Go = aVar;
    }

    public f(String str, long j) {
        this(new d(str), j);
    }

    public f(String str, String str2, long j) {
        this(new e(str, str2), j);
    }

    @Override // com.bumptech.glide.load.engine.a.a.C0009a
    public a build() {
        File Q = this.Go.Q();
        if (Q == null) {
            return null;
        }
        if (Q.mkdirs() || (Q.exists() && Q.isDirectory())) {
            return g.create(Q, this.Fo);
        }
        return null;
    }
}
