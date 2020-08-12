package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.f;
import com.bumptech.glide.load.model.t;
import com.bumptech.glide.util.e;
import java.util.Collections;
import java.util.List;

/* compiled from: SourceGenerator */
class E implements f, d.a<Object>, f.a {
    private static final String TAG = "SourceGenerator";
    private int Wn;
    private c Xn;
    private Object Yn;
    private d Zn;
    private final f.a cb;
    private final g<?> jm;
    private volatile t.a<?> om;

    E(g<?> gVar, f.a aVar) {
        this.jm = gVar;
        this.cb = aVar;
    }

    private boolean in() {
        return this.Wn < this.jm.ij().size();
    }

    /* JADX INFO: finally extract failed */
    private void v(Object obj) {
        long Jk = e.Jk();
        try {
            a<X> l = this.jm.l(obj);
            e eVar = new e(l, obj, this.jm.getOptions());
            this.Zn = new d(this.om.lm, this.jm.getSignature());
            this.jm.H().a(this.Zn, eVar);
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Finished encoding source to cache, key: " + this.Zn + ", data: " + obj + ", encoder: " + l + ", duration: " + e.e(Jk));
            }
            this.om.eq.cleanup();
            this.Xn = new c(Collections.singletonList(this.om.lm), this.jm, this);
        } catch (Throwable th) {
            this.om.eq.cleanup();
            throw th;
        }
    }

    @Override // com.bumptech.glide.load.engine.f
    public boolean K() {
        Object obj = this.Yn;
        if (obj != null) {
            this.Yn = null;
            v(obj);
        }
        c cVar = this.Xn;
        if (cVar != null && cVar.K()) {
            return true;
        }
        this.Xn = null;
        this.om = null;
        boolean z = false;
        while (!z && in()) {
            List<t.a<?>> ij = this.jm.ij();
            int i = this.Wn;
            this.Wn = i + 1;
            this.om = ij.get(i);
            if (this.om != null && (this.jm.hj().a(this.om.eq.L()) || this.jm.e((Class<?>) this.om.eq.ga()))) {
                this.om.eq.a(this.jm.getPriority(), this);
                z = true;
            }
        }
        return z;
    }

    @Override // com.bumptech.glide.load.engine.f.a
    public void a(c cVar, Exception exc, d<?> dVar, DataSource dataSource) {
        this.cb.a(cVar, exc, dVar, this.om.eq.L());
    }

    @Override // com.bumptech.glide.load.engine.f.a
    public void a(c cVar, Object obj, d<?> dVar, DataSource dataSource, c cVar2) {
        this.cb.a(cVar, obj, dVar, this.om.eq.L(), cVar);
    }

    @Override // com.bumptech.glide.load.a.d.a
    public void a(@NonNull Exception exc) {
        this.cb.a(this.Zn, exc, this.om.eq, this.om.eq.L());
    }

    @Override // com.bumptech.glide.load.a.d.a
    public void b(Object obj) {
        o hj = this.jm.hj();
        if (obj == null || !hj.a(this.om.eq.L())) {
            this.cb.a(this.om.lm, obj, this.om.eq, this.om.eq.L(), this.Zn);
            return;
        }
        this.Yn = obj;
        this.cb.ea();
    }

    @Override // com.bumptech.glide.load.engine.f
    public void cancel() {
        t.a<?> aVar = this.om;
        if (aVar != null) {
            aVar.eq.cancel();
        }
    }

    @Override // com.bumptech.glide.load.engine.f.a
    public void ea() {
        throw new UnsupportedOperationException();
    }
}
