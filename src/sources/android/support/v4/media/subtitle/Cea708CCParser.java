package android.support.v4.media.subtitle;

import android.graphics.Color;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RequiresApi(28)
class Cea708CCParser {
    public static final int CAPTION_EMIT_TYPE_BUFFER = 1;
    public static final int CAPTION_EMIT_TYPE_COMMAND_CLW = 4;
    public static final int CAPTION_EMIT_TYPE_COMMAND_CWX = 3;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DFX = 16;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLC = 10;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLW = 8;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLY = 9;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DSW = 5;
    public static final int CAPTION_EMIT_TYPE_COMMAND_HDW = 6;
    public static final int CAPTION_EMIT_TYPE_COMMAND_RST = 11;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPA = 12;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPC = 13;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPL = 14;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SWA = 15;
    public static final int CAPTION_EMIT_TYPE_COMMAND_TGW = 7;
    public static final int CAPTION_EMIT_TYPE_CONTROL = 2;
    private static final boolean DEBUG = false;
    private static final String MUSIC_NOTE_CHAR = new String("♫".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    private static final String TAG = "Cea708CCParser";
    private final StringBuilder mBuilder = new StringBuilder();
    private int mCommand = 0;
    private DisplayListener mListener = new DisplayListener() {
        /* class android.support.v4.media.subtitle.Cea708CCParser.AnonymousClass1 */

        @Override // android.support.v4.media.subtitle.Cea708CCParser.DisplayListener
        public void emitEvent(CaptionEvent captionEvent) {
        }
    };

    public static class CaptionColor {
        private static final int[] COLOR_MAP = {0, 15, 240, 255};
        public static final int OPACITY_FLASH = 1;
        private static final int[] OPACITY_MAP = {255, 254, 128, 0};
        public static final int OPACITY_SOLID = 0;
        public static final int OPACITY_TRANSLUCENT = 2;
        public static final int OPACITY_TRANSPARENT = 3;
        public final int blue;
        public final int green;
        public final int opacity;
        public final int red;

        CaptionColor(int i, int i2, int i3, int i4) {
            this.opacity = i;
            this.red = i2;
            this.green = i3;
            this.blue = i4;
        }

        public int getArgbValue() {
            int i = OPACITY_MAP[this.opacity];
            int[] iArr = COLOR_MAP;
            return Color.argb(i, iArr[this.red], iArr[this.green], iArr[this.blue]);
        }
    }

    public static class CaptionEvent {
        public final Object obj;
        public final int type;

        CaptionEvent(int i, Object obj2) {
            this.type = i;
            this.obj = obj2;
        }
    }

    public static class CaptionPenAttr {
        public static final int OFFSET_NORMAL = 1;
        public static final int OFFSET_SUBSCRIPT = 0;
        public static final int OFFSET_SUPERSCRIPT = 2;
        public static final int PEN_SIZE_LARGE = 2;
        public static final int PEN_SIZE_SMALL = 0;
        public static final int PEN_SIZE_STANDARD = 1;
        public final int edgeType;
        public final int fontTag;
        public final boolean italic;
        public final int penOffset;
        public final int penSize;
        public final int textTag;
        public final boolean underline;

        CaptionPenAttr(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
            this.penSize = i;
            this.penOffset = i2;
            this.textTag = i3;
            this.fontTag = i4;
            this.edgeType = i5;
            this.underline = z;
            this.italic = z2;
        }
    }

    public static class CaptionPenColor {
        public final CaptionColor backgroundColor;
        public final CaptionColor edgeColor;
        public final CaptionColor foregroundColor;

        CaptionPenColor(CaptionColor captionColor, CaptionColor captionColor2, CaptionColor captionColor3) {
            this.foregroundColor = captionColor;
            this.backgroundColor = captionColor2;
            this.edgeColor = captionColor3;
        }
    }

    public static class CaptionPenLocation {
        public final int column;
        public final int row;

        CaptionPenLocation(int i, int i2) {
            this.row = i;
            this.column = i2;
        }
    }

    public static class CaptionWindow {
        public final int anchorHorizontal;
        public final int anchorId;
        public final int anchorVertical;
        public final int columnCount;
        public final boolean columnLock;
        public final int id;
        public final int penStyle;
        public final int priority;
        public final boolean relativePositioning;
        public final int rowCount;
        public final boolean rowLock;
        public final boolean visible;
        public final int windowStyle;

        CaptionWindow(int i, boolean z, boolean z2, boolean z3, int i2, boolean z4, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.id = i;
            this.visible = z;
            this.rowLock = z2;
            this.columnLock = z3;
            this.priority = i2;
            this.relativePositioning = z4;
            this.anchorVertical = i3;
            this.anchorHorizontal = i4;
            this.anchorId = i5;
            this.rowCount = i6;
            this.columnCount = i7;
            this.penStyle = i8;
            this.windowStyle = i9;
        }
    }

    public static class CaptionWindowAttr {
        public final CaptionColor borderColor;
        public final int borderType;
        public final int displayEffect;
        public final int effectDirection;
        public final int effectSpeed;
        public final CaptionColor fillColor;
        public final int justify;
        public final int printDirection;
        public final int scrollDirection;
        public final boolean wordWrap;

        CaptionWindowAttr(CaptionColor captionColor, CaptionColor captionColor2, int i, boolean z, int i2, int i3, int i4, int i5, int i6, int i7) {
            this.fillColor = captionColor;
            this.borderColor = captionColor2;
            this.borderType = i;
            this.wordWrap = z;
            this.printDirection = i2;
            this.scrollDirection = i3;
            this.justify = i4;
            this.effectDirection = i5;
            this.effectSpeed = i6;
            this.displayEffect = i7;
        }
    }

    private static class Const {
        public static final int CODE_C0_BS = 8;
        public static final int CODE_C0_CR = 13;
        public static final int CODE_C0_ETX = 3;
        public static final int CODE_C0_EXT1 = 16;
        public static final int CODE_C0_FF = 12;
        public static final int CODE_C0_HCR = 14;
        public static final int CODE_C0_NUL = 0;
        public static final int CODE_C0_P16 = 24;
        public static final int CODE_C0_RANGE_END = 31;
        public static final int CODE_C0_RANGE_START = 0;
        public static final int CODE_C0_SKIP1_RANGE_END = 23;
        public static final int CODE_C0_SKIP1_RANGE_START = 16;
        public static final int CODE_C0_SKIP2_RANGE_END = 31;
        public static final int CODE_C0_SKIP2_RANGE_START = 24;
        public static final int CODE_C1_CLW = 136;
        public static final int CODE_C1_CW0 = 128;
        public static final int CODE_C1_CW1 = 129;
        public static final int CODE_C1_CW2 = 130;
        public static final int CODE_C1_CW3 = 131;
        public static final int CODE_C1_CW4 = 132;
        public static final int CODE_C1_CW5 = 133;
        public static final int CODE_C1_CW6 = 134;
        public static final int CODE_C1_CW7 = 135;
        public static final int CODE_C1_DF0 = 152;
        public static final int CODE_C1_DF1 = 153;
        public static final int CODE_C1_DF2 = 154;
        public static final int CODE_C1_DF3 = 155;
        public static final int CODE_C1_DF4 = 156;
        public static final int CODE_C1_DF5 = 157;
        public static final int CODE_C1_DF6 = 158;
        public static final int CODE_C1_DF7 = 159;
        public static final int CODE_C1_DLC = 142;
        public static final int CODE_C1_DLW = 140;
        public static final int CODE_C1_DLY = 141;
        public static final int CODE_C1_DSW = 137;
        public static final int CODE_C1_HDW = 138;
        public static final int CODE_C1_RANGE_END = 159;
        public static final int CODE_C1_RANGE_START = 128;
        public static final int CODE_C1_RST = 143;
        public static final int CODE_C1_SPA = 144;
        public static final int CODE_C1_SPC = 145;
        public static final int CODE_C1_SPL = 146;
        public static final int CODE_C1_SWA = 151;
        public static final int CODE_C1_TGW = 139;
        public static final int CODE_C2_RANGE_END = 31;
        public static final int CODE_C2_RANGE_START = 0;
        public static final int CODE_C2_SKIP0_RANGE_END = 7;
        public static final int CODE_C2_SKIP0_RANGE_START = 0;
        public static final int CODE_C2_SKIP1_RANGE_END = 15;
        public static final int CODE_C2_SKIP1_RANGE_START = 8;
        public static final int CODE_C2_SKIP2_RANGE_END = 23;
        public static final int CODE_C2_SKIP2_RANGE_START = 16;
        public static final int CODE_C2_SKIP3_RANGE_END = 31;
        public static final int CODE_C2_SKIP3_RANGE_START = 24;
        public static final int CODE_C3_RANGE_END = 159;
        public static final int CODE_C3_RANGE_START = 128;
        public static final int CODE_C3_SKIP4_RANGE_END = 135;
        public static final int CODE_C3_SKIP4_RANGE_START = 128;
        public static final int CODE_C3_SKIP5_RANGE_END = 143;
        public static final int CODE_C3_SKIP5_RANGE_START = 136;
        public static final int CODE_G0_MUSICNOTE = 127;
        public static final int CODE_G0_RANGE_END = 127;
        public static final int CODE_G0_RANGE_START = 32;
        public static final int CODE_G1_RANGE_END = 255;
        public static final int CODE_G1_RANGE_START = 160;
        public static final int CODE_G2_BLK = 48;
        public static final int CODE_G2_NBTSP = 33;
        public static final int CODE_G2_RANGE_END = 127;
        public static final int CODE_G2_RANGE_START = 32;
        public static final int CODE_G2_TSP = 32;
        public static final int CODE_G3_CC = 160;
        public static final int CODE_G3_RANGE_END = 255;
        public static final int CODE_G3_RANGE_START = 160;

        private Const() {
        }
    }

    interface DisplayListener {
        void emitEvent(CaptionEvent captionEvent);
    }

    Cea708CCParser(DisplayListener displayListener) {
        if (displayListener != null) {
            this.mListener = displayListener;
        }
    }

    private void emitCaptionBuffer() {
        if (this.mBuilder.length() > 0) {
            this.mListener.emitEvent(new CaptionEvent(1, this.mBuilder.toString()));
            this.mBuilder.setLength(0);
        }
    }

    private void emitCaptionEvent(CaptionEvent captionEvent) {
        emitCaptionBuffer();
        this.mListener.emitEvent(captionEvent);
    }

    private int parseC0(byte[] bArr, int i) {
        int i2 = this.mCommand;
        if (i2 < 24 || i2 > 31) {
            int i3 = this.mCommand;
            if (i3 >= 16 && i3 <= 23) {
                return i + 1;
            }
            int i4 = this.mCommand;
            if (i4 == 0) {
                return i;
            }
            if (i4 == 3) {
                emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) i4)));
                return i;
            } else if (i4 != 8) {
                switch (i4) {
                    case 12:
                        emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) i4)));
                        return i;
                    case 13:
                        this.mBuilder.append('\n');
                        return i;
                    case 14:
                        emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) i4)));
                        return i;
                    default:
                        return i;
                }
            } else {
                emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) i4)));
                return i;
            }
        } else {
            if (i2 == 24) {
                try {
                    if (bArr[i] == 0) {
                        this.mBuilder.append((char) bArr[i + 1]);
                    } else {
                        this.mBuilder.append(new String(Arrays.copyOfRange(bArr, i, i + 2), "EUC-KR"));
                    }
                } catch (UnsupportedEncodingException e2) {
                    Log.e(TAG, "P16 Code - Could not find supported encoding", e2);
                }
            }
            return i + 2;
        }
    }

    private int parseC1(byte[] bArr, int i) {
        int i2;
        int i3 = this.mCommand;
        switch (i3) {
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
                emitCaptionEvent(new CaptionEvent(3, Integer.valueOf(i3 - 128)));
                return i;
            case 136:
                int i4 = i + 1;
                emitCaptionEvent(new CaptionEvent(4, Integer.valueOf(bArr[i] & 255)));
                return i4;
            case 137:
                int i5 = i + 1;
                emitCaptionEvent(new CaptionEvent(5, Integer.valueOf(bArr[i] & 255)));
                return i5;
            case 138:
                int i6 = i + 1;
                emitCaptionEvent(new CaptionEvent(6, Integer.valueOf(bArr[i] & 255)));
                return i6;
            case 139:
                int i7 = i + 1;
                emitCaptionEvent(new CaptionEvent(7, Integer.valueOf(bArr[i] & 255)));
                return i7;
            case 140:
                int i8 = i + 1;
                emitCaptionEvent(new CaptionEvent(8, Integer.valueOf(bArr[i] & 255)));
                return i8;
            case 141:
                int i9 = i + 1;
                emitCaptionEvent(new CaptionEvent(9, Integer.valueOf(bArr[i] & 255)));
                return i9;
            case 142:
                emitCaptionEvent(new CaptionEvent(10, null));
                return i;
            case 143:
                emitCaptionEvent(new CaptionEvent(11, null));
                return i;
            case 144:
                int i10 = (bArr[i] & 240) >> 4;
                byte b2 = bArr[i] & 3;
                int i11 = (bArr[i] & 12) >> 2;
                int i12 = i + 1;
                boolean z = (bArr[i12] & 128) != 0;
                boolean z2 = (bArr[i12] & 64) != 0;
                byte b3 = bArr[i12] & 7;
                i2 = i + 2;
                emitCaptionEvent(new CaptionEvent(12, new CaptionPenAttr(b2, i11, i10, b3, (bArr[i12] & 56) >> 3, z2, z)));
                return i2;
            case 145:
                int i13 = i + 1;
                int i14 = i13 + 1;
                i2 = i14 + 1;
                emitCaptionEvent(new CaptionEvent(13, new CaptionPenColor(new CaptionColor((bArr[i] & 192) >> 6, (bArr[i] & 48) >> 4, (bArr[i] & 12) >> 2, bArr[i] & 3), new CaptionColor((bArr[i13] & 192) >> 6, (bArr[i13] & 48) >> 4, (bArr[i13] & 12) >> 2, bArr[i13] & 3), new CaptionColor(0, (bArr[i14] & 48) >> 4, (bArr[i14] & 12) >> 2, bArr[i14] & 3))));
                return i2;
            case 146:
                int i15 = i + 2;
                emitCaptionEvent(new CaptionEvent(14, new CaptionPenLocation(bArr[i] & 15, bArr[i + 1] & 63)));
                return i15;
            case 147:
            case 148:
            case 149:
            case 150:
            default:
                return i;
            case 151:
                CaptionColor captionColor = new CaptionColor((bArr[i] & 192) >> 6, (bArr[i] & 48) >> 4, (bArr[i] & 12) >> 2, bArr[i] & 3);
                int i16 = i + 1;
                int i17 = i + 2;
                int i18 = ((bArr[i16] & 192) >> 6) | ((bArr[i17] & 128) >> 5);
                CaptionColor captionColor2 = new CaptionColor(0, (bArr[i16] & 48) >> 4, (bArr[i16] & 12) >> 2, bArr[i16] & 3);
                boolean z3 = (bArr[i17] & 64) != 0;
                int i19 = (bArr[i17] & 48) >> 4;
                int i20 = (bArr[i17] & 12) >> 2;
                byte b4 = bArr[i17] & 3;
                int i21 = i + 3;
                int i22 = (bArr[i21] & 240) >> 4;
                byte b5 = bArr[i21] & 3;
                i2 = i + 4;
                emitCaptionEvent(new CaptionEvent(15, new CaptionWindowAttr(captionColor, captionColor2, i18, z3, i19, i20, b4, (bArr[i21] & 12) >> 2, i22, b5)));
                return i2;
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
                int i23 = i3 - 152;
                boolean z4 = (bArr[i] & 32) != 0;
                boolean z5 = (bArr[i] & 16) != 0;
                boolean z6 = (bArr[i] & 8) != 0;
                byte b6 = bArr[i] & 7;
                int i24 = i + 1;
                boolean z7 = (bArr[i24] & 128) != 0;
                int i25 = i + 3;
                byte b7 = bArr[i + 4] & 63;
                int i26 = i + 5;
                byte b8 = bArr[i26] & 7;
                int i27 = i + 6;
                emitCaptionEvent(new CaptionEvent(16, new CaptionWindow(i23, z4, z5, z6, b6, z7, bArr[i24] & Byte.MAX_VALUE, bArr[i + 2] & 255, (bArr[i25] & 240) >> 4, bArr[i25] & 15, b7, b8, (bArr[i26] & 56) >> 3)));
                return i27;
        }
    }

    private int parseC2(byte[] bArr, int i) {
        int i2 = this.mCommand;
        if (i2 >= 0 && i2 <= 7) {
            return i;
        }
        int i3 = this.mCommand;
        if (i3 >= 8 && i3 <= 15) {
            return i + 1;
        }
        int i4 = this.mCommand;
        if (i4 >= 16 && i4 <= 23) {
            return i + 2;
        }
        int i5 = this.mCommand;
        return (i5 < 24 || i5 > 31) ? i : i + 3;
    }

    private int parseC3(byte[] bArr, int i) {
        int i2 = this.mCommand;
        if (i2 >= 128 && i2 <= 135) {
            return i + 4;
        }
        int i3 = this.mCommand;
        return (i3 < 136 || i3 > 143) ? i : i + 5;
    }

    private int parseExt1(byte[] bArr, int i) {
        this.mCommand = bArr[i] & 255;
        int i2 = i + 1;
        int i3 = this.mCommand;
        if (i3 >= 0 && i3 <= 31) {
            return parseC2(bArr, i2);
        }
        int i4 = this.mCommand;
        if (i4 >= 128 && i4 <= 159) {
            return parseC3(bArr, i2);
        }
        int i5 = this.mCommand;
        if (i5 < 32 || i5 > 127) {
            int i6 = this.mCommand;
            if (i6 < 160 || i6 > 255) {
                return i2;
            }
            parseG3(bArr, i2);
            return i2;
        }
        parseG2(bArr, i2);
        return i2;
    }

    private int parseG0(byte[] bArr, int i) {
        int i2 = this.mCommand;
        if (i2 == 127) {
            this.mBuilder.append(MUSIC_NOTE_CHAR);
        } else {
            this.mBuilder.append((char) i2);
        }
        return i;
    }

    private int parseG1(byte[] bArr, int i) {
        this.mBuilder.append((char) this.mCommand);
        return i;
    }

    private int parseG2(byte[] bArr, int i) {
        int i2 = this.mCommand;
        if (!(i2 == 32 || i2 == 33)) {
        }
        return i;
    }

    private int parseG3(byte[] bArr, int i) {
        return i;
    }

    private int parseServiceBlockData(byte[] bArr, int i) {
        this.mCommand = bArr[i] & 255;
        int i2 = i + 1;
        int i3 = this.mCommand;
        if (i3 == 16) {
            return parseExt1(bArr, i2);
        }
        if (i3 >= 0 && i3 <= 31) {
            return parseC0(bArr, i2);
        }
        int i4 = this.mCommand;
        if (i4 >= 128 && i4 <= 159) {
            return parseC1(bArr, i2);
        }
        int i5 = this.mCommand;
        if (i5 < 32 || i5 > 127) {
            int i6 = this.mCommand;
            if (i6 < 160 || i6 > 255) {
                return i2;
            }
            parseG1(bArr, i2);
            return i2;
        }
        parseG0(bArr, i2);
        return i2;
    }

    public void parse(byte[] bArr) {
        int i = 0;
        while (i < bArr.length) {
            i = parseServiceBlockData(bArr, i);
        }
        emitCaptionBuffer();
    }
}
