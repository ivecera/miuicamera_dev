package com.xiaomi.mediaprocess;

public interface EffectCoverNotifier {
    void OnReceiveAllComplete();

    void OnReceivePngFile(String str, long j);
}
