package com.android.camera.data.data.global;

import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.mi.config.a;
import java.util.ArrayList;
import java.util.List;

public class ComponentModuleList extends ComponentData {
    private int mIntentType;
    private int mLastCameraId;

    public ComponentModuleList(DataItemGlobal dataItemGlobal) {
        super(dataItemGlobal);
    }

    public static final int getTransferredMode(int i) {
        if (i == 165) {
            return 163;
        }
        if (i == 169) {
            return 162;
        }
        if (i == 176) {
            return 166;
        }
        if (i == 182) {
            return 163;
        }
        if (i == 179) {
            return 162;
        }
        if (i != 180) {
            return i;
        }
        return 167;
    }

    private List<ComponentDataItem> initItems() {
        if (this.mIntentType != -1) {
            ArrayList arrayList = new ArrayList();
            a dataItemFeature = DataRepository.dataItemFeature();
            if (!DataRepository.dataItemFeature().c_0x58() || this.mIntentType != 0) {
                if (this.mIntentType == 0 && dataItemFeature.wf() && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
                    arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_new_slow_motion, String.valueOf(172)));
                }
                if (!dataItemFeature.c_0x03() && !dataItemFeature.c_36211_0x0001() && this.mIntentType == 0) {
                    arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_fun, String.valueOf(161)));
                }
                if (!dataItemFeature.c_0x03() && dataItemFeature.c_36211_0x0001() && this.mIntentType == 0) {
                    arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_fun, String.valueOf(183)));
                }
                if (dataItemFeature.c_0x03() && this.mIntentType == 0) {
                    arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_fun, String.valueOf(174)));
                }
                int i = this.mIntentType;
                if (i == 2 || i == 0) {
                    arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_video, String.valueOf(162)));
                }
                int i2 = this.mIntentType;
                if (i2 == 3) {
                    arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_capture, String.valueOf(163)));
                } else if (i2 == 1 || i2 == 0) {
                    arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_capture, String.valueOf(163)));
                    if (this.mIntentType == 0 && dataItemFeature.of() && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
                        arrayList.add(new ComponentDataItem(-1, -1, CameraAppImpl.getAndroidContext().getResources().getString(R.string.module_name_pixel), String.valueOf(175)));
                    }
                    if (dataItemFeature.c_0x34_OR_T() && this.mIntentType == 0) {
                        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_portrait, String.valueOf(171)));
                    }
                    if ((dataItemFeature.s_s_n() || dataItemFeature.c_28041_0x0001()) && this.mIntentType == 0 && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
                        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_scenemode_entry_night, String.valueOf(173)));
                    }
                    if (DataRepository.dataItemFeature().od() && this.mIntentType == 0) {
                        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_fun_ar, String.valueOf(177)));
                    }
                    if (DataRepository.dataItemFeature().Bd() && this.mIntentType == 0) {
                        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_mimoji2, String.valueOf(184)));
                    }
                    if (dataItemFeature.de() && this.mIntentType == 0 && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
                        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_panorama, String.valueOf(166)));
                    }
                    if (DataRepository.dataItemGlobal().getDisplayMode() == 1 && this.mIntentType == 0) {
                        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_professional, String.valueOf(167)));
                    }
                }
                return arrayList;
            }
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.module_name_fun_ar, String.valueOf(177)));
            return arrayList;
        }
        throw new RuntimeException("parse intent first!");
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        int currentCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
        if (DataRepository.dataItemFeature().c_19039_0x0001() && this.mLastCameraId != currentCameraId) {
            this.mLastCameraId = currentCameraId;
            ((ComponentData) this).mItems = initItems();
        }
        if (((ComponentData) this).mItems == null) {
            ((ComponentData) this).mItems = initItems();
        }
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return DataItemGlobal.DATA_COMMON_CURRENT_MODE + this.mIntentType;
    }

    public int getMode(int i) {
        return Integer.valueOf(((ComponentData) this).mItems.get(i).mValue).intValue();
    }

    public boolean needShowLiveRedDot() {
        return !CameraSettings.isLiveModuleClicked();
    }

    public void reInit() {
    }

    public void setIntentType(int i) {
        this.mIntentType = i;
        if (((ComponentData) this).mItems != null) {
            ((ComponentData) this).mItems.clear();
        }
        ((ComponentData) this).mItems = initItems();
    }
}
