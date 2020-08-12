package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferFileLoader */
public class f implements t<File, ByteBuffer> {
    private static final String TAG = "ByteBufferFileLoader";

    /* compiled from: ByteBufferFileLoader */
    private static final class a implements d<ByteBuffer> {
        private final File file;

        a(File file2) {
            this.file = file2;
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public DataSource L() {
            return DataSource.LOCAL;
        }

        @Override // com.bumptech.glide.load.a.d
        public void a(@NonNull Priority priority, @NonNull d.a<? super ByteBuffer> aVar) {
            try {
                aVar.b(com.bumptech.glide.util.a.fromFile(this.file));
            } catch (IOException e2) {
                if (Log.isLoggable(f.TAG, 3)) {
                    Log.d(f.TAG, "Failed to obtain ByteBuffer for file", e2);
                }
                aVar.a(e2);
            }
        }

        @Override // com.bumptech.glide.load.a.d
        public void cancel() {
        }

        @Override // com.bumptech.glide.load.a.d
        public void cleanup() {
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public Class<ByteBuffer> ga() {
            return ByteBuffer.class;
        }
    }

    /* compiled from: ByteBufferFileLoader */
    public static class b implements u<File, ByteBuffer> {
        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<File, ByteBuffer> a(@NonNull x xVar) {
            return new f();
        }
    }

    public t.a<ByteBuffer> a(@NonNull File file, int i, int i2, @NonNull g gVar) {
        return new t.a<>(new com.bumptech.glide.e.d(file), new a(file));
    }

    /* renamed from: f */
    public boolean c(@NonNull File file) {
        return true;
    }
}
