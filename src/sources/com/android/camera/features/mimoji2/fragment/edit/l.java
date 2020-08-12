package com.android.camera.features.mimoji2.fragment.edit;

import com.arcsoft.avatar.emoticon.EmoInfo;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    private final /* synthetic */ FragmentMimojiEmoticon Hi;
    private final /* synthetic */ EmoInfo Li;
    private final /* synthetic */ int Mi;

    public /* synthetic */ l(FragmentMimojiEmoticon fragmentMimojiEmoticon, EmoInfo emoInfo, int i) {
        this.Hi = fragmentMimojiEmoticon;
        this.Li = emoInfo;
        this.Mi = i;
    }

    public final void run() {
        this.Hi.a(this.Li, this.Mi);
    }
}
