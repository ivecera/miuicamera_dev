package com.android.camera.resource.tmmusic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DataZipUtil {
    static final int BUFFER = 10240;

    private static byte charToByte(char c2) {
        return (byte) "0123456789ABCDEF".indexOf(c2);
    }

    public static void compress(InputStream inputStream, OutputStream outputStream) throws Exception {
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(outputStream);
        byte[] bArr = new byte[BUFFER];
        while (true) {
            int read = inputStream.read(bArr, 0, BUFFER);
            if (read != -1) {
                gZIPOutputStream.write(bArr, 0, read);
            } else {
                gZIPOutputStream.finish();
                gZIPOutputStream.flush();
                gZIPOutputStream.close();
                return;
            }
        }
    }

    public static byte[] compress(byte[] bArr) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        compress(byteArrayInputStream, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        byteArrayInputStream.close();
        return byteArray;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0020  */
    public static void decompress(InputStream inputStream, OutputStream outputStream) throws Exception {
        GZIPInputStream gZIPInputStream;
        try {
            gZIPInputStream = new GZIPInputStream(inputStream);
            try {
                byte[] bArr = new byte[BUFFER];
                while (true) {
                    int read = gZIPInputStream.read(bArr, 0, BUFFER);
                    if (read != -1) {
                        outputStream.write(bArr, 0, read);
                    } else {
                        gZIPInputStream.close();
                        return;
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (gZIPInputStream != null) {
                    gZIPInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            gZIPInputStream = null;
            if (gZIPInputStream != null) {
            }
            throw th;
        }
    }

    public static byte[] decompress(byte[] bArr) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        decompress(byteArrayInputStream, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        byteArrayInputStream.close();
        return byteArray;
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
        }
        return bArr;
    }
}
