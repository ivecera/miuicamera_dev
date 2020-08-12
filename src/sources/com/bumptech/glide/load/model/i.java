package com.bumptech.glide.load.model;

import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: FileLoader */
public class i<Data> implements t<File, Data> {
    private static final String TAG = "FileLoader";
    private final d<Data> Pp;

    /* compiled from: FileLoader */
    public static class a<Data> implements u<File, Data> {
        private final d<Data> _l;

        public a(d<Data> dVar) {
            this._l = dVar;
        }

        @Override // com.bumptech.glide.load.model.u
        public final void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public final t<File, Data> a(@NonNull x xVar) {
            return new i(this._l);
        }
    }

    /* compiled from: FileLoader */
    public static class b extends a<ParcelFileDescriptor> {
        public b() {
            super(new j());
        }
    }

    /* compiled from: FileLoader */
    private static final class c<Data> implements com.bumptech.glide.load.a.d<Data> {
        private final d<Data> _l;
        private Data data;
        private final File file;

        c(File file2, d<Data> dVar) {
            this.file = file2;
            this._l = dVar;
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public DataSource L() {
            return DataSource.LOCAL;
        }

        @Override // com.bumptech.glide.load.a.d
        public void a(@NonNull Priority priority, @NonNull d.a<? super Data> aVar) {
            try {
                this.data = this._l.b(this.file);
                aVar.b(this.data);
            } catch (FileNotFoundException e2) {
                if (Log.isLoggable(i.TAG, 3)) {
                    Log.d(i.TAG, "Failed to open file", e2);
                }
                aVar.a(e2);
            }
        }

        @Override // com.bumptech.glide.load.a.d
        public void cancel() {
        }

        @Override // com.bumptech.glide.load.a.d
        public void cleanup() {
            Data data2 = this.data;
            if (data2 != null) {
                try {
                    this._l.e(data2);
                } catch (IOException unused) {
                }
            }
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public Class<Data> ga() {
            return this._l.ga();
        }
    }

    /* compiled from: FileLoader */
    public interface d<Data> {
        Data b(File file) throws FileNotFoundException;

        void e(Data data) throws IOException;

        Class<Data> ga();
    }

    /* compiled from: FileLoader */
    public static class e extends a<InputStream> {
        public e() {
            super(new k());
        }
    }

    public i(d<Data> dVar) {
        this.Pp = dVar;
    }

    public t.a<Data> a(@NonNull File file, int i, int i2, @NonNull g gVar) {
        return new t.a<>(new com.bumptech.glide.e.d(file), new c(file, this.Pp));
    }

    /* renamed from: f */
    public boolean c(@NonNull File file) {
        return true;
    }
}
