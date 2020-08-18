package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: ModelLoaderRegistry */
public class v {
    private final a cache;
    private final x gq;

    /* compiled from: ModelLoaderRegistry */
    private static class a {
        private final Map<Class<?>, C0013a<?>> fq = new HashMap();

        /* renamed from: com.bumptech.glide.load.model.v$a$a  reason: collision with other inner class name */
        /* compiled from: ModelLoaderRegistry */
        private static class C0013a<Model> {
            final List<t<Model, ?>> loaders;

            public C0013a(List<t<Model, ?>> list) {
                this.loaders = list;
            }
        }

        a() {
        }

        public <Model> void a(Class<Model> cls, List<t<Model, ?>> list) {
            if (this.fq.put(cls, new C0013a<>(list)) != null) {
                throw new IllegalStateException("Already cached loaders for model: " + cls);
            }
        }

        public void clear() {
            this.fq.clear();
        }

        @Nullable
        public <Model> List<t<Model, ?>> get(Class<Model> cls) {
            C0013a<?> aVar = this.fq.get(cls);
            if (aVar == null) {
                return null;
            }
            return aVar.loaders;
        }
    }

    public v(@NonNull Pools.Pool<List<Throwable>> pool) {
        this(new x(pool));
    }

    private v(@NonNull x xVar) {
        this.cache = new a();
        this.gq = xVar;
    }

    private <Model, Data> void h(@NonNull List<u<? extends Model, ? extends Data>> list) {
        for (u<? extends Model, ? extends Data> uVar : list) {
            uVar.X();
        }
    }

    @NonNull
    private <A> List<t<A, ?>> o(@NonNull Class<A> cls) {
        List<t<A, ?>> list = this.cache.get(cls);
        if (list != null) {
            return list;
        }
        List<t<A, ?>> unmodifiableList = Collections.unmodifiableList(this.gq.h(cls));
        this.cache.a(cls, unmodifiableList);
        return unmodifiableList;
    }

    /* JADX DEBUG: Type inference failed for r0v1. Raw type applied. Possible types: java.lang.Class<?>, java.lang.Class<A> */
    @NonNull
    private static <A> Class<A> x(@NonNull A a2) {
        return a2.getClass();
    }

    public synchronized <Model, Data> t<Model, Data> a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        return this.gq.a(cls, cls2);
    }

    public synchronized <Model, Data> void a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<? extends Model, ? extends Data> uVar) {
        this.gq.a(cls, cls2, uVar);
        this.cache.clear();
    }

    public synchronized <Model, Data> void b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        h(this.gq.b(cls, cls2));
        this.cache.clear();
    }

    public synchronized <Model, Data> void b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<? extends Model, ? extends Data> uVar) {
        this.gq.b(cls, cls2, uVar);
        this.cache.clear();
    }

    public synchronized <Model, Data> void c(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<? extends Model, ? extends Data> uVar) {
        h(this.gq.c(cls, cls2, uVar));
        this.cache.clear();
    }

    @NonNull
    public synchronized List<Class<?>> f(@NonNull Class<?> cls) {
        return this.gq.f(cls);
    }

    @NonNull
    public synchronized <A> List<t<A, ?>> j(@NonNull A a2) {
        ArrayList arrayList;
        List<t<A, ?>> o = o(x(a2));
        int size = o.size();
        arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            t<A, ?> tVar = o.get(i);
            if (tVar.c(a2)) {
                arrayList.add(tVar);
            }
        }
        return arrayList;
    }
}
