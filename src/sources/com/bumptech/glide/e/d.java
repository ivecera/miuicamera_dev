package com.bumptech.glide.e;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;

/* compiled from: ObjectKey */
public final class d implements c {
    private final Object object;

    public d(@NonNull Object obj) {
        i.checkNotNull(obj);
        this.object = obj;
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (obj instanceof d) {
            return this.object.equals(((d) obj).object);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        return this.object.hashCode();
    }

    public String toString() {
        return "ObjectKey{object=" + this.object + '}';
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(this.object.toString().getBytes(c.CHARSET));
    }
}
