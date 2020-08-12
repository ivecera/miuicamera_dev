package com.android.camera.module.loader.camera2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.view.Surface;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.constant.ExceptionConstant;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.observables.ConnectableObservable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressLint({"MissingPermission"})
@TargetApi(21)
public class ParallelSnapshotManager {
    private static final int CAMERA_ID_VIRTUAL_PARALLEL = 102;
    private static final long CAMERA_OPEN_OR_CLOSE_TIMEOUT = 5000;
    private static final int MANAGER_MSG_CLOSE_CAMERA = 2;
    private static final int MANAGER_MSG_CLOSE_FINISH = 3;
    private static final int MANAGER_MSG_CLOSE_SESSION = 6;
    private static final int MANAGER_MSG_CREATE_SESSION = 5;
    private static final int MANAGER_MSG_OPEN_FINISH = 4;
    private static final int MANAGER_MSG_REQUEST_CAMERA = 1;
    private static final int MANAGER_STATE_IDLE = 1;
    private static final int MANAGER_STATE_PENDING_CREATE_SESSION = 5;
    private static final int MANAGER_STATE_WAITING_CLOSE = 2;
    private static final int MANAGER_STATE_WAITING_CREATE_SESSION = 4;
    private static final int MANAGER_STATE_WAITING_OPEN = 3;
    public static final int REASON_DISCONNECTED = 1;
    /* access modifiers changed from: private */
    public static final String TAG = "ParallelSnapshotManager";
    private static ParallelSnapshotManager sInstance;
    /* access modifiers changed from: private */
    public CameraCaptureSession mCameraCaptureSession;
    /* access modifiers changed from: private */
    public CameraDevice mCameraDevice;
    /* access modifiers changed from: private */
    public Handler mCameraHandler;
    private CameraManager mCameraManager;
    private final CameraDevice.StateCallback mCameraOpenCallback = new CameraDevice.StateCallback() {
        /* class com.android.camera.module.loader.camera2.ParallelSnapshotManager.AnonymousClass2 */

        public void onClosed(@NonNull CameraDevice cameraDevice) {
            String access$100 = ParallelSnapshotManager.TAG;
            Log.d(access$100, "CameraOpenCallback: closed " + cameraDevice.getId());
            ParallelSnapshotManager.this.mCameraHandler.sendEmptyMessage(3);
        }

        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            String access$100 = ParallelSnapshotManager.TAG;
            Log.d(access$100, "CameraOpenCallback: onDisconnected " + cameraDevice.getId());
            if (ParallelSnapshotManager.this.mCameraDevice != null) {
                Message obtainMessage = ParallelSnapshotManager.this.mCameraHandler.obtainMessage();
                obtainMessage.what = 2;
                obtainMessage.arg1 = 1;
                ParallelSnapshotManager.this.mCameraHandler.sendMessageAtFrontOfQueue(obtainMessage);
            }
        }

        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            String str = "onError: cameraId=" + cameraDevice.getId() + " error=" + i;
            Log.e(ParallelSnapshotManager.TAG, "CameraOpenCallback: " + str);
            ParallelSnapshotManager.this.removeAllAppMessages();
            synchronized (ParallelSnapshotManager.this.mCaptureLock) {
                boolean unused = ParallelSnapshotManager.this.mIsCameraOpened = false;
                List unused2 = ParallelSnapshotManager.this.mCurrentSurfaceList = null;
                List unused3 = ParallelSnapshotManager.this.mPendingSurfaceList = null;
                CameraDevice unused4 = ParallelSnapshotManager.this.mCameraDevice = null;
                CameraCaptureSession unused5 = ParallelSnapshotManager.this.mCameraCaptureSession = null;
                ParallelSnapshotManager.this.setManagerState(1);
            }
            ParallelSnapshotManager.this.onCameraOpenFailed(ExceptionConstant.transFromCamera2Error(i), str);
        }

        public void onOpened(@NonNull CameraDevice cameraDevice) {
            int parseInt = Integer.parseInt(cameraDevice.getId());
            CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(parseInt);
            String str = "CameraOpenCallback: camera " + parseInt + " was opened successfully";
            if (capabilities != null) {
                Message obtain = Message.obtain();
                obtain.what = 4;
                obtain.obj = cameraDevice;
                ParallelSnapshotManager.this.mCameraHandler.sendMessage(obtain);
                return;
            }
            String str2 = str + ", but corresponding CameraCapabilities is null";
            Log.e(ParallelSnapshotManager.TAG, str2);
            ParallelSnapshotManager.this.onCameraOpenFailed(231, str2);
        }
    };
    private ObservableEmitter<Camera2Result> mCameraResultEmitter;
    private ConnectableObservable<Camera2Result> mCameraResultObservable;
    /* access modifiers changed from: private */
    public final Object mCaptureLock = new Object();
    private int mCurrentPreviewCameraID;
    private int mCurrentState = 1;
    /* access modifiers changed from: private */
    public List<Surface> mCurrentSurfaceList;
    private final Object mEmitterLock = new Object();
    /* access modifiers changed from: private */
    public boolean mIsCameraOpened;
    private final int mMaxQueueSize;
    /* access modifiers changed from: private */
    public List<Surface> mPendingSurfaceList;

    private class CaptureSessionStateCallback extends CameraCaptureSession.StateCallback {
        private CaptureSessionStateCallback() {
        }

        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
            Log.d(ParallelSnapshotManager.TAG, "onConfigureFailed");
            synchronized (ParallelSnapshotManager.this.mCaptureLock) {
                CameraCaptureSession unused = ParallelSnapshotManager.this.mCameraCaptureSession = null;
            }
            ParallelSnapshotManager.this.setManagerState(1);
        }

        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
            Log.d(ParallelSnapshotManager.TAG, "onConfigured>>");
            synchronized (ParallelSnapshotManager.this.mCaptureLock) {
                CameraCaptureSession unused = ParallelSnapshotManager.this.mCameraCaptureSession = cameraCaptureSession;
            }
            ParallelSnapshotManager.this.setManagerState(1);
            Log.d(ParallelSnapshotManager.TAG, "onConfigured<<");
        }
    }

    @interface ManagerState {
    }

    private ParallelSnapshotManager() {
        HandlerThread handlerThread = new HandlerThread("VT Camera Handler Thread");
        handlerThread.start();
        this.mCameraHandler = new Handler(handlerThread.getLooper()) {
            /* class com.android.camera.module.loader.camera2.ParallelSnapshotManager.AnonymousClass1 */

            public void handleMessage(Message message) {
                ParallelSnapshotManager.this.onMessage(message);
            }
        };
        this.mCameraManager = (CameraManager) CameraAppImpl.getAndroidContext().getSystemService("camera");
        if (Process.getTotalMemory() / 1073741824 < 6) {
            this.mMaxQueueSize = 3;
        } else {
            this.mMaxQueueSize = 5;
        }
    }

    private void abandonOpenObservableIfExists() {
        Log.d(TAG, "abandonOpenObservableIfExists: E");
        synchronized (this.mEmitterLock) {
            String str = TAG;
            Log.d(str, "abandonOpenObservableIfExists: start mCameraResultEmitter = " + this.mCameraResultEmitter);
            if (this.mCameraResultEmitter != null && !this.mCameraResultEmitter.isDisposed()) {
                this.mCameraResultEmitter.onNext(Camera2Result.create(3).setCameraError(225));
                this.mCameraResultEmitter.onComplete();
                this.mCameraResultEmitter = null;
            }
        }
        Log.d(TAG, "abandonOpenObservableIfExists: X");
    }

    private boolean attachInObservable(Observer<Camera2Result> observer) {
        boolean z;
        synchronized (this.mEmitterLock) {
            if (this.mCameraResultEmitter != null) {
                if (!this.mCameraResultEmitter.isDisposed()) {
                    this.mCameraResultObservable.subscribe(observer);
                    z = true;
                }
            }
            this.mCameraResultObservable = Observable.create(new e(this)).timeout(getCameraOpTimeout(), TimeUnit.MILLISECONDS).onErrorResumeNext(new d(this)).observeOn(GlobalConstant.sCameraSetupScheduler).publish();
            this.mCameraResultObservable.subscribe(observer);
            this.mCameraResultObservable.connect();
            z = false;
        }
        return z;
    }

    private long getCameraOpTimeout() {
        return this.mCameraDevice != null ? CAMERA_OPEN_OR_CLOSE_TIMEOUT + (CameraSettings.getExposureTime() / 1000000) : CAMERA_OPEN_OR_CLOSE_TIMEOUT;
    }

    public static synchronized ParallelSnapshotManager getInstance() {
        ParallelSnapshotManager parallelSnapshotManager;
        synchronized (ParallelSnapshotManager.class) {
            if (sInstance == null) {
                sInstance = new ParallelSnapshotManager();
            }
            parallelSnapshotManager = sInstance;
        }
        return parallelSnapshotManager;
    }

    @ManagerState
    private int getManagerState() {
        return this.mCurrentState;
    }

    /* access modifiers changed from: private */
    public void onCameraOpenFailed(int i, String str) {
        String str2 = TAG;
        Log.e(str2, "onCameraOpenFailed: " + i + " msg:" + str);
        setManagerState(1);
        synchronized (this.mEmitterLock) {
            if (this.mCameraResultEmitter != null) {
                this.mCameraResultEmitter.onNext(Camera2Result.create(3).setCameraError(i));
                this.mCameraResultEmitter.onComplete();
            }
        }
    }

    private void onCameraOpenSuccess() {
        setManagerState(1);
        Log.d(TAG, "onCameraOpenSuccess: E");
    }

    /* access modifiers changed from: private */
    public void onMessage(Message message) {
        switch (message.what) {
            case 1:
                if (getManagerState() == 1) {
                    try {
                        Log.d(TAG, "open start");
                        setManagerState(3);
                        this.mCameraManager.openCamera(String.valueOf(102), this.mCameraOpenCallback, this.mCameraHandler);
                        return;
                    } catch (CameraAccessException | IllegalArgumentException | SecurityException e2) {
                        e2.printStackTrace();
                        onCameraOpenFailed(230, e2.getClass().getSimpleName());
                        Log.e(TAG, "openCamera: failed to open camera 102", e2);
                        return;
                    }
                } else {
                    return;
                }
            case 2:
                if (getManagerState() != 1) {
                    String str = TAG;
                    Log.w(str, "not idle, break on msg.what " + message.what + ", mCurrentState " + this.mCurrentState);
                    return;
                }
                setManagerState(2);
                int i = message.arg1;
                String str2 = TAG;
                Log.d(str2, "force close start reason " + i);
                this.mCameraDevice.close();
                this.mCameraDevice = null;
                this.mCameraCaptureSession = null;
                this.mCurrentSurfaceList = null;
                return;
            case 3:
                Log.d(TAG, "close finish");
                setManagerState(1);
                this.mCameraDevice = null;
                this.mCameraCaptureSession = null;
                this.mCurrentSurfaceList = null;
                return;
            case 4:
                Log.d(TAG, "open finish");
                this.mCameraDevice = (CameraDevice) message.obj;
                if (getManagerState() == 5) {
                    Log.w(TAG, "try to recreate session");
                    setManagerState(1);
                    createCaptureSession(this.mPendingSurfaceList);
                    this.mPendingSurfaceList = null;
                }
                this.mCameraDevice = (CameraDevice) message.obj;
                setManagerState(1);
                return;
            case 5:
                Log.d(TAG, "create CaptureSession >>>>>>");
                if (getManagerState() != 1) {
                    String str3 = TAG;
                    Log.w(str3, "manager state" + getManagerState());
                    if (getManagerState() == 3) {
                        this.mPendingSurfaceList = (List) message.obj;
                        setManagerState(5);
                        Log.w(TAG, "waiting camera open finsi to recreate session");
                        return;
                    }
                    return;
                }
                synchronized (this.mCaptureLock) {
                    if (this.mCurrentSurfaceList != null && this.mCurrentSurfaceList.equals((List) message.obj)) {
                        Log.d(TAG, "the same surface, skip");
                        return;
                    } else if (this.mCameraCaptureSession != null) {
                        Log.d(TAG, "cameraCaptureSession is not null");
                        this.mPendingSurfaceList = (List) message.obj;
                        return;
                    } else {
                        setManagerState(4);
                        try {
                            this.mCameraDevice.createCaptureRequest(2);
                            this.mCameraDevice.createCaptureSession((List) message.obj, new CaptureSessionStateCallback(), this.mCameraHandler);
                        } catch (CameraAccessException e3) {
                            e3.printStackTrace();
                        }
                        this.mCurrentSurfaceList = (List) message.obj;
                        Log.d(TAG, "createSession<<");
                        return;
                    }
                }
            case 6:
                if (getManagerState() != 1) {
                    String str4 = TAG;
                    Log.w(str4, "manager state" + getManagerState());
                    return;
                }
                synchronized (this.mCaptureLock) {
                    if (this.mCameraCaptureSession != null) {
                        this.mCameraCaptureSession.close();
                        this.mCameraCaptureSession = null;
                        this.mCurrentSurfaceList = null;
                    }
                    if (this.mPendingSurfaceList != null) {
                        createCaptureSession(this.mPendingSurfaceList);
                        this.mPendingSurfaceList = null;
                    }
                }
                Log.d(TAG, "CaptureSession close");
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void removeAllAppMessages() {
        this.mCameraHandler.removeMessages(1);
        this.mCameraHandler.removeMessages(3);
        this.mCameraHandler.removeMessages(4);
        this.mCameraHandler.removeMessages(2);
    }

    /* access modifiers changed from: private */
    public void setManagerState(@ManagerState int i) {
        this.mCurrentState = i;
    }

    public void closeCamera() {
        synchronized (this.mCaptureLock) {
            if (this.mIsCameraOpened) {
                this.mCameraHandler.sendEmptyMessage(2);
                this.mIsCameraOpened = false;
            }
        }
    }

    public void closeCaptureSession(List<Surface> list) {
        synchronized (this.mCaptureLock) {
            if (list != null) {
                if (this.mCurrentSurfaceList != null && list.containsAll(this.mCurrentSurfaceList)) {
                    this.mCameraHandler.sendEmptyMessage(6);
                }
            }
        }
    }

    public void createCaptureSession(List<Surface> list) {
        Message obtain = Message.obtain();
        obtain.obj = list;
        obtain.what = 5;
        this.mCameraHandler.sendMessage(obtain);
    }

    public /* synthetic */ void e(ObservableEmitter observableEmitter) throws Exception {
        this.mCameraResultEmitter = observableEmitter;
    }

    public CameraDevice getCameraDevice() {
        CameraDevice cameraDevice;
        synchronized (this.mCaptureLock) {
            cameraDevice = this.mCameraDevice;
        }
        return cameraDevice;
    }

    public CameraCaptureSession getCaptureSession() {
        CameraCaptureSession cameraCaptureSession;
        synchronized (this.mCaptureLock) {
            cameraCaptureSession = this.mCameraCaptureSession;
        }
        return cameraCaptureSession;
    }

    public Surface getCaptureSurface(int i) {
        if (i == 1) {
            return this.mCurrentSurfaceList.get(0);
        }
        if (i != 2) {
            return null;
        }
        return this.mCurrentSurfaceList.get(1);
    }

    public int getMaxQueueSize() {
        return this.mMaxQueueSize;
    }

    public /* synthetic */ ObservableSource i(Throwable th) throws Exception {
        String str = TAG;
        Log.d(str, "Exception occurs in camera open or close: " + th);
        if (!this.mCameraHandler.getLooper().getQueue().isPolling()) {
            Log.d(TAG, "CameraHandlerThread is being stuck...");
        }
        return Observable.just(Camera2Result.create(3).setCameraError(236));
    }

    public boolean isParallelCameraDeviceConfiged(CameraCapabilities cameraCapabilities) {
        if (cameraCapabilities != null) {
            return cameraCapabilities.isSupportPallelCameraDevice();
        }
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(this.mCurrentPreviewCameraID);
        if (capabilities != null) {
            return capabilities.isSupportPallelCameraDevice();
        }
        return false;
    }

    public boolean isParallelSessionReady() {
        return this.mPendingSurfaceList == null && this.mCameraCaptureSession != null;
    }

    public void openCamera(int i, Observer<Camera2Result> observer) {
        this.mCurrentPreviewCameraID = i;
        if (isParallelCameraDeviceConfiged(null)) {
            synchronized (this.mCaptureLock) {
                if (!this.mIsCameraOpened) {
                    attachInObservable(observer);
                    this.mCameraHandler.sendEmptyMessage(1);
                    this.mIsCameraOpened = true;
                }
            }
        }
    }

    public void release() {
        abandonOpenObservableIfExists();
        this.mCameraHandler.removeMessages(1);
        Handler handler = this.mCameraHandler;
        handler.sendMessage(handler.obtainMessage(2));
    }
}
