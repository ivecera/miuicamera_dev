package com.bumptech.glide.load.resource.bitmap;

import android.support.annotation.NonNull;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.util.i;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/* compiled from: DefaultImageHeaderParser */
public final class m implements ImageHeaderParser {
    static final int Aq = 65496;
    private static final int Bq = 19789;
    private static final int Cq = 18761;
    private static final String Dq = "Exif\u0000\u0000";
    static final byte[] Eq = Dq.getBytes(Charset.forName("UTF-8"));
    private static final int Fq = 218;
    private static final int Gq = 217;
    static final int Hq = 255;
    static final int Iq = 225;
    private static final int Jq = 274;
    private static final int[] Kq = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
    private static final int Lq = 1380533830;
    private static final int Mq = 1464156752;
    private static final int Nq = 1448097792;
    private static final int Oq = -256;
    private static final int Pq = 255;
    private static final int Qq = 88;
    private static final int Rq = 76;
    private static final int Sq = 16;
    private static final String TAG = "DfltImageHeaderParser";
    private static final int Tq = 8;
    private static final int yq = 4671814;
    private static final int zq = -1991225785;

    /* compiled from: DefaultImageHeaderParser */
    private static final class a implements c {
        private final ByteBuffer byteBuffer;

        a(ByteBuffer byteBuffer2) {
            this.byteBuffer = byteBuffer2;
            byteBuffer2.order(ByteOrder.BIG_ENDIAN);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.m.c
        public int V() {
            return (getByte() & 255) | ((getByte() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.m.c
        public int a(byte[] bArr, int i) {
            int min = Math.min(i, this.byteBuffer.remaining());
            if (min == 0) {
                return -1;
            }
            this.byteBuffer.get(bArr, 0, min);
            return min;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.m.c
        public short fa() {
            return (short) (getByte() & 255);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.m.c
        public int getByte() {
            if (this.byteBuffer.remaining() < 1) {
                return -1;
            }
            return this.byteBuffer.get();
        }

        @Override // com.bumptech.glide.load.resource.bitmap.m.c
        public long skip(long j) {
            int min = (int) Math.min((long) this.byteBuffer.remaining(), j);
            ByteBuffer byteBuffer2 = this.byteBuffer;
            byteBuffer2.position(byteBuffer2.position() + min);
            return (long) min;
        }
    }

    /* compiled from: DefaultImageHeaderParser */
    private static final class b {
        private final ByteBuffer data;

        b(byte[] bArr, int i) {
            this.data = (ByteBuffer) ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).limit(i);
        }

        private boolean isAvailable(int i, int i2) {
            return this.data.remaining() - i >= i2;
        }

        /* access modifiers changed from: package-private */
        public short B(int i) {
            if (isAvailable(i, 2)) {
                return this.data.getShort(i);
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public int C(int i) {
            if (isAvailable(i, 4)) {
                return this.data.getInt(i);
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public int length() {
            return this.data.remaining();
        }

        /* access modifiers changed from: package-private */
        public void order(ByteOrder byteOrder) {
            this.data.order(byteOrder);
        }
    }

    /* compiled from: DefaultImageHeaderParser */
    private interface c {
        int V() throws IOException;

        int a(byte[] bArr, int i) throws IOException;

        short fa() throws IOException;

        int getByte() throws IOException;

        long skip(long j) throws IOException;
    }

    /* compiled from: DefaultImageHeaderParser */
    private static final class d implements c {
        private final InputStream xq;

        d(InputStream inputStream) {
            this.xq = inputStream;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.m.c
        public int V() throws IOException {
            return (this.xq.read() & 255) | ((this.xq.read() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.m.c
        public int a(byte[] bArr, int i) throws IOException {
            int i2 = i;
            while (i2 > 0) {
                int read = this.xq.read(bArr, i - i2, i2);
                if (read == -1) {
                    break;
                }
                i2 -= read;
            }
            return i - i2;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.m.c
        public short fa() throws IOException {
            return (short) (this.xq.read() & 255);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.m.c
        public int getByte() throws IOException {
            return this.xq.read();
        }

        @Override // com.bumptech.glide.load.resource.bitmap.m.c
        public long skip(long j) throws IOException {
            if (j < 0) {
                return 0;
            }
            long j2 = j;
            while (j2 > 0) {
                long skip = this.xq.skip(j2);
                if (skip <= 0) {
                    if (this.xq.read() == -1) {
                        break;
                    }
                    skip = 1;
                }
                j2 -= skip;
            }
            return j - j2;
        }
    }

    private static int a(b bVar) {
        ByteOrder byteOrder;
        short B = bVar.B(6);
        if (B == Cq) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else if (B != Bq) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unknown endianness = " + ((int) B));
            }
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else {
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        bVar.order(byteOrder);
        int C = bVar.C(10) + 6;
        short B2 = bVar.B(C);
        for (int i = 0; i < B2; i++) {
            int q = q(C, i);
            short B3 = bVar.B(q);
            if (B3 == 274) {
                short B4 = bVar.B(q + 2);
                if (B4 >= 1 && B4 <= 12) {
                    int C2 = bVar.C(q + 4);
                    if (C2 >= 0) {
                        if (Log.isLoggable(TAG, 3)) {
                            Log.d(TAG, "Got tagIndex=" + i + " tagType=" + ((int) B3) + " formatCode=" + ((int) B4) + " componentCount=" + C2);
                        }
                        int i2 = C2 + Kq[B4];
                        if (i2 <= 4) {
                            int i3 = q + 8;
                            if (i3 < 0 || i3 > bVar.length()) {
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal tagValueOffset=" + i3 + " tagType=" + ((int) B3));
                                }
                            } else if (i2 >= 0 && i2 + i3 <= bVar.length()) {
                                return bVar.B(i3);
                            } else {
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal number of bytes for TI tag data tagType=" + ((int) B3));
                                }
                            }
                        } else if (Log.isLoggable(TAG, 3)) {
                            Log.d(TAG, "Got byte count > 4, not orientation, continuing, formatCode=" + ((int) B4));
                        }
                    } else if (Log.isLoggable(TAG, 3)) {
                        Log.d(TAG, "Negative tiff component count");
                    }
                } else if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Got invalid format code = " + ((int) B4));
                }
            }
        }
        return -1;
    }

    private int a(c cVar, com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        int V = cVar.V();
        if (!ca(V)) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Parser doesn't handle magic number: " + V);
            }
            return -1;
        }
        int b2 = b(cVar);
        if (b2 == -1) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to parse exif segment length, or exif segment not found");
            }
            return -1;
        }
        byte[] bArr = (byte[]) bVar.a(b2, byte[].class);
        try {
            return a(cVar, bArr, b2);
        } finally {
            bVar.put(bArr);
        }
    }

    private int a(c cVar, byte[] bArr, int i) throws IOException {
        int a2 = cVar.a(bArr, i);
        if (a2 != i) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unable to read exif segment data, length: " + i + ", actually read: " + a2);
            }
            return -1;
        } else if (d(bArr, i)) {
            return a(new b(bArr, i));
        } else {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Missing jpeg exif preamble");
            }
            return -1;
        }
    }

    @NonNull
    private ImageHeaderParser.ImageType a(c cVar) throws IOException {
        int V = cVar.V();
        if (V == Aq) {
            return ImageHeaderParser.ImageType.JPEG;
        }
        int V2 = ((V << 16) & SupportMenu.CATEGORY_MASK) | (cVar.V() & 65535);
        if (V2 == zq) {
            cVar.skip(21);
            return cVar.getByte() >= 3 ? ImageHeaderParser.ImageType.PNG_A : ImageHeaderParser.ImageType.PNG;
        } else if ((V2 >> 8) == yq) {
            return ImageHeaderParser.ImageType.GIF;
        } else {
            if (V2 != Lq) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            cVar.skip(4);
            if ((((cVar.V() << 16) & SupportMenu.CATEGORY_MASK) | (cVar.V() & 65535)) != Mq) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            int V3 = ((cVar.V() << 16) & SupportMenu.CATEGORY_MASK) | (cVar.V() & 65535);
            if ((V3 & -256) != Nq) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            int i = V3 & 255;
            if (i == 88) {
                cVar.skip(4);
                return (cVar.getByte() & 16) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
            } else if (i != 76) {
                return ImageHeaderParser.ImageType.WEBP;
            } else {
                cVar.skip(4);
                return (cVar.getByte() & 8) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
            }
        }
    }

    private int b(c cVar) throws IOException {
        short fa;
        int V;
        long j;
        long skip;
        do {
            short fa2 = cVar.fa();
            if (fa2 != 255) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Unknown segmentId=" + ((int) fa2));
                }
                return -1;
            }
            fa = cVar.fa();
            if (fa == 218) {
                return -1;
            }
            if (fa == 217) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Found MARKER_EOI in exif segment");
                }
                return -1;
            }
            V = cVar.V() - 2;
            if (fa == 225) {
                return V;
            }
            j = (long) V;
            skip = cVar.skip(j);
        } while (skip == j);
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Unable to skip enough data, type: " + ((int) fa) + ", wanted to skip: " + V + ", but actually skipped: " + skip);
        }
        return -1;
    }

    private static boolean ca(int i) {
        return (i & Aq) == Aq || i == Bq || i == Cq;
    }

    private boolean d(byte[] bArr, int i) {
        boolean z = bArr != null && i > Eq.length;
        if (z) {
            int i2 = 0;
            while (true) {
                byte[] bArr2 = Eq;
                if (i2 >= bArr2.length) {
                    break;
                } else if (bArr[i2] != bArr2[i2]) {
                    return false;
                } else {
                    i2++;
                }
            }
        }
        return z;
    }

    private static int q(int i, int i2) {
        return i + 2 + (i2 * 12);
    }

    @Override // com.bumptech.glide.load.ImageHeaderParser
    public int a(@NonNull InputStream inputStream, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        i.checkNotNull(inputStream);
        d dVar = new d(inputStream);
        i.checkNotNull(bVar);
        return a(dVar, bVar);
    }

    @Override // com.bumptech.glide.load.ImageHeaderParser
    public int a(@NonNull ByteBuffer byteBuffer, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        i.checkNotNull(byteBuffer);
        a aVar = new a(byteBuffer);
        i.checkNotNull(bVar);
        return a(aVar, bVar);
    }

    @Override // com.bumptech.glide.load.ImageHeaderParser
    @NonNull
    public ImageHeaderParser.ImageType a(@NonNull ByteBuffer byteBuffer) throws IOException {
        i.checkNotNull(byteBuffer);
        return a(new a(byteBuffer));
    }

    @Override // com.bumptech.glide.load.ImageHeaderParser
    @NonNull
    public ImageHeaderParser.ImageType c(@NonNull InputStream inputStream) throws IOException {
        i.checkNotNull(inputStream);
        return a(new d(inputStream));
    }
}
