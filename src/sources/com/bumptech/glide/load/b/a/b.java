package com.bumptech.glide.load.b.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.util.i;

/* compiled from: BytesResource */
public class b implements A<byte[]> {
    private final byte[] bytes;

    public b(byte[] bArr) {
        i.checkNotNull(bArr);
        this.bytes = bArr;
    }

    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public Class<byte[]> T() {
        return byte[].class;
    }

    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public byte[] get() {
        return this.bytes;
    }

    @Override // com.bumptech.glide.load.engine.A
    public int getSize() {
        return this.bytes.length;
    }

    @Override // com.bumptech.glide.load.engine.A
    public void recycle() {
    }
}
