package com.bumptech.glide.a;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/* compiled from: StrictLineReader */
class c extends ByteArrayOutputStream {
    final /* synthetic */ d this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    c(d dVar, int i) {
        super(i);
        this.this$0 = dVar;
    }

    public String toString() {
        int i = ((ByteArrayOutputStream) this).count;
        try {
            return new String(((ByteArrayOutputStream) this).buf, 0, (i <= 0 || ((ByteArrayOutputStream) this).buf[i + -1] != 13) ? ((ByteArrayOutputStream) this).count : i - 1, this.this$0.charset.name());
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }
}
