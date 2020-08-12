package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import com.bumptech.glide.request.c;

/* compiled from: BaseTarget */
public abstract class b<Z> implements o<Z> {
    private c request;

    @Override // com.bumptech.glide.request.target.o
    public void b(@Nullable Drawable drawable) {
    }

    @Override // com.bumptech.glide.request.target.o
    public void c(@Nullable Drawable drawable) {
    }

    @Override // com.bumptech.glide.request.target.o
    public void d(@Nullable Drawable drawable) {
    }

    @Override // com.bumptech.glide.request.target.o
    public void f(@Nullable c cVar) {
        this.request = cVar;
    }

    @Override // com.bumptech.glide.request.target.o
    @Nullable
    public c getRequest() {
        return this.request;
    }

    @Override // com.bumptech.glide.manager.j
    public void onDestroy() {
    }

    @Override // com.bumptech.glide.manager.j
    public void onStart() {
    }

    @Override // com.bumptech.glide.manager.j
    public void onStop() {
    }
}
