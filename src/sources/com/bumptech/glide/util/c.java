package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

/* compiled from: ExceptionCatchingInputStream */
public class c extends InputStream {
    private static final Queue<c> QUEUE = l.createQueue(0);
    private InputStream Qf;
    private IOException exception;

    c() {
    }

    static void clearQueue() {
        while (!QUEUE.isEmpty()) {
            QUEUE.remove();
        }
    }

    @NonNull
    public static c j(@NonNull InputStream inputStream) {
        c poll;
        synchronized (QUEUE) {
            poll = QUEUE.poll();
        }
        if (poll == null) {
            poll = new c();
        }
        poll.setInputStream(inputStream);
        return poll;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.Qf.available();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable, java.io.InputStream
    public void close() throws IOException {
        this.Qf.close();
    }

    @Nullable
    public IOException getException() {
        return this.exception;
    }

    public void mark(int i) {
        this.Qf.mark(i);
    }

    public boolean markSupported() {
        return this.Qf.markSupported();
    }

    @Override // java.io.InputStream
    public int read() {
        try {
            return this.Qf.read();
        } catch (IOException e2) {
            this.exception = e2;
            return -1;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) {
        try {
            return this.Qf.read(bArr);
        } catch (IOException e2) {
            this.exception = e2;
            return -1;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) {
        try {
            return this.Qf.read(bArr, i, i2);
        } catch (IOException e2) {
            this.exception = e2;
            return -1;
        }
    }

    public void release() {
        this.exception = null;
        this.Qf = null;
        synchronized (QUEUE) {
            QUEUE.offer(this);
        }
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        this.Qf.reset();
    }

    /* access modifiers changed from: package-private */
    public void setInputStream(@NonNull InputStream inputStream) {
        this.Qf = inputStream;
    }

    @Override // java.io.InputStream
    public long skip(long j) {
        try {
            return this.Qf.skip(j);
        } catch (IOException e2) {
            this.exception = e2;
            return 0;
        }
    }
}
