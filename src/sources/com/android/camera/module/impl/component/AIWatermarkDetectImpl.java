package com.android.camera.module.impl.component;

import com.android.camera.aiwatermark.lisenter.IASDListener;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class AIWatermarkDetectImpl implements ModeProtocol.AIWatermarkDetect {
    private int mASDResult = 0;
    private IASDListener mListener = null;

    public static ModeProtocol.BaseProtocol create() {
        return new AIWatermarkDetectImpl();
    }

    @Override // com.android.camera.protocol.ModeProtocol.AIWatermarkDetect
    public void onASDChange(int i) {
        if (this.mASDResult != i) {
            this.mASDResult = i;
            IASDListener iASDListener = this.mListener;
            if (iASDListener != null) {
                iASDListener.onASDChange(i);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(254, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AIWatermarkDetect
    public void resetResult() {
        this.mASDResult = 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.AIWatermarkDetect
    public void setListener(IASDListener iASDListener) {
        this.mListener = iASDListener;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(254, this);
    }
}
