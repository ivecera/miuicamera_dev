package com.android.camera;

import android.content.Intent;
import android.util.Log;

public final class ThermalHelper {
    private static final String PACKAGE_NAME = "com.miui.powerkeeper";
    private static final String TAG = "ThermalHelper";

    private ThermalHelper() {
    }

    public static void notifyThermalRecordStart(int i, int i2) {
        Log.d(TAG, "notifyThermalRecordStart: quality = " + i + ", fps = " + i2);
        Intent intent = new Intent();
        intent.setPackage(PACKAGE_NAME);
        intent.setAction("record_start");
        intent.putExtra("quality", i);
        intent.putExtra("fps", i2);
        CameraAppImpl.getAndroidContext().sendBroadcast(intent);
    }

    public static void notifyThermalRecordStop(int i, int i2) {
        Log.d(TAG, "notifyThermalRecordStop: quality = " + i + ", fps = " + i2);
        Intent intent = new Intent();
        intent.setPackage(PACKAGE_NAME);
        intent.setAction("record_end");
        intent.putExtra("quality", i);
        intent.putExtra("fps", i2);
        CameraAppImpl.getAndroidContext().sendBroadcast(intent);
    }
}
