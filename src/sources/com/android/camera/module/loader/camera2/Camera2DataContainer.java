package com.android.camera.module.loader.camera2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.SparseArray;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import java.util.Arrays;
import java.util.Locale;

@SuppressLint({"MissingPermission"})
@TargetApi(21)
public class Camera2DataContainer {
    public static final int BOGUS_CAMERA_ID_BACK = 0;
    public static final int BOGUS_CAMERA_ID_FRONT = 1;
    private static final int INDEX_AUX = 1;
    private static final int INDEX_BOKEH = 3;
    private static final int INDEX_INFRARED = 5;
    private static final int INDEX_MAIN = 0;
    private static final int INDEX_SAT = 2;
    private static final int INDEX_VIRTUAL = 4;
    protected static final int INVALID_CAMERA_ID = -1;
    private static final int MAX_TYPES_OF_CAMERAS_OF_EACH_FACING_DIRECTION = 6;
    public static final int STANDALONE_MACRO_CAMERA_ID = 22;
    private static final int STANDALONE_SAT_CAMERA_ID = 180;
    private static final String TAG = "Camera2DataContainer";
    public static final int TELE_CAMERA_ID = 20;
    public static final int TRIPLE_SAT_CAMERA_ID = 120;
    public static final int ULTRA_TELE_CAMERA_ID = 23;
    public static final int ULTRA_WIDE_BOKEH_CAMERA_ID = 63;
    public static final int ULTRA_WIDE_CAMERA_ID = 21;
    private static final Camera2DataContainer sInstance = new Camera2DataContainer();
    protected volatile SparseArray<CameraCapabilities> mCapabilities = null;
    protected volatile int mCurrentOpenedCameraId = -1;
    private volatile int[] mOrderedCameraIds = null;

    protected Camera2DataContainer() {
    }

    private void dumpCameraIds() {
        int[] iArr = new int[6];
        int[] iArr2 = new int[6];
        for (int i = 0; i < 6; i++) {
            iArr[i] = this.mOrderedCameraIds[i];
            iArr2[i] = this.mOrderedCameraIds[(this.mOrderedCameraIds.length / 2) + i];
        }
        Log.d(TAG, "====================================================================");
        String str = TAG;
        Log.d(str, " BACK: [main, aux, sat, bokeh, virtual, infrared] = " + Arrays.toString(iArr));
        String str2 = TAG;
        Log.d(str2, "FRONT: [main, aux, sat, bokeh, virtual, infrared] = " + Arrays.toString(iArr2));
        Log.d(TAG, "====================================================================");
    }

    public static Camera2DataContainer getInstance() {
        if (b.Bl()) {
            return Camera2RoleContainer.getInstance();
        }
        synchronized (sInstance) {
            if (!sInstance.isInitialized()) {
                sInstance.init((CameraManager) CameraAppImpl.getAndroidContext().getSystemService("camera"));
            }
        }
        return sInstance;
    }

    public static Camera2DataContainer getInstance(CameraManager cameraManager) {
        if (b.Bl()) {
            return Camera2RoleContainer.getInstance(cameraManager);
        }
        synchronized (sInstance) {
            if (!sInstance.isInitialized()) {
                sInstance.init(cameraManager);
            }
        }
        return sInstance;
    }

    private int getVideoModeCameraId(int i, int i2) {
        if (!CameraSettings.isDualCameraSatEnable() || !DataRepository.dataItemFeature().c_28041_0x0006()) {
            return i;
        }
        if (CameraSettings.isVideoQuality8KOpen(i2)) {
            return getMainBackCameraId();
        }
        DataRepository.dataItemConfig().getComponentConfigVideoQuality().getComponentValue(i2);
        return !DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(i2) ? getMainBackCameraId() : getVideoSATCameraId();
    }

    private void init(CameraManager cameraManager) {
        Log.d(TAG, "E: init()");
        try {
            reset();
            String[] cameraIdList = cameraManager.getCameraIdList();
            Log.d(TAG, "All available camera ids: " + Arrays.deepToString(cameraIdList));
            int max = Math.max(6, cameraIdList.length);
            this.mOrderedCameraIds = new int[(max * 2)];
            Arrays.fill(this.mOrderedCameraIds, -1);
            this.mCapabilities = new SparseArray<>(cameraIdList.length);
            int i = max;
            int i2 = 0;
            for (String str : cameraIdList) {
                try {
                    int parseInt = Integer.parseInt(str);
                    CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str);
                    this.mCapabilities.put(parseInt, new CameraCapabilities(cameraCharacteristics, parseInt));
                    if ((!DataRepository.dataItemFeature().isSupportUltraWide() || ((21 != parseInt || b._u) && 63 != parseInt)) && ((!DataRepository.dataItemFeature().isSupportMacroMode() || 22 != parseInt) && (!DataRepository.dataItemFeature().c_22367_0x0001() || 23 != parseInt))) {
                        Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                        if (num == null) {
                            Log.d(TAG, "Unknown facing direction of camera " + parseInt);
                        } else if (num.intValue() == 1) {
                            this.mOrderedCameraIds[i2] = parseInt;
                            i2++;
                        } else if (num.intValue() == 0) {
                            this.mOrderedCameraIds[i] = parseInt;
                            i++;
                        }
                    }
                } catch (NumberFormatException unused) {
                    Log.e(TAG, "non-integer camera id: " + str);
                }
            }
            dumpCameraIds();
        } catch (Exception e2) {
            Log.e(TAG, "Failed to init Camera2DataContainer: " + e2);
            reset();
        }
        Log.d(TAG, "X: init()");
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public synchronized int getActualOpenCameraId(int i, int i2) {
        int i3;
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getActualOpenCameraId(): #init() failed.");
            return i;
        }
        if (i == 0) {
            if (!((!CameraSettings.isDualCameraEnable() || (!CameraSettings.isSupportedOpticalZoom() && !CameraSettings.isSupportedPortrait()) || DataRepository.dataItemGlobal().isForceMainBackCamera()) ? false : true) && i2 != 167) {
                return i;
            }
            if (DataRepository.dataItemGlobal().isNormalIntent() || !this.mCapabilities.get(getMainBackCameraId()).isSupportLightTripartite()) {
                switch (i2) {
                    case 161:
                    case 162:
                    case 169:
                    case 174:
                    case 183:
                        if (!CameraSettings.isMacroModeEnabled(i2)) {
                            if (!CameraSettings.isAutoZoomEnabled(i2)) {
                                if (!CameraSettings.isSuperEISEnabled(i2)) {
                                    if (!CameraSettings.isUltraWideConfigOpen(i2)) {
                                        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                                            float f2 = HybridZoomingSystem.toFloat(HybridZoomingSystem.getZoomRatioHistory(i2, "1.0"), 1.0f);
                                            String str = TAG;
                                            Log.d(str, "Currently user selected zoom ratio is " + f2);
                                            if (f2 >= 1.0f || DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(i2)) {
                                                if (HybridZoomingSystem.IS_4_SAT && f2 >= HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE && !DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(i2)) {
                                                    i3 = getAuxCameraId();
                                                    break;
                                                }
                                            } else {
                                                i3 = getUltraWideCameraId();
                                                break;
                                            }
                                        }
                                        i3 = getVideoModeCameraId(i, i2);
                                        break;
                                    } else {
                                        i3 = getUltraWideCameraId();
                                        break;
                                    }
                                } else if (!DataRepository.dataItemFeature().c_27810_0x0007()) {
                                    i3 = getUltraWideCameraId();
                                    break;
                                } else {
                                    i3 = getMainBackCameraId();
                                    break;
                                }
                            } else {
                                i3 = getUltraWideCameraId();
                                break;
                            }
                        } else if (DataRepository.dataItemFeature().c_0x50() && !b.fv) {
                            i3 = getStandaloneMacroCameraId();
                            break;
                        } else {
                            i3 = getUltraWideCameraId();
                            break;
                        }
                        break;
                    case 163:
                    case 165:
                        if (CameraSettings.isMacroModeEnabled(i2)) {
                            if (!DataRepository.dataItemFeature().c_0x50()) {
                                i3 = getUltraWideCameraId();
                                break;
                            } else {
                                i3 = getStandaloneMacroCameraId();
                                break;
                            }
                        } else {
                            i3 = CameraSettings.isUltraPixelOn() ? getMainBackCameraId() : (!CameraSettings.isDualCameraSatEnable() || !b.isSupportedOpticalZoom()) ? CameraSettings.isUltraWideConfigOpen(i2) ? getUltraWideCameraId() : getMainBackCameraId() : CameraSettings.isUltraWideConfigOpen(i2) ? getUltraWideCameraId() : getSATCameraId();
                            if (!CameraSettings.isSupportedOpticalZoom() && HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isUltraPixelOn()) {
                                float f3 = HybridZoomingSystem.toFloat(HybridZoomingSystem.getZoomRatioHistory(i2, "1.0"), 1.0f);
                                String str2 = TAG;
                                Log.d(str2, "Currently user selected zoom ratio is " + f3);
                                if (f3 < 1.0f) {
                                    i3 = getUltraWideCameraId();
                                    break;
                                }
                            }
                        }
                        break;
                    case 166:
                    case 167:
                    case 180:
                        if (CameraSettings.isZoomByCameraSwitchingSupported()) {
                            String cameraLensType = CameraSettings.getCameraLensType(i2);
                            if (!ComponentManuallyDualLens.LENS_WIDE.equals(cameraLensType)) {
                                if (!ComponentManuallyDualLens.LENS_TELE.equals(cameraLensType)) {
                                    if (!ComponentManuallyDualLens.LENS_ULTRA.equals(cameraLensType)) {
                                        if (!"macro".equals(cameraLensType)) {
                                            if (ComponentManuallyDualLens.LENS_STANDALONE.equals(cameraLensType)) {
                                                i3 = getUltraTeleCameraId();
                                                break;
                                            }
                                        } else {
                                            i3 = getStandaloneMacroCameraId();
                                            break;
                                        }
                                    } else {
                                        i3 = getUltraWideCameraId();
                                        break;
                                    }
                                } else {
                                    i3 = getAuxCameraId();
                                    break;
                                }
                            } else {
                                i3 = getMainBackCameraId();
                                break;
                            }
                        }
                        break;
                    case 171:
                        if (!DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled") || getUltraWideBokehCameraId() == -1) {
                            if (getBokehCameraId() == -1) {
                                i3 = getSATCameraId();
                                break;
                            } else {
                                i3 = getBokehCameraId();
                                break;
                            }
                        } else {
                            i3 = getUltraWideBokehCameraId();
                            break;
                        }
                        break;
                    case 172:
                        if (DataRepository.dataItemFeature().c_19039_0x0002()) {
                            i3 = getUltraWideCameraId();
                            break;
                        }
                        break;
                    case 173:
                        if (CameraSettings.isSuperNightUWOpen(i2)) {
                            if (!CameraSettings.isUltraWideConfigOpen(i2)) {
                                if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                                    float f4 = HybridZoomingSystem.toFloat(HybridZoomingSystem.getZoomRatioHistory(i2, "1.0"), 1.0f);
                                    String str3 = TAG;
                                    Log.d(str3, "Currently user selected zoom ratio is " + f4);
                                    if (f4 >= 1.0f) {
                                        i3 = getMainBackCameraId();
                                        break;
                                    } else {
                                        i3 = getUltraWideCameraId();
                                        break;
                                    }
                                }
                            } else {
                                i3 = getUltraWideCameraId();
                                break;
                            }
                        } else {
                            i3 = getMainBackCameraId();
                            break;
                        }
                        break;
                    case 175:
                    case 182:
                        i3 = getMainBackCameraId();
                        break;
                    case 179:
                        if (!DataRepository.dataItemFeature().yd()) {
                            i3 = getMainBackCameraId();
                            break;
                        } else {
                            i3 = getUltraWideCameraId();
                            break;
                        }
                }
                Log.d(TAG, String.format(Locale.US, "getActualOpenCameraId: mode=%x, id=%d->%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3)));
                return i3;
            }
            return getMainBackCameraId();
        } else if (i == 1) {
            if (i2 == 161 || i2 == 162) {
                if (CameraSettings.isVideoBokehOn() && getBokehFrontCameraId() != -1) {
                    i3 = getBokehFrontCameraId();
                    Log.d(TAG, String.format(Locale.US, "getActualOpenCameraId: mode=%x, id=%d->%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3)));
                    return i3;
                }
            } else if (i2 == 171 && getBokehFrontCameraId() != -1) {
                boolean isIntentAction = DataRepository.dataItemGlobal().isIntentAction();
                if (!b.hl() || isIntentAction) {
                    i3 = getBokehFrontCameraId();
                    Log.d(TAG, String.format(Locale.US, "getActualOpenCameraId: mode=%x, id=%d->%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3)));
                    return i3;
                }
            }
        }
        i3 = i;
        Log.d(TAG, String.format(Locale.US, "getActualOpenCameraId: mode=%x, id=%d->%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3)));
        return i3;
    }

    public synchronized int getAuxCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getAuxCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[1];
    }

    public synchronized int getAuxFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getAuxFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[(this.mOrderedCameraIds.length / 2) + 1];
    }

    public synchronized int getBokehCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getBokehCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[3];
    }

    public synchronized int getBokehFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getBokehFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[(this.mOrderedCameraIds.length / 2) + 3];
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0032, code lost:
        return r0;
     */
    public synchronized CameraCapabilities getCapabilities(int i) {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getCapabilities(): #init() failed.");
            return null;
        }
        CameraCapabilities cameraCapabilities = this.mCapabilities.get(i);
        if (cameraCapabilities == null) {
            String str = TAG;
            Log.d(str, "Warning: getCapabilities(): return null for camera: " + i);
        }
    }

    public synchronized CameraCapabilities getCapabilitiesByBogusCameraId(int i, int i2) {
        return getCapabilities(getActualOpenCameraId(i, i2));
    }

    public synchronized CameraCapabilities getCurrentCameraCapabilities() {
        if (this.mCurrentOpenedCameraId == -1) {
            Log.d(TAG, "Warning: getCurrentCameraCapabilities(): mCurrentOpenedCameraId is invalid.");
        }
        return getCapabilities(this.mCurrentOpenedCameraId);
    }

    public synchronized int getFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[(this.mOrderedCameraIds.length / 2) + 0];
    }

    public synchronized int getMainBackCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getMainBackCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[0];
    }

    public synchronized int getRoleIdByActualId(int i) {
        return i;
    }

    public synchronized int getSATCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getSATCameraId(): #init() failed.");
            return -1;
        } else if (HybridZoomingSystem.IS_4_SAT && CameraSettings.isSupportedOpticalZoom()) {
            return 180;
        } else {
            if (HybridZoomingSystem.IS_3_SAT && CameraSettings.isSupportedOpticalZoom()) {
                return 120;
            }
            return this.mOrderedCameraIds[2];
        }
    }

    public synchronized int getSATFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getSATFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[(this.mOrderedCameraIds.length / 2) + 2];
    }

    public synchronized int getStandaloneMacroCameraId() {
        if (isInitialized()) {
            return 22;
        }
        Log.d(TAG, "Warning: getStandaloneMacroCameraId(): #init() failed.");
        return -1;
    }

    public synchronized int getUltraTeleCameraId() {
        if (isInitialized()) {
            return 23;
        }
        Log.d(TAG, "Warning: getUltraWideCameraId(): #init() failed.");
        return -1;
    }

    public synchronized int getUltraWideBokehCameraId() {
        if (isInitialized()) {
            return 63;
        }
        Log.d(TAG, "Warning: getUltraWideBokehCameraId(): #init() failed.");
        return -1;
    }

    public synchronized int getUltraWideCameraId() {
        if (isInitialized()) {
            return 21;
        }
        Log.d(TAG, "Warning: getUltraWideCameraId(): #init() failed.");
        return -1;
    }

    public synchronized int getVideoSATCameraId() {
        return -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001b, code lost:
        return r1;
     */
    public synchronized boolean hasBokehCamera() {
        boolean z = false;
        if (!isInitialized()) {
            Log.d(TAG, "Warning: hasBokehCamera(): #init() failed.");
            return false;
        } else if (this.mOrderedCameraIds[3] != -1) {
            z = true;
        }
    }

    public boolean hasPortraitCamera() {
        return hasSATCamera();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001b, code lost:
        return r1;
     */
    public synchronized boolean hasSATCamera() {
        boolean z = false;
        if (!isInitialized()) {
            Log.d(TAG, "Warning: hasSATCamera(): #init() failed.");
            return false;
        } else if (this.mOrderedCameraIds[2] != -1) {
            z = true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
        return r0;
     */
    public synchronized boolean isFrontCameraId(int i) {
        CameraCapabilities capabilities = getCapabilities(i);
        boolean z = false;
        if (capabilities == null) {
            Log.d(TAG, "Warning: isFrontCameraId(): #init() failed.");
            return false;
        } else if (capabilities.getFacing() == 0) {
            z = true;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isInitialized() {
        return (this.mCapabilities == null || this.mOrderedCameraIds == null) ? false : true;
    }

    public synchronized void reset() {
        Log.d(TAG, "E: reset()");
        this.mCurrentOpenedCameraId = -1;
        this.mCapabilities = null;
        this.mOrderedCameraIds = null;
        Log.d(TAG, "X: reset()");
    }

    public synchronized void setCurrentOpenedCameraId(int i) {
        this.mCurrentOpenedCameraId = i;
    }
}
