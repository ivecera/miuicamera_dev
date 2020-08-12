package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class p implements Runnable {
    private final /* synthetic */ Camera2Module Hi;
    private final /* synthetic */ boolean Li;

    public /* synthetic */ p(Camera2Module camera2Module, boolean z) {
        this.Hi = camera2Module;
        this.Li = z;
    }

    public final void run() {
        this.Hi.t(this.Li);
    }
}
