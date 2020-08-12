package com.bumptech.glide.load.b;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.util.i;

/* compiled from: SimpleResource */
public class a<T> implements A<T> {
    protected final T data;

    public a(@NonNull T t) {
        i.checkNotNull(t);
        this.data = t;
    }

    /* JADX DEBUG: Type inference failed for r0v2. Raw type applied. Possible types: java.lang.Class<?>, java.lang.Class<T> */
    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public Class<T> T() {
        return this.data.getClass();
    }

    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public final T get() {
        return this.data;
    }

    @Override // com.bumptech.glide.load.engine.A
    public final int getSize() {
        return 1;
    }

    @Override // com.bumptech.glide.load.engine.A
    public void recycle() {
    }
}
