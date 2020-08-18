package com.bumptech.glide.load;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;

/* compiled from: Option */
public final class f<T> {
    private static final a<Object> Al = new e();
    private final T defaultValue;
    private final String key;
    private final a<T> yl;
    private volatile byte[] zl;

    /* compiled from: Option */
    public interface a<T> {
        void a(@NonNull byte[] bArr, @NonNull T t, @NonNull MessageDigest messageDigest);
    }

    private f(@NonNull String str, @Nullable T t, @NonNull a<T> aVar) {
        i.E(str);
        this.key = str;
        this.defaultValue = t;
        i.checkNotNull(aVar);
        this.yl = aVar;
    }

    @NonNull
    public static <T> f<T> A(@NonNull String str) {
        return new f<>(str, null, en());
    }

    @NonNull
    public static <T> f<T> a(@NonNull String str, @NonNull a<T> aVar) {
        return new f<>(str, null, aVar);
    }

    @NonNull
    public static <T> f<T> a(@NonNull String str, @NonNull T t) {
        return new f<>(str, t, en());
    }

    @NonNull
    public static <T> f<T> a(@NonNull String str, @Nullable T t, @NonNull a<T> aVar) {
        return new f<>(str, t, aVar);
    }

    @NonNull
    private static <T> a<T> en() {
        return Al;
    }

    @NonNull
    private byte[] fn() {
        if (this.zl == null) {
            this.zl = this.key.getBytes(c.CHARSET);
        }
        return this.zl;
    }

    public void a(@NonNull T t, @NonNull MessageDigest messageDigest) {
        this.yl.a(fn(), t, messageDigest);
    }

    public boolean equals(Object obj) {
        if (obj instanceof f) {
            return this.key.equals(((f) obj).key);
        }
        return false;
    }

    @Nullable
    public T getDefaultValue() {
        return this.defaultValue;
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public String toString() {
        return "Option{key='" + this.key + '\'' + '}';
    }
}
