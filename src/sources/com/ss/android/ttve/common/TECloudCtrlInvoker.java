package com.ss.android.ttve.common;

import com.ss.android.ttve.nativePort.TENativeLibsLoader;

public class TECloudCtrlInvoker {
    static {
        TENativeLibsLoader.loadLibrary();
    }

    private native int nativeVerifyJson(byte[] bArr, byte[] bArr2);

    public boolean verifyJson(String str, String str2) {
        return !str.isEmpty() && !str2.isEmpty() && nativeVerifyJson(str.getBytes(), str2.getBytes()) == 0;
    }
}
