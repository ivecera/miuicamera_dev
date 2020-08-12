package com.android.camera.snap;

import a.c.a;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.OutputConfiguration;
import android.location.Location;
import android.media.CamcorderProfile;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.OrientationEventListener;
import android.view.Surface;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.FileCompat;
import com.android.camera.LocationManager;
import com.android.camera.MiuiCameraSound;
import com.android.camera.PictureSizeManager;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.VideoModule;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.storage.MediaProviderUtil;
import com.android.camera.storage.Storage;
import com.android.camera2.CameraCapabilities;
import com.android.gallery3d.exif.ExifHelper;
import com.mi.config.b;
import com.xiaomi.camera.core.PictureInfo;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@TargetApi(26)
public class SnapCamera implements MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener {
    private static final int MSG_FOCUS_TIMEOUT = 1;
    private static final String SUFFIX = "_SNAP";
    /* access modifiers changed from: private */
    public static final String TAG = "SnapCamera";
    /* access modifiers changed from: private */
    public Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private CameraCapabilities mCameraCapabilities;
    /* access modifiers changed from: private */
    public CameraDevice mCameraDevice;
    /* access modifiers changed from: private */
    public Handler mCameraHandler;
    private int mCameraId;
    private MiuiCameraSound mCameraSound;
    private CameraDevice.StateCallback mCameraStateCallback = new CameraDevice.StateCallback() {
        /* class com.android.camera.snap.SnapCamera.AnonymousClass2 */

        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            Log.w(SnapCamera.TAG, "onDisconnected");
            SnapCamera.this.release();
        }

        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            String access$100 = SnapCamera.TAG;
            Log.e(access$100, "onError: " + i);
            SnapCamera.this.release();
        }

        public void onOpened(@NonNull CameraDevice cameraDevice) {
            synchronized (SnapCamera.this) {
                CameraDevice unused = SnapCamera.this.mCameraDevice = cameraDevice;
            }
            if (SnapCamera.this.mStatusListener != null) {
                SnapCamera.this.mStatusListener.onCameraOpened();
            }
        }
    };
    private final CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        /* class com.android.camera.snap.SnapCamera.AnonymousClass7 */

        private void process(CaptureResult captureResult) {
            if (SnapCamera.this.mCameraDevice != null && !SnapCamera.this.mFocused && captureResult.get(CaptureResult.CONTROL_AF_STATE) != null) {
                Integer num = (Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE);
                String access$100 = SnapCamera.TAG;
                Log.d(access$100, "process: afState=" + num + " aeState=" + captureResult.get(CaptureResult.CONTROL_AE_STATE) + " mFocused=" + SnapCamera.this.mFocused);
                if (2 == num.intValue()) {
                    boolean unused = SnapCamera.this.mFocused = true;
                    SnapCamera.this.mCameraHandler.removeMessages(1);
                }
            }
        }

        public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            process(totalCaptureResult);
        }

        public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
            super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
            Log.e(SnapCamera.TAG, "onCaptureFailed");
        }
    };
    /* access modifiers changed from: private */
    public CameraCaptureSession mCaptureSession;
    private ContentValues mContentValues = null;
    private Context mContext;
    /* access modifiers changed from: private */
    public volatile boolean mFocused = false;
    private HandlerThread mHandlerThread;
    private int mHeight;
    private boolean mIsCamcorder = false;
    private Handler mMainHandler;
    /* access modifiers changed from: private */
    public MediaRecorder mMediaRecorder;
    /* access modifiers changed from: private */
    public int mOrientation = 0;
    private OrientationEventListener mOrientationListener;
    private final ImageReader.OnImageAvailableListener mPhotoAvailableListener = new ImageReader.OnImageAvailableListener() {
        /* class com.android.camera.snap.SnapCamera.AnonymousClass6 */

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0020, code lost:
            if (r2 != null) goto L_0x0022;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
            r2.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0026, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0027, code lost:
            r1.addSuppressed(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x002a, code lost:
            throw r0;
         */
        public void onImageAvailable(ImageReader imageReader) {
            try {
                Image acquireNextImage = imageReader.acquireNextImage();
                if (acquireNextImage != null) {
                    byte[] firstPlane = Util.getFirstPlane(acquireNextImage);
                    if (firstPlane != null) {
                        SnapCamera.this.onPictureTaken(firstPlane);
                    }
                    if (acquireNextImage != null) {
                        acquireNextImage.close();
                    }
                } else if (acquireNextImage != null) {
                    acquireNextImage.close();
                }
            } catch (Exception e2) {
                Log.e(SnapCamera.TAG, e2.getMessage(), e2);
            }
        }
    };
    private ImageReader mPhotoImageReader;
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private Surface mPreviewSurface;
    private CamcorderProfile mProfile;
    /* access modifiers changed from: private */
    public boolean mRecording = false;
    private CameraCaptureSession.StateCallback mSessionCallback = new CameraCaptureSession.StateCallback() {
        /* class com.android.camera.snap.SnapCamera.AnonymousClass1 */

        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
            Log.e(SnapCamera.TAG, "sessionCb: onConfigureFailed");
        }

        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
            synchronized (SnapCamera.this) {
                if (SnapCamera.this.mCameraDevice == null) {
                    Log.e(SnapCamera.TAG, "onConfigured: CameraDevice was already closed.");
                    cameraCaptureSession.close();
                    return;
                }
                CameraCaptureSession unused = SnapCamera.this.mCaptureSession = cameraCaptureSession;
                SnapCamera.this.startPreview();
                SnapCamera.this.capture();
            }
        }
    };
    /* access modifiers changed from: private */
    public SnapStatusListener mStatusListener;
    private SurfaceTexture mSurfaceTexture;
    /* access modifiers changed from: private */
    public CaptureRequest.Builder mVideoRequestBuilder;
    private int mWidth;

    public interface SnapStatusListener {
        void onCameraOpened();

        void onDone(Uri uri);

        void onSkipCapture();
    }

    public SnapCamera(Context context, SnapStatusListener snapStatusListener) {
        try {
            LocationManager.instance().recordLocation(CameraSettings.isRecordLocation());
            this.mStatusListener = snapStatusListener;
            this.mContext = context;
            initSound();
            initHandler();
            initSnapType();
            initOrientationListener();
            initCamera();
        } catch (Exception e2) {
            String str = TAG;
            Log.e(str, "init failed " + e2.getMessage());
        }
    }

    private void applySettingsForPreview(CaptureRequest.Builder builder) {
        builder.set(CaptureRequest.CONTROL_MODE, 1);
        builder.set(CaptureRequest.CONTROL_AF_MODE, 4);
        builder.set(CaptureRequest.CONTROL_AE_MODE, 1);
        builder.set(CaptureRequest.FLASH_MODE, 0);
        builder.set(CaptureRequest.CONTROL_AWB_MODE, 1);
    }

    /* access modifiers changed from: private */
    public synchronized void capture() {
        if (this.mFocused) {
            try {
                CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(2);
                createCaptureRequest.addTarget(this.mPhotoImageReader.getSurface());
                int jpegRotation = Util.getJpegRotation(this.mCameraId, this.mOrientation);
                createCaptureRequest.set(CaptureRequest.JPEG_ORIENTATION, Integer.valueOf(jpegRotation));
                String str = TAG;
                Log.d(str, "capture, orientation=" + jpegRotation);
                Location currentLocation = LocationManager.instance().getCurrentLocation();
                if (currentLocation != null) {
                    createCaptureRequest.set(CaptureRequest.JPEG_GPS_LOCATION, currentLocation);
                }
                createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) this.mPreviewRequestBuilder.get(CaptureRequest.CONTROL_AF_MODE));
                this.mCaptureSession.capture(createCaptureRequest.build(), this.mCaptureCallback, this.mCameraHandler);
            } catch (CameraAccessException e2) {
                String str2 = TAG;
                Log.e(str2, "capture: " + e2.getMessage(), e2);
            }
        } else if (this.mStatusListener != null) {
            this.mStatusListener.onSkipCapture();
        }
    }

    private PictureInfo getPictureInfo() {
        PictureInfo pictureInfo = new PictureInfo();
        pictureInfo.setSensorType(this.mCameraId == Camera2DataContainer.getInstance().getFrontCameraId());
        return pictureInfo;
    }

    private void initCamera() {
        this.mCameraId = 0;
        if (Settings.System.getInt(this.mContext.getContentResolver(), "persist.camera.snap.auto_switch", 0) == 1) {
            this.mCameraId = CameraSettings.readPreferredCameraId();
        }
        CameraManager cameraManager = (CameraManager) this.mContext.getSystemService("camera");
        try {
            String valueOf = String.valueOf(this.mCameraId);
            cameraManager.openCamera(valueOf, this.mCameraStateCallback, this.mMainHandler);
            this.mCameraCapabilities = new CameraCapabilities(cameraManager.getCameraCharacteristics(valueOf), this.mCameraId);
            if (isCamcorder()) {
                int preferVideoQuality = CameraSettings.getPreferVideoQuality(this.mCameraId, 162);
                if (CamcorderProfile.hasProfile(this.mCameraId, preferVideoQuality)) {
                    this.mProfile = CamcorderProfile.get(this.mCameraId, preferVideoQuality);
                    return;
                }
                String str = TAG;
                Log.w(str, "invalid camcorder profile " + preferVideoQuality);
                this.mProfile = CamcorderProfile.get(this.mCameraId, 5);
                return;
            }
            PictureSizeManager.initialize(this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(256), 0, 163, this.mCameraId);
            CameraSize bestPictureSize = PictureSizeManager.getBestPictureSize();
            CameraSize optimalPreviewSize = Util.getOptimalPreviewSize(false, this.mCameraId, this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class), (double) CameraSettings.getPreviewAspectRatio(bestPictureSize.width, bestPictureSize.height));
            this.mSurfaceTexture = new SurfaceTexture(false);
            this.mSurfaceTexture.setDefaultBufferSize(optimalPreviewSize.width, optimalPreviewSize.height);
            this.mPreviewSurface = new Surface(this.mSurfaceTexture);
            preparePhotoImageReader(bestPictureSize);
            this.mWidth = bestPictureSize.width;
            this.mHeight = bestPictureSize.height;
        } catch (CameraAccessException | SecurityException e2) {
            String str2 = TAG;
            Log.e(str2, "initCamera: " + e2.getMessage(), e2);
        }
    }

    private void initHandler() {
        this.mHandlerThread = new HandlerThread("SnapCameraThread");
        this.mHandlerThread.start();
        this.mMainHandler = new Handler();
        this.mCameraHandler = new Handler(this.mHandlerThread.getLooper()) {
            /* class com.android.camera.snap.SnapCamera.AnonymousClass3 */

            public void handleMessage(Message message) {
                if (1 == message.what) {
                    boolean unused = SnapCamera.this.mFocused = true;
                }
            }
        };
    }

    private void initOrientationListener() {
        this.mOrientationListener = new OrientationEventListener(this.mContext, b.Wk() ? 2 : 3) {
            /* class com.android.camera.snap.SnapCamera.AnonymousClass4 */

            public void onOrientationChanged(int i) {
                SnapCamera snapCamera = SnapCamera.this;
                int unused = snapCamera.mOrientation = Util.roundOrientation(i, snapCamera.mOrientation);
            }
        };
        if (this.mOrientationListener.canDetectOrientation()) {
            Log.d(TAG, "Can detect orientation");
            this.mOrientationListener.enable();
            return;
        }
        Log.d(TAG, "Cannot detect orientation");
        this.mOrientationListener.disable();
    }

    private void initSnapType() {
        this.mIsCamcorder = false;
    }

    private void initSound() {
        this.mCameraSound = new MiuiCameraSound(CameraAppImpl.getAndroidContext(), true);
        this.mCameraSound.load(0);
    }

    public static boolean isSnapEnabled(Context context) {
        String string = DataRepository.dataItemGlobal().getString("pref_camera_snap_key", null);
        if (string != null) {
            Settings.Secure.putString(context.getContentResolver(), a.b.Lh, CameraSettings.getMiuiSettingsKeyForStreetSnap(string));
            DataRepository.dataItemGlobal().editor().remove("pref_camera_snap_key").apply();
        }
        String string2 = Settings.Secure.getString(context.getContentResolver(), a.b.Lh);
        return a.b.Oh.equals(string2) || a.b.Ph.equals(string2);
    }

    /* access modifiers changed from: private */
    public void onPictureTaken(byte[] bArr) {
        try {
            Uri addImageForSnapCamera = Storage.addImageForSnapCamera(this.mContext, Util.createJpegName(System.currentTimeMillis()) + SUFFIX, System.currentTimeMillis(), LocationManager.instance().getCurrentLocation(), ExifHelper.getOrientation(bArr), bArr, this.mWidth, this.mHeight, false, false, false, null, getPictureInfo());
            if (this.mStatusListener != null) {
                playSound();
                this.mStatusListener.onDone(addImageForSnapCamera);
            }
        } catch (Exception e2) {
            String str = TAG;
            Log.e(str, "save picture failed " + e2.getMessage());
        }
    }

    private void playSound() {
        MiuiCameraSound miuiCameraSound = this.mCameraSound;
        if (miuiCameraSound != null) {
            miuiCameraSound.playSound(0);
        }
    }

    private void preparePhotoImageReader(@NonNull CameraSize cameraSize) {
        ImageReader imageReader = this.mPhotoImageReader;
        if (imageReader != null) {
            imageReader.close();
        }
        this.mPhotoImageReader = ImageReader.newInstance(cameraSize.getWidth(), cameraSize.getHeight(), 256, 2);
        this.mPhotoImageReader.setOnImageAvailableListener(this.mPhotoAvailableListener, this.mCameraHandler);
    }

    private void setRecorderOrientationHint() {
        int sensorOrientation = this.mCameraCapabilities.getSensorOrientation();
        if (this.mOrientation != -1) {
            sensorOrientation = this.mCameraCapabilities.getFacing() == 0 ? ((sensorOrientation - this.mOrientation) + 360) % 360 : (sensorOrientation + this.mOrientation) % 360;
        }
        String str = TAG;
        Log.d(str, "setOrientationHint: " + sensorOrientation);
        this.mMediaRecorder.setOrientationHint(sensorOrientation);
    }

    private void setupMediaRecorder() {
        this.mMediaRecorder = new MediaRecorder();
        this.mMediaRecorder.setAudioSource(5);
        this.mMediaRecorder.setVideoSource(2);
        CamcorderProfile camcorderProfile = this.mProfile;
        camcorderProfile.duration = SnapTrigger.MAX_VIDEO_DURATION;
        this.mMediaRecorder.setProfile(camcorderProfile);
        this.mMediaRecorder.setMaxDuration(this.mProfile.duration);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (currentLocation != null) {
            this.mMediaRecorder.setLocation((float) currentLocation.getLatitude(), (float) currentLocation.getLongitude());
        }
        String format = new SimpleDateFormat(this.mContext.getString(R.string.video_file_name_format), Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis()));
        String str = format + SUFFIX + Util.convertOutputFormatToFileExt(this.mProfile.fileFormat);
        String convertOutputFormatToMimeType = Util.convertOutputFormatToMimeType(this.mProfile.fileFormat);
        String str2 = Storage.DIRECTORY + '/' + str;
        this.mContentValues = new ContentValues(7);
        this.mContentValues.put("title", format);
        this.mContentValues.put("_display_name", str);
        this.mContentValues.put("mime_type", convertOutputFormatToMimeType);
        this.mContentValues.put("_data", str2);
        this.mContentValues.put("resolution", Integer.toString(this.mProfile.videoFrameWidth) + "x" + Integer.toString(this.mProfile.videoFrameHeight));
        if (currentLocation != null) {
            this.mContentValues.put("latitude", Double.valueOf(currentLocation.getLatitude()));
            this.mContentValues.put("longitude", Double.valueOf(currentLocation.getLongitude()));
        }
        long availableSpace = Storage.getAvailableSpace() - Storage.LOW_STORAGE_THRESHOLD;
        if (VideoModule.VIDEO_MAX_SINGLE_FILE_SIZE < availableSpace && DataRepository.dataItemFeature().Nc()) {
            Log.d(TAG, "need reduce , now maxFileSize = " + availableSpace);
            availableSpace = 3670016000L;
        }
        long j = VideoModule.VIDEO_MIN_SINGLE_FILE_SIZE;
        if (availableSpace < j) {
            availableSpace = j;
        }
        try {
            this.mMediaRecorder.setMaxFileSize(availableSpace);
        } catch (RuntimeException unused) {
        }
        setRecorderOrientationHint();
        this.mMediaRecorder.setOnErrorListener(this);
        this.mMediaRecorder.setOnInfoListener(this);
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            Log.d(TAG, "save to " + str2);
            if (!Storage.isUseDocumentMode()) {
                this.mMediaRecorder.setOutputFile(str2);
            } else {
                parcelFileDescriptor = FileCompat.getParcelFileDescriptor(str2, true);
                this.mMediaRecorder.setOutputFile(parcelFileDescriptor.getFileDescriptor());
            }
            this.mMediaRecorder.prepare();
        } catch (IOException e2) {
            Log.e(TAG, "prepare failed for " + str2, e2);
        } catch (Throwable th) {
            Util.closeSilently(null);
            throw th;
        }
        Util.closeSilently(parcelFileDescriptor);
    }

    private void startBackgroundThread() {
        this.mBackgroundThread = new HandlerThread("CameraBackground");
        this.mBackgroundThread.start();
        Process.setThreadPriority(-16);
        this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper());
    }

    /* access modifiers changed from: private */
    public synchronized void startPreview() {
        if (this.mCameraDevice == null) {
            Log.e(TAG, "startPreview: CameraDevice was already closed");
        } else if (this.mCaptureSession == null) {
            Log.e(TAG, "startPreview: null capture session");
        } else {
            try {
                Log.d(TAG, "startPreview");
                this.mCaptureSession.setRepeatingRequest(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, this.mCameraHandler);
                this.mCameraHandler.sendEmptyMessageDelayed(1, 3000);
            } catch (CameraAccessException e2) {
                Log.e(TAG, e2.getMessage());
            }
        }
    }

    private void stopBackgroundThread() {
        this.mBackgroundThread.quitSafely();
        try {
            this.mBackgroundThread.join();
            this.mBackgroundThread = null;
            this.mBackgroundHandler = null;
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0104  */
    public synchronized void stopCamcorder() {
        ParcelFileDescriptor parcelFileDescriptor;
        long j;
        if (this.mMediaRecorder != null) {
            if (this.mRecording) {
                try {
                    this.mMediaRecorder.stop();
                } catch (Exception e2) {
                    this.mRecording = false;
                    Log.w(TAG, "mMediaRecorder stop failed", e2);
                }
            }
            this.mMediaRecorder.reset();
            this.mMediaRecorder.release();
            Uri uri = null;
            this.mMediaRecorder = null;
            stopBackgroundThread();
            if (this.mRecording) {
                String str = (String) this.mContentValues.get("_data");
                try {
                    long length = new File(str).length();
                    if (length > 0) {
                        if (!Storage.isUseDocumentMode()) {
                            j = Util.getDuration(str);
                            if (j == 0) {
                                new File(str).delete();
                            }
                            parcelFileDescriptor = null;
                        } else {
                            ParcelFileDescriptor parcelFileDescriptor2 = FileCompat.getParcelFileDescriptor(str, false);
                            try {
                                long duration = Util.getDuration(parcelFileDescriptor2.getFileDescriptor());
                                if (0 == duration) {
                                    FileCompat.deleteFile(str);
                                }
                                parcelFileDescriptor = parcelFileDescriptor2;
                                j = duration;
                            } catch (Exception e3) {
                                e = e3;
                                parcelFileDescriptor = parcelFileDescriptor2;
                                try {
                                    e.printStackTrace();
                                    Log.e(TAG, "Failed to write MediaStore " + e);
                                    Util.closeSilently(parcelFileDescriptor);
                                    if (this.mStatusListener != null) {
                                    }
                                    this.mRecording = false;
                                } catch (Throwable th) {
                                    th = th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                parcelFileDescriptor = parcelFileDescriptor2;
                                Util.closeSilently(parcelFileDescriptor);
                                throw th;
                            }
                        }
                        if (j > 0) {
                            try {
                                this.mContentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
                                this.mContentValues.put("_size", Long.valueOf(length));
                                this.mContentValues.put("duration", Long.valueOf(j));
                                Uri insert = this.mContext.getContentResolver().insert(Storage.getMediaUri(this.mContext, true, str), this.mContentValues);
                                if (insert == null) {
                                    Log.d(TAG, "insert MediaProvider failed, attempt to find uri by path, " + str);
                                    uri = MediaProviderUtil.getContentUriFromPath(this.mContext, str);
                                } else {
                                    uri = insert;
                                }
                            } catch (Exception e4) {
                                e = e4;
                                e.printStackTrace();
                                Log.e(TAG, "Failed to write MediaStore " + e);
                                Util.closeSilently(parcelFileDescriptor);
                                if (this.mStatusListener != null) {
                                }
                                this.mRecording = false;
                            }
                        }
                    } else {
                        parcelFileDescriptor = null;
                    }
                } catch (Exception e5) {
                    e = e5;
                    parcelFileDescriptor = null;
                    e.printStackTrace();
                    Log.e(TAG, "Failed to write MediaStore " + e);
                    Util.closeSilently(parcelFileDescriptor);
                    if (this.mStatusListener != null) {
                    }
                    this.mRecording = false;
                } catch (Throwable th3) {
                    th = th3;
                    parcelFileDescriptor = null;
                    Util.closeSilently(parcelFileDescriptor);
                    throw th;
                }
                Util.closeSilently(parcelFileDescriptor);
                if (this.mStatusListener != null) {
                    this.mStatusListener.onDone(uri);
                }
            }
            this.mRecording = false;
        }
    }

    public boolean isCamcorder() {
        return this.mIsCamcorder;
    }

    public void onError(MediaRecorder mediaRecorder, int i, int i2) {
        stopCamcorder();
    }

    public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
        if (i == 800 || i == 801) {
            Log.d(TAG, "duration or file size reach MAX");
            stopCamcorder();
        }
    }

    public synchronized void release() {
        Log.d(TAG, "release(): E");
        try {
            this.mOrientation = 0;
            LocationManager.instance().recordLocation(false);
            if (this.mOrientationListener != null) {
                this.mOrientationListener.disable();
                this.mOrientationListener = null;
            }
            if (this.mCameraSound != null) {
                this.mCameraSound.release();
                this.mCameraSound = null;
            }
        } catch (Exception e2) {
            Log.e(TAG, e2.getMessage(), e2);
        }
        try {
            stopCamcorder();
        } catch (Exception e3) {
            Log.e(TAG, e3.getMessage(), e3);
        }
        try {
            if (this.mSurfaceTexture != null) {
                this.mSurfaceTexture.release();
                this.mSurfaceTexture = null;
            }
        } catch (Exception e4) {
            Log.e(TAG, e4.getMessage(), e4);
        }
        if (this.mCameraHandler != null) {
            this.mCameraHandler.removeCallbacksAndMessages(null);
        }
        if (this.mHandlerThread != null) {
            this.mHandlerThread.quitSafely();
        }
        if (this.mCaptureSession != null) {
            this.mCaptureSession.close();
            this.mCaptureSession = null;
        }
        if (this.mCameraDevice != null) {
            this.mCameraDevice.close();
            this.mCameraDevice = null;
        }
        Log.d(TAG, "release(): X");
    }

    public synchronized void startCamcorder() {
        if (this.mCameraDevice == null) {
            Log.e(TAG, "startCamcorder: CameraDevice is opening or was already closed");
            return;
        }
        startBackgroundThread();
        setupMediaRecorder();
        List<Surface> asList = Arrays.asList(this.mMediaRecorder.getSurface());
        try {
            this.mVideoRequestBuilder = this.mCameraDevice.createCaptureRequest(3);
            this.mVideoRequestBuilder.addTarget(this.mMediaRecorder.getSurface());
            this.mCameraDevice.createCaptureSession(asList, new CameraCaptureSession.StateCallback() {
                /* class com.android.camera.snap.SnapCamera.AnonymousClass5 */

                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Log.e(SnapCamera.TAG, "videoSessionCb::onConfigureFailed");
                    SnapCamera.this.stopCamcorder();
                }

                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    try {
                        cameraCaptureSession.setRepeatingRequest(SnapCamera.this.mVideoRequestBuilder.build(), new CameraCaptureSession.CaptureCallback() {
                            /* class com.android.camera.snap.SnapCamera.AnonymousClass5.AnonymousClass1 */

                            public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j, long j2) {
                                if (!SnapCamera.this.mRecording) {
                                    try {
                                        SnapCamera.this.mMediaRecorder.start();
                                    } catch (Exception e2) {
                                        String access$100 = SnapCamera.TAG;
                                        Log.e(access$100, "failed to start media recorder: " + e2.getMessage(), e2);
                                        e2.printStackTrace();
                                        SnapCamera.this.stopCamcorder();
                                    }
                                    boolean unused = SnapCamera.this.mRecording = true;
                                }
                            }
                        }, SnapCamera.this.mBackgroundHandler);
                    } catch (CameraAccessException e2) {
                        String access$100 = SnapCamera.TAG;
                        Log.e(access$100, "videoSessionCb::onConfigured: " + e2.getMessage(), e2);
                    }
                }
            }, this.mCameraHandler);
        } catch (CameraAccessException e2) {
            String str = TAG;
            Log.e(str, "failed to startCamcorder: " + e2.getMessage(), e2);
        }
    }

    public synchronized void takeSnap() {
        if (this.mCameraDevice == null) {
            Log.e(TAG, "takeSnap: CameraDevice is opening or was already closed.");
        } else if (this.mCaptureSession == null) {
            try {
                Log.d(TAG, "createCaptureSession");
                this.mPreviewRequestBuilder = this.mCameraDevice.createCaptureRequest(1);
                this.mPreviewRequestBuilder.addTarget(this.mPreviewSurface);
                applySettingsForPreview(this.mPreviewRequestBuilder);
                if (DataRepository.dataItemFeature().dc()) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new OutputConfiguration(this.mPreviewSurface));
                    arrayList.add(new OutputConfiguration(this.mPhotoImageReader.getSurface()));
                    CompatibilityUtils.createCaptureSessionWithSessionConfiguration(this.mCameraDevice, 0, null, arrayList, this.mPreviewRequestBuilder.build(), this.mSessionCallback, this.mCameraHandler);
                } else {
                    this.mCameraDevice.createCaptureSession(Arrays.asList(this.mPreviewSurface, this.mPhotoImageReader.getSurface()), this.mSessionCallback, this.mCameraHandler);
                }
            } catch (CameraAccessException e2) {
                String str = TAG;
                Log.e(str, "takeSnap: " + e2.getMessage(), e2);
            }
        } else {
            capture();
        }
    }
}
