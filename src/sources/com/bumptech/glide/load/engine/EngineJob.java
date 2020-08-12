package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.b.b;
import com.bumptech.glide.util.a.d;
import com.bumptech.glide.util.a.g;
import com.bumptech.glide.util.l;
import java.util.ArrayList;
import java.util.List;

class EngineJob<R> implements DecodeJob.a<R>, d.c {
    private static final Handler Bn = new Handler(Looper.getMainLooper(), new a());
    private static final int Cn = 1;
    private static final int Dn = 2;
    private static final EngineResourceFactory El = new EngineResourceFactory();
    private static final int En = 3;
    private DecodeJob<R> An;
    private final g Hm;
    private volatile boolean Il;
    private boolean Om;
    private DataSource dataSource;
    private final b en;
    private GlideException exception;
    private final b ij;
    private final b jj;
    private c key;
    private final r listener;
    private final b nj;
    private final Pools.Pool<EngineJob<?>> pool;
    private A<?> resource;
    private final List<com.bumptech.glide.request.g> rn;
    private final EngineResourceFactory sn;
    private boolean tn;
    private boolean un;
    private boolean vn;
    private boolean wn;
    private boolean xn;
    private List<com.bumptech.glide.request.g> yn;
    private u<?> zn;

    @VisibleForTesting
    static class EngineResourceFactory {
        EngineResourceFactory() {
        }

        public <R> u<R> a(A<R> a2, boolean z) {
            return new u<>(a2, z, true);
        }
    }

    private static class a implements Handler.Callback {
        a() {
        }

        public boolean handleMessage(Message message) {
            EngineJob engineJob = (EngineJob) message.obj;
            int i = message.what;
            if (i == 1) {
                engineJob.uj();
            } else if (i == 2) {
                engineJob.tj();
            } else if (i == 3) {
                engineJob.sj();
            } else {
                throw new IllegalStateException("Unrecognized message: " + message.what);
            }
            return true;
        }
    }

    EngineJob(b bVar, b bVar2, b bVar3, b bVar4, r rVar, Pools.Pool<EngineJob<?>> pool2) {
        this(bVar, bVar2, bVar3, bVar4, rVar, pool2, El);
    }

    @VisibleForTesting
    EngineJob(b bVar, b bVar2, b bVar3, b bVar4, r rVar, Pools.Pool<EngineJob<?>> pool2, EngineResourceFactory engineResourceFactory) {
        this.rn = new ArrayList(2);
        this.Hm = g.newInstance();
        this.jj = bVar;
        this.ij = bVar2;
        this.en = bVar3;
        this.nj = bVar4;
        this.listener = rVar;
        this.pool = pool2;
        this.sn = engineResourceFactory;
    }

    private void c(com.bumptech.glide.request.g gVar) {
        if (this.yn == null) {
            this.yn = new ArrayList(2);
        }
        if (!this.yn.contains(gVar)) {
            this.yn.add(gVar);
        }
    }

    private boolean d(com.bumptech.glide.request.g gVar) {
        List<com.bumptech.glide.request.g> list = this.yn;
        return list != null && list.contains(gVar);
    }

    private b qn() {
        return this.un ? this.en : this.vn ? this.nj : this.ij;
    }

    private void release(boolean z) {
        l.Lk();
        this.rn.clear();
        this.key = null;
        this.zn = null;
        this.resource = null;
        List<com.bumptech.glide.request.g> list = this.yn;
        if (list != null) {
            list.clear();
        }
        this.xn = false;
        this.Il = false;
        this.wn = false;
        this.An.release(z);
        this.An = null;
        this.exception = null;
        this.dataSource = null;
        this.pool.release(this);
    }

    @Override // com.bumptech.glide.load.engine.DecodeJob.a
    public void a(A<R> a2, DataSource dataSource2) {
        this.resource = a2;
        this.dataSource = dataSource2;
        Bn.obtainMessage(1, this).sendToTarget();
    }

    @Override // com.bumptech.glide.load.engine.DecodeJob.a
    public void a(DecodeJob<?> decodeJob) {
        qn().execute(decodeJob);
    }

    @Override // com.bumptech.glide.load.engine.DecodeJob.a
    public void a(GlideException glideException) {
        this.exception = glideException;
        Bn.obtainMessage(2, this).sendToTarget();
    }

    /* access modifiers changed from: package-private */
    public void a(com.bumptech.glide.request.g gVar) {
        l.Lk();
        this.Hm.Pk();
        if (this.wn) {
            gVar.a(this.zn, this.dataSource);
        } else if (this.xn) {
            gVar.a(this.exception);
        } else {
            this.rn.add(gVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(com.bumptech.glide.request.g gVar) {
        l.Lk();
        this.Hm.Pk();
        if (this.wn || this.xn) {
            c(gVar);
            return;
        }
        this.rn.remove(gVar);
        if (this.rn.isEmpty()) {
            cancel();
        }
    }

    public void c(DecodeJob<R> decodeJob) {
        this.An = decodeJob;
        (decodeJob.pj() ? this.jj : qn()).execute(decodeJob);
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
        if (!this.xn && !this.wn && !this.Il) {
            this.Il = true;
            this.An.cancel();
            this.listener.a(this, this.key);
        }
    }

    @Override // com.bumptech.glide.util.a.d.c
    @NonNull
    public g getVerifier() {
        return this.Hm;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public EngineJob<R> init(c cVar, boolean z, boolean z2, boolean z3, boolean z4) {
        this.key = cVar;
        this.tn = z;
        this.un = z2;
        this.vn = z3;
        this.Om = z4;
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean isCancelled() {
        return this.Il;
    }

    /* access modifiers changed from: package-private */
    public void sj() {
        this.Hm.Pk();
        if (this.Il) {
            this.listener.a(this, this.key);
            release(false);
            return;
        }
        throw new IllegalStateException("Not cancelled");
    }

    /* access modifiers changed from: package-private */
    public void tj() {
        this.Hm.Pk();
        if (this.Il) {
            release(false);
        } else if (this.rn.isEmpty()) {
            throw new IllegalStateException("Received an exception without any callbacks to notify");
        } else if (!this.xn) {
            this.xn = true;
            this.listener.a(this, this.key, null);
            for (com.bumptech.glide.request.g gVar : this.rn) {
                if (!d(gVar)) {
                    gVar.a(this.exception);
                }
            }
            release(false);
        } else {
            throw new IllegalStateException("Already failed once");
        }
    }

    /* access modifiers changed from: package-private */
    public void uj() {
        this.Hm.Pk();
        if (this.Il) {
            this.resource.recycle();
            release(false);
        } else if (this.rn.isEmpty()) {
            throw new IllegalStateException("Received a resource without any callbacks to notify");
        } else if (!this.wn) {
            this.zn = this.sn.a(this.resource, this.tn);
            this.wn = true;
            this.zn.acquire();
            this.listener.a(this, this.key, this.zn);
            int size = this.rn.size();
            for (int i = 0; i < size; i++) {
                com.bumptech.glide.request.g gVar = this.rn.get(i);
                if (!d(gVar)) {
                    this.zn.acquire();
                    gVar.a(this.zn, this.dataSource);
                }
            }
            this.zn.release();
            release(false);
        } else {
            throw new IllegalStateException("Already have resource");
        }
    }

    /* access modifiers changed from: package-private */
    public boolean vj() {
        return this.Om;
    }
}
