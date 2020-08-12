package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;

/* compiled from: Resource */
public interface A<Z> {
    @NonNull
    Class<Z> T();

    @NonNull
    Z get();

    int getSize();

    void recycle();
}
