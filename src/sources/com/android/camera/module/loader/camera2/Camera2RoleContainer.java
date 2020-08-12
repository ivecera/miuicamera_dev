package com.android.camera.module.loader.camera2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.camera2.CameraManager;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.camera.CameraAppImpl;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.Arrays;
import java.util.Set;

@SuppressLint({"MissingPermission"})
@TargetApi(21)
public class Camera2RoleContainer extends Camera2DataContainer {
    private static final int BOKEH_ROLE_ID = 61;
    private static final int DEPTH_ROLE_ID = 25;
    private static final int FRONT_AUX_ROLE_ID = 40;
    private static final int FRONT_BOKEH_ROLE_ID = 81;
    private static final int FRONT_SAT_ROLE_ID = 80;
    private static final int MACRO_2X_ROLE_ID = 24;
    private static final int MACRO_ROLE_ID = 22;
    private static final int MAIN_BACK_ROLE_ID = 0;
    private static final int MAIN_FRONT_ROLE_ID = 1;
    private static final int PHOTO_SAT_ROLE_ID = 60;
    private static final int PIP_ROLE_ID = 64;
    private static final String TAG = "Camera2RoleContainer";
    private static final int TELE_4X_ROLE_ID = 23;
    private static final int TELE_ROLE_ID = 20;
    private static final int ULTRA_WIDE_BOKEH_ROLE_ID = 63;
    private static final int ULTRA_WIDE_ROLE_ID = 21;
    private static final int VIDEO_SAT_ROLE_ID = 62;
    private static final int VIRTUAL_BACK_ROLE_ID = 100;
    private static final int VIRTUAL_FRONT_ROLE_ID = 101;
    private static final Camera2RoleContainer sInstance = new Camera2RoleContainer();
    private volatile SparseIntArray mCameraRoleIdMap;

    protected Camera2RoleContainer() {
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    private void dumpCameraIds() {
        for (int i = 0; i < this.mCameraRoleIdMap.size(); i++) {
            int keyAt = this.mCameraRoleIdMap.keyAt(i);
            int valueAt = this.mCameraRoleIdMap.valueAt(i);
            Set<String> physicalCameraIds = ((Camera2DataContainer) this).mCapabilities.get(valueAt).getPhysicalCameraIds();
            float viewAngle = ((Camera2DataContainer) this).mCapabilities.get(valueAt).getViewAngle(false);
            if (physicalCameraIds == null || physicalCameraIds.isEmpty()) {
                Log.d(TAG, String.format("role: %3d (%5.1f°) <-> %2d", Integer.valueOf(keyAt), Float.valueOf(viewAngle), Integer.valueOf(valueAt)));
            } else {
                Log.d(TAG, String.format("role: %3d (%5.1f°) <-> %2d = %s", Integer.valueOf(keyAt), Float.valueOf(viewAngle), Integer.valueOf(valueAt), physicalCameraIds));
            }
        }
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public static Camera2RoleContainer getInstance() {
        synchronized (sInstance) {
            if (!sInstance.isInitialized()) {
                sInstance.init((CameraManager) CameraAppImpl.getAndroidContext().getSystemService("camera"));
            }
        }
        return sInstance;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public static Camera2RoleContainer getInstance(CameraManager cameraManager) {
        synchronized (sInstance) {
            if (!sInstance.isInitialized()) {
                sInstance.init(cameraManager);
            }
        }
        return sInstance;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    private void init(CameraManager cameraManager) {
        Log.d(TAG, "E: init()");
        try {
            reset();
            String[] cameraIdList = cameraManager.getCameraIdList();
            Log.d(TAG, "All available camera ids: " + Arrays.deepToString(cameraIdList));
            ((Camera2DataContainer) this).mCapabilities = new SparseArray<>(cameraIdList.length);
            this.mCameraRoleIdMap = new SparseIntArray(cameraIdList.length);
            for (String str : cameraIdList) {
                try {
                    int parseInt = Integer.parseInt(str);
                    CameraCapabilities cameraCapabilities = new CameraCapabilities(cameraManager.getCameraCharacteristics(str), parseInt);
                    ((Camera2DataContainer) this).mCapabilities.put(parseInt, cameraCapabilities);
                    this.mCameraRoleIdMap.put(cameraCapabilities.getCameraRoleId(), parseInt);
                } catch (NumberFormatException unused) {
                    Log.e(TAG, "non-integer camera id: " + str);
                }
            }
            dumpCameraIds();
        } catch (Exception e2) {
            Log.e(TAG, "Failed to init Camera2RoleContainer: " + e2);
            reset();
        }
        Log.d(TAG, "X: init()");
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getAuxCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getAuxCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(20, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getAuxFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getAuxFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(40, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getBokehCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getBokehCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(61, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getBokehFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getBokehFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(81, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(1, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getMainBackCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getMainBackCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(0, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getRoleIdByActualId(int i) {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getRoleIdByActualId(): #init() failed.");
            return -1;
        }
        int indexOfValue = this.mCameraRoleIdMap.indexOfValue(i);
        if (indexOfValue < 0) {
            return -1;
        }
        return this.mCameraRoleIdMap.keyAt(indexOfValue);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getSATCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getSATCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(60, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getSATFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getSATFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(80, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getStandaloneMacroCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getStandaloneMacroCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(22, this.mCameraRoleIdMap.get(24, -1));
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getUltraTeleCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getUltraTeleCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(23, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getUltraWideBokehCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getUltraWideBokehCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(63, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getUltraWideCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getUltraWideCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(21, -1);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized int getVideoSATCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getVideoSATCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(62, -1);
    }

    public synchronized int getVirtualBackCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getVirtualBackCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(100, -1);
    }

    public synchronized int getVirtualFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getVirtualFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mCameraRoleIdMap.get(101, -1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        return r1;
     */
    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized boolean hasBokehCamera() {
        boolean z = false;
        if (!isInitialized()) {
            Log.d(TAG, "Warning: hasBokehCamera(): #init() failed.");
            return false;
        } else if (this.mCameraRoleIdMap.indexOfKey(61) >= 0) {
            z = true;
        }
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public boolean hasPortraitCamera() {
        return hasBokehCamera();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        return r1;
     */
    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized boolean hasSATCamera() {
        boolean z = false;
        if (!isInitialized()) {
            Log.d(TAG, "Warning: hasSATCamera(): #init() failed.");
            return false;
        } else if (this.mCameraRoleIdMap.indexOfKey(60) >= 0) {
            z = true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public boolean isInitialized() {
        return (((Camera2DataContainer) this).mCapabilities == null || this.mCameraRoleIdMap == null) ? false : true;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2DataContainer
    public synchronized void reset() {
        Log.d(TAG, "E: reset()");
        ((Camera2DataContainer) this).mCurrentOpenedCameraId = -1;
        ((Camera2DataContainer) this).mCapabilities = null;
        this.mCameraRoleIdMap = null;
        Log.d(TAG, "X: reset()");
    }
}
