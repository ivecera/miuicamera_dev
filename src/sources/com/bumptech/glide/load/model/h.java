package com.bumptech.glide.load.model;

import android.util.Base64;
import com.bumptech.glide.load.model.g;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: DataUrlLoader */
class h implements g.a<InputStream> {
    final /* synthetic */ g.c this$0;

    h(g.c cVar) {
        this.this$0 = cVar;
    }

    @Override // com.bumptech.glide.load.model.g.a
    public InputStream decode(String str) {
        if (str.startsWith("data:image")) {
            int indexOf = str.indexOf(44);
            if (indexOf == -1) {
                throw new IllegalArgumentException("Missing comma in data URL.");
            } else if (str.substring(0, indexOf).endsWith(";base64")) {
                return new ByteArrayInputStream(Base64.decode(str.substring(indexOf + 1), 0));
            } else {
                throw new IllegalArgumentException("Not a base64 image data URL.");
            }
        } else {
            throw new IllegalArgumentException("Not a valid image data URL.");
        }
    }

    /* renamed from: f */
    public void e(InputStream inputStream) throws IOException {
        inputStream.close();
    }

    @Override // com.bumptech.glide.load.model.g.a
    public Class<InputStream> ga() {
        return InputStream.class;
    }
}
