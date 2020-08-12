package com.android.camera.module;

import com.android.camera2.CameraHardwareFace;

/* compiled from: lambda */
public final /* synthetic */ class o implements Runnable {
    private final /* synthetic */ Camera2Module Hi;
    private final /* synthetic */ CameraHardwareFace[] Li;

    public /* synthetic */ o(Camera2Module camera2Module, CameraHardwareFace[] cameraHardwareFaceArr) {
        this.Hi = camera2Module;
        this.Li = cameraHardwareFaceArr;
    }

    public final void run() {
        this.Hi.a(this.Li);
    }
}
