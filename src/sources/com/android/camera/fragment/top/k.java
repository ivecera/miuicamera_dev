package com.android.camera.fragment.top;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class k implements DialogInterface.OnClickListener {
    private final /* synthetic */ FragmentTopAlert Hi;

    public /* synthetic */ k(FragmentTopAlert fragmentTopAlert) {
        this.Hi = fragmentTopAlert;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.Hi.j(dialogInterface, i);
    }
}
