package com.xiaomi.Video2GifEditer;

public interface EffectCoverNotifier {
    void OnReceiveAllComplete();

    void OnReceivePngFile(String str, long j);
}
