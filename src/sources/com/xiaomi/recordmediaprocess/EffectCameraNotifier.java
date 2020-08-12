package com.xiaomi.recordmediaprocess;

public interface EffectCameraNotifier {
    void OnNeedStopRecording();

    void OnNotifyRender();

    void OnRecordFailed();

    void OnRecordFinish(String str);

    void OnRecordFinish(String str, long j, long j2);
}
