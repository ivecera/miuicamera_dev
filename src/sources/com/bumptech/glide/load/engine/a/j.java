package com.bumptech.glide.load.engine.a;

import android.content.Context;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.a.f;
import java.io.File;

/* compiled from: ExternalPreferredCacheDiskCacheFactory */
class j implements f.a {
    final /* synthetic */ String Eo;
    final /* synthetic */ Context val$context;

    j(Context context, String str) {
        this.val$context = context;
        this.Eo = str;
    }

    @Nullable
    private File yn() {
        File cacheDir = this.val$context.getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        String str = this.Eo;
        return str != null ? new File(cacheDir, str) : cacheDir;
    }

    @Override // com.bumptech.glide.load.engine.a.f.a
    public File Q() {
        File externalCacheDir;
        File yn = yn();
        if ((yn != null && yn.exists()) || (externalCacheDir = this.val$context.getExternalCacheDir()) == null || !externalCacheDir.canWrite()) {
            return yn;
        }
        String str = this.Eo;
        return str != null ? new File(externalCacheDir, str) : externalCacheDir;
    }
}
