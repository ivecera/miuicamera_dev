package com.bumptech.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.engine.a.m;
import com.bumptech.glide.load.engine.a.o;
import com.bumptech.glide.load.engine.a.q;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.bitmap_recycle.i;
import com.bumptech.glide.load.engine.bitmap_recycle.j;
import com.bumptech.glide.manager.g;
import com.bumptech.glide.manager.n;
import com.bumptech.glide.request.f;
import java.util.Map;

/* compiled from: GlideBuilder */
public final class d {
    private b Ma;
    private f Oa = new f();
    private final Map<Class<?>, n<?, ?>> Pa = new ArrayMap();
    private Engine Qa;
    private com.bumptech.glide.load.engine.bitmap_recycle.d Xi;
    private o Yi;
    private com.bumptech.glide.manager.d cj;
    private com.bumptech.glide.load.engine.b.b ij;
    private com.bumptech.glide.load.engine.b.b jj;
    private a.C0009a kj;
    private q lj;
    private int logLevel = 4;
    @Nullable
    private n.a mj;
    private com.bumptech.glide.load.engine.b.b nj;
    private boolean oj;

    /* access modifiers changed from: package-private */
    @NonNull
    public c E(@NonNull Context context) {
        if (this.ij == null) {
            this.ij = com.bumptech.glide.load.engine.b.b.Ej();
        }
        if (this.jj == null) {
            this.jj = com.bumptech.glide.load.engine.b.b.Dj();
        }
        if (this.nj == null) {
            this.nj = com.bumptech.glide.load.engine.b.b.Cj();
        }
        if (this.lj == null) {
            this.lj = new q.a(context).build();
        }
        if (this.cj == null) {
            this.cj = new g();
        }
        if (this.Xi == null) {
            int zj = this.lj.zj();
            if (zj > 0) {
                this.Xi = new j((long) zj);
            } else {
                this.Xi = new e();
            }
        }
        if (this.Ma == null) {
            this.Ma = new i(this.lj.yj());
        }
        if (this.Yi == null) {
            this.Yi = new com.bumptech.glide.load.engine.a.n((long) this.lj.Aj());
        }
        if (this.kj == null) {
            this.kj = new m(context);
        }
        if (this.Qa == null) {
            this.Qa = new Engine(this.Yi, this.kj, this.jj, this.ij, com.bumptech.glide.load.engine.b.b.Fj(), com.bumptech.glide.load.engine.b.b.Cj(), this.oj);
        }
        return new c(context, this.Qa, this.Yi, this.Xi, this.Ma, new n(this.mj), this.cj, this.logLevel, this.Oa.lock(), this.Pa);
    }

    /* access modifiers changed from: package-private */
    public d a(Engine engine) {
        this.Qa = engine;
        return this;
    }

    @NonNull
    public d a(@Nullable a.C0009a aVar) {
        this.kj = aVar;
        return this;
    }

    @NonNull
    public d a(@Nullable o oVar) {
        this.Yi = oVar;
        return this;
    }

    @NonNull
    public d a(@NonNull q.a aVar) {
        return a(aVar.build());
    }

    @NonNull
    public d a(@Nullable q qVar) {
        this.lj = qVar;
        return this;
    }

    @NonNull
    public d a(@Nullable com.bumptech.glide.load.engine.b.b bVar) {
        this.nj = bVar;
        return this;
    }

    @NonNull
    public d a(@Nullable b bVar) {
        this.Ma = bVar;
        return this;
    }

    @NonNull
    public d a(@Nullable com.bumptech.glide.load.engine.bitmap_recycle.d dVar) {
        this.Xi = dVar;
        return this;
    }

    @NonNull
    public d a(@Nullable com.bumptech.glide.manager.d dVar) {
        this.cj = dVar;
        return this;
    }

    @NonNull
    public d a(@Nullable f fVar) {
        this.Oa = fVar;
        return this;
    }

    @NonNull
    public <T> d a(@NonNull Class<T> cls, @Nullable n<?, T> nVar) {
        this.Pa.put(cls, nVar);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a(@Nullable n.a aVar) {
        this.mj = aVar;
    }

    @NonNull
    public d b(@Nullable com.bumptech.glide.load.engine.b.b bVar) {
        this.jj = bVar;
        return this;
    }

    @Deprecated
    public d c(@Nullable com.bumptech.glide.load.engine.b.b bVar) {
        return d(bVar);
    }

    @NonNull
    public d d(@Nullable com.bumptech.glide.load.engine.b.b bVar) {
        this.ij = bVar;
        return this;
    }

    @NonNull
    public d setLogLevel(int i) {
        if (i < 2 || i > 6) {
            throw new IllegalArgumentException("Log level must be one of Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, or Log.ERROR");
        }
        this.logLevel = i;
        return this;
    }

    @NonNull
    public d z(boolean z) {
        this.oj = z;
        return this;
    }
}
