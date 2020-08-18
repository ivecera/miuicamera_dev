package com.android.camera.fragment.top;

/* compiled from: lambda */
public final /* synthetic */ class n implements Runnable {
    private final /* synthetic */ FragmentTopConfig Hi;
    private final /* synthetic */ boolean Li;

    public /* synthetic */ n(FragmentTopConfig fragmentTopConfig, boolean z) {
        this.Hi = fragmentTopConfig;
        this.Li = z;
    }

    public final void run() {
        this.Hi.q(this.Li);
    }
}
