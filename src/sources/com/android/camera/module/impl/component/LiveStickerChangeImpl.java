package com.android.camera.module.impl.component;

import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.ss.android.vesdk.TERecorder;

public class LiveStickerChangeImpl implements ModeProtocol.StickerProtocol {
    private TERecorder mRecorder;

    public LiveStickerChangeImpl(TERecorder tERecorder) {
        this.mRecorder = tERecorder;
    }

    @Override // com.android.camera.protocol.ModeProtocol.StickerProtocol
    public void onStickerChanged(String str) {
        TERecorder tERecorder = this.mRecorder;
        tERecorder.switchEffect(FileUtils.STICKER_RESOURCE_DIR + str);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(178, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(178, this);
    }
}
