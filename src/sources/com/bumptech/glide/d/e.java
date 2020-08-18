package com.bumptech.glide.d;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.h;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: ResourceDecoderRegistry */
public class e {
    private final List<String> Gs = new ArrayList();
    private final Map<String, List<a<?, ?>>> Xm = new HashMap();

    /* compiled from: ResourceDecoderRegistry */
    private static class a<T, R> {
        private final Class<T> dataClass;
        final h<T, R> decoder;
        final Class<R> rm;

        public a(@NonNull Class<T> cls, @NonNull Class<R> cls2, h<T, R> hVar) {
            this.dataClass = cls;
            this.rm = cls2;
            this.decoder = hVar;
        }

        public boolean c(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return this.dataClass.isAssignableFrom(cls) && cls2.isAssignableFrom(this.rm);
        }
    }

    @NonNull
    private synchronized List<a<?, ?>> T(@NonNull String str) {
        List<a<?, ?>> list;
        if (!this.Gs.contains(str)) {
            this.Gs.add(str);
        }
        list = this.Xm.get(str);
        if (list == null) {
            list = new ArrayList<>();
            this.Xm.put(str, list);
        }
        return list;
    }

    public synchronized <T, R> void a(@NonNull String str, @NonNull h<T, R> hVar, @NonNull Class<T> cls, @NonNull Class<R> cls2) {
        T(str).add(new a<>(cls, cls2, hVar));
    }

    public synchronized <T, R> void b(@NonNull String str, @NonNull h<T, R> hVar, @NonNull Class<T> cls, @NonNull Class<R> cls2) {
        T(str).add(0, new a<>(cls, cls2, hVar));
    }

    public synchronized void e(@NonNull List<String> list) {
        ArrayList<String> arrayList = new ArrayList(this.Gs);
        this.Gs.clear();
        this.Gs.addAll(list);
        for (String str : arrayList) {
            if (!list.contains(str)) {
                this.Gs.add(str);
            }
        }
    }

    @NonNull
    public synchronized <T, R> List<h<T, R>> f(@NonNull Class<T> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (String str : this.Gs) {
            List<a<?, ?>> list = this.Xm.get(str);
            if (list != null) {
                for (a<?, ?> aVar : list) {
                    if (aVar.c(cls, cls2)) {
                        arrayList.add(aVar.decoder);
                    }
                }
            }
        }
        return arrayList;
    }

    @NonNull
    public synchronized <T, R> List<Class<R>> g(@NonNull Class<T> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (String str : this.Gs) {
            List<a<?, ?>> list = this.Xm.get(str);
            if (list != null) {
                for (a<?, ?> aVar : list) {
                    if (aVar.c(cls, cls2) && !arrayList.contains(aVar.rm)) {
                        arrayList.add(aVar.rm);
                    }
                }
            }
        }
        return arrayList;
    }
}
