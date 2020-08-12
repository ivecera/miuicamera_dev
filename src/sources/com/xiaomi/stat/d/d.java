package com.xiaomi.stat.d;

import java.io.UnsupportedEncodingException;
import org.jcodec.platform.Platform;

public class d {

    /* renamed from: a  reason: collision with root package name */
    private static final String f539a = "Base64Utils";

    /* renamed from: b  reason: collision with root package name */
    private static char[] f540b = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    /* renamed from: c  reason: collision with root package name */
    private static byte[] f541c = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

    public static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int i2 = i + 1;
            byte b2 = bArr[i] & 255;
            if (i2 == length) {
                stringBuffer.append(f540b[b2 >>> 2]);
                stringBuffer.append(f540b[(b2 & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i3 = i2 + 1;
            byte b3 = bArr[i2] & 255;
            if (i3 == length) {
                stringBuffer.append(f540b[b2 >>> 2]);
                stringBuffer.append(f540b[((b2 & 3) << 4) | ((b3 & 240) >>> 4)]);
                stringBuffer.append(f540b[(b3 & 15) << 2]);
                stringBuffer.append("=");
                break;
            }
            int i4 = i3 + 1;
            byte b4 = bArr[i3] & 255;
            stringBuffer.append(f540b[b2 >>> 2]);
            stringBuffer.append(f540b[((b2 & 3) << 4) | ((b3 & 240) >>> 4)]);
            stringBuffer.append(f540b[((b3 & 15) << 2) | ((b4 & 192) >>> 6)]);
            stringBuffer.append(f540b[b4 & 63]);
            i = i4;
        }
        return stringBuffer.toString();
    }

    public static byte[] a(String str) {
        try {
            return b(str);
        } catch (UnsupportedEncodingException e2) {
            k.d(f539a, "decode e", e2);
            return new byte[0];
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x008b A[LOOP:0: B:1:0x000d->B:33:0x008b, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0097 A[EDGE_INSN: B:36:0x0097->B:34:0x0097 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0097 A[EDGE_INSN: B:39:0x0097->B:34:0x0097 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0097 A[EDGE_INSN: B:40:0x0097->B:34:0x0097 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0097 A[EDGE_INSN: B:42:0x0097->B:34:0x0097 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025 A[LOOP:2: B:8:0x0025->B:11:0x0032, LOOP_START, PHI: r5 
      PHI: (r5v1 int) = (r5v0 int), (r5v9 int) binds: [B:7:0x0021, B:11:0x0032] A[DONT_GENERATE, DONT_INLINE]] */
    private static byte[] b(String str) throws UnsupportedEncodingException {
        byte b2;
        byte b3;
        byte b4;
        byte b5;
        StringBuffer stringBuffer = new StringBuffer();
        byte[] bytes = str.getBytes("US-ASCII");
        int length = bytes.length;
        int i = 0;
        while (i < length) {
            while (true) {
                int i2 = i + 1;
                b2 = f541c[bytes[i]];
                if (i2 < length && b2 == -1) {
                    i = i2;
                } else if (b2 != -1) {
                    break;
                } else {
                    while (true) {
                        int i3 = i2 + 1;
                        b3 = f541c[bytes[i2]];
                        if (i3 < length && b3 == -1) {
                            i2 = i3;
                        } else if (b3 != -1) {
                            break;
                        } else {
                            stringBuffer.append((char) ((b2 << 2) | ((b3 & 48) >>> 4)));
                            while (true) {
                                int i4 = i3 + 1;
                                byte b6 = bytes[i3];
                                if (b6 == 61) {
                                    return stringBuffer.toString().getBytes(Platform.ISO8859_1);
                                }
                                b4 = f541c[b6];
                                if (i4 < length && b4 == -1) {
                                    i3 = i4;
                                } else if (b4 != -1) {
                                    break;
                                } else {
                                    stringBuffer.append((char) (((b3 & 15) << 4) | ((b4 & 60) >>> 2)));
                                    while (true) {
                                        int i5 = i4 + 1;
                                        byte b7 = bytes[i4];
                                        if (b7 == 61) {
                                            return stringBuffer.toString().getBytes(Platform.ISO8859_1);
                                        }
                                        b5 = f541c[b7];
                                        if (i5 < length && b5 == -1) {
                                            i4 = i5;
                                        } else if (b5 != -1) {
                                            break;
                                        } else {
                                            stringBuffer.append((char) (b5 | ((b4 & 3) << 6)));
                                            i = i5;
                                        }
                                    }
                                    if (b5 != -1) {
                                    }
                                }
                            }
                            if (b4 != -1) {
                            }
                        }
                    }
                    if (b3 != -1) {
                    }
                }
            }
            if (b2 != -1) {
            }
        }
        return stringBuffer.toString().getBytes(Platform.ISO8859_1);
    }
}
