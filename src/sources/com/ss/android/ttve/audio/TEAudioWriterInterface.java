package com.ss.android.ttve.audio;

public interface TEAudioWriterInterface {
    int addPCMData(byte[] bArr, int i);

    int closeWavFile();

    void destroy();

    int initWavFile(String str, int i, int i2, double d2, int i3, int i4);
}
