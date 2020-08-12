package com.android.camera.fragment;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class b implements DialogInterface.OnClickListener {
    private final /* synthetic */ CtaNoticeFragment Hi;

    public /* synthetic */ b(CtaNoticeFragment ctaNoticeFragment) {
        this.Hi = ctaNoticeFragment;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.Hi.a(dialogInterface, i);
    }
}
