package com.bumptech.glide.d;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.bumptech.glide.util.h;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ModelToResourceClassCache */
public class d {
    private final AtomicReference<h> Es = new AtomicReference<>();
    private final ArrayMap<h, List<Class<?>>> Fs = new ArrayMap<>();

    public void a(@NonNull Class<?> cls, @NonNull Class<?> cls2, @NonNull List<Class<?>> list) {
        synchronized (this.Fs) {
            this.Fs.put(new h(cls, cls2), list);
        }
    }

    public void clear() {
        synchronized (this.Fs) {
            this.Fs.clear();
        }
    }

    @Nullable
    public List<Class<?>> d(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
        List<Class<?>> list;
        h andSet = this.Es.getAndSet(null);
        if (andSet == null) {
            andSet = new h(cls, cls2);
        } else {
            andSet.h(cls, cls2);
        }
        synchronized (this.Fs) {
            list = this.Fs.get(andSet);
        }
        this.Es.set(andSet);
        return list;
    }
}
