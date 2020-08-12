package com.android.camera.fragment.fullscreen;

/* compiled from: lambda */
public final /* synthetic */ class h implements Runnable {
    private final /* synthetic */ FragmentFullScreen Hi;

    public /* synthetic */ h(FragmentFullScreen fragmentFullScreen) {
        this.Hi = fragmentFullScreen;
    }

    public final void run() {
        this.Hi.runPendingTask();
    }
}
