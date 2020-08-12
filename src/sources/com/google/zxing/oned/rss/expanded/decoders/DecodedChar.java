package com.google.zxing.oned.rss.expanded.decoders;

final class DecodedChar extends DecodedObject {
    static final char FNC1 = '$';
    private final char value;

    DecodedChar(int i, char c2) {
        super(i);
        this.value = c2;
    }

    /* access modifiers changed from: package-private */
    public char getValue() {
        return this.value;
    }

    /* access modifiers changed from: package-private */
    public boolean isFNC1() {
        return this.value == '$';
    }
}
