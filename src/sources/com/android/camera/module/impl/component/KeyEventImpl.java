package com.android.camera.module.impl.component;

import android.view.KeyEvent;
import com.android.camera.ActivityBase;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.Optional;

public class KeyEventImpl implements ModeProtocol.KeyEvent {
    public static final int KEYCODE_MODE_SWITCH = 259;
    public static final int KEYCODE_SHUTTER = 24;
    public static final int KEYCODE_SWITCH_LENS = 119;
    public static final int KEYCODE_ZOOM_IN = 168;
    public static final int KEYCODE_ZOOM_OUT = 169;
    private static final String TAG = "KeyEventImpl";
    private final ActivityBase mActivity;

    public KeyEventImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
    }

    private void changeMode() {
        ModeProtocol.ModeChangeController modeChangeController;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && baseModule.get().isCreated()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (((moduleIndex != 161 && moduleIndex != 162 && moduleIndex != 172 && moduleIndex != 174) || !baseModule.get().isRecording()) && (modeChangeController = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179)) != null) {
                int i = 163;
                if (DataRepository.dataItemGlobal().getCurrentMode() == 163) {
                    i = 162;
                }
                modeChangeController.changeModeByNewMode(i, 0);
            }
        }
    }

    private void changeZoom(int i) {
        ModeProtocol.ZoomProtocol zoomProtocol;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && baseModule.get().isCreated() && baseModule.get().isZoomEnabled() && DataRepository.dataItemGlobal().getCurrentCameraId() != Camera2DataContainer.getInstance().getFrontCameraId() && (zoomProtocol = (ModeProtocol.ZoomProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(170)) != null) {
            if (i == 168) {
                zoomProtocol.zoomIn(0.1f);
            } else if (i == 169) {
                zoomProtocol.zoomOut(0.1f);
            }
        }
    }

    public static ModeProtocol.KeyEvent create(ActivityBase activityBase) {
        return new KeyEventImpl(activityBase);
    }

    private Optional<BaseModule> getBaseModule() {
        ActivityBase activityBase = this.mActivity;
        return activityBase == null ? Optional.empty() : Optional.ofNullable((BaseModule) activityBase.getCurrentModule());
    }

    private void switchCameraLens() {
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && baseModule.get().isCreated()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (((moduleIndex != 161 && moduleIndex != 162 && moduleIndex != 172 && moduleIndex != 174) || !baseModule.get().isRecording()) && (bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)) != null) {
                bottomMenuProtocol.onSwitchCameraPicker();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.KeyEvent
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (!baseModule.isPresent() || !baseModule.get().isCreated() || baseModule.get().isIgnoreTouchEvent()) {
            return false;
        }
        String str = TAG;
        Log.d(str, "KeyEventImpl-onKeyDown:" + i);
        if (i == 168) {
            changeZoom(168);
            return true;
        } else if (i != 169) {
            return false;
        } else {
            changeZoom(169);
            return true;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.KeyEvent
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (!baseModule.isPresent() || !baseModule.get().isCreated() || baseModule.get().isIgnoreTouchEvent()) {
            return false;
        }
        String str = TAG;
        Log.d(str, "KeyEventImpl-onKeyUp:" + i);
        if (i == 119) {
            switchCameraLens();
            return true;
        } else if (i == 259) {
            changeMode();
            return true;
        } else if (i == 168) {
            changeZoom(168);
            return true;
        } else if (i != 169) {
            return false;
        } else {
            changeZoom(169);
            return true;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(239, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(239, this);
    }
}
