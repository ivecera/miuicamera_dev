package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.i;
import java.io.File;

/* compiled from: BitmapDrawableEncoder */
public class b implements i<BitmapDrawable> {
    private final d Xi;
    private final i<Bitmap> encoder;

    public b(d dVar, i<Bitmap> iVar) {
        this.Xi = dVar;
        this.encoder = iVar;
    }

    @Override // com.bumptech.glide.load.i
    @NonNull
    public EncodeStrategy a(@NonNull g gVar) {
        return this.encoder.a(gVar);
    }

    public boolean a(@NonNull A<BitmapDrawable> a2, @NonNull File file, @NonNull g gVar) {
        return this.encoder.a(new f(a2.get().getBitmap(), this.Xi), file, gVar);
    }
}
