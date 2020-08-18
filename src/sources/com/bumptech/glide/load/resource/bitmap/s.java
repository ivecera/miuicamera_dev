package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import java.security.MessageDigest;

/* compiled from: FitCenter */
public class s extends g {
    private static final String ID = "com.bumptech.glide.load.resource.bitmap.FitCenter";
    private static final byte[] uq = ID.getBytes(c.CHARSET);

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        return obj instanceof s;
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        return ID.hashCode();
    }

    /* access modifiers changed from: protected */
    @Override // com.bumptech.glide.load.resource.bitmap.g
    public Bitmap transform(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        return y.d(dVar, bitmap, i, i2);
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(uq);
    }
}
