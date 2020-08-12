package com.bumptech.glide.load.engine.a;

import com.bumptech.glide.load.engine.a.f;
import java.io.File;

/* compiled from: DiskLruCacheFactory */
class d implements f.a {
    final /* synthetic */ String Do;

    d(String str) {
        this.Do = str;
    }

    @Override // com.bumptech.glide.load.engine.a.f.a
    public File Q() {
        return new File(this.Do);
    }
}
