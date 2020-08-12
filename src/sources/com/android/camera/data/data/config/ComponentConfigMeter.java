package com.android.camera.data.data.config;

import android.annotation.TargetApi;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TargetApi(21)
public class ComponentConfigMeter extends ComponentData {
    public static final String METERING_MODE_CENTER_WEIGHTED = "1";
    public static final String METERING_MODE_FRAME_AVERAGE = "0";
    public static final String METERING_MODE_SPOT_METERING = "2";

    public ComponentConfigMeter(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
        ((ComponentData) this).mItems = new ArrayList();
        ((ComponentData) this).mItems.add(new ComponentDataItem(getCenterWeighted(), getCenterWeighted(), (int) R.string.pref_camera_autoexposure_entry_centerweighted, "1"));
    }

    private int getCenterWeighted() {
        return R.drawable.ic_new_config_meter_center_weighted;
    }

    private int getFrameAverage() {
        return R.drawable.ic_new_config_meter_frame_average;
    }

    private int getSpotMetering() {
        return R.drawable.ic_new_config_meter_spot_metering;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return isEmpty() ? "1" : super.getComponentValue(i);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "1";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_autoexposure_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 180 ? "pref_camera_autoexposure_key" : CameraSettings.KEY_PRO_VIDEO_AUTOEXPOSURE;
    }

    public String getTrackValue(int i) {
        String componentValue = getComponentValue(i);
        return "0".equals(componentValue) ? MistatsConstants.Manual.AVERAGE_PHOTOMETRY : "1".equals(componentValue) ? MistatsConstants.Manual.CENTER_WEIGHT : "2".equals(componentValue) ? MistatsConstants.Manual.CENTER_PHOTOMETRY : MistatsConstants.BaseEvent.UNSPECIFIED;
    }

    public int getValueSelectedDrawableIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        return "0".equals(componentValue) ? getFrameAverage() : "1".equals(componentValue) ? getCenterWeighted() : "2".equals(componentValue) ? getSpotMetering() : getCenterWeighted();
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("0".equals(componentValue)) {
            return R.string.pref_camera_autoexposure_entry_frameaverage;
        }
        if ("1".equals(componentValue)) {
            return R.string.pref_camera_autoexposure_entry_centerweighted;
        }
        if ("2".equals(componentValue)) {
            return R.string.pref_camera_autoexposure_entry_spotmetering;
        }
        return -1;
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem(getCenterWeighted(), getCenterWeighted(), (int) R.string.pref_camera_autoexposure_entry_centerweighted, "1"));
        arrayList.add(new ComponentDataItem(getFrameAverage(), getFrameAverage(), (int) R.string.pref_camera_autoexposure_entry_frameaverage, "0"));
        arrayList.add(new ComponentDataItem(getSpotMetering(), getSpotMetering(), (int) R.string.pref_camera_autoexposure_entry_spotmetering, "2"));
        ((ComponentData) this).mItems = Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        super.setComponentValue(i, str);
    }
}
