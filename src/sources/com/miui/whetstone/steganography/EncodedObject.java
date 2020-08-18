package com.miui.whetstone.steganography;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EncodedObject {
    private final Bitmap bitmap;

    public EncodedObject(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public Bitmap intoBitmap() {
        return this.bitmap;
    }

    public File intoFile(File file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.close();
        return file;
    }

    public File intoFile(String str) throws IOException {
        return intoFile(new File(str));
    }
}
