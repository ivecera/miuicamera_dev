package com.android.camera.fragment.fullscreen;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ FragmentFullScreen Hi;

    public /* synthetic */ a(FragmentFullScreen fragmentFullScreen) {
        this.Hi = fragmentFullScreen;
    }

    public final void run() {
        this.Hi.startConcatVideoIfNeed();
    }
}
