package com.bumptech.glide.load.b.d;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.resource.gif.b;
import com.bumptech.glide.util.a;

/* compiled from: GifDrawableBytesTranscoder */
public class d implements e<b, byte[]> {
    @Override // com.bumptech.glide.load.b.d.e
    @Nullable
    public A<byte[]> a(@NonNull A<b> a2, @NonNull g gVar) {
        return new com.bumptech.glide.load.b.a.b(a.e(a2.get().getBuffer()));
    }
}
