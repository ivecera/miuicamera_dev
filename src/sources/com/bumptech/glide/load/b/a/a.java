package com.bumptech.glide.load.b.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.e;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferRewinder */
public class a implements e<ByteBuffer> {
    private final ByteBuffer buffer;

    /* renamed from: com.bumptech.glide.load.b.a.a$a  reason: collision with other inner class name */
    /* compiled from: ByteBufferRewinder */
    public static class C0008a implements e.a<ByteBuffer> {
        @NonNull
        /* renamed from: c */
        public e<ByteBuffer> build(ByteBuffer byteBuffer) {
            return new a(byteBuffer);
        }

        @Override // com.bumptech.glide.load.a.e.a
        @NonNull
        public Class<ByteBuffer> ga() {
            return ByteBuffer.class;
        }
    }

    public a(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
    }

    @Override // com.bumptech.glide.load.a.e
    @NonNull
    public ByteBuffer W() {
        this.buffer.position(0);
        return this.buffer;
    }

    @Override // com.bumptech.glide.load.a.e
    public void cleanup() {
    }
}
