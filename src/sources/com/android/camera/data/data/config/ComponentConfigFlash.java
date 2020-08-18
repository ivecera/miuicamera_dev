package com.android.camera.data.data.config;

import android.annotation.TargetApi;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.ThermalDetector;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TargetApi(21)
public class ComponentConfigFlash extends ComponentData {
    public static final String FLASH_VALUE_AUTO = "3";
    public static final String FLASH_VALUE_BACK_SOFT_LIGHT = "5";
    public static final String FLASH_VALUE_MANUAL_OFF = "200";
    public static final String FLASH_VALUE_OFF = "0";
    public static final String FLASH_VALUE_ON = "1";
    public static final String FLASH_VALUE_SCREEN_LIGHT_AUTO = "103";
    public static final String FLASH_VALUE_SCREEN_LIGHT_ON = "101";
    public static final String FLASH_VALUE_TORCH = "2";
    private SparseArray<String> mFlashValuesForSceneMode = new SparseArray<>();
    private boolean mIsBackSoftLightSupported;
    private boolean mIsClosed;
    private boolean mIsHardwareSupported;

    public ComponentConfigFlash(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
        ((ComponentData) this).mItems = new ArrayList();
        ((ComponentData) this).mItems.add(new ComponentDataItem(getFlashOffRes(), getFlashOffRes(), (int) R.string.pref_camera_flashmode_entry_off, "0"));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0045, code lost:
        if (r11 != 173) goto L_0x0074;
     */
    private List<ComponentDataItem> createItems(int i, int i2, CameraCapabilities cameraCapabilities, ComponentConfigUltraWide componentConfigUltraWide) {
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        this.mIsHardwareSupported = (!cameraCapabilities.isFlashSupported() || DataRepository.dataItemGlobal().getDisplayMode() != 1) ? false : true;
        if (DataRepository.dataItemFeature().c_33066_0x0001() && i2 == 0 && cameraCapabilities.isBackSoftLightSupported()) {
            z = true;
        }
        this.mIsBackSoftLightSupported = z;
        if (i != 166) {
            if (i == 171) {
                if (this.mIsHardwareSupported && this.mIsBackSoftLightSupported) {
                    arrayList.add(new ComponentDataItem(getFlashBackSoftLightRes(), getFlashBackSoftLightRes(), (int) R.string.pref_camera_flashmode_entry_off, "0"));
                    arrayList.add(new ComponentDataItem(getFlashBackSoftLightSelectedRes(), getFlashBackSoftLightSelectedRes(), (int) R.string.pref_camera_flashmode_entry_back_soft_light, "5"));
                    return arrayList;
                }
            }
        }
        if (i2 == 0) {
            return arrayList;
        }
        if (!this.mIsHardwareSupported) {
            if (i2 == 1 && b.Ll()) {
                if (i == 163 || i == 165 || i == 171) {
                    arrayList.add(new ComponentDataItem(getFlashOffRes(), getFlashOffRes(), (int) R.string.pref_camera_flashmode_entry_off, "0"));
                    arrayList.add(new ComponentDataItem(getFlashAutoRes(), getFlashAutoRes(), (int) R.string.pref_camera_flashmode_entry_auto, FLASH_VALUE_SCREEN_LIGHT_AUTO));
                    arrayList.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, FLASH_VALUE_SCREEN_LIGHT_ON));
                }
                if (i == 177 || i == 184) {
                    arrayList.add(new ComponentDataItem(getFlashOffRes(), getFlashOffRes(), (int) R.string.pref_camera_flashmode_entry_off, "0"));
                    arrayList.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, FLASH_VALUE_SCREEN_LIGHT_ON));
                }
            }
            return arrayList;
        }
        arrayList.add(new ComponentDataItem(getFlashOffRes(), getFlashOffRes(), (int) R.string.pref_camera_flashmode_entry_off, "0"));
        if (!(i == 161 || i == 162 || i == 169 || i == 172 || i == 174)) {
            if (i == 177) {
                if (CameraSettings.isBackCamera()) {
                    arrayList.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, "1"));
                }
                if (CameraSettings.isFrontCamera() && b.El()) {
                    arrayList.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, "2"));
                } else if (b.lm()) {
                    arrayList.add(new ComponentDataItem(getFlashTorchRes(), getFlashTorchRes(), (int) R.string.pref_camera_flashmode_entry_torch, "2"));
                }
            } else if (!(i == 179 || i == 180 || i == 183)) {
                if (i != 184) {
                    arrayList.add(new ComponentDataItem(getFlashAutoRes(), getFlashAutoRes(), (int) R.string.pref_camera_flashmode_entry_auto, "3"));
                    if (CameraSettings.isBackCamera()) {
                        arrayList.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, "1"));
                    }
                    if (CameraSettings.isFrontCamera() && b.El()) {
                        arrayList.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, "2"));
                    } else if (b.lm()) {
                        arrayList.add(new ComponentDataItem(getFlashTorchRes(), getFlashTorchRes(), (int) R.string.pref_camera_flashmode_entry_torch, "2"));
                    }
                    if (this.mIsBackSoftLightSupported) {
                        arrayList.add(new ComponentDataItem(getFlashBackSoftLightSelectedRes(), getFlashBackSoftLightSelectedRes(), (int) R.string.pref_camera_flashmode_entry_back_soft_light, "5"));
                    }
                } else {
                    if (CameraSettings.isBackCamera() && DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 0) {
                        arrayList.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, "1"));
                    }
                    if (CameraSettings.isFrontCamera() && b.El()) {
                        arrayList.add(new ComponentDataItem(getFlashOnRes(), getFlashOnRes(), (int) R.string.pref_camera_flashmode_entry_on, "2"));
                    } else if (b.lm()) {
                        arrayList.add(new ComponentDataItem(getFlashTorchRes(), getFlashTorchRes(), (int) R.string.pref_camera_flashmode_entry_torch, "2"));
                    }
                }
            }
            return arrayList;
        }
        arrayList.add(new ComponentDataItem(getFlashTorchRes(), getFlashTorchRes(), (int) R.string.pref_camera_flashmode_entry_torch, "2"));
        if (this.mIsBackSoftLightSupported) {
            arrayList.add(new ComponentDataItem(getFlashBackSoftLightSelectedRes(), getFlashBackSoftLightSelectedRes(), (int) R.string.pref_camera_flashmode_entry_back_soft_light, "5"));
        }
        return arrayList;
    }

    private String getComponentValueInternal(int i) {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        if (dataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key")) {
            String flashModeByScene = CameraSettings.getFlashModeByScene(dataItemRunning.getComponentRunningSceneValue().getComponentValue(i));
            if (!TextUtils.isEmpty(flashModeByScene)) {
                return flashModeByScene;
            }
        }
        return super.getComponentValue(i);
    }

    private int getFlashAutoRes() {
        return R.drawable.ic_new_config_flash_auto;
    }

    private int getFlashBackSoftLightRes() {
        return R.drawable.ic_new_config_flash_back_soft_light;
    }

    private int getFlashBackSoftLightSelectedRes() {
        return R.drawable.ic_new_config_flash_back_soft_light_selected;
    }

    private int getFlashOffRes() {
        return R.drawable.ic_new_config_flash_off;
    }

    private int getFlashOnRes() {
        return R.drawable.ic_new_config_flash_on;
    }

    private int getFlashTorchRes() {
        return R.drawable.ic_new_config_flash_torch;
    }

    public void clearClosed() {
        this.mIsClosed = false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean disableUpdate() {
        return (!ThermalDetector.getInstance().thermalCloseFlash() || !isHardwareSupported()) ? false : true;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return (!isClosed() && !isEmpty()) ? (i != 167 || CameraSettings.isFlashSupportedInManualMode()) ? getComponentValueInternal(i) : "0" : "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisableReasonString() {
        return CameraSettings.isFrontCamera() ? R.string.close_front_flash_toast : R.string.close_back_flash_toast;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_flashmode_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i == 169 || i == 174) {
            return CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE;
        }
        if (i == 177) {
            return CameraSettings.KEY_FUN_AR_FLASH_MODE;
        }
        if (i == 171) {
            return CameraSettings.KEY_PORTRAIT_FLASH_MODE;
        }
        if (i == 172 || i == 179 || i == 180 || i == 183) {
            return CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE;
        }
        if (i == 184) {
            return DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 0 ? CameraSettings.KEY_FUN_AR2_PHOTO_FLASH_MODE : CameraSettings.KEY_FUN_AR2_VIDEO_FLASH_MODE;
        }
        switch (i) {
            case 160:
                throw new RuntimeException("unspecified flash");
            case 161:
            case 162:
                return CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE;
            default:
                return CameraSettings.KEY_FLASH_MODE;
        }
    }

    public int getValueSelectedDrawableIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("1".equals(componentValue)) {
            return getFlashOnRes();
        }
        if ("3".equals(componentValue)) {
            return getFlashAutoRes();
        }
        if ("0".equals(componentValue)) {
            return (i != 171 || !this.mIsBackSoftLightSupported) ? getFlashOffRes() : getFlashBackSoftLightRes();
        }
        if ("2".equals(componentValue)) {
            return CameraSettings.isFrontCamera() ? getFlashOnRes() : getFlashTorchRes();
        }
        if (FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
            return getFlashAutoRes();
        }
        if (FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue)) {
            return getFlashOnRes();
        }
        if ("5".equals(componentValue)) {
            return getFlashBackSoftLightSelectedRes();
        }
        return -1;
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("1".equals(componentValue)) {
            return R.string.accessibility_flash_on;
        }
        if ("3".equals(componentValue)) {
            return R.string.accessibility_flash_auto;
        }
        if ("0".equals(componentValue)) {
            return R.string.accessibility_flash_off;
        }
        if ("2".equals(componentValue)) {
            return CameraSettings.isFrontCamera() ? R.string.accessibility_flash_on : R.string.accessibility_flash_torch;
        }
        if (FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
            return R.string.accessibility_flash_auto;
        }
        if (FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue)) {
            return R.string.accessibility_flash_on;
        }
        if ("5".equals(componentValue)) {
            return R.string.accessibility_flash_back_soft_light;
        }
        return -1;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean isHardwareSupported() {
        return this.mIsHardwareSupported;
    }

    public boolean isValidFlashValue(String str) {
        return str.matches("^[0-9]+$");
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities, ComponentConfigUltraWide componentConfigUltraWide) {
        ((ComponentData) this).mItems = Collections.unmodifiableList(createItems(i, i2, cameraCapabilities, componentConfigUltraWide));
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        setClosed(false);
        super.setComponentValue(i, str);
    }

    public void setSceneModeFlashValue(int i, String str) {
        this.mFlashValuesForSceneMode.put(i, str);
    }
}
