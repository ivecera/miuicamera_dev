package com.android.camera.snap;

import com.android.camera.ThermalDetector;

/* compiled from: lambda */
public final /* synthetic */ class a implements ThermalDetector.OnThermalNotificationListener {
    public static final /* synthetic */ a INSTANCE = new a();

    private /* synthetic */ a() {
    }

    @Override // com.android.camera.ThermalDetector.OnThermalNotificationListener
    public final void onThermalNotification(int i) {
        SnapService.i(i);
    }
}
