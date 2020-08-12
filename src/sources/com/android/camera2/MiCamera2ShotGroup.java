package com.android.camera2;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.media.Image;
import android.media.ImageReader;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.Util;
import com.android.camera.groupshot.GroupShot;
import com.android.camera.log.Log;
import com.android.camera.module.SaveOutputImageTask;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.storage.SaverCallback;
import com.android.camera.storage.Storage;
import com.android.camera2.Camera2Proxy;
import com.android.gallery3d.exif.ExifHelper;
import com.mi.config.b;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelCallback;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import java.util.Locale;

public class MiCamera2ShotGroup extends MiCamera2Shot<byte[]> {
    /* access modifiers changed from: private */
    public static final String TAG = "MiCamera2ShotGroup";
    private final int MAX_GROUP_FACE_NUM = 10;
    private CaptureResult mCaptureResult;
    private Context mContext;
    /* access modifiers changed from: private */
    public ParallelTaskData mCurrentParallelTaskData;
    private GroupShot mGroupShot;
    private String mGroupShotFolderName;
    /* access modifiers changed from: private */
    public int mReceivedJpegCallbackNum;
    /* access modifiers changed from: private */
    public int mTotalJpegCallbackNum;

    public MiCamera2ShotGroup(MiCamera2 miCamera2, int i, Context context, CaptureResult captureResult) {
        super(miCamera2);
        this.mTotalJpegCallbackNum = i;
        this.mContext = context;
        this.mCaptureResult = captureResult;
    }

    private int getGroupShotMaxImage() {
        Face[] faceArr = (Face[]) this.mCaptureResult.get(CaptureResult.STATISTICS_FACES);
        return Util.clamp((faceArr != null ? faceArr.length : 0) + 1, 2, 4);
    }

    private int getGroupShotNum() {
        if (Util.isMemoryRich(this.mContext)) {
            return getGroupShotMaxImage();
        }
        return 1;
    }

    private void initGroupShot(int i) {
        GroupShot groupShot = this.mGroupShot;
        if (groupShot == null || groupShot.isUsed()) {
            this.mGroupShot = new GroupShot();
        }
        int deviceOrientation = ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().getDeviceOrientation();
        if (deviceOrientation == -1) {
            deviceOrientation = 0;
        }
        CameraSize pictureSize = ((MiCamera2Shot) this).mMiCamera.getPictureSize();
        CameraSize previewSize = ((MiCamera2Shot) this).mMiCamera.getPreviewSize();
        if (deviceOrientation % 180 != 0 || !b.pl()) {
            this.mGroupShot.initialize(i, 10, pictureSize.getWidth(), pictureSize.getHeight(), previewSize.getWidth(), previewSize.getHeight());
        } else {
            this.mGroupShot.initialize(i, 10, pictureSize.getHeight(), pictureSize.getWidth(), previewSize.getHeight(), previewSize.getWidth());
        }
    }

    private void prepareGroupShot() {
        this.mTotalJpegCallbackNum = getGroupShotNum();
        initGroupShot(this.mTotalJpegCallbackNum);
        GroupShot groupShot = this.mGroupShot;
        if (groupShot != null) {
            groupShot.attach_start(1);
        } else {
            this.mTotalJpegCallbackNum = 1;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotGroup.AnonymousClass1 */

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                String access$100 = MiCamera2ShotGroup.TAG;
                Log.d(access$100, "onCaptureCompleted: " + totalCaptureResult.getFrameNumber());
                MiCamera2ShotGroup miCamera2ShotGroup = MiCamera2ShotGroup.this;
                ((MiCamera2Shot) miCamera2ShotGroup).mMiCamera.onCapturePictureFinished(miCamera2ShotGroup.mReceivedJpegCallbackNum >= MiCamera2ShotGroup.this.mTotalJpegCallbackNum, MiCamera2ShotGroup.this);
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                String access$100 = MiCamera2ShotGroup.TAG;
                Log.e(access$100, "onCaptureFailed: " + captureFailure.getReason());
                MiCamera2ShotGroup miCamera2ShotGroup = MiCamera2ShotGroup.this;
                ((MiCamera2Shot) miCamera2ShotGroup).mMiCamera.onCapturePictureFinished(false, miCamera2ShotGroup);
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                if (0 == MiCamera2ShotGroup.this.mCurrentParallelTaskData.getTimestamp()) {
                    MiCamera2ShotGroup.this.mCurrentParallelTaskData.setTimestamp(j);
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder createCaptureRequest = ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(2);
        ImageReader photoImageReader = ((MiCamera2Shot) this).mMiCamera.getPhotoImageReader();
        createCaptureRequest.addTarget(photoImageReader.getSurface());
        String str = TAG;
        Log.d(str, "size=" + photoImageReader.getWidth() + "x" + photoImageReader.getHeight());
        if (!isInQcfaMode() || Camera2DataContainer.getInstance().getBokehFrontCameraId() == ((MiCamera2Shot) this).mMiCamera.getId()) {
            createCaptureRequest.addTarget(((MiCamera2Shot) this).mMiCamera.getPreviewSurface());
        }
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) ((MiCamera2Shot) this).mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        ((MiCamera2Shot) this).mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(byte[] bArr) {
        int i;
        int i2;
        this.mReceivedJpegCallbackNum++;
        ParallelCallback parallelCallback = getParallelCallback();
        if (parallelCallback == null) {
            Log.w(TAG, "notifyResultData: null parallel callback");
            return;
        }
        ParallelTaskDataParameter dataParameter = this.mCurrentParallelTaskData.getDataParameter();
        if (this.mGroupShotFolderName == null) {
            this.mGroupShotFolderName = Util.createJpegName(System.currentTimeMillis()) + dataParameter.getSuffix();
        }
        int attach = this.mGroupShot.attach(bArr);
        if (Util.sIsDumpOrigJpg) {
            Storage.saveOriginalPic(bArr, this.mReceivedJpegCallbackNum, this.mGroupShotFolderName);
        }
        Log.v(TAG, String.format(Locale.ENGLISH, "groupShot attach() = 0x%08x index=%d", Integer.valueOf(attach), Integer.valueOf(this.mReceivedJpegCallbackNum)));
        int i3 = this.mReceivedJpegCallbackNum;
        if (i3 >= this.mTotalJpegCallbackNum) {
            int width = dataParameter.getPictureSize().getWidth();
            int height = dataParameter.getPictureSize().getHeight();
            int orientation = ExifHelper.getOrientation(bArr);
            if ((dataParameter.getJpegRotation() + orientation) % 180 == 0) {
                i2 = width;
                i = height;
            } else {
                i = width;
                i2 = height;
            }
            new SaveOutputImageTask((SaverCallback) getParallelCallback(), System.currentTimeMillis(), dataParameter.getLocation(), i2, i, orientation, this.mGroupShotFolderName, this.mGroupShot).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
            if (pictureCallback != null) {
                pictureCallback.onPictureTakenFinished(true);
            } else {
                Log.w(TAG, "notifyResultData: null picture callback");
            }
        } else if (i3 == 1 && CameraSettings.isSaveGroushotPrimitiveOn()) {
            this.mCurrentParallelTaskData.setNeedThumbnail(false);
            this.mCurrentParallelTaskData.fillJpegData(bArr, 0);
            parallelCallback.onParallelProcessFinish(this.mCurrentParallelTaskData, null, null);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
        String str = TAG;
        Log.d(str, "onImageReceived: " + this.mReceivedJpegCallbackNum + "/" + this.mTotalJpegCallbackNum);
        long timestamp = image.getTimestamp();
        if (0 == this.mCurrentParallelTaskData.getTimestamp()) {
            Log.w(TAG, "onImageReceived: image arrived first");
            this.mCurrentParallelTaskData.setTimestamp(timestamp);
        }
        byte[] firstPlane = Util.getFirstPlane(image);
        image.close();
        notifyResultData(firstPlane);
        if (this.mReceivedJpegCallbackNum < this.mTotalJpegCallbackNum && !((MiCamera2Shot) this).mDeparted) {
            startSessionCapture();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
        prepareGroupShot();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        try {
            if (this.mCurrentParallelTaskData == null) {
                this.mCurrentParallelTaskData = generateParallelTaskData(0);
                if (this.mCurrentParallelTaskData == null) {
                    Log.w(TAG, "startSessionCapture: null task data");
                    return;
                }
            }
            CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
            CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
            PerformanceTracker.trackPictureCapture(0);
            ((MiCamera2Shot) this).mMiCamera.getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, ((MiCamera2Shot) this).mCameraHandler);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(TAG, "Cannot capture a still picture");
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to capture a still picture, IllegalState", e3);
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(256);
        }
    }
}
