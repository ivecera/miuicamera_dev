package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.List;

public class ComponentConfigAuxiliary extends ComponentData {
    public static final String A_CLOSE = "close";
    public static final String A_EXPOSURE_FEEDBACK = "exposure_feedback";
    public static final String A_FOCUS_PEAK = "peak_focus";

    public ComponentConfigAuxiliary(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "close";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_auxiliary_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i == 167) {
            return CameraSettings.KEY_CAMERA_PRO_PHOTO_AUXILIARY;
        }
        if (i != 180) {
            return null;
        }
        return CameraSettings.KEY_CAMERA_PRO_VIDEO_AUXILIARY;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getValueSelectedDrawable(int i) {
        String componentValue = getComponentValue(i);
        return A_FOCUS_PEAK.equals(componentValue) ? R.drawable.ic_config_focus_peak_on : "exposure_feedback".equals(componentValue) ? R.drawable.ic_config_exposure_feedback_on : R.drawable.ic_config_auxiliary;
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        return A_FOCUS_PEAK.equals(componentValue) ? R.string.auxiliary_focus_peak_status : "exposure_feedback".equals(componentValue) ? R.string.auxiliary_exposure_feedback_status : R.string.auxiliary_close_status;
    }

    public List<ComponentDataItem> reInit(int i, CameraCapabilities cameraCapabilities) {
        if (((ComponentData) this).mItems == null) {
            ((ComponentData) this).mItems = new ArrayList();
        } else {
            ((ComponentData) this).mItems.clear();
        }
        if (i == 167 || i == 180) {
            ((ComponentData) this).mItems.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_auxiliary_close, "close"));
            ((ComponentData) this).mItems.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_auxiliary_focus_peak, A_FOCUS_PEAK));
            ((ComponentData) this).mItems.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_auxiliary_exposure_feedback, "exposure_feedback"));
        }
        return ((ComponentData) this).mItems;
    }
}
