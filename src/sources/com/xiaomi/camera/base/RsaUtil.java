package com.xiaomi.camera.base;

import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class RsaUtil {
    public static final String KEY_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    public static final String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDI9k5fmdE2SlFjyaRSkr3wh93Q\nXwL/5Lpc9Xll9NPtOXliyu0x4ZgmWeLv9IWXRV2Bkh3Rce2YkVZZ8hgJT9SKfhnL\nVHINpNwP4abHpIusZCTE387nN+nNYjzdkqXPKo6KPDxKdQadp+PqFKjdjPkIL2xV\n6jhxOpf6TQsCAWfBuwIDAQAB";

    public static byte[] encryptByPublicKey(byte[] bArr) {
        byte[] bArr2 = new byte[0];
        try {
            PublicKey publicKey = getPublicKey();
            Cipher instance = Cipher.getInstance(KEY_ALGORITHM);
            instance.init(1, publicKey);
            int length = bArr.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = length - i;
                if (i3 > 0) {
                    byte[] doFinal = i3 > 117 ? instance.doFinal(bArr, i, 117) : instance.doFinal(bArr, i, i3);
                    byteArrayOutputStream.write(doFinal, 0, doFinal.length);
                    i2++;
                    i = i2 * 117;
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                }
            }
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return bArr2;
        } catch (NoSuchPaddingException e3) {
            e3.printStackTrace();
            return bArr2;
        } catch (Exception e4) {
            e4.printStackTrace();
            return bArr2;
        }
    }

    public static PublicKey getPublicKey() {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(publicKeyStr.getBytes(), 0)));
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        } catch (InvalidKeySpecException e3) {
            e3.printStackTrace();
        }
        return null;
    }
}
