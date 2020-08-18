package com.xiaomi.engine;

import android.content.Context;
import android.hardware.camera2.params.OutputConfiguration;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;
import com.xiaomi.engine.TaskSession;
import java.util.List;

public final class MiCameraAlgo {
    private static final String TAG = "MiCameraAlgo";

    public static TaskSession createSessionByOutputConfigurations(@NonNull BufferFormat bufferFormat, @NonNull List<OutputConfiguration> list, TaskSession.SessionStatusCallback sessionStatusCallback) {
        Log.d(TAG, "createSessionByOutputConfigurations: start");
        long createSessionByOutputConfigurations = MiCamAlgoInterfaceJNI.createSessionByOutputConfigurations(bufferFormat, list, sessionStatusCallback);
        if (createSessionByOutputConfigurations != 0) {
            TaskSession taskSession = new TaskSession(createSessionByOutputConfigurations);
            String str = TAG;
            Log.d(str, "createSessionByOutputConfigurations: A new TaskSession had been created:" + taskSession);
            return taskSession;
        }
        throw new RuntimeException("Create session failed: Session handle is null!");
    }

    public static TaskSession createSessionWithSurfaces(@NonNull BufferFormat bufferFormat, @NonNull List<Surface> list, TaskSession.SessionStatusCallback sessionStatusCallback) {
        Log.d(TAG, "createSessionWithSurfaces: start");
        long createSessionWithSurfaces = MiCamAlgoInterfaceJNI.createSessionWithSurfaces(bufferFormat, list, sessionStatusCallback);
        if (createSessionWithSurfaces != 0) {
            return new TaskSession(createSessionWithSurfaces);
        }
        throw new RuntimeException("Create session failed: Session handle is null!");
    }

    public static void deInit() {
        Log.d(TAG, "deInit: start");
        Util.assertOrNot(MiCamAlgoInterfaceJNI.deInit());
    }

    public static void init(Context context) {
        Log.d(TAG, "init: start");
        if (context != null) {
            String absolutePath = context.getFilesDir().getAbsolutePath();
            String str = TAG;
            Log.d(str, "init: application file path to algorithm lib:" + absolutePath);
            Util.assertOrNot(MiCamAlgoInterfaceJNI.init(absolutePath));
            return;
        }
        throw new IllegalArgumentException("context is null!");
    }
}
