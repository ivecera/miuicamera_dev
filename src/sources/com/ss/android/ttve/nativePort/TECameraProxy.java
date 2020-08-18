package com.ss.android.ttve.nativePort;

import android.graphics.SurfaceTexture;
import android.support.annotation.Keep;
import com.ss.android.ttve.utils.CameraInstance;
import com.ss.android.vesdk.VELogUtil;

@Keep
public class TECameraProxy extends CameraInstance {
    private static final String TAG = "TECameraProxy";
    /* access modifiers changed from: private */
    public static long mNativeAddr;
    private CameraInstance.CameraOpenCallback mCameraOpenCallback = new CameraInstance.CameraOpenCallback() {
        /* class com.ss.android.ttve.nativePort.TECameraProxy.AnonymousClass2 */

        @Override // com.ss.android.ttve.utils.CameraInstance.CameraOpenCallback
        public void cameraReady() {
            int unused = TECameraProxy.this.nativeOnCameraCreate(TECameraProxy.mNativeAddr, 0);
        }
    };
    private int mCameraTextureID = 0;
    private SurfaceTexture mSurfaceTexture;
    /* access modifiers changed from: private */
    public boolean mbSurfaceTextureReleased = false;

    static {
        TENativeLibsLoader.loadLibrary();
    }

    public static TECameraProxy create(long j) {
        mNativeAddr = j;
        return new TECameraProxy();
    }

    /* access modifiers changed from: private */
    public native int nativeOnCameraCreate(long j, int i);

    /* access modifiers changed from: private */
    public native int nativeOnFrameAvailable(long j, SurfaceTexture surfaceTexture);

    public synchronized void getNextFrame() {
        this.mSurfaceTexture.updateTexImage();
    }

    public synchronized boolean open(int i) {
        return tryOpenCamera(this.mCameraOpenCallback, i);
    }

    public int setSurfaceTexture(Object obj, int i) {
        VELogUtil.d(TAG, "setSurfaceTexture...");
        if (i == 0) {
            VELogUtil.e(TAG, "Invalid Texture ID!");
            return -100;
        } else if (obj instanceof SurfaceTexture) {
            this.mSurfaceTexture = (SurfaceTexture) obj;
            this.mSurfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
                /* class com.ss.android.ttve.nativePort.TECameraProxy.AnonymousClass1 */

                public synchronized void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    if (!TECameraProxy.this.mbSurfaceTextureReleased) {
                        int unused = TECameraProxy.this.nativeOnFrameAvailable(TECameraProxy.mNativeAddr, surfaceTexture);
                    }
                }
            });
            return 0;
        } else {
            VELogUtil.e(TAG, "Invalid SurfaceTexture!");
            return -100;
        }
    }

    public synchronized void startPreview() {
        startPreview(this.mSurfaceTexture);
        this.mbSurfaceTextureReleased = false;
    }

    @Override // com.ss.android.ttve.utils.CameraInstance
    public synchronized void stopCamera() {
        this.mbSurfaceTextureReleased = true;
        this.mSurfaceTexture.setOnFrameAvailableListener(null);
        super.stopCamera();
    }

    public synchronized boolean switchCamera(int i) {
        if (!tryOpenCamera(this.mCameraOpenCallback, i)) {
            return false;
        }
        startPreview(this.mSurfaceTexture);
        return true;
    }
}
