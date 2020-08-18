package com.bumptech.glide.load.resource.gif;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.b.b.b;
import com.bumptech.glide.load.engine.v;

/* compiled from: GifDrawableResource */
public class d extends b<b> implements v {
    public d(b bVar) {
        super(bVar);
    }

    @Override // com.bumptech.glide.load.engine.A
    @NonNull
    public Class<b> T() {
        return b.class;
    }

    @Override // com.bumptech.glide.load.engine.A
    public int getSize() {
        return ((b) this).drawable.getSize();
    }

    @Override // com.bumptech.glide.load.engine.v, com.bumptech.glide.load.b.b.b
    public void initialize() {
        ((b) this).drawable.ya().prepareToDraw();
    }

    @Override // com.bumptech.glide.load.engine.A
    public void recycle() {
        ((b) this).drawable.stop();
        ((b) this).drawable.recycle();
    }
}
