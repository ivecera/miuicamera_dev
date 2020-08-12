package com.android.camera.module.loader.camera2;

import com.android.camera2.Camera2Proxy;

/* compiled from: lambda */
public final /* synthetic */ class c implements Camera2Proxy.CaptureBusyCallback {
    private final /* synthetic */ Camera2OpenManager Hi;
    private final /* synthetic */ boolean Li;

    public /* synthetic */ c(Camera2OpenManager camera2OpenManager, boolean z) {
        this.Hi = camera2OpenManager;
        this.Li = z;
    }

    @Override // com.android.camera2.Camera2Proxy.CaptureBusyCallback
    public final void onCaptureCompleted(boolean z) {
        this.Hi.c(this.Li, z);
    }
}
