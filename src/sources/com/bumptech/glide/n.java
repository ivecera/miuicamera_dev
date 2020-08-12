package com.bumptech.glide;

import android.support.annotation.NonNull;
import com.bumptech.glide.n;
import com.bumptech.glide.request.a.e;
import com.bumptech.glide.request.a.g;
import com.bumptech.glide.request.a.h;
import com.bumptech.glide.request.a.j;
import com.bumptech.glide.util.i;

/* compiled from: TransitionOptions */
public abstract class n<CHILD extends n<CHILD, TranscodeType>, TranscodeType> implements Cloneable {
    private g<? super TranscodeType> hk = e.getFactory();

    private CHILD self() {
        return this;
    }

    @NonNull
    public final CHILD Xi() {
        return a(e.getFactory());
    }

    /* access modifiers changed from: package-private */
    public final g<? super TranscodeType> Yi() {
        return this.hk;
    }

    @NonNull
    public final CHILD a(@NonNull g<? super TranscodeType> gVar) {
        i.checkNotNull(gVar);
        this.hk = gVar;
        self();
        return this;
    }

    @NonNull
    public final CHILD a(@NonNull j.a aVar) {
        return a(new com.bumptech.glide.request.a.i(aVar));
    }

    @Override // java.lang.Object
    public final CHILD clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    @NonNull
    public final CHILD s(int i) {
        return a(new h(i));
    }
}
