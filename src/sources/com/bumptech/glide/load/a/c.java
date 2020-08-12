package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import java.io.IOException;
import java.io.OutputStream;

/* compiled from: BufferedOutputStream */
public final class c extends OutputStream {
    private b Ma;
    private byte[] buffer;
    private int index;
    @NonNull
    private final OutputStream out;

    public c(@NonNull OutputStream outputStream, @NonNull b bVar) {
        this(outputStream, bVar, 65536);
    }

    @VisibleForTesting
    c(@NonNull OutputStream outputStream, b bVar, int i) {
        this.out = outputStream;
        this.Ma = bVar;
        this.buffer = (byte[]) bVar.a(i, byte[].class);
    }

    private void no() throws IOException {
        int i = this.index;
        if (i > 0) {
            this.out.write(this.buffer, 0, i);
            this.index = 0;
        }
    }

    private void oo() throws IOException {
        if (this.index == this.buffer.length) {
            no();
        }
    }

    private void release() {
        byte[] bArr = this.buffer;
        if (bArr != null) {
            this.Ma.put(bArr);
            this.buffer = null;
        }
    }

    /* JADX INFO: finally extract failed */
    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            flush();
            this.out.close();
            release();
        } catch (Throwable th) {
            this.out.close();
            throw th;
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        no();
        this.out.flush();
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        byte[] bArr = this.buffer;
        int i2 = this.index;
        this.index = i2 + 1;
        bArr[i2] = (byte) i;
        oo();
    }

    @Override // java.io.OutputStream
    public void write(@NonNull byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(@NonNull byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        do {
            int i4 = i2 - i3;
            int i5 = i + i3;
            if (this.index != 0 || i4 < this.buffer.length) {
                int min = Math.min(i4, this.buffer.length - this.index);
                System.arraycopy(bArr, i5, this.buffer, this.index, min);
                this.index += min;
                i3 += min;
                oo();
            } else {
                this.out.write(bArr, i5, i4);
                return;
            }
        } while (i3 < i2);
    }
}
