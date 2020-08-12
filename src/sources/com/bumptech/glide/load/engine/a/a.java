package com.bumptech.glide.load.engine.a;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.c;
import java.io.File;

/* compiled from: DiskCache */
public interface a {

    /* renamed from: com.bumptech.glide.load.engine.a.a$a  reason: collision with other inner class name */
    /* compiled from: DiskCache */
    public interface C0009a {
        public static final int Hz = 262144000;
        public static final String fj = "image_manager_disk_cache";

        @Nullable
        a build();
    }

    /* compiled from: DiskCache */
    public interface b {
        boolean c(@NonNull File file);
    }

    void a(c cVar, b bVar);

    @Nullable
    File b(c cVar);

    void c(c cVar);

    void clear();
}
