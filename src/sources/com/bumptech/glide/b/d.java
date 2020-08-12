package com.bumptech.glide.b;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* compiled from: GifHeaderParser */
public class d {
    private static final int Kk = 255;
    private static final int Lk = 44;
    private static final int Mk = 33;
    private static final int Nk = 59;
    private static final int Ok = 249;
    private static final int Pk = 255;
    private static final int Qk = 254;
    private static final int Rk = 1;
    private static final int Sk = 28;
    private static final String TAG = "GifHeaderParser";
    private static final int Tk = 2;
    private static final int Uk = 1;
    private static final int Vk = 128;
    private static final int Wk = 64;
    private static final int Xk = 7;
    private static final int Yk = 128;
    private static final int Zk = 7;
    static final int _k = 2;
    static final int bl = 10;
    private static final int cl = 256;
    private final byte[] Ik = new byte[256];
    private ByteBuffer Jk;
    private int blockSize = 0;
    private c header;

    @Nullable
    private int[] V(int i) {
        byte[] bArr = new byte[(i * 3)];
        int[] iArr = null;
        try {
            this.Jk.get(bArr);
            iArr = new int[256];
            int i2 = 0;
            int i3 = 0;
            while (i2 < i) {
                int i4 = i3 + 1;
                int i5 = i4 + 1;
                int i6 = i5 + 1;
                int i7 = i2 + 1;
                iArr[i2] = ((bArr[i3] & 255) << 16) | -16777216 | ((bArr[i4] & 255) << 8) | (bArr[i5] & 255);
                i3 = i6;
                i2 = i7;
            }
        } catch (BufferUnderflowException e2) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Format Error Reading Color Table", e2);
            }
            this.header.status = 1;
        }
        return iArr;
    }

    private boolean Vm() {
        return this.header.status != 0;
    }

    private void W(int i) {
        boolean z = false;
        while (!z && !Vm() && this.header.frameCount <= i) {
            int read = read();
            if (read == 33) {
                int read2 = read();
                if (read2 == 1) {
                    skip();
                } else if (read2 == 249) {
                    this.header.Bk = new b();
                    Zm();
                } else if (read2 == 254) {
                    skip();
                } else if (read2 != 255) {
                    skip();
                } else {
                    Xm();
                    StringBuilder sb = new StringBuilder();
                    for (int i2 = 0; i2 < 11; i2++) {
                        sb.append((char) this.Ik[i2]);
                    }
                    if (sb.toString().equals("NETSCAPE2.0")) {
                        an();
                    } else {
                        skip();
                    }
                }
            } else if (read == 44) {
                c cVar = this.header;
                if (cVar.Bk == null) {
                    cVar.Bk = new b();
                }
                Wm();
            } else if (read != 59) {
                this.header.status = 1;
            } else {
                z = true;
            }
        }
    }

    private void Wm() {
        this.header.Bk.lk = readShort();
        this.header.Bk.mk = readShort();
        this.header.Bk.nk = readShort();
        this.header.Bk.pk = readShort();
        int read = read();
        boolean z = false;
        boolean z2 = (read & 128) != 0;
        int pow = (int) Math.pow(2.0d, (double) ((read & 7) + 1));
        b bVar = this.header.Bk;
        if ((read & 64) != 0) {
            z = true;
        }
        bVar.qk = z;
        if (z2) {
            this.header.Bk.vk = V(pow);
        } else {
            this.header.Bk.vk = null;
        }
        this.header.Bk.uk = this.Jk.position();
        bn();
        if (!Vm()) {
            c cVar = this.header;
            cVar.frameCount++;
            cVar.frames.add(cVar.Bk);
        }
    }

    private void Xm() {
        this.blockSize = read();
        if (this.blockSize > 0) {
            int i = 0;
            int i2 = 0;
            while (i < this.blockSize) {
                try {
                    i2 = this.blockSize - i;
                    this.Jk.get(this.Ik, i, i2);
                    i += i2;
                } catch (Exception e2) {
                    if (Log.isLoggable(TAG, 3)) {
                        Log.d(TAG, "Error Reading Block n: " + i + " count: " + i2 + " blockSize: " + this.blockSize, e2);
                    }
                    this.header.status = 1;
                    return;
                }
            }
        }
    }

    private void Ym() {
        W(Integer.MAX_VALUE);
    }

    private void Zm() {
        read();
        int read = read();
        b bVar = this.header.Bk;
        bVar.sk = (read & 28) >> 2;
        boolean z = true;
        if (bVar.sk == 0) {
            bVar.sk = 1;
        }
        b bVar2 = this.header.Bk;
        if ((read & 1) == 0) {
            z = false;
        }
        bVar2.rk = z;
        int readShort = readShort();
        if (readShort < 2) {
            readShort = 10;
        }
        b bVar3 = this.header.Bk;
        bVar3.delay = readShort * 10;
        bVar3.tk = read();
        read();
    }

    private void _m() {
        this.header.width = readShort();
        this.header.height = readShort();
        int read = read();
        this.header.Ck = (read & 128) != 0;
        this.header.Dk = (int) Math.pow(2.0d, (double) ((read & 7) + 1));
        this.header.Ek = read();
        this.header.Fk = read();
    }

    private void an() {
        do {
            Xm();
            byte[] bArr = this.Ik;
            if (bArr[0] == 1) {
                this.header.Uf = ((bArr[2] & 255) << 8) | (bArr[1] & 255);
            }
            if (this.blockSize <= 0) {
                return;
            }
        } while (!Vm());
    }

    private void bn() {
        read();
        skip();
    }

    private int read() {
        try {
            return this.Jk.get() & 255;
        } catch (Exception unused) {
            this.header.status = 1;
            return 0;
        }
    }

    private void readHeader() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((char) read());
        }
        if (!sb.toString().startsWith("GIF")) {
            this.header.status = 1;
            return;
        }
        _m();
        if (this.header.Ck && !Vm()) {
            c cVar = this.header;
            cVar.Ak = V(cVar.Dk);
            c cVar2 = this.header;
            cVar2.bgColor = cVar2.Ak[cVar2.Ek];
        }
    }

    private int readShort() {
        return this.Jk.getShort();
    }

    private void reset() {
        this.Jk = null;
        Arrays.fill(this.Ik, (byte) 0);
        this.header = new c();
        this.blockSize = 0;
    }

    private void skip() {
        int read;
        do {
            read = read();
            this.Jk.position(Math.min(this.Jk.position() + read, this.Jk.limit()));
        } while (read > 0);
    }

    public void clear() {
        this.Jk = null;
        this.header = null;
    }

    @NonNull
    public c dj() {
        if (this.Jk == null) {
            throw new IllegalStateException("You must call setData() before parseHeader()");
        } else if (Vm()) {
            return this.header;
        } else {
            readHeader();
            if (!Vm()) {
                Ym();
                c cVar = this.header;
                if (cVar.frameCount < 0) {
                    cVar.status = 1;
                }
            }
            return this.header;
        }
    }

    public boolean isAnimated() {
        readHeader();
        if (!Vm()) {
            W(2);
        }
        return this.header.frameCount > 1;
    }

    public d setData(@NonNull ByteBuffer byteBuffer) {
        reset();
        this.Jk = byteBuffer.asReadOnlyBuffer();
        this.Jk.position(0);
        this.Jk.order(ByteOrder.LITTLE_ENDIAN);
        return this;
    }

    public d setData(@Nullable byte[] bArr) {
        if (bArr != null) {
            setData(ByteBuffer.wrap(bArr));
        } else {
            this.Jk = null;
            this.header.status = 2;
        }
        return this;
    }
}
