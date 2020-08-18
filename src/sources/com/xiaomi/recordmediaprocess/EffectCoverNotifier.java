package com.xiaomi.recordmediaprocess;

public interface EffectCoverNotifier {
    void OnReceiveAllComplete();

    void OnReceivePngFile(String str, long j);
}
