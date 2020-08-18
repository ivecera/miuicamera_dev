package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.resource.bitmap.o;
import com.bumptech.glide.util.c;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: StreamBitmapDecoder */
public class x implements h<InputStream, Bitmap> {
    private final b Ll;
    private final o wq;

    /* compiled from: StreamBitmapDecoder */
    static class a implements o.a {
        private final RecyclableBufferedInputStream Ml;
        private final c ur;

        a(RecyclableBufferedInputStream recyclableBufferedInputStream, c cVar) {
            this.Ml = recyclableBufferedInputStream;
            this.ur = cVar;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.o.a
        public void N() {
            this.Ml.tm();
        }

        @Override // com.bumptech.glide.load.resource.bitmap.o.a
        public void a(d dVar, Bitmap bitmap) throws IOException {
            IOException exception = this.ur.getException();
            if (exception != null) {
                if (bitmap != null) {
                    dVar.a(bitmap);
                }
                throw exception;
            }
        }
    }

    public x(o oVar, b bVar) {
        this.wq = oVar;
        this.Ll = bVar;
    }

    /* renamed from: a */
    public A<Bitmap> b(@NonNull InputStream inputStream, int i, int i2, @NonNull g gVar) throws IOException {
        RecyclableBufferedInputStream recyclableBufferedInputStream;
        boolean z;
        if (inputStream instanceof RecyclableBufferedInputStream) {
            recyclableBufferedInputStream = (RecyclableBufferedInputStream) inputStream;
            z = false;
        } else {
            z = true;
            recyclableBufferedInputStream = new RecyclableBufferedInputStream(inputStream, this.Ll);
        }
        c j = c.j(recyclableBufferedInputStream);
        try {
            return this.wq.a(new com.bumptech.glide.util.g(j), i, i2, gVar, new a(recyclableBufferedInputStream, j));
        } finally {
            j.release();
            if (z) {
                recyclableBufferedInputStream.release();
            }
        }
    }

    public boolean a(@NonNull InputStream inputStream, @NonNull g gVar) {
        return this.wq.h(inputStream);
    }
}
