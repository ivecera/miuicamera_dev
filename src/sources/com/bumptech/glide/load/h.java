package com.bumptech.glide.load;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.A;
import java.io.IOException;

/* compiled from: ResourceDecoder */
public interface h<T, Z> {
    boolean a(@NonNull T t, @NonNull g gVar) throws IOException;

    @Nullable
    A<Z> b(@NonNull T t, int i, int i2, @NonNull g gVar) throws IOException;
}
