package com.google.zxing.datamatrix.encoder;

final class X12Encoder extends C40Encoder {
    X12Encoder() {
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder, com.google.zxing.datamatrix.encoder.C40Encoder
    public void encode(EncoderContext encoderContext) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            char currentChar = encoderContext.getCurrentChar();
            encoderContext.pos++;
            encodeChar(currentChar, sb);
            if (sb.length() % 3 == 0) {
                C40Encoder.writeNextTriplet(encoderContext, sb);
                if (HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, getEncodingMode()) != getEncodingMode()) {
                    encoderContext.signalEncoderChange(0);
                    break;
                }
            }
        }
        handleEOD(encoderContext, sb);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public int encodeChar(char c2, StringBuilder sb) {
        if (c2 == '\r') {
            sb.append((char) 0);
        } else if (c2 == ' ') {
            sb.append((char) 3);
        } else if (c2 == '*') {
            sb.append((char) 1);
        } else if (c2 == '>') {
            sb.append((char) 2);
        } else if (c2 >= '0' && c2 <= '9') {
            sb.append((char) ((c2 - '0') + 4));
        } else if (c2 < 'A' || c2 > 'Z') {
            HighLevelEncoder.illegalCharacter(c2);
            throw null;
        } else {
            sb.append((char) ((c2 - 'A') + 14));
        }
        return 1;
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder, com.google.zxing.datamatrix.encoder.C40Encoder
    public int getEncodingMode() {
        return 3;
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public void handleEOD(EncoderContext encoderContext, StringBuilder sb) {
        encoderContext.updateSymbolInfo();
        int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount();
        encoderContext.pos -= sb.length();
        if (encoderContext.getRemainingCharacters() > 1 || dataCapacity > 1 || encoderContext.getRemainingCharacters() != dataCapacity) {
            encoderContext.writeCodeword(254);
        }
        if (encoderContext.getNewEncoding() < 0) {
            encoderContext.signalEncoderChange(0);
        }
    }
}
