package com.bumptech.glide.request;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.GlideException;

/* compiled from: ResourceCallback */
public interface g {
    void a(A<?> a2, DataSource dataSource);

    void a(GlideException glideException);
}
