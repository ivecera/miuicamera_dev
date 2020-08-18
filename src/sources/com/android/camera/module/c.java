package com.android.camera.module;

import com.android.camera2.Camera2Proxy;

/* compiled from: lambda */
public final /* synthetic */ class c implements Camera2Proxy.CaptureCallback {
    private final /* synthetic */ Camera2Module Hi;

    public /* synthetic */ c(Camera2Module camera2Module) {
        this.Hi = camera2Module;
    }

    @Override // com.android.camera2.Camera2Proxy.CaptureCallback
    public final void onCaptureCompleted(boolean z) {
        this.Hi.u(z);
    }
}
