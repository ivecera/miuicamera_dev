package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.util.f;

/* compiled from: ModelCache */
class s extends f<ModelCache.ModelKey<A>, B> {
    final /* synthetic */ ModelCache this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    s(ModelCache modelCache, long j) {
        super(j);
        this.this$0 = modelCache;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void b(@NonNull ModelCache.ModelKey<A> modelKey, @Nullable B b2) {
        modelKey.release();
    }
}
