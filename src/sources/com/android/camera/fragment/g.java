package com.android.camera.fragment;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class g implements DialogInterface.OnClickListener {
    private final /* synthetic */ GoogleLensFragment Hi;

    public /* synthetic */ g(GoogleLensFragment googleLensFragment) {
        this.Hi = googleLensFragment;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.Hi.c(dialogInterface, i);
    }
}
