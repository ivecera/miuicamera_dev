package com.bumptech.glide.load.resource.gif;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.i;
import com.bumptech.glide.util.a;
import java.io.File;
import java.io.IOException;

/* compiled from: GifDrawableEncoder */
public class c implements i<b> {
    private static final String TAG = "GifEncoder";

    @Override // com.bumptech.glide.load.i
    @NonNull
    public EncodeStrategy a(@NonNull g gVar) {
        return EncodeStrategy.SOURCE;
    }

    public boolean a(@NonNull A<b> a2, @NonNull File file, @NonNull g gVar) {
        try {
            a.a(a2.get().getBuffer(), file);
            return true;
        } catch (IOException e2) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Failed to encode GIF drawable data", e2);
            }
            return false;
        }
    }
}
