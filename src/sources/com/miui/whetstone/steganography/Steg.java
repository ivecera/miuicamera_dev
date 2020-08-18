package com.miui.whetstone.steganography;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;

public class Steg {
    private final int PASS_NONE = 0;
    private final int PASS_SIMPLE_XOR = 1;
    private Bitmap inBitmap = null;
    private String key = null;
    private int passmode = 0;

    private int bytesAvaliableInBitmap() {
        Bitmap bitmap = this.inBitmap;
        if (bitmap == null) {
            return 0;
        }
        return (((bitmap.getWidth() * this.inBitmap.getHeight()) * 3) / 8) - 12;
    }

    private void setInputBitmap(Bitmap bitmap) {
        this.inBitmap = bitmap;
    }

    public static Steg withInput(Bitmap bitmap) {
        Steg steg = new Steg();
        steg.setInputBitmap(bitmap);
        return steg;
    }

    public static Steg withInput(File file) {
        Steg steg = new Steg();
        steg.setInputBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        return steg;
    }

    public static Steg withInput(String str) {
        Steg steg = new Steg();
        steg.setInputBitmap(BitmapFactory.decodeFile(str));
        return steg;
    }

    public DecodedObject decode() throws Exception {
        return new DecodedObject(BitmapEncoder.decode(this.inBitmap));
    }

    public EncodedObject encode(File file) throws Exception {
        throw new RuntimeException("Not implemented yet");
    }

    public EncodedObject encode(String str) throws Exception {
        return encode(str.getBytes());
    }

    public EncodedObject encode(byte[] bArr) throws Exception {
        if (bArr.length <= bytesAvaliableInBitmap()) {
            return new EncodedObject(BitmapEncoder.encode(this.inBitmap, bArr));
        }
        throw new IllegalArgumentException("Not enough space in bitmap to hold data (max:" + bytesAvaliableInBitmap() + ")");
    }

    public Steg withPassword(String str) {
        withPassword(str, 1);
        return this;
    }

    public Steg withPassword(String str, int i) {
        this.key = str;
        this.passmode = i;
        throw new RuntimeException("Not implemented yet");
    }
}
