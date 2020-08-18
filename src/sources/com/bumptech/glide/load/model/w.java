package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import com.bumptech.glide.util.i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: MultiModelLoader */
class w<Model, Data> implements t<Model, Data> {
    private final Pools.Pool<List<Throwable>> iq;
    private final List<t<Model, Data>> mm;

    /* compiled from: MultiModelLoader */
    static class a<Data> implements d<Data>, d.a<Data> {
        private final Pools.Pool<List<Throwable>> Hj;
        private d.a<? super Data> callback;
        private int currentIndex = 0;
        @Nullable
        private List<Throwable> exceptions;
        private final List<d<Data>> hq;
        private Priority priority;

        a(@NonNull List<d<Data>> list, @NonNull Pools.Pool<List<Throwable>> pool) {
            this.Hj = pool;
            i.a(list);
            this.hq = list;
        }

        private void Hn() {
            if (this.currentIndex < this.hq.size() - 1) {
                this.currentIndex++;
                a(this.priority, this.callback);
                return;
            }
            i.checkNotNull(this.exceptions);
            this.callback.a(new GlideException("Fetch failed", new ArrayList(this.exceptions)));
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public DataSource L() {
            return this.hq.get(0).L();
        }

        @Override // com.bumptech.glide.load.a.d
        public void a(@NonNull Priority priority2, @NonNull d.a<? super Data> aVar) {
            this.priority = priority2;
            this.callback = aVar;
            this.exceptions = this.Hj.acquire();
            this.hq.get(this.currentIndex).a(priority2, this);
        }

        @Override // com.bumptech.glide.load.a.d.a
        public void a(@NonNull Exception exc) {
            List<Throwable> list = this.exceptions;
            i.checkNotNull(list);
            list.add(exc);
            Hn();
        }

        @Override // com.bumptech.glide.load.a.d.a
        public void b(@Nullable Data data) {
            if (data != null) {
                this.callback.b(data);
            } else {
                Hn();
            }
        }

        @Override // com.bumptech.glide.load.a.d
        public void cancel() {
            for (d<Data> dVar : this.hq) {
                dVar.cancel();
            }
        }

        @Override // com.bumptech.glide.load.a.d
        public void cleanup() {
            List<Throwable> list = this.exceptions;
            if (list != null) {
                this.Hj.release(list);
            }
            this.exceptions = null;
            for (d<Data> dVar : this.hq) {
                dVar.cleanup();
            }
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public Class<Data> ga() {
            return this.hq.get(0).ga();
        }
    }

    w(@NonNull List<t<Model, Data>> list, @NonNull Pools.Pool<List<Throwable>> pool) {
        this.mm = list;
        this.iq = pool;
    }

    @Override // com.bumptech.glide.load.model.t
    public t.a<Data> a(@NonNull Model model, int i, int i2, @NonNull g gVar) {
        t.a<Data> a2;
        int size = this.mm.size();
        ArrayList arrayList = new ArrayList(size);
        c cVar = null;
        for (int i3 = 0; i3 < size; i3++) {
            t<Model, Data> tVar = this.mm.get(i3);
            if (tVar.c(model) && (a2 = tVar.a(model, i, i2, gVar)) != null) {
                cVar = a2.lm;
                arrayList.add(a2.eq);
            }
        }
        if (arrayList.isEmpty() || cVar == null) {
            return null;
        }
        return new t.a<>(cVar, new a(arrayList, this.iq));
    }

    @Override // com.bumptech.glide.load.model.t
    public boolean c(@NonNull Model model) {
        for (t<Model, Data> tVar : this.mm) {
            if (tVar.c(model)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "MultiModelLoader{modelLoaders=" + Arrays.toString(this.mm.toArray()) + '}';
    }
}
