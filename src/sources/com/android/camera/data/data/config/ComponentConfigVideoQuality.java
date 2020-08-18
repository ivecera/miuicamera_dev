package com.android.camera.data.data.config;

import android.graphics.SurfaceTexture;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.MiCustomFpsRange;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ComponentConfigVideoQuality extends ComponentData {
    public static final String QUALITY_1080P = "6";
    public static final String QUALITY_1080P_60FPS = "6,60";
    public static final String QUALITY_4K = "8";
    public static final String QUALITY_4K_60FPS = "8,60";
    public static final String QUALITY_720P = "5";
    public static final String QUALITY_8K = "3001";
    private static final String TAG = "ComponentConfigVideoQuality";
    private String mDefaultValue = "6";
    private String mForceValue = null;
    private String mForceValueOverlay = null;

    public ComponentConfigVideoQuality(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    public static boolean is8K30FpsCamcorderSupported(int i, CameraCapabilities cameraCapabilities) {
        return (!b.nm() || !cameraCapabilities.getSupportedOutputSizeWithTargetMode(MediaRecorder.class, 32772).contains(new CameraSize(7680, 4320)) || !CamcorderProfile.hasProfile(i, CameraSettings.get8kProfile())) ? false : true;
    }

    private boolean isContain(String str, List<ComponentDataItem> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (ComponentDataItem componentDataItem : list) {
            if (TextUtils.equals(str, componentDataItem.mValue)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSupportFpsRange(int i, int i2, int i3, CameraCapabilities cameraCapabilities) {
        List<MiCustomFpsRange> supportedCustomFpsRange;
        if (i3 == 0 && (supportedCustomFpsRange = cameraCapabilities.getSupportedCustomFpsRange()) != null && !supportedCustomFpsRange.isEmpty()) {
            for (MiCustomFpsRange miCustomFpsRange : supportedCustomFpsRange) {
                if (miCustomFpsRange.getWidth() == i && miCustomFpsRange.getHeight() == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.ComponentData
    public boolean checkValueValid(int i, String str) {
        if (isContain(str, ((ComponentData) this).mItems)) {
            return true;
        }
        String str2 = TAG;
        Log.d(str2, "checkValueValid: invalid value: " + str);
        return false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean disableUpdate() {
        return this.mForceValue != null || this.mForceValueOverlay != null || ((ComponentData) this).mItems == null || ((ComponentData) this).mItems.size() == 1;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return getComponentValue(i, "");
    }

    public String getComponentValue(int i, String str) {
        String str2 = this.mForceValueOverlay;
        if (str2 != null) {
            return str2;
        }
        String str3 = this.mForceValue;
        if (str3 != null) {
            return str3;
        }
        if (!TextUtils.isEmpty(str) && checkValueValid(i, str)) {
            return str;
        }
        String defaultValue = getDefaultValue(i);
        String string = ((ComponentData) this).mParentDataItem.getString(getKey(i), defaultValue);
        if (string == null || string.equals(defaultValue) || checkValueValid(i, string)) {
            return string;
        }
        String simpleName = ComponentConfigVideoQuality.class.getSimpleName();
        Log.e(simpleName, "reset invalid value " + string);
        int indexOf = string.indexOf(",");
        if (indexOf <= 0) {
            return getDefaultValue(i);
        }
        String substring = string.substring(0, indexOf);
        return isContain(substring, ((ComponentData) this).mItems) ? substring : getDefaultValue(i);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        String str = this.mForceValueOverlay;
        if (str != null) {
            return str;
        }
        String str2 = this.mForceValue;
        return str2 != null ? str2 : this.mDefaultValue;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_video_quality_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public List<ComponentDataItem> getItems() {
        if (((ComponentData) this).mItems == null) {
            Log.e(TAG, "List is empty!");
        }
        return ((ComponentData) this).mItems == null ? Collections.emptyList() : ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 161 ? i != 180 ? i != 183 ? "pref_video_quality_key" : CameraSettings.KEY_MI_LIVE_QUALITY : CameraSettings.KEY_CAMERA_PRO_VIDEO_QUALITY : CameraSettings.KEY_CAMERA_FUN_VIDEO_QUALITY;
    }

    public String getNextValue(int i) {
        String persistValue = getPersistValue(i);
        if (((ComponentData) this).mItems != null) {
            int size = ((ComponentData) this).mItems.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (TextUtils.equals(((ComponentData) this).mItems.get(i2).mValue, persistValue)) {
                    return ((ComponentData) this).mItems.get((i2 + 1) % size).mValue;
                }
            }
        }
        return getDefaultValue(i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0061 A[RETURN] */
    @Override // com.android.camera.data.data.ComponentData
    @StringRes
    public int getValueDisplayString(int i) {
        boolean z;
        String str = this.mForceValueOverlay;
        if (str == null) {
            str = this.mForceValue;
        }
        if (str != null) {
            int hashCode = str.hashCode();
            if (hashCode != 53) {
                if (hashCode != 54) {
                    if (hashCode != 56) {
                        if (hashCode == 1567006 && str.equals(QUALITY_8K)) {
                            z = true;
                            if (!z) {
                                return R.string.pref_video_quality_entry_720p;
                            }
                            if (z) {
                                return R.string.pref_video_quality_entry_1080p;
                            }
                            if (z) {
                                return R.string.pref_video_quality_entry_4kuhd;
                            }
                            if (!z) {
                                return -1;
                            }
                            return R.string.pref_video_quality_entry_8kuhd;
                        }
                    } else if (str.equals("8")) {
                        z = true;
                        if (!z) {
                        }
                    }
                } else if (str.equals("6")) {
                    z = true;
                    if (!z) {
                    }
                }
            } else if (str.equals("5")) {
                z = false;
                if (!z) {
                }
            }
            z = true;
            if (!z) {
            }
        } else {
            String componentValue = getComponentValue(i);
            for (ComponentDataItem componentDataItem : getItems()) {
                if (componentDataItem.mValue.equals(componentValue)) {
                    return componentDataItem.mDisplayNameRes;
                }
            }
        }
        return -1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0061 A[RETURN] */
    @Override // com.android.camera.data.data.ComponentData
    @DrawableRes
    public int getValueSelectedDrawable(int i) {
        boolean z;
        String str = this.mForceValueOverlay;
        if (str == null) {
            str = this.mForceValue;
        }
        if (str != null) {
            int hashCode = str.hashCode();
            if (hashCode != 53) {
                if (hashCode != 54) {
                    if (hashCode != 56) {
                        if (hashCode == 1567006 && str.equals(QUALITY_8K)) {
                            z = true;
                            if (!z) {
                                return R.drawable.ic_config_720p_30_disable;
                            }
                            if (z) {
                                return R.drawable.ic_config_1080p_30_disable;
                            }
                            if (z) {
                                return R.drawable.ic_config_4k_30_disable;
                            }
                            if (!z) {
                                return -1;
                            }
                            return R.drawable.ic_config_8k_30_disable;
                        }
                    } else if (str.equals("8")) {
                        z = true;
                        if (!z) {
                        }
                    }
                } else if (str.equals("6")) {
                    z = true;
                    if (!z) {
                    }
                }
            } else if (str.equals("5")) {
                z = false;
                if (!z) {
                }
            }
            z = true;
            if (!z) {
            }
        } else {
            String componentValue = getComponentValue(i);
            for (ComponentDataItem componentDataItem : getItems()) {
                if (componentDataItem.mValue.equals(componentValue)) {
                    return componentDataItem.mIconSelectedRes;
                }
            }
        }
        return -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0029, code lost:
        if (r21 != 183) goto L_0x013c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0142  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x016b  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x018d  */
    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities, int i3) {
        ArrayList arrayList = new ArrayList();
        if (i != 161) {
            if (i == 162 || i == 169 || i == 180) {
                List<CameraSize> supportedOutputSizeWithTargetMode = cameraCapabilities.getSupportedOutputSizeWithTargetMode(MediaRecorder.class, 32772);
                if (supportedOutputSizeWithTargetMode.contains(new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH)) && CamcorderProfile.hasProfile(i2, 5)) {
                    arrayList.add(new ComponentDataItem(R.drawable.ic_config_720p_30, R.drawable.ic_config_720p_30, R.drawable.ic_config_720p_30_disable, R.string.pref_video_quality_entry_720p, "5"));
                }
                if (supportedOutputSizeWithTargetMode.contains(new CameraSize(1920, 1080)) && CamcorderProfile.hasProfile(i2, 6)) {
                    arrayList.add(new ComponentDataItem(R.drawable.ic_config_1080p_30, R.drawable.ic_config_1080p_30, R.drawable.ic_config_1080p_30_disable, R.string.pref_video_quality_entry_1080p, "6"));
                    if (i != 169 && isSupportFpsRange(1920, 1080, i3, cameraCapabilities)) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_1080p_60, (int) R.drawable.ic_config_1080p_60, (int) R.string.pref_video_quality_entry_1080p_60fps, QUALITY_1080P_60FPS));
                    }
                }
                int i4 = CameraSettings.get4kProfile();
                if (b.nm() && supportedOutputSizeWithTargetMode.contains(new CameraSize(3840, 2160)) && CamcorderProfile.hasProfile(i2, i4)) {
                    arrayList.add(new ComponentDataItem(R.drawable.ic_config_4k_30, R.drawable.ic_config_4k_30, R.drawable.ic_config_4k_30_disable, R.string.pref_video_quality_entry_4kuhd, "8"));
                    if (i != 169 && isSupportFpsRange(3840, 2160, i3, cameraCapabilities)) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4k_60, (int) R.drawable.ic_config_4k_60, (int) R.string.pref_video_quality_entry_4kuhd_60fps, QUALITY_4K_60FPS));
                    }
                }
                if (this.mForceValueOverlay != null) {
                    Log.d(TAG, String.format(Locale.US, "The video quality of current mode (%s) is forcely set to '%s', is it what you expected?", Integer.valueOf(i), this.mForceValueOverlay));
                }
                this.mForceValue = null;
                this.mDefaultValue = null;
                if (arrayList.size() != 1) {
                    if (arrayList.get(0).mValue.equals("5")) {
                        this.mForceValue = "5";
                    } else if (arrayList.get(0).mValue.equals("6")) {
                        this.mForceValue = "6";
                    }
                } else if (i != 161) {
                    if (CameraSettings.isAutoZoomEnabled(i)) {
                        this.mForceValue = "6";
                    }
                    if (CameraSettings.isSuperEISEnabled(i)) {
                        this.mForceValue = "6";
                    }
                    if (CameraSettings.isFaceBeautyOn(i, null)) {
                        this.mForceValue = "5";
                    }
                    if (CameraSettings.isMiLiveBeautyOpen()) {
                        this.mForceValue = "5";
                    }
                    if (CameraSettings.isVideoBokehOn()) {
                        this.mForceValue = "5";
                    }
                    if (CameraSettings.isProVideoLogOpen(i)) {
                        this.mForceValue = "8";
                    }
                    if (CameraSettings.isVideoQuality8KOpen(i)) {
                        this.mForceValue = QUALITY_8K;
                    }
                }
                if (161 != i || 183 == i) {
                    this.mDefaultValue = "6";
                } else if (i2 == 1) {
                    this.mDefaultValue = "6";
                } else if (i2 == 0) {
                    String string = CameraSettings.getString(CameraSettings.getDefaultPreferenceId(R.string.pref_video_quality_default));
                    if (!isContain(string, arrayList)) {
                        int size = arrayList.size();
                        if (size > 0) {
                            this.mDefaultValue = arrayList.get(size - 1).mValue;
                        }
                    } else {
                        this.mDefaultValue = string;
                    }
                }
                ((ComponentData) this).mItems = Collections.unmodifiableList(arrayList);
            }
        }
        List<CameraSize> supportedOutputSizeWithAssignedMode = cameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        if (supportedOutputSizeWithAssignedMode.contains(new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH))) {
            arrayList.add(new ComponentDataItem(R.drawable.ic_config_720p_30, R.drawable.ic_config_720p_30, R.drawable.ic_config_720p_30_disable, R.string.pref_video_quality_entry_720p, "5"));
        }
        if (supportedOutputSizeWithAssignedMode.contains(new CameraSize(1920, 1080))) {
            arrayList.add(new ComponentDataItem(R.drawable.ic_config_1080p_30, R.drawable.ic_config_1080p_30, R.drawable.ic_config_1080p_30_disable, R.string.pref_video_quality_entry_1080p, "6"));
        }
        if (this.mForceValueOverlay != null) {
        }
        this.mForceValue = null;
        this.mDefaultValue = null;
        if (arrayList.size() != 1) {
        }
        if (161 != i) {
        }
        this.mDefaultValue = "6";
        ((ComponentData) this).mItems = Collections.unmodifiableList(arrayList);
    }

    public void setForceValueOverlay(@Nullable String str) {
        if (str == null) {
            this.mForceValueOverlay = null;
        } else {
            this.mForceValueOverlay = str;
        }
    }

    public boolean supportVideoSATForVideoQuality(int i) {
        if (!DataRepository.dataItemFeature().c_28041_0x0006() || !CameraSettings.isSettingsVideoSATEnable() || i != 162) {
            return false;
        }
        String componentValue = getComponentValue(i);
        return !"8".equals(componentValue) && !QUALITY_4K_60FPS.equals(componentValue) && !QUALITY_1080P_60FPS.equals(componentValue);
    }
}
