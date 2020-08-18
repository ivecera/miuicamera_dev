package com.bumptech.glide.e;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import java.security.MessageDigest;

/* compiled from: EmptySignature */
public final class b implements c {
    private static final b gu = new b();

    private b() {
    }

    @NonNull
    public static b obtain() {
        return gu;
    }

    public String toString() {
        return "EmptySignature";
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}
