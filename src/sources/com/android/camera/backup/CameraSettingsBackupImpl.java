package com.android.camera.backup;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import d.a.a.b;
import java.util.ArrayList;
import java.util.List;
import miui.cloud.backup.data.DataPackage;
import miui.cloud.backup.data.e;

public class CameraSettingsBackupImpl implements b {
    private static final int FRONT_CLOUD_CAMERA_ID = 1;
    private static final e.a[] PREF_ENTRIES = CameraBackupSettings.PREF_ENTRIES;
    private static final int REAR_CLOUD_CAMERA_ID = 0;
    private static final String TAG = "CameraSettingsBackup";

    interface BackupRestoreHandler {
        void handle(SharedPreferences sharedPreferences, DataPackage dataPackage, e.a[] aVarArr);
    }

    private static e.a[] addPrefixToEntries(e.a[] aVarArr, String str) {
        e.a[] aVarArr2 = new e.a[aVarArr.length];
        for (int i = 0; i < aVarArr.length; i++) {
            e.a aVar = aVarArr[i];
            Class<?> Am = aVar.Am();
            String str2 = str + "::" + aVar.ym();
            String zm = aVar.zm();
            Object defaultValue = aVar.getDefaultValue();
            e.a aVar2 = null;
            if (Am.equals(Integer.class)) {
                aVar2 = defaultValue == null ? e.a.q(str2, zm) : e.a.a(str2, zm, ((Integer) defaultValue).intValue());
            } else if (Am.equals(Boolean.class)) {
                aVar2 = defaultValue == null ? e.a.p(str2, zm) : e.a.b(str2, zm, ((Boolean) defaultValue).booleanValue());
            } else if (Am.equals(String.class)) {
                aVar2 = defaultValue == null ? e.a.s(str2, zm) : e.a.b(str2, zm, (String) defaultValue);
            } else if (Am.equals(Long.class)) {
                aVar2 = defaultValue == null ? e.a.r(str2, zm) : e.a.a(str2, zm, ((Long) defaultValue).longValue());
            }
            aVarArr2[i] = aVar2;
        }
        return aVarArr2;
    }

    private static boolean checkCameraId(int i) {
        if (i < 0) {
            return false;
        }
        if (i < 2) {
            return true;
        }
        throw new IllegalArgumentException("cameraId is invalid: " + i);
    }

    private static List<Integer> getAvailableCameraIds() {
        ArrayList arrayList = new ArrayList();
        if (checkCameraId(0)) {
            arrayList.add(0);
        }
        if (checkCameraId(1)) {
            arrayList.add(1);
        }
        return arrayList;
    }

    private static String getCloudPrefix(int i, int i2) {
        if (checkCameraId(i)) {
            if (i == 0) {
                i = 0;
            } else if (i == 1) {
                i = 1;
            }
        }
        return "camera_settings_simple_mode_local_" + DataItemConfig.provideLocalId(i, i2);
    }

    private static String getCloudPrefixByCameraIdAndMode(int i, int i2) {
        if (checkCameraId(i)) {
            if (i == 0) {
                i = 0;
            } else if (i == 1) {
                i = 1;
            }
        }
        return "camera_settings_simple_mode_local_" + BaseModule.getPreferencesLocalId(i, i2);
    }

    private static String getSharedPreferencesName(int i, int i2) {
        return "camera_settings_simple_mode_local_" + DataItemConfig.provideLocalId(i, i2);
    }

    private void handleBackupOrRestore(Context context, DataPackage dataPackage, BackupRestoreHandler backupRestoreHandler) {
        int[] iArr = {0, 1};
        List<Integer> availableCameraIds = getAvailableCameraIds();
        for (int i : iArr) {
            for (Integer num : availableCameraIds) {
                int intValue = num.intValue();
                SharedPreferences sharedPreferences = context.getSharedPreferences(getSharedPreferencesName(intValue, i), 0);
                if (sharedPreferences != null) {
                    backupRestoreHandler.handle(sharedPreferences, dataPackage, addPrefixToEntries(PREF_ENTRIES, getCloudPrefix(intValue, i)));
                }
            }
        }
        backupRestoreHandler.handle(context.getSharedPreferences("camera_settings_global", 0), dataPackage, addPrefixToEntries(PREF_ENTRIES, "camera_settings_global"));
    }

    private void restoreFromVersion1(Context context, DataPackage dataPackage) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("camera_settings_global", 0);
        int i = 2;
        int[] iArr = {0, 1};
        List<Integer> availableCameraIds = getAvailableCameraIds();
        int length = iArr.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr[i2];
            for (Integer num : availableCameraIds) {
                int intValue = num.intValue();
                SharedPreferences sharedPreferences2 = context.getSharedPreferences(getSharedPreferencesName(intValue, i3), 0);
                if (sharedPreferences2 != null) {
                    e.a[] addPrefixToEntries = addPrefixToEntries(PREF_ENTRIES, getCloudPrefixByCameraIdAndMode(intValue, i3 == 0 ? 0 : i));
                    CameraBackupHelper.restoreSettings(sharedPreferences2, dataPackage, addPrefixToEntries, false);
                    if (i3 == 0 && intValue == 0) {
                        CameraBackupHelper.restoreSettings(sharedPreferences, dataPackage, addPrefixToEntries, true);
                    }
                }
                i = 2;
            }
            i2++;
            i = 2;
        }
        CameraBackupHelper.restoreSettings(sharedPreferences, dataPackage, addPrefixToEntries(PREF_ENTRIES, "camera_settings_global"), true);
    }

    private void restoreFromVersion3(Context context, DataPackage dataPackage) {
        int[] iArr = {0, 1};
        List<Integer> availableCameraIds = getAvailableCameraIds();
        for (int i : iArr) {
            for (Integer num : availableCameraIds) {
                int intValue = num.intValue();
                SharedPreferences sharedPreferences = context.getSharedPreferences(getSharedPreferencesName(intValue, i), 0);
                if (sharedPreferences != null) {
                    CameraBackupHelper.restoreSettings(sharedPreferences, dataPackage, addPrefixToEntries(PREF_ENTRIES, getCloudPrefix(intValue, i)), false);
                }
            }
        }
        CameraBackupHelper.restoreSettings(context.getSharedPreferences("camera_settings_global", 0), dataPackage, addPrefixToEntries(PREF_ENTRIES, "camera_settings_global"), true);
    }

    @Override // d.a.a.b
    public int getCurrentVersion(Context context) {
        return 4;
    }

    @Override // d.a.a.b
    public void onBackupSettings(Context context, DataPackage dataPackage) {
        Log.d(TAG, "backup version " + getCurrentVersion(context));
        handleBackupOrRestore(context, dataPackage, new BackupRestoreHandler() {
            /* class com.android.camera.backup.CameraSettingsBackupImpl.AnonymousClass1 */

            @Override // com.android.camera.backup.CameraSettingsBackupImpl.BackupRestoreHandler
            public void handle(SharedPreferences sharedPreferences, DataPackage dataPackage, e.a[] aVarArr) {
                e.a(sharedPreferences, dataPackage, aVarArr);
            }
        });
    }

    @Override // d.a.a.b
    public void onRestoreSettings(Context context, DataPackage dataPackage, int i) {
        int currentVersion = getCurrentVersion(context);
        if (i > currentVersion) {
            Log.w(TAG, "skip restore due to cloud version " + i + " is higher than local version " + currentVersion);
            return;
        }
        Log.d(TAG, "restore version " + i);
        if (4 <= i) {
            handleBackupOrRestore(context, dataPackage, new BackupRestoreHandler() {
                /* class com.android.camera.backup.CameraSettingsBackupImpl.AnonymousClass2 */

                @Override // com.android.camera.backup.CameraSettingsBackupImpl.BackupRestoreHandler
                public void handle(SharedPreferences sharedPreferences, DataPackage dataPackage, e.a[] aVarArr) {
                    e.b(sharedPreferences, dataPackage, aVarArr);
                }
            });
        } else if (3 == i) {
            restoreFromVersion3(context, dataPackage);
        } else if (1 == i) {
            restoreFromVersion1(context, dataPackage);
        }
    }
}
