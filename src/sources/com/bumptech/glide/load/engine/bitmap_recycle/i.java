package com.bumptech.glide.load.engine.bitmap_recycle;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/* compiled from: LruArrayPool */
public final class i implements b {
    private static final int DEFAULT_SIZE = 4194304;
    @VisibleForTesting
    static final int MAX_OVER_SIZE_MULTIPLE = 8;
    private static final int ho = 2;
    private final b _n;
    private final g<a, Object> bo;
    private int currentSize;
    private final Map<Class<?>, NavigableMap<Integer, Integer>> fo;
    private final Map<Class<?>, a<?>> go;
    private final int maxSize;

    /* compiled from: LruArrayPool */
    private static final class a implements l {
        private Class<?> eo;
        private final b pool;
        int size;

        a(b bVar) {
            this.pool = bVar;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.l
        public void O() {
            this.pool.a(this);
        }

        /* access modifiers changed from: package-private */
        public void c(int i, Class<?> cls) {
            this.size = i;
            this.eo = cls;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.size == aVar.size && this.eo == aVar.eo;
        }

        public int hashCode() {
            int i = this.size * 31;
            Class<?> cls = this.eo;
            return i + (cls != null ? cls.hashCode() : 0);
        }

        public String toString() {
            return "Key{size=" + this.size + "array=" + this.eo + '}';
        }
    }

    /* compiled from: LruArrayPool */
    private static final class b extends c<a> {
        b() {
        }

        /* access modifiers changed from: package-private */
        public a a(int i, Class<?> cls) {
            a aVar = (a) get();
            aVar.c(i, cls);
            return aVar;
        }

        /* access modifiers changed from: protected */
        @Override // com.bumptech.glide.load.engine.bitmap_recycle.c
        public a create() {
            return new a(this);
        }
    }

    @VisibleForTesting
    public i() {
        this.bo = new g<>();
        this._n = new b();
        this.fo = new HashMap();
        this.go = new HashMap();
        this.maxSize = 4194304;
    }

    public i(int i) {
        this.bo = new g<>();
        this._n = new b();
        this.fo = new HashMap();
        this.go = new HashMap();
        this.maxSize = i;
    }

    private void Z(int i) {
        while (this.currentSize > i) {
            Object removeLast = this.bo.removeLast();
            com.bumptech.glide.util.i.checkNotNull(removeLast);
            a w = w(removeLast);
            this.currentSize -= w.a(removeLast) * w.Y();
            d(w.a(removeLast), removeLast.getClass());
            if (Log.isLoggable(w.getTag(), 2)) {
                Log.v(w.getTag(), "evicted: " + w.a(removeLast));
            }
        }
    }

    @Nullable
    private <T> T a(a aVar) {
        return this.bo.b(aVar);
    }

    private <T> T a(a aVar, Class<T> cls) {
        a<T> m = m(cls);
        T a2 = a(aVar);
        if (a2 != null) {
            this.currentSize -= m.a(a2) * m.Y();
            d(m.a(a2), cls);
        }
        if (a2 != null) {
            return a2;
        }
        if (Log.isLoggable(m.getTag(), 2)) {
            Log.v(m.getTag(), "Allocated " + aVar.size + " bytes");
        }
        return m.newArray(aVar.size);
    }

    private boolean a(int i, Integer num) {
        return num != null && (tn() || num.intValue() <= i * 8);
    }

    private boolean aa(int i) {
        return i <= this.maxSize / 2;
    }

    private void d(int i, Class<?> cls) {
        NavigableMap<Integer, Integer> n = n(cls);
        Integer num = (Integer) n.get(Integer.valueOf(i));
        if (num == null) {
            throw new NullPointerException("Tried to decrement empty size, size: " + i + ", this: " + this);
        } else if (num.intValue() == 1) {
            n.remove(Integer.valueOf(i));
        } else {
            n.put(Integer.valueOf(i), Integer.valueOf(num.intValue() - 1));
        }
    }

    private <T> a<T> m(Class<T> cls) {
        a<T> aVar = this.go.get(cls);
        if (aVar == null) {
            if (cls.equals(int[].class)) {
                aVar = new h();
            } else if (cls.equals(byte[].class)) {
                aVar = new f();
            } else {
                throw new IllegalArgumentException("No array pool found for: " + cls.getSimpleName());
            }
            this.go.put(cls, aVar);
        }
        return aVar;
    }

    private NavigableMap<Integer, Integer> n(Class<?> cls) {
        NavigableMap<Integer, Integer> navigableMap = this.fo.get(cls);
        if (navigableMap != null) {
            return navigableMap;
        }
        TreeMap treeMap = new TreeMap();
        this.fo.put(cls, treeMap);
        return treeMap;
    }

    private void sn() {
        Z(this.maxSize);
    }

    private boolean tn() {
        int i = this.currentSize;
        return i == 0 || this.maxSize / i >= 2;
    }

    private <T> a<T> w(T t) {
        return m(t.getClass());
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.b
    public synchronized <T> T a(int i, Class<T> cls) {
        Integer ceilingKey;
        ceilingKey = n(cls).ceilingKey(Integer.valueOf(i));
        return a(a(i, ceilingKey) ? this._n.a(ceilingKey.intValue(), cls) : this._n.a(i, cls), (Class) cls);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.b
    @Deprecated
    public <T> void a(T t, Class<T> cls) {
        put(t);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.b
    public synchronized void aa() {
        Z(0);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.b
    public synchronized <T> T b(int i, Class<T> cls) {
        return a(this._n.a(i, cls), (Class) cls);
    }

    /* access modifiers changed from: package-private */
    public int da() {
        int i = 0;
        for (Class<?> cls : this.fo.keySet()) {
            for (Integer num : this.fo.get(cls).keySet()) {
                i += num.intValue() * ((Integer) this.fo.get(cls).get(num)).intValue() * m(cls).Y();
            }
        }
        return i;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.b
    public synchronized <T> void put(T t) {
        Class<?> cls = t.getClass();
        a<T> m = m(cls);
        int a2 = m.a(t);
        int Y = m.Y() * a2;
        if (aa(Y)) {
            a a3 = this._n.a(a2, cls);
            this.bo.a(a3, t);
            NavigableMap<Integer, Integer> n = n(cls);
            Integer num = (Integer) n.get(Integer.valueOf(a3.size));
            Integer valueOf = Integer.valueOf(a3.size);
            int i = 1;
            if (num != null) {
                i = 1 + num.intValue();
            }
            n.put(valueOf, Integer.valueOf(i));
            this.currentSize += Y;
            sn();
        }
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.b
    public synchronized void trimMemory(int i) {
        if (i >= 40) {
            try {
                aa();
            } catch (Throwable th) {
                throw th;
            }
        } else if (i >= 20 || i == 15) {
            Z(this.maxSize / 2);
        }
    }
}
