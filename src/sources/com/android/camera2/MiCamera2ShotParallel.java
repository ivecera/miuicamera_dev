package com.android.camera2;

import android.graphics.Rect;
import android.hardware.camera2.CaptureResult;
import android.media.Image;
import android.util.Size;
import android.view.Surface;
import com.android.camera.CameraSize;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.parallel.AlgoConnector;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.GraphDescriptorBean;

public abstract class MiCamera2ShotParallel<T> extends MiCamera2Shot<T> {
    private static final String TAG = "ShotParallelBase";
    protected final Rect mActiveArraySize;
    CameraSize mCapturedImageSize;
    protected CaptureResult mPreviewCaptureResult;

    MiCamera2ShotParallel(MiCamera2 miCamera2) {
        super(miCamera2);
        this.mActiveArraySize = miCamera2.getCapabilities().getActiveArraySize();
    }

    private boolean hasDualCamera() {
        return ((MiCamera2Shot) this).mMiCamera.getId() == Camera2DataContainer.getInstance().getSATFrontCameraId() || ((MiCamera2Shot) this).mMiCamera.getId() == Camera2DataContainer.getInstance().getBokehFrontCameraId() || ((MiCamera2Shot) this).mMiCamera.getId() == Camera2DataContainer.getInstance().getSATCameraId() || ((MiCamera2Shot) this).mMiCamera.getId() == Camera2DataContainer.getInstance().getBokehCameraId() || ((MiCamera2Shot) this).mMiCamera.getId() == Camera2DataContainer.getInstance().getUltraWideBokehCameraId();
    }

    /* access modifiers changed from: package-private */
    public void configParallelSession(Size size) {
        GraphDescriptorBean graphDescriptorBean;
        int cameraCombinationMode = CameraDeviceUtil.getCameraCombinationMode(Camera2DataContainer.getInstance().getRoleIdByActualId(((MiCamera2Shot) this).mMiCamera.getId()));
        if (ModuleManager.isPortraitModule()) {
            int i = hasDualCamera() ? 2 : 1;
            Log.d(TAG, "configParallelSession: inputStreamNum = " + i);
            graphDescriptorBean = new GraphDescriptorBean(32770, i, true, cameraCombinationMode);
        } else {
            graphDescriptorBean = ModuleManager.isProPhotoModule() ? new GraphDescriptorBean(32771, 1, true, cameraCombinationMode) : ModuleManager.isUltraPixel() ? new GraphDescriptorBean(33011, 1, true, cameraCombinationMode) : new GraphDescriptorBean(0, 1, true, cameraCombinationMode);
        }
        int width = size.getWidth();
        int height = size.getHeight();
        Log.d(TAG, "configParallelSession: " + width + "x" + height);
        AlgoConnector.getInstance().getLocalBinder().configCaptureSession(new BufferFormat(width, height, 35, graphDescriptorBean));
        this.mCapturedImageSize = new CameraSize(width, height);
    }

    /* access modifiers changed from: package-private */
    public void configParallelSession(Size size, int i) {
        GraphDescriptorBean graphDescriptorBean;
        if (ModuleManager.isPortraitModule()) {
            int i2 = hasDualCamera() ? 2 : 1;
            Log.d(TAG, "configParallelSession: inputStreamNum = " + i2);
            graphDescriptorBean = new GraphDescriptorBean(32770, i2, true, i);
        } else {
            graphDescriptorBean = new GraphDescriptorBean(0, 1, true, i);
        }
        int width = size.getWidth();
        int height = size.getHeight();
        Log.d(TAG, "configParallelSession: " + width + "x" + height);
        AlgoConnector.getInstance().getLocalBinder().configCaptureSession(new BufferFormat(width, height, 35, graphDescriptorBean));
        this.mCapturedImageSize = new CameraSize(width, height);
    }

    /* access modifiers changed from: protected */
    public Surface getMainCaptureSurface() {
        return ((MiCamera2Shot) this).mMiCamera.getMainCaptureSurface();
    }

    /* access modifiers changed from: protected */
    public boolean isIn3OrMoreSatMode() {
        return ((MiCamera2Shot) this).mMiCamera.isIn3OrMoreSatMode();
    }

    /* access modifiers changed from: protected */
    public boolean isInMultiSurfaceSatMode() {
        return ((MiCamera2Shot) this).mMiCamera.isInMultiSurfaceSatMode();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void notifyResultData(T t) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
    }
}
