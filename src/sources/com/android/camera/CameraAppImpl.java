package com.android.camera;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.camera.aftersales.AftersalesManager;
import com.android.camera.network.util.NetworkUtils;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.vendortag.CameraCharacteristicsVendorTags;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.struct.MarshalQueryableRegister;
import com.mi.config.b;
import com.miui.filtersdk.BeautificationSDK;
import com.xiaomi.camera.imagecodec.ImagePool;
import miui.external.Application;

public class CameraAppImpl extends Application {
    private static CameraApplicationDelegate sApplicationDelegate;
    private boolean isMimojiNeedUpdate = true;
    private boolean sLaunched = true;

    public static Context getAndroidContext() {
        return CameraApplicationDelegate.getAndroidContext();
    }

    public void addActivity(Activity activity) {
        sApplicationDelegate.addActivity(activity);
    }

    public void closeAllActivitiesBut(Activity activity) {
        sApplicationDelegate.closeAllActivitiesBut(activity);
    }

    public boolean containsResumedCameraInStack() {
        return sApplicationDelegate.containsResumedCameraInStack();
    }

    public Activity getActivity(int i) {
        return sApplicationDelegate.getActivity(i);
    }

    public int getActivityCount() {
        return sApplicationDelegate.getActivityCount();
    }

    public boolean isApplicationFirstLaunched() {
        boolean z = this.sLaunched;
        if (!z) {
            return z;
        }
        this.sLaunched = !z;
        return !this.sLaunched;
    }

    public boolean isMainIntentActivityLaunched() {
        return sApplicationDelegate.isMainIntentActivityLaunched();
    }

    public boolean isMimojiNeedUpdate() {
        if (!this.isMimojiNeedUpdate) {
            return false;
        }
        this.isMimojiNeedUpdate = false;
        return true;
    }

    public boolean isNeedRestore() {
        return sApplicationDelegate.getSettingsFlag();
    }

    @Override // miui.external.Application
    public CameraApplicationDelegate onCreateApplicationDelegate() {
        if (sApplicationDelegate == null) {
            sApplicationDelegate = new CameraApplicationDelegate(this);
        }
        System.setProperty("rx2.purge-period-seconds", "3600");
        AftersalesManager.checkSelf(this);
        boolean z = true;
        if ("avenger".equals(b.buildDevice)) {
            getPackageManager().setApplicationEnabledSetting("com.android.camera", 2, 1);
        }
        MarshalQueryableRegister.preload();
        CameraCharacteristicsVendorTags.preload();
        CaptureRequestVendorTags.preload();
        CaptureResultVendorTags.preload();
        try {
            if (getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getInt("com.xiaomi.camera.parallel.enable", 0) != 1) {
                z = false;
            }
            if (z) {
                ImagePool.init(getResources().getInteger(R.integer.parallel_maxAcquireCount), getResources().getInteger(R.integer.parallel_maxDequeueCount));
                AlgoConnector.getInstance().startService(this);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        CrashHandler.getInstance().init(this);
        NetworkUtils.bind(this);
        BeautificationSDK.init(this);
        return sApplicationDelegate;
    }

    public void removeActivity(Activity activity) {
        sApplicationDelegate.removeActivity(activity);
    }

    public void resetRestoreFlag() {
        sApplicationDelegate.resetRestoreFlag();
    }
}
