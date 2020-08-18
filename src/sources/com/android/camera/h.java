package com.android.camera;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* compiled from: lambda */
public final /* synthetic */ class h implements DialogInterface.OnKeyListener {
    public static final /* synthetic */ h INSTANCE = new h();

    private /* synthetic */ h() {
    }

    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return RotateDialogController.c(dialogInterface, i, keyEvent);
    }
}
