package com.android.camera;

import com.android.camera.aftersales.AftersalesManager;
import com.android.camera.log.Log;
import com.android.camera.module.Module;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera2.Camera2Proxy;
import java.lang.ref.WeakReference;

public class CameraErrorCallbackImpl implements Camera2Proxy.CameraErrorCallback {
    private static final String TAG = "CameraErrorCallback";
    private WeakReference<ActivityBase> mWeakActivity;

    public CameraErrorCallbackImpl(ActivityBase activityBase) {
        this.mWeakActivity = new WeakReference<>(activityBase);
    }

    @Override // com.android.camera2.Camera2Proxy.CameraErrorCallback
    public void onCameraError(Camera2Proxy camera2Proxy, int i) {
        if (i == 5) {
            Log.e(TAG, "onCameraError: camera service error");
        } else if (i == 4) {
            Log.e(TAG, "onCameraError: camera device error");
        } else if (i == 3) {
            Log.e(TAG, "onCameraError: camera disabled");
        } else if (i == 2) {
            Log.e(TAG, "onCameraError: max camera in use");
        } else if (i == 1) {
            Log.e(TAG, "onCameraError: camera in use");
        } else {
            Log.e(TAG, "onCameraError: other error " + i);
        }
        CameraStatUtils.trackCameraError("" + i);
        AftersalesManager.getInstance().count(System.currentTimeMillis(), 5, CameraSettings.getCameraId());
        ActivityBase activityBase = this.mWeakActivity.get();
        if (activityBase != null) {
            Module currentModule = activityBase.getCurrentModule();
            if (currentModule != null && currentModule.isCreated()) {
                currentModule.notifyError();
                return;
            }
            return;
        }
        Log.d(TAG, "mActivity has been collected.");
    }
}
