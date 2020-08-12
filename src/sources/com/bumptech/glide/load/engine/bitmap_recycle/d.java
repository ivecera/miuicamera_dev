package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/* compiled from: BitmapPool */
public interface d {
    void a(float f2);

    void a(Bitmap bitmap);

    void aa();

    @NonNull
    Bitmap c(int i, int i2, Bitmap.Config config);

    @NonNull
    Bitmap d(int i, int i2, Bitmap.Config config);

    long getMaxSize();

    void trimMemory(int i);
}
