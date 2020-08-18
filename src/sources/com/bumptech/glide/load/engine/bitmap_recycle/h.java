package com.bumptech.glide.load.engine.bitmap_recycle;

/* compiled from: IntegerArrayAdapter */
public final class h implements a<int[]> {
    private static final String TAG = "IntegerArrayPool";

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public int Y() {
        return 4;
    }

    /* renamed from: c */
    public int a(int[] iArr) {
        return iArr.length;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public String getTag() {
        return TAG;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public int[] newArray(int i) {
        return new int[i];
    }
}
