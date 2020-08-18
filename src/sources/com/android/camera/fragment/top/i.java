package com.android.camera.fragment.top;

import com.android.camera.ui.ToggleSwitch;

/* compiled from: lambda */
public final /* synthetic */ class i implements ToggleSwitch.OnCheckedChangeListener {
    public static final /* synthetic */ i INSTANCE = new i();

    private /* synthetic */ i() {
    }

    @Override // com.android.camera.ui.ToggleSwitch.OnCheckedChangeListener
    public final void onCheckedChanged(ToggleSwitch toggleSwitch, boolean z) {
        FragmentTopAlert.b(toggleSwitch, z);
    }
}
