package com.bumptech.glide.load.engine.bitmap_recycle;

/* compiled from: ByteArrayAdapter */
public final class f implements a<byte[]> {
    private static final String TAG = "ByteArrayPool";

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public int Y() {
        return 1;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public String getTag() {
        return TAG;
    }

    /* renamed from: h */
    public int a(byte[] bArr) {
        return bArr.length;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public byte[] newArray(int i) {
        return new byte[i];
    }
}
