package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    private final /* synthetic */ Camera2Module Hi;

    public /* synthetic */ g(Camera2Module camera2Module) {
        this.Hi = camera2Module;
    }

    public final void run() {
        this.Hi.startLensActivity();
    }
}
