package com.android.camera.snap;

import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.ViewConfiguration;
import com.android.camera.GeneralUtils;
import com.android.camera.ProximitySensorLock;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.snap.SnapCamera;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.storage.Storage;

public class SnapTrigger implements SnapCamera.SnapStatusListener {
    private static final int INTERVAL_DELAY = 200;
    private static final int MAX_BURST_COUNT = 100;
    public static final int MAX_VIDEO_DURATION = 300000;
    public static final String STREET_SNAP_CHANNEL_ID = "com.android.camera.streetsnap";
    /* access modifiers changed from: private */
    public static final String TAG = "SnapTrigger";
    private static final int TRIGGER_KEY = 25;
    private static SnapTrigger sInstance;
    private int mBurstCount = 0;
    /* access modifiers changed from: private */
    public SnapCamera mCamera = null;
    private boolean mCameraOpened;
    private Context mContext;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private final Runnable mLongPressRunnable = new Runnable() {
        /* class com.android.camera.snap.SnapTrigger.AnonymousClass2 */

        public void run() {
            SnapTrigger.this.initCamera();
        }
    };
    /* access modifiers changed from: private */
    public PowerManager mPowerManager = null;
    private ProximitySensorLock mProximityLock;
    private final Runnable mSnapRunnable = new Runnable() {
        /* class com.android.camera.snap.SnapTrigger.AnonymousClass1 */

        public void run() {
            if (SnapTrigger.this.mCamera != null) {
                if (SnapTrigger.this.mPowerManager != null && SnapTrigger.this.mPowerManager.isScreenOn()) {
                    Log.d(SnapTrigger.TAG, "isScreenOn is true, stop take snap");
                    SnapTrigger.this.mHandler.removeMessages(101);
                } else if (SnapTrigger.this.shouldQuitSnap() || Storage.getAvailableSpace() < Storage.LOW_STORAGE_THRESHOLD) {
                } else {
                    if (SnapTrigger.this.mCamera.isCamcorder()) {
                        SnapTrigger.this.shutdownWatchDog();
                        SnapTrigger.this.vibratorShort();
                        SnapTrigger.this.mCamera.startCamcorder();
                        Log.d(SnapTrigger.TAG, "take movie");
                        CameraStatUtils.trackSnapInfo(true);
                        return;
                    }
                    SnapTrigger.this.triggerWatchdog(false);
                    SnapTrigger.this.mCamera.takeSnap();
                    SnapTrigger.access$808(SnapTrigger.this);
                    Log.d(SnapTrigger.TAG, "take snap");
                    CameraStatUtils.trackSnapInfo(false);
                }
            }
        }
    };

    static /* synthetic */ int access$808(SnapTrigger snapTrigger) {
        int i = snapTrigger.mBurstCount;
        snapTrigger.mBurstCount = i + 1;
        return i;
    }

    public static synchronized void destroy() {
        synchronized (SnapTrigger.class) {
            if (sInstance != null) {
                sInstance.onDestroy();
                sInstance.mBurstCount = 0;
                if (sInstance.mCamera != null) {
                    sInstance.mCamera.release();
                    sInstance.mCamera = null;
                }
                sInstance.mHandler = null;
                sInstance.mContext = null;
                sInstance = null;
            }
        }
    }

    public static SnapTrigger getInstance() {
        if (sInstance == null) {
            sInstance = new SnapTrigger();
        }
        return sInstance;
    }

    /* access modifiers changed from: private */
    public void initCamera() {
        Context context;
        if (this.mCamera == null && (context = this.mContext) != null) {
            this.mCameraOpened = false;
            this.mCamera = new SnapCamera(context, this);
        }
    }

    public static void notifyForDetail(Context context, Uri uri, String str, String str2, boolean z) {
        GeneralUtils.notifyForDetail(context, uri, str, str2, z);
    }

    private void onDestroy() {
        ProximitySensorLock proximitySensorLock = this.mProximityLock;
        if (proximitySensorLock != null) {
            proximitySensorLock.destroy();
        }
        this.mProximityLock = null;
    }

    /* access modifiers changed from: private */
    public boolean shouldQuitSnap() {
        if (!ProximitySensorLock.enabled() || !Util.isNonUIEnabled()) {
            ProximitySensorLock proximitySensorLock = this.mProximityLock;
            return proximitySensorLock != null && proximitySensorLock.shouldQuitSnap();
        }
        boolean isNonUI = Util.isNonUI();
        String str = TAG;
        Log.d(str, "shouldQuitSnap isNonUI = " + isNonUI);
        if (isNonUI) {
            CameraStatUtils.trackPocketModeEnter(MistatsConstants.NonUI.POCKET_MODE_NONUI_ENTER_SNAP);
        }
        return isNonUI;
    }

    /* access modifiers changed from: private */
    public void shutdownWatchDog() {
        if (this.mHandler != null) {
            Log.d(TAG, "watch dog Off");
            this.mHandler.removeMessages(101);
        }
    }

    /* access modifiers changed from: private */
    public void triggerWatchdog(boolean z) {
        if (this.mHandler != null) {
            String str = TAG;
            Log.d(str, "watch dog On -" + z);
            this.mHandler.removeMessages(101);
            this.mHandler.sendEmptyMessageDelayed(101, z ? 0 : 5000);
        }
    }

    private void vibrator(long[] jArr) {
        try {
            Log.d(TAG, "call vibrate to notify");
            ((Vibrator) this.mContext.getSystemService("vibrator")).vibrate(VibrationEffect.createWaveform(jArr, -1), new AudioAttributes.Builder().setUsage(4).build());
        } catch (Exception e2) {
            Log.e(TAG, "vibrator exception", e2);
        }
    }

    /* access modifiers changed from: private */
    public void vibratorShort() {
        vibrator(new long[]{10, 20});
    }

    public void handleKeyEvent(int i, int i2, long j) {
        if (isRunning()) {
            boolean z = true;
            if (i == 25) {
                if (i2 == 0) {
                    this.mHandler.postDelayed(this.mLongPressRunnable, ViewConfiguration.getGlobalActionKeyTimeout());
                } else if (i2 == 1) {
                    this.mHandler.removeCallbacks(this.mLongPressRunnable);
                    this.mHandler.removeCallbacks(this.mSnapRunnable);
                    triggerWatchdog(z);
                }
            } else if (i == 26) {
                this.mHandler.removeCallbacks(this.mLongPressRunnable);
                this.mHandler.removeCallbacks(this.mSnapRunnable);
                triggerWatchdog(z);
            }
            z = false;
            triggerWatchdog(z);
        }
    }

    public synchronized boolean init(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        if (ProximitySensorLock.enabled() && !Util.isNonUIEnabled()) {
            this.mProximityLock = new ProximitySensorLock(this.mContext);
            String str = TAG;
            Log.d(str, "init, create a new instance -> " + this.mProximityLock);
            this.mProximityLock.startWatching();
        }
        return isRunning();
    }

    public synchronized boolean isRunning() {
        return (this.mContext == null || this.mHandler == null) ? false : true;
    }

    @Override // com.android.camera.snap.SnapCamera.SnapStatusListener
    public void onCameraOpened() {
        if (!isRunning()) {
            Log.w(TAG, "onCameraOpened: exit");
            return;
        }
        Log.d(TAG, "onCameraOpened");
        this.mCameraOpened = true;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.postDelayed(this.mSnapRunnable, this.mCamera.isCamcorder() ? 100 : 0);
        }
    }

    @Override // com.android.camera.snap.SnapCamera.SnapStatusListener
    public void onDone(Uri uri) {
        if (isRunning()) {
            triggerWatchdog(false);
            vibratorShort();
            if (!this.mCamera.isCamcorder() && this.mBurstCount < 100) {
                this.mHandler.postDelayed(this.mSnapRunnable, 200);
            }
            if (uri != null) {
                Context context = this.mContext;
                notifyForDetail(context, uri, context.getString(R.string.camera_snap_mode_title), this.mContext.getString(R.string.camera_snap_mode_title_detail), this.mCamera.isCamcorder());
            }
        }
    }

    @Override // com.android.camera.snap.SnapCamera.SnapStatusListener
    public void onSkipCapture() {
        if (!isRunning()) {
            Log.w(TAG, "onSkipCapture: exit");
            return;
        }
        Log.d(TAG, "onSkipCapture");
        this.mBurstCount--;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.postDelayed(this.mSnapRunnable, 200);
        }
    }

    public void onThermalConstrained() {
        Log.d(TAG, "onThermalConstrained");
        this.mHandler.removeCallbacks(this.mLongPressRunnable);
        this.mHandler.removeCallbacks(this.mSnapRunnable);
        triggerWatchdog(true);
    }
}
