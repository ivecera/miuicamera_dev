package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.EncodeStrategy;

/* compiled from: DiskCacheStrategy */
class l extends o {
    l() {
    }

    @Override // com.bumptech.glide.load.engine.o
    public boolean a(DataSource dataSource) {
        return (dataSource == DataSource.DATA_DISK_CACHE || dataSource == DataSource.MEMORY_CACHE) ? false : true;
    }

    @Override // com.bumptech.glide.load.engine.o
    public boolean a(boolean z, DataSource dataSource, EncodeStrategy encodeStrategy) {
        return false;
    }

    @Override // com.bumptech.glide.load.engine.o
    public boolean qj() {
        return true;
    }

    @Override // com.bumptech.glide.load.engine.o
    public boolean rj() {
        return false;
    }
}
