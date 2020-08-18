package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.b.a;
import com.bumptech.glide.b.c;
import com.bumptech.glide.b.d;
import com.bumptech.glide.b.e;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import com.bumptech.glide.util.l;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;

public class ByteBufferGifDecoder implements h<ByteBuffer, b> {
    private static final GifDecoderFactory Or = new GifDecoderFactory();
    private static final GifHeaderParserPool Pr = new GifHeaderParserPool();
    private static final String TAG = "BufferGifDecoder";
    private final GifHeaderParserPool Mr;
    private final GifDecoderFactory Nr;
    private final List<ImageHeaderParser> cm;
    private final Context context;
    private final a provider;

    @VisibleForTesting
    static class GifDecoderFactory {
        GifDecoderFactory() {
        }

        /* access modifiers changed from: package-private */
        public a a(a.C0006a aVar, c cVar, ByteBuffer byteBuffer, int i) {
            return new e(aVar, cVar, byteBuffer, i);
        }
    }

    @VisibleForTesting
    static class GifHeaderParserPool {
        private final Queue<d> pool = l.createQueue(0);

        GifHeaderParserPool() {
        }

        /* access modifiers changed from: package-private */
        public synchronized void a(d dVar) {
            dVar.clear();
            this.pool.offer(dVar);
        }

        /* access modifiers changed from: package-private */
        public synchronized d d(ByteBuffer byteBuffer) {
            d poll;
            poll = this.pool.poll();
            if (poll == null) {
                poll = new d();
            }
            return poll.setData(byteBuffer);
        }
    }

    public ByteBufferGifDecoder(Context context2) {
        this(context2, com.bumptech.glide.c.get(context2).wa().Ji(), com.bumptech.glide.c.get(context2).Fi(), com.bumptech.glide.c.get(context2).sa());
    }

    public ByteBufferGifDecoder(Context context2, List<ImageHeaderParser> list, com.bumptech.glide.load.engine.bitmap_recycle.d dVar, b bVar) {
        this(context2, list, dVar, bVar, Pr, Or);
    }

    @VisibleForTesting
    ByteBufferGifDecoder(Context context2, List<ImageHeaderParser> list, com.bumptech.glide.load.engine.bitmap_recycle.d dVar, b bVar, GifHeaderParserPool gifHeaderParserPool, GifDecoderFactory gifDecoderFactory) {
        this.context = context2.getApplicationContext();
        this.cm = list;
        this.Nr = gifDecoderFactory;
        this.provider = new a(dVar, bVar);
        this.Mr = gifHeaderParserPool;
    }

    private static int a(c cVar, int i, int i2) {
        int min = Math.min(cVar.getHeight() / i2, cVar.getWidth() / i);
        int max = Math.max(1, min == 0 ? 0 : Integer.highestOneBit(min));
        if (Log.isLoggable(TAG, 2) && max > 1) {
            Log.v(TAG, "Downsampling GIF, sampleSize: " + max + ", target dimens: [" + i + "x" + i2 + "], actual dimens: [" + cVar.getWidth() + "x" + cVar.getHeight() + "]");
        }
        return max;
    }

    @Nullable
    private d a(ByteBuffer byteBuffer, int i, int i2, d dVar, g gVar) {
        long Jk = com.bumptech.glide.util.e.Jk();
        try {
            c dj = dVar.dj();
            if (dj.getNumFrames() > 0) {
                if (dj.getStatus() == 0) {
                    Bitmap.Config config = gVar.a(g.Yq) == DecodeFormat.Ix ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;
                    a a2 = this.Nr.a(this.provider, dj, byteBuffer, a(dj, i, i2));
                    a2.a(config);
                    a2.advance();
                    Bitmap nextFrame = a2.getNextFrame();
                    if (nextFrame == null) {
                        if (Log.isLoggable(TAG, 2)) {
                            Log.v(TAG, "Decoded GIF from stream in " + com.bumptech.glide.util.e.e(Jk));
                        }
                        return null;
                    }
                    d dVar2 = new d(new b(this.context, a2, com.bumptech.glide.load.b.b.get(), i, i2, nextFrame));
                    if (Log.isLoggable(TAG, 2)) {
                        Log.v(TAG, "Decoded GIF from stream in " + com.bumptech.glide.util.e.e(Jk));
                    }
                    return dVar2;
                }
            }
            return null;
        } finally {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Decoded GIF from stream in " + com.bumptech.glide.util.e.e(Jk));
            }
        }
    }

    /* renamed from: a */
    public d b(@NonNull ByteBuffer byteBuffer, int i, int i2, @NonNull g gVar) {
        d d2 = this.Mr.d(byteBuffer);
        try {
            return a(byteBuffer, i, i2, d2, gVar);
        } finally {
            this.Mr.a(d2);
        }
    }

    public boolean a(@NonNull ByteBuffer byteBuffer, @NonNull g gVar) throws IOException {
        return !((Boolean) gVar.a(g.Zr)).booleanValue() && com.bumptech.glide.load.b.a(this.cm, byteBuffer) == ImageHeaderParser.ImageType.GIF;
    }
}
