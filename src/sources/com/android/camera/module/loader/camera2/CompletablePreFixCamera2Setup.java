package com.android.camera.module.loader.camera2;

import android.content.Intent;
import android.support.annotation.WorkerThread;
import android.support.v4.util.Pair;
import com.android.camera.CameraScreenNail;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.db.DbRepository;
import com.android.camera.log.Log;
import com.android.camera.module.Module;
import com.android.camera.module.loader.StartControl;
import com.android.camera.network.util.NetworkUtils;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CompletablePreFixCamera2Setup implements CompletableOnSubscribe, Observer<Camera2Result> {
    private static final String TAG = "prefix";
    private boolean isFromVoiceControl;
    private CameraScreenNail mCameraScreenNail;
    private CompletableEmitter mEmitter;
    private boolean mFromScreenSlide;
    private Intent mIntent;
    private Module mLastMode;
    private boolean mModuleChanged;
    private boolean mNeedBlur;
    private boolean mNeedConfigData;
    private boolean mStartFromKeyguard;

    public CompletablePreFixCamera2Setup(Module module, StartControl startControl, CameraScreenNail cameraScreenNail, Intent intent, boolean z, boolean z2) {
        this.mLastMode = module;
        if (startControl != null) {
            this.mNeedBlur = startControl.mNeedBlurAnimation;
            this.mFromScreenSlide = startControl.mFromScreenSlide;
            this.mNeedConfigData = startControl.mNeedReConfigureData;
        }
        this.mModuleChanged = startControl == null || module == null || (startControl.mTargetMode != module.getModuleIndex() && !isSameModule(startControl.mTargetMode, module.getModuleIndex()));
        this.mCameraScreenNail = cameraScreenNail;
        this.mIntent = intent;
        this.mStartFromKeyguard = z;
        this.isFromVoiceControl = z2;
    }

    private void closeLastModule() {
        Module module = this.mLastMode;
        if (module != null) {
            module.unRegisterProtocol();
            this.mLastMode.onPause();
            this.mLastMode.onStop();
            this.mLastMode.onDestroy();
        }
    }

    private boolean isLastModuleAlive() {
        Module module = this.mLastMode;
        return module != null && module.isCreated();
    }

    private boolean isSameModule(int i, int i2) {
        return (i == 163 || i == 165) && (i2 == 163 || i2 == 165);
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
    }

    public void onNext(Camera2Result camera2Result) {
        this.mEmitter.onComplete();
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
    }

    @Override // io.reactivex.CompletableOnSubscribe
    @WorkerThread
    public void subscribe(CompletableEmitter completableEmitter) {
        int i;
        int i2;
        this.mEmitter = completableEmitter;
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        StringBuilder sb = new StringBuilder();
        sb.append("mModuleChanged ");
        sb.append(this.mModuleChanged);
        sb.append(" LastMode is ");
        Module module = this.mLastMode;
        sb.append(module == null ? "null" : Integer.valueOf(module.getModuleIndex()));
        Log.d(TAG, sb.toString());
        if (this.mModuleChanged) {
            Module module2 = this.mLastMode;
            if (module2 != null) {
                module2.unRegisterModulePersistProtocol();
            }
            HybridZoomingSystem.clearZoomRatioHistory();
        }
        if (this.mNeedBlur) {
            this.mCameraScreenNail.animateModuleCopyTexture(this.mFromScreenSlide);
        }
        if (isLastModuleAlive()) {
            if (this.mNeedConfigData) {
                DataRepository.getInstance().backUp().backupRunning(DataRepository.dataItemRunning(), dataItemGlobal.getDataBackUpKey(this.mLastMode.getModuleIndex()), dataItemGlobal.getLastCameraId(), true);
            }
            closeLastModule();
        }
        Log.d(TAG, "subscribe: mIntent = " + this.mIntent);
        Intent intent = this.mIntent;
        if (intent != null) {
            Pair<Integer, Integer> parseIntent = dataItemGlobal.parseIntent(intent, Boolean.valueOf(this.isFromVoiceControl), this.mStartFromKeyguard, true, true);
            int intValue = parseIntent.first.intValue();
            int intValue2 = parseIntent.second.intValue();
            if (DataRepository.dataItemFeature().c_0x03()) {
                NetworkUtils.tryRequestTTSticker();
            }
            if (DataRepository.dataItemFeature().ye()) {
                DbRepository.dbItemSaveTask().markAllDepartedTask();
            }
            i2 = intValue2;
            i = intValue;
        } else {
            i = dataItemGlobal.getCurrentCameraId();
            i2 = dataItemGlobal.getCurrentMode();
        }
        Camera2OpenManager.getInstance().openCamera(i, i2, this, DataRepository.dataItemFeature().c_0x20_OR_T());
        ParallelSnapshotManager.getInstance().openCamera(i, this);
    }
}
