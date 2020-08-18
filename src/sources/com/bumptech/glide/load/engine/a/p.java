package com.bumptech.glide.load.engine.a;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.a.o;

/* compiled from: MemoryCacheAdapter */
public class p implements o {
    private o.a listener;

    @Override // com.bumptech.glide.load.engine.a.o
    @Nullable
    public A<?> a(@NonNull c cVar) {
        return null;
    }

    @Override // com.bumptech.glide.load.engine.a.o
    @Nullable
    public A<?> a(@NonNull c cVar, @Nullable A<?> a2) {
        if (a2 == null) {
            return null;
        }
        this.listener.b(a2);
        return null;
    }

    @Override // com.bumptech.glide.load.engine.a.o
    public void a(float f2) {
    }

    @Override // com.bumptech.glide.load.engine.a.o
    public void a(@NonNull o.a aVar) {
        this.listener = aVar;
    }

    @Override // com.bumptech.glide.load.engine.a.o
    public void aa() {
    }

    @Override // com.bumptech.glide.load.engine.a.o
    public long da() {
        return 0;
    }

    @Override // com.bumptech.glide.load.engine.a.o
    public long getMaxSize() {
        return 0;
    }

    @Override // com.bumptech.glide.load.engine.a.o
    public void trimMemory(int i) {
    }
}
