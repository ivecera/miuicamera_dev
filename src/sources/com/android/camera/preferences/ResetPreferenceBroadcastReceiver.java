package com.android.camera.preferences;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.Util;
import com.android.camera.log.Log;

public class ResetPreferenceBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (Util.ACTION_RESET_CAMERA_PREF.equals(intent.getAction())) {
            Log.d("ResetPreference", "receive ACTION_RESET_CAMERA_PREF action, reset camera settings!");
            CameraPreferenceActivity.resetPreferences(true);
        }
    }
}
