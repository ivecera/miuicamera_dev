package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.EncodeStrategy;

/* compiled from: DiskCacheStrategy */
public abstract class o {
    public static final o ALL = new j();
    public static final o AUTOMATIC = new n();
    public static final o DATA = new l();
    public static final o NONE = new k();
    public static final o bn = new m();

    public abstract boolean a(DataSource dataSource);

    public abstract boolean a(boolean z, DataSource dataSource, EncodeStrategy encodeStrategy);

    public abstract boolean qj();

    public abstract boolean rj();
}
