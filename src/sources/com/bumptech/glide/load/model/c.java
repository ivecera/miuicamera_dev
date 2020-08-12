package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.model.t;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* compiled from: ByteArrayLoader */
public class c<Data> implements t<byte[], Data> {
    private final b<Data> Kp;

    /* compiled from: ByteArrayLoader */
    public static class a implements u<byte[], ByteBuffer> {
        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<byte[], ByteBuffer> a(@NonNull x xVar) {
            return new c(new b(this));
        }
    }

    /* compiled from: ByteArrayLoader */
    public interface b<Data> {
        Data e(byte[] bArr);

        Class<Data> ga();
    }

    /* renamed from: com.bumptech.glide.load.model.c$c  reason: collision with other inner class name */
    /* compiled from: ByteArrayLoader */
    private static class C0012c<Data> implements com.bumptech.glide.load.a.d<Data> {
        private final b<Data> Kp;
        private final byte[] model;

        C0012c(byte[] bArr, b<Data> bVar) {
            this.model = bArr;
            this.Kp = bVar;
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public DataSource L() {
            return DataSource.LOCAL;
        }

        @Override // com.bumptech.glide.load.a.d
        public void a(@NonNull Priority priority, @NonNull d.a<? super Data> aVar) {
            aVar.b(this.Kp.e(this.model));
        }

        @Override // com.bumptech.glide.load.a.d
        public void cancel() {
        }

        @Override // com.bumptech.glide.load.a.d
        public void cleanup() {
        }

        @Override // com.bumptech.glide.load.a.d
        @NonNull
        public Class<Data> ga() {
            return this.Kp.ga();
        }
    }

    /* compiled from: ByteArrayLoader */
    public static class d implements u<byte[], InputStream> {
        @Override // com.bumptech.glide.load.model.u
        public void X() {
        }

        @Override // com.bumptech.glide.load.model.u
        @NonNull
        public t<byte[], InputStream> a(@NonNull x xVar) {
            return new c(new d(this));
        }
    }

    public c(b<Data> bVar) {
        this.Kp = bVar;
    }

    public t.a<Data> a(@NonNull byte[] bArr, int i, int i2, @NonNull g gVar) {
        return new t.a<>(new com.bumptech.glide.e.d(bArr), new C0012c(bArr, this.Kp));
    }

    /* renamed from: i */
    public boolean c(@NonNull byte[] bArr) {
        return true;
    }
}
