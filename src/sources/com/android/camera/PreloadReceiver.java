package com.android.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.camera.aftersales.AftersalesManager;
import com.android.camera.log.Log;
import com.android.camera.module.Camera2Module;
import com.android.camera.module.impl.component.ConfigChangeImpl;
import com.android.camera.module.loader.camera2.Camera2OpenManager;
import com.android.camera.statistic.SettingUploadJobService;

public class PreloadReceiver extends BroadcastReceiver {
    private static final String ACTION_POPUP_FAILED = "miui.intent.action.POPUP_UPDOWN_FAILED";
    private static final String KEY_POPUP_FAILED_TYPE = "updown_failed_type";
    private static final String TAG = "PreloadReceiver";

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            Log.i(TAG, "receive boot complete");
            HybridZoomingSystem.preload();
            new Camera2Module();
            Camera2OpenManager.preload();
            new CameraSettings();
            ConfigChangeImpl.preload();
            SettingUploadJobService.scheduleSettingUploadJob(context);
        } else if (ACTION_POPUP_FAILED.equals(action)) {
            int intExtra = intent.getIntExtra(KEY_POPUP_FAILED_TYPE, -1);
            if (intExtra == 0) {
                AftersalesManager.getInstance().count(System.currentTimeMillis(), 6);
            } else if (1 == intExtra) {
                AftersalesManager.getInstance().count(System.currentTimeMillis(), 10);
            }
        }
    }
}
