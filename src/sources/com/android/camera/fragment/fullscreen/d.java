package com.android.camera.fragment.fullscreen;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    private final /* synthetic */ FragmentFullScreen Hi;

    public /* synthetic */ d(FragmentFullScreen fragmentFullScreen) {
        this.Hi = fragmentFullScreen;
    }

    public final void run() {
        this.Hi.showExitConfirm();
    }
}
