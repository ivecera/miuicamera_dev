package com.android.camera.fragment.fullscreen;

/* compiled from: lambda */
public final /* synthetic */ class e implements Runnable {
    private final /* synthetic */ FragmentFullScreen Hi;

    public /* synthetic */ e(FragmentFullScreen fragmentFullScreen) {
        this.Hi = fragmentFullScreen;
    }

    public final void run() {
        this.Hi.startCombine();
    }
}
