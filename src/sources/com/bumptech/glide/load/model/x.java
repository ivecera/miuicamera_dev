package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import com.bumptech.glide.util.i;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: MultiModelLoaderFactory */
public class x {
    private static final c El = new c();
    private static final t<Object, Object> lq = new a();
    private final Pools.Pool<List<Throwable>> Hj;
    private final List<b<?, ?>> entries;
    private final c factory;
    private final Set<b<?, ?>> kq;

    /* compiled from: MultiModelLoaderFactory */
    private static class a implements t<Object, Object> {
        a() {
        }

        @Override // com.bumptech.glide.load.model.t
        @Nullable
        public t.a<Object> a(@NonNull Object obj, int i, int i2, @NonNull g gVar) {
            return null;
        }

        @Override // com.bumptech.glide.load.model.t
        public boolean c(@NonNull Object obj) {
            return false;
        }
    }

    /* compiled from: MultiModelLoaderFactory */
    private static class b<Model, Data> {
        final Class<Data> dataClass;
        final u<? extends Model, ? extends Data> factory;
        private final Class<Model> jq;

        public b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<? extends Model, ? extends Data> uVar) {
            this.jq = cls;
            this.dataClass = cls2;
            this.factory = uVar;
        }

        public boolean c(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return g(cls) && this.dataClass.isAssignableFrom(cls2);
        }

        public boolean g(@NonNull Class<?> cls) {
            return this.jq.isAssignableFrom(cls);
        }
    }

    /* compiled from: MultiModelLoaderFactory */
    static class c {
        c() {
        }

        @NonNull
        public <Model, Data> w<Model, Data> a(@NonNull List<t<Model, Data>> list, @NonNull Pools.Pool<List<Throwable>> pool) {
            return new w<>(list, pool);
        }
    }

    public x(@NonNull Pools.Pool<List<Throwable>> pool) {
        this(pool, El);
    }

    @VisibleForTesting
    x(@NonNull Pools.Pool<List<Throwable>> pool, @NonNull c cVar) {
        this.entries = new ArrayList();
        this.kq = new HashSet();
        this.Hj = pool;
        this.factory = cVar;
    }

    @NonNull
    private static <Model, Data> t<Model, Data> In() {
        return lq;
    }

    @NonNull
    private <Model, Data> t<Model, Data> a(@NonNull b<?, ?> bVar) {
        t<Model, Data> a2 = bVar.factory.a(this);
        i.checkNotNull(a2);
        return a2;
    }

    private <Model, Data> void a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<? extends Model, ? extends Data> uVar, boolean z) {
        b<?, ?> bVar = new b<>(cls, cls2, uVar);
        List<b<?, ?>> list = this.entries;
        list.add(z ? list.size() : 0, bVar);
    }

    @NonNull
    private <Model, Data> u<Model, Data> b(@NonNull b<?, ?> bVar) {
        return bVar.factory;
    }

    @NonNull
    public synchronized <Model, Data> t<Model, Data> a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        try {
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            for (b<?, ?> bVar : this.entries) {
                if (this.kq.contains(bVar)) {
                    z = true;
                } else if (bVar.c(cls, cls2)) {
                    this.kq.add(bVar);
                    arrayList.add(a(bVar));
                    this.kq.remove(bVar);
                }
            }
            if (arrayList.size() > 1) {
                return this.factory.a(arrayList, this.Hj);
            } else if (arrayList.size() == 1) {
                return (t) arrayList.get(0);
            } else if (z) {
                return In();
            } else {
                throw new Registry.NoModelLoaderAvailableException(cls, cls2);
            }
        } catch (Throwable th) {
            this.kq.clear();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized <Model, Data> void a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<? extends Model, ? extends Data> uVar) {
        a(cls, cls2, uVar, true);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized <Model, Data> List<u<? extends Model, ? extends Data>> b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<b<?, ?>> it = this.entries.iterator();
        while (it.hasNext()) {
            b<?, ?> next = it.next();
            if (next.c(cls, cls2)) {
                it.remove();
                arrayList.add(b(next));
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public synchronized <Model, Data> void b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<? extends Model, ? extends Data> uVar) {
        a(cls, cls2, uVar, false);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized <Model, Data> List<u<? extends Model, ? extends Data>> c(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<? extends Model, ? extends Data> uVar) {
        List<u<? extends Model, ? extends Data>> b2;
        b2 = b(cls, cls2);
        a(cls, cls2, uVar);
        return b2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized List<Class<?>> f(@NonNull Class<?> cls) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (b<?, ?> bVar : this.entries) {
            if (!arrayList.contains(bVar.dataClass) && bVar.g(cls)) {
                arrayList.add(bVar.dataClass);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized <Model> List<t<Model, ?>> h(@NonNull Class<Model> cls) {
        ArrayList arrayList;
        try {
            arrayList = new ArrayList();
            for (b<?, ?> bVar : this.entries) {
                if (!this.kq.contains(bVar)) {
                    if (bVar.g(cls)) {
                        this.kq.add(bVar);
                        arrayList.add(a(bVar));
                        this.kq.remove(bVar);
                    }
                }
            }
        } catch (Throwable th) {
            this.kq.clear();
            throw th;
        }
        return arrayList;
    }
}
