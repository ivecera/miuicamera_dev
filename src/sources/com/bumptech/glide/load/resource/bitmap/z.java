package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import com.bumptech.glide.util.l;

/* compiled from: UnitBitmapDecoder */
public final class z implements h<Bitmap, Bitmap> {

    /* compiled from: UnitBitmapDecoder */
    private static final class a implements A<Bitmap> {
        private final Bitmap bitmap;

        a(@NonNull Bitmap bitmap2) {
            this.bitmap = bitmap2;
        }

        @Override // com.bumptech.glide.load.engine.A
        @NonNull
        public Class<Bitmap> T() {
            return Bitmap.class;
        }

        @Override // com.bumptech.glide.load.engine.A
        @NonNull
        public Bitmap get() {
            return this.bitmap;
        }

        @Override // com.bumptech.glide.load.engine.A
        public int getSize() {
            return l.j(this.bitmap);
        }

        @Override // com.bumptech.glide.load.engine.A
        public void recycle() {
        }
    }

    /* renamed from: a */
    public A<Bitmap> b(@NonNull Bitmap bitmap, int i, int i2, @NonNull g gVar) {
        return new a(bitmap);
    }

    public boolean a(@NonNull Bitmap bitmap, @NonNull g gVar) {
        return true;
    }
}
