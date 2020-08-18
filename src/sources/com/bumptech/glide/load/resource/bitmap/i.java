package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import com.bumptech.glide.util.a;
import java.io.IOException;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferBitmapDecoder */
public class i implements h<ByteBuffer, Bitmap> {
    private final o wq;

    public i(o oVar) {
        this.wq = oVar;
    }

    /* renamed from: a */
    public A<Bitmap> b(@NonNull ByteBuffer byteBuffer, int i, int i2, @NonNull g gVar) throws IOException {
        return this.wq.a(a.f(byteBuffer), i, i2, gVar);
    }

    public boolean a(@NonNull ByteBuffer byteBuffer, @NonNull g gVar) {
        return this.wq.b(byteBuffer);
    }
}
