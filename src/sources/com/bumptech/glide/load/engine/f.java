package com.bumptech.glide.load.engine;

import android.support.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;

/* compiled from: DataFetcherGenerator */
interface f {

    /* compiled from: DataFetcherGenerator */
    public interface a {
        void a(c cVar, Exception exc, d<?> dVar, DataSource dataSource);

        void a(c cVar, @Nullable Object obj, d<?> dVar, DataSource dataSource, c cVar2);

        void ea();
    }

    boolean K();

    void cancel();
}
