package com.android.camera2;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.Util;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.compat.MiCameraCompat;
import com.android.camera2.vendortag.CameraCharacteristicsVendorTags;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTag;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.SlowMotionVideoConfiguration;
import com.mi.config.b;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@TargetApi(21)
public class CameraCapabilities {
    public static final int CAMERA_ILLEGALSTATE_EXCEPTION = 256;
    private static final boolean DEBUG = false;
    private static final float DEFAULT_VIEW_ANGLE = 51.5f;
    public static final int HAL_PIXEL_FORMAT_BLOB = 33;
    public static final int HAL_PIXEL_FORMAT_IMPLEMENTATION_DEFINED = 34;
    public static final int HAL_PIXEL_FORMAT_YCbCr_420_888 = 35;
    public static final int SESSION_OPERATION_MODE_ALGO_UP_DUAL_BOKEH = 36864;
    public static final int SESSION_OPERATION_MODE_ALGO_UP_HD = 36868;
    public static final int SESSION_OPERATION_MODE_ALGO_UP_MANUAL = 36872;
    public static final int SESSION_OPERATION_MODE_ALGO_UP_MANUAL_G7 = 36870;
    public static final int SESSION_OPERATION_MODE_ALGO_UP_MANUAL_ULTRA_PIXEL = 36871;
    public static final int SESSION_OPERATION_MODE_ALGO_UP_NORMAL = 36869;
    public static final int SESSION_OPERATION_MODE_ALGO_UP_QCFA = 36865;
    public static final int SESSION_OPERATION_MODE_ALGO_UP_SAT = 36866;
    public static final int SESSION_OPERATION_MODE_ALGO_UP_SINGLE_BOKEH = 36867;
    public static final int SESSION_OPERATION_MODE_AUTO_ZOOM = 33012;
    public static final int SESSION_OPERATION_MODE_FOVC = 61456;
    public static final int SESSION_OPERATION_MODE_FRONT_PORTRAIT = 33009;
    public static final int SESSION_OPERATION_MODE_HFR_120 = 32888;
    public static final int SESSION_OPERATION_MODE_HIGH_SPEED = 1;
    public static final int SESSION_OPERATION_MODE_HSR_60 = 32828;
    public static final int SESSION_OPERATION_MODE_MANUAL = 32771;
    public static final int SESSION_OPERATION_MODE_MCTF = 32816;
    public static final int SESSION_OPERATION_MODE_MIMOJI = 32779;
    public static final int SESSION_OPERATION_MODE_MIUI_BACK = 32769;
    public static final int SESSION_OPERATION_MODE_MIUI_FRONT = 32773;
    public static final int SESSION_OPERATION_MODE_MI_LIVE = 32780;
    public static final int SESSION_OPERATION_MODE_NORMAL = 0;
    public static final int SESSION_OPERATION_MODE_NORMAL_ULTRA_PIXEL_PHOTOGRAPHY = 33011;
    public static final int SESSION_OPERATION_MODE_PANORMA = 32776;
    public static final int SESSION_OPERATION_MODE_PORTRAIT = 32770;
    public static final int SESSION_OPERATION_MODE_PROFESSIONAL_ULTRA_PIXEL_PHOTOGRAPHY = 33013;
    public static final int SESSION_OPERATION_MODE_QCFA = 32775;
    public static final int SESSION_OPERATION_MODE_SUPER_NIGHT = 32778;
    public static final int SESSION_OPERATION_MODE_VIDEO = 32772;
    public static final int SESSION_OPERATION_MODE_VIDEO_BEAUTY = 32777;
    public static final int SESSION_OPERATION_MODE_VIDEO_SUPEREIS = 32781;
    public static final int SESSION_OPERATION_MODE_VV = 32780;
    private static final List<VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>>> STREAM_CONFIGURATIONS_VENDOR_KEYS = new ArrayList<VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>>>(3) {
        /* class com.android.camera2.CameraCapabilities.AnonymousClass1 */

        {
            add(CameraCharacteristicsVendorTags.QCFA_STREAM_CONFIGURATIONS);
            add(CameraCharacteristicsVendorTags.SCALER_AVAILABLE_LIMIT_STREAM_CONFIGURATIONS);
            add(CameraCharacteristicsVendorTags.SCALER_AVAILABLE_SR_STREAM_CONFIGURATIONS);
        }
    };
    private static final String TAG = "CameraCapabilities";
    public static final int ULTRA_PIXEL_FRONT_32M_INDEX = 0;
    public static final int ULTRA_PIXEL_REAR_108M_INDEX = 3;
    public static final int ULTRA_PIXEL_REAR_48M_INDEX = 1;
    public static final int ULTRA_PIXEL_REAR_64M_INDEX = 2;
    public static final int XIAOMI_YUV_FORMAT_INVALID = -1;
    public static final int XIAOMI_YUV_FORMAT_NV12 = 1;
    public static final int XIAOMI_YUV_FORMAT_NV21 = 2;
    private final int mCameraId;
    private final HashSet<String> mCaptureRequestVendorKeys;
    private final CameraCharacteristics mCharacteristics;
    private int mOperatingMode;
    private SparseArray<StreamConfigurationMap> mStreamConfigurationMap;

    @Retention(RetentionPolicy.SOURCE)
    public @interface OperatingMode {
    }

    public CameraCapabilities(CameraCharacteristics cameraCharacteristics, int i) {
        if (cameraCharacteristics != null) {
            this.mCharacteristics = cameraCharacteristics;
            this.mCameraId = i;
            if (((Integer) this.mCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2) {
                this.mCaptureRequestVendorKeys = new HashSet<>();
                return;
            }
            ArrayList<CaptureRequest.Key> allVendorKeys = this.mCharacteristics.getNativeCopy().getAllVendorKeys(CaptureRequest.Key.class);
            if (allVendorKeys != null) {
                this.mCaptureRequestVendorKeys = new HashSet<>(allVendorKeys.size());
                for (CaptureRequest.Key key : allVendorKeys) {
                    this.mCaptureRequestVendorKeys.add(key.getName());
                }
                return;
            }
            throw new IllegalArgumentException("Null vendor tag! Need to check it!");
        }
        throw new IllegalArgumentException("Null CameraCharacteristics");
    }

    private void addStreamConfigurationToList(List<StreamConfiguration> list, VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> vendorTag) {
        if (vendorTag == null) {
            Log.w(TAG, "addStreamConfigurationToList: but the key is null!");
            return;
        }
        StreamConfiguration[] streamConfigurationArr = (StreamConfiguration[]) VendorTagHelper.getValueSafely(this.mCharacteristics, vendorTag);
        if (streamConfigurationArr != null) {
            list.addAll(Arrays.asList(streamConfigurationArr));
            String str = TAG;
            Log.d(str, "addStreamConfigurationToList: " + vendorTag.getName() + ": size = " + streamConfigurationArr.length);
            return;
        }
        String str2 = TAG;
        Log.w(str2, "addStreamConfigurationToList: " + vendorTag.getName() + "'s configurations is null!");
    }

    private static boolean contains(int[] iArr, int i) {
        if (iArr == null) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    private List<CameraSize> convertToPictureSize(Size[] sizeArr) {
        ArrayList arrayList = new ArrayList();
        if (sizeArr != null) {
            for (Size size : sizeArr) {
                arrayList.add(new CameraSize(size.getWidth(), size.getHeight()));
            }
        }
        return arrayList;
    }

    public static int getBurstShootCount() {
        return b.getBurstShootCount();
    }

    private List<MiHighSpeedVideoConfiguration> getExtraHighSpeedVideoConfiguration() {
        Integer num;
        int[] iArr;
        if (!isSupportExtraHighSpeedVideoConfiguration() || (num = (Integer) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.EXTRA_HIGH_SPEED_VIDEO_NUMBER)) == null || num.intValue() <= 0 || (iArr = (int[]) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.EXTRA_HIGH_SPEED_VIDEO_CONFIGURATIONS)) == null) {
            return null;
        }
        return MiHighSpeedVideoConfiguration.unmarshal(iArr, num.intValue());
    }

    private SlowMotionVideoConfiguration[] getSlowMotionVideoConfiguration() {
        if (isSupportSlowMotionVideoConfiguration()) {
            return (SlowMotionVideoConfiguration[]) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.SLOW_MOTION_VIDEO_CONFIGURATIONS);
        }
        return null;
    }

    private StreamConfigurationMap getStreamConfigurationMap(int i) {
        if (this.mStreamConfigurationMap == null) {
            this.mStreamConfigurationMap = new SparseArray<>(5);
        }
        if (this.mStreamConfigurationMap.get(i) == null) {
            List<StreamConfiguration> streamConfigurations = getStreamConfigurations(i);
            if (streamConfigurations.size() == 0) {
                this.mStreamConfigurationMap.put(i, (StreamConfigurationMap) this.mCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP));
            } else {
                this.mStreamConfigurationMap.put(i, CompatibilityUtils.createStreamConfigMap(streamConfigurations, this.mCharacteristics));
            }
        }
        return this.mStreamConfigurationMap.get(i);
    }

    private List<StreamConfiguration> getStreamConfigurations(int i) {
        ArrayList arrayList = new ArrayList();
        boolean isTagDefined = isTagDefined(CameraCharacteristicsVendorTags.SCALER_AVAILABLE_LIMIT_STREAM_CONFIGURATIONS.getName());
        boolean isTagDefined2 = isTagDefined(CameraCharacteristicsVendorTags.SCALER_AVAILABLE_SR_STREAM_CONFIGURATIONS.getName());
        boolean isTagDefined3 = isTagDefined(CameraCharacteristicsVendorTags.QCFA_STREAM_CONFIGURATIONS.getName());
        if (isSupportedQcfa() && isTagDefined2 && isUltraPixelPhotographyMode(i)) {
            addStreamConfigurationToList(arrayList, CameraCharacteristicsVendorTags.SCALER_AVAILABLE_SR_STREAM_CONFIGURATIONS);
            return arrayList;
        } else if (!isSupportedQcfa() || !isTagDefined3 || (!isQcfaMode() && ((isTagDefined || isSupportedAndroidScalerStream()) && !isUltraPixelPhotographyMode(i)))) {
            if (isTagDefined) {
                addStreamConfigurationToList(arrayList, CameraCharacteristicsVendorTags.SCALER_AVAILABLE_LIMIT_STREAM_CONFIGURATIONS);
            }
            if (arrayList.size() == 0) {
                addStreamConfigurationToList(arrayList, MiCameraCompat.getDefaultSteamConfigurationsTag());
            }
            return arrayList;
        } else {
            if (i != 36867) {
                addStreamConfigurationToList(arrayList, CameraCharacteristicsVendorTags.QCFA_STREAM_CONFIGURATIONS);
            }
            if (isTagDefined) {
                addStreamConfigurationToList(arrayList, CameraCharacteristicsVendorTags.SCALER_AVAILABLE_LIMIT_STREAM_CONFIGURATIONS);
            } else {
                addStreamConfigurationToList(arrayList, CameraCharacteristicsVendorTags.SCALER_AVAILABLE_STREAM_CONFIGURATIONS);
            }
            return arrayList;
        }
    }

    private boolean isSupportedAndroidScalerStream() {
        return (b.Wu || b.bv) && getFacing() == 1;
    }

    private boolean isUltraPixelPhotographyMode(int i) {
        return i == 33011 || i == 36868 || i == 33013 || i == 36871;
    }

    private void outputSizeDebugLog(Size[] sizeArr) {
    }

    public Rect getActiveArraySize() {
        return (!isSupportedQcfa() || !isTagDefined(CameraCharacteristicsVendorTags.QCFA_ACTIVE_ARRAY_SIZE.getName())) ? (Rect) this.mCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE) : (Rect) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.QCFA_ACTIVE_ARRAY_SIZE);
    }

    public int getAiColorCorrectionVersion() {
        Byte b2 = isTagDefined(CameraCharacteristicsVendorTags.XIAOMI_AI_COLOR_CORRECTION_VERSION.getName()) ? (Byte) VendorTagHelper.getValueSafely(this.mCharacteristics, CameraCharacteristicsVendorTags.XIAOMI_AI_COLOR_CORRECTION_VERSION) : null;
        if (b2 == null) {
            return 0;
        }
        return b2.byteValue();
    }

    public int getBeautyVersion() {
        Byte b2 = isTagDefined(CameraCharacteristicsVendorTags.BEAUTY_VERSION.getName()) ? (Byte) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.BEAUTY_VERSION) : null;
        if (b2 != null) {
            return b2.byteValue();
        }
        return -1;
    }

    public byte[] getCameraCalibrationData() {
        if (isTagDefined(CameraCharacteristicsVendorTags.CAM_CALIBRATION_DATA.getName())) {
            return (byte[]) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.CAM_CALIBRATION_DATA);
        }
        return null;
    }

    public CameraCharacteristics getCameraCharacteristics() {
        return this.mCharacteristics;
    }

    public int getCameraId() {
        return this.mCameraId;
    }

    public int getCameraRoleId() {
        Integer num = isTagDefined(CameraCharacteristicsVendorTags.CAMERA_ROLE_ID.getName()) ? (Integer) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.CAMERA_ROLE_ID) : null;
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public HashSet<String> getCaptureRequestVendorKeys() {
        return this.mCaptureRequestVendorKeys;
    }

    public Range<Integer> getExposureCompensationRange() {
        return (Range) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE);
    }

    public Rational getExposureCompensationRational() {
        return (Rational) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP);
    }

    public float getExposureCompensationStep() {
        Rational rational = (Rational) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP);
        if (rational != null) {
            return rational.floatValue();
        }
        return 1.0f;
    }

    public Range<Long> getExposureTimeRange() {
        return (Range) this.mCharacteristics.get(CameraCharacteristics.SENSOR_INFO_EXPOSURE_TIME_RANGE);
    }

    public int getFacing() {
        Integer num = (Integer) this.mCharacteristics.get(CameraCharacteristics.LENS_FACING);
        if (num != null) {
            return num.intValue();
        }
        return 1;
    }

    public Range<Integer> getIsoRange() {
        Range<Integer> range = (Range) this.mCharacteristics.get(CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE);
        if (range == null) {
            range = new Range<>(0, 0);
        }
        int[] iArr = (int[]) VendorTagHelper.getValueSafely(this.mCharacteristics, CameraCharacteristicsVendorTags.XIAOMI_SENSOR_INFO_SENSITIVITY_RANGE);
        return (iArr == null || iArr.length != 2) ? range : new Range<>(Integer.valueOf(Math.min(iArr[0], range.getLower().intValue())), Integer.valueOf(Math.max(iArr[1], range.getUpper().intValue())));
    }

    public int getMaxExposureCompensation() {
        Range<Integer> exposureCompensationRange = getExposureCompensationRange();
        if (exposureCompensationRange == null) {
            return 0;
        }
        return exposureCompensationRange.getUpper().intValue();
    }

    public int getMaxFaceCount() {
        Integer num = (Integer) this.mCharacteristics.get(CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public int getMaxIso() {
        Range<Integer> isoRange = getIsoRange();
        if (isoRange == null) {
            return 0;
        }
        return isoRange.getUpper().intValue();
    }

    public float getMaxZoomRatio() {
        Float f2 = (Float) this.mCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
        if (f2 != null) {
            return f2.floatValue();
        }
        return 1.0f;
    }

    public float getMiAlgoASDVersion() {
        Float f2;
        if (this.mCaptureRequestVendorKeys == null || (f2 = (Float) VendorTagHelper.getValueQuietly(this.mCharacteristics, CameraCharacteristicsVendorTags.MI_ALGO_ASD_VERSION)) == null) {
            return 0.0f;
        }
        return f2.floatValue();
    }

    public float getMinimumFocusDistance() {
        Float f2 = (Float) this.mCharacteristics.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
        if (f2 != null) {
            return f2.floatValue();
        }
        return 0.0f;
    }

    public int getOperatingMode() {
        return this.mOperatingMode;
    }

    public Set<String> getPhysicalCameraIds() {
        return CompatibilityUtils.getPhysicalCameraIds(this.mCharacteristics);
    }

    public int getScreenLightBrightness() {
        Integer num = isTagDefined(CameraCharacteristicsVendorTags.SCREEN_LIGHT_BRIGHTNESS.getName()) ? (Integer) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.SCREEN_LIGHT_BRIGHTNESS) : null;
        String str = TAG;
        Log.d(str, "Screen light brightness: " + num);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public int getSensorOrientation() {
        Integer num = (Integer) this.mCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public int[] getSupportedAWBModes() {
        return (int[]) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES);
    }

    public int[] getSupportedAntiBandingModes() {
        return (int[]) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES);
    }

    public int[] getSupportedColorEffects() {
        return (int[]) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS);
    }

    public List<MiCustomFpsRange> getSupportedCustomFpsRange() {
        int[] iArr;
        if (!isTagDefined(CameraCharacteristicsVendorTags.CUSTOM_HFR_FPS_TABLE.getName()) || (iArr = (int[]) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.CUSTOM_HFR_FPS_TABLE)) == null) {
            return null;
        }
        return MiCustomFpsRange.unmarshal(iArr);
    }

    public int[] getSupportedFlashModes() {
        return (int[]) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
    }

    public int[] getSupportedFocusModes() {
        return (int[]) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
    }

    public Range<Integer>[] getSupportedFpsRange() {
        return (Range[]) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
    }

    public List<Integer> getSupportedHFRVideoFPSList(Size size) {
        ArrayList arrayList = new ArrayList();
        Range<Integer>[] supportedHighSpeedVideoFPSRange = getSupportedHighSpeedVideoFPSRange(size);
        for (Range<Integer> range : supportedHighSpeedVideoFPSRange) {
            if (range.getUpper().equals(range.getLower()) && !arrayList.contains(range.getUpper())) {
                arrayList.add(range.getUpper());
            }
        }
        return arrayList;
    }

    public int getSupportedHardwareLevel() {
        return ((Integer) this.mCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue();
    }

    public List<CameraSize> getSupportedHeicOutputStreamSizes() {
        StreamConfiguration[] streamConfigurationArr = (StreamConfiguration[]) VendorTagHelper.getValueQuietly(this.mCharacteristics, CameraCharacteristicsVendorTags.XIAOMI_SCALER_HEIC_STREAM_CONFIGURATIONS);
        ArrayList arrayList = new ArrayList();
        if (streamConfigurationArr == null) {
            return arrayList;
        }
        Arrays.stream(streamConfigurationArr).forEach(new a(arrayList));
        return arrayList;
    }

    public Range<Integer>[] getSupportedHighSpeedVideoFPSRange(Size size) {
        List<MiHighSpeedVideoConfiguration> extraHighSpeedVideoConfiguration;
        SlowMotionVideoConfiguration[] slowMotionVideoConfiguration;
        Range<Integer>[] highSpeedVideoFpsRangesFor = ((StreamConfigurationMap) this.mCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getHighSpeedVideoFpsRangesFor(size);
        ArrayList arrayList = new ArrayList();
        if (b.isMTKPlatform()) {
            if (!isSupportSlowMotionVideoConfiguration() || (slowMotionVideoConfiguration = getSlowMotionVideoConfiguration()) == null) {
                return highSpeedVideoFpsRangesFor;
            }
            for (SlowMotionVideoConfiguration slowMotionVideoConfiguration2 : slowMotionVideoConfiguration) {
                if (size != null && size.getWidth() == slowMotionVideoConfiguration2.width && size.getHeight() == slowMotionVideoConfiguration2.height) {
                    arrayList.add(new Range(Integer.valueOf(slowMotionVideoConfiguration2.maxFps), Integer.valueOf(slowMotionVideoConfiguration2.maxFps)));
                }
            }
        } else if (!isSupportExtraHighSpeedVideoConfiguration() || (extraHighSpeedVideoConfiguration = getExtraHighSpeedVideoConfiguration()) == null) {
            return highSpeedVideoFpsRangesFor;
        } else {
            for (MiHighSpeedVideoConfiguration miHighSpeedVideoConfiguration : extraHighSpeedVideoConfiguration) {
                if (size != null && size.equals(miHighSpeedVideoConfiguration.getSize())) {
                    arrayList.add(miHighSpeedVideoConfiguration.getFpsRange());
                }
            }
        }
        if (highSpeedVideoFpsRangesFor != null) {
            Collections.addAll(arrayList, highSpeedVideoFpsRangesFor);
        }
        return (Range[]) arrayList.toArray(new Range[arrayList.size()]);
    }

    public Size[] getSupportedHighSpeedVideoSize() {
        List<MiHighSpeedVideoConfiguration> extraHighSpeedVideoConfiguration;
        SlowMotionVideoConfiguration[] slowMotionVideoConfiguration;
        Size[] highSpeedVideoSizes = ((StreamConfigurationMap) this.mCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getHighSpeedVideoSizes();
        ArrayList arrayList = new ArrayList();
        if (b.isMTKPlatform()) {
            if (!isSupportSlowMotionVideoConfiguration() || (slowMotionVideoConfiguration = getSlowMotionVideoConfiguration()) == null) {
                return highSpeedVideoSizes;
            }
            for (SlowMotionVideoConfiguration slowMotionVideoConfiguration2 : slowMotionVideoConfiguration) {
                Size size = new Size(slowMotionVideoConfiguration2.width, slowMotionVideoConfiguration2.height);
                if (!arrayList.contains(size)) {
                    arrayList.add(size);
                }
            }
        } else if (!isSupportExtraHighSpeedVideoConfiguration() || (extraHighSpeedVideoConfiguration = getExtraHighSpeedVideoConfiguration()) == null) {
            return highSpeedVideoSizes;
        } else {
            for (MiHighSpeedVideoConfiguration miHighSpeedVideoConfiguration : extraHighSpeedVideoConfiguration) {
                Size size2 = miHighSpeedVideoConfiguration.getSize();
                if (!arrayList.contains(size2)) {
                    arrayList.add(size2);
                }
            }
        }
        if (highSpeedVideoSizes != null) {
            for (Size size3 : highSpeedVideoSizes) {
                if (!arrayList.contains(size3)) {
                    arrayList.add(size3);
                }
            }
        }
        return (Size[]) arrayList.toArray(new Size[0]);
    }

    public List<CameraSize> getSupportedOutputSizeWithAssignedMode(int i) {
        StreamConfigurationMap streamConfigurationMap = getStreamConfigurationMap(this.mOperatingMode);
        if (streamConfigurationMap == null) {
            return new ArrayList(0);
        }
        if (!DataRepository.dataItemFeature().Le()) {
            return convertToPictureSize(streamConfigurationMap.getOutputSizes(i));
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(convertToPictureSize(streamConfigurationMap.getHighResolutionOutputSizes(i)));
        arrayList.addAll(convertToPictureSize(streamConfigurationMap.getOutputSizes(i)));
        return arrayList;
    }

    public <T> List<CameraSize> getSupportedOutputSizeWithAssignedMode(Class<T> cls) {
        return getSupportedOutputSizeWithTargetMode(cls, this.mOperatingMode);
    }

    public <T> List<CameraSize> getSupportedOutputSizeWithTargetMode(Class<T> cls, int i) {
        StreamConfigurationMap streamConfigurationMap = getStreamConfigurationMap(i);
        if (streamConfigurationMap == null) {
            return new ArrayList(0);
        }
        Size[] outputSizes = streamConfigurationMap.getOutputSizes(cls);
        outputSizeDebugLog(outputSizes);
        return convertToPictureSize(outputSizes);
    }

    public List<CameraSize> getSupportedOutputStreamSizes(int i) {
        StreamConfiguration[] streamConfigurationArr;
        ArrayList arrayList = new ArrayList();
        for (VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> vendorTag : STREAM_CONFIGURATIONS_VENDOR_KEYS) {
            if (isTagDefined(vendorTag.getName()) && (streamConfigurationArr = (StreamConfiguration[]) VendorTagHelper.getValue(this.mCharacteristics, vendorTag)) != null && streamConfigurationArr.length > 0) {
                for (StreamConfiguration streamConfiguration : streamConfigurationArr) {
                    if (streamConfiguration.getFormat() == i && streamConfiguration.isOutput()) {
                        arrayList.add(new CameraSize(streamConfiguration.getSize()));
                    }
                }
            }
        }
        return arrayList;
    }

    public int[] getSupportedSceneModes() {
        return (int[]) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES);
    }

    public List<CameraSize> getSupportedThumbnailSizes() {
        return convertToPictureSize((Size[]) this.mCharacteristics.get(CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES));
    }

    public float getViewAngle(boolean z) {
        float[] fArr = (float[]) this.mCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
        String str = "vertical";
        if (fArr != null && fArr.length > 0) {
            float f2 = fArr[0];
            SizeF sizeF = (SizeF) this.mCharacteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
            if (sizeF != null) {
                float height = z ? sizeF.getHeight() : sizeF.getWidth();
                if (height > 0.0f) {
                    float degrees = (float) (Math.toDegrees(Math.atan((((double) height) * 0.5d) / ((double) f2))) * 2.0d);
                    String str2 = TAG;
                    Locale locale = Locale.US;
                    Object[] objArr = new Object[4];
                    if (!z) {
                        str = "horizontal";
                    }
                    objArr[0] = str;
                    objArr[1] = Float.valueOf(degrees);
                    objArr[2] = sizeF;
                    objArr[3] = Float.valueOf(f2);
                    Log.d(str2, String.format(locale, "%s view angle: %.2f, size = %s, focalLength = %.4f", objArr));
                    return degrees;
                }
            }
        }
        String str3 = TAG;
        Locale locale2 = Locale.US;
        Object[] objArr2 = new Object[1];
        if (!z) {
            str = "horizontal";
        }
        objArr2[0] = str;
        Log.e(str3, String.format(locale2, "failed to get %s view angle", objArr2));
        return DEFAULT_VIEW_ANGLE;
    }

    public int getXiaomiYuvFormat() {
        Integer num = isTagDefined(CameraCharacteristicsVendorTags.XIAOMI_YUV_FORMAT.getName()) ? (Integer) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.XIAOMI_YUV_FORMAT) : null;
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public boolean hasStandaloneHeicStreamConfigurations() {
        return isTagDefined(CameraCharacteristicsVendorTags.XIAOMI_SCALER_HEIC_STREAM_CONFIGURATIONS.getName());
    }

    @TargetApi(23)
    public boolean isAELockSupported() {
        Boolean bool = (Boolean) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE);
        return bool != null && bool.booleanValue();
    }

    public boolean isAERegionSupported() {
        Integer num = (Integer) this.mCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AE);
        return num != null && num.intValue() > 0;
    }

    public boolean isAFRegionSupported() {
        Float f2 = (Float) this.mCharacteristics.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
        return f2 != null && f2.floatValue() > 0.0f;
    }

    public boolean isASDSceneSupported() {
        return isTagDefined(CaptureRequestVendorTags.AI_SCENE_APPLY.getName());
    }

    @TargetApi(23)
    public boolean isAWBLockSupported() {
        Boolean bool = (Boolean) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AWB_LOCK_AVAILABLE);
        return bool != null && bool.booleanValue();
    }

    public boolean isAdaptiveSnapshotSizeInSatModeSupported() {
        boolean z = false;
        if (this.mCameraId != Camera2DataContainer.getInstance().getSATCameraId()) {
            Log.d(TAG, "isAdaptiveSnapshotSizeInSatModeSupported(): false");
            return false;
        } else if (!isTagDefined(CameraCharacteristicsVendorTags.ADAPTIVE_SNAPSHOT_SIZE_IN_SAT_MODE_SUPPORTED.getName())) {
            Log.d(TAG, "isAdaptiveSnapshotSizeInSatModeSupported(): false");
            return false;
        } else {
            Boolean bool = (Boolean) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.ADAPTIVE_SNAPSHOT_SIZE_IN_SAT_MODE_SUPPORTED);
            if (bool != null && bool.booleanValue()) {
                z = true;
            }
            String str = TAG;
            Log.d(str, "isAdaptiveSnapshotSizeInSatModeSupported(): " + z);
            return z;
        }
    }

    public boolean isAutoFocusSupported() {
        return Util.isSupported(1, getSupportedFocusModes());
    }

    public boolean isBackSoftLightSupported() {
        return isTagDefined(CaptureRequestVendorTags.BACK_SOFT_LIGHT.getName());
    }

    public boolean isBackwardCaptureSupported() {
        return isTagDefined(CaptureRequestVendorTags.BACKWARD_CAPTURE_HINT.getName());
    }

    public boolean isCinematicPhotoSupported() {
        return false;
    }

    public boolean isCinematicVideoSupported() {
        return isTagDefined(CaptureRequestVendorTags.CINEMATIC_VIDEO_ENABLED.getName());
    }

    public boolean isDebugInfoAsWatermarkSupported() {
        return isTagDefined(CaptureRequestVendorTags.DEBUG_INFO_AS_WATERMARK.getName());
    }

    public boolean isEISPreviewSupported() {
        if (!isTagDefined(CameraCharacteristicsVendorTags.EIS_PREVIEW_SUPPORTED.getName())) {
            return false;
        }
        Byte b2 = (Byte) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.EIS_PREVIEW_SUPPORTED);
        String str = TAG;
        Log.d(str, "isEISPreviewSupported: " + b2);
        return b2 != null && b2.byteValue() == 1;
    }

    public boolean isFaceDetectionSupported() {
        int[] iArr = (int[]) this.mCharacteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES);
        if (iArr != null) {
            for (int i : iArr) {
                if (i == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFixedFocus() {
        return getMinimumFocusDistance() > 0.0f;
    }

    public boolean isFlashSupported() {
        Boolean bool = (Boolean) this.mCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        return bool != null && bool.booleanValue();
    }

    public boolean isFovcSupported() {
        Byte b2 = isTagDefined(CameraCharacteristicsVendorTags.FOVC_SUPPORTED.getName()) ? (Byte) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.FOVC_SUPPORTED) : null;
        String str = TAG;
        Log.d(str, "isFovcSupported: " + b2);
        return b2 != null && b2.byteValue() == 1;
    }

    public boolean isHeicSupported() {
        return CompatibilityUtils.isHeicSupported(this.mCharacteristics);
    }

    public boolean isHistogramStatsSupported() {
        return isTagDefined(CaptureRequestVendorTags.HISTOGRAM_STATS_ENABLED.getName());
    }

    public boolean isLLSSupported() {
        return isTagDefined(CaptureResultVendorTags.IS_LLS_NEEDED.getName());
    }

    public boolean isMFNRBokehSupported() {
        Byte b2 = isTagDefined(CameraCharacteristicsVendorTags.MFNR_BOKEH_SUPPORTED.getName()) ? (Byte) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.MFNR_BOKEH_SUPPORTED) : null;
        String str = TAG;
        Log.d(str, "isMFNRBokehSupported: " + b2);
        return b2 != null && b2.byteValue() == 1;
    }

    public boolean isMfnrMacroZoomSupported() {
        Byte b2 = isTagDefined(CameraCharacteristicsVendorTags.MACRO_ZOOM_FEATURE.getName()) ? (Byte) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.MACRO_ZOOM_FEATURE) : null;
        String str = TAG;
        Log.d(str, "isMfnrMacroZoomSupported: " + b2);
        return b2 != null && b2.byteValue() == 1;
    }

    public boolean isMotionDetectionSupported() {
        return isTagDefined(CaptureResultVendorTags.HDR_MOTION_DETECTED.getName());
    }

    public boolean isPartialMetadataSupported() {
        Integer num = (Integer) this.mCharacteristics.get(CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT);
        return num != null && num.intValue() > 1;
    }

    public boolean isQcfaMode() {
        int i = this.mOperatingMode;
        return i == 32775 || i == 36865 || DataRepository.dataItemFeature().r(CameraSettings.isFrontCamera());
    }

    public boolean isRemosaicDetecedSupported() {
        return isTagDefined(CaptureResultVendorTags.REMOSAIC_DETECTED.getName());
    }

    public boolean isScreenLightHintSupported() {
        return isTagDefined(CaptureRequestVendorTags.SCREEN_LIGHT_HINT.getName());
    }

    public boolean isSensorHdrSupported() {
        return isTagDefined(CaptureResultVendorTags.SENSOR_HDR_ENABLE.getName());
    }

    public boolean isSupportAutoHdr() {
        return isTagDefined(CaptureRequestVendorTags.HDR_CHECKER_ENABLE.getName());
    }

    public boolean isSupportBeauty() {
        return isTagDefined(CaptureRequestVendorTags.BEAUTY_LEVEL.getName());
    }

    public boolean isSupportBeautyMakeup() {
        Boolean bool;
        return isTagDefined(CameraCharacteristicsVendorTags.BEAUTY_MAKEUP.getName()) && (bool = (Boolean) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.BEAUTY_MAKEUP)) != null && bool.booleanValue();
    }

    public boolean isSupportBeautyType(String str) {
        if (BeautyConstant.BEAUTY_TYPE_VENDOR_TAG_MAP.containsKey(str)) {
            return isTagDefined(BeautyConstant.BEAUTY_TYPE_VENDOR_TAG_MAP.get(str).getName());
        }
        return false;
    }

    public boolean isSupportBokehAdjust() {
        return isTagDefined(CaptureRequestVendorTags.BOKEH_F_NUMBER.getName());
    }

    public boolean isSupportBurstFps() {
        return isTagDefined(CaptureRequestVendorTags.BURST_SHOOT_FPS.getName());
    }

    public boolean isSupportBurstHint() {
        return isTagDefined(CaptureRequestVendorTags.BURST_CAPTURE_HINT.getName());
    }

    public boolean isSupportCameraAi30() {
        return isTagDefined(CaptureRequestVendorTags.CAMERA_AI_30.getName());
    }

    public boolean isSupportColorRetentionBackRequestTag() {
        return isTagDefined(CaptureRequestVendorTags.VIDEO_FILTER_COLOR_RETENTION_BACK.getName());
    }

    public boolean isSupportColorRetentionFrontRequestTag() {
        return isTagDefined(CaptureRequestVendorTags.VIDEO_FILTER_COLOR_RETENTION_FRONT.getName());
    }

    public boolean isSupportContrast() {
        return isTagDefined(CaptureRequestVendorTags.CONTRAST_LEVEL.getName());
    }

    public boolean isSupportCustomWatermark() {
        return isTagDefined(CaptureRequestVendorTags.CUSTOM_WATERMARK_TEXT.getName());
    }

    public boolean isSupportDepurple() {
        return isTagDefined(CaptureRequestVendorTags.DEPURPLE.getName());
    }

    public boolean isSupportDeviceOrientation() {
        return isTagDefined(CaptureRequestVendorTags.DEVICE_ORIENTATION.getName());
    }

    public boolean isSupportExtraHighSpeedVideoConfiguration() {
        return isTagDefined(CameraCharacteristicsVendorTags.EXTRA_HIGH_SPEED_VIDEO_CONFIGURATIONS.getName()) && isTagDefined(CameraCharacteristicsVendorTags.EXTRA_HIGH_SPEED_VIDEO_NUMBER.getName());
    }

    public boolean isSupportEyeLight() {
        return isTagDefined(CaptureRequestVendorTags.EYE_LIGHT_TYPE.getName());
    }

    public boolean isSupportFaceAgeAnalyze() {
        return isTagDefined(CaptureRequestVendorTags.FACE_AGE_ANALYZE_ENABLED.getName());
    }

    public boolean isSupportFaceScore() {
        return isTagDefined(CaptureRequestVendorTags.FACE_SCORE_ENABLED.getName());
    }

    public boolean isSupportFastZoomIn() {
        return isTagDefined(CaptureResultVendorTags.FAST_ZOOM_RESULT.getName());
    }

    public boolean isSupportFrontMirror() {
        return isTagDefined(CaptureRequestVendorTags.FRONT_MIRROR.getName());
    }

    public boolean isSupportHFRDeflicker() {
        return isTagDefined(CaptureRequestVendorTags.DEFLICKER_ENABLED.getName());
    }

    public boolean isSupportHHT() {
        return isTagDefined(CaptureRequestVendorTags.HHT_ENABLED.getName());
    }

    public boolean isSupportHdr() {
        return isTagDefined(CaptureRequestVendorTags.HDR_ENABLED.getName());
    }

    public boolean isSupportHdrBokeh() {
        return isTagDefined(CaptureRequestVendorTags.HDR_BOKEH_ENABLED.getName());
    }

    public boolean isSupportHdrCheckerStatus() {
        return isTagDefined(CaptureRequestVendorTags.HDR_CHECKER_STATUS.getName());
    }

    public boolean isSupportHdrMode() {
        return isTagDefined(CaptureRequestVendorTags.HDR_MODE.getName());
    }

    public boolean isSupportHistogram() {
        return isTagDefined(CaptureResultVendorTags.HISTOGRAM_STATS.getName());
    }

    public boolean isSupportLensDirtyDetect() {
        return isTagDefined(CaptureRequestVendorTags.LENS_DIRTY_DETECT.getName());
    }

    public boolean isSupportLightTripartite() {
        Boolean bool = false;
        if (isTagDefined(CameraCharacteristicsVendorTags.TRIPARTITE_LIGHT.getName())) {
            bool = (Boolean) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.TRIPARTITE_LIGHT);
        }
        return bool != null && bool.booleanValue();
    }

    public boolean isSupportMacroMode() {
        return isTagDefined(CaptureRequestVendorTags.MACRO_MODE.getName());
    }

    public boolean isSupportMfnr() {
        return isTagDefined(CaptureRequestVendorTags.MFNR_ENABLED.getName());
    }

    public boolean isSupportMiBokeh() {
        return isTagDefined(CaptureRequestVendorTags.FRONT_SINGLE_CAMERA_BOKEH.getName());
    }

    public boolean isSupportNormalWideLDC() {
        if (isTagDefined(CaptureRequestVendorTags.NORMAL_WIDE_LENS_DISTORTION_CORRECTION_LEVEL.getName())) {
            Log.d(TAG, "isSupportNormalWideLDC: true");
            return true;
        }
        Log.d(TAG, "isSupportNormalWideLDC: false");
        return false;
    }

    public boolean isSupportOIS() {
        int[] iArr = (int[]) this.mCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION);
        if (iArr == null || iArr.length == 0) {
            return false;
        }
        return (iArr.length == 1 && iArr[0] == 0) ? false : true;
    }

    public boolean isSupportPallelCameraDevice() {
        if (!isTagDefined(CameraCharacteristicsVendorTags.PARALLEL_CAMERA_DEVICE.getName())) {
            return false;
        }
        Byte b2 = (Byte) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.PARALLEL_CAMERA_DEVICE);
        return b2 != null && b2.byteValue() == 1;
    }

    public boolean isSupportParallel() {
        return isTagDefined(CaptureRequestVendorTags.PARALLEL_ENABLED.getName());
    }

    public boolean isSupportPortraitLighting() {
        return isTagDefined(CaptureRequestVendorTags.PORTRAIT_LIGHTING.getName());
    }

    public boolean isSupportRaw() {
        return contains((int[]) this.mCharacteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES), 3);
    }

    public boolean isSupportRearBokeh() {
        return isTagDefined(CaptureRequestVendorTags.REAR_BOKEH_ENABLE.getName());
    }

    public boolean isSupportSlowMotionVideoConfiguration() {
        return isTagDefined(CameraCharacteristicsVendorTags.SLOW_MOTION_VIDEO_CONFIGURATIONS.getName());
    }

    public boolean isSupportSnapShotTorch() {
        return isTagDefined(CaptureRequestVendorTags.SNAP_SHOT_TORCH.getName());
    }

    public boolean isSupportSuperNight() {
        return isTagDefined(CaptureRequestVendorTags.SUPER_NIGHT_SCENE_ENABLED.getName());
    }

    public boolean isSupportSuperResolution() {
        return isTagDefined(CaptureRequestVendorTags.SUPER_RESOLUTION_ENABLED.getName());
    }

    public boolean isSupportSwMfnr() {
        return isTagDefined(CaptureRequestVendorTags.SW_MFNR_ENABLED.getName());
    }

    public boolean isSupportUltraWideLDC() {
        if (isTagDefined(CaptureRequestVendorTags.ULTRA_WIDE_LENS_DISTORTION_CORRECTION_LEVEL.getName())) {
            Log.d(TAG, "isSupportUltraWideLDC: true");
            return true;
        }
        Log.d(TAG, "isSupportUltraWideLDC: false");
        return false;
    }

    public boolean isSupportVideoBeauty() {
        Boolean bool;
        return isTagDefined(CameraCharacteristicsVendorTags.VIDEO_BEAUTY.getName()) && (bool = (Boolean) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.VIDEO_BEAUTY)) != null && bool.booleanValue();
    }

    public boolean isSupportVideoBokehAdjust() {
        if (CameraSettings.isFrontCamera()) {
            if (isTagDefined(CameraCharacteristicsVendorTags.VIDEO_BOKEH_FRONT_ADJUEST.getName())) {
                Boolean bool = (Boolean) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.VIDEO_BOKEH_FRONT_ADJUEST);
                return bool != null && bool.booleanValue();
            }
        } else if (isTagDefined(CameraCharacteristicsVendorTags.VIDEO_BOKEH_ADJUEST.getName())) {
            Boolean bool2 = (Boolean) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.VIDEO_BOKEH_ADJUEST);
            return bool2 != null && bool2.booleanValue();
        }
        return false;
    }

    public boolean isSupportVideoBokehRequestTag(boolean z) {
        return z ? isTagDefined(CaptureRequestVendorTags.VIDEO_BOKEH_FRONT_LEVEL.getName()) : isTagDefined(CaptureRequestVendorTags.VIDEO_BOKEH_BACK_LEVEL.getName());
    }

    public boolean isSupportVideoFilter() {
        Boolean bool;
        return isTagDefined(CameraCharacteristicsVendorTags.VIDEO_FILTER.getName()) && (bool = (Boolean) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.VIDEO_FILTER)) != null && bool.booleanValue();
    }

    public boolean isSupportVideoFilterColorRetentionBack() {
        Boolean bool;
        return isTagDefined(CameraCharacteristicsVendorTags.VIDEO_COLOR_RENTENTION_BACK.getName()) && (bool = (Boolean) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.VIDEO_COLOR_RENTENTION_BACK)) != null && bool.booleanValue();
    }

    public boolean isSupportVideoFilterColorRetentionFront() {
        Boolean bool;
        return isTagDefined(CameraCharacteristicsVendorTags.VIDEO_COLOR_RENTENTION_FRONT.getName()) && (bool = (Boolean) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.VIDEO_COLOR_RENTENTION_FRONT)) != null && bool.booleanValue();
    }

    public boolean isSupportVideoFilterRequestTag() {
        return isTagDefined(CaptureRequestVendorTags.VIDEO_FILTER_ID.getName());
    }

    public boolean isSupportWatermark() {
        return isTagDefined(CaptureRequestVendorTags.WATERMARK_APPLIEDTYPE.getName());
    }

    public boolean isSupportedColorEnhance() {
        if (isTagDefined(CameraCharacteristicsVendorTags.COLOR_ENHANCE.getName())) {
            return ((Boolean) VendorTagHelper.getValueQuietly(this.mCharacteristics, CameraCharacteristicsVendorTags.COLOR_ENHANCE)).booleanValue();
        }
        return false;
    }

    public boolean isSupportedQcfa() {
        if (!isTagDefined(CameraCharacteristicsVendorTags.IS_QCFA_SENSOR.getName())) {
            return false;
        }
        Byte b2 = (Byte) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.IS_QCFA_SENSOR);
        return b2 != null && b2.byteValue() == 1;
    }

    public boolean isSupportedSuperPortrait() {
        if (isTagDefined(CameraCharacteristicsVendorTags.SUPER_PORTRAIT_SUPPORTED.getName())) {
            return ((Boolean) VendorTagHelper.getValueQuietly(this.mCharacteristics, CameraCharacteristicsVendorTags.SUPER_PORTRAIT_SUPPORTED)).booleanValue();
        }
        return false;
    }

    public boolean isSupportedVideoLogFormat() {
        if (isTagDefined(CameraCharacteristicsVendorTags.LOG_FORMAT.getName())) {
            return ((Boolean) VendorTagHelper.getValueQuietly(this.mCharacteristics, CameraCharacteristicsVendorTags.LOG_FORMAT)).booleanValue();
        }
        return false;
    }

    public boolean isSupportedVideoMiMovie() {
        if (isTagDefined(CameraCharacteristicsVendorTags.VIDEO_MIMOVIE.getName())) {
            return ((Boolean) VendorTagHelper.getValueQuietly(this.mCharacteristics, CameraCharacteristicsVendorTags.VIDEO_MIMOVIE)).booleanValue();
        }
        return false;
    }

    public boolean isTagDefined(String str) {
        HashSet<String> hashSet = this.mCaptureRequestVendorKeys;
        return hashSet != null && hashSet.contains(str);
    }

    public boolean isTeleOISSupported() {
        Byte b2 = isTagDefined(CameraCharacteristicsVendorTags.TELE_OIS_SUPPORTED.getName()) ? (Byte) VendorTagHelper.getValue(this.mCharacteristics, CameraCharacteristicsVendorTags.TELE_OIS_SUPPORTED) : null;
        String str = TAG;
        Log.d(str, "isTeleOISSupported: " + b2);
        return b2 != null && b2.byteValue() == 1;
    }

    public boolean isUltraPixelPhotographySupported(Size size) {
        if (size == null || !isSupportedQcfa()) {
            return false;
        }
        for (CameraSize cameraSize : getSupportedOutputStreamSizes(33)) {
            if (size.getWidth() == cameraSize.getWidth() && size.getHeight() == cameraSize.getHeight()) {
                return true;
            }
        }
        return false;
    }

    public boolean isUltraPixelPortraitTagDefined() {
        return isTagDefined(CaptureRequestVendorTags.ULTRA_PIXEL_PORTRAIT_ENABLED.getName());
    }

    public boolean isVideoStabilizationSupported() {
        int[] iArr = (int[]) this.mCharacteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES);
        return iArr != null && iArr.length > 1;
    }

    public boolean isZoomSupported() {
        return getMaxZoomRatio() > 1.0f;
    }

    public void setOperatingMode(int i) {
        this.mOperatingMode = i;
    }
}
