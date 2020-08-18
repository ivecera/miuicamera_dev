package com.bumptech.glide.load.engine.a;

import com.bumptech.glide.load.engine.a.f;
import java.io.File;

/* compiled from: DiskLruCacheFactory */
class e implements f.a {
    final /* synthetic */ String Do;
    final /* synthetic */ String Eo;

    e(String str, String str2) {
        this.Do = str;
        this.Eo = str2;
    }

    @Override // com.bumptech.glide.load.engine.a.f.a
    public File Q() {
        return new File(this.Do, this.Eo);
    }
}
