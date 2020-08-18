package com.android.camera;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.mi.config.b;

public class ProximitySensorLock implements SensorEventListener {
    private static final int DELAY_CHECK_TIMEOUT = 300;
    private static final int MSG_DELAY_CHECK = 2;
    private static final int MSG_TIMEOUT = 1;
    private static final int PROXIMITY_THRESHOLD = 3;
    private static final int SHORTCUT_UNLOCK = (getKeyBitmask(4) | getKeyBitmask(24));
    private static final String TAG = "ProximitySensorLock";
    private static final int TIMEOUT = 30000;
    private Context mContext;
    private final boolean mFromVolumeKey;
    /* access modifiers changed from: private */
    public View mHintView;
    private volatile boolean mJudged;
    private int mKeyPressed;
    private int mKeyPressing;
    /* access modifiers changed from: private */
    public Boolean mProximityNear = null;
    private Sensor mProximitySensor;
    /* access modifiers changed from: private */
    public volatile boolean mResumeCalled;
    private Handler mWorkerHandler;
    private HandlerThread mWorkerThread;

    public ProximitySensorLock(Context context) {
        this.mContext = context;
        if ((context instanceof Activity) == 1) {
            Intent intent = ((Activity) context).getIntent();
            int flags = intent.getFlags();
            if (DataRepository.dataItemFeature().c_0x44() == 1) {
                this.mFromVolumeKey = TextUtils.equals(intent.getStringExtra(CameraIntentManager.EXTRA_LAUNCH_SOURCE), "power_double_tap");
            } else {
                this.mFromVolumeKey = (8388608 & flags) == 0 ? true : false;
            }
            Log.d(TAG, "from volume key ->" + this.mFromVolumeKey);
        } else {
            this.mFromVolumeKey = false;
        }
        resetKeyStatus();
        this.mJudged = false;
        this.mWorkerThread = new HandlerThread("Proximity sensor lock");
        this.mWorkerThread.start();
        this.mWorkerHandler = new Handler(this.mWorkerThread.getLooper()) {
            /* class com.android.camera.ProximitySensorLock.AnonymousClass1 */

            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    CameraStatUtils.trackPocketModeExit(MistatsConstants.NonUI.POCKET_MODE_KEYGUARD_EXIT_TIMEOUT);
                    ProximitySensorLock.this.exit();
                } else if (i == 2) {
                    removeMessages(2);
                    if (ProximitySensorLock.this.mProximityNear == null) {
                        Log.d(ProximitySensorLock.TAG, "delay check timeout, callback not returned, take it as far");
                        CameraStatUtils.trackPocketModeSensorDelay();
                        Boolean unused = ProximitySensorLock.this.mProximityNear = false;
                        if (ProximitySensorLock.this.isFromSnap() != 1 && ProximitySensorLock.this.mResumeCalled) {
                            ProximitySensorLock.this.judge();
                        }
                    }
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void doShow() {
        FrameLayout frameLayout;
        if (this.mWorkerHandler != null && !active() && (frameLayout = (FrameLayout) ((Activity) this.mContext).findViewById(16908290)) != null) {
            if (this.mHintView == null) {
                this.mHintView = inflateHint();
            }
            frameLayout.addView(this.mHintView);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mHintView, View.ALPHA, 0.0f, 1.0f);
            ofFloat.setDuration(500L);
            ofFloat.start();
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500);
            alphaAnimation.setRepeatCount(-1);
            alphaAnimation.setRepeatMode(2);
            alphaAnimation.setStartOffset(500);
            this.mHintView.findViewById(R.id.screen_on_proximity_sensor_hint_animation).startAnimation(alphaAnimation);
            resetKeyStatus();
            this.mWorkerHandler.sendEmptyMessageDelayed(1, 30000);
        }
    }

    public static boolean enabled() {
        return supported() ? CameraSettings.isProximityLockOpen() : Util.isNonUIEnabled();
    }

    /* access modifiers changed from: private */
    public void exit() {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        if (!(context instanceof Activity) || !((Activity) context).isFinishing()) {
            Log.d(TAG, "Finish activity, exiting.");
            ((Activity) this.mContext).finish();
        }
    }

    private static int getKeyBitmask(int i) {
        if (i == 3) {
            return 4;
        }
        if (i == 4) {
            return 8;
        }
        if (i == 82 || i == 187) {
            return 2;
        }
        switch (i) {
            case 24:
                return 64;
            case 25:
                return 32;
            case 26:
                return 16;
            default:
                return 1;
        }
    }

    private void hide() {
        resetKeyStatus();
        Handler handler = this.mWorkerHandler;
        if (handler != null) {
            handler.removeMessages(1);
        }
        Context context = this.mContext;
        if (context != null && (context instanceof Activity)) {
            ((Activity) context).runOnUiThread(new Runnable() {
                /* class com.android.camera.ProximitySensorLock.AnonymousClass3 */

                public void run() {
                    ViewGroup viewGroup;
                    if (ProximitySensorLock.this.mHintView != null && (viewGroup = (ViewGroup) ProximitySensorLock.this.mHintView.getParent()) != null) {
                        viewGroup.removeView(ProximitySensorLock.this.mHintView);
                    }
                }
            });
        }
    }

    private View inflateHint() {
        return LayoutInflater.from(this.mContext).inflate(R.layout.screen_on_proximity_sensor_guide, (ViewGroup) null, false);
    }

    private static boolean isEllipticProximity() {
        return Build.VERSION.SDK_INT >= 28 ? SystemProperties.getBoolean("ro.vendor.audio.us.proximity", false) : SystemProperties.getBoolean("ro.audio.us.proximity", false);
    }

    /* access modifiers changed from: private */
    public boolean isFromSnap() {
        return !(this.mContext instanceof Activity) ? true : false;
    }

    /* access modifiers changed from: private */
    public void judge() {
        if (((this.mFromVolumeKey == 1 && this.mProximityNear.booleanValue() == 1) ? true : false) == 1) {
            CameraStatUtils.trackPocketModeEnter(MistatsConstants.NonUI.POCKET_MODE_PSENSOR_ENTER_VOLUME);
            stopWatching();
            exit();
        } else if (this.mProximityNear.booleanValue() == 1) {
            CameraStatUtils.trackPocketModeEnter(MistatsConstants.NonUI.POCKET_MODE_PSENSOR_ENTER_KEYGUARD);
            show();
        } else {
            stopWatching();
        }
        this.mJudged = true;
    }

    private void resetKeyStatus() {
        this.mKeyPressed = 0;
        this.mKeyPressing = 0;
    }

    private boolean shouldBeBlocked(KeyEvent keyEvent) {
        int keyCode;
        if (!(keyEvent == null || active() != 1 || (keyCode = keyEvent.getKeyCode()) == 79 || keyCode == 126 || keyCode == 127)) {
            switch (keyCode) {
                case 85:
                case 86:
                case 87:
                    break;
                default:
                    return true;
            }
        }
        return false;
    }

    private void show() {
        Context context;
        if (enabled() && this.mFromVolumeKey != 1 && this.mWorkerHandler != null && (context = this.mContext) != null && (context instanceof Activity)) {
            ((Activity) context).runOnUiThread(new Runnable() {
                /* class com.android.camera.ProximitySensorLock.AnonymousClass2 */

                public void run() {
                    ProximitySensorLock.this.doShow();
                }
            });
        }
    }

    private void stopWatching() {
        if (this.mProximitySensor != null) {
            Log.d(TAG, "stopWatching proximity sensor " + this.mContext);
            ((SensorManager) this.mContext.getApplicationContext().getSystemService("sensor")).unregisterListener(this);
            this.mProximitySensor = null;
            stopWorkerThread();
        }
    }

    private void stopWorkerThread() {
        HandlerThread handlerThread = this.mWorkerThread;
        if (handlerThread != null) {
            if (Build.VERSION.SDK_INT >= 19) {
                handlerThread.quitSafely();
            } else {
                handlerThread.quit();
            }
            this.mWorkerThread = null;
        }
        this.mWorkerHandler = null;
        this.mJudged = false;
        this.mResumeCalled = false;
    }

    public static boolean supported() {
        return (b.Jl() == 1 && !isEllipticProximity()) ? true : false;
    }

    public boolean active() {
        View view = this.mHintView;
        return (view == null || view.getParent() == null) ? false : true;
    }

    public void destroy() {
        Log.d(TAG, "destroying");
        hide();
        stopWatching();
        stopWorkerThread();
        this.mJudged = false;
        this.mResumeCalled = false;
        this.mContext = null;
    }

    public boolean intercept(KeyEvent keyEvent) {
        boolean z = false;
        if (!enabled() || !active() || !shouldBeBlocked(keyEvent)) {
            return false;
        }
        if (keyEvent.getAction() == 0) {
            z = true;
        }
        int keyBitmask = getKeyBitmask(keyEvent.getKeyCode());
        if (this.mKeyPressing == 0) {
            resetKeyStatus();
        }
        if (z == 1) {
            this.mKeyPressed |= keyBitmask;
            this.mKeyPressing = keyBitmask | this.mKeyPressing;
        } else {
            this.mKeyPressing = (~keyBitmask) & this.mKeyPressing;
        }
        if (this.mKeyPressed == SHORTCUT_UNLOCK) {
            CameraStatUtils.trackPocketModeExit(MistatsConstants.NonUI.POCKET_MODE_KEYGUARD_EXIT_DISMISS);
            hide();
            stopWatching();
        }
        return true;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onResume() {
        Log.d(TAG, "onResume enabled " + enabled() + ", mFromVolumeKey " + this.mFromVolumeKey + ", mProximityNear " + this.mProximityNear);
        if (enabled()) {
            this.mResumeCalled = true;
            if (this.mProximityNear != null) {
                judge();
            }
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        boolean z = true;
        boolean z2 = this.mProximityNear == null ? true : false;
        float[] fArr = sensorEvent.values;
        if (fArr[0] <= 3.0f && fArr[0] != sensorEvent.sensor.getMaximumRange()) {
            z = false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("onSensorChanged near ");
        sb.append(!z);
        sb.append(", values ");
        sb.append(sensorEvent.values[0]);
        sb.append(", max ");
        sb.append(sensorEvent.sensor.getMaximumRange());
        Log.d(TAG, sb.toString());
        this.mProximityNear = Boolean.valueOf(!z);
        Handler handler = this.mWorkerHandler;
        if (handler != null) {
            boolean hasMessages = handler.hasMessages(2);
            this.mWorkerHandler.removeMessages(2);
            if (isFromSnap() || this.mResumeCalled != 1) {
                return;
            }
            if (z2 && hasMessages) {
                judge();
            } else if (this.mFromVolumeKey != 1 && this.mJudged) {
                if (z == 1) {
                    CameraStatUtils.trackPocketModeExit(MistatsConstants.NonUI.POCKET_MODE_KEYGUARD_EXIT_UNLOCK);
                    hide();
                    return;
                }
                CameraStatUtils.trackPocketModeEnter(MistatsConstants.NonUI.POCKET_MODE_PSENSOR_ENTER_KEYGUARD);
                show();
            }
        }
    }

    public boolean shouldQuitSnap() {
        Boolean bool;
        Log.d(TAG, "shouldQuit fromSnap " + isFromSnap() + ", proximity ->" + this.mProximityNear);
        boolean z = (isFromSnap() == 1 && ((bool = this.mProximityNear) == null || bool.booleanValue() == 1)) ? true : false;
        if (z == 1) {
            CameraStatUtils.trackPocketModeEnter(MistatsConstants.NonUI.POCKET_MODE_PSENSOR_ENTER_SNAP);
        }
        return z;
    }

    public void startWatching() {
        if (enabled() && this.mProximitySensor == null) {
            Log.d(TAG, "startWatching proximity sensor " + this.mContext);
            this.mJudged = false;
            this.mResumeCalled = false;
            SensorManager sensorManager = (SensorManager) this.mContext.getApplicationContext().getSystemService("sensor");
            this.mProximitySensor = sensorManager.getDefaultSensor(8);
            sensorManager.registerListener(this, this.mProximitySensor, 0, this.mWorkerHandler);
            this.mWorkerHandler.removeMessages(2);
            this.mWorkerHandler.sendEmptyMessageDelayed(2, 300);
        }
    }
}
