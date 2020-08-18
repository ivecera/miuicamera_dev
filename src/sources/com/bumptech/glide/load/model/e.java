package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.load.a;
import com.bumptech.glide.load.g;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferEncoder */
public class e implements a<ByteBuffer> {
    private static final String TAG = "ByteBufferEncoder";

    public boolean a(@NonNull ByteBuffer byteBuffer, @NonNull File file, @NonNull g gVar) {
        try {
            com.bumptech.glide.util.a.a(byteBuffer, file);
            return true;
        } catch (IOException e2) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to write data", e2);
            }
            return false;
        }
    }
}
