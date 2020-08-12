package com.android.camera.resource;

import android.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static final String CHARSET = "utf-8";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";

    public static String encry(String str, String str2) throws Exception {
        Cipher instance = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        instance.init(1, toKey(str));
        return Base64.encodeToString(instance.doFinal(str2.getBytes(CHARSET)), 2);
    }

    public static String encry(byte[] bArr, String str) throws Exception {
        Cipher instance = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        instance.init(1, toKey(bArr));
        return Base64.encodeToString(instance.doFinal(str.getBytes(CHARSET)), 2);
    }

    public static String getEncryString(String str, String str2, String str3) {
        try {
            return encry(str3, "appid=" + str + "&nonce=" + str2 + "&ts=" + System.currentTimeMillis());
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static Key toKey(String str) {
        return toKey(Base64.decode(str, 0));
    }

    private static Key toKey(byte[] bArr) {
        return new SecretKeySpec(bArr, KEY_ALGORITHM);
    }
}
