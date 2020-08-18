package com.android.camera.fragment.dialog;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class h implements DialogInterface.OnClickListener {
    private final /* synthetic */ FragmentLiveReview Hi;

    public /* synthetic */ h(FragmentLiveReview fragmentLiveReview) {
        this.Hi = fragmentLiveReview;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.Hi.g(dialogInterface, i);
    }
}
