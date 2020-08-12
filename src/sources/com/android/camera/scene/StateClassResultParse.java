package com.android.camera.scene;

import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import java.lang.ref.WeakReference;

public class StateClassResultParse extends ASDResultParse {
    public StateClassResultParse(WeakReference<BaseModule> weakReference) {
        super(weakReference);
    }

    private void updateASDScene(MarshalQueryableASDScene.ASDScene aSDScene) {
        int i = aSDScene.type;
        int i2 = aSDScene.value;
        if (i == 4) {
            if (!MiAlgoAsdSceneProfile.isSceneChange(i, i2)) {
                FunctionMiAlgoASDEngine.LOGD("ON_TRIPOD scene no change");
                return;
            }
            MarshalQueryableASDScene.ASDScene[] aSDSceneArr = {aSDScene};
            BaseModule baseModule = ((ASDResultParse) this).mModule.get();
            if (baseModule != null && (baseModule instanceof Camera2Module)) {
                Camera2Module camera2Module = (Camera2Module) baseModule;
                camera2Module.setAsdScenes(aSDSceneArr);
                camera2Module.updatePreferenceInWorkThread(59);
            }
        }
    }

    public void parseMiAlgoAsdResult(MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        if (aSDSceneArr != null && aSDSceneArr.length > 0) {
            FunctionMiAlgoASDEngine.LOGD("(StateClass)scenes size:" + aSDSceneArr.length);
            for (MarshalQueryableASDScene.ASDScene aSDScene : aSDSceneArr) {
                FunctionMiAlgoASDEngine.LOGD("(StateClass)->" + aSDScene.toString());
                updateASDScene(aSDScene);
            }
        }
    }
}
