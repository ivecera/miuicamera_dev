package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import com.bumptech.glide.util.a.d;
import com.bumptech.glide.util.a.g;
import com.bumptech.glide.util.i;

/* compiled from: LockedResource */
final class z<Z> implements A<Z>, d.c {
    private static final Pools.Pool<z<?>> Nn = d.b(20, new y());
    private final g Hm = g.newInstance();
    private A<Z> Ln;
    private boolean Mn;
    private boolean Sf;

    z() {
    }

    @NonNull
    static <Z> z<Z> f(A<Z> a2) {
        z<Z> acquire = Nn.acquire();
        i.checkNotNull(acquire);
        z<Z> zVar = acquire;
        zVar.i(a2);
        return zVar;
    }

    private void i(A<Z> a2) {
        this.Sf = false;
        this.Mn = true;
        this.Ln = a2;
    }

    private void release() {
        this.Ln = null;
        Nn.release(this);
    }

    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public Class<Z> T() {
        return this.Ln.T();
    }

    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public Z get() {
        return this.Ln.get();
    }

    @Override // com.bumptech.glide.load.engine.A
    public int getSize() {
        return this.Ln.getSize();
    }

    @Override // com.bumptech.glide.util.a.d.c
    @NonNull
    public g getVerifier() {
        return this.Hm;
    }

    @Override // com.bumptech.glide.load.engine.A
    public synchronized void recycle() {
        this.Hm.Pk();
        this.Sf = true;
        if (!this.Mn) {
            this.Ln.recycle();
            release();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void unlock() {
        this.Hm.Pk();
        if (this.Mn) {
            this.Mn = false;
            if (this.Sf) {
                recycle();
            }
        } else {
            throw new IllegalStateException("Already unlocked");
        }
    }
}
