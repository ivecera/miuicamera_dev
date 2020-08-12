package com.android.camera.fragment;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    private final /* synthetic */ FragmentMainContent Hi;
    private final /* synthetic */ boolean Li;

    public /* synthetic */ f(FragmentMainContent fragmentMainContent, boolean z) {
        this.Hi = fragmentMainContent;
        this.Li = z;
    }

    public final void run() {
        this.Hi.p(this.Li);
    }
}
