package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

/* compiled from: LruPoolStrategy */
interface k {
    void a(Bitmap bitmap);

    String b(int i, int i2, Bitmap.Config config);

    int c(Bitmap bitmap);

    @Nullable
    Bitmap d(int i, int i2, Bitmap.Config config);

    String e(Bitmap bitmap);

    @Nullable
    Bitmap removeLast();
}
