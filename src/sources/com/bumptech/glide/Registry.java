package com.bumptech.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import com.bumptech.glide.d.a;
import com.bumptech.glide.d.b;
import com.bumptech.glide.d.c;
import com.bumptech.glide.d.d;
import com.bumptech.glide.d.e;
import com.bumptech.glide.d.f;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.load.a.g;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.engine.x;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.model.t;
import com.bumptech.glide.load.model.u;
import com.bumptech.glide.load.model.v;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Registry {
    public static final String Ij = "Gif";
    public static final String Jj = "Bitmap";
    public static final String Kj = "BitmapDrawable";
    private static final String Lj = "legacy_prepend_all";
    private static final String Mj = "legacy_append";
    private final e Aj = new e();
    private final f Bj = new f();
    private final g Cj = new g();
    private final com.bumptech.glide.load.b.d.f Dj = new com.bumptech.glide.load.b.d.f();
    private final b Ej = new b();
    private final d Fj = new d();
    private final c Gj = new c();
    private final Pools.Pool<List<Throwable>> Hj = com.bumptech.glide.util.a.d.Ok();
    private final v yj = new v(this.Hj);
    private final a zj = new a();

    public static class MissingComponentException extends RuntimeException {
        public MissingComponentException(@NonNull String str) {
            super(str);
        }
    }

    public static final class NoImageHeaderParserException extends MissingComponentException {
        public NoImageHeaderParserException() {
            super("Failed to find image header parser.");
        }
    }

    public static class NoModelLoaderAvailableException extends MissingComponentException {
        public NoModelLoaderAvailableException(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            super("Failed to find any ModelLoaders for model: " + cls + " and data: " + cls2);
        }

        public NoModelLoaderAvailableException(@NonNull Object obj) {
            super("Failed to find any ModelLoaders for model: " + obj);
        }
    }

    public static class NoResultEncoderAvailableException extends MissingComponentException {
        public NoResultEncoderAvailableException(@NonNull Class<?> cls) {
            super("Failed to find result encoder for resource class: " + cls + ", you may need to consider registering a new Encoder for the requested type or DiskCacheStrategy.DATA/DiskCacheStrategy.NONE if caching your transformed resource is unnecessary.");
        }
    }

    public static class NoSourceEncoderAvailableException extends MissingComponentException {
        public NoSourceEncoderAvailableException(@NonNull Class<?> cls) {
            super("Failed to find source encoder for data class: " + cls);
        }
    }

    public Registry() {
        d(Arrays.asList(Ij, Jj, Kj));
    }

    @NonNull
    private <Data, TResource, Transcode> List<i<Data, TResource, Transcode>> e(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        ArrayList arrayList = new ArrayList();
        for (Class cls4 : this.Aj.g(cls, cls2)) {
            for (Class cls5 : this.Dj.e(cls4, cls3)) {
                arrayList.add(new i(cls, cls4, cls5, this.Aj.f(cls, cls4), this.Dj.d(cls4, cls5), this.Hj));
            }
        }
        return arrayList;
    }

    @NonNull
    public List<ImageHeaderParser> Ji() {
        List<ImageHeaderParser> Qj = this.Ej.Qj();
        if (!Qj.isEmpty()) {
            return Qj;
        }
        throw new NoImageHeaderParserException();
    }

    @NonNull
    public Registry a(@NonNull ImageHeaderParser imageHeaderParser) {
        this.Ej.b(imageHeaderParser);
        return this;
    }

    @NonNull
    public Registry a(@NonNull e.a<?> aVar) {
        this.Cj.a(aVar);
        return this;
    }

    @NonNull
    public <Data> Registry a(@NonNull Class<Data> cls, @NonNull com.bumptech.glide.load.a<Data> aVar) {
        this.zj.a(cls, aVar);
        return this;
    }

    @NonNull
    public <TResource> Registry a(@NonNull Class<TResource> cls, @NonNull com.bumptech.glide.load.i<TResource> iVar) {
        this.Bj.a(cls, iVar);
        return this;
    }

    @NonNull
    public <TResource, Transcode> Registry a(@NonNull Class<TResource> cls, @NonNull Class<Transcode> cls2, @NonNull com.bumptech.glide.load.b.d.e<TResource, Transcode> eVar) {
        this.Dj.a(cls, cls2, eVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry a(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull h<Data, TResource> hVar) {
        a(Mj, cls, cls2, hVar);
        return this;
    }

    @NonNull
    public <Model, Data> Registry a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<Model, Data> uVar) {
        this.yj.a(cls, cls2, uVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry a(@NonNull String str, @NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull h<Data, TResource> hVar) {
        this.Aj.a(str, hVar, cls, cls2);
        return this;
    }

    @Nullable
    public <Data, TResource, Transcode> x<Data, TResource, Transcode> a(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        x<Data, TResource, Transcode> c2 = this.Gj.c(cls, cls2, cls3);
        if (this.Gj.a(c2)) {
            return null;
        }
        if (c2 == null) {
            List<i<Data, TResource, Transcode>> e2 = e(cls, cls2, cls3);
            c2 = e2.isEmpty() ? null : new x<>(cls, cls2, cls3, e2, this.Hj);
            this.Gj.a(cls, cls2, cls3, c2);
        }
        return c2;
    }

    @NonNull
    public <Data> Registry b(@NonNull Class<Data> cls, @NonNull com.bumptech.glide.load.a<Data> aVar) {
        this.zj.b(cls, aVar);
        return this;
    }

    @NonNull
    public <TResource> Registry b(@NonNull Class<TResource> cls, @NonNull com.bumptech.glide.load.i<TResource> iVar) {
        this.Bj.b(cls, iVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry b(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull h<Data, TResource> hVar) {
        b(Lj, cls, cls2, hVar);
        return this;
    }

    @NonNull
    public <Model, Data> Registry b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<Model, Data> uVar) {
        this.yj.b(cls, cls2, uVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry b(@NonNull String str, @NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull h<Data, TResource> hVar) {
        this.Aj.b(str, hVar, cls, cls2);
        return this;
    }

    @NonNull
    public <Model, TResource, Transcode> List<Class<?>> b(@NonNull Class<Model> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        List<Class<?>> d2 = this.Fj.d(cls, cls2);
        if (d2 == null) {
            d2 = new ArrayList<>();
            for (Class<?> cls4 : this.yj.f(cls)) {
                for (Class<?> cls5 : this.Aj.g(cls4, cls2)) {
                    if (!this.Dj.e(cls5, cls3).isEmpty() && !d2.contains(cls5)) {
                        d2.add(cls5);
                    }
                }
            }
            this.Fj.a(cls, cls2, Collections.unmodifiableList(d2));
        }
        return d2;
    }

    @NonNull
    @Deprecated
    public <Data> Registry c(@NonNull Class<Data> cls, @NonNull com.bumptech.glide.load.a<Data> aVar) {
        return a(cls, aVar);
    }

    @NonNull
    @Deprecated
    public <TResource> Registry c(@NonNull Class<TResource> cls, @NonNull com.bumptech.glide.load.i<TResource> iVar) {
        return a((Class) cls, (com.bumptech.glide.load.i) iVar);
    }

    @NonNull
    public <Model, Data> Registry c(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<? extends Model, ? extends Data> uVar) {
        this.yj.c(cls, cls2, uVar);
        return this;
    }

    @NonNull
    public <X> com.bumptech.glide.load.i<X> c(@NonNull A<X> a2) throws NoResultEncoderAvailableException {
        com.bumptech.glide.load.i<X> iVar = this.Bj.get(a2.T());
        if (iVar != null) {
            return iVar;
        }
        throw new NoResultEncoderAvailableException(a2.T());
    }

    @NonNull
    public final Registry d(@NonNull List<String> list) {
        ArrayList arrayList = new ArrayList(list);
        arrayList.add(0, Lj);
        arrayList.add(Mj);
        this.Aj.e(arrayList);
        return this;
    }

    public boolean d(@NonNull A<?> a2) {
        return this.Bj.get(a2.T()) != null;
    }

    @NonNull
    public <Model> List<t<Model, ?>> j(@NonNull Model model) {
        List<t<Model, ?>> j = this.yj.j(model);
        if (!j.isEmpty()) {
            return j;
        }
        throw new NoModelLoaderAvailableException(model);
    }

    @NonNull
    public <X> com.bumptech.glide.load.a.e<X> k(@NonNull X x) {
        return this.Cj.build(x);
    }

    @NonNull
    public <X> com.bumptech.glide.load.a<X> l(@NonNull X x) throws NoSourceEncoderAvailableException {
        com.bumptech.glide.load.a<X> i = this.zj.i(x.getClass());
        if (i != null) {
            return i;
        }
        throw new NoSourceEncoderAvailableException(x.getClass());
    }
}
