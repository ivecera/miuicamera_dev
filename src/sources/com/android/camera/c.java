package com.android.camera;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class c implements DialogInterface.OnClickListener {
    private final /* synthetic */ CameraPreferenceActivity Hi;

    public /* synthetic */ c(CameraPreferenceActivity cameraPreferenceActivity) {
        this.Hi = cameraPreferenceActivity;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.Hi.e(dialogInterface, i);
    }
}
