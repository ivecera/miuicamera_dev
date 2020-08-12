package com.miui.whetstone.steganography;

import java.io.File;
import java.io.IOException;

public class DecodedObject {
    private final byte[] bytes;

    public DecodedObject(byte[] bArr) {
        this.bytes = bArr;
    }

    public byte[] intoByteArray() {
        return this.bytes;
    }

    public File intoFile(File file) {
        throw new RuntimeException("Not implemented yet");
    }

    public File intoFile(String str) throws IOException {
        return intoFile(new File(str));
    }

    public String intoString() {
        return new String(this.bytes);
    }
}
