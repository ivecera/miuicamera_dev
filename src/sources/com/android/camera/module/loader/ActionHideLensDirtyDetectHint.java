package com.android.camera.module.loader;

import com.android.camera.Camera;
import com.android.camera.module.BaseModule;
import io.reactivex.functions.Action;
import java.lang.ref.WeakReference;

public class ActionHideLensDirtyDetectHint implements Action {
    private WeakReference<BaseModule> mModuleWeakReference;

    public ActionHideLensDirtyDetectHint(BaseModule baseModule) {
        this.mModuleWeakReference = new WeakReference<>(baseModule);
    }

    @Override // io.reactivex.functions.Action
    public void run() throws Exception {
        Camera activity;
        BaseModule baseModule = this.mModuleWeakReference.get();
        if (baseModule != null && (activity = baseModule.getActivity()) != null) {
            activity.hideLensDirtyDetectedHint();
        }
    }
}
