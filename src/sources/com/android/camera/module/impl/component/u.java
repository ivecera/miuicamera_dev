package com.android.camera.module.impl.component;

import com.android.camera.module.Camera2Module;

/* compiled from: lambda */
public final /* synthetic */ class u implements Runnable {
    private final /* synthetic */ Camera2Module Hi;
    private final /* synthetic */ boolean Li;
    private final /* synthetic */ int Mi;

    public /* synthetic */ u(Camera2Module camera2Module, boolean z, int i) {
        this.Hi = camera2Module;
        this.Li = z;
        this.Mi = i;
    }

    public final void run() {
        MiAsdDetectImpl.a(this.Hi, this.Li, this.Mi);
    }
}
