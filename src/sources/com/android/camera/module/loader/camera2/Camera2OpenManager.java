package com.android.camera.module.loader.camera2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.support.annotation.NonNull;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.constant.ExceptionConstant;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.MiCamera2;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@SuppressLint({"MissingPermission"})
@TargetApi(21)
public class Camera2OpenManager {
    private static final long CAMERA_OPEN_OR_CLOSE_TIMEOUT = 5000;
    private static final int MANAGER_MSG_CLOSE_CAMERA_AFTER_CAPTURE = 5;
    private static final int MANAGER_MSG_CLOSE_FINISH = 3;
    private static final int MANAGER_MSG_FORCE_CLOSE_CAMERA = 2;
    private static final int MANAGER_MSG_OPEN_FINISH = 4;
    private static final int MANAGER_MSG_REQUEST_CAMERA = 1;
    private static final int MANAGER_STATE_IDLE = 1;
    private static final int MANAGER_STATE_WAITING_CLOSE = 2;
    private static final int MANAGER_STATE_WAITING_OPEN = 3;
    /* access modifiers changed from: private */
    public static final long POP_CAMERA_DELAY_CREATE_SESSION = SystemProperties.getLong("delay_create_session", 450);
    public static final int REASON_CLOSE = 0;
    public static final int REASON_DISCONNECTED = 1;
    /* access modifiers changed from: private */
    public static final String TAG = "Camera2OpenManager";
    private static Camera2OpenManager sInstance;
    /* access modifiers changed from: private */
    public Camera2Proxy mCamera2Device;
    /* access modifiers changed from: private */
    public Handler mCameraHandler;
    private Handler mCameraMainThreadHandler;
    private CameraManager mCameraManager;
    private final CameraDevice.StateCallback mCameraOpenCallback = new CameraDevice.StateCallback() {
        /* class com.android.camera.module.loader.camera2.Camera2OpenManager.AnonymousClass2 */

        public void onClosed(@NonNull CameraDevice cameraDevice) {
            String access$300 = Camera2OpenManager.TAG;
            Log.d(access$300, "CameraOpenCallback: closed " + cameraDevice.getId());
            if (DataRepository.dataItemFeature().c_0x1b() && Integer.valueOf(cameraDevice.getId()).intValue() == 1 && Camera2OpenManager.this.mPendingCameraId.get() == -1) {
                CompatibilityUtils.takebackMotor();
            }
            Camera2OpenManager.this.mCameraHandler.sendEmptyMessage(3);
        }

        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            String access$300 = Camera2OpenManager.TAG;
            Log.d(access$300, "CameraOpenCallback: onDisconnected " + cameraDevice.getId());
            Camera2OpenManager.this.mCameraHandler.removeMessages(2);
            Camera2OpenManager.this.mCameraHandler.removeMessages(5);
            if (Camera2OpenManager.this.mCamera2Device != null) {
                Camera2OpenManager.this.mCamera2Device.onCameraDisconnected();
                Camera2OpenManager.this.mPendingCameraId.set(-1);
                Message obtainMessage = Camera2OpenManager.this.mCameraHandler.obtainMessage();
                obtainMessage.what = 2;
                obtainMessage.arg1 = 1;
                Camera2OpenManager.this.mCameraHandler.sendMessageAtFrontOfQueue(obtainMessage);
            }
        }

        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            String str = "onError: cameraId=" + cameraDevice.getId() + " error=" + i;
            Log.e(Camera2OpenManager.TAG, "CameraOpenCallback: " + str);
            Camera2OpenManager.this.onCameraOpenFailed(ExceptionConstant.transFromCamera2Error(i), str);
        }

        public void onOpened(@NonNull CameraDevice cameraDevice) {
            int parseInt = Integer.parseInt(cameraDevice.getId());
            CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(parseInt);
            String str = "CameraOpenCallback: camera " + parseInt + " was opened successfully";
            if (capabilities != null) {
                Camera2OpenManager camera2OpenManager = Camera2OpenManager.this;
                Camera2Proxy unused = camera2OpenManager.mCamera2Device = new MiCamera2(cameraDevice, parseInt, capabilities, camera2OpenManager.getCameraHandler(), Camera2OpenManager.this.getCameraPreviewHandler(), Camera2OpenManager.this.getCameraMainThreadHandler());
                Camera2DataContainer.getInstance().setCurrentOpenedCameraId(parseInt);
                if (DataRepository.dataItemFeature().c_0x1b()) {
                    long currentTimeMillis = System.currentTimeMillis() - Camera2OpenManager.this.mPopCameraTimeStamp.get();
                    Log.d(Camera2OpenManager.TAG, str + ", current delay = " + currentTimeMillis + ", should delay = " + Camera2OpenManager.POP_CAMERA_DELAY_CREATE_SESSION);
                    if (currentTimeMillis <= Camera2OpenManager.POP_CAMERA_DELAY_CREATE_SESSION) {
                        Log.d(Camera2OpenManager.TAG, "onOpened: sleep start...");
                        try {
                            Thread.sleep(Camera2OpenManager.POP_CAMERA_DELAY_CREATE_SESSION - currentTimeMillis);
                        } catch (InterruptedException unused2) {
                        }
                        Log.d(Camera2OpenManager.TAG, "onOpened: sleep end...");
                    }
                    Camera2OpenManager.this.mPopCameraTimeStamp.set(-1);
                }
                Camera2OpenManager.this.mCameraHandler.sendEmptyMessage(4);
                return;
            }
            String str2 = str + ", but corresponding CameraCapabilities is null";
            Log.e(Camera2OpenManager.TAG, str2);
            Camera2OpenManager.this.onCameraOpenFailed(231, str2);
        }
    };
    private Handler mCameraPreviewHandler;
    private ObservableEmitter<Camera2Result> mCameraResultEmitter;
    private ConnectableObservable<Camera2Result> mCameraResultObservable;
    private AtomicInteger mCurrentModule = new AtomicInteger(160);
    private int mCurrentState = 1;
    private final Object mEmitterLock = new Object();
    /* access modifiers changed from: private */
    public AtomicInteger mPendingCameraId = new AtomicInteger(-1);
    /* access modifiers changed from: private */
    public AtomicLong mPopCameraTimeStamp = new AtomicLong(-1);

    @interface ManagerState {
    }

    private Camera2OpenManager() {
        initData();
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
            this.mCameraResultObservable = Observable.create(new a(this)).timeout(getCameraOpTimeout(), TimeUnit.MILLISECONDS).onErrorResumeNext(new b(this)).observeOn(GlobalConstant.sCameraSetupScheduler).publish();
            this.mCameraResultObservable.subscribe(observer);
            this.mCameraResultObservable.connect();
            z = false;
        }
        return z;
    }

    private long getCameraOpTimeout() {
        return (this.mCamera2Device == null || !ModuleManager.isProModule()) ? CAMERA_OPEN_OR_CLOSE_TIMEOUT : CAMERA_OPEN_OR_CLOSE_TIMEOUT + (CameraSettings.getExposureTime() / 1000000);
    }

    public static synchronized Camera2OpenManager getInstance() {
        Camera2OpenManager camera2OpenManager;
        synchronized (Camera2OpenManager.class) {
            if (sInstance == null) {
                sInstance = new Camera2OpenManager();
            }
            camera2OpenManager = sInstance;
        }
        return camera2OpenManager;
    }

    @ManagerState
    private int getManagerState() {
        return this.mCurrentState;
    }

    private void initData() {
        HandlerThread handlerThread = new HandlerThread("Camera Handler Thread");
        handlerThread.start();
        this.mCameraHandler = new Handler(handlerThread.getLooper()) {
            /* class com.android.camera.module.loader.camera2.Camera2OpenManager.AnonymousClass1 */

            public void handleMessage(Message message) {
                Camera2OpenManager.this.onMessage(message);
            }
        };
        HandlerThread handlerThread2 = new HandlerThread("CameraPreviewHandlerThread");
        handlerThread2.start();
        this.mCameraPreviewHandler = new Handler(handlerThread2.getLooper());
        this.mCameraMainThreadHandler = new Handler(Looper.getMainLooper());
        this.mCameraManager = (CameraManager) CameraAppImpl.getAndroidContext().getSystemService("camera");
        Camera2DataContainer.getInstance(this.mCameraManager);
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
        synchronized (this.mEmitterLock) {
            String str = TAG;
            Log.d(str, "onCameraOpenSuccess: mCameraResultEmitter = " + this.mCameraResultEmitter);
            if (this.mCameraResultEmitter != null) {
                this.mCameraResultEmitter.onNext(Camera2Result.create(2));
                this.mCameraResultEmitter.onComplete();
            }
        }
        Log.d(TAG, "onCameraOpenSuccess: X");
    }

    /* access modifiers changed from: private */
    public void onMessage(Message message) {
        int i = message.what;
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    Log.e(TAG, "close finish");
                    if (DataRepository.dataItemFeature().c_0x1b() && this.mPopCameraTimeStamp.get() > 0 && this.mPendingCameraId.get() == -1) {
                        CompatibilityUtils.takebackMotor();
                    }
                    setManagerState(1);
                    this.mCameraHandler.sendEmptyMessage(1);
                    return;
                } else if (i == 4) {
                    Log.e(TAG, "open finish");
                    setManagerState(1);
                    this.mCameraHandler.sendEmptyMessage(1);
                    return;
                } else if (i != 5) {
                    return;
                }
            }
            if (this.mCamera2Device == null) {
                this.mCameraHandler.sendEmptyMessage(1);
            } else if (getManagerState() != 1) {
                Log.w(TAG, "not idle, break on msg.what " + message.what + ", mCurrentState " + this.mCurrentState);
            } else {
                setManagerState(2);
                int i2 = message.arg1;
                Log.e(TAG, "force close start reason " + i2);
                this.mCamera2Device.releasePreview(i2);
                this.mCamera2Device.resetConfigs();
                this.mCamera2Device.close();
                this.mCamera2Device = null;
            }
        } else {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.setCaptureBusyCallback(null);
            }
            if (this.mPendingCameraId.get() >= 0) {
                Camera2Proxy camera2Proxy2 = this.mCamera2Device;
                if (camera2Proxy2 != null && camera2Proxy2.getId() == this.mPendingCameraId.get()) {
                    this.mCameraHandler.removeMessages(5);
                    String str = "Camera " + this.mCamera2Device.getId() + " was opened successfully";
                    if (this.mCamera2Device.getCapabilities() == null) {
                        String str2 = str + ", but corresponding CameraCapabilities is null";
                        Log.d(TAG, str2);
                        onCameraOpenFailed(231, str2);
                        return;
                    }
                    Log.d(TAG, str);
                    if (!DataRepository.dataItemFeature().c_0x20_OR_T()) {
                        this.mCamera2Device.cancelSession();
                    }
                    onCameraOpenSuccess();
                } else if (this.mCamera2Device != null) {
                    setManagerState(2);
                    Log.d(TAG, "close start");
                    this.mCamera2Device.close();
                    this.mCamera2Device = null;
                } else if (getManagerState() == 1) {
                    try {
                        setManagerState(3);
                        Log.d(TAG, "open start");
                        this.mCameraManager.openCamera(String.valueOf(this.mPendingCameraId), this.mCameraOpenCallback, this.mCameraHandler);
                    } catch (CameraAccessException | IllegalArgumentException | SecurityException e2) {
                        e2.printStackTrace();
                        onCameraOpenFailed(230, e2.getClass().getSimpleName());
                        Log.e(TAG, "openCamera: failed to open camera " + this.mPendingCameraId.get(), e2);
                    }
                }
            } else if (this.mCamera2Device != null) {
                setManagerState(2);
                Log.e(TAG, "close start");
                this.mCamera2Device.close();
                this.mCamera2Device = null;
            }
        }
    }

    public static void preload() {
        Log.i(TAG, "preload");
    }

    private void removeAllAppMessages() {
        this.mCameraHandler.removeMessages(1);
        this.mCameraHandler.removeMessages(2);
        this.mCameraHandler.removeMessages(3);
        this.mCameraHandler.removeMessages(4);
        this.mCameraHandler.removeMessages(5);
    }

    private void setManagerState(@ManagerState int i) {
        this.mCurrentState = i;
    }

    public /* synthetic */ void c(boolean z, boolean z2) {
        String str = TAG;
        Log.d(str, "release onCaptureCompleted: success:" + z2 + " immediate:" + z);
        if (z) {
            Handler handler = this.mCameraHandler;
            handler.sendMessage(handler.obtainMessage(5));
        }
    }

    public /* synthetic */ void d(ObservableEmitter observableEmitter) throws Exception {
        this.mCameraResultEmitter = observableEmitter;
    }

    public Handler getCameraHandler() {
        return this.mCameraHandler;
    }

    public Handler getCameraMainThreadHandler() {
        return this.mCameraMainThreadHandler;
    }

    public Handler getCameraPreviewHandler() {
        return this.mCameraPreviewHandler;
    }

    public Camera2Proxy getCurrentCamera2Device() {
        return this.mCamera2Device;
    }

    public int getPendingCameraId() {
        return this.mPendingCameraId.get();
    }

    public /* synthetic */ ObservableSource h(Throwable th) throws Exception {
        String str = TAG;
        Log.d(str, "Exception occurs in camera open or close: " + th);
        if (!this.mCameraHandler.getLooper().getQueue().isPolling()) {
            Log.d(TAG, "CameraHandlerThread is being stuck...");
        }
        return Observable.just(Camera2Result.create(3).setCameraError(236));
    }

    public void openCamera(int i, int i2, Observer<Camera2Result> observer, boolean z) {
        int actualOpenCameraId = Camera2DataContainer.getInstance().getActualOpenCameraId(i, i2);
        String str = TAG;
        Log.d(str, "openCamera: pendingOpenId = " + actualOpenCameraId + ", mPendingCameraId = " + this.mPendingCameraId.get() + ", currentMode = " + i2 + ", mCurrentModule = " + this.mCurrentModule.get() + ", forceClose = " + z);
        if (DataRepository.dataItemFeature().c_0x1b()) {
            if (i == 1 && this.mPendingCameraId.get() != actualOpenCameraId) {
                boolean popupMotor = CompatibilityUtils.popupMotor();
                this.mPopCameraTimeStamp.set(System.currentTimeMillis());
                String str2 = TAG;
                Log.d(str2, "openCamera: popupMotor = " + popupMotor);
            } else if (i == 0) {
                this.mPopCameraTimeStamp.set(-1);
            }
        }
        if (!(this.mPendingCameraId.get() == actualOpenCameraId && this.mCurrentModule.get() == i2)) {
            removeAllAppMessages();
            this.mPendingCameraId.set(actualOpenCameraId);
            this.mCurrentModule.set(i2);
            abandonOpenObservableIfExists();
        }
        attachInObservable(observer);
        if (z) {
            this.mCameraHandler.sendEmptyMessage(2);
        } else {
            this.mCameraHandler.sendEmptyMessage(1);
        }
    }

    public void release(boolean z) {
        abandonOpenObservableIfExists();
        this.mPendingCameraId.set(-1);
        this.mCameraHandler.removeMessages(1);
        this.mCurrentModule.set(160);
        this.mCamera2Device.setCaptureBusyCallback(new c(this, z));
    }
}
