package com.android.camera;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    private final /* synthetic */ CameraPreferenceActivity Hi;

    public /* synthetic */ f(CameraPreferenceActivity cameraPreferenceActivity) {
        this.Hi = cameraPreferenceActivity;
    }

    public final void run() {
        this.Hi.restorePreferences();
    }
}
