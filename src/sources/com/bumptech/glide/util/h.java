package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* compiled from: MultiClassKey */
public class h {
    private Class<?> first;
    private Class<?> ku;
    private Class<?> second;

    public h() {
    }

    public h(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
        h(cls, cls2);
    }

    public h(@NonNull Class<?> cls, @NonNull Class<?> cls2, @Nullable Class<?> cls3) {
        d(cls, cls2, cls3);
    }

    public void d(@NonNull Class<?> cls, @NonNull Class<?> cls2, @Nullable Class<?> cls3) {
        this.first = cls;
        this.second = cls2;
        this.ku = cls3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || h.class != obj.getClass()) {
            return false;
        }
        h hVar = (h) obj;
        return this.first.equals(hVar.first) && this.second.equals(hVar.second) && l.d(this.ku, hVar.ku);
    }

    public void h(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
        d(cls, cls2, null);
    }

    public int hashCode() {
        int hashCode = ((this.first.hashCode() * 31) + this.second.hashCode()) * 31;
        Class<?> cls = this.ku;
        return hashCode + (cls != null ? cls.hashCode() : 0);
    }

    public String toString() {
        return "MultiClassKey{first=" + this.first + ", second=" + this.second + '}';
    }
}
