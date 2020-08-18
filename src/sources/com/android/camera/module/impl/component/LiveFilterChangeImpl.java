package com.android.camera.module.impl.component;

import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.ss.android.vesdk.TERecorder;

public class LiveFilterChangeImpl implements ModeProtocol.FilterProtocol {
    private TERecorder mRecorder;

    public LiveFilterChangeImpl(TERecorder tERecorder) {
        this.mRecorder = tERecorder;
    }

    @Override // com.android.camera.protocol.ModeProtocol.FilterProtocol
    public void onFilterChanged(int i, int i2) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(165, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(165, this);
    }
}
