package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.model.t;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: DataUrlLoader */
public final class g<Model, Data> implements t<Model, Data> {
    private static final String Np = "data:image";
    private static final String Op = ";base64";
    private final a<Data> Mp;

    /* compiled from: DataUrlLoader */
    public interface a<Data> {
        Data decode(String str) throws IllegalArgumentException;

        void e(Data data) throws IOException;

        Class<Data> ga();
    }

    /* compiled from: DataUrlLoader */
    private static final class b<Data> implements d<Data> {
        private final String Lp;
        private Data data;
        private final a<Data> reader;

        b(String str, a<Data> aVar) {
            this.Lp = str;
            this.reader = aVar;
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public DataSource L() {
            return DataSource.LOCAL;
        }

        @Override // com.bumptech.glide.load.a.d
        public void a(@NonNull Priority priority, @NonNull d.a<? super Data> aVar) {
            try {
                this.data = this.reader.decode(this.Lp);
                aVar.b(this.data);
            } catch (IllegalArgumentException e2) {
                aVar.a(e2);
            }
        }

        @Override // com.bumptech.glide.load.a.d
        public void cancel() {
        }

        @Override // com.bumptech.glide.load.a.d
        public void cleanup() {
            try {
                this.reader.e(this.data);
            } catch (IOException unused) {
            }
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public Class<Data> ga() {
            return this.reader.ga();
        }
    }

    /* compiled from: DataUrlLoader */
    public static final class c<Model> implements u<Model, InputStream> {
        private final a<InputStream> _l = new h(this);

        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<Model, InputStream> a(@NonNull x xVar) {
            return new g(this._l);
        }
    }

    public g(a<Data> aVar) {
        this.Mp = aVar;
    }

    @Override // com.bumptech.glide.load.model.t
    public t.a<Data> a(@NonNull Model model, int i, int i2, @NonNull com.bumptech.glide.load.g gVar) {
        return new t.a<>(new com.bumptech.glide.e.d(model), new b(model.toString(), this.Mp));
    }

    @Override // com.bumptech.glide.load.model.t
    public boolean c(@NonNull Model model) {
        return model.toString().startsWith(Np);
    }
}
