package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

final class DecodedBitStreamParser {
    private static final int AL = 28;
    private static final int AS = 27;
    private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
    private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
    private static final int BYTE_COMPACTION_MODE_LATCH = 901;
    private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final BigInteger[] EXP900;
    private static final int LL = 27;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_ADDRESSEE = 4;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_CHECKSUM = 6;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_FILE_NAME = 0;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_FILE_SIZE = 5;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_SEGMENT_COUNT = 1;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_SENDER = 3;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_TIME_STAMP = 2;
    private static final int MACRO_PDF417_TERMINATOR = 922;
    private static final int MAX_NUMERIC_CODEWORDS = 15;
    private static final char[] MIXED_CHARS = "0123456789&\r\t,:#-.$/+%*=^".toCharArray();
    private static final int ML = 28;
    private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
    private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
    private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
    private static final int PAL = 29;
    private static final int PL = 25;
    private static final int PS = 29;
    private static final char[] PUNCT_CHARS = ";<>@[\\]_`~!\r\t,:\n-.$/\"|*()?{}'".toCharArray();
    private static final int TEXT_COMPACTION_MODE_LATCH = 900;

    /* renamed from: com.google.zxing.pdf417.decoder.DecodedBitStreamParser$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode = new int[Mode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|(3:11|12|14)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.ALPHA.ordinal()] = 1;
            $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.LOWER.ordinal()] = 2;
            $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.MIXED.ordinal()] = 3;
            $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.PUNCT.ordinal()] = 4;
            $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.ALPHA_SHIFT.ordinal()] = 5;
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.PUNCT_SHIFT.ordinal()] = 6;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    private enum Mode {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT
    }

    static {
        BigInteger[] bigIntegerArr = new BigInteger[16];
        EXP900 = bigIntegerArr;
        bigIntegerArr[0] = BigInteger.ONE;
        BigInteger valueOf = BigInteger.valueOf(900);
        EXP900[1] = valueOf;
        int i = 2;
        while (true) {
            BigInteger[] bigIntegerArr2 = EXP900;
            if (i < bigIntegerArr2.length) {
                bigIntegerArr2[i] = bigIntegerArr2[i - 1].multiply(valueOf);
                i++;
            } else {
                return;
            }
        }
    }

    private DecodedBitStreamParser() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0045 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0021 A[ADDED_TO_REGION, SYNTHETIC] */
    private static int byteCompaction(int i, int[] iArr, Charset charset, int i2, StringBuilder sb) {
        int i3;
        int i4;
        long j;
        int i5;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        long j2 = 900;
        int i6 = 6;
        if (i == 901) {
            int[] iArr2 = new int[6];
            int i7 = i2 + 1;
            int i8 = iArr[i2];
            boolean z = false;
            int i9 = 0;
            while (true) {
                long j3 = 0;
                while (i5 < iArr[0] && !z) {
                    int i10 = i4 + 1;
                    iArr2[i4] = i8;
                    j3 = (j3 * j) + ((long) i8);
                    int i11 = i5 + 1;
                    i8 = iArr[i5];
                    if (i8 != 928) {
                        switch (i8) {
                            case 900:
                            case 901:
                            case 902:
                                break;
                            default:
                                switch (i8) {
                                    case MACRO_PDF417_TERMINATOR /*{ENCODED_INT: 922}*/:
                                    case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*{ENCODED_INT: 923}*/:
                                    case BYTE_COMPACTION_MODE_LATCH_6 /*{ENCODED_INT: 924}*/:
                                        break;
                                    default:
                                        if (i10 % 5 != 0 || i10 <= 0) {
                                            z = z;
                                            i5 = i11;
                                            i4 = i10;
                                            j = 900;
                                            i6 = 6;
                                        } else {
                                            int i12 = 0;
                                            while (i12 < i6) {
                                                byteArrayOutputStream.write((byte) ((int) (j3 >> ((5 - i12) * 8))));
                                                i12++;
                                                i6 = 6;
                                                z = z;
                                            }
                                            i7 = i11;
                                            i9 = 0;
                                            j2 = 900;
                                        }
                                }
                                break;
                        }
                    }
                    i5 = i11 - 1;
                    i4 = i10;
                    j = 900;
                    i6 = 6;
                    z = true;
                }
            }
            if (i5 == iArr[0] && i8 < 900) {
                iArr2[i4] = i8;
                i4++;
            }
            for (int i13 = 0; i13 < i4; i13++) {
                byteArrayOutputStream.write((byte) iArr2[i13]);
            }
            i3 = i5;
        } else if (i != BYTE_COMPACTION_MODE_LATCH_6) {
            i3 = i2;
        } else {
            i3 = i2;
            boolean z2 = false;
            int i14 = 0;
            while (true) {
                long j4 = 0;
                while (i3 < iArr[0] && !z2) {
                    int i15 = i3 + 1;
                    int i16 = iArr[i3];
                    if (i16 < 900) {
                        i14++;
                        j4 = (j4 * 900) + ((long) i16);
                    } else {
                        if (i16 != 928) {
                            switch (i16) {
                                default:
                                    switch (i16) {
                                    }
                                case 900:
                                case 901:
                                case 902:
                                    i3 = i15 - 1;
                                    z2 = true;
                                    break;
                            }
                            if (i14 % 5 == 0 && i14 > 0) {
                                for (int i17 = 0; i17 < 6; i17++) {
                                    byteArrayOutputStream.write((byte) ((int) (j4 >> ((5 - i17) * 8))));
                                }
                                i14 = 0;
                            }
                        }
                        i3 = i15 - 1;
                        z2 = true;
                        if (i14 % 5 == 0) {
                        }
                    }
                    i3 = i15;
                    if (i14 % 5 == 0) {
                    }
                }
            }
        }
        sb.append(new String(byteArrayOutputStream.toByteArray(), charset));
        return i3;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004e  */
    static DecoderResult decode(int[] iArr, String str) throws FormatException {
        int i;
        StringBuilder sb = new StringBuilder(iArr.length << 1);
        Charset charset = StandardCharsets.ISO_8859_1;
        int i2 = iArr[1];
        PDF417ResultMetadata pDF417ResultMetadata = new PDF417ResultMetadata();
        int i3 = 2;
        while (i3 < iArr[0]) {
            if (i2 != MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                switch (i2) {
                    case 900:
                        i = textCompaction(iArr, i3, sb);
                        break;
                    case 901:
                        i = byteCompaction(i2, iArr, charset, i3, sb);
                        break;
                    case 902:
                        i = numericCompaction(iArr, i3, sb);
                        break;
                    default:
                        switch (i2) {
                            case MACRO_PDF417_TERMINATOR /*{ENCODED_INT: 922}*/:
                            case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*{ENCODED_INT: 923}*/:
                                throw FormatException.getFormatInstance();
                            case BYTE_COMPACTION_MODE_LATCH_6 /*{ENCODED_INT: 924}*/:
                                break;
                            case ECI_USER_DEFINED /*{ENCODED_INT: 925}*/:
                                i = i3 + 1;
                                break;
                            case ECI_GENERAL_PURPOSE /*{ENCODED_INT: 926}*/:
                                i = i3 + 2;
                                break;
                            case ECI_CHARSET /*{ENCODED_INT: 927}*/:
                                i = i3 + 1;
                                charset = Charset.forName(CharacterSetECI.getCharacterSetECIByValue(iArr[i3]).name());
                                break;
                            case 928:
                                i = decodeMacroBlock(iArr, i3, pDF417ResultMetadata);
                                break;
                            default:
                                i = textCompaction(iArr, i3 - 1, sb);
                                break;
                        }
                }
            } else {
                i = i3 + 1;
                sb.append((char) iArr[i3]);
            }
            if (i < iArr.length) {
                i3 = i + 1;
                i2 = iArr[i];
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (sb.length() != 0) {
            DecoderResult decoderResult = new DecoderResult(null, sb.toString(), null, str);
            decoderResult.setOther(pDF417ResultMetadata);
            return decoderResult;
        }
        throw FormatException.getFormatInstance();
    }

    private static String decodeBase900toBase10(int[] iArr, int i) throws FormatException {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i2 = 0; i2 < i; i2++) {
            bigInteger = bigInteger.add(EXP900[(i - i2) - 1].multiply(BigInteger.valueOf((long) iArr[i2])));
        }
        String bigInteger2 = bigInteger.toString();
        if (bigInteger2.charAt(0) == '1') {
            return bigInteger2.substring(1);
        }
        throw FormatException.getFormatInstance();
    }

    static int decodeMacroBlock(int[] iArr, int i, PDF417ResultMetadata pDF417ResultMetadata) throws FormatException {
        if (i + 2 <= iArr[0]) {
            int[] iArr2 = new int[2];
            int i2 = i;
            int i3 = 0;
            while (i3 < 2) {
                iArr2[i3] = iArr[i2];
                i3++;
                i2++;
            }
            pDF417ResultMetadata.setSegmentIndex(Integer.parseInt(decodeBase900toBase10(iArr2, 2)));
            StringBuilder sb = new StringBuilder();
            int textCompaction = textCompaction(iArr, i2, sb);
            pDF417ResultMetadata.setFileId(sb.toString());
            int i4 = iArr[textCompaction] == BEGIN_MACRO_PDF417_OPTIONAL_FIELD ? textCompaction + 1 : -1;
            while (textCompaction < iArr[0]) {
                int i5 = iArr[textCompaction];
                if (i5 == MACRO_PDF417_TERMINATOR) {
                    textCompaction++;
                    pDF417ResultMetadata.setLastSegment(true);
                } else if (i5 == BEGIN_MACRO_PDF417_OPTIONAL_FIELD) {
                    int i6 = textCompaction + 1;
                    switch (iArr[i6]) {
                        case 0:
                            StringBuilder sb2 = new StringBuilder();
                            textCompaction = textCompaction(iArr, i6 + 1, sb2);
                            pDF417ResultMetadata.setFileName(sb2.toString());
                            continue;
                        case 1:
                            StringBuilder sb3 = new StringBuilder();
                            textCompaction = numericCompaction(iArr, i6 + 1, sb3);
                            pDF417ResultMetadata.setSegmentCount(Integer.parseInt(sb3.toString()));
                            continue;
                        case 2:
                            StringBuilder sb4 = new StringBuilder();
                            textCompaction = numericCompaction(iArr, i6 + 1, sb4);
                            pDF417ResultMetadata.setTimestamp(Long.parseLong(sb4.toString()));
                            continue;
                        case 3:
                            StringBuilder sb5 = new StringBuilder();
                            textCompaction = textCompaction(iArr, i6 + 1, sb5);
                            pDF417ResultMetadata.setSender(sb5.toString());
                            continue;
                        case 4:
                            StringBuilder sb6 = new StringBuilder();
                            textCompaction = textCompaction(iArr, i6 + 1, sb6);
                            pDF417ResultMetadata.setAddressee(sb6.toString());
                            continue;
                        case 5:
                            StringBuilder sb7 = new StringBuilder();
                            textCompaction = numericCompaction(iArr, i6 + 1, sb7);
                            pDF417ResultMetadata.setFileSize(Long.parseLong(sb7.toString()));
                            continue;
                        case 6:
                            StringBuilder sb8 = new StringBuilder();
                            textCompaction = numericCompaction(iArr, i6 + 1, sb8);
                            pDF417ResultMetadata.setChecksum(Integer.parseInt(sb8.toString()));
                            continue;
                        default:
                            throw FormatException.getFormatInstance();
                    }
                } else {
                    throw FormatException.getFormatInstance();
                }
            }
            if (i4 != -1) {
                int i7 = textCompaction - i4;
                if (pDF417ResultMetadata.isLastSegment()) {
                    i7--;
                }
                pDF417ResultMetadata.setOptionalData(Arrays.copyOfRange(iArr, i4, i7 + i4));
            }
            return textCompaction;
        }
        throw FormatException.getFormatInstance();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00d1 A[PHI: r2 
      PHI: (r2v4 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode) = (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode), (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode), (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode), (r2v17 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode) binds: [B:54:0x00c0, B:43:0x009f, B:33:0x007c, B:20:0x004f] A[DONT_GENERATE, DONT_INLINE]] */
    private static void decodeTextCompaction(int[] iArr, int[] iArr2, int i, StringBuilder sb) {
        char c2;
        Mode mode;
        int i2;
        char c3;
        Mode mode2 = Mode.ALPHA;
        Mode mode3 = mode2;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr[i3];
            switch (AnonymousClass1.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[mode2.ordinal()]) {
                case 1:
                    if (i4 >= 26) {
                        if (i4 == 900) {
                            mode2 = Mode.ALPHA;
                        } else if (i4 != MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                            switch (i4) {
                                case 26:
                                    c2 = ' ';
                                    break;
                                case 27:
                                    mode2 = Mode.LOWER;
                                    break;
                                case 28:
                                    mode2 = Mode.MIXED;
                                    break;
                                case 29:
                                    mode = Mode.PUNCT_SHIFT;
                                    c2 = 0;
                                    mode3 = mode2;
                                    mode2 = mode;
                                    break;
                            }
                        } else {
                            sb.append((char) iArr2[i3]);
                        }
                        c2 = 0;
                        break;
                    } else {
                        i2 = i4 + 65;
                        c2 = (char) i2;
                        break;
                    }
                case 2:
                    if (i4 >= 26) {
                        if (i4 == 900) {
                            mode2 = Mode.ALPHA;
                        } else if (i4 != MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                            switch (i4) {
                                case 27:
                                    mode = Mode.ALPHA_SHIFT;
                                    c2 = 0;
                                    mode3 = mode2;
                                    mode2 = mode;
                                    break;
                                case 28:
                                    mode2 = Mode.MIXED;
                                    break;
                                case 29:
                                    mode = Mode.PUNCT_SHIFT;
                                    c2 = 0;
                                    mode3 = mode2;
                                    mode2 = mode;
                                    break;
                            }
                        } else {
                            sb.append((char) iArr2[i3]);
                        }
                        c2 = 0;
                        break;
                    } else {
                        i2 = i4 + 97;
                        c2 = (char) i2;
                        break;
                    }
                case 3:
                    if (i4 < 25) {
                        c2 = MIXED_CHARS[i4];
                        break;
                    } else {
                        if (i4 != 900) {
                            if (i4 != MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                                switch (i4) {
                                    case 25:
                                        mode2 = Mode.PUNCT;
                                        break;
                                    case 27:
                                        mode2 = Mode.LOWER;
                                        break;
                                    case 29:
                                        mode = Mode.PUNCT_SHIFT;
                                        c2 = 0;
                                        mode3 = mode2;
                                        mode2 = mode;
                                        break;
                                }
                            } else {
                                sb.append((char) iArr2[i3]);
                            }
                            c2 = 0;
                            break;
                        }
                        mode2 = Mode.ALPHA;
                        c2 = 0;
                    }
                    break;
                case 4:
                    if (i4 < 29) {
                        c2 = PUNCT_CHARS[i4];
                        break;
                    } else if (i4 == 29 || i4 == 900) {
                        mode2 = Mode.ALPHA;
                        c2 = 0;
                        break;
                    } else {
                        if (i4 == MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                            sb.append((char) iArr2[i3]);
                        }
                        c2 = 0;
                    }
                case 5:
                    if (i4 >= 26) {
                        if (i4 == 26) {
                            mode2 = mode3;
                            c2 = ' ';
                            break;
                        } else {
                            mode2 = i4 != 900 ? mode3 : Mode.ALPHA;
                            c2 = 0;
                            break;
                        }
                    } else {
                        c3 = (char) (i4 + 65);
                        c2 = c3;
                        mode2 = mode3;
                        break;
                    }
                case 6:
                    if (i4 < 29) {
                        c3 = PUNCT_CHARS[i4];
                        c2 = c3;
                        mode2 = mode3;
                        break;
                    } else if (i4 == 29 || i4 == 900) {
                        mode2 = Mode.ALPHA;
                        c2 = 0;
                        break;
                    } else {
                        if (i4 == MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                            sb.append((char) iArr2[i3]);
                        }
                        c2 = 0;
                        mode2 = mode3;
                    }
                default:
                    c2 = 0;
                    break;
            }
            if (c2 != 0) {
                sb.append(c2);
            }
        }
    }

    private static int numericCompaction(int[] iArr, int i, StringBuilder sb) throws FormatException {
        int[] iArr2 = new int[15];
        boolean z = false;
        int i2 = 0;
        while (i < iArr[0] && !z) {
            int i3 = i + 1;
            int i4 = iArr[i];
            if (i3 == iArr[0]) {
                z = true;
            }
            if (i4 < 900) {
                iArr2[i2] = i4;
                i2++;
            } else {
                if (!(i4 == 900 || i4 == 901 || i4 == 928)) {
                    switch (i4) {
                    }
                }
                i3--;
                z = true;
            }
            if ((i2 % 15 == 0 || i4 == 902 || z) && i2 > 0) {
                sb.append(decodeBase900toBase10(iArr2, i2));
                i2 = 0;
            }
            i = i3;
        }
        return i;
    }

    private static int textCompaction(int[] iArr, int i, StringBuilder sb) {
        int[] iArr2 = new int[((iArr[0] - i) << 1)];
        int[] iArr3 = new int[((iArr[0] - i) << 1)];
        boolean z = false;
        int i2 = 0;
        while (i < iArr[0] && !z) {
            int i3 = i + 1;
            int i4 = iArr[i];
            if (i4 < 900) {
                iArr2[i2] = i4 / 30;
                iArr2[i2 + 1] = i4 % 30;
                i2 += 2;
            } else if (i4 != MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                if (i4 != 928) {
                    switch (i4) {
                        case 900:
                            iArr2[i2] = 900;
                            i2++;
                            break;
                        default:
                            switch (i4) {
                            }
                        case 901:
                        case 902:
                            i = i3 - 1;
                            z = true;
                            break;
                    }
                }
                i = i3 - 1;
                z = true;
            } else {
                iArr2[i2] = MODE_SHIFT_TO_BYTE_COMPACTION_MODE;
                i = i3 + 1;
                iArr3[i2] = iArr[i3];
                i2++;
            }
            i = i3;
        }
        decodeTextCompaction(iArr2, iArr3, i2, sb);
        return i;
    }
}
