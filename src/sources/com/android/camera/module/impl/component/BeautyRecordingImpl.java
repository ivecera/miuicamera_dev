package com.android.camera.module.impl.component;

import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.ArrayList;
import java.util.Iterator;

public class BeautyRecordingImpl implements ModeProtocol.BeautyRecording {
    private ArrayList<ModeProtocol.HandleBeautyRecording> recordingArrayList = new ArrayList<>();

    public static BeautyRecordingImpl create() {
        return new BeautyRecordingImpl();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BeautyRecording
    public <P extends ModeProtocol.HandleBeautyRecording> void addBeautyStack(P p) {
        this.recordingArrayList.add(p);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BeautyRecording
    public void handleAngleChang(float f2) {
        Iterator<ModeProtocol.HandleBeautyRecording> it = this.recordingArrayList.iterator();
        while (it.hasNext()) {
            it.next().onAngleChanged(f2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BeautyRecording
    public void handleBeautyRecordingStart() {
        Iterator<ModeProtocol.HandleBeautyRecording> it = this.recordingArrayList.iterator();
        while (it.hasNext()) {
            it.next().onBeautyRecordingStart();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BeautyRecording
    public void handleBeautyRecordingStop() {
        Iterator<ModeProtocol.HandleBeautyRecording> it = this.recordingArrayList.iterator();
        while (it.hasNext()) {
            it.next().onBeautyRecordingStop();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(173, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BeautyRecording
    public <P extends ModeProtocol.HandleBeautyRecording> void removeBeautyStack(P p) {
        this.recordingArrayList.remove(p);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        this.recordingArrayList.clear();
        ModeCoordinatorImpl.getInstance().detachProtocol(173, this);
    }
}
