package com.android.camera.fragment.beauty;

import android.view.View;
import android.view.ViewGroup;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.protocol.ModeProtocol;

public class FragmentPopuEyeLightTip extends BaseFragment {
    public static final int FRAGMENT_INFO = 4089;

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4089;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.eye_light_popu_tip;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight() + getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
    }
}
