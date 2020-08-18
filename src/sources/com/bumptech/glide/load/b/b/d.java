package com.bumptech.glide.load.b.b;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.A;

/* compiled from: NonOwnedDrawableResource */
final class d extends b<Drawable> {
    private d(Drawable drawable) {
        super(drawable);
    }

    @Nullable
    static A<Drawable> e(@Nullable Drawable drawable) {
        if (drawable != null) {
            return new d(drawable);
        }
        return null;
    }

    /* JADX DEBUG: Type inference failed for r0v2. Raw type applied. Possible types: java.lang.Class<?>, java.lang.Class<android.graphics.drawable.Drawable> */
    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public Class<Drawable> T() {
        return ((b) this).drawable.getClass();
    }

    @Override // com.bumptech.glide.load.engine.A
    public int getSize() {
        return Math.max(1, ((b) this).drawable.getIntrinsicWidth() * ((b) this).drawable.getIntrinsicHeight() * 4);
    }

    @Override // com.bumptech.glide.load.engine.A
    public void recycle() {
    }
}
