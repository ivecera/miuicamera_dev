package com.android.camera.fragment.dual;

import android.view.View;
import com.android.camera.data.data.ComponentData;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.protocol.ModeProtocol;

public class FragmentDualStereo extends BaseFragment implements View.OnClickListener, View.OnLongClickListener, ManuallyListener, ModeProtocol.HandleBackTrace, ModeProtocol.DualController {
    public static final int FRAGMENT_INFO = 4085;

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4085;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void hideSlideView() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void hideZoomButton() {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public boolean isSlideVisible() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public boolean isZoomSliderViewIdle() {
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public boolean isZoomVisible() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        return false;
    }

    public void onClick(View view) {
    }

    public boolean onLongClick(View view) {
        return false;
    }

    @Override // com.android.camera.fragment.manually.ManuallyListener
    public void onManuallyDataChanged(ComponentData componentData, String str, String str2, boolean z, int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void setImmersiveModeEnabled(boolean z) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void setRecordingOrPausing(boolean z) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void showZoomButton() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public boolean updateSlideAndZoomRatio(int i) {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void updateZoomRatio(int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public int visibleHeight() {
        return 0;
    }
}
