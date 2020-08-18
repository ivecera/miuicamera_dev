package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;

/* compiled from: DataFetcher */
public interface d<T> {

    /* compiled from: DataFetcher */
    public interface a<T> {
        void a(@NonNull Exception exc);

        void b(@Nullable T t);
    }

    @NonNull
    DataSource L();

    void a(@NonNull Priority priority, @NonNull a<? super T> aVar);

    void cancel();

    void cleanup();

    @NonNull
    Class<T> ga();
}
