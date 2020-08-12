package com.google.zxing.qrcode.encoder;

import java.lang.reflect.Array;
import java.util.Arrays;

public final class ByteMatrix {
    private final byte[][] bytes;
    private final int height;
    private final int width;

    public ByteMatrix(int i, int i2) {
        this.bytes = (byte[][]) Array.newInstance(byte.class, i2, i);
        this.width = i;
        this.height = i2;
    }

    public void clear(byte b2) {
        for (byte[] bArr : this.bytes) {
            Arrays.fill(bArr, b2);
        }
    }

    public byte get(int i, int i2) {
        return this.bytes[i2][i];
    }

    public byte[][] getArray() {
        return this.bytes;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void set(int i, int i2, byte b2) {
        this.bytes[i2][i] = b2;
    }

    public void set(int i, int i2, int i3) {
        this.bytes[i2][i] = (byte) i3;
    }

    public void set(int i, int i2, boolean z) {
        this.bytes[i2][i] = z ? (byte) 1 : 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder((this.width * 2 * this.height) + 2);
        for (int i = 0; i < this.height; i++) {
            byte[] bArr = this.bytes[i];
            for (int i2 = 0; i2 < this.width; i2++) {
                byte b2 = bArr[i2];
                if (b2 == 0) {
                    sb.append(" 0");
                } else if (b2 != 1) {
                    sb.append("  ");
                } else {
                    sb.append(" 1");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
