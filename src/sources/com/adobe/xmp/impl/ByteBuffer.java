package com.adobe.xmp.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.jcodec.platform.Platform;

public class ByteBuffer {
    private byte[] buffer;
    private String encoding;
    private int length;

    public ByteBuffer(int i) {
        this.encoding = null;
        this.buffer = new byte[i];
        this.length = 0;
    }

    public ByteBuffer(InputStream inputStream) throws IOException {
        this.encoding = null;
        this.length = 0;
        this.buffer = new byte[16384];
        while (true) {
            int read = inputStream.read(this.buffer, this.length, 16384);
            if (read > 0) {
                this.length += read;
                if (read == 16384) {
                    ensureCapacity(this.length + 16384);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public ByteBuffer(byte[] bArr) {
        this.encoding = null;
        this.buffer = bArr;
        this.length = bArr.length;
    }

    public ByteBuffer(byte[] bArr, int i) {
        this.encoding = null;
        if (i <= bArr.length) {
            this.buffer = bArr;
            this.length = i;
            return;
        }
        throw new ArrayIndexOutOfBoundsException("Valid length exceeds the buffer length.");
    }

    public ByteBuffer(byte[] bArr, int i, int i2) {
        this.encoding = null;
        if (i2 <= bArr.length - i) {
            this.buffer = new byte[i2];
            System.arraycopy(bArr, i, this.buffer, 0, i2);
            this.length = i2;
            return;
        }
        throw new ArrayIndexOutOfBoundsException("Valid length exceeds the buffer length.");
    }

    private void ensureCapacity(int i) {
        byte[] bArr = this.buffer;
        if (i > bArr.length) {
            this.buffer = new byte[(bArr.length * 2)];
            System.arraycopy(bArr, 0, this.buffer, 0, bArr.length);
        }
    }

    public void append(byte b2) {
        ensureCapacity(this.length + 1);
        byte[] bArr = this.buffer;
        int i = this.length;
        this.length = i + 1;
        bArr[i] = b2;
    }

    public void append(ByteBuffer byteBuffer) {
        append(byteBuffer.buffer, 0, byteBuffer.length);
    }

    public void append(byte[] bArr) {
        append(bArr, 0, bArr.length);
    }

    public void append(byte[] bArr, int i, int i2) {
        ensureCapacity(this.length + i2);
        System.arraycopy(bArr, i, this.buffer, this.length, i2);
        this.length += i2;
    }

    public byte byteAt(int i) {
        if (i < this.length) {
            return this.buffer[i];
        }
        throw new IndexOutOfBoundsException("The index exceeds the valid buffer area");
    }

    public int charAt(int i) {
        if (i < this.length) {
            return this.buffer[i] & 255;
        }
        throw new IndexOutOfBoundsException("The index exceeds the valid buffer area");
    }

    public InputStream getByteStream() {
        return new ByteArrayInputStream(this.buffer, 0, this.length);
    }

    public String getEncoding() {
        if (this.encoding == null) {
            int i = this.length;
            if (i < 2) {
                this.encoding = "UTF-8";
            } else {
                byte[] bArr = this.buffer;
                if (bArr[0] == 0) {
                    if (i < 4 || bArr[1] != 0) {
                        this.encoding = Platform.UTF_16BE;
                    } else if ((bArr[2] & 255) == 254 && (bArr[3] & 255) == 255) {
                        this.encoding = "UTF-32BE";
                    } else {
                        this.encoding = "UTF-32";
                    }
                } else if ((bArr[0] & 255) < 128) {
                    if (bArr[1] != 0) {
                        this.encoding = "UTF-8";
                    } else if (i < 4 || bArr[2] != 0) {
                        this.encoding = "UTF-16LE";
                    } else {
                        this.encoding = "UTF-32LE";
                    }
                } else if ((bArr[0] & 255) == 239) {
                    this.encoding = "UTF-8";
                } else if ((bArr[0] & 255) == 254) {
                    this.encoding = "UTF-16";
                } else if (i < 4 || bArr[2] != 0) {
                    this.encoding = "UTF-16";
                } else {
                    this.encoding = "UTF-32";
                }
            }
        }
        return this.encoding;
    }

    public int length() {
        return this.length;
    }
}
