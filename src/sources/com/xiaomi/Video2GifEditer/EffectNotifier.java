package com.xiaomi.Video2GifEditer;

public interface EffectNotifier {
    void OnReadyNow();

    void OnReceiveFailed();

    void OnReceiveFinish();
}
