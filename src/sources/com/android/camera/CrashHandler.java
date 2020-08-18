package com.android.camera;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.provider.Settings;
import com.android.camera.aftersales.AftersalesManager;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import java.lang.Thread;
import java.lang.ref.WeakReference;
import java.util.Locale;
import miui.external.Application;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CameraFCHandler";
    private static CrashHandler sInstance = new CrashHandler();
    private WeakReference<Application> mContextRef;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    private void resetBrightnessInAutoMode(Context context) {
        DisplayManager displayManager = (DisplayManager) CameraAppImpl.getAndroidContext().getSystemService(DisplayManager.class);
        try {
            if (1 == Settings.System.getInt(context.getContentResolver(), "screen_brightness_mode")) {
                CompatibilityUtils.setTemporaryAutoBrightnessAdjustment(displayManager, 0.0f);
            }
        } catch (Settings.SettingNotFoundException e2) {
            Log.e(TAG, "Meet exception when resetBrightnessInAutoMode(): " + e2);
        } catch (SecurityException e3) {
            Log.e(TAG, "Meet exception when resetBrightnessInAutoMode(): " + e3);
        }
    }

    public void init(Application application) {
        this.mContextRef = new WeakReference<>(application);
        if (this.mDefaultHandler == null) {
            this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        LocationManager.instance().recordLocation(false);
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        Log.e(TAG, String.format(Locale.ENGLISH, "Camera FC, @Module = %d And @CameraId = %d", Integer.valueOf(dataItemGlobal.getCurrentMode()), Integer.valueOf(dataItemGlobal.getCurrentCameraId())));
        AftersalesManager.getInstance().count(System.currentTimeMillis(), 1);
        Log.e(TAG, "Camera FC, msg=" + th.getMessage(), th);
        WeakReference<Application> weakReference = this.mContextRef;
        if (weakReference != null) {
            CameraSettings.setEdgeMode(weakReference.get(), false);
            resetBrightnessInAutoMode(this.mContextRef.get());
            Util.setBrightnessRampRate(-1);
            Util.setScreenEffect(false);
            this.mContextRef = null;
        }
        if (this.mDefaultHandler != null) {
            Log.e(TAG, "mDefaultHandler=" + this.mDefaultHandler);
            this.mDefaultHandler.uncaughtException(thread, th);
            this.mDefaultHandler = null;
        }
    }
}
