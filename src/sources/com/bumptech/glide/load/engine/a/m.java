package com.bumptech.glide.load.engine.a;

import android.content.Context;
import com.bumptech.glide.load.engine.a.a;

/* compiled from: InternalCacheDiskCacheFactory */
public final class m extends f {
    public m(Context context) {
        this(context, a.C0009a.fj, 262144000);
    }

    public m(Context context, long j) {
        this(context, a.C0009a.fj, j);
    }

    public m(Context context, String str, long j) {
        super(new l(context, str), j);
    }
}
