package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import com.android.camera.scene.FunctionMiAlgoASDEngine;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseSuperNight implements Function<CaptureResult, CaptureResult> {
    public static final int SUPER_NIGHT = 3;
    public static final String TAG = "FunctionParseSuperNight";
    private boolean mIsSuperNight;
    private WeakReference<Camera2Proxy.SuperNightCallback> mSuperNightCallback;

    public FunctionParseSuperNight(Camera2Proxy.SuperNightCallback superNightCallback) {
        this.mSuperNightCallback = new WeakReference<>(superNightCallback);
    }

    private void updateASDScene(Camera2Proxy.SuperNightCallback superNightCallback, MarshalQueryableASDScene.ASDScene aSDScene) {
        int i = aSDScene.type;
        boolean z = (aSDScene.value & 256) != 0;
        if (i == 3) {
            FunctionMiAlgoASDEngine.LOGD("Algo reporting super night se enable:" + z);
            if (z != this.mIsSuperNight) {
                this.mIsSuperNight = z;
                superNightCallback.onSuperNightChanged(this.mIsSuperNight);
            }
        }
    }

    public CaptureResult apply(CaptureResult captureResult) throws Exception {
        Camera2Proxy.SuperNightCallback superNightCallback = this.mSuperNightCallback.get();
        if (superNightCallback == null) {
            return captureResult;
        }
        if (!superNightCallback.isSupportSuperNight()) {
            this.mIsSuperNight = false;
            superNightCallback.onSuperNightChanged(false);
            return captureResult;
        }
        MarshalQueryableASDScene.ASDScene[] aSDSceneArr = (MarshalQueryableASDScene.ASDScene[]) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.NON_SEMANTIC_SCENE);
        if (aSDSceneArr == null || aSDSceneArr.length <= 0) {
            FunctionMiAlgoASDEngine.LOGD("(" + CaptureResultVendorTags.NON_SEMANTIC_SCENE.getName() + ") asd scene result null!");
            return captureResult;
        }
        for (MarshalQueryableASDScene.ASDScene aSDScene : aSDSceneArr) {
            FunctionMiAlgoASDEngine.LOGD("(NoneSemantics)-->" + aSDScene.toString());
            updateASDScene(superNightCallback, aSDScene);
        }
        return captureResult;
    }
}
