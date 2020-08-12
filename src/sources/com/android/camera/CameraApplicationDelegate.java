package com.android.camera;

import android.app.Activity;
import android.content.Context;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.statistic.MistatsWrapper;
import java.util.Stack;
import miui.external.ApplicationDelegate;

public class CameraApplicationDelegate extends ApplicationDelegate {
    private static CameraAppImpl sContext;
    private Stack<Activity> mActivities;
    private boolean mMainIntentActivityLaunched = false;
    private boolean mRestoreSetting = false;

    public CameraApplicationDelegate(CameraAppImpl cameraAppImpl) {
        sContext = cameraAppImpl;
    }

    public static Context getAndroidContext() {
        return sContext;
    }

    public synchronized void addActivity(Activity activity) {
        if (activity != null) {
            if ("android.intent.action.MAIN".equals(activity.getIntent().getAction()) == 1) {
                this.mMainIntentActivityLaunched = true;
            }
            this.mActivities.push(activity);
        }
    }

    public synchronized void closeAllActivitiesBut(Activity activity) {
        int i = 0;
        for (int i2 = 0; i2 < getActivityCount(); i2++) {
            Activity activity2 = getActivity(i);
            if (activity2 == activity || "android.intent.action.MAIN".equals(activity2.getIntent().getAction()) == 1) {
                i++;
            } else {
                activity2.finish();
                this.mActivities.remove(activity2);
            }
        }
    }

    public synchronized boolean containsResumedCameraInStack() {
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            Activity activity = this.mActivities.get(size);
            if ((activity instanceof Camera) && ((Camera) activity).isActivityPaused() != 1) {
                return true;
            }
        }
        return false;
    }

    public synchronized Activity getActivity(int i) {
        if (i >= 0) {
            if (i < getActivityCount()) {
                return this.mActivities.get(i);
            }
        }
        return null;
    }

    public synchronized int getActivityCount() {
        return this.mActivities.size();
    }

    public boolean getSettingsFlag() {
        return this.mRestoreSetting;
    }

    public boolean isMainIntentActivityLaunched() {
        return this.mMainIntentActivityLaunched;
    }

    @Override // miui.external.ApplicationDelegate
    public void onCreate() {
        super.onCreate();
        Util.initialize(this);
        GlobalConstant.sCameraSetupScheduler.scheduleDirect(new Runnable() {
            /* class com.android.camera.CameraApplicationDelegate.AnonymousClass1 */

            public void run() {
                Camera2DataContainer.getInstance();
                DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
                if (!DataRepository.dataItemGlobal().matchCustomWatermarkVersion()) {
                    Util.removeCustomWatermark();
                    dataItemGlobal.editor().remove(DataItemGlobal.DATA_COMMON_CUSTOM_WATERMARK_VERSION).apply();
                }
                if (WatermarkMiSysUtils.isFileExist(Util.getDefaultWatermarkFileName()) == 0) {
                    Util.generateWatermark2File();
                }
                if (DataRepository.dataItemFeature().c_0x46() == 1 && WatermarkMiSysUtils.isFileExist(Util.WATERMARK_FRONT_FILE_NAME) == 0) {
                    Util.generateFrontWatermark2File();
                }
                if (WatermarkMiSysUtils.isFileExist(Util.WATERMARK_ULTRA_PIXEL_FILE_NAME) == 0) {
                    Util.generateUltraPixelWatermark2File();
                }
            }
        });
        MistatsWrapper.initialize(getAndroidContext());
        this.mActivities = new Stack<>();
        this.mRestoreSetting = true;
    }

    public synchronized void removeActivity(Activity activity) {
        if (activity != null) {
            if ("android.intent.action.MAIN".equals(activity.getIntent().getAction()) == 1) {
                this.mMainIntentActivityLaunched = false;
            }
            this.mActivities.remove(activity);
        }
    }

    public void resetRestoreFlag() {
        this.mRestoreSetting = false;
    }
}
