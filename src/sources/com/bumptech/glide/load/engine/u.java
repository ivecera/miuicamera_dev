package com.bumptech.glide.load.engine;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.util.i;

/* compiled from: EngineResource */
class u<Z> implements A<Z> {
    private final boolean Fn;
    private int Gn;
    private boolean Sf;
    private c key;
    private a listener;
    private final A<Z> resource;
    private final boolean tn;

    /* compiled from: EngineResource */
    interface a {
        void a(c cVar, u<?> uVar);
    }

    u(A<Z> a2, boolean z, boolean z2) {
        i.checkNotNull(a2);
        this.resource = a2;
        this.tn = z;
        this.Fn = z2;
    }

    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public Class<Z> T() {
        return this.resource.T();
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar, a aVar) {
        this.key = cVar;
        this.listener = aVar;
    }

    /* access modifiers changed from: package-private */
    public void acquire() {
        if (this.Sf) {
            throw new IllegalStateException("Cannot acquire a recycled resource");
        } else if (Looper.getMainLooper().equals(Looper.myLooper())) {
            this.Gn++;
        } else {
            throw new IllegalThreadStateException("Must call acquire on the main thread");
        }
    }

    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public Z get() {
        return this.resource.get();
    }

    @Override // com.bumptech.glide.load.engine.A
    public int getSize() {
        return this.resource.getSize();
    }

    @Override // com.bumptech.glide.load.engine.A
    public void recycle() {
        if (this.Gn > 0) {
            throw new IllegalStateException("Cannot recycle a resource while it is still acquired");
        } else if (!this.Sf) {
            this.Sf = true;
            if (this.Fn) {
                this.resource.recycle();
            }
        } else {
            throw new IllegalStateException("Cannot recycle a resource that has already been recycled");
        }
    }

    /* access modifiers changed from: package-private */
    public void release() {
        if (this.Gn <= 0) {
            throw new IllegalStateException("Cannot release a recycled or not yet acquired resource");
        } else if (Looper.getMainLooper().equals(Looper.myLooper())) {
            int i = this.Gn - 1;
            this.Gn = i;
            if (i == 0) {
                this.listener.a(this.key, this);
            }
        } else {
            throw new IllegalThreadStateException("Must call release on the main thread");
        }
    }

    public String toString() {
        return "EngineResource{isCacheable=" + this.tn + ", listener=" + this.listener + ", key=" + this.key + ", acquired=" + this.Gn + ", isRecycled=" + this.Sf + ", resource=" + this.resource + '}';
    }

    /* access modifiers changed from: package-private */
    public A<Z> wj() {
        return this.resource;
    }

    /* access modifiers changed from: package-private */
    public boolean xj() {
        return this.tn;
    }
}
