package com.bumptech.glide.load.b.c;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import java.io.File;

/* compiled from: FileDecoder */
public class a implements h<File, File> {
    public boolean a(@NonNull File file, @NonNull g gVar) {
        return true;
    }

    public A<File> b(@NonNull File file, int i, int i2, @NonNull g gVar) {
        return new b(file);
    }
}
