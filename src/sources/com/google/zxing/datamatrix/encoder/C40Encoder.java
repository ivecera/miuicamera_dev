package com.google.zxing.datamatrix.encoder;

class C40Encoder implements Encoder {
    C40Encoder() {
    }

    private int backtrackOneCharacter(EncoderContext encoderContext, StringBuilder sb, StringBuilder sb2, int i) {
        int length = sb.length();
        sb.delete(length - i, length);
        encoderContext.pos--;
        int encodeChar = encodeChar(encoderContext.getCurrentChar(), sb2);
        encoderContext.resetSymbolInfo();
        return encodeChar;
    }

    private static String encodeToCodewords(CharSequence charSequence, int i) {
        int charAt = (charSequence.charAt(i) * 1600) + (charSequence.charAt(i + 1) * '(') + charSequence.charAt(i + 2) + 1;
        return new String(new char[]{(char) (charAt / 256), (char) (charAt % 256)});
    }

    static void writeNextTriplet(EncoderContext encoderContext, StringBuilder sb) {
        encoderContext.writeCodewords(encodeToCodewords(sb, 0));
        sb.delete(0, 3);
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            char currentChar = encoderContext.getCurrentChar();
            encoderContext.pos++;
            int encodeChar = encodeChar(currentChar, sb);
            int codewordCount = encoderContext.getCodewordCount() + ((sb.length() / 3) << 1);
            encoderContext.updateSymbolInfo(codewordCount);
            int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - codewordCount;
            if (encoderContext.hasMoreCharacters()) {
                if (sb.length() % 3 == 0 && HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, getEncodingMode()) != getEncodingMode()) {
                    encoderContext.signalEncoderChange(0);
                    break;
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                if (sb.length() % 3 == 2 && dataCapacity != 2) {
                    encodeChar = backtrackOneCharacter(encoderContext, sb, sb2, encodeChar);
                }
                while (sb.length() % 3 == 1 && (encodeChar > 3 || dataCapacity != 1)) {
                    encodeChar = backtrackOneCharacter(encoderContext, sb, sb2, encodeChar);
                }
            }
        }
        handleEOD(encoderContext, sb);
    }

    /* access modifiers changed from: package-private */
    public int encodeChar(char c2, StringBuilder sb) {
        if (c2 == ' ') {
            sb.append((char) 3);
            return 1;
        } else if (c2 >= '0' && c2 <= '9') {
            sb.append((char) ((c2 - '0') + 4));
            return 1;
        } else if (c2 >= 'A' && c2 <= 'Z') {
            sb.append((char) ((c2 - 'A') + 14));
            return 1;
        } else if (c2 < ' ') {
            sb.append((char) 0);
            sb.append(c2);
            return 2;
        } else if (c2 <= '/') {
            sb.append((char) 1);
            sb.append((char) (c2 - '!'));
            return 2;
        } else if (c2 <= '@') {
            sb.append((char) 1);
            sb.append((char) ((c2 - ':') + 15));
            return 2;
        } else if (c2 <= '_') {
            sb.append((char) 1);
            sb.append((char) ((c2 - '[') + 22));
            return 2;
        } else if (c2 <= 127) {
            sb.append((char) 2);
            sb.append((char) (c2 - '`'));
            return 2;
        } else {
            sb.append("\u0001\u001e");
            return encodeChar((char) (c2 - 128), sb) + 2;
        }
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public int getEncodingMode() {
        return 1;
    }

    /* access modifiers changed from: package-private */
    public void handleEOD(EncoderContext encoderContext, StringBuilder sb) {
        int length = sb.length() % 3;
        int codewordCount = encoderContext.getCodewordCount() + ((sb.length() / 3) << 1);
        encoderContext.updateSymbolInfo(codewordCount);
        int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - codewordCount;
        if (length == 2) {
            sb.append((char) 0);
            while (sb.length() >= 3) {
                writeNextTriplet(encoderContext, sb);
            }
            if (encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword(254);
            }
        } else if (dataCapacity == 1 && length == 1) {
            while (sb.length() >= 3) {
                writeNextTriplet(encoderContext, sb);
            }
            if (encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword(254);
            }
            encoderContext.pos--;
        } else if (length == 0) {
            while (sb.length() >= 3) {
                writeNextTriplet(encoderContext, sb);
            }
            if (dataCapacity > 0 || encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword(254);
            }
        } else {
            throw new IllegalStateException("Unexpected case. Please report!");
        }
        encoderContext.signalEncoderChange(0);
    }
}
