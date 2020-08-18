package com.xiaomi.stat.d;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class o {

    /* renamed from: a  reason: collision with root package name */
    private static final String f598a = "RsaUtils";

    /* renamed from: b  reason: collision with root package name */
    private static final String f599b = "RSA/ECB/PKCS1Padding";

    /* renamed from: c  reason: collision with root package name */
    private static final String f600c = "BC";

    /* renamed from: d  reason: collision with root package name */
    private static final String f601d = "RSA";

    private static RSAPublicKey a(byte[] bArr) throws Exception {
        return (RSAPublicKey) KeyFactory.getInstance(f601d).generatePublic(new X509EncodedKeySpec(bArr));
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        try {
            RSAPublicKey a2 = a(bArr);
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, a2);
            return instance.doFinal(bArr2);
        } catch (Exception e2) {
            k.d(f598a, "RsaUtils encrypt exception:", e2);
            return null;
        }
    }
}
