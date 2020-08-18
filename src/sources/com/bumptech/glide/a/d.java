package com.bumptech.glide.a;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/* compiled from: StrictLineReader */
class d implements Closeable {
    private static final byte CR = 13;
    private static final byte LF = 10;
    private byte[] buf;
    /* access modifiers changed from: private */
    public final Charset charset;
    private int end;
    private final InputStream in;
    private int pos;

    public d(InputStream inputStream, int i, Charset charset2) {
        if (inputStream == null || charset2 == null) {
            throw new NullPointerException();
        } else if (i < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else if (charset2.equals(e.US_ASCII)) {
            this.in = inputStream;
            this.charset = charset2;
            this.buf = new byte[i];
        } else {
            throw new IllegalArgumentException("Unsupported encoding");
        }
    }

    public d(InputStream inputStream, Charset charset2) {
        this(inputStream, 8192, charset2);
    }

    private void Um() throws IOException {
        InputStream inputStream = this.in;
        byte[] bArr = this.buf;
        int read = inputStream.read(bArr, 0, bArr.length);
        if (read != -1) {
            this.pos = 0;
            this.end = read;
            return;
        }
        throw new EOFException();
    }

    public boolean cj() {
        return this.end == -1;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.in) {
            if (this.buf != null) {
                this.buf = null;
                this.in.close();
            }
        }
    }

    public String readLine() throws IOException {
        int i;
        int i2;
        synchronized (this.in) {
            if (this.buf != null) {
                if (this.pos >= this.end) {
                    Um();
                }
                for (int i3 = this.pos; i3 != this.end; i3++) {
                    if (this.buf[i3] == 10) {
                        if (i3 != this.pos) {
                            i2 = i3 - 1;
                            if (this.buf[i2] == 13) {
                                String str = new String(this.buf, this.pos, i2 - this.pos, this.charset.name());
                                this.pos = i3 + 1;
                                return str;
                            }
                        }
                        i2 = i3;
                        String str2 = new String(this.buf, this.pos, i2 - this.pos, this.charset.name());
                        this.pos = i3 + 1;
                        return str2;
                    }
                }
                c cVar = new c(this, (this.end - this.pos) + 80);
                loop1:
                while (true) {
                    cVar.write(this.buf, this.pos, this.end - this.pos);
                    this.end = -1;
                    Um();
                    i = this.pos;
                    while (true) {
                        if (i != this.end) {
                            if (this.buf[i] == 10) {
                                break loop1;
                            }
                            i++;
                        }
                    }
                }
                if (i != this.pos) {
                    cVar.write(this.buf, this.pos, i - this.pos);
                }
                this.pos = i + 1;
                String cVar2 = cVar.toString();
                return cVar2;
            }
            throw new IOException("LineReader is closed");
        }
    }
}
