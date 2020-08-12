package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.f;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.util.List;

/* compiled from: ResourceCacheGenerator */
class B implements f, d.a<Object> {
    private int Pn = -1;
    private C Qn;
    private final f.a cb;
    private final g<?> jm;
    private int km;
    private c lm;
    private List<t<File, ?>> mm;
    private int nm;
    private volatile t.a<?> om;
    private File pm;

    B(g<?> gVar, f.a aVar) {
        this.jm = gVar;
        this.cb = aVar;
    }

    private boolean in() {
        return this.nm < this.mm.size();
    }

    @Override // com.bumptech.glide.load.engine.f
    public boolean K() {
        List<c> gj = this.jm.gj();
        boolean z = false;
        if (gj.isEmpty()) {
            return false;
        }
        List<Class<?>> kj = this.jm.kj();
        if (kj.isEmpty() && File.class.equals(this.jm.lj())) {
            return false;
        }
        while (true) {
            if (this.mm == null || !in()) {
                this.Pn++;
                if (this.Pn >= kj.size()) {
                    this.km++;
                    if (this.km >= gj.size()) {
                        return false;
                    }
                    this.Pn = 0;
                }
                c cVar = gj.get(this.km);
                Class<?> cls = kj.get(this.Pn);
                this.Qn = new C(this.jm.sa(), cVar, this.jm.getSignature(), this.jm.getWidth(), this.jm.getHeight(), this.jm.d(cls), cls, this.jm.getOptions());
                this.pm = this.jm.H().b(this.Qn);
                File file = this.pm;
                if (file != null) {
                    this.lm = cVar;
                    this.mm = this.jm.e(file);
                    this.nm = 0;
                }
            } else {
                this.om = null;
                while (!z && in()) {
                    List<t<File, ?>> list = this.mm;
                    int i = this.nm;
                    this.nm = i + 1;
                    this.om = list.get(i).a(this.pm, this.jm.getWidth(), this.jm.getHeight(), this.jm.getOptions());
                    if (this.om != null && this.jm.e((Class<?>) this.om.eq.ga())) {
                        this.om.eq.a(this.jm.getPriority(), this);
                        z = true;
                    }
                }
                return z;
            }
        }
    }

    @Override // com.bumptech.glide.load.a.d.a
    public void a(@NonNull Exception exc) {
        this.cb.a(this.Qn, exc, this.om.eq, DataSource.RESOURCE_DISK_CACHE);
    }

    @Override // com.bumptech.glide.load.a.d.a
    public void b(Object obj) {
        this.cb.a(this.lm, obj, this.om.eq, DataSource.RESOURCE_DISK_CACHE, this.Qn);
    }

    @Override // com.bumptech.glide.load.engine.f
    public void cancel() {
        t.a<?> aVar = this.om;
        if (aVar != null) {
            aVar.eq.cancel();
        }
    }
}
