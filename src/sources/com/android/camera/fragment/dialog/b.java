package com.android.camera.fragment.dialog;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class b implements DialogInterface.OnClickListener {
    private final /* synthetic */ FragmentLiveReview Hi;

    public /* synthetic */ b(FragmentLiveReview fragmentLiveReview) {
        this.Hi = fragmentLiveReview;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.Hi.h(dialogInterface, i);
    }
}
