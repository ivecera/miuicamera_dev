package com.google.zxing.datamatrix.encoder;

final class TextEncoder extends C40Encoder {
    TextEncoder() {
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public int encodeChar(char c2, StringBuilder sb) {
        if (c2 == ' ') {
            sb.append((char) 3);
            return 1;
        } else if (c2 >= '0' && c2 <= '9') {
            sb.append((char) ((c2 - '0') + 4));
            return 1;
        } else if (c2 >= 'a' && c2 <= 'z') {
            sb.append((char) ((c2 - 'a') + 14));
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
        } else if (c2 >= '[' && c2 <= '_') {
            sb.append((char) 1);
            sb.append((char) ((c2 - '[') + 22));
            return 2;
        } else if (c2 == '`') {
            sb.append((char) 2);
            sb.append((char) (c2 - '`'));
            return 2;
        } else if (c2 <= 'Z') {
            sb.append((char) 2);
            sb.append((char) ((c2 - 'A') + 1));
            return 2;
        } else if (c2 <= 127) {
            sb.append((char) 2);
            sb.append((char) ((c2 - '{') + 27));
            return 2;
        } else {
            sb.append("\u0001\u001e");
            return encodeChar((char) (c2 - 128), sb) + 2;
        }
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder, com.google.zxing.datamatrix.encoder.C40Encoder
    public int getEncodingMode() {
        return 2;
    }
}
