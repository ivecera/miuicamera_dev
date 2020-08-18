package com.bumptech.glide.load.engine.a;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import com.bumptech.glide.load.c;
import com.bumptech.glide.util.a.d;
import com.bumptech.glide.util.a.g;
import com.bumptech.glide.util.f;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.security.MessageDigest;

/* compiled from: SafeKeyGenerator */
public class s {
    private final f<c, String> bp = new f<>(1000);
    private final Pools.Pool<a> cp = d.b(10, new r(this));

    /* access modifiers changed from: private */
    /* compiled from: SafeKeyGenerator */
    public static final class a implements d.c {
        private final g Hm = g.newInstance();
        final MessageDigest messageDigest;

        a(MessageDigest messageDigest2) {
            this.messageDigest = messageDigest2;
        }

        @Override // com.bumptech.glide.util.a.d.c
        @NonNull
        public g getVerifier() {
            return this.Hm;
        }
    }

    private String j(c cVar) {
        a acquire = this.cp.acquire();
        i.checkNotNull(acquire);
        a aVar = acquire;
        try {
            cVar.updateDiskCacheKey(aVar.messageDigest);
            return l.j(aVar.messageDigest.digest());
        } finally {
            this.cp.release(aVar);
        }
    }

    public String f(c cVar) {
        String str;
        synchronized (this.bp) {
            str = this.bp.get(cVar);
        }
        if (str == null) {
            str = j(cVar);
        }
        synchronized (this.bp) {
            this.bp.put(cVar, str);
        }
        return str;
    }
}
