package com.bumptech.glide.load.engine;

import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.e;
import com.bumptech.glide.load.b.b;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.j;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: DecodeHelper */
final class g<Transcode> {
    private Class<Transcode> Pj;
    private e _i;
    private int height;
    private final List<c> im = new ArrayList();
    private Object model;
    private final List<t.a<?>> om = new ArrayList();
    private com.bumptech.glide.load.g options;
    private Priority priority;
    private c qm;
    private Class<?> rm;
    private DecodeJob.d sm;
    private boolean tm;
    private boolean um;
    private o vm;
    private int width;
    private boolean wm;
    private Map<Class<?>, j<?>> xl;
    private boolean xm;

    g() {
    }

    /* access modifiers changed from: package-private */
    public a H() {
        return this.sm.H();
    }

    /* access modifiers changed from: package-private */
    public <R> void a(e eVar, Object obj, c cVar, int i, int i2, o oVar, Class<?> cls, Class<R> cls2, Priority priority2, com.bumptech.glide.load.g gVar, Map<Class<?>, j<?>> map, boolean z, boolean z2, DecodeJob.d dVar) {
        this._i = eVar;
        this.model = obj;
        this.qm = cVar;
        this.width = i;
        this.height = i2;
        this.vm = oVar;
        this.rm = cls;
        this.sm = dVar;
        this.Pj = cls2;
        this.priority = priority2;
        this.options = gVar;
        this.xl = map;
        this.wm = z;
        this.xm = z2;
    }

    /* access modifiers changed from: package-private */
    public <Data> x<Data, ?, Transcode> c(Class<Data> cls) {
        return this._i.wa().a(cls, this.rm, this.Pj);
    }

    /* access modifiers changed from: package-private */
    public <Z> i<Z> c(A<Z> a2) {
        return this._i.wa().c(a2);
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this._i = null;
        this.model = null;
        this.qm = null;
        this.rm = null;
        this.Pj = null;
        this.options = null;
        this.priority = null;
        this.xl = null;
        this.vm = null;
        this.om.clear();
        this.tm = false;
        this.im.clear();
        this.um = false;
    }

    /* access modifiers changed from: package-private */
    public <Z> j<Z> d(Class<Z> cls) {
        j<Z> jVar = this.xl.get(cls);
        if (jVar == null) {
            Iterator<Map.Entry<Class<?>, j<?>>> it = this.xl.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<Class<?>, j<?>> next = it.next();
                if (next.getKey().isAssignableFrom(cls)) {
                    jVar = next.getValue();
                    break;
                }
            }
        }
        if (jVar != null) {
            return jVar;
        }
        if (!this.xl.isEmpty() || !this.wm) {
            return b.get();
        }
        throw new IllegalArgumentException("Missing transformation for " + cls + ". If you wish to ignore unknown resource types, use the optional transformation methods.");
    }

    /* access modifiers changed from: package-private */
    public boolean d(A<?> a2) {
        return this._i.wa().d(a2);
    }

    /* access modifiers changed from: package-private */
    public List<t<File, ?>> e(File file) throws Registry.NoModelLoaderAvailableException {
        return this._i.wa().j(file);
    }

    /* access modifiers changed from: package-private */
    public boolean e(c cVar) {
        List<t.a<?>> ij = ij();
        int size = ij.size();
        for (int i = 0; i < size; i++) {
            if (ij.get(i).lm.equals(cVar)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean e(Class<?> cls) {
        return c(cls) != null;
    }

    /* access modifiers changed from: package-private */
    public int getHeight() {
        return this.height;
    }

    /* access modifiers changed from: package-private */
    public com.bumptech.glide.load.g getOptions() {
        return this.options;
    }

    /* access modifiers changed from: package-private */
    public Priority getPriority() {
        return this.priority;
    }

    /* access modifiers changed from: package-private */
    public c getSignature() {
        return this.qm;
    }

    /* access modifiers changed from: package-private */
    public int getWidth() {
        return this.width;
    }

    /* access modifiers changed from: package-private */
    public List<c> gj() {
        if (!this.um) {
            this.um = true;
            this.im.clear();
            List<t.a<?>> ij = ij();
            int size = ij.size();
            for (int i = 0; i < size; i++) {
                t.a<?> aVar = ij.get(i);
                if (!this.im.contains(aVar.lm)) {
                    this.im.add(aVar.lm);
                }
                for (int i2 = 0; i2 < aVar.dq.size(); i2++) {
                    if (!this.im.contains(aVar.dq.get(i2))) {
                        this.im.add(aVar.dq.get(i2));
                    }
                }
            }
        }
        return this.im;
    }

    /* access modifiers changed from: package-private */
    public o hj() {
        return this.vm;
    }

    /* access modifiers changed from: package-private */
    public List<t.a<?>> ij() {
        if (!this.tm) {
            this.tm = true;
            this.om.clear();
            List j = this._i.wa().j(this.model);
            int size = j.size();
            for (int i = 0; i < size; i++) {
                t.a<?> a2 = ((t) j.get(i)).a(this.model, this.width, this.height, this.options);
                if (a2 != null) {
                    this.om.add(a2);
                }
            }
        }
        return this.om;
    }

    /* access modifiers changed from: package-private */
    public Class<?> jj() {
        return this.model.getClass();
    }

    /* access modifiers changed from: package-private */
    public List<Class<?>> kj() {
        return this._i.wa().b(this.model.getClass(), this.rm, this.Pj);
    }

    /* access modifiers changed from: package-private */
    public <X> com.bumptech.glide.load.a<X> l(X x) throws Registry.NoSourceEncoderAvailableException {
        return this._i.wa().l(x);
    }

    /* JADX DEBUG: Type inference failed for r0v1. Raw type applied. Possible types: java.lang.Class<Transcode>, java.lang.Class<?> */
    /* access modifiers changed from: package-private */
    public Class<?> lj() {
        return this.Pj;
    }

    /* access modifiers changed from: package-private */
    public boolean mj() {
        return this.xm;
    }

    /* access modifiers changed from: package-private */
    public com.bumptech.glide.load.engine.bitmap_recycle.b sa() {
        return this._i.sa();
    }
}
