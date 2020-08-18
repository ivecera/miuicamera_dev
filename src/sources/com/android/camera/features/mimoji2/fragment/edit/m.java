package com.android.camera.features.mimoji2.fragment.edit;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class m implements DialogInterface.OnMultiChoiceClickListener {
    private final /* synthetic */ boolean[] Hi;

    public /* synthetic */ m(boolean[] zArr) {
        this.Hi = zArr;
    }

    public final void onClick(DialogInterface dialogInterface, int i, boolean z) {
        FragmentMimojiEmoticon.a(this.Hi, dialogInterface, i, z);
    }
}
