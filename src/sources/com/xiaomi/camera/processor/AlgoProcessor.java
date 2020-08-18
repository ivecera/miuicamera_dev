package com.xiaomi.camera.processor;

import android.support.annotation.NonNull;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.engine.TaskSession;

public interface AlgoProcessor {
    void doProcess(@NonNull CaptureData captureData, ProcessResultListener processResultListener, TaskSession taskSession);
}
