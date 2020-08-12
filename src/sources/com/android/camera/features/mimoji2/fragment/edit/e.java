package com.android.camera.features.mimoji2.fragment.edit;

/* compiled from: lambda */
public final /* synthetic */ class e implements Runnable {
    private final /* synthetic */ FragmentMimojiEmoticon Hi;
    private final /* synthetic */ int Li;

    public /* synthetic */ e(FragmentMimojiEmoticon fragmentMimojiEmoticon, int i) {
        this.Hi = fragmentMimojiEmoticon;
        this.Li = i;
    }

    public final void run() {
        this.Hi.l(this.Li);
    }
}
