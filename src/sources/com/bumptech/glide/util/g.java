package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: MarkEnforcingInputStream */
public class g extends FilterInputStream {
    private static final int Cx = -1;
    private static final int UNSET = Integer.MIN_VALUE;
    private int Bx = Integer.MIN_VALUE;

    public g(@NonNull InputStream inputStream) {
        super(inputStream);
    }

    private long h(long j) {
        int i = this.Bx;
        if (i == 0) {
            return -1;
        }
        return (i == Integer.MIN_VALUE || j <= ((long) i)) ? j : (long) i;
    }

    private void i(long j) {
        int i = this.Bx;
        if (i != Integer.MIN_VALUE && j != -1) {
            this.Bx = (int) (((long) i) - j);
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        int i = this.Bx;
        return i == Integer.MIN_VALUE ? super.available() : Math.min(i, super.available());
    }

    public synchronized void mark(int i) {
        super.mark(i);
        this.Bx = i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (h(1) == -1) {
            return -1;
        }
        int read = super.read();
        i(1);
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(@NonNull byte[] bArr, int i, int i2) throws IOException {
        int h = (int) h((long) i2);
        if (h == -1) {
            return -1;
        }
        int read = super.read(bArr, i, h);
        i((long) read);
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        super.reset();
        this.Bx = Integer.MIN_VALUE;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        long h = h(j);
        if (h == -1) {
            return 0;
        }
        long skip = super.skip(h);
        i(skip);
        return skip;
    }
}
