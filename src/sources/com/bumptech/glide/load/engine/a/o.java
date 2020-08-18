package com.bumptech.glide.load.engine.a;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.A;

/* compiled from: MemoryCache */
public interface o {

    /* compiled from: MemoryCache */
    public interface a {
        void b(@NonNull A<?> a2);
    }

    @Nullable
    A<?> a(@NonNull c cVar);

    @Nullable
    A<?> a(@NonNull c cVar, @Nullable A<?> a2);

    void a(float f2);

    void a(@NonNull a aVar);

    void aa();

    long da();

    long getMaxSize();

    void trimMemory(int i);
}
