package com.bumptech.glide.request.a;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.a.f;

/* compiled from: NoTransition */
public class e<R> implements f<R> {
    static final e<?> bu = new e<>();
    private static final g<?> cu = new a();

    /* compiled from: NoTransition */
    public static class a<R> implements g<R> {
        @Override // com.bumptech.glide.request.a.g
        public f<R> a(DataSource dataSource, boolean z) {
            return e.bu;
        }
    }

    public static <R> f<R> get() {
        return bu;
    }

    /* JADX DEBUG: Type inference failed for r0v0. Raw type applied. Possible types: com.bumptech.glide.request.a.g<?>, com.bumptech.glide.request.a.g<R> */
    public static <R> g<R> getFactory() {
        return cu;
    }

    @Override // com.bumptech.glide.request.a.f
    public boolean a(Object obj, f.a aVar) {
        return false;
    }
}
