package com.android.camera.module.impl.component;

import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.ss.android.vesdk.TERecorder;

public class LiveBeautyChangeImpl implements ModeProtocol.OnShineChangedProtocol {
    private TERecorder mRecorder;

    public LiveBeautyChangeImpl(TERecorder tERecorder) {
        this.mRecorder = tERecorder;
    }

    @Override // com.android.camera.protocol.ModeProtocol.OnShineChangedProtocol
    public void onShineChanged(boolean z, int i) {
        this.mRecorder.setBeautyFace(3, FileUtils.BEAUTY_12_DIR);
        this.mRecorder.setBeautyFaceIntensity(0.35f, 0.35f);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(234, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(234, this);
    }
}
