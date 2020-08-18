package com.bumptech.glide.request.a;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.a.j;

/* compiled from: ViewPropertyAnimationFactory */
public class i<R> implements g<R> {
    private j<R> animation;
    private final j.a animator;

    public i(j.a aVar) {
        this.animator = aVar;
    }

    @Override // com.bumptech.glide.request.a.g
    public f<R> a(DataSource dataSource, boolean z) {
        if (dataSource == DataSource.MEMORY_CACHE || !z) {
            return e.get();
        }
        if (this.animation == null) {
            this.animation = new j<>(this.animator);
        }
        return this.animation;
    }
}
