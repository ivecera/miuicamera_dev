package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import java.io.IOException;

/* compiled from: DataRewinder */
public interface e<T> {

    /* compiled from: DataRewinder */
    public interface a<T> {
        @NonNull
        e<T> build(@NonNull T t);

        @NonNull
        Class<T> ga();
    }

    @NonNull
    T W() throws IOException;

    void cleanup();
}
