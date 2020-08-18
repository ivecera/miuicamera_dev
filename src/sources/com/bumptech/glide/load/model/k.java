package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.i;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: FileLoader */
class k implements i.d<InputStream> {
    k() {
    }

    @Override // com.bumptech.glide.load.model.i.d
    public InputStream b(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    /* renamed from: f */
    public void e(InputStream inputStream) throws IOException {
        inputStream.close();
    }

    @Override // com.bumptech.glide.load.model.i.d
    public Class<InputStream> ga() {
        return InputStream.class;
    }
}
