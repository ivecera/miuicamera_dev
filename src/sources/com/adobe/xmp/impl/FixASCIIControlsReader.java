package com.adobe.xmp.impl;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class FixASCIIControlsReader extends PushbackReader {
    private static final int BUFFER_SIZE = 8;
    private static final int STATE_AMP = 1;
    private static final int STATE_DIG1 = 4;
    private static final int STATE_ERROR = 5;
    private static final int STATE_HASH = 2;
    private static final int STATE_HEX = 3;
    private static final int STATE_START = 0;
    private int control = 0;
    private int digits = 0;
    private int state = 0;

    public FixASCIIControlsReader(Reader reader) {
        super(reader, 8);
    }

    private char processChar(char c2) {
        int i;
        int i2 = this.state;
        if (i2 == 0) {
            if (c2 == '&') {
                this.state = 1;
            }
            return c2;
        } else if (i2 == 1) {
            if (c2 == '#') {
                this.state = 2;
            } else {
                this.state = 5;
            }
            return c2;
        } else if (i2 != 2) {
            if (i2 == 3) {
                if (('0' <= c2 && c2 <= '9') || (('a' <= c2 && c2 <= 'f') || ('A' <= c2 && c2 <= 'F'))) {
                    this.control = (this.control * 16) + Character.digit(c2, 16);
                    this.digits++;
                    if (this.digits <= 4) {
                        this.state = 3;
                    } else {
                        this.state = 5;
                    }
                } else if (c2 != ';' || !Utils.isControlChar((char) this.control)) {
                    this.state = 5;
                } else {
                    this.state = 0;
                    i = this.control;
                }
                return c2;
            } else if (i2 == 4) {
                if ('0' <= c2 && c2 <= '9') {
                    this.control = (this.control * 10) + Character.digit(c2, 10);
                    this.digits++;
                    if (this.digits <= 5) {
                        this.state = 4;
                    } else {
                        this.state = 5;
                    }
                } else if (c2 != ';' || !Utils.isControlChar((char) this.control)) {
                    this.state = 5;
                } else {
                    this.state = 0;
                    i = this.control;
                }
                return c2;
            } else if (i2 != 5) {
                return c2;
            } else {
                this.state = 0;
                return c2;
            }
            return (char) i;
        } else {
            if (c2 == 'x') {
                this.control = 0;
                this.digits = 0;
                this.state = 3;
            } else if ('0' > c2 || c2 > '9') {
                this.state = 5;
            } else {
                this.control = Character.digit(c2, 10);
                this.digits = 1;
                this.state = 4;
            }
            return c2;
        }
    }

    @Override // java.io.PushbackReader, java.io.FilterReader, java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        boolean z;
        char[] cArr2 = new char[8];
        int i3 = i;
        int i4 = 0;
        int i5 = 0;
        loop0:
        while (true) {
            z = true;
            while (z && i4 < i2) {
                z = super.read(cArr2, i5, 1) == 1;
                if (z) {
                    char processChar = processChar(cArr2[i5]);
                    int i6 = this.state;
                    if (i6 == 0) {
                        if (Utils.isControlChar(processChar)) {
                            processChar = ' ';
                        }
                        cArr[i3] = processChar;
                        i4++;
                        i3++;
                    } else if (i6 == 5) {
                        unread(cArr2, 0, i5 + 1);
                    } else {
                        i5++;
                    }
                    i5 = 0;
                } else if (i5 > 0) {
                    unread(cArr2, 0, i5);
                    this.state = 5;
                    i5 = 0;
                }
            }
        }
        if (i4 > 0 || z) {
            return i4;
        }
        return -1;
    }
}
