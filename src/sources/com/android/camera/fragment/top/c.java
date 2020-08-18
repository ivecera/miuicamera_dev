package com.android.camera.fragment.top;

import com.android.camera.ui.ToggleSwitch;

/* compiled from: lambda */
public final /* synthetic */ class c implements ToggleSwitch.OnCheckedChangeListener {
    public static final /* synthetic */ c INSTANCE = new c();

    private /* synthetic */ c() {
    }

    @Override // com.android.camera.ui.ToggleSwitch.OnCheckedChangeListener
    public final void onCheckedChanged(ToggleSwitch toggleSwitch, boolean z) {
        FragmentTopAlert.a(toggleSwitch, z);
    }
}
