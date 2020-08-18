package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.engine.f;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.util.List;

/* compiled from: DataCacheGenerator */
class c implements f, d.a<Object> {
    private final f.a cb;
    private final List<com.bumptech.glide.load.c> im;
    private final g<?> jm;
    private int km;
    private com.bumptech.glide.load.c lm;
    private List<t<File, ?>> mm;
    private int nm;
    private volatile t.a<?> om;
    private File pm;

    c(g<?> gVar, f.a aVar) {
        this(gVar.gj(), gVar, aVar);
    }

    c(List<com.bumptech.glide.load.c> list, g<?> gVar, f.a aVar) {
        this.km = -1;
        this.im = list;
        this.jm = gVar;
        this.cb = aVar;
    }

    private boolean in() {
        return this.nm < this.mm.size();
    }

    @Override // com.bumptech.glide.load.engine.f
    public boolean K() {
        while (true) {
            boolean z = false;
            if (this.mm == null || !in()) {
                this.km++;
                if (this.km >= this.im.size()) {
                    return false;
                }
                com.bumptech.glide.load.c cVar = this.im.get(this.km);
                this.pm = this.jm.H().b(new d(cVar, this.jm.getSignature()));
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
        this.cb.a(this.lm, exc, this.om.eq, DataSource.DATA_DISK_CACHE);
    }

    @Override // com.bumptech.glide.load.a.d.a
    public void b(Object obj) {
        this.cb.a(this.lm, obj, this.om.eq, DataSource.DATA_DISK_CACHE, this.lm);
    }

    @Override // com.bumptech.glide.load.engine.f
    public void cancel() {
        t.a<?> aVar = this.om;
        if (aVar != null) {
            aVar.eq.cancel();
        }
    }
}
