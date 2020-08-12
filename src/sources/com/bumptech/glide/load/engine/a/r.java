package com.bumptech.glide.load.engine.a;

import com.bumptech.glide.load.engine.a.s;
import com.bumptech.glide.util.a.d;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: SafeKeyGenerator */
class r implements d.a<s.a> {
    final /* synthetic */ s this$0;

    r(s sVar) {
        this.this$0 = sVar;
    }

    @Override // com.bumptech.glide.util.a.d.a
    public s.a create() {
        try {
            return new s.a(MessageDigest.getInstance("SHA-256"));
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        }
    }
}
