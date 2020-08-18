package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/* compiled from: BitmapPoolAdapter */
public class e implements d {
    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    public void a(float f2) {
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    public void a(Bitmap bitmap) {
        bitmap.recycle();
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    public void aa() {
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    @NonNull
    public Bitmap c(int i, int i2, Bitmap.Config config) {
        return d(i, i2, config);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    @NonNull
    public Bitmap d(int i, int i2, Bitmap.Config config) {
        return Bitmap.createBitmap(i, i2, config);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    public long getMaxSize() {
        return 0;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    public void trimMemory(int i) {
    }
}
