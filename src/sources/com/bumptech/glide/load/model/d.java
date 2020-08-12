package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.c;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/* compiled from: ByteArrayLoader */
class d implements c.b<InputStream> {
    final /* synthetic */ c.d this$0;

    d(c.d dVar) {
        this.this$0 = dVar;
    }

    @Override // com.bumptech.glide.load.model.c.b
    public InputStream e(byte[] bArr) {
        return new ByteArrayInputStream(bArr);
    }

    @Override // com.bumptech.glide.load.model.c.b
    public Class<InputStream> ga() {
        return InputStream.class;
    }
}
