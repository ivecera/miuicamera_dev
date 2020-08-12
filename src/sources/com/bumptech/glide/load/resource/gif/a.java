package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.b.a;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;

/* compiled from: GifBitmapProvider */
public final class a implements a.C0006a {
    @Nullable
    private final b Ma;
    private final d Xi;

    public a(d dVar) {
        this(dVar, null);
    }

    public a(d dVar, @Nullable b bVar) {
        this.Xi = dVar;
        this.Ma = bVar;
    }

    @Override // com.bumptech.glide.b.a.C0006a
    @NonNull
    public Bitmap a(int i, int i2, @NonNull Bitmap.Config config) {
        return this.Xi.c(i, i2, config);
    }

    @Override // com.bumptech.glide.b.a.C0006a
    public void a(@NonNull int[] iArr) {
        b bVar = this.Ma;
        if (bVar != null) {
            bVar.put(iArr);
        }
    }

    @Override // com.bumptech.glide.b.a.C0006a
    public void d(@NonNull Bitmap bitmap) {
        this.Xi.a(bitmap);
    }

    @Override // com.bumptech.glide.b.a.C0006a
    @NonNull
    public byte[] f(int i) {
        b bVar = this.Ma;
        return bVar == null ? new byte[i] : (byte[]) bVar.a(i, byte[].class);
    }

    @Override // com.bumptech.glide.b.a.C0006a
    public void g(@NonNull byte[] bArr) {
        b bVar = this.Ma;
        if (bVar != null) {
            bVar.put(bArr);
        }
    }

    @Override // com.bumptech.glide.b.a.C0006a
    @NonNull
    public int[] h(int i) {
        b bVar = this.Ma;
        return bVar == null ? new int[i] : (int[]) bVar.a(i, int[].class);
    }
}
