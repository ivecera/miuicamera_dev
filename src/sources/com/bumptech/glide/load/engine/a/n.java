package com.bumptech.glide.load.engine.a;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.a.o;
import com.bumptech.glide.util.f;

/* compiled from: LruResourceCache */
public class n extends f<c, A<?>> implements o {
    private o.a listener;

    public n(long j) {
        super(j);
    }

    @Override // com.bumptech.glide.load.engine.a.o
    @Nullable
    public /* bridge */ /* synthetic */ A a(@NonNull c cVar) {
        return (A) super.remove(cVar);
    }

    @Override // com.bumptech.glide.load.engine.a.o
    @Nullable
    public /* bridge */ /* synthetic */ A a(@NonNull c cVar, @Nullable A a2) {
        return (A) super.put(cVar, a2);
    }

    @Override // com.bumptech.glide.load.engine.a.o
    public void a(@NonNull o.a aVar) {
        this.listener = aVar;
    }

    /* access modifiers changed from: protected */
    public void b(@NonNull c cVar, @Nullable A<?> a2) {
        o.a aVar = this.listener;
        if (aVar != null && a2 != null) {
            aVar.b(a2);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public int r(@Nullable A<?> a2) {
        return a2 == null ? super.r(null) : a2.getSize();
    }

    @Override // com.bumptech.glide.load.engine.a.o
    @SuppressLint({"InlinedApi"})
    public void trimMemory(int i) {
        if (i >= 40) {
            aa();
        } else if (i >= 20 || i == 15) {
            f(getMaxSize() / 2);
        }
    }
}
