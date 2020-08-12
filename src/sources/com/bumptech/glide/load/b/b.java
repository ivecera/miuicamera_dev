package com.bumptech.glide.load.b;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.j;
import java.security.MessageDigest;

/* compiled from: UnitTransformation */
public final class b<T> implements j<T> {
    private static final j<?> rq = new b();

    private b() {
    }

    @NonNull
    public static <T> b<T> get() {
        return (b) rq;
    }

    @Override // com.bumptech.glide.load.j
    @NonNull
    public A<T> transform(@NonNull Context context, @NonNull A<T> a2, int i, int i2) {
        return a2;
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}
