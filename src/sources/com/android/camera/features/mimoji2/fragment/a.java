package com.android.camera.features.mimoji2.fragment;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ FragmentMimojiFullScreen Hi;
    private final /* synthetic */ String Li;

    public /* synthetic */ a(FragmentMimojiFullScreen fragmentMimojiFullScreen, String str) {
        this.Hi = fragmentMimojiFullScreen;
        this.Li = str;
    }

    public final void run() {
        this.Hi.l(this.Li);
    }
}
