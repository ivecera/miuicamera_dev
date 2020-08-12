package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.load.a.g;

/* compiled from: DataRewinderRegistry */
class f implements e.a<Object> {
    f() {
    }

    @Override // com.bumptech.glide.load.a.e.a
    @NonNull
    public e<Object> build(@NonNull Object obj) {
        return new g.a(obj);
    }

    @Override // com.bumptech.glide.load.a.e.a
    @NonNull
    public Class<Object> ga() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
