package com.ss.android.ttve.audio;

import com.ss.android.vesdk.VEResult;

public class TEAudioWriter implements TEAudioWriterInterface {
    long mHandle = nativeCreate();

    @Override // com.ss.android.ttve.audio.TEAudioWriterInterface
    public int addPCMData(byte[] bArr, int i) {
        long j = this.mHandle;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeAddPCMData(j, bArr, i);
    }

    @Override // com.ss.android.ttve.audio.TEAudioWriterInterface
    public int closeWavFile() {
        long j = this.mHandle;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeCloseWavFile(j);
    }

    @Override // com.ss.android.ttve.audio.TEAudioWriterInterface
    public void destroy() {
        long j = this.mHandle;
        if (j != 0) {
            nativeDestroy(j);
        }
    }

    @Override // com.ss.android.ttve.audio.TEAudioWriterInterface
    public int initWavFile(String str, int i, int i2, double d2, int i3, int i4) {
        long j = this.mHandle;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeInitWavFile(j, str, i, i2, d2);
    }

    public native int nativeAddPCMData(long j, byte[] bArr, int i);

    public native int nativeCloseWavFile(long j);

    public native long nativeCreate();

    public native void nativeDestroy(long j);

    public native int nativeInitWavFile(long j, String str, int i, int i2, double d2);
}
