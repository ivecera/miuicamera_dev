package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import java.security.MessageDigest;

/* compiled from: DataCacheKey */
final class d implements c {
    private final c lm;
    private final c qm;

    d(c cVar, c cVar2) {
        this.lm = cVar;
        this.qm = cVar2;
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return this.lm.equals(dVar.lm) && this.qm.equals(dVar.qm);
    }

    /* access modifiers changed from: package-private */
    public c fj() {
        return this.lm;
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        return (this.lm.hashCode() * 31) + this.qm.hashCode();
    }

    public String toString() {
        return "DataCacheKey{sourceKey=" + this.lm + ", signature=" + this.qm + '}';
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.lm.updateDiskCacheKey(messageDigest);
        this.qm.updateDiskCacheKey(messageDigest);
    }
}
