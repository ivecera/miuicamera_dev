package com.android.camera.module.loader;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.support.annotation.NonNull;
import com.android.camera.CameraAppImpl;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2OpenManager;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class SurfaceCreatedOnSubScribe implements SingleOnSubscribe<Boolean>, SurfaceCreatedCallback {
    /* access modifiers changed from: private */
    public SingleEmitter<Boolean> mSingleEmitter;
    private SurfaceStateListener mSurfaceStateListener;

    public SurfaceCreatedOnSubScribe(SurfaceStateListener surfaceStateListener) {
        this.mSurfaceStateListener = surfaceStateListener;
    }

    private void onSurfaceCreated(SingleEmitter<Boolean> singleEmitter) {
        this.mSingleEmitter = singleEmitter;
        openCamera2();
    }

    @SuppressLint({"MissingPermission"})
    @TargetApi(21)
    private void openCamera2() {
        CameraManager cameraManager = (CameraManager) CameraAppImpl.getAndroidContext().getSystemService("camera");
        String[] strArr = new String[0];
        try {
            strArr = cameraManager.getCameraIdList();
            cameraManager.openCamera(strArr[0], new CameraDevice.StateCallback() {
                /* class com.android.camera.module.loader.SurfaceCreatedOnSubScribe.AnonymousClass1 */

                public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                }

                public void onError(@NonNull CameraDevice cameraDevice, int i) {
                }

                public void onOpened(@NonNull CameraDevice cameraDevice) {
                    SurfaceCreatedOnSubScribe.this.mSingleEmitter.onSuccess(true);
                }
            }, Camera2OpenManager.getInstance().getCameraHandler());
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
        }
        for (String str : strArr) {
            Log.e("ids:", str + "");
        }
    }

    @Override // com.android.camera.module.loader.SurfaceCreatedCallback
    public void onGlSurfaceCreated() {
        SingleEmitter<Boolean> singleEmitter = this.mSingleEmitter;
        if (singleEmitter != null && !singleEmitter.isDisposed()) {
            onSurfaceCreated(this.mSingleEmitter);
            this.mSingleEmitter = null;
        }
    }

    @Override // io.reactivex.SingleOnSubscribe
    public void subscribe(SingleEmitter<Boolean> singleEmitter) throws Exception {
        if (this.mSurfaceStateListener.hasSurface()) {
            this.mSingleEmitter = null;
            onSurfaceCreated(singleEmitter);
            return;
        }
        this.mSingleEmitter = singleEmitter;
    }
}
