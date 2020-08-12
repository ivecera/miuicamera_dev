package com.bumptech.glide.load;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import com.bumptech.glide.util.CachedHashCodeArrayMap;
import java.security.MessageDigest;

/* compiled from: Options */
public final class g implements c {
    private final ArrayMap<f<?>, Object> values = new CachedHashCodeArrayMap();

    private static <T> void a(@NonNull f<T> fVar, @NonNull Object obj, @NonNull MessageDigest messageDigest) {
        fVar.a(obj, messageDigest);
    }

    @NonNull
    public <T> g a(@NonNull f<T> fVar, @NonNull T t) {
        this.values.put(fVar, t);
        return this;
    }

    @Nullable
    public <T> T a(@NonNull f<T> fVar) {
        return this.values.containsKey(fVar) ? this.values.get(fVar) : fVar.getDefaultValue();
    }

    public void b(@NonNull g gVar) {
        this.values.putAll((SimpleArrayMap<? extends f<?>, ? extends Object>) gVar.values);
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (obj instanceof g) {
            return this.values.equals(((g) obj).values);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        return this.values.hashCode();
    }

    public String toString() {
        return "Options{values=" + this.values + '}';
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        for (int i = 0; i < this.values.size(); i++) {
            a(this.values.keyAt(i), this.values.valueAt(i), messageDigest);
        }
    }
}
