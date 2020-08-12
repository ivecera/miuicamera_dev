package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.g;
import com.bumptech.glide.util.i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: LoadPath */
public class x<Data, ResourceType, Transcode> {
    private final List<? extends i<Data, ResourceType, Transcode>> Kn;
    private final Pools.Pool<List<Throwable>> Zm;
    private final String _m;
    private final Class<Data> dataClass;

    public x(Class<Data> cls, Class<ResourceType> cls2, Class<Transcode> cls3, List<i<Data, ResourceType, Transcode>> list, Pools.Pool<List<Throwable>> pool) {
        this.dataClass = cls;
        this.Zm = pool;
        i.a(list);
        this.Kn = list;
        this._m = "Failed LoadPath{" + cls.getSimpleName() + "->" + cls2.getSimpleName() + "->" + cls3.getSimpleName() + "}";
    }

    private A<Transcode> a(e<Data> eVar, @NonNull g gVar, int i, int i2, i.a<ResourceType> aVar, List<Throwable> list) throws GlideException {
        int size = this.Kn.size();
        A<Transcode> a2 = null;
        for (int i3 = 0; i3 < size; i3++) {
            try {
                a2 = ((i) this.Kn.get(i3)).a(eVar, i, i2, gVar, aVar);
            } catch (GlideException e2) {
                list.add(e2);
            }
            if (a2 != null) {
                break;
            }
        }
        if (a2 != null) {
            return a2;
        }
        throw new GlideException(this._m, new ArrayList(list));
    }

    public A<Transcode> a(e<Data> eVar, @NonNull g gVar, int i, int i2, i.a<ResourceType> aVar) throws GlideException {
        List<Throwable> acquire = this.Zm.acquire();
        com.bumptech.glide.util.i.checkNotNull(acquire);
        List<Throwable> list = acquire;
        try {
            return a(eVar, gVar, i, i2, aVar, list);
        } finally {
            this.Zm.release(list);
        }
    }

    public Class<Data> ga() {
        return this.dataClass;
    }

    public String toString() {
        return "LoadPath{decodePaths=" + Arrays.toString(this.Kn.toArray()) + '}';
    }
}
