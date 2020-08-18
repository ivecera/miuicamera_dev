package com.android.camera;

import com.android.camera.ThermalDetector;

/* compiled from: lambda */
public final /* synthetic */ class a implements ThermalDetector.OnThermalNotificationListener {
    private final /* synthetic */ Camera Hi;

    public /* synthetic */ a(Camera camera) {
        this.Hi = camera;
    }

    @Override // com.android.camera.ThermalDetector.OnThermalNotificationListener
    public final void onThermalNotification(int i) {
        this.Hi.j(i);
    }
}
