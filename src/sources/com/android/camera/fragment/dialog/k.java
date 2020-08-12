package com.android.camera.fragment.dialog;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* compiled from: lambda */
public final /* synthetic */ class k implements DialogInterface.OnKeyListener {
    private final /* synthetic */ ThermalDialogFragment Hi;

    public /* synthetic */ k(ThermalDialogFragment thermalDialogFragment) {
        this.Hi = thermalDialogFragment;
    }

    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return this.Hi.a(dialogInterface, i, keyEvent);
    }
}
