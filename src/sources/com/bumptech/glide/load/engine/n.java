package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.EncodeStrategy;

/* compiled from: DiskCacheStrategy */
class n extends o {
    n() {
    }

    @Override // com.bumptech.glide.load.engine.o
    public boolean a(DataSource dataSource) {
        return dataSource == DataSource.REMOTE;
    }

    @Override // com.bumptech.glide.load.engine.o
    public boolean a(boolean z, DataSource dataSource, EncodeStrategy encodeStrategy) {
        return ((z && dataSource == DataSource.DATA_DISK_CACHE) || dataSource == DataSource.LOCAL) && encodeStrategy == EncodeStrategy.TRANSFORMED;
    }

    @Override // com.bumptech.glide.load.engine.o
    public boolean qj() {
        return true;
    }

    @Override // com.bumptech.glide.load.engine.o
    public boolean rj() {
        return true;
    }
}
