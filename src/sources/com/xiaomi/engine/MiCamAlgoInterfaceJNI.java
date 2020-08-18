package com.xiaomi.engine;

import android.hardware.camera2.params.OutputConfiguration;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;
import com.xiaomi.engine.TaskSession;
import java.util.List;

class MiCamAlgoInterfaceJNI {
    private static final String LIB_NAME = "com.xiaomi.camera.algojni";
    private static final String TAG = "MiCamAlgoInterfaceJNI";

    static {
        try {
            Log.e(TAG, "start loading com.xiaomi.camera.algojni");
            System.loadLibrary(LIB_NAME);
            Log.d(TAG, "static initializer: loadLibrary com.xiaomi.camera.algojni");
        } catch (UnsatisfiedLinkError e2) {
            String str = TAG;
            Log.e(str, "can not load library:com.xiaomi.camera.algojni : " + Log.getStackTraceString(e2));
        }
    }

    MiCamAlgoInterfaceJNI() {
    }

    static native long createSessionByOutputConfigurations(BufferFormat bufferFormat, List<OutputConfiguration> list, @NonNull TaskSession.SessionStatusCallback sessionStatusCallback);

    static native long createSessionWithSurfaces(BufferFormat bufferFormat, List<Surface> list, @NonNull TaskSession.SessionStatusCallback sessionStatusCallback);

    static native int deInit();

    static native int destroySession(long j);

    static native int flush(long j);

    static native int init(String str);

    static native int processFrame(long j, @NonNull FrameData frameData, @NonNull TaskSession.FrameCallback frameCallback);

    static native int processFrameWithSync(long j, @NonNull List<FrameData> list, @NonNull Image image, int i);
}
