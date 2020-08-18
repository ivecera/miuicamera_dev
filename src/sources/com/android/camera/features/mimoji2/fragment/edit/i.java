package com.android.camera.features.mimoji2.fragment.edit;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class i implements DialogInterface.OnClickListener {
    private final /* synthetic */ FragmentMimojiEmoticon Hi;
    private final /* synthetic */ boolean[] Li;

    public /* synthetic */ i(FragmentMimojiEmoticon fragmentMimojiEmoticon, boolean[] zArr) {
        this.Hi = fragmentMimojiEmoticon;
        this.Li = zArr;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.Hi.a(this.Li, dialogInterface, i);
    }
}
