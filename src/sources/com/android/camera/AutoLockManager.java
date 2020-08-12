package com.android.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import com.android.camera.log.Log;
import com.mi.config.b;
import java.util.WeakHashMap;

public class AutoLockManager {
    public static int HIBERNATION_TIMEOUT = 3;
    private static long MILLIS_IN_MINUTE = 60000;
    private static final int MSG_HIBERNATE = 1;
    private static final int MSG_LOCK_SCREEN = 0;
    private static final String TAG = "AutoLockManager";
    private static WeakHashMap<Context, AutoLockManager> sMap = new WeakHashMap<>();
    private boolean mCameraAlwaysKeepScreenOn = false;
    private Context mContext;
    private Handler mHandler;
    private long mHibernationTimeOut;
    private boolean mPaused;
    private long mScreenOffTimeOut = 15000;

    private AutoLockManager(Context context) {
        this.mContext = context;
        int i = SystemProperties.getInt("camera.debug.hibernation_timeout_seconds", 0);
        this.mCameraAlwaysKeepScreenOn = SystemProperties.getBoolean("camera_always_keep_screen_on", false);
        if (i > 0) {
            this.mHibernationTimeOut = ((long) i) * 1000;
        } else {
            this.mHibernationTimeOut = MILLIS_IN_MINUTE * ((long) b.Vk());
        }
        String str = TAG;
        Log.v(str, "mHibernationTimeOut = " + this.mHibernationTimeOut + ", mScreenOffTimeOut = " + this.mScreenOffTimeOut);
        updateScreenOffTimeout(context);
    }

    public static AutoLockManager getInstance(Context context) {
        AutoLockManager autoLockManager = sMap.get(context);
        if (autoLockManager != null) {
            return autoLockManager;
        }
        AutoLockManager autoLockManager2 = new AutoLockManager(context);
        sMap.put(context, autoLockManager2);
        return autoLockManager2;
    }

    /* access modifiers changed from: private */
    public void hibernate() {
        if (!this.mPaused) {
            ((Camera) this.mContext).onHibernate();
        }
    }

    private void initHandler() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("my_handler_thread");
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper()) {
                /* class com.android.camera.AutoLockManager.AnonymousClass1 */

                public void dispatchMessage(Message message) {
                    int i = message.what;
                    if (i == 0) {
                        AutoLockManager.this.lockScreen();
                    } else if (1 == i) {
                        AutoLockManager.this.hibernate();
                    }
                }
            };
        }
    }

    /* access modifiers changed from: private */
    public void lockScreen() {
        if (!this.mPaused) {
            this.mContext.sendBroadcast(new Intent("com.miui.app.ExtraStatusBarManager.TRIGGER_TOGGLE_LOCK"));
        }
    }

    public static void removeInstance(Context context) {
        Handler handler;
        AutoLockManager remove = sMap.remove(context);
        if (remove != null && (handler = remove.mHandler) != null) {
            handler.getLooper().quit();
        }
    }

    private void updateScreenOffTimeout(Context context) {
        try {
            this.mScreenOffTimeOut = (long) Settings.System.getInt(context.getContentResolver(), "screen_off_timeout");
        } catch (Settings.SettingNotFoundException e2) {
            Log.e(TAG, e2.getMessage());
        }
    }

    public void hibernateDelayed() {
        if (!this.mCameraAlwaysKeepScreenOn && !this.mPaused && this.mHibernationTimeOut < this.mScreenOffTimeOut) {
            initHandler();
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.removeMessages(1);
                Camera camera = (Camera) this.mContext;
                if (!camera.isRecording()) {
                    this.mHandler.sendEmptyMessageDelayed(1, this.mHibernationTimeOut);
                    Log.v(TAG, "send MSG_HIBERNATE");
                    return;
                }
                String str = TAG;
                Log.w(str, "isRecording = " + camera.isRecording());
            }
        }
    }

    public void lockScreenDelayed() {
        initHandler();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(0);
        }
        String str = TAG;
        Log.d(str, "lockScreenDelayed: " + this.mScreenOffTimeOut);
        this.mHandler.sendEmptyMessageDelayed(0, this.mScreenOffTimeOut);
    }

    public void onPause() {
        this.mPaused = true;
        removeMessage();
    }

    public void onResume() {
        this.mPaused = false;
        updateScreenOffTimeout(this.mContext);
    }

    public void onUserInteraction() {
        hibernateDelayed();
    }

    public void removeMessage() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            Log.v(TAG, "removeMessage");
        }
    }
}
