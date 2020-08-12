package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ Camera2Module Hi;
    private final /* synthetic */ boolean Li;

    public /* synthetic */ a(Camera2Module camera2Module, boolean z) {
        this.Hi = camera2Module;
        this.Li = z;
    }

    public final void run() {
        this.Hi.w(this.Li);
    }
}
