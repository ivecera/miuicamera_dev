package com.android.camera.module;

import a.a.a;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.camera2.CameraCaptureSession;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.format.DateFormat;
import android.util.Size;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import com.android.camera.AutoLockManager;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.FileCompat;
import com.android.camera.LocationManager;
import com.android.camera.OnClickAttr;
import com.android.camera.PictureSizeManager;
import com.android.camera.R;
import com.android.camera.SensorStateManager;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.panorama.AttachRunnable;
import com.android.camera.panorama.Camera1Image;
import com.android.camera.panorama.Camera2Image;
import com.android.camera.panorama.CaptureImage;
import com.android.camera.panorama.DirectionFunction;
import com.android.camera.panorama.DownDirectionFunction;
import com.android.camera.panorama.GyroscopeRoundDetector;
import com.android.camera.panorama.InputSave;
import com.android.camera.panorama.LeftDirectionFunction;
import com.android.camera.panorama.MorphoPanoramaGP3;
import com.android.camera.panorama.MorphoSensorFusion;
import com.android.camera.panorama.PanoramaGP3ImageFormat;
import com.android.camera.panorama.PanoramaSetting;
import com.android.camera.panorama.PanoramaState;
import com.android.camera.panorama.PositionDetector;
import com.android.camera.panorama.RightDirectionFunction;
import com.android.camera.panorama.RoundDetector;
import com.android.camera.panorama.SensorFusion;
import com.android.camera.panorama.SensorInfoManager;
import com.android.camera.panorama.UpDirectionFunction;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.storage.MediaProviderUtil;
import com.android.camera.storage.Storage;
import com.android.camera2.Camera2Proxy;
import com.android.gallery3d.exif.ExifHelper;
import com.mi.config.b;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
public class Panorama3Module extends BaseModule implements ModeProtocol.CameraAction, Camera2Proxy.CameraPreviewCallback {
    /* access modifiers changed from: private */
    public static final boolean DUMP_YUV = SystemProperties.getBoolean("camera.debug.panorama", false);
    private static final int MIN_SHOOTING_TIME = 600;
    private static final int SENSOR_LIST = 186;
    /* access modifiers changed from: private */
    public static final String TAG = "Panorama3Module";
    public static final Object mEngineLock = new Object();
    /* access modifiers changed from: private */
    public static final Object mPreviewImageLock = new Object();
    /* access modifiers changed from: private */
    public static final CaptureImage sAttachExit = new Camera1Image(null, 0, 0);
    /* access modifiers changed from: private */
    public final LinkedBlockingQueue<CaptureImage> mAttachImageQueue = new LinkedBlockingQueue<>();
    private int mAttachPosOffsetX;
    /* access modifiers changed from: private */
    public int mAttachPosOffsetY;
    /* access modifiers changed from: private */
    public int mCameraOrientation = 0;
    /* access modifiers changed from: private */
    public volatile boolean mCanSavePanorama = false;
    /* access modifiers changed from: private */
    public boolean mCaptureOrientationDecided = false;
    /* access modifiers changed from: private */
    public SensorInfoManager mCurrentSensorInfoManager;
    private final Object mDeviceLock = new Object();
    private int mDeviceOrientationAtCapture;
    /* access modifiers changed from: private */
    public int mDirection;
    /* access modifiers changed from: private */
    public DirectionFunction mDirectionFunction;
    /* access modifiers changed from: private */
    public Bitmap mDispPreviewImage;
    /* access modifiers changed from: private */
    public Canvas mDispPreviewImageCanvas;
    /* access modifiers changed from: private */
    public Paint mDispPreviewImagePaint;
    /* access modifiers changed from: private */
    public final ExecutorService mExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
        /* class com.android.camera.module.Panorama3Module.AnonymousClass3 */

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "PanoramaThread");
        }
    }, new RejectedExecutionHandler() {
        /* class com.android.camera.module.Panorama3Module.AnonymousClass4 */

        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            String access$400 = Panorama3Module.TAG;
            Log.w(access$400, "rejectedExecution " + runnable);
        }
    });
    private int mGoalAngle = 280;
    /* access modifiers changed from: private */
    public float[] mGravities;
    /* access modifiers changed from: private */
    public String mImageFormat = PanoramaGP3ImageFormat.YVU420_SEMIPLANAR;
    /* access modifiers changed from: private */
    public MorphoPanoramaGP3.InitParam mInitParam = new MorphoPanoramaGP3.InitParam();
    private boolean mIsRegisterSensorListener = false;
    /* access modifiers changed from: private */
    public boolean mIsSensorAverage;
    /* access modifiers changed from: private */
    public volatile boolean mIsShooting = false;
    private Location mLocation;
    /* access modifiers changed from: private */
    public float mLongSideCropRatio = 0.8766f;
    /* access modifiers changed from: private */
    public int mMaxHeight;
    /* access modifiers changed from: private */
    public int mMaxWidth;
    /* access modifiers changed from: private */
    public MorphoPanoramaGP3 mMorphoPanoramaGP3;
    private PanoramaSetting mPanoramaSetting;
    private long mPanoramaShootingStartTime;
    /* access modifiers changed from: private */
    public PanoramaState mPanoramaState;
    /* access modifiers changed from: private */
    public int mPictureHeight;
    /* access modifiers changed from: private */
    public int mPictureWidth;
    /* access modifiers changed from: private */
    public Bitmap mPreviewImage;
    /* access modifiers changed from: private */
    public int mPreviewRefY;
    /* access modifiers changed from: private */
    public volatile boolean mRequestStop = false;
    /* access modifiers changed from: private */
    public RoundDetector mRoundDetector;
    private SaveOutputImageTask mSaveOutputImageTask;
    /* access modifiers changed from: private */
    public int mSensorCnt;
    private SensorEventListener mSensorEventListener = new MySensorEventListener(new SensorListener() {
        /* class com.android.camera.module.Panorama3Module.AnonymousClass1 */

        @Override // com.android.camera.module.Panorama3Module.SensorListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (Panorama3Module.this.mIsSensorAverage) {
                float[] access$100 = Panorama3Module.this.mGravities;
                access$100[0] = access$100[0] + sensorEvent.values[0];
                float[] access$1002 = Panorama3Module.this.mGravities;
                access$1002[1] = access$1002[1] + sensorEvent.values[1];
                float[] access$1003 = Panorama3Module.this.mGravities;
                access$1003[2] = access$1003[2] + sensorEvent.values[2];
                Panorama3Module.access$208(Panorama3Module.this);
                return;
            }
            Panorama3Module.this.mGravities[0] = sensorEvent.values[0];
            Panorama3Module.this.mGravities[1] = sensorEvent.values[1];
            Panorama3Module.this.mGravities[2] = sensorEvent.values[2];
            int unused = Panorama3Module.this.mSensorCnt = 1;
        }
    });
    /* access modifiers changed from: private */
    public SensorFusion mSensorFusion = null;
    private int mSensorFusionMode;
    private ArrayList<SensorInfoManager> mSensorInfoManagerList;
    private SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateAdapter() {
        /* class com.android.camera.module.Panorama3Module.AnonymousClass2 */

        @Override // com.android.camera.SensorStateManager.SensorStateListener, com.android.camera.SensorStateManager.SensorStateAdapter
        public boolean isWorking() {
            return Panorama3Module.this.isAlive() && Panorama3Module.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener, com.android.camera.SensorStateManager.SensorStateAdapter
        public void onSensorChanged(SensorEvent sensorEvent) {
            super.onSensorChanged(sensorEvent);
            if (sensorEvent.sensor.getType() != 9) {
                Panorama3Module.this.mSensorFusion.onSensorChanged(sensorEvent);
            }
        }
    };
    private String mShutterEndTime;
    private String mShutterStartTime;
    /* access modifiers changed from: private */
    public int mSmallPreviewHeight;
    private int mSnapshotFocusMode = 1;
    private int mTargetFocusMode = 4;
    /* access modifiers changed from: private */
    public long mTimeTaken;
    /* access modifiers changed from: private */
    public float mViewAngleH = 71.231476f;
    /* access modifiers changed from: private */
    public float mViewAngleV = 56.49462f;

    private class DecideDirection extends PanoramaState {
        private boolean mHasSubmit;

        private class DecideDirectionAttach extends AttachRunnable {

            private class DecideRunnable implements Runnable {
                private DecideRunnable() {
                }

                public void run() {
                    Panorama3Module.this.reInitGravitySensorData();
                    if (Panorama3Module.this.mRequestStop) {
                        Log.w(Panorama3Module.TAG, "DecideRunnable exit request stop");
                        return;
                    }
                    Log.d(Panorama3Module.TAG, "go to PanoramaPreview in DecideRunnable");
                    synchronized (Panorama3Module.mEngineLock) {
                        if (Panorama3Module.this.mMorphoPanoramaGP3 == null) {
                            Log.w(Panorama3Module.TAG, "DecideRunnable exit due to mMorphoPanoramaGP3 is null");
                            return;
                        }
                        PanoramaState unused = Panorama3Module.this.mPanoramaState = new PanoramaPreview();
                        Panorama3Module.this.mPanoramaState.setPanoramaStateEventListener(((PanoramaState) DecideDirection.this).listener);
                        DecideDirection.this.clearListener();
                    }
                }
            }

            private DecideDirectionAttach() {
            }

            private void createDirection(int i) {
                if (Panorama3Module.this.mInitParam.output_rotation == 90 || Panorama3Module.this.mInitParam.output_rotation == 270) {
                    if (i == 3) {
                        Log.i(Panorama3Module.TAG, "direction : VERTICAL_UP");
                        int scaleV = getScaleV();
                        if (Panorama3Module.this.mCameraOrientation == 90) {
                            Panorama3Module panorama3Module = Panorama3Module.this;
                            DirectionFunction unused = panorama3Module.mDirectionFunction = new RightDirectionFunction(panorama3Module.mPictureWidth, Panorama3Module.this.mPictureHeight, Panorama3Module.this.mMaxWidth, Panorama3Module.this.mMaxHeight, scaleV, Panorama3Module.this.mInitParam.output_rotation);
                            return;
                        }
                        Panorama3Module panorama3Module2 = Panorama3Module.this;
                        DirectionFunction unused2 = panorama3Module2.mDirectionFunction = new LeftDirectionFunction(panorama3Module2.mPictureWidth, Panorama3Module.this.mPictureHeight, Panorama3Module.this.mMaxWidth, Panorama3Module.this.mMaxHeight, scaleV, Panorama3Module.this.mInitParam.output_rotation);
                    } else if (i == 4) {
                        Log.i(Panorama3Module.TAG, "direction : VERTICAL_DOWN");
                        int scaleV2 = getScaleV();
                        if (Panorama3Module.this.mCameraOrientation == 90) {
                            Panorama3Module panorama3Module3 = Panorama3Module.this;
                            DirectionFunction unused3 = panorama3Module3.mDirectionFunction = new LeftDirectionFunction(panorama3Module3.mPictureWidth, Panorama3Module.this.mPictureHeight, Panorama3Module.this.mMaxWidth, Panorama3Module.this.mMaxHeight, scaleV2, Panorama3Module.this.mInitParam.output_rotation);
                            return;
                        }
                        Panorama3Module panorama3Module4 = Panorama3Module.this;
                        DirectionFunction unused4 = panorama3Module4.mDirectionFunction = new RightDirectionFunction(panorama3Module4.mPictureWidth, Panorama3Module.this.mPictureHeight, Panorama3Module.this.mMaxWidth, Panorama3Module.this.mMaxHeight, scaleV2, Panorama3Module.this.mInitParam.output_rotation);
                    }
                } else if (i == 3) {
                    Log.i(Panorama3Module.TAG, "direction : VERTICAL_UP");
                    int scaleH = getScaleH();
                    if (Panorama3Module.this.mCameraOrientation == 90) {
                        Panorama3Module panorama3Module5 = Panorama3Module.this;
                        DirectionFunction unused5 = panorama3Module5.mDirectionFunction = new UpDirectionFunction(panorama3Module5.mPictureWidth, Panorama3Module.this.mPictureHeight, Panorama3Module.this.mMaxWidth, Panorama3Module.this.mMaxHeight, scaleH, Panorama3Module.this.mInitParam.output_rotation);
                        return;
                    }
                    Panorama3Module panorama3Module6 = Panorama3Module.this;
                    DirectionFunction unused6 = panorama3Module6.mDirectionFunction = new DownDirectionFunction(panorama3Module6.mPictureWidth, Panorama3Module.this.mPictureHeight, Panorama3Module.this.mMaxWidth, Panorama3Module.this.mMaxHeight, scaleH, Panorama3Module.this.mInitParam.output_rotation);
                } else if (i == 4) {
                    Log.i(Panorama3Module.TAG, "direction : VERTICAL_DOWN");
                    int scaleH2 = getScaleH();
                    if (Panorama3Module.this.mCameraOrientation == 90) {
                        Panorama3Module panorama3Module7 = Panorama3Module.this;
                        DirectionFunction unused7 = panorama3Module7.mDirectionFunction = new DownDirectionFunction(panorama3Module7.mPictureWidth, Panorama3Module.this.mPictureHeight, Panorama3Module.this.mMaxWidth, Panorama3Module.this.mMaxHeight, scaleH2, Panorama3Module.this.mInitParam.output_rotation);
                        return;
                    }
                    Panorama3Module panorama3Module8 = Panorama3Module.this;
                    DirectionFunction unused8 = panorama3Module8.mDirectionFunction = new UpDirectionFunction(panorama3Module8.mPictureWidth, Panorama3Module.this.mPictureHeight, Panorama3Module.this.mMaxWidth, Panorama3Module.this.mMaxHeight, scaleH2, Panorama3Module.this.mInitParam.output_rotation);
                }
            }

            private int getScaleH() {
                return Math.max(1, (Panorama3Module.this.mMaxHeight / Util.sWindowHeight) * 2);
            }

            private int getScaleV() {
                return (int) Math.max(1.0f, (((float) Panorama3Module.this.mMaxHeight) / (((float) Panorama3Module.this.mSmallPreviewHeight) / Panorama3Module.this.mLongSideCropRatio)) - 1.0f);
            }

            private boolean isUnDecideDirection(int i) {
                return i == -1 || i == 0 || i == 2 || i == 1;
            }

            /* JADX WARNING: Code restructure failed: missing block: B:40:0x019b, code lost:
                closeSrc();
                com.android.camera.module.Panorama3Module.access$3402(r18.this$1.this$0, r3);
                createDirection(r3);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:41:0x01b4, code lost:
                if (com.android.camera.module.Panorama3Module.access$3500(r18.this$1.this$0).enabled() == false) goto L_0x0002;
             */
            public void run() {
                int i;
                while (true) {
                    try {
                        CaptureImage captureImage = (CaptureImage) Panorama3Module.this.mAttachImageQueue.take();
                        if (captureImage == Panorama3Module.sAttachExit) {
                            break;
                        }
                        try {
                            setImage(captureImage);
                            synchronized (Panorama3Module.mEngineLock) {
                                Panorama3Module.this.setSensorFusionValue(captureImage);
                                if (Panorama3Module.this.mRequestStop) {
                                    Log.e(Panorama3Module.TAG, "DecideDirectionAttach request stop");
                                    closeSrc();
                                    return;
                                }
                                Log.d(Panorama3Module.TAG, "DecideDirectionAttach attach start");
                                int attach = Panorama3Module.this.mMorphoPanoramaGP3.attach(((AttachRunnable) this).byteBuffer[0], ((AttachRunnable) this).byteBuffer[1], ((AttachRunnable) this).byteBuffer[2], ((AttachRunnable) this).rowStride[0], ((AttachRunnable) this).rowStride[1], ((AttachRunnable) this).rowStride[2], ((AttachRunnable) this).pixelStride[0], ((AttachRunnable) this).pixelStride[1], ((AttachRunnable) this).pixelStride[2], Panorama3Module.this.mCurrentSensorInfoManager, null, Panorama3Module.this.getActivity().getApplicationContext());
                                Log.d(Panorama3Module.TAG, "DecideDirectionAttach attach end");
                                boolean z = attach == -1073741823;
                                if (attach != 0) {
                                    if (!z) {
                                        String access$400 = Panorama3Module.TAG;
                                        Log.e(access$400, "DecideDirectionAttach error ret:" + attach);
                                    }
                                    Log.e(Panorama3Module.TAG, String.format(Locale.US, "attach error ret:0x%08X", Integer.valueOf(attach)));
                                    closeSrc();
                                    return;
                                }
                                if (isUnDecideDirection(Panorama3Module.this.mInitParam.direction)) {
                                    i = Panorama3Module.this.mMorphoPanoramaGP3.getDirection();
                                    if (i == Panorama3Module.this.mInitParam.direction) {
                                    }
                                } else {
                                    i = Panorama3Module.this.mInitParam.direction;
                                }
                                String access$4002 = Panorama3Module.TAG;
                                Log.d(access$4002, "getDirection = " + i);
                                int[] iArr = new int[2];
                                int outputImageSize = Panorama3Module.this.mMorphoPanoramaGP3.getOutputImageSize(iArr);
                                if (outputImageSize != 0) {
                                    Log.e(Panorama3Module.TAG, String.format(Locale.US, "getOutputImageSize error ret:0x%08X", Integer.valueOf(outputImageSize)));
                                    closeSrc();
                                    return;
                                }
                                int unused = Panorama3Module.this.mMaxWidth = iArr[0];
                                int unused2 = Panorama3Module.this.mMaxHeight = iArr[1];
                                String access$4003 = Panorama3Module.TAG;
                                Log.d(access$4003, "mMaxWidth = " + Panorama3Module.this.mMaxWidth + ", mMaxHeight = " + Panorama3Module.this.mMaxHeight);
                            }
                        } finally {
                            closeSrc();
                        }
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                Panorama3Module.this.getActivity().runOnUiThread(new DecideRunnable());
                Log.d(Panorama3Module.TAG, "DecideDirectionAttach end");
            }
        }

        private DecideDirection() {
            this.mHasSubmit = false;
        }

        @Override // com.android.camera.panorama.PanoramaState
        public boolean onSaveImage(CaptureImage captureImage) {
            Panorama3Module.this.addAttachQueue(captureImage);
            if (!this.mHasSubmit) {
                Log.d(Panorama3Module.TAG, "submit DecideDirectionAttach");
                Panorama3Module.this.mExecutor.submit(new DecideDirectionAttach());
                this.mHasSubmit = true;
            }
            return true;
        }
    }

    private static class MainHandler extends Handler {
        private WeakReference<Panorama3Module> mModule;

        public MainHandler(Panorama3Module panorama3Module, Looper looper) {
            super(looper);
            this.mModule = new WeakReference<>(panorama3Module);
        }

        public void handleMessage(Message message) {
            Panorama3Module panorama3Module = this.mModule.get();
            if (panorama3Module != null) {
                if (message.what == 45) {
                    Log.d(Panorama3Module.TAG, "onMessage MSG_ABANDON_HANDLER setActivity null");
                    panorama3Module.setActivity(null);
                }
                if (!panorama3Module.isCreated()) {
                    removeCallbacksAndMessages(null);
                } else if (panorama3Module.getActivity() != null) {
                    int i = message.what;
                    if (i != 2) {
                        if (i == 17) {
                            removeMessages(17);
                            removeMessages(2);
                            panorama3Module.getWindow().addFlags(128);
                            sendEmptyMessageDelayed(2, (long) panorama3Module.getScreenDelay());
                            return;
                        } else if (i == 51) {
                            Camera camera = ((BaseModule) panorama3Module).mActivity;
                            if (camera != null && !camera.isActivityPaused()) {
                                ((BaseModule) panorama3Module).mOpenCameraFail = true;
                                panorama3Module.onCameraException();
                                return;
                            }
                            return;
                        } else if (i == 9) {
                            CameraSize cameraSize = ((BaseModule) panorama3Module).mPreviewSize;
                            int uIStyleByPreview = CameraSettings.getUIStyleByPreview(cameraSize.width, cameraSize.height);
                            if (uIStyleByPreview != ((BaseModule) panorama3Module).mUIStyle) {
                                ((BaseModule) panorama3Module).mUIStyle = uIStyleByPreview;
                            }
                            panorama3Module.initPreviewLayout();
                            return;
                        } else if (i == 10) {
                            ((BaseModule) panorama3Module).mOpenCameraFail = true;
                            panorama3Module.onCameraException();
                            return;
                        } else if (!BaseModule.DEBUG) {
                            String access$400 = Panorama3Module.TAG;
                            Log.e(access$400, "no consumer for this message: " + message.what);
                        } else {
                            throw new RuntimeException("no consumer for this message: " + message.what);
                        }
                    }
                    panorama3Module.getWindow().clearFlags(128);
                }
            }
        }
    }

    private static class MySensorEventListener implements SensorEventListener {
        private WeakReference<SensorListener> mRefSensorListener;

        public MySensorEventListener(SensorListener sensorListener) {
            this.mRefSensorListener = new WeakReference<>(sensorListener);
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            SensorListener sensorListener = this.mRefSensorListener.get();
            if (sensorListener != null) {
                sensorListener.onSensorChanged(sensorEvent);
            }
        }
    }

    private class PanoramaFirst extends PanoramaState {
        private PanoramaFirst() {
        }

        @Override // com.android.camera.panorama.PanoramaState
        public boolean onSaveImage(CaptureImage captureImage) {
            captureImage.close();
            Panorama3Module.this.setNullDirectionFunction();
            if (Panorama3Module.this.mRequestStop) {
                Log.e(Panorama3Module.TAG, "PanoramaFirst.onSaveImage request stop");
                return false;
            }
            boolean unused = Panorama3Module.this.configMorphoPanoramaGP3();
            int start = Panorama3Module.this.mMorphoPanoramaGP3.start(Panorama3Module.this.mPictureWidth, Panorama3Module.this.mPictureHeight);
            if (start != 0) {
                String access$400 = Panorama3Module.TAG;
                Log.e(access$400, "start error resultCode:" + start);
                return false;
            }
            Panorama3Module panorama3Module = Panorama3Module.this;
            PanoramaState unused2 = panorama3Module.mPanoramaState = new DecideDirection();
            Panorama3Module.this.mPanoramaState.setPanoramaStateEventListener(((PanoramaState) this).listener);
            clearListener();
            return true;
        }
    }

    private class PanoramaInit extends PanoramaState {
        private PanoramaInit() {
        }

        @Override // com.android.camera.panorama.PanoramaState
        public boolean onSaveImage(CaptureImage captureImage) {
            String unused = Panorama3Module.this.mImageFormat = captureImage.getImageFormat();
            String access$400 = Panorama3Module.TAG;
            Log.d(access$400, "PanoramaInit onSaveImage start, ImageFormat :" + Panorama3Module.this.mImageFormat);
            if (Panorama3Module.this.mRequestStop) {
                Log.w(Panorama3Module.TAG, "mRequestStop when PanoramaInit");
                captureImage.close();
                return false;
            }
            synchronized (Panorama3Module.mEngineLock) {
                if (Panorama3Module.this.createEngine()) {
                    int inputImageFormat = Panorama3Module.this.mMorphoPanoramaGP3.setInputImageFormat(Panorama3Module.this.mImageFormat);
                    if (inputImageFormat != 0) {
                        String access$4002 = Panorama3Module.TAG;
                        Log.e(access$4002, "setInputImageFormat error resultCode:" + inputImageFormat);
                    }
                    PanoramaState unused2 = Panorama3Module.this.mPanoramaState = new PanoramaFirst();
                    Panorama3Module.this.mPanoramaState.setPanoramaStateEventListener(((PanoramaState) this).listener);
                    clearListener();
                    Panorama3Module.this.mPanoramaState.onSaveImage(captureImage);
                    Log.d(Panorama3Module.TAG, "PanoramaInit onSaveImage end");
                    return true;
                }
                captureImage.close();
                return true;
            }
        }
    }

    private class PanoramaPreview extends PanoramaState {
        /* access modifiers changed from: private */
        public PositionDetector mDetector;
        private boolean mHasSubmit;
        private int mPreviewImgHeight = 0;
        private int mPreviewImgWidth = 0;
        /* access modifiers changed from: private */
        public final UiUpdateRunnable mUiUpdateRunnable = new UiUpdateRunnable();

        private class PreviewAttach extends AttachRunnable {
            private InputSave mInputSave;
            private boolean mIsAttachEnd;
            private final PostAttachRunnable mPostAttachRunnable;
            private int mResultCode;

            private class PostAttachRunnable implements Runnable {
                private PostAttachRunnable() {
                }

                public void run() {
                    if (!((BaseModule) Panorama3Module.this).mPaused && !Panorama3Module.this.mRequestStop) {
                        Panorama3Module.this.onPreviewMoving();
                        if (!Panorama3Module.this.mCaptureOrientationDecided) {
                            Panorama3Module.this.onCaptureOrientationDecided();
                        }
                        ModeProtocol.PanoramaProtocol panoramaProtocol = (ModeProtocol.PanoramaProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(176);
                        synchronized (Panorama3Module.mPreviewImageLock) {
                            if (panoramaProtocol != null) {
                                if (Panorama3Module.this.mDispPreviewImage != null) {
                                    panoramaProtocol.setDisplayPreviewBitmap(Panorama3Module.this.mDispPreviewImage);
                                }
                            }
                        }
                    }
                }
            }

            private PreviewAttach() {
                this.mIsAttachEnd = false;
                this.mPostAttachRunnable = new PostAttachRunnable();
                this.mInputSave = new InputSave();
            }

            private void checkAttachEnd(double[] dArr) {
                int detect = PanoramaPreview.this.mDetector.detect(dArr[0], dArr[1]);
                String access$400 = Panorama3Module.TAG;
                Log.v(access$400, "checkAttachEnd detect_result = " + detect);
                if (detect == -2 || detect == -1 || detect == 1) {
                    this.mResultCode = 0;
                    this.mIsAttachEnd = true;
                }
                PanoramaPreview.this.mUiUpdateRunnable.setDetectResult(detect);
                Panorama3Module.this.getActivity().runOnUiThread(PanoramaPreview.this.mUiUpdateRunnable);
                if (this.mIsAttachEnd) {
                }
            }

            /* JADX WARNING: Code restructure failed: missing block: B:25:0x01f8, code lost:
                return;
             */
            private void updatePreviewImage() {
                Bitmap bitmap;
                synchronized (Panorama3Module.mPreviewImageLock) {
                    int updatePreviewImage = Panorama3Module.this.mMorphoPanoramaGP3.updatePreviewImage(Panorama3Module.this.mPreviewImage);
                    if (updatePreviewImage != 0) {
                        String access$400 = Panorama3Module.TAG;
                        Log.e(access$400, "updatePreviewImage error ret:" + updatePreviewImage);
                    } else if (Panorama3Module.this.mPreviewImage == null) {
                        Log.w(Panorama3Module.TAG, "mPreviewImage is null when updatePreviewImage");
                    } else {
                        int i = 90;
                        if (Panorama3Module.this.mInitParam.output_rotation % 180 == 90) {
                            if (Panorama3Module.this.mInitParam.output_rotation == 270) {
                                Matrix matrix = new Matrix();
                                matrix.postRotate((float) 180);
                                bitmap = Bitmap.createBitmap(Panorama3Module.this.mPreviewImage, 0, 0, Panorama3Module.this.mPreviewImage.getWidth(), Panorama3Module.this.mPreviewImage.getHeight(), matrix, true);
                            } else {
                                bitmap = Panorama3Module.this.mPreviewImage;
                            }
                            int width = bitmap.getWidth();
                            int round = Math.round(((float) bitmap.getHeight()) * Panorama3Module.this.mLongSideCropRatio);
                            int width2 = Panorama3Module.this.mDispPreviewImage.getWidth();
                            int height = Panorama3Module.this.mDispPreviewImage.getHeight();
                            Rect rect = new Rect(0, 0, width2, height);
                            int i2 = (int) (((float) width) / (((float) width2) / ((float) height)));
                            int height2 = ((int) ((((float) bitmap.getHeight()) * (1.0f - Panorama3Module.this.mLongSideCropRatio)) / 2.0f)) + ((i2 - round) / 2);
                            Rect rect2 = new Rect(0, height2, width, i2 + height2);
                            String access$4002 = Panorama3Module.TAG;
                            Log.v(access$4002, "src " + rect2 + ", dst = " + rect);
                            Panorama3Module.this.mDispPreviewImageCanvas.drawBitmap(bitmap, rect2, rect, Panorama3Module.this.mDispPreviewImagePaint);
                        } else {
                            if (Panorama3Module.this.mInitParam.output_rotation == 180) {
                                i = -90;
                            }
                            Matrix matrix2 = new Matrix();
                            matrix2.postRotate((float) i);
                            Bitmap createBitmap = Bitmap.createBitmap(Panorama3Module.this.mPreviewImage, 0, 0, Panorama3Module.this.mPreviewImage.getWidth(), Panorama3Module.this.mPreviewImage.getHeight(), matrix2, true);
                            int width3 = createBitmap.getWidth();
                            int round2 = Math.round(((float) createBitmap.getHeight()) * Panorama3Module.this.mLongSideCropRatio);
                            int width4 = Panorama3Module.this.mDispPreviewImage.getWidth();
                            int height3 = Panorama3Module.this.mDispPreviewImage.getHeight();
                            Rect rect3 = new Rect(0, 0, width4, height3);
                            int i3 = (int) (((float) width3) / (((float) width4) / ((float) height3)));
                            int height4 = ((int) ((((float) createBitmap.getHeight()) * (1.0f - Panorama3Module.this.mLongSideCropRatio)) / 2.0f)) + ((i3 - round2) / 2);
                            Rect rect4 = new Rect(0, height4, width3, i3 + height4);
                            String access$4003 = Panorama3Module.TAG;
                            Log.v(access$4003, "src " + rect4 + ", dst = " + rect3);
                            Panorama3Module.this.mDispPreviewImageCanvas.drawBitmap(createBitmap, rect4, rect3, Panorama3Module.this.mDispPreviewImagePaint);
                        }
                    }
                }
            }

            /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
                r19.this$1.this$0.getActivity().runOnUiThread(r19.mPostAttachRunnable);
                checkAttachEnd(r15);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:30:0x010c, code lost:
                if (r19.mIsAttachEnd == false) goto L_0x0118;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:31:0x010e, code lost:
                com.android.camera.log.Log.d(com.android.camera.module.Panorama3Module.access$400(), "preview attach end");
             */
            /* JADX WARNING: Code restructure failed: missing block: B:37:0x0123, code lost:
                r0 = th;
             */
            /* JADX WARNING: Removed duplicated region for block: B:49:0x0146  */
            /* JADX WARNING: Removed duplicated region for block: B:51:0x0150  */
            public void run() {
                int i;
                Log.d(Panorama3Module.TAG, "PreviewAttach.run start");
                char c2 = 2;
                double[] dArr = new double[2];
                while (true) {
                    try {
                        CaptureImage captureImage = (CaptureImage) Panorama3Module.this.mAttachImageQueue.take();
                        if (captureImage == Panorama3Module.sAttachExit) {
                            break;
                        }
                        try {
                            setImage(captureImage);
                            if (Panorama3Module.DUMP_YUV) {
                                this.mInputSave.onSaveImage(captureImage, Panorama3Module.this.mImageFormat);
                            }
                            synchronized (Panorama3Module.mEngineLock) {
                                try {
                                    if (Panorama3Module.this.mRequestStop) {
                                        Log.w(Panorama3Module.TAG, "PreviewAttach request stop");
                                        closeSrc();
                                        return;
                                    }
                                    Log.v(Panorama3Module.TAG, "PreviewAttach attach start");
                                    Panorama3Module.this.setSensorFusionValue(captureImage);
                                    i = -1;
                                    try {
                                        if (Panorama3Module.this.mMorphoPanoramaGP3.attach(((AttachRunnable) this).byteBuffer[0], ((AttachRunnable) this).byteBuffer[1], ((AttachRunnable) this).byteBuffer[c2], ((AttachRunnable) this).rowStride[0], ((AttachRunnable) this).rowStride[1], ((AttachRunnable) this).rowStride[c2], ((AttachRunnable) this).pixelStride[0], ((AttachRunnable) this).pixelStride[1], ((AttachRunnable) this).pixelStride[c2], Panorama3Module.this.mCurrentSensorInfoManager, dArr, Panorama3Module.this.getActivity()) != 0) {
                                            Log.e(Panorama3Module.TAG, "PreviewAttach attach error.");
                                            this.mResultCode = -1;
                                        } else {
                                            Log.v(Panorama3Module.TAG, "PreviewAttach attach end");
                                            boolean unused = Panorama3Module.this.mCanSavePanorama = true;
                                            updatePreviewImage();
                                            Log.v(Panorama3Module.TAG, "mCenter = " + dArr[0] + ", " + dArr[1]);
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    throw th;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            closeSrc();
                            throw th;
                        }
                        closeSrc();
                        dArr = dArr;
                        c2 = 2;
                    } catch (InterruptedException e2) {
                        e = e2;
                        i = -1;
                        Log.w(Panorama3Module.TAG, "PreviewAttach interrupted", e);
                        this.mResultCode = i;
                        if (!Panorama3Module.this.mRequestStop) {
                        }
                    }
                }
                try {
                    closeSrc();
                } catch (InterruptedException e3) {
                    e = e3;
                }
                if (!Panorama3Module.this.mRequestStop) {
                    Log.d(Panorama3Module.TAG, "PreviewAttach exit. (request exit)");
                    return;
                }
                final int i2 = this.mResultCode;
                Panorama3Module.this.getActivity().runOnUiThread(new Runnable() {
                    /* class com.android.camera.module.Panorama3Module.PanoramaPreview.PreviewAttach.AnonymousClass1 */

                    public void run() {
                        PanoramaPreview.this.attachEnd(i2);
                    }
                });
                Log.d(Panorama3Module.TAG, "PreviewAttach exit.");
            }
        }

        /* access modifiers changed from: private */
        public class UiUpdateRunnable implements Runnable {
            private int mDetectResult;

            private UiUpdateRunnable() {
            }

            /* JADX WARNING: Code restructure failed: missing block: B:19:0x0084, code lost:
                com.android.camera.module.Panorama3Module.access$6302(r5.this$1.this$0, java.lang.Math.min(r3, r4) / 2);
                r0 = (com.android.camera.protocol.ModeProtocol.PanoramaProtocol) com.android.camera.protocol.ModeCoordinatorImpl.getInstance().getAttachProtocol(176);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:20:0x009d, code lost:
                if (r0 == null) goto L_?;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:21:0x009f, code lost:
                r0.setDirectionPosition(r2, com.android.camera.module.Panorama3Module.access$6300(r5.this$1.this$0));
             */
            /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ac, code lost:
                if (r5.mDetectResult == 2) goto L_0x00b3;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:23:0x00ae, code lost:
                r0.setDirectionTooFast(false, 0);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b3, code lost:
                r0.setDirectionTooFast(true, com.android.camera.constant.DurationConstant.DURATION_LANDSCAPE_HINT);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
                return;
             */
            public void run() {
                int i = this.mDetectResult;
                if (i == -2 || i == -1 || i == 1) {
                    if (this.mDetectResult != 1) {
                        String access$400 = Panorama3Module.TAG;
                        Log.w(access$400, "stopPanoramaShooting due to detect result " + this.mDetectResult);
                    }
                    Panorama3Module.this.stopPanoramaShooting(true);
                    return;
                }
                RectF frameRect = PanoramaPreview.this.mDetector.getFrameRect();
                String access$4002 = Panorama3Module.TAG;
                Log.v(access$4002, "frame_rect = " + frameRect);
                Point point = new Point();
                if (Panorama3Module.this.mDirection == 3) {
                    point.x = (int) frameRect.right;
                } else {
                    point.x = (int) frameRect.left;
                }
                point.y = (int) frameRect.centerY();
                synchronized (Panorama3Module.mPreviewImageLock) {
                    if (Panorama3Module.this.mDispPreviewImage == null) {
                        Log.w(Panorama3Module.TAG, "mPreviewImage is null in UiUpdateRunnable");
                    } else {
                        int width = Panorama3Module.this.mDispPreviewImage.getWidth();
                        int height = Panorama3Module.this.mDispPreviewImage.getHeight();
                    }
                }
            }

            public void setDetectResult(int i) {
                this.mDetectResult = i;
            }
        }

        @TargetApi(21)
        public PanoramaPreview() {
            int scale = Panorama3Module.this.mDirectionFunction.getScale();
            Size previewSize = Panorama3Module.this.mDirectionFunction.getPreviewSize();
            Log.d(Panorama3Module.TAG, String.format(Locale.US, "previewSize %dx%d, scale %d", Integer.valueOf(previewSize.getWidth()), Integer.valueOf(previewSize.getHeight()), Integer.valueOf(scale)));
            this.mPreviewImgWidth = previewSize.getWidth();
            this.mPreviewImgHeight = previewSize.getHeight();
            String access$400 = Panorama3Module.TAG;
            Log.d(access$400, "mPreviewImgWidth = " + this.mPreviewImgWidth + ", mPreviewImgHeight = " + this.mPreviewImgHeight);
            int previewImage = Panorama3Module.this.mMorphoPanoramaGP3.setPreviewImage(this.mPreviewImgWidth, this.mPreviewImgHeight);
            if (previewImage != 0) {
                Log.e(Panorama3Module.TAG, String.format(Locale.US, "setPreviewImage error ret:0x%08X", Integer.valueOf(previewImage)));
            }
            ModeProtocol.PanoramaProtocol panoramaProtocol = (ModeProtocol.PanoramaProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(176);
            if (panoramaProtocol != null) {
                this.mDetector = new PositionDetector(Panorama3Module.this.mInitParam, panoramaProtocol.getPreivewContainer(), false, ((BaseModule) Panorama3Module.this).mCameraDisplayOrientation, Panorama3Module.this.mPictureWidth, Panorama3Module.this.mPictureHeight, Panorama3Module.this.mDirectionFunction.getDirection(), Panorama3Module.this.mMaxWidth, Panorama3Module.this.mMaxHeight);
                Panorama3Module.this.mRoundDetector.setStartPosition(Panorama3Module.this.mInitParam.output_rotation, 1, Panorama3Module.this.mViewAngleH, Panorama3Module.this.mViewAngleV, false);
                allocateDisplayBuffers();
            }
        }

        private void allocateDisplayBuffers() {
            synchronized (Panorama3Module.mPreviewImageLock) {
                if (!(Panorama3Module.this.mPreviewImage == null || (Panorama3Module.this.mPreviewImage.getWidth() == this.mPreviewImgWidth && Panorama3Module.this.mPreviewImage.getHeight() == this.mPreviewImgHeight))) {
                    Panorama3Module.this.mPreviewImage.recycle();
                    Bitmap unused = Panorama3Module.this.mPreviewImage = null;
                    Panorama3Module.this.mDispPreviewImage.recycle();
                    Bitmap unused2 = Panorama3Module.this.mDispPreviewImage = null;
                }
                if (Panorama3Module.this.mPreviewImage == null) {
                    Bitmap unused3 = Panorama3Module.this.mPreviewImage = Bitmap.createBitmap(this.mPreviewImgWidth, this.mPreviewImgHeight, Bitmap.Config.ARGB_8888);
                    Bitmap unused4 = Panorama3Module.this.mDispPreviewImage = Bitmap.createBitmap(Util.sWindowWidth, Panorama3Module.this.mSmallPreviewHeight, Bitmap.Config.ARGB_8888);
                    int unused5 = Panorama3Module.this.mAttachPosOffsetY = ((Panorama3Module.this.mDispPreviewImage.getWidth() * Panorama3Module.this.mPictureWidth) / Panorama3Module.this.mPictureHeight) / 2;
                    String access$400 = Panorama3Module.TAG;
                    Log.d(access$400, "mAttachPosOffsetY = " + Panorama3Module.this.mAttachPosOffsetY);
                    Canvas unused6 = Panorama3Module.this.mDispPreviewImageCanvas = new Canvas(Panorama3Module.this.mDispPreviewImage);
                    Paint unused7 = Panorama3Module.this.mDispPreviewImagePaint = new Paint();
                    Panorama3Module.this.mDispPreviewImagePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                }
            }
        }

        /* access modifiers changed from: private */
        public void attachEnd(int i) {
            Panorama3Module.this.initAttachQueue();
            ((PanoramaState) this).listener.requestEnd(this, i);
            if (i == 0) {
                Panorama3Module.this.stopPanoramaShooting(true);
            }
        }

        @Override // com.android.camera.panorama.PanoramaState
        public boolean onSaveImage(CaptureImage captureImage) {
            Panorama3Module.this.addAttachQueue(captureImage);
            if (!this.mHasSubmit) {
                Log.d(Panorama3Module.TAG, "submit PreviewAttach");
                Panorama3Module.this.mExecutor.submit(new PreviewAttach());
                this.mHasSubmit = true;
            }
            return true;
        }
    }

    private class SaveOutputImageTask extends AsyncTask<Void, Integer, Integer> {
        private boolean mSaveImage;
        private long start_time;

        SaveOutputImageTask(boolean z) {
            this.mSaveImage = z;
        }

        private void savePanoramaPicture() {
            Log.d(Panorama3Module.TAG, "savePanoramaPicture start");
            synchronized (Panorama3Module.mEngineLock) {
                try {
                    Log.d(Panorama3Module.TAG, "savePanoramaPicture enter mEngineLock");
                    if (Panorama3Module.this.mMorphoPanoramaGP3 == null) {
                        Log.w(Panorama3Module.TAG, "savePanoramaPicture while mMorphoPanoramaGP3 is null");
                    } else if (!this.mSaveImage) {
                        Log.w(Panorama3Module.TAG, String.format("savePanoramaPicture, don't save image", new Object[0]));
                        int end = Panorama3Module.this.mMorphoPanoramaGP3.end(1, (double) Panorama3Module.this.mRoundDetector.currentDegree0Base());
                        if (end != 0) {
                            Log.e(Panorama3Module.TAG, String.format("savePanoramaPicture, end() -> 0x%x", Integer.valueOf(end)));
                        }
                        Panorama3Module.this.finishEngine();
                    } else {
                        int noiseReductionParam = Panorama3Module.this.mMorphoPanoramaGP3.setNoiseReductionParam(0);
                        if (noiseReductionParam != 0) {
                            String access$400 = Panorama3Module.TAG;
                            Log.e(access$400, "setNoiseReductionParam error ret:" + noiseReductionParam);
                        }
                        int unsharpStrength = Panorama3Module.this.mMorphoPanoramaGP3.setUnsharpStrength(1536);
                        if (unsharpStrength != 0) {
                            Log.e(Panorama3Module.TAG, String.format(Locale.US, "setUnsharpStrength error ret:0x%08X", Integer.valueOf(unsharpStrength)));
                        }
                        int end2 = Panorama3Module.this.mMorphoPanoramaGP3.end(1, (double) Panorama3Module.this.mRoundDetector.currentDegree0Base());
                        if (end2 != 0) {
                            Log.e(Panorama3Module.TAG, String.format("savePanoramaPicture, end() -> 0x%x", Integer.valueOf(end2)));
                            Panorama3Module.this.finishEngine();
                            return;
                        }
                        Rect rect = new Rect();
                        int clippingRect = Panorama3Module.this.mMorphoPanoramaGP3.getClippingRect(rect);
                        if (clippingRect != 0) {
                            Log.e(Panorama3Module.TAG, String.format("getClippingRect() -> 0x%x", Integer.valueOf(clippingRect)));
                            Panorama3Module.this.finishEngine();
                            return;
                        }
                        int width = rect.width();
                        int height = rect.height();
                        if (width == 0 || height == 0) {
                            String access$4002 = Panorama3Module.TAG;
                            Log.e(access$4002, "getClippingRect() " + rect);
                            Panorama3Module.this.finishEngine();
                            return;
                        }
                        int createOutputImage = Panorama3Module.this.mMorphoPanoramaGP3.createOutputImage(rect);
                        if (createOutputImage != 0) {
                            String access$4003 = Panorama3Module.TAG;
                            Log.e(access$4003, "createOutputImage error ret:" + createOutputImage);
                            Panorama3Module.this.finishEngine();
                            return;
                        }
                        String access$1100 = Panorama3Module.this.createNameString(Panorama3Module.this.mTimeTaken);
                        String generateFilepath4Image = Storage.generateFilepath4Image(access$1100, false);
                        if (!Panorama3Module.this.savePanoramaFile(generateFilepath4Image, width, height)) {
                            Panorama3Module.this.finishEngine();
                            return;
                        }
                        Panorama3Module.this.addImageAsApplication(generateFilepath4Image, access$1100, width, height, Panorama3Module.this.calibrateRotation(1));
                        Panorama3Module.this.finishEngine();
                        Log.d(Panorama3Module.TAG, "savePanoramaPicture end");
                    }
                } finally {
                    Panorama3Module.this.finishEngine();
                }
            }
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... voidArr) {
            Log.v(Panorama3Module.TAG, "doInBackground>>");
            savePanoramaPicture();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer num) {
            Log.d(Panorama3Module.TAG, "PanoramaFinish done");
            Camera camera = ((BaseModule) Panorama3Module.this).mActivity;
            if (camera != null) {
                AutoLockManager.getInstance(camera).hibernateDelayed();
            }
            if (((BaseModule) Panorama3Module.this).mPaused) {
                boolean unused = Panorama3Module.this.mIsShooting = false;
                Log.w(Panorama3Module.TAG, "panorama mode has been paused");
                return;
            }
            if (camera != null) {
                camera.getThumbnailUpdater().updateThumbnailView(true);
            }
            Panorama3Module.this.enableCameraControls(true);
            ((BaseModule) Panorama3Module.this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.Panorama3Module.SaveOutputImageTask.AnonymousClass1 */

                public void run() {
                    Panorama3Module.this.handlePendingScreenSlide();
                }
            });
            boolean unused2 = Panorama3Module.this.mIsShooting = false;
            Log.d(Panorama3Module.TAG, String.format(Locale.ENGLISH, "[MORTIME] PanoramaFinish time = %d", Long.valueOf(System.currentTimeMillis() - this.start_time)));
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            this.start_time = System.currentTimeMillis();
        }
    }

    public interface SensorListener {
        void onSensorChanged(SensorEvent sensorEvent);
    }

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static /* synthetic */ int access$208(Panorama3Module panorama3Module) {
        int i = panorama3Module.mSensorCnt;
        panorama3Module.mSensorCnt = i + 1;
        return i;
    }

    /* access modifiers changed from: private */
    public void addAttachQueue(CaptureImage captureImage) {
        if (captureImage == null) {
            Log.w(TAG, "addAttachQueue failed due to image is null");
            return;
        }
        boolean z = false;
        try {
            if (this.mRequestStop) {
                Log.w(TAG, "addAttachQueue failed due to request stop");
                captureImage.close();
                return;
            }
            z = this.mAttachImageQueue.offer(captureImage);
            while (this.mAttachImageQueue.size() > 1) {
                CaptureImage poll = this.mAttachImageQueue.poll();
                if (poll != null) {
                    poll.close();
                }
            }
            Log.v(TAG, "addAttachQueue");
        } finally {
            if (!z) {
                captureImage.close();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0032, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0034, code lost:
        if (r4 != null) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0036, code lost:
        $closeResource(r0, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        throw r0;
     */
    public void addImageAsApplication(String str, String str2, int i, int i2, int i3) {
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (!Storage.isUseDocumentMode()) {
            ExifHelper.writeExifByFilePath(str, i3, currentLocation, this.mTimeTaken);
        } else {
            try {
                ParcelFileDescriptor parcelFileDescriptor = FileCompat.getParcelFileDescriptor(str, true);
                ExifHelper.writeExifByFd(parcelFileDescriptor.getFileDescriptor(), i3, currentLocation, this.mTimeTaken);
                if (parcelFileDescriptor != null) {
                    $closeResource(null, parcelFileDescriptor);
                }
            } catch (Exception e2) {
                String str3 = TAG;
                Log.e(str3, "open file failed, filePath " + str, e2);
            }
        }
        boolean z = currentLocation != null;
        Uri addImageForGroupOrPanorama = Storage.addImageForGroupOrPanorama(CameraAppImpl.getAndroidContext(), str, i3, this.mTimeTaken, this.mLocation, i, i2);
        if (addImageForGroupOrPanorama == null) {
            String str4 = TAG;
            Log.w(str4, "insert MediaProvider failed, attempt to find uri by path, " + str);
            addImageForGroupOrPanorama = MediaProviderUtil.getContentUriFromPath(CameraAppImpl.getAndroidContext(), str);
        }
        String str5 = TAG;
        Log.d(str5, "addImageAsApplication uri = " + addImageForGroupOrPanorama + ", path = " + str);
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, false);
        trackGeneralInfo(hashMap, 1, false, null, z, 0);
        MistatsWrapper.PictureTakenParameter pictureTakenParameter = new MistatsWrapper.PictureTakenParameter();
        pictureTakenParameter.takenNum = 1;
        pictureTakenParameter.burst = false;
        pictureTakenParameter.location = z;
        pictureTakenParameter.aiSceneName = null;
        pictureTakenParameter.isEnteringMoon = false;
        pictureTakenParameter.isSelectMoonMode = false;
        pictureTakenParameter.beautyValues = null;
        trackPictureTaken(pictureTakenParameter);
        Camera camera = ((BaseModule) this).mActivity;
        if (isCreated() && camera != null) {
            camera.getScreenHint().updateHint();
            if (addImageForGroupOrPanorama != null) {
                camera.onNewUriArrived(addImageForGroupOrPanorama, str2);
                Thumbnail createThumbnailFromUri = Thumbnail.createThumbnailFromUri(camera, addImageForGroupOrPanorama, false);
                Util.broadcastNewPicture(camera, addImageForGroupOrPanorama);
                camera.getThumbnailUpdater().setThumbnail(createThumbnailFromUri, false, false);
            }
        }
    }

    /* access modifiers changed from: private */
    public int calibrateRotation(int i) {
        if (!(i == 0 || i == 90 || i == 180 || i == 270)) {
            i = 0;
        }
        return (i + this.mDeviceOrientationAtCapture) % 360;
    }

    /* access modifiers changed from: private */
    public boolean configMorphoPanoramaGP3() {
        Log.d(TAG, "configMorphoPanoramaGP3 start");
        this.mMorphoPanoramaGP3.setAttachEnabled(true);
        this.mMorphoPanoramaGP3.disableSaveInputImages();
        int shrinkRatio = this.mMorphoPanoramaGP3.setShrinkRatio((double) this.mPanoramaSetting.getShrink_ratio());
        if (shrinkRatio != 0) {
            Log.e(TAG, String.format(Locale.US, "setShrinkRatio error ret:0x%08X", Integer.valueOf(shrinkRatio)));
        }
        int calcseamPixnum = this.mMorphoPanoramaGP3.setCalcseamPixnum(this.mPanoramaSetting.getCalcseam_pixnum());
        if (calcseamPixnum != 0) {
            Log.e(TAG, String.format(Locale.US, "setCalcseamPixnum error ret:0x%08X", Integer.valueOf(calcseamPixnum)));
        }
        int useDeform = this.mMorphoPanoramaGP3.setUseDeform(this.mPanoramaSetting.isUse_deform());
        if (useDeform != 0) {
            Log.e(TAG, String.format(Locale.US, "setUseDeform error ret:0x%08X", Integer.valueOf(useDeform)));
        }
        int useLuminanceCorrection = this.mMorphoPanoramaGP3.setUseLuminanceCorrection(this.mPanoramaSetting.isUse_luminance_correction());
        if (useLuminanceCorrection != 0) {
            Log.e(TAG, String.format(Locale.US, "setUseLuminanceCorrection error ret:0x%08X", Integer.valueOf(useLuminanceCorrection)));
        }
        int seamsearchRatio = this.mMorphoPanoramaGP3.setSeamsearchRatio(this.mPanoramaSetting.getSeamsearch_ratio());
        if (seamsearchRatio != 0) {
            Log.e(TAG, String.format(Locale.US, "setSeamsearchRatio error ret:0x%08X", Integer.valueOf(seamsearchRatio)));
        }
        int zrotationCoeff = this.mMorphoPanoramaGP3.setZrotationCoeff(this.mPanoramaSetting.getZrotation_coeff());
        if (zrotationCoeff != 0) {
            Log.e(TAG, String.format(Locale.US, "setZrotationCoeff error ret:0x%08X", Integer.valueOf(zrotationCoeff)));
        }
        int drawThreshold = this.mMorphoPanoramaGP3.setDrawThreshold(this.mPanoramaSetting.getDraw_threshold());
        if (drawThreshold != 0) {
            Log.e(TAG, String.format(Locale.US, "setDrawThreshold error ret:0x%08X", Integer.valueOf(drawThreshold)));
        }
        int aovGain = this.mMorphoPanoramaGP3.setAovGain(this.mPanoramaSetting.getAov_gain());
        if (aovGain != 0) {
            Log.e(TAG, String.format(Locale.US, "setAovGain error ret:0x%08X", Integer.valueOf(aovGain)));
        }
        int distortionCorrectionParam = this.mMorphoPanoramaGP3.setDistortionCorrectionParam(this.mPanoramaSetting.getDistortion_k1(), this.mPanoramaSetting.getDistortion_k2(), this.mPanoramaSetting.getDistortion_k3(), this.mPanoramaSetting.getDistortion_k4());
        if (distortionCorrectionParam != 0) {
            Log.e(TAG, String.format(Locale.US, "setDistortionCorrectionParam error ret:0x%08X", Integer.valueOf(distortionCorrectionParam)));
        }
        int rotationRatio = this.mMorphoPanoramaGP3.setRotationRatio(this.mPanoramaSetting.getRotation_ratio());
        if (rotationRatio != 0) {
            Log.e(TAG, String.format(Locale.US, "setRotationRatio error ret:0x%08X", Integer.valueOf(rotationRatio)));
        }
        int sensorUseMode = this.mMorphoPanoramaGP3.setSensorUseMode(0);
        if (sensorUseMode != 0) {
            Log.e(TAG, String.format(Locale.US, "setSensorUseMode error ret:0x%08X", Integer.valueOf(sensorUseMode)));
        }
        int projectionMode = this.mMorphoPanoramaGP3.setProjectionMode(0);
        if (projectionMode != 0) {
            Log.e(TAG, String.format(Locale.US, "setProjectionMode error ret:0x%08X", Integer.valueOf(projectionMode)));
        }
        int motionDetectionMode = this.mMorphoPanoramaGP3.setMotionDetectionMode(0);
        if (motionDetectionMode != 0) {
            Log.e(TAG, String.format(Locale.US, "setMotionDetectionMode error ret:0x%08X", Integer.valueOf(motionDetectionMode)));
        }
        Log.d(TAG, "configMorphoPanoramaGP3 end");
        return true;
    }

    public static String createDateStringForAppSeg(long j) {
        Date date = new Date(j);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(date);
    }

    /* access modifiers changed from: private */
    public boolean createEngine() {
        if (this.mMorphoPanoramaGP3 != null) {
            Log.w(TAG, "finish prior Engine");
            finishEngine();
        }
        this.mMorphoPanoramaGP3 = new MorphoPanoramaGP3();
        if (PanoramaGP3ImageFormat.YUV420_PLANAR.equals(this.mImageFormat)) {
            MorphoPanoramaGP3.InitParam initParam = this.mInitParam;
            initParam.input_format = this.mImageFormat;
            initParam.output_format = PanoramaGP3ImageFormat.YUV420_SEMIPLANAR;
        } else {
            MorphoPanoramaGP3.InitParam initParam2 = this.mInitParam;
            String str = this.mImageFormat;
            initParam2.input_format = str;
            initParam2.output_format = str;
        }
        MorphoPanoramaGP3.InitParam initParam3 = this.mInitParam;
        initParam3.input_width = this.mPictureWidth;
        initParam3.input_height = this.mPictureHeight;
        initParam3.aovx = (double) this.mViewAngleH;
        initParam3.aovy = (double) this.mViewAngleV;
        initParam3.direction = CameraSettings.getPanoramaMoveDirection(((BaseModule) this).mActivity);
        int displayRotation = Util.getDisplayRotation(((BaseModule) this).mActivity);
        int i = ((BaseModule) this).mOrientation;
        if (i == -1) {
            this.mInitParam.output_rotation = ((this.mCameraOrientation + displayRotation) + 360) % 360;
        } else {
            this.mInitParam.output_rotation = (((this.mCameraOrientation + displayRotation) + i) + 360) % 360;
        }
        String cameraLensType = CameraSettings.getCameraLensType(166);
        String str2 = TAG;
        Log.d(str2, "lensType " + cameraLensType);
        if (ComponentManuallyDualLens.LENS_WIDE.equals(cameraLensType)) {
            this.mInitParam.goal_angle = (double) this.mGoalAngle;
        } else if (Build.DEVICE.equals("cepheus")) {
            this.mInitParam.goal_angle = 152.18d;
        } else {
            this.mInitParam.goal_angle = ((double) this.mGoalAngle) * 0.6265d;
        }
        int i2 = this.mCameraOrientation;
        int rotation = this.mSensorFusion.setRotation(i2 != 90 ? i2 != 180 ? i2 != 270 ? 0 : 3 : 2 : 1);
        if (rotation != 0) {
            Log.e(TAG, String.format(Locale.US, "SensorFusion.setRotation error ret:0x%08X", Integer.valueOf(rotation)));
        }
        initializeEngine(this.mMorphoPanoramaGP3, this.mInitParam);
        return true;
    }

    /* access modifiers changed from: private */
    public String createNameString(long j) {
        return DateFormat.format(getString(R.string.pano_file_name_format), j).toString();
    }

    private void doLaterReleaseIfNeed() {
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null && camera.isActivityPaused()) {
            ((BaseModule) this).mActivity.pause();
            ((BaseModule) this).mActivity.releaseAll(true, true);
        }
    }

    /* access modifiers changed from: private */
    public void finishEngine() {
        if (this.mMorphoPanoramaGP3 != null) {
            Log.d(TAG, "finishEngine start");
            this.mMorphoPanoramaGP3.deleteNativeOutputInfo();
            int finish = this.mMorphoPanoramaGP3.finish(true);
            if (finish != 0) {
                Log.e(TAG, String.format(Locale.US, "finish error ret:0x%08X", Integer.valueOf(finish)));
            }
            Log.d(TAG, "finishEngine end");
            this.mMorphoPanoramaGP3 = null;
        }
    }

    /* access modifiers changed from: private */
    public void initAttachQueue() {
        while (this.mAttachImageQueue.size() > 0) {
            CaptureImage poll = this.mAttachImageQueue.poll();
            if (poll != null) {
                poll.close();
            }
        }
        Log.d(TAG, "initAttachQueue");
    }

    /* access modifiers changed from: private */
    public void initPreviewLayout() {
        if (isAlive()) {
            CameraScreenNail cameraScreenNail = ((BaseModule) this).mActivity.getCameraScreenNail();
            CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
            cameraScreenNail.setPreviewSize(cameraSize.width, cameraSize.height);
            CameraScreenNail cameraScreenNail2 = ((BaseModule) this).mActivity.getCameraScreenNail();
            int width = cameraScreenNail2.getWidth();
            int dimensionPixelSize = ((BaseModule) this).mActivity.getResources().getDimensionPixelSize(R.dimen.pano_preview_hint_frame_height);
            int height = (width * dimensionPixelSize) / ((int) (((float) cameraScreenNail2.getHeight()) * this.mLongSideCropRatio));
            CameraSize cameraSize2 = ((BaseModule) this).mPreviewSize;
            ((ModeProtocol.PanoramaProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(176)).initPreviewLayout(height, dimensionPixelSize, cameraSize2.width, cameraSize2.height);
        }
    }

    private boolean initializeEngine(MorphoPanoramaGP3 morphoPanoramaGP3, MorphoPanoramaGP3.InitParam initParam) {
        Log.d(TAG, "initializeEngine start");
        int createNativeOutputInfo = morphoPanoramaGP3.createNativeOutputInfo();
        if (createNativeOutputInfo != 0) {
            Log.e(TAG, String.format(Locale.US, "createNativeOutputInfo error ret:0x%08X", Integer.valueOf(createNativeOutputInfo)));
        }
        int initialize = morphoPanoramaGP3.initialize(initParam);
        if (initialize != 0) {
            Log.e(TAG, String.format(Locale.US, "initialize error ret:0x%08X", Integer.valueOf(initialize)));
            return false;
        }
        Log.e(TAG, "initializeEngine end");
        return true;
    }

    private boolean isProcessingFinishTask() {
        SaveOutputImageTask saveOutputImageTask = this.mSaveOutputImageTask;
        return (saveOutputImageTask == null || saveOutputImageTask.getStatus() == AsyncTask.Status.FINISHED) ? false : true;
    }

    private boolean isShootingTooShort() {
        return SystemClock.elapsedRealtime() - this.mPanoramaShootingStartTime < 600;
    }

    private void keepScreenOnAwhile() {
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.sendEmptyMessageDelayed(17, 1000);
        }
    }

    /* access modifiers changed from: private */
    public void onCaptureOrientationDecided() {
        Log.d(TAG, "onCaptureOrientationDecided");
        ((ModeProtocol.PanoramaProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(176)).onCaptureOrientationDecided(this.mDirection, this.mAttachPosOffsetX, this.mAttachPosOffsetY);
        this.mCaptureOrientationDecided = true;
    }

    /* access modifiers changed from: private */
    public void onPreviewMoving() {
        ((ModeProtocol.PanoramaProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(176)).onPreviewMoving();
    }

    private void onSaveFinish() {
        Camera2Proxy camera2Proxy;
        if (isAlive() && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null) {
            if (((BaseModule) this).mAeLockSupported) {
                camera2Proxy.setAELock(false);
            }
            if (((BaseModule) this).mAwbLockSupported) {
                ((BaseModule) this).mCamera2Device.setAWBLock(false);
            }
            ((BaseModule) this).mCamera2Device.setFocusMode(this.mTargetFocusMode);
            startPreview();
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onPostSavingFinish();
            }
        }
    }

    private void onStopShooting(boolean z) {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState == null) {
            String str = TAG;
            Log.w(str, "onStopShooting recordState is null, succeed = " + z);
            return;
        }
        if (z) {
            recordState.onPostSavingStart();
        } else {
            recordState.onFailed();
        }
        Bitmap bitmap = this.mDispPreviewImage;
        if (bitmap != null) {
            bitmap.eraseColor(0);
        }
        onSaveFinish();
    }

    /* access modifiers changed from: private */
    public void reInitGravitySensorData() {
        float[] fArr = this.mGravities;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        this.mSensorCnt = 0;
    }

    private void registerSensorListener() {
        if (!this.mIsRegisterSensorListener) {
            this.mIsRegisterSensorListener = true;
            this.mIsSensorAverage = false;
            this.mSensorCnt = 0;
            this.mGravities = new float[3];
            this.mRoundDetector = new GyroscopeRoundDetector();
            ((BaseModule) this).mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
            ((BaseModule) this).mActivity.getSensorStateManager().register(186);
        }
    }

    private void requestStopShoot() {
        addAttachQueue(sAttachExit);
        this.mRequestStop = true;
    }

    private void resetScreenOn() {
        ((BaseModule) this).mHandler.removeMessages(17);
        ((BaseModule) this).mHandler.removeMessages(2);
    }

    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Incorrect type for immutable var: ssa=int, code=?, for r10v0, types: [boolean, int] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x008c  */
    public boolean savePanoramaFile(String str, int i, int i2) {
        ? r10;
        int i3;
        ParcelFileDescriptor parcelFileDescriptor;
        MorphoPanoramaGP3.GalleryInfoData galleryInfoData = new MorphoPanoramaGP3.GalleryInfoData();
        int integer = (CameraSettings.getEncodingQuality(false).toInteger(false) * 256) / 100;
        if (Storage.isUseDocumentMode()) {
            try {
                ParcelFileDescriptor parcelFileDescriptor2 = FileCompat.getParcelFileDescriptor(str, true);
                try {
                    parcelFileDescriptor = parcelFileDescriptor2;
                    try {
                        i3 = this.mMorphoPanoramaGP3.savePanorama360(i, i2, parcelFileDescriptor2.getFileDescriptor(), integer, this.mShutterStartTime, this.mShutterEndTime, false, galleryInfoData, false);
                        if (parcelFileDescriptor != null) {
                            try {
                                $closeResource(null, parcelFileDescriptor);
                            } catch (Exception e2) {
                                e = e2;
                            }
                        }
                        r10 = 1;
                    } catch (Throwable th) {
                        th = th;
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            if (parcelFileDescriptor != null) {
                                $closeResource(th, parcelFileDescriptor);
                            }
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    parcelFileDescriptor = parcelFileDescriptor2;
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                i3 = -1;
                String str2 = TAG;
                Log.e(str2, "open file failed, filePath " + str, e);
                r10 = 1;
                if (i3 != 0) {
                }
            }
        } else {
            r10 = 1;
            i3 = this.mMorphoPanoramaGP3.savePanorama360(i, i2, str, integer, this.mShutterStartTime, this.mShutterEndTime, false, galleryInfoData, false);
        }
        if (i3 != 0) {
            Log.d(TAG, galleryInfoData.toString());
            return r10;
        }
        String str3 = TAG;
        Object[] objArr = new Object[r10];
        objArr[0] = Integer.valueOf(i3);
        Log.e(str3, String.format("savePanorama360() -> 0x%x", objArr));
        return false;
    }

    private void setInitialRotationByGravity() {
        int i;
        if (this.mMorphoPanoramaGP3 != null && (i = this.mSensorCnt) > 0) {
            float[] fArr = this.mGravities;
            float f2 = fArr[0] / ((float) i);
            float f3 = fArr[1] / ((float) i);
            float f4 = fArr[2] / ((float) i);
            Log.d(TAG, String.format(Locale.US, "Gravity Sensor Value X=%f Y=%f Z=%f cnt=%d", Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4), Integer.valueOf(this.mSensorCnt)));
            int initialRotationByGravity = this.mMorphoPanoramaGP3.setInitialRotationByGravity((double) f2, (double) f3, (double) f4);
            if (initialRotationByGravity != 0) {
                Log.e(TAG, String.format(Locale.US, "setInitialRotationByGravity error ret:0x%08X", Integer.valueOf(initialRotationByGravity)));
            }
        }
    }

    /* access modifiers changed from: private */
    public void setSensorFusionValue(CaptureImage captureImage) {
        SensorFusion sensorFusion;
        int gyroscopeData;
        int size;
        int size2;
        int size3;
        if (this.mMorphoPanoramaGP3 != null && (sensorFusion = this.mSensorFusion) != null) {
            int[] iArr = new int[4];
            int sensorMatrix = sensorFusion.getSensorMatrix(null, null, null, iArr);
            if (sensorMatrix != 0) {
                Log.e(TAG, String.format(Locale.US, "SensorFusion.getSensorMatrix error ret:0x%08X", Integer.valueOf(sensorMatrix)));
            }
            ArrayList<ArrayList<MorphoSensorFusion.SensorData>> stockData = this.mSensorFusion.getStockData();
            SensorInfoManager sensorInfoManager = new SensorInfoManager(4);
            sensorInfoManager.g_ix = iArr[0];
            sensorInfoManager.r_ix = iArr[3];
            sensorInfoManager.a_ix = iArr[1];
            sensorInfoManager.img_ix = this.mMorphoPanoramaGP3.getAttachCount();
            sensorInfoManager.timeMillis = System.currentTimeMillis();
            sensorInfoManager.imageTimeStamp = captureImage.getTimestamp();
            sensorInfoManager.sensitivity = captureImage.getSensitivity();
            sensorInfoManager.exposureTime = captureImage.getExposureTime();
            sensorInfoManager.rollingShutterSkew = captureImage.getRollingShutterSkew();
            sensorInfoManager.sensorTimeStamp = captureImage.getSensorTimeStamp();
            sensorInfoManager.sensorData[0] = (ArrayList) stockData.get(0).clone();
            sensorInfoManager.sensorData[3] = (ArrayList) stockData.get(3).clone();
            sensorInfoManager.sensorData[1] = (ArrayList) stockData.get(1).clone();
            if (sensorInfoManager.sensorData[0].isEmpty() && (size3 = this.mSensorInfoManagerList.size()) > 0) {
                SensorInfoManager sensorInfoManager2 = this.mSensorInfoManagerList.get(size3 - 1);
                sensorInfoManager.g_ix = sensorInfoManager2.g_ix;
                sensorInfoManager.sensorData[0] = sensorInfoManager2.sensorData[0];
            }
            if (sensorInfoManager.sensorData[3].isEmpty() && (size2 = this.mSensorInfoManagerList.size()) > 0) {
                SensorInfoManager sensorInfoManager3 = this.mSensorInfoManagerList.get(size2 - 1);
                sensorInfoManager.r_ix = sensorInfoManager3.r_ix;
                sensorInfoManager.sensorData[3] = sensorInfoManager3.sensorData[3];
            }
            if (sensorInfoManager.sensorData[1].isEmpty() && (size = this.mSensorInfoManagerList.size()) > 0) {
                SensorInfoManager sensorInfoManager4 = this.mSensorInfoManagerList.get(size - 1);
                sensorInfoManager.a_ix = sensorInfoManager4.a_ix;
                sensorInfoManager.sensorData[1] = sensorInfoManager4.sensorData[1];
            }
            this.mCurrentSensorInfoManager = sensorInfoManager;
            this.mSensorInfoManagerList.add(sensorInfoManager);
            long attachCount = this.mMorphoPanoramaGP3.getAttachCount();
            int size4 = stockData.get(0).size();
            if (size4 > 0 && attachCount > 0 && (gyroscopeData = this.mMorphoPanoramaGP3.setGyroscopeData((MorphoSensorFusion.SensorData[]) stockData.get(0).toArray(new MorphoSensorFusion.SensorData[size4]))) != 0) {
                Log.e(TAG, String.format(Locale.US, "setGyroscopeData error ret:0x%08X", Integer.valueOf(gyroscopeData)));
            }
            this.mSensorFusion.clearStockData();
        }
    }

    private void setupCaptureParams() {
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy == null) {
            Log.e(TAG, "camera device is not ready");
            return;
        }
        camera2Proxy.setFocusMode(this.mTargetFocusMode);
        ((BaseModule) this).mCamera2Device.setZoomRatio(1.0f);
        ((BaseModule) this).mCamera2Device.setFlashMode(0);
        String antiBanding = CameraSettings.getAntiBanding();
        ((BaseModule) this).mCamera2Device.setAntiBanding(Integer.valueOf(antiBanding).intValue());
        String str = TAG;
        Log.d(str, "antiBanding=" + antiBanding);
        ((BaseModule) this).mCamera2Device.setEnableZsl(isZslPreferred());
        ((BaseModule) this).mCamera2Device.setHHT(false);
        ((BaseModule) this).mCamera2Device.setEnableOIS(false);
        ((BaseModule) this).mCamera2Device.setTimeWaterMarkEnable(false);
        ((BaseModule) this).mCamera2Device.setFaceWaterMarkEnable(false);
    }

    /* access modifiers changed from: private */
    public void stopPanoramaShooting(boolean z) {
        stopPanoramaShooting(z, false);
    }

    private void stopPanoramaShooting(boolean z, boolean z2) {
        if (!this.mIsShooting) {
            Log.w(TAG, "stopPanoramaShooting while not shooting");
            return;
        }
        String str = TAG;
        Log.v(str, "stopPanoramaShooting: saveImage=" + z + ", isRelease=" + z2);
        requestStopShoot();
        keepScreenOnAwhile();
        this.mRoundDetector.stop();
        synchronized (this.mDeviceLock) {
            if (((BaseModule) this).mCamera2Device != null) {
                if (z2) {
                    ModeProtocol.PanoramaProtocol panoramaProtocol = (ModeProtocol.PanoramaProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(176);
                    if (panoramaProtocol != null) {
                        Log.d(TAG, "onPause setDisplayPreviewBitmap null");
                        panoramaProtocol.setDisplayPreviewBitmap(null);
                    }
                } else {
                    ((BaseModule) this).mCamera2Device.captureAbortBurst();
                }
                ((BaseModule) this).mCamera2Device.stopPreviewCallback(z2);
            }
        }
        boolean z3 = z && this.mCanSavePanorama;
        this.mShutterEndTime = createDateStringForAppSeg(System.currentTimeMillis());
        this.mSaveOutputImageTask = new SaveOutputImageTask(z3);
        this.mSaveOutputImageTask.execute(new Void[0]);
        onStopShooting(z3);
    }

    private void unRegisterSensorListener() {
        if (this.mIsRegisterSensorListener) {
            this.mIsRegisterSensorListener = false;
            ((BaseModule) this).mActivity.getSensorStateManager().setSensorStateListener(null);
            ((BaseModule) this).mActivity.getSensorStateManager().unregister(186);
        }
    }

    private void updatePictureAndPreviewSize() {
        CameraSize bestPanoPictureSize = getBestPanoPictureSize(((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(256), DataRepository.dataItemFeature().c_0x5a_OR_0());
        ((BaseModule) this).mPreviewSize = Util.getOptimalPreviewSize(false, ((BaseModule) this).mBogusCameraId, ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class), (double) CameraSettings.getPreviewAspectRatio(bestPanoPictureSize.width, bestPanoPictureSize.height));
        ((BaseModule) this).mPictureSize = bestPanoPictureSize;
        CameraSize cameraSize = ((BaseModule) this).mPictureSize;
        this.mPictureWidth = cameraSize.width;
        this.mPictureHeight = cameraSize.height;
        String str = TAG;
        Log.d(str, "pictureSize= " + bestPanoPictureSize.width + "X" + bestPanoPictureSize.height + " previewSize=" + ((BaseModule) this).mPreviewSize.width + "X" + ((BaseModule) this).mPreviewSize.height);
        CameraSize cameraSize2 = ((BaseModule) this).mPreviewSize;
        updateCameraScreenNailSize(cameraSize2.width, cameraSize2.height);
    }

    @Override // com.android.camera.module.Module
    public void closeCamera() {
        Log.d(TAG, "closeCamera: start");
        synchronized (this.mDeviceLock) {
            setCameraState(0);
            if (((BaseModule) this).mCamera2Device != null) {
                ((BaseModule) this).mCamera2Device.setErrorCallback(null);
                ((BaseModule) this).mCamera2Device.stopPreviewCallback(true);
                ((BaseModule) this).mCamera2Device = null;
            }
        }
        Log.d(TAG, "closeCamera: end");
    }

    @Override // com.android.camera.module.BaseModule
    public void consumePreference(@UpdateConstant.UpdateType int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (!(i == 2 || i == 5 || i == 11 || i == 20)) {
                if (i == 24) {
                    applyZoomRatio();
                } else if (i == 30) {
                    continue;
                } else if (i == 32) {
                    setupCaptureParams();
                } else if (!(i == 34 || i == 42)) {
                    if (i == 55) {
                        updateModuleRelated();
                    } else if (i != 66) {
                        switch (i) {
                            case 46:
                            case 47:
                            case 48:
                                continue;
                            default:
                                if (!BaseModule.DEBUG) {
                                    Log.w(TAG, "no consumer for this updateType: " + i);
                                    continue;
                                } else {
                                    throw new RuntimeException("no consumer for this updateType: " + i);
                                }
                        }
                    } else {
                        updateThermalLevel();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public CameraSize getBestPanoPictureSize(List<CameraSize> list, int i) {
        PictureSizeManager.initialize(list, i, ((BaseModule) this).mModuleIndex, ((BaseModule) this).mBogusCameraId);
        return PictureSizeManager.getBestPanoPictureSize();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        return 32776;
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        return isProcessingFinishTask();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isRecording() {
        return this.mIsShooting;
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        ((BaseModule) this).mUnInterruptableReason = null;
        if (this.mIsShooting) {
            ((BaseModule) this).mUnInterruptableReason = "shooting";
        }
        return ((BaseModule) this).mUnInterruptableReason != null;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isZoomEnabled() {
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isZslPreferred() {
        return !b.isMTKPlatform();
    }

    /* access modifiers changed from: protected */
    public void keepScreenOn() {
        ((BaseModule) this).mHandler.removeMessages(17);
        ((BaseModule) this).mHandler.removeMessages(2);
        getWindow().addFlags(128);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onBackPressed() {
        if (!this.mIsShooting) {
            return false;
        }
        if (!isProcessingFinishTask()) {
            playCameraSound(3);
            stopPanoramaShooting(true);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        checkDisplayOrientation();
        updatePreferenceTrampoline(UpdateConstant.PANORAMA_TYPES_INIT);
        startSession();
        ((BaseModule) this).mHandler.sendEmptyMessage(9);
        Log.v(TAG, "SetupCameraThread done");
        this.mViewAngleH = ((BaseModule) this).mCameraCapabilities.getViewAngle(false);
        this.mViewAngleV = ((BaseModule) this).mCameraCapabilities.getViewAngle(true);
        if (b.vu.equals("lavender") && this.mViewAngleH > 50.0f) {
            this.mGoalAngle = 291;
        }
        this.mCameraOrientation = ((BaseModule) this).mCameraCapabilities.getSensorOrientation();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        ((BaseModule) this).mHandler = new MainHandler(this, ((BaseModule) this).mActivity.getMainLooper());
        this.mGoalAngle = DataRepository.dataItemFeature().c_0x01_p_g_a_v_OR_280();
        this.mLongSideCropRatio = DataRepository.dataItemFeature().Db();
        this.mSmallPreviewHeight = (int) ((BaseModule) this).mActivity.getResources().getDimension(R.dimen.pano_preview_hint_frame_height);
        EffectController.getInstance().setEffect(FilterInfo.FILTER_ID_NONE);
        onCameraOpened();
        this.mRoundDetector = new RoundDetector();
        this.mPanoramaSetting = new PanoramaSetting(((BaseModule) this).mActivity.getApplicationContext());
        this.mSensorFusion = new SensorFusion(true);
        this.mSensorFusionMode = 1;
        int mode = this.mSensorFusion.setMode(this.mSensorFusionMode);
        if (mode != 0) {
            Log.e(TAG, String.format(Locale.US, "SensorFusion.setMode error ret:0x%08X", Integer.valueOf(mode)));
        }
        int offsetMode = this.mSensorFusion.setOffsetMode(0);
        if (offsetMode != 0) {
            Log.e(TAG, String.format(Locale.US, "SensorFusion.setOffsetMode error ret:0x%08X", Integer.valueOf(offsetMode)));
        }
        int appState = this.mSensorFusion.setAppState(1);
        if (appState != 0) {
            Log.e(TAG, String.format(Locale.US, "SensorFusion.setAppState error ret:0x%08X", Integer.valueOf(appState)));
        }
        this.mSensorInfoManagerList = new ArrayList<>();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onDestroy() {
        super.onDestroy();
        ((BaseModule) this).mHandler.sendEmptyMessage(45);
        this.mExecutor.shutdown();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onHostStopAndNotifyActionStop() {
        playCameraSound(3);
        stopPanoramaShooting(true, true);
        synchronized (mPreviewImageLock) {
            if (this.mPreviewImage != null) {
                this.mPreviewImage.recycle();
                this.mPreviewImage = null;
            }
            if (this.mDispPreviewImage != null) {
                String str = TAG;
                Log.d(str, "onPause recycle bitmap " + this.mDispPreviewImage);
                this.mDispPreviewImage.recycle();
                this.mDispPreviewImage = null;
            }
        }
        doLaterReleaseIfNeed();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (((BaseModule) this).mPaused) {
            return true;
        }
        boolean z = false;
        if (!isFrameAvailable()) {
            return false;
        }
        if (i == 27 || i == 66) {
            if (keyEvent.getRepeatCount() == 0) {
                if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                    performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                } else if (CameraSettings.isFingerprintCaptureEnable()) {
                    performKeyClicked(30, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                }
                return true;
            }
        } else if (i != 700) {
            if (!(i == 87 || i == 88)) {
                switch (i) {
                    case 23:
                        if (keyEvent.getRepeatCount() == 0) {
                            performKeyClicked(50, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                            return true;
                        }
                        break;
                }
            }
            if (i == 24 || i == 88) {
                z = true;
            }
            if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
                return true;
            }
        } else if (this.mIsShooting) {
            stopPanoramaShooting(true);
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (((BaseModule) this).mPaused) {
            return true;
        }
        if (i != 4) {
            if (i == 27 || i == 66) {
                return true;
            }
        } else if (((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPause() {
        super.onPause();
        unRegisterSensorListener();
        ((BaseModule) this).mHandler.removeCallbacksAndMessages(null);
        closeCamera();
        resetScreenOn();
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewLayoutChanged(Rect rect) {
        ((BaseModule) this).mActivity.onLayoutChange(rect);
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        if (!isTextureExpired() || !retryOnceIfCameraError(((BaseModule) this).mHandler)) {
            ((BaseModule) this).mHandler.sendEmptyMessage(51);
        } else {
            Log.d(TAG, "sessionFailed due to surfaceTexture expired, retry");
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        if (cameraCaptureSession != null && isAlive()) {
            setCameraState(1);
            updatePreferenceInWorkThread(UpdateConstant.PANORAMA_ON_PREVIEW_SUCCESS);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewSizeChanged(int i, int i2) {
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onResume() {
        super.onResume();
        keepScreenOnAwhile();
        registerSensorListener();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewCancelClicked() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
        if (!((BaseModule) this).mPaused && getCameraState() != 0 && !isIgnoreTouchEvent()) {
            if (isFrontCamera() && ((BaseModule) this).mActivity.isScreenSlideOff()) {
                return;
            }
            if (isDoingAction()) {
                Log.w(TAG, "onShutterButtonClick return, isDoingAction");
                return;
            }
            setTriggerMode(i);
            if (!this.mIsShooting) {
                ((BaseModule) this).mActivity.getScreenHint().updateHint();
                if (Storage.isLowStorageAtLastPoint()) {
                    ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFailed();
                    return;
                }
                this.mPanoramaState = new PanoramaInit();
                initAttachQueue();
                playCameraSound(2);
                startPanoramaShooting();
            } else if (isShootingTooShort()) {
                Log.w(TAG, "panorama shooting is too short, ignore this click");
            } else {
                playCameraSound(3);
                stopPanoramaShooting(true, false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean onShutterButtonLongClick() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonLongClickCancel(boolean z) {
        onShutterButtonFocus(false, 2);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onThermalConstrained() {
        super.onThermalConstrained();
        if (!this.mIsShooting) {
            return;
        }
        if (isShootingTooShort()) {
            Log.w(TAG, "panorama shooting is too short, ignore this click");
            return;
        }
        playCameraSound(3);
        stopPanoramaShooting(true, false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    @OnClickAttr
    public void onThumbnailClicked(View view) {
        if (!((BaseModule) this).mPaused && !isProcessingFinishTask() && ((BaseModule) this).mActivity.getThumbnailUpdater().getThumbnail() != null) {
            ((BaseModule) this).mActivity.gotoGallery();
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onUserInteraction() {
        super.onUserInteraction();
        if (!((BaseModule) this).mPaused && !this.mIsShooting) {
            keepScreenOnAwhile();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void openSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(((BaseModule) this).mActivity, CameraPreferenceActivity.class);
        intent.putExtra(BasePreferenceActivity.FROM_WHERE, 166);
        intent.putExtra(a.zf, getResources().getString(R.string.pref_camera_settings_category));
        if (((BaseModule) this).mActivity.startFromKeyguard()) {
            intent.putExtra(a.mf, true);
        }
        ((BaseModule) this).mActivity.startActivity(intent);
        ((BaseModule) this).mActivity.setJumpFlag(2);
        CameraStatUtils.trackGotoSettings(((BaseModule) this).mModuleIndex);
    }

    @Override // com.android.camera.module.Module
    public void pausePreview() {
        Log.v(TAG, "pausePreview");
        ((BaseModule) this).mCamera2Device.pausePreview();
        setCameraState(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (i2 == 0 && z) {
            onShutterButtonClick(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        getActivity().getImplFactory().initAdditional(getActivity(), 174, 164, 212);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void requestRender() {
        ModeProtocol.PanoramaProtocol panoramaProtocol = (ModeProtocol.PanoramaProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(176);
        if (panoramaProtocol != null) {
            panoramaProtocol.requestRender();
        }
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        Log.v(TAG, "resumePreview");
        ((BaseModule) this).mCamera2Device.resumePreview();
        setCameraState(1);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void sendOpenFailMessage() {
        ((BaseModule) this).mHandler.sendEmptyMessage(10);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void setFrameAvailable(boolean z) {
        Camera camera;
        super.setFrameAvailable(z);
        if (z && CameraSettings.isCameraSoundOpen() && (camera = ((BaseModule) this).mActivity) != null) {
            camera.loadCameraSound(2);
            camera.loadCameraSound(3);
        }
    }

    public void setNullDirectionFunction() {
        this.mDirectionFunction = new DirectionFunction(this.mPictureWidth, this.mPictureHeight, 1, 1, 1, 0);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean shouldReleaseLater() {
        return isRecording();
    }

    public void startPanoramaShooting() {
        if (isProcessingFinishTask()) {
            Log.e(TAG, "previous save task is on going");
            return;
        }
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        recordState.onPrepare();
        Log.v(TAG, "startPanoramaShooting");
        this.mCaptureOrientationDecided = false;
        this.mDirection = this.mInitParam.direction;
        this.mTimeTaken = System.currentTimeMillis();
        this.mDeviceOrientationAtCapture = ((BaseModule) this).mOrientationCompensation;
        this.mIsShooting = true;
        this.mCanSavePanorama = false;
        this.mRequestStop = false;
        this.mShutterStartTime = createDateStringForAppSeg(System.currentTimeMillis());
        this.mShutterEndTime = "";
        synchronized (this.mDeviceLock) {
            if (((BaseModule) this).mAeLockSupported) {
                ((BaseModule) this).mCamera2Device.setAELock(true);
            }
            if (((BaseModule) this).mAwbLockSupported && DataRepository.dataItemFeature().c_0x55_OR_T()) {
                ((BaseModule) this).mCamera2Device.setAWBLock(true);
            }
            this.mLocation = LocationManager.instance().getCurrentLocation();
            ((BaseModule) this).mCamera2Device.setGpsLocation(this.mLocation);
            ((BaseModule) this).mCamera2Device.setFocusMode(this.mSnapshotFocusMode);
            ((BaseModule) this).mCamera2Device.setJpegQuality(CameraSettings.getEncodingQuality(false).toInteger(false));
            ((BaseModule) this).mCamera2Device.setJpegThumbnailSize(getJpegThumbnailSize());
            ((BaseModule) this).mCamera2Device.setEnableZsl(isZslPreferred());
            ((BaseModule) this).mCamera2Device.setNeedPausePreview(false);
            ((BaseModule) this).mCamera2Device.setShotType(3);
            ((BaseModule) this).mCamera2Device.captureBurstPictures(-1, new Camera2Proxy.PictureCallbackWrapper() {
                /* class com.android.camera.module.Panorama3Module.AnonymousClass5 */

                @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
                public void onPictureTakenFinished(boolean z) {
                    String access$400 = Panorama3Module.TAG;
                    Log.d(access$400, "onPictureBurstFinished success = " + z);
                }

                @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
                public boolean onPictureTakenImageConsumed(Image image) {
                    String access$400 = Panorama3Module.TAG;
                    Log.v(access$400, "onPictureTaken>>image=" + image);
                    if (((BaseModule) Panorama3Module.this).mCamera2Device == null) {
                        image.close();
                        return true;
                    }
                    if (!Panorama3Module.this.mPanoramaState.onSaveImage(new Camera2Image(image))) {
                        Log.w(Panorama3Module.TAG, "set mPanoramaState PanoramaState");
                        PanoramaState unused = Panorama3Module.this.mPanoramaState = new PanoramaState();
                    }
                    return true;
                }
            }, null);
        }
        keepScreenOnAwhile();
        recordState.onStart();
        this.mPanoramaShootingStartTime = SystemClock.elapsedRealtime();
        keepScreenOn();
        AutoLockManager.getInstance(((BaseModule) this).mActivity).removeMessage();
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        synchronized (this.mDeviceLock) {
            if (((BaseModule) this).mCamera2Device == null) {
                Log.e(TAG, "startPreview: camera has been closed");
                return;
            }
            checkDisplayOrientation();
            ((BaseModule) this).mCamera2Device.setDisplayOrientation(((BaseModule) this).mCameraDisplayOrientation);
            if (((BaseModule) this).mAeLockSupported) {
                ((BaseModule) this).mCamera2Device.setAELock(false);
            }
            if (((BaseModule) this).mAwbLockSupported) {
                ((BaseModule) this).mCamera2Device.setAWBLock(false);
            }
            ((BaseModule) this).mCamera2Device.setFocusMode(this.mTargetFocusMode);
            ((BaseModule) this).mCamera2Device.resumePreview();
            setCameraState(1);
        }
    }

    public void startSession() {
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy == null) {
            Log.e(TAG, "startPreview: camera has been closed");
            return;
        }
        camera2Proxy.setDualCamWaterMarkEnable(false);
        ((BaseModule) this).mCamera2Device.setErrorCallback(((BaseModule) this).mErrorCallback);
        ((BaseModule) this).mCamera2Device.setPreviewSize(((BaseModule) this).mPreviewSize);
        ((BaseModule) this).mCamera2Device.setAlgorithmPreviewSize(((BaseModule) this).mPreviewSize);
        ((BaseModule) this).mCamera2Device.setPictureSize(((BaseModule) this).mPictureSize);
        ((BaseModule) this).mCamera2Device.setPictureMaxImages(3);
        ((BaseModule) this).mCamera2Device.setPictureFormat(35);
        ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
        ((BaseModule) this).mCamera2Device.startPreviewSession(new Surface(((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture()), !b.isMTKPlatform(), false, false, getOperatingMode(), false, this);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void trackModeCustomInfo(Map map, boolean z, BeautyValues beautyValues, int i) {
        CameraStatUtils.trackPictureTakenInPanorama(map, ((BaseModule) this).mActivity, beautyValues, i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null) {
            camera.getImplFactory().detachAdditional();
        }
    }
}
