package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.f;

/* compiled from: FixedPreloadSizeProvider */
public class d<T> implements f.b<T> {
    private final int[] size;

    public d(int i, int i2) {
        this.size = new int[]{i, i2};
    }

    @Override // com.bumptech.glide.f.b
    @Nullable
    public int[] a(@NonNull T t, int i, int i2) {
        return this.size;
    }
}
