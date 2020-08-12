package com.bumptech.glide.util.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.util.a.d;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FactoryPools */
class b implements d.a<List<T>> {
    b() {
    }

    @Override // com.bumptech.glide.util.a.d.a
    @NonNull
    public List<T> create() {
        return new ArrayList();
    }
}
