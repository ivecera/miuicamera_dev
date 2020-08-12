package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.c;
import java.nio.ByteBuffer;

/* compiled from: ByteArrayLoader */
class b implements c.b<ByteBuffer> {
    final /* synthetic */ c.a this$0;

    b(c.a aVar) {
        this.this$0 = aVar;
    }

    @Override // com.bumptech.glide.load.model.c.b
    public ByteBuffer e(byte[] bArr) {
        return ByteBuffer.wrap(bArr);
    }

    @Override // com.bumptech.glide.load.model.c.b
    public Class<ByteBuffer> ga() {
        return ByteBuffer.class;
    }
}
