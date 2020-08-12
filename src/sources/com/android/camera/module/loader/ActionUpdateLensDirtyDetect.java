package com.android.camera.module.loader;

import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.module.BaseModule;
import com.android.camera2.CameraCapabilities;
import io.reactivex.functions.Action;
import java.lang.ref.WeakReference;

public class ActionUpdateLensDirtyDetect implements Action {
    private boolean mIsOnCreate;
    private WeakReference<BaseModule> mModuleWeakReference;

    public ActionUpdateLensDirtyDetect(BaseModule baseModule, boolean z) {
        this.mModuleWeakReference = new WeakReference<>(baseModule);
        this.mIsOnCreate = z;
    }

    @Override // io.reactivex.functions.Action
    public void run() throws Exception {
        BaseModule baseModule = this.mModuleWeakReference.get();
        if (baseModule != null) {
            if (this.mIsOnCreate) {
                CameraSettings.setLensDirtyDetectEnable(false);
            } else {
                CameraCapabilities cameraCapabilities = baseModule.getCameraCapabilities();
                if (cameraCapabilities == null || cameraCapabilities.getMiAlgoASDVersion() < 2.0f) {
                    CameraSettings.addLensDirtyDetectedTimes();
                } else {
                    DataItemConfig dataNormalItemConfig = DataRepository.dataNormalItemConfig();
                    DataProvider.ProviderEditor editor = dataNormalItemConfig.editor();
                    editor.putBoolean(CameraSettings.KEY_LENS_DIRTY_DETECT_ENABLED, false);
                    if (dataNormalItemConfig.contains(CameraSettings.KEY_LENS_DIRTY_DETECT_TIMES)) {
                        dataNormalItemConfig.remove(CameraSettings.KEY_LENS_DIRTY_DETECT_TIMES);
                    }
                    if (dataNormalItemConfig.contains(CameraSettings.KEY_LENS_DIRTY_DETECT_DATE)) {
                        dataNormalItemConfig.remove(CameraSettings.KEY_LENS_DIRTY_DETECT_DATE);
                    }
                    editor.apply();
                }
            }
            baseModule.updatePreferenceTrampoline(61);
            baseModule.updateLensDirtyDetect(true);
        }
    }
}
