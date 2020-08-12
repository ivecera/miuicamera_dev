package com.bumptech.glide.load.b.d;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/* compiled from: TranscoderRegistry */
public class f {
    private final List<a<?, ?>> is = new ArrayList();

    /* compiled from: TranscoderRegistry */
    private static final class a<Z, R> {
        final e<Z, R> Ym;
        private final Class<Z> gs;
        private final Class<R> hs;

        a(@NonNull Class<Z> cls, @NonNull Class<R> cls2, @NonNull e<Z, R> eVar) {
            this.gs = cls;
            this.hs = cls2;
            this.Ym = eVar;
        }

        public boolean c(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return this.gs.isAssignableFrom(cls) && cls2.isAssignableFrom(this.hs);
        }
    }

    public synchronized <Z, R> void a(@NonNull Class<Z> cls, @NonNull Class<R> cls2, @NonNull e<Z, R> eVar) {
        this.is.add(new a<>(cls, cls2, eVar));
    }

    @NonNull
    public synchronized <Z, R> e<Z, R> d(@NonNull Class<Z> cls, @NonNull Class<R> cls2) {
        if (cls2.isAssignableFrom(cls)) {
            return g.get();
        }
        for (a<?, ?> aVar : this.is) {
            if (aVar.c(cls, cls2)) {
                return aVar.Ym;
            }
        }
        throw new IllegalArgumentException("No transcoder registered to transcode from " + cls + " to " + cls2);
    }

    @NonNull
    public synchronized <Z, R> List<Class<R>> e(@NonNull Class<Z> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList = new ArrayList();
        if (cls2.isAssignableFrom(cls)) {
            arrayList.add(cls2);
            return arrayList;
        }
        for (a<?, ?> aVar : this.is) {
            if (aVar.c(cls, cls2)) {
                arrayList.add(cls2);
            }
        }
        return arrayList;
    }
}
