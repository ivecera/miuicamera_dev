package com.android.camera;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    private final /* synthetic */ CameraPreferenceActivity Hi;

    public /* synthetic */ g(CameraPreferenceActivity cameraPreferenceActivity) {
        this.Hi = cameraPreferenceActivity;
    }

    public final void run() {
        this.Hi.installQRCodeReceiver();
    }
}
