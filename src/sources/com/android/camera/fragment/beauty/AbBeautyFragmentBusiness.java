package com.android.camera.fragment.beauty;

import com.android.camera.R;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public abstract class AbBeautyFragmentBusiness implements IBeautyFragmentBusiness {
    @Override // com.android.camera.fragment.beauty.IBeautyBusiness
    public Object operate(Object obj) {
        return null;
    }

    public boolean removeFragmentBeauty(int i) {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate == null || baseDelegate.getActiveFragment(R.id.bottom_beauty) != 251) {
            return false;
        }
        baseDelegate.delegateEvent(10);
        ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onSwitchCameraActionMenu(0);
        return true;
    }
}
