package com.bumptech.glide.request;

import android.support.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.o;

/* compiled from: RequestListener */
public interface e<R> {
    boolean a(@Nullable GlideException glideException, Object obj, o<R> oVar, boolean z);

    boolean a(R r, Object obj, o<R> oVar, DataSource dataSource, boolean z);
}
