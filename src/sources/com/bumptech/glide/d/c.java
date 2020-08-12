package com.bumptech.glide.d;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.bumptech.glide.load.b.d.g;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.engine.x;
import com.bumptech.glide.util.h;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: LoadPathCache */
public class c {
    private static final x<?, ?, ?> Ds = new x<>(Object.class, Object.class, Object.class, Collections.singletonList(new i(Object.class, Object.class, Object.class, Collections.emptyList(), new g(), null)), null);
    private final AtomicReference<h> Cs = new AtomicReference<>();
    private final ArrayMap<h, x<?, ?, ?>> cache = new ArrayMap<>();

    private h f(Class<?> cls, Class<?> cls2, Class<?> cls3) {
        h andSet = this.Cs.getAndSet(null);
        if (andSet == null) {
            andSet = new h();
        }
        andSet.d(cls, cls2, cls3);
        return andSet;
    }

    public void a(Class<?> cls, Class<?> cls2, Class<?> cls3, @Nullable x<?, ?, ?> xVar) {
        synchronized (this.cache) {
            ArrayMap<h, x<?, ?, ?>> arrayMap = this.cache;
            h hVar = new h(cls, cls2, cls3);
            if (xVar == null) {
                xVar = Ds;
            }
            arrayMap.put(hVar, xVar);
        }
    }

    public boolean a(@Nullable x<?, ?, ?> xVar) {
        return Ds.equals(xVar);
    }

    @Nullable
    public <Data, TResource, Transcode> x<Data, TResource, Transcode> c(Class<Data> cls, Class<TResource> cls2, Class<Transcode> cls3) {
        x<Data, TResource, Transcode> xVar;
        h f2 = f(cls, cls2, cls3);
        synchronized (this.cache) {
            xVar = this.cache.get(f2);
        }
        this.Cs.set(f2);
        return xVar;
    }
}
