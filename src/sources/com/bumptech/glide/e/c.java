package com.bumptech.glide.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: MediaStoreSignature */
public class c implements com.bumptech.glide.load.c {
    private final long hu;
    @NonNull
    private final String mimeType;
    private final int orientation;

    public c(@Nullable String str, long j, int i) {
        this.mimeType = str == null ? "" : str;
        this.hu = j;
        this.orientation = i;
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || c.class != obj.getClass()) {
            return false;
        }
        c cVar = (c) obj;
        return this.hu == cVar.hu && this.orientation == cVar.orientation && this.mimeType.equals(cVar.mimeType);
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        long j = this.hu;
        return (((this.mimeType.hashCode() * 31) + ((int) (j ^ (j >>> 32)))) * 31) + this.orientation;
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ByteBuffer.allocate(12).putLong(this.hu).putInt(this.orientation).array());
        messageDigest.update(this.mimeType.getBytes(com.bumptech.glide.load.c.CHARSET));
    }
}
