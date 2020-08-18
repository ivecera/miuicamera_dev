package com.android.camera.features.mimoji2.fragment.edit;

import com.android.camera.features.mimoji2.fragment.edit.MimojiEmoticonAdapter;

/* compiled from: lambda */
public final /* synthetic */ class f implements MimojiEmoticonAdapter.OnAllSelectStateChangeListener {
    private final /* synthetic */ FragmentMimojiEmoticon Hi;

    public /* synthetic */ f(FragmentMimojiEmoticon fragmentMimojiEmoticon) {
        this.Hi = fragmentMimojiEmoticon;
    }

    @Override // com.android.camera.features.mimoji2.fragment.edit.MimojiEmoticonAdapter.OnAllSelectStateChangeListener
    public final void onAllSelectStateChange(boolean z) {
        this.Hi.o(z);
    }
}
