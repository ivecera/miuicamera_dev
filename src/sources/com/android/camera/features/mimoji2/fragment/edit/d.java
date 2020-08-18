package com.android.camera.features.mimoji2.fragment.edit;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* compiled from: lambda */
public final /* synthetic */ class d implements DialogInterface.OnKeyListener {
    private final /* synthetic */ FragmentMimojiEmoticon Hi;

    public /* synthetic */ d(FragmentMimojiEmoticon fragmentMimojiEmoticon) {
        this.Hi = fragmentMimojiEmoticon;
    }

    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return this.Hi.b(dialogInterface, i, keyEvent);
    }
}
