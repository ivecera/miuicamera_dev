package com.xiaomi.Video2GifEditer;

public interface EffectCameraNotifier {
    void OnNeedStopRecording();

    void OnNotifyRender();

    void OnRecordFailed();

    void OnRecordFinish(String str);
}
