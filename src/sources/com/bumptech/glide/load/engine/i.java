package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import android.util.Log;
import com.bumptech.glide.load.b.d.e;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DecodePath */
public class i<DataType, ResourceType, Transcode> {
    private static final String TAG = "DecodePath";
    private final List<? extends h<DataType, ResourceType>> Xm;
    private final e<ResourceType, Transcode> Ym;
    private final Pools.Pool<List<Throwable>> Zm;
    private final String _m;
    private final Class<DataType> dataClass;

    /* compiled from: DecodePath */
    interface a<ResourceType> {
        @NonNull
        A<ResourceType> a(@NonNull A<ResourceType> a2);
    }

    public i(Class<DataType> cls, Class<ResourceType> cls2, Class<Transcode> cls3, List<? extends h<DataType, ResourceType>> list, e<ResourceType, Transcode> eVar, Pools.Pool<List<Throwable>> pool) {
        this.dataClass = cls;
        this.Xm = list;
        this.Ym = eVar;
        this.Zm = pool;
        this._m = "Failed DecodePath{" + cls.getSimpleName() + "->" + cls2.getSimpleName() + "->" + cls3.getSimpleName() + "}";
    }

    @NonNull
    private A<ResourceType> a(com.bumptech.glide.load.a.e<DataType> eVar, int i, int i2, @NonNull g gVar) throws GlideException {
        List<Throwable> acquire = this.Zm.acquire();
        com.bumptech.glide.util.i.checkNotNull(acquire);
        List<Throwable> list = acquire;
        try {
            return a(eVar, i, i2, gVar, list);
        } finally {
            this.Zm.release(list);
        }
    }

    @NonNull
    private A<ResourceType> a(com.bumptech.glide.load.a.e<DataType> eVar, int i, int i2, @NonNull g gVar, List<Throwable> list) throws GlideException {
        int size = this.Xm.size();
        A<ResourceType> a2 = null;
        for (int i3 = 0; i3 < size; i3++) {
            h hVar = (h) this.Xm.get(i3);
            try {
                if (hVar.a(eVar.W(), gVar)) {
                    a2 = hVar.b(eVar.W(), i, i2, gVar);
                }
            } catch (IOException | OutOfMemoryError | RuntimeException e2) {
                if (Log.isLoggable(TAG, 2)) {
                    Log.v(TAG, "Failed to decode data for " + hVar, e2);
                }
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

    public A<Transcode> a(com.bumptech.glide.load.a.e<DataType> eVar, int i, int i2, @NonNull g gVar, a<ResourceType> aVar) throws GlideException {
        return this.Ym.a(aVar.a(a(eVar, i, i2, gVar)), gVar);
    }

    public String toString() {
        return "DecodePath{ dataClass=" + this.dataClass + ", decoders=" + this.Xm + ", transcoder=" + this.Ym + '}';
    }
}
