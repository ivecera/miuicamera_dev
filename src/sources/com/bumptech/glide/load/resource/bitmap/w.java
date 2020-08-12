package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: RoundedCorners */
public final class w extends g {
    private static final String ID = "com.bumptech.glide.load.resource.bitmap.RoundedCorners";
    private static final byte[] uq = ID.getBytes(c.CHARSET);
    private final int vq;

    public w(int i) {
        i.b(i > 0, "roundingRadius must be greater than 0.");
        this.vq = i;
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        return (obj instanceof w) && this.vq == ((w) obj).vq;
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        return l.n(ID.hashCode(), l.hashCode(this.vq));
    }

    /* access modifiers changed from: protected */
    @Override // com.bumptech.glide.load.resource.bitmap.g
    public Bitmap transform(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        return y.b(dVar, bitmap, this.vq);
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(uq);
        messageDigest.update(ByteBuffer.allocate(4).putInt(this.vq).array());
    }
}
