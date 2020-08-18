package com.android.camera.backup;

import android.content.Intent;
import com.android.camera.log.Log;
import d.a.a.a;
import d.a.a.b;

public class CameraSettingsBackupService extends a {
    /* access modifiers changed from: protected */
    @Override // d.a.a.a
    public b getBackupImpl() {
        return new CameraSettingsBackupImpl();
    }

    /* access modifiers changed from: protected */
    @Override // d.a.a.a
    public void onHandleIntent(Intent intent) {
        try {
            super.onHandleIntent(intent);
        } catch (Exception e2) {
            Log.e("", "exception when onHandleIntent ", e2);
        }
    }
}
