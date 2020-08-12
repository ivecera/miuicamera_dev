package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.e;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.engine.a.o;
import com.bumptech.glide.load.engine.u;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.j;
import com.bumptech.glide.util.a.d;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Engine implements r, o.a, u.a {
    private static final String TAG = "Engine";
    private static final int pn = 150;
    private static final boolean qn = Log.isLoggable(TAG, 2);
    private final o cache;
    private final w hn;
    private final t jn;
    private final EngineJobFactory kn;
    private final D ln;
    private final DecodeJobFactory mn;
    private final ActiveResources nn;
    private final a sm;

    @VisibleForTesting
    static class DecodeJobFactory {
        private int cn;
        final Pools.Pool<DecodeJob<?>> pool = d.a(150, new p(this));
        final DecodeJob.d sm;

        DecodeJobFactory(DecodeJob.d dVar) {
            this.sm = dVar;
        }

        /* JADX DEBUG: Type inference failed for r0v1. Raw type applied. Possible types: com.bumptech.glide.load.engine.DecodeJob<?>, com.bumptech.glide.load.engine.DecodeJob<R> */
        /* access modifiers changed from: package-private */
        public <R> DecodeJob<R> a(e eVar, Object obj, s sVar, c cVar, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, o oVar, Map<Class<?>, j<?>> map, boolean z, boolean z2, boolean z3, g gVar, DecodeJob.a<R> aVar) {
            DecodeJob<?> acquire = this.pool.acquire();
            i.checkNotNull(acquire);
            DecodeJob<?> decodeJob = acquire;
            int i3 = this.cn;
            this.cn = i3 + 1;
            return decodeJob.a(eVar, obj, sVar, cVar, i, i2, cls, cls2, priority, oVar, map, z, z2, z3, gVar, aVar, i3);
        }
    }

    @VisibleForTesting
    static class EngineJobFactory {
        final com.bumptech.glide.load.engine.b.b en;
        final com.bumptech.glide.load.engine.b.b ij;
        final com.bumptech.glide.load.engine.b.b jj;
        final r listener;
        final com.bumptech.glide.load.engine.b.b nj;
        final Pools.Pool<EngineJob<?>> pool = d.a(150, new q(this));

        EngineJobFactory(com.bumptech.glide.load.engine.b.b bVar, com.bumptech.glide.load.engine.b.b bVar2, com.bumptech.glide.load.engine.b.b bVar3, com.bumptech.glide.load.engine.b.b bVar4, r rVar) {
            this.jj = bVar;
            this.ij = bVar2;
            this.en = bVar3;
            this.nj = bVar4;
            this.listener = rVar;
        }

        private static void a(ExecutorService executorService) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                    if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                        throw new RuntimeException("Failed to shutdown");
                    }
                }
            } catch (InterruptedException e2) {
                throw new RuntimeException(e2);
            }
        }

        /* JADX DEBUG: Type inference failed for r6v4. Raw type applied. Possible types: com.bumptech.glide.load.engine.EngineJob<?>, com.bumptech.glide.load.engine.EngineJob<R> */
        /* access modifiers changed from: package-private */
        public <R> EngineJob<R> a(c cVar, boolean z, boolean z2, boolean z3, boolean z4) {
            EngineJob<?> acquire = this.pool.acquire();
            i.checkNotNull(acquire);
            return acquire.init(cVar, z, z2, z3, z4);
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public void shutdown() {
            a(this.jj);
            a(this.ij);
            a(this.en);
            a(this.nj);
        }
    }

    private static class a implements DecodeJob.d {
        private final a.C0009a factory;
        private volatile com.bumptech.glide.load.engine.a.a fn;

        a(a.C0009a aVar) {
            this.factory = aVar;
        }

        @Override // com.bumptech.glide.load.engine.DecodeJob.d
        public com.bumptech.glide.load.engine.a.a H() {
            if (this.fn == null) {
                synchronized (this) {
                    if (this.fn == null) {
                        this.fn = this.factory.build();
                    }
                    if (this.fn == null) {
                        this.fn = new com.bumptech.glide.load.engine.a.b();
                    }
                }
            }
            return this.fn;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public synchronized void clearDiskCacheIfCreated() {
            if (this.fn != null) {
                this.fn.clear();
            }
        }
    }

    public static class b {
        private final com.bumptech.glide.request.g cb;
        private final EngineJob<?> gn;

        b(com.bumptech.glide.request.g gVar, EngineJob<?> engineJob) {
            this.cb = gVar;
            this.gn = engineJob;
        }

        public void cancel() {
            this.gn.b(this.cb);
        }
    }

    @VisibleForTesting
    Engine(o oVar, a.C0009a aVar, com.bumptech.glide.load.engine.b.b bVar, com.bumptech.glide.load.engine.b.b bVar2, com.bumptech.glide.load.engine.b.b bVar3, com.bumptech.glide.load.engine.b.b bVar4, w wVar, t tVar, ActiveResources activeResources, EngineJobFactory engineJobFactory, DecodeJobFactory decodeJobFactory, D d2, boolean z) {
        this.cache = oVar;
        this.sm = new a(aVar);
        ActiveResources activeResources2 = activeResources == null ? new ActiveResources(z) : activeResources;
        this.nn = activeResources2;
        activeResources2.a(this);
        this.jn = tVar == null ? new t() : tVar;
        this.hn = wVar == null ? new w() : wVar;
        this.kn = engineJobFactory == null ? new EngineJobFactory(bVar, bVar2, bVar3, bVar4, this) : engineJobFactory;
        this.mn = decodeJobFactory == null ? new DecodeJobFactory(this.sm) : decodeJobFactory;
        this.ln = d2 == null ? new D() : d2;
        oVar.a(this);
    }

    public Engine(o oVar, a.C0009a aVar, com.bumptech.glide.load.engine.b.b bVar, com.bumptech.glide.load.engine.b.b bVar2, com.bumptech.glide.load.engine.b.b bVar3, com.bumptech.glide.load.engine.b.b bVar4, boolean z) {
        this(oVar, aVar, bVar, bVar2, bVar3, bVar4, null, null, null, null, null, null, z);
    }

    private static void a(String str, long j, c cVar) {
        Log.v(TAG, str + " in " + com.bumptech.glide.util.e.e(j) + "ms, key: " + cVar);
    }

    @Nullable
    private u<?> b(c cVar, boolean z) {
        if (!z) {
            return null;
        }
        u<?> b2 = this.nn.b(cVar);
        if (b2 != null) {
            b2.acquire();
        }
        return b2;
    }

    private u<?> c(c cVar, boolean z) {
        if (!z) {
            return null;
        }
        u<?> i = i(cVar);
        if (i != null) {
            i.acquire();
            this.nn.b(cVar, i);
        }
        return i;
    }

    private u<?> i(c cVar) {
        A<?> a2 = this.cache.a(cVar);
        if (a2 == null) {
            return null;
        }
        return a2 instanceof u ? (u) a2 : new u<>(a2, true, true);
    }

    public void Ei() {
        this.sm.H().clear();
    }

    public <R> b a(e eVar, Object obj, c cVar, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, o oVar, Map<Class<?>, j<?>> map, boolean z, boolean z2, g gVar, boolean z3, boolean z4, boolean z5, boolean z6, com.bumptech.glide.request.g gVar2) {
        l.Lk();
        long Jk = qn ? com.bumptech.glide.util.e.Jk() : 0;
        s a2 = this.jn.a(obj, cVar, i, i2, map, cls, cls2, gVar);
        u<?> b2 = b(a2, z3);
        if (b2 != null) {
            gVar2.a(b2, DataSource.MEMORY_CACHE);
            if (qn) {
                a("Loaded resource from active resources", Jk, a2);
            }
            return null;
        }
        u<?> c2 = c(a2, z3);
        if (c2 != null) {
            gVar2.a(c2, DataSource.MEMORY_CACHE);
            if (qn) {
                a("Loaded resource from cache", Jk, a2);
            }
            return null;
        }
        EngineJob<?> a3 = this.hn.a(a2, z6);
        if (a3 != null) {
            a3.a(gVar2);
            if (qn) {
                a("Added to existing load", Jk, a2);
            }
            return new b(gVar2, a3);
        }
        EngineJob<R> a4 = this.kn.a(a2, z3, z4, z5, z6);
        DecodeJob<R> a5 = this.mn.a(eVar, obj, a2, cVar, i, i2, cls, cls2, priority, oVar, map, z, z2, z6, gVar, a4);
        this.hn.a((c) a2, (EngineJob<?>) a4);
        a4.a(gVar2);
        a4.c(a5);
        if (qn) {
            a("Started new load", Jk, a2);
        }
        return new b(gVar2, a4);
    }

    @Override // com.bumptech.glide.load.engine.u.a
    public void a(c cVar, u<?> uVar) {
        l.Lk();
        this.nn.d(cVar);
        if (uVar.xj()) {
            this.cache.a(cVar, uVar);
        } else {
            this.ln.g(uVar);
        }
    }

    @Override // com.bumptech.glide.load.engine.r
    public void a(EngineJob<?> engineJob, c cVar) {
        l.Lk();
        this.hn.b(cVar, engineJob);
    }

    @Override // com.bumptech.glide.load.engine.r
    public void a(EngineJob<?> engineJob, c cVar, u<?> uVar) {
        l.Lk();
        if (uVar != null) {
            uVar.a(cVar, this);
            if (uVar.xj()) {
                this.nn.b(cVar, uVar);
            }
        }
        this.hn.b(cVar, engineJob);
    }

    @Override // com.bumptech.glide.load.engine.a.o.a
    public void b(@NonNull A<?> a2) {
        l.Lk();
        this.ln.g(a2);
    }

    public void e(A<?> a2) {
        l.Lk();
        if (a2 instanceof u) {
            ((u) a2).release();
            return;
        }
        throw new IllegalArgumentException("Cannot release anything but an EngineResource");
    }

    @VisibleForTesting
    public void shutdown() {
        this.kn.shutdown();
        this.sm.clearDiskCacheIfCreated();
        this.nn.shutdown();
    }
}
