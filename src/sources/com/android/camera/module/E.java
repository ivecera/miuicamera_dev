package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class E implements Runnable {
    private final /* synthetic */ VideoModule Hi;
    private final /* synthetic */ boolean Li;

    public /* synthetic */ E(VideoModule videoModule, boolean z) {
        this.Hi = videoModule;
        this.Li = z;
    }

    public final void run() {
        this.Hi.x(this.Li);
    }
}
