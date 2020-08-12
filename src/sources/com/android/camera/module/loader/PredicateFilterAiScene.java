package com.android.camera.module.loader;

import com.android.camera.module.Camera2Module;
import io.reactivex.functions.Predicate;
import java.lang.ref.WeakReference;

public class PredicateFilterAiScene implements Predicate<Integer> {
    private static final String TAG = "PredicateFilterAiScene";
    private int mCurrentDetectedScene;
    private boolean mIsSupportIDCardMode;
    private long mLastChangeSceneTime = 0;
    private WeakReference<Camera2Module> mModuleWeakReference;

    public PredicateFilterAiScene(Camera2Module camera2Module, boolean z) {
        this.mModuleWeakReference = new WeakReference<>(camera2Module);
        this.mIsSupportIDCardMode = z;
    }

    public boolean test(Integer num) {
        Camera2Module camera2Module = this.mModuleWeakReference.get();
        if (camera2Module != null && !camera2Module.isDoingAction() && camera2Module.isAlive()) {
            if (this.mCurrentDetectedScene == num.intValue() || this.mCurrentDetectedScene != 38 || !this.mIsSupportIDCardMode) {
                if (this.mCurrentDetectedScene != num.intValue() && System.currentTimeMillis() - this.mLastChangeSceneTime > 300) {
                    this.mCurrentDetectedScene = num.intValue();
                    this.mLastChangeSceneTime = System.currentTimeMillis();
                    return true;
                }
            } else if (System.currentTimeMillis() - this.mLastChangeSceneTime <= 3000) {
                return false;
            } else {
                this.mCurrentDetectedScene = num.intValue();
                this.mLastChangeSceneTime = System.currentTimeMillis();
                return true;
            }
        }
        return false;
    }
}
