package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.g;
import java.io.File;

/* compiled from: DataCacheWriter */
class e<DataType> implements a.b {
    private final DataType data;
    private final com.bumptech.glide.load.a<DataType> encoder;
    private final g options;

    e(com.bumptech.glide.load.a<DataType> aVar, DataType datatype, g gVar) {
        this.encoder = aVar;
        this.data = datatype;
        this.options = gVar;
    }

    @Override // com.bumptech.glide.load.engine.a.a.b
    public boolean c(@NonNull File file) {
        return this.encoder.a(this.data, file, this.options);
    }
}
