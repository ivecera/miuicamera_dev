package com.bumptech.glide.load.resource.bitmap;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.f;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: VideoDecoder */
class B implements f.a<Long> {
    private final ByteBuffer buffer = ByteBuffer.allocate(8);

    B() {
    }

    public void a(@NonNull byte[] bArr, @NonNull Long l, @NonNull MessageDigest messageDigest) {
        messageDigest.update(bArr);
        synchronized (this.buffer) {
            this.buffer.position(0);
            messageDigest.update(this.buffer.putLong(l.longValue()).array());
        }
    }
}
