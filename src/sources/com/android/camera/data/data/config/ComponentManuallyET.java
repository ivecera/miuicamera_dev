package com.android.camera.data.data.config;

import android.annotation.TargetApi;
import android.text.TextUtils;
import android.util.Range;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ComponentManuallyET extends ComponentData {
    private static final String TAG = "ComponentManuallyET";
    private ComponentDataItem[] mFullItems;
    private boolean mSupperEISEnabled;
    private ComponentDataItem[] mVideoFullItems;

    public ComponentManuallyET(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    private List<ComponentDataItem> createItems(int i, CameraCapabilities cameraCapabilities) {
        ArrayList arrayList = new ArrayList();
        if (cameraCapabilities == null) {
            return arrayList;
        }
        if (i == 167 || i == 180) {
            Range<Long> exposureTimeRange = cameraCapabilities.getExposureTimeRange();
            ComponentDataItem[] videoFullItems = i == 180 ? getVideoFullItems() : getFullItems();
            arrayList.add(videoFullItems[0]);
            if (exposureTimeRange != null) {
                long longValue = exposureTimeRange.getLower().longValue();
                long longValue2 = exposureTimeRange.getUpper().longValue();
                for (int i2 = 1; i2 < videoFullItems.length; i2++) {
                    ComponentDataItem componentDataItem = videoFullItems[i2];
                    long parseLong = Long.parseLong(componentDataItem.mValue);
                    if (longValue <= parseLong && parseLong <= longValue2) {
                        arrayList.add(componentDataItem);
                    }
                }
            }
        }
        return arrayList;
    }

    private ComponentDataItem[] getFullItems() {
        ComponentDataItem[] componentDataItemArr = this.mFullItems;
        if (componentDataItemArr != null) {
            return componentDataItemArr;
        }
        if (DataRepository.dataItemFeature().bf() || DataRepository.dataItemFeature().c_27845_0x0003()) {
            this.mFullItems = new ComponentDataItem[]{new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_auto_abbr, "0"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_4000, "250000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_3200, "312500"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_2500, "400000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_2000, "500000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1600, "625000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1250, "800000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1000, "1000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_800, "1250000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_640, "1562500"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_500, "2000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_400, "2500000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_320, "3125000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_250, "4000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_200, "5000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_160, "6250000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_125, "8000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_100, "10000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_80, "12500000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_60, "16670000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_50, "20000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_40, "25000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_30, "33300000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_25, "40000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_20, "50000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_15, "66700000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_13, "76900000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_10, "100000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_8, "125000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_6, "166700000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_5, "200000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_4, "250000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_0_3, "300000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_0_4, "400000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_0_5, "500000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_0_6, "600000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_0_8, "800000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1s, "1000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1_3, "1300000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1_6, "1600000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_2s, "2000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_2_5, "2500000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_3_2, "3200000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_4s, "4000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_5s, "5000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_6s, "6000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_8s, "8000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_10s, "10000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_13s, "13000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_15s, "15000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_20s, "20000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_25s, "25000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_30s, "30000000000")};
        } else {
            this.mFullItems = new ComponentDataItem[]{new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_auto_abbr, "0"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1000, "1000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_500, "2000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_250, "4000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_125, "8000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_60, "16667000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_30, "33333000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_15, "66667000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_8, "125000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_4, "250000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1_2, "500000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1s, "1000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_2s, "2000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_4s, "4000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_8s, "8000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_16s, "16000000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_32s, "32000000000")};
        }
        return this.mFullItems;
    }

    private ComponentDataItem[] getVideoFullItems() {
        boolean isMovieSolidOn = CameraSettings.isMovieSolidOn();
        ComponentDataItem[] componentDataItemArr = this.mVideoFullItems;
        if (componentDataItemArr != null && this.mSupperEISEnabled == isMovieSolidOn) {
            return componentDataItemArr;
        }
        this.mSupperEISEnabled = isMovieSolidOn;
        ArrayList arrayList = new ArrayList(Arrays.asList(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_auto_abbr, "0"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_4000, "250000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_3200, "312500"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_2500, "400000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_2000, "500000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1600, "625000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1250, "800000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_1000, "1000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_800, "1250000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_640, "1562500"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_500, "2000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_400, "2500000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_320, "3125000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_250, "4000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_200, "5000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_160, "6250000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_125, "8000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_100, "10000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_80, "12500000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_60, "16670000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_50, "20000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_40, "25000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_30, "33300000")));
        if (!isMovieSolidOn) {
            arrayList.addAll(Arrays.asList(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_25, "40000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_20, "50000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_15, "66700000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_13, "76900000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_10, "100000000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_exposuretime_entry_8, "125000000")));
        }
        this.mVideoFullItems = (ComponentDataItem[]) arrayList.toArray(new ComponentDataItem[arrayList.size()]);
        return this.mVideoFullItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean checkValueValid(int i, String str) {
        if (!TextUtils.isEmpty(str)) {
            for (ComponentDataItem componentDataItem : i == 180 ? getVideoFullItems() : getFullItems()) {
                if (componentDataItem.mValue.equals(str)) {
                    return true;
                }
            }
        }
        Log.d(TAG, "checkValueValid: invalid value!");
        return false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        String componentValue = super.getComponentValue(i);
        List<ComponentDataItem> items = getItems();
        if (items != null && !items.isEmpty()) {
            String str = items.get(items.size() - 1).mValue;
            if (Long.parseLong(componentValue) > Long.parseLong(str)) {
                setComponentValue(i, str);
                return str;
            }
        }
        return componentValue;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_manual_exposure_title_abbr;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 167 ? CameraSettings.KEY_QC_PRO_VIDEO_EXPOSURETIME : CameraSettings.KEY_QC_EXPOSURETIME;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getValueDisplayString(int i) {
        String componentValue = getComponentValue(i);
        ComponentDataItem[] videoFullItems = i == 180 ? getVideoFullItems() : getFullItems();
        for (ComponentDataItem componentDataItem : videoFullItems) {
            if (componentDataItem.mValue.equals(componentValue)) {
                return componentDataItem.mDisplayNameRes;
            }
        }
        String format = String.format(Locale.ENGLISH, "mode %1$d, invalid value %2$s for %3$s", Integer.valueOf(i), componentValue, getKey(i));
        Log.e(ComponentManuallyET.class.getSimpleName(), format);
        if (!Util.isDebugOsBuild()) {
            return -1;
        }
        throw new IllegalArgumentException(format);
    }

    @TargetApi(21)
    public void reInit(int i, CameraCapabilities cameraCapabilities) {
        ((ComponentData) this).mItems = Collections.unmodifiableList(createItems(i, cameraCapabilities));
    }

    @Override // com.android.camera.data.data.ComponentData
    public void resetComponentValue(int i) {
        super.resetComponentValue(i);
        setComponentValue(i, getDefaultValue(i));
    }
}
