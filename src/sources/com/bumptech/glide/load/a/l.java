package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: InputStreamRewinder */
public final class l implements e<InputStream> {
    private static final int Nl = 5242880;
    private final RecyclableBufferedInputStream Ml;

    /* compiled from: InputStreamRewinder */
    public static final class a implements e.a<InputStream> {
        private final b Ll;

        public a(b bVar) {
            this.Ll = bVar;
        }

        @NonNull
        /* renamed from: g */
        public e<InputStream> build(InputStream inputStream) {
            return new l(inputStream, this.Ll);
        }

        @Override // com.bumptech.glide.load.a.e.a
        @NonNull
        public Class<InputStream> ga() {
            return InputStream.class;
        }
    }

    l(InputStream inputStream, b bVar) {
        this.Ml = new RecyclableBufferedInputStream(inputStream, bVar);
        this.Ml.mark(Nl);
    }

    @Override // com.bumptech.glide.load.a.e
    @NonNull
    public InputStream W() throws IOException {
        this.Ml.reset();
        return this.Ml;
    }

    @Override // com.bumptech.glide.load.a.e
    public void cleanup() {
        this.Ml.release();
    }
}
