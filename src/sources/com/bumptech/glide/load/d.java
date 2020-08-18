package com.bumptech.glide.load;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: MultiTransformation */
public class d<T> implements j<T> {
    private final Collection<? extends j<T>> xl;

    public d(@NonNull Collection<? extends j<T>> collection) {
        if (!collection.isEmpty()) {
            this.xl = collection;
            return;
        }
        throw new IllegalArgumentException("MultiTransformation must contain at least one Transformation");
    }

    @SafeVarargs
    public d(@NonNull j<T>... jVarArr) {
        if (jVarArr.length != 0) {
            this.xl = Arrays.asList(jVarArr);
            return;
        }
        throw new IllegalArgumentException("MultiTransformation must contain at least one Transformation");
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (obj instanceof d) {
            return this.xl.equals(((d) obj).xl);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        return this.xl.hashCode();
    }

    @Override // com.bumptech.glide.load.j
    @NonNull
    public A<T> transform(@NonNull Context context, @NonNull A<T> a2, int i, int i2) {
        Iterator<? extends j<T>> it = this.xl.iterator();
        A<T> a3 = a2;
        while (it.hasNext()) {
            A<T> transform = ((j) it.next()).transform(context, a3, i, i2);
            if (a3 != null && !a3.equals(a2) && !a3.equals(transform)) {
                a3.recycle();
            }
            a3 = transform;
        }
        return a3;
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        Iterator<? extends j<T>> it = this.xl.iterator();
        while (it.hasNext()) {
            ((j) it.next()).updateDiskCacheKey(messageDigest);
        }
    }
}
