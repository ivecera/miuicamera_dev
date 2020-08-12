package com.bumptech.glide.load.resource.bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.b.b.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.v;
import com.bumptech.glide.util.l;

/* compiled from: BitmapDrawableResource */
public class c extends b<BitmapDrawable> implements v {
    private final d Xi;

    public c(BitmapDrawable bitmapDrawable, d dVar) {
        super(bitmapDrawable);
        this.Xi = dVar;
    }

    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public Class<BitmapDrawable> T() {
        return BitmapDrawable.class;
    }

    @Override // com.bumptech.glide.load.engine.A
    public int getSize() {
        return l.j(((b) this).drawable.getBitmap());
    }

    @Override // com.bumptech.glide.load.engine.v, com.bumptech.glide.load.b.b.b
    public void initialize() {
        ((b) this).drawable.getBitmap().prepareToDraw();
    }

    @Override // com.bumptech.glide.load.engine.A
    public void recycle() {
        this.Xi.a(((b) this).drawable.getBitmap());
    }
}
