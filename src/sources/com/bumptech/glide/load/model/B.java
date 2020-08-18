package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;

/* compiled from: UnitModelLoader */
public class B<Model> implements t<Model, Model> {
    private static final B<?> INSTANCE = new B<>();

    /* compiled from: UnitModelLoader */
    public static class a<Model> implements u<Model, Model> {
        private static final a<?> FACTORY = new a<>();

        /* JADX DEBUG: Type inference failed for r0v0. Raw type applied. Possible types: com.bumptech.glide.load.model.B$a<?>, com.bumptech.glide.load.model.B$a<T> */
        public static <T> a<T> getInstance() {
            return FACTORY;
        }

        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<Model, Model> a(x xVar) {
            return B.getInstance();
        }
    }

    /* compiled from: UnitModelLoader */
    private static class b<Model> implements d<Model> {
        private final Model resource;

        b(Model model) {
            this.resource = model;
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public DataSource L() {
            return DataSource.LOCAL;
        }

        @Override // com.bumptech.glide.load.a.d
        public void a(@NonNull Priority priority, @NonNull d.a<? super Model> aVar) {
            aVar.b(this.resource);
        }

        @Override // com.bumptech.glide.load.a.d
        public void cancel() {
        }

        @Override // com.bumptech.glide.load.a.d
        public void cleanup() {
        }

        /* JADX DEBUG: Type inference failed for r0v2. Raw type applied. Possible types: java.lang.Class<?>, java.lang.Class<Model> */
        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public Class<Model> ga() {
            return this.resource.getClass();
        }
    }

    /* JADX DEBUG: Type inference failed for r0v0. Raw type applied. Possible types: com.bumptech.glide.load.model.B<?>, com.bumptech.glide.load.model.B<T> */
    public static <T> B<T> getInstance() {
        return INSTANCE;
    }

    @Override // com.bumptech.glide.load.model.t
    public t.a<Model> a(@NonNull Model model, int i, int i2, @NonNull g gVar) {
        return new t.a<>(new com.bumptech.glide.e.d(model), new b(model));
    }

    @Override // com.bumptech.glide.load.model.t
    public boolean c(@NonNull Model model) {
        return true;
    }
}
