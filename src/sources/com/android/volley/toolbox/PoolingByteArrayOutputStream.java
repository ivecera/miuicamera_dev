package com.android.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PoolingByteArrayOutputStream extends ByteArrayOutputStream {
    private static final int DEFAULT_SIZE = 256;
    private final ByteArrayPool mPool;

    public PoolingByteArrayOutputStream(ByteArrayPool byteArrayPool) {
        this(byteArrayPool, 256);
    }

    public PoolingByteArrayOutputStream(ByteArrayPool byteArrayPool, int i) {
        this.mPool = byteArrayPool;
        ((ByteArrayOutputStream) this).buf = this.mPool.getBuf(Math.max(i, 256));
    }

    private void expand(int i) {
        int i2 = ((ByteArrayOutputStream) this).count;
        if (i2 + i > ((ByteArrayOutputStream) this).buf.length) {
            byte[] buf = this.mPool.getBuf((i2 + i) * 2);
            System.arraycopy(((ByteArrayOutputStream) this).buf, 0, buf, 0, ((ByteArrayOutputStream) this).count);
            this.mPool.returnBuf(((ByteArrayOutputStream) this).buf);
            ((ByteArrayOutputStream) this).buf = buf;
        }
    }

    @Override // java.io.OutputStream, java.io.ByteArrayOutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.mPool.returnBuf(((ByteArrayOutputStream) this).buf);
        ((ByteArrayOutputStream) this).buf = null;
        super.close();
    }

    @Override // java.lang.Object
    public void finalize() {
        this.mPool.returnBuf(((ByteArrayOutputStream) this).buf);
    }

    @Override // java.io.OutputStream, java.io.ByteArrayOutputStream
    public synchronized void write(int i) {
        expand(1);
        super.write(i);
    }

    @Override // java.io.OutputStream
    public synchronized void write(byte[] bArr, int i, int i2) {
        expand(i2);
        super.write(bArr, i, i2);
    }
}
