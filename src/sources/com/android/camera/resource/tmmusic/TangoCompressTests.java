package com.android.camera.resource.tmmusic;

public class TangoCompressTests {
    private static final char[] hex = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length << 1);
        for (int i = 0; i < bArr.length; i++) {
            sb.append(hex[(bArr[i] & 240) >> 4]);
            sb.append(hex[bArr[i] & 15]);
        }
        return sb.toString();
    }

    public void generateWMReaportData() throws Exception {
    }
}
