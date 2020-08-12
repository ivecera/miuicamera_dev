package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class I implements Runnable {
    private final /* synthetic */ Camera2Module Hi;

    public /* synthetic */ I(Camera2Module camera2Module) {
        this.Hi = camera2Module;
    }

    public final void run() {
        this.Hi.restartModule();
    }
}
