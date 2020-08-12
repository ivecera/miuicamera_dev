package com.bumptech.glide.b;

import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.b.a;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: StandardGifDecoder */
public class e implements a {
    private static final int Kk = 255;
    private static final String TAG = "e";
    private static final int rl = 4096;
    private static final int sl = -1;
    private static final int tl = -1;
    private static final int ul = 4;
    @ColorInt
    private static final int vl = 0;
    private byte[] Ik;
    private ByteBuffer Jk;
    @ColorInt
    private int[] dl;
    @ColorInt
    private final int[] el;
    private final a.C0006a fl;
    private byte[] gl;
    private c header;
    private byte[] hl;
    private byte[] il;
    @ColorInt
    private int[] jl;
    private int kl;
    private Bitmap ll;
    private boolean ml;
    private int nl;
    private int ol;
    private d parser;
    @Nullable
    private Boolean pl;
    private short[] prefix;
    @NonNull
    private Bitmap.Config ql;
    private int sampleSize;
    private int status;

    public e(@NonNull a.C0006a aVar) {
        this.el = new int[256];
        this.ql = Bitmap.Config.ARGB_8888;
        this.fl = aVar;
        this.header = new c();
    }

    public e(@NonNull a.C0006a aVar, c cVar, ByteBuffer byteBuffer) {
        this(aVar, cVar, byteBuffer, 1);
    }

    public e(@NonNull a.C0006a aVar, c cVar, ByteBuffer byteBuffer, int i) {
        this(aVar);
        a(cVar, byteBuffer, i);
    }

    private int Xm() {
        int readByte = readByte();
        if (readByte <= 0) {
            return readByte;
        }
        ByteBuffer byteBuffer = this.Jk;
        byteBuffer.get(this.Ik, 0, Math.min(readByte, byteBuffer.remaining()));
        return readByte;
    }

    private Bitmap a(b bVar, b bVar2) {
        int i;
        int i2;
        Bitmap bitmap;
        int[] iArr = this.jl;
        int i3 = 0;
        if (bVar2 == null) {
            Bitmap bitmap2 = this.ll;
            if (bitmap2 != null) {
                this.fl.d(bitmap2);
            }
            this.ll = null;
            Arrays.fill(iArr, 0);
        }
        if (bVar2 != null && bVar2.sk == 3 && this.ll == null) {
            Arrays.fill(iArr, 0);
        }
        if (bVar2 != null && (i2 = bVar2.sk) > 0) {
            if (i2 == 2) {
                if (!bVar.rk) {
                    c cVar = this.header;
                    int i4 = cVar.bgColor;
                    if (bVar.vk == null || cVar.Ek != bVar.tk) {
                        i3 = i4;
                    }
                } else if (this.kl == 0) {
                    this.pl = true;
                }
                int i5 = bVar2.pk;
                int i6 = this.sampleSize;
                int i7 = i5 / i6;
                int i8 = bVar2.mk / i6;
                int i9 = bVar2.nk / i6;
                int i10 = bVar2.lk / i6;
                int i11 = this.ol;
                int i12 = (i8 * i11) + i10;
                int i13 = (i7 * i11) + i12;
                while (i12 < i13) {
                    int i14 = i12 + i9;
                    for (int i15 = i12; i15 < i14; i15++) {
                        iArr[i15] = i3;
                    }
                    i12 += this.ol;
                }
            } else if (i2 == 3 && (bitmap = this.ll) != null) {
                int i16 = this.ol;
                bitmap.getPixels(iArr, 0, i16, 0, 0, i16, this.nl);
            }
        }
        c(bVar);
        if (bVar.qk || this.sampleSize != 1) {
            a(bVar);
        } else {
            b(bVar);
        }
        if (this.ml && ((i = bVar.sk) == 0 || i == 1)) {
            if (this.ll == null) {
                this.ll = dn();
            }
            Bitmap bitmap3 = this.ll;
            int i17 = this.ol;
            bitmap3.setPixels(iArr, 0, i17, 0, 0, i17, this.nl);
        }
        Bitmap dn = dn();
        int i18 = this.ol;
        dn.setPixels(iArr, 0, i18, 0, 0, i18, this.nl);
        return dn;
    }

    private void a(b bVar) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int[] iArr = this.jl;
        int i6 = bVar.pk;
        int i7 = this.sampleSize;
        int i8 = i6 / i7;
        int i9 = bVar.mk / i7;
        int i10 = bVar.nk / i7;
        int i11 = bVar.lk / i7;
        boolean z = true;
        boolean z2 = this.kl == 0;
        int i12 = this.sampleSize;
        int i13 = this.ol;
        int i14 = this.nl;
        byte[] bArr = this.il;
        int[] iArr2 = this.dl;
        int i15 = 1;
        int i16 = 8;
        int i17 = 0;
        Boolean bool = this.pl;
        int i18 = 0;
        while (i18 < i8) {
            if (bVar.qk) {
                if (i17 >= i8) {
                    i = i8;
                    i5 = i15 + 1;
                    if (i5 == 2) {
                        i17 = 4;
                    } else if (i5 == 3) {
                        i16 = 4;
                        i17 = 2;
                    } else if (i5 == 4) {
                        i17 = 1;
                        i16 = 2;
                    }
                } else {
                    i = i8;
                    i5 = i15;
                }
                i2 = i17 + i16;
                i15 = i5;
            } else {
                i = i8;
                i2 = i17;
                i17 = i18;
            }
            int i19 = i17 + i9;
            boolean z3 = i12 == 1;
            if (i19 < i14) {
                int i20 = i19 * i13;
                int i21 = i20 + i11;
                int i22 = i21 + i10;
                int i23 = i20 + i13;
                if (i23 < i22) {
                    i22 = i23;
                }
                i3 = i9;
                int i24 = i18 * i12 * bVar.nk;
                if (z3) {
                    int i25 = i21;
                    while (i25 < i22) {
                        int i26 = iArr2[bArr[i24] & 255];
                        if (i26 != 0) {
                            iArr[i25] = i26;
                        } else if (z2 && bool == null) {
                            bool = z;
                        }
                        i24 += i12;
                        i25++;
                        i10 = i10;
                    }
                } else {
                    i4 = i10;
                    int i27 = ((i22 - i21) * i12) + i24;
                    int i28 = i21;
                    while (i28 < i22) {
                        int c2 = c(i24, i27, bVar.nk);
                        if (c2 != 0) {
                            iArr[i28] = c2;
                        } else if (z2 && bool == null) {
                            bool = z;
                        }
                        i24 += i12;
                        i28++;
                        i22 = i22;
                    }
                    i18++;
                    i17 = i2;
                    i10 = i4;
                    z = z;
                    i8 = i;
                    i9 = i3;
                }
            } else {
                i3 = i9;
            }
            i4 = i10;
            i18++;
            i17 = i2;
            i10 = i4;
            z = z;
            i8 = i;
            i9 = i3;
        }
        if (this.pl == null) {
            this.pl = Boolean.valueOf(bool == null ? false : bool.booleanValue());
        }
    }

    private void b(b bVar) {
        b bVar2 = bVar;
        int[] iArr = this.jl;
        int i = bVar2.pk;
        int i2 = bVar2.mk;
        int i3 = bVar2.nk;
        int i4 = bVar2.lk;
        boolean z = this.kl == 0;
        int i5 = this.ol;
        byte[] bArr = this.il;
        int[] iArr2 = this.dl;
        int i6 = 0;
        byte b2 = -1;
        while (i6 < i) {
            int i7 = (i6 + i2) * i5;
            int i8 = i7 + i4;
            int i9 = i8 + i3;
            int i10 = i7 + i5;
            if (i10 < i9) {
                i9 = i10;
            }
            int i11 = bVar2.nk * i6;
            for (int i12 = i8; i12 < i9; i12++) {
                byte b3 = bArr[i11];
                byte b4 = b3 & 255;
                if (b4 != b2) {
                    int i13 = iArr2[b4];
                    if (i13 != 0) {
                        iArr[i12] = i13;
                    } else {
                        b2 = b3;
                    }
                }
                i11++;
            }
            i6++;
            bVar2 = bVar;
        }
        this.pl = Boolean.valueOf(this.pl == null && z && b2 != -1);
    }

    @ColorInt
    private int c(int i, int i2, int i3) {
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        for (int i9 = i; i9 < this.sampleSize + i; i9++) {
            byte[] bArr = this.il;
            if (i9 >= bArr.length || i9 >= i2) {
                break;
            }
            int i10 = this.dl[bArr[i9] & 255];
            if (i10 != 0) {
                i4 += (i10 >> 24) & 255;
                i5 += (i10 >> 16) & 255;
                i6 += (i10 >> 8) & 255;
                i7 += i10 & 255;
                i8++;
            }
        }
        int i11 = i + i3;
        for (int i12 = i11; i12 < this.sampleSize + i11; i12++) {
            byte[] bArr2 = this.il;
            if (i12 >= bArr2.length || i12 >= i2) {
                break;
            }
            int i13 = this.dl[bArr2[i12] & 255];
            if (i13 != 0) {
                i4 += (i13 >> 24) & 255;
                i5 += (i13 >> 16) & 255;
                i6 += (i13 >> 8) & 255;
                i7 += i13 & 255;
                i8++;
            }
        }
        if (i8 == 0) {
            return 0;
        }
        return ((i4 / i8) << 24) | ((i5 / i8) << 16) | ((i6 / i8) << 8) | (i7 / i8);
    }

    private void c(b bVar) {
        int i;
        int i2;
        short s;
        e eVar = this;
        if (bVar != null) {
            eVar.Jk.position(bVar.uk);
        }
        if (bVar == null) {
            c cVar = eVar.header;
            i = cVar.width;
            i2 = cVar.height;
        } else {
            i = bVar.nk;
            i2 = bVar.pk;
        }
        int i3 = i * i2;
        byte[] bArr = eVar.il;
        if (bArr == null || bArr.length < i3) {
            eVar.il = eVar.fl.f(i3);
        }
        byte[] bArr2 = eVar.il;
        if (eVar.prefix == null) {
            eVar.prefix = new short[4096];
        }
        short[] sArr = eVar.prefix;
        if (eVar.gl == null) {
            eVar.gl = new byte[4096];
        }
        byte[] bArr3 = eVar.gl;
        if (eVar.hl == null) {
            eVar.hl = new byte[4097];
        }
        byte[] bArr4 = eVar.hl;
        int readByte = readByte();
        int i4 = 1 << readByte;
        int i5 = i4 + 1;
        int i6 = i4 + 2;
        int i7 = readByte + 1;
        int i8 = (1 << i7) - 1;
        int i9 = 0;
        for (int i10 = 0; i10 < i4; i10++) {
            sArr[i10] = 0;
            bArr3[i10] = (byte) i10;
        }
        byte[] bArr5 = eVar.Ik;
        int i11 = i7;
        int i12 = i6;
        int i13 = i8;
        int i14 = 0;
        byte b2 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        byte b3 = 0;
        int i18 = 0;
        byte b4 = -1;
        while (true) {
            if (i9 < i3) {
                if (i14 == 0) {
                    i14 = Xm();
                    if (i14 <= 0) {
                        eVar.status = 3;
                        break;
                    }
                    i16 = 0;
                }
                i15 += (bArr5[i16] & 255) << b2;
                i16++;
                i14--;
                int i19 = b2 + 8;
                byte b5 = b4;
                byte b6 = b3;
                int i20 = i12;
                int i21 = i17;
                int i22 = i9;
                int i23 = i11;
                while (true) {
                    if (i19 < i23) {
                        i11 = i23;
                        b3 = b6;
                        i9 = i22;
                        i17 = i21;
                        b2 = i19;
                        i12 = i20;
                        b4 = b5;
                        eVar = this;
                        break;
                    }
                    byte b7 = i15 & i13;
                    i15 >>= i23;
                    i19 -= i23;
                    if (b7 == i4) {
                        i23 = i7;
                        i20 = i6;
                        i13 = i8;
                        b5 = -1;
                    } else if (b7 == i5) {
                        b2 = i19;
                        i11 = i23;
                        i9 = i22;
                        i17 = i21;
                        i12 = i20;
                        b3 = b6;
                        b4 = b5;
                        break;
                    } else {
                        if (b5 == -1) {
                            bArr2[i21] = bArr3[b7];
                            i21++;
                            i22++;
                            b5 = b7;
                            b6 = b5;
                        } else {
                            int i24 = i20;
                            if (b7 >= i24) {
                                bArr4[i18] = (byte) b6;
                                i18++;
                                s = b5;
                            } else {
                                s = b7;
                            }
                            while (s >= i4) {
                                bArr4[i18] = bArr3[s];
                                i18++;
                                s = sArr[s];
                            }
                            byte b8 = bArr3[s] & 255;
                            byte b9 = (byte) b8;
                            bArr2[i21] = b9;
                            while (true) {
                                i21++;
                                i22++;
                                if (i18 <= 0) {
                                    break;
                                }
                                i18--;
                                bArr2[i21] = bArr4[i18];
                            }
                            if (i24 < 4096) {
                                sArr[i24] = (short) b5;
                                bArr3[i24] = b9;
                                i24++;
                                if ((i24 & i13) == 0 && i24 < 4096) {
                                    i23++;
                                    i13 += i24;
                                }
                            }
                            b5 = b7;
                            i19 = i19;
                            i7 = i7;
                            b6 = b8;
                            i20 = i24;
                        }
                        eVar = this;
                    }
                }
            } else {
                break;
            }
        }
        Arrays.fill(bArr2, i17, i3, (byte) 0);
    }

    @NonNull
    private d cn() {
        if (this.parser == null) {
            this.parser = new d();
        }
        return this.parser;
    }

    private Bitmap dn() {
        Boolean bool = this.pl;
        Bitmap a2 = this.fl.a(this.ol, this.nl, (bool == null || bool.booleanValue()) ? Bitmap.Config.ARGB_8888 : this.ql);
        a2.setHasAlpha(true);
        return a2;
    }

    private int readByte() {
        return this.Jk.get() & 255;
    }

    @Override // com.bumptech.glide.b.a
    public void M() {
        this.kl = -1;
    }

    @Override // com.bumptech.glide.b.a
    public int P() {
        return this.kl;
    }

    @Override // com.bumptech.glide.b.a
    public int R() {
        return this.Jk.limit() + this.il.length + (this.jl.length * 4);
    }

    @Override // com.bumptech.glide.b.a
    public int Z() {
        int i = this.header.Uf;
        if (i == -1) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        return i + 1;
    }

    @Override // com.bumptech.glide.b.a
    public void a(@NonNull Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888 || config == Bitmap.Config.RGB_565) {
            this.ql = config;
            return;
        }
        throw new IllegalArgumentException("Unsupported format: " + config + ", must be one of " + Bitmap.Config.ARGB_8888 + " or " + Bitmap.Config.RGB_565);
    }

    @Override // com.bumptech.glide.b.a
    public synchronized void a(@NonNull c cVar, @NonNull ByteBuffer byteBuffer) {
        a(cVar, byteBuffer, 1);
    }

    @Override // com.bumptech.glide.b.a
    public synchronized void a(@NonNull c cVar, @NonNull ByteBuffer byteBuffer, int i) {
        if (i > 0) {
            int highestOneBit = Integer.highestOneBit(i);
            this.status = 0;
            this.header = cVar;
            this.kl = -1;
            this.Jk = byteBuffer.asReadOnlyBuffer();
            this.Jk.position(0);
            this.Jk.order(ByteOrder.LITTLE_ENDIAN);
            this.ml = false;
            Iterator<b> it = cVar.frames.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().sk == 3) {
                        this.ml = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            this.sampleSize = highestOneBit;
            this.ol = cVar.width / highestOneBit;
            this.nl = cVar.height / highestOneBit;
            this.il = this.fl.f(cVar.width * cVar.height);
            this.jl = this.fl.h(this.ol * this.nl);
        } else {
            throw new IllegalArgumentException("Sample size must be >=0, not: " + i);
        }
    }

    @Override // com.bumptech.glide.b.a
    public synchronized void a(@NonNull c cVar, @NonNull byte[] bArr) {
        a(cVar, ByteBuffer.wrap(bArr));
    }

    @Override // com.bumptech.glide.b.a
    public void advance() {
        this.kl = (this.kl + 1) % this.header.frameCount;
    }

    @Override // com.bumptech.glide.b.a
    public int ca() {
        int i;
        if (this.header.frameCount <= 0 || (i = this.kl) < 0) {
            return 0;
        }
        return getDelay(i);
    }

    @Override // com.bumptech.glide.b.a
    public void clear() {
        this.header = null;
        byte[] bArr = this.il;
        if (bArr != null) {
            this.fl.g(bArr);
        }
        int[] iArr = this.jl;
        if (iArr != null) {
            this.fl.a(iArr);
        }
        Bitmap bitmap = this.ll;
        if (bitmap != null) {
            this.fl.d(bitmap);
        }
        this.ll = null;
        this.Jk = null;
        this.pl = null;
        byte[] bArr2 = this.Ik;
        if (bArr2 != null) {
            this.fl.g(bArr2);
        }
    }

    @Override // com.bumptech.glide.b.a
    @NonNull
    public ByteBuffer getData() {
        return this.Jk;
    }

    @Override // com.bumptech.glide.b.a
    public int getDelay(int i) {
        if (i >= 0) {
            c cVar = this.header;
            if (i < cVar.frameCount) {
                return cVar.frames.get(i).delay;
            }
        }
        return -1;
    }

    @Override // com.bumptech.glide.b.a
    public int getFrameCount() {
        return this.header.frameCount;
    }

    @Override // com.bumptech.glide.b.a
    public int getHeight() {
        return this.header.height;
    }

    @Override // com.bumptech.glide.b.a
    @Deprecated
    public int getLoopCount() {
        int i = this.header.Uf;
        if (i == -1) {
            return 1;
        }
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00e9, code lost:
        return null;
     */
    @Override // com.bumptech.glide.b.a
    @Nullable
    public synchronized Bitmap getNextFrame() {
        if (this.header.frameCount <= 0 || this.kl < 0) {
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                Log.d(str, "Unable to decode frame, frameCount=" + this.header.frameCount + ", framePointer=" + this.kl);
            }
            this.status = 1;
        }
        if (this.status != 1) {
            if (this.status != 2) {
                this.status = 0;
                if (this.Ik == null) {
                    this.Ik = this.fl.f(255);
                }
                b bVar = this.header.frames.get(this.kl);
                int i = this.kl - 1;
                b bVar2 = i >= 0 ? this.header.frames.get(i) : null;
                this.dl = bVar.vk != null ? bVar.vk : this.header.Ak;
                if (this.dl == null) {
                    if (Log.isLoggable(TAG, 3)) {
                        String str2 = TAG;
                        Log.d(str2, "No valid color table found for frame #" + this.kl);
                    }
                    this.status = 1;
                    return null;
                }
                if (bVar.rk) {
                    System.arraycopy(this.dl, 0, this.el, 0, this.dl.length);
                    this.dl = this.el;
                    this.dl[bVar.tk] = 0;
                }
                return a(bVar, bVar2);
            }
        }
        if (Log.isLoggable(TAG, 3)) {
            String str3 = TAG;
            Log.d(str3, "Unable to decode frame, status=" + this.status);
        }
    }

    @Override // com.bumptech.glide.b.a
    public int getStatus() {
        return this.status;
    }

    @Override // com.bumptech.glide.b.a
    public int getWidth() {
        return this.header.width;
    }

    @Override // com.bumptech.glide.b.a
    public int ha() {
        return this.header.Uf;
    }

    @Override // com.bumptech.glide.b.a
    public int read(@Nullable InputStream inputStream, int i) {
        if (inputStream != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i > 0 ? i + 4096 : 16384);
                byte[] bArr = new byte[16384];
                while (true) {
                    int read = inputStream.read(bArr, 0, bArr.length);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.flush();
                read(byteArrayOutputStream.toByteArray());
            } catch (IOException e2) {
                Log.w(TAG, "Error reading data from stream", e2);
            }
        } else {
            this.status = 2;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e3) {
                Log.w(TAG, "Error closing stream", e3);
            }
        }
        return this.status;
    }

    @Override // com.bumptech.glide.b.a
    public synchronized int read(@Nullable byte[] bArr) {
        this.header = cn().setData(bArr).dj();
        if (bArr != null) {
            a(this.header, bArr);
        }
        return this.status;
    }
}
