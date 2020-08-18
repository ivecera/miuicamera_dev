package com.bumptech.glide.load.engine.a;

import android.content.Context;
import com.bumptech.glide.load.engine.a.f;
import java.io.File;

/* compiled from: InternalCacheDiskCacheFactory */
class l implements f.a {
    final /* synthetic */ String Eo;
    final /* synthetic */ Context val$context;

    l(Context context, String str) {
        this.val$context = context;
        this.Eo = str;
    }

    @Override // com.bumptech.glide.load.engine.a.f.a
    public File Q() {
        File cacheDir = this.val$context.getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        String str = this.Eo;
        return str != null ? new File(cacheDir, str) : cacheDir;
    }
}
