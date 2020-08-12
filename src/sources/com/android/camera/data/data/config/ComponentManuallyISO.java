package com.android.camera.data.data.config;

import android.annotation.TargetApi;
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
import java.util.Collections;
import java.util.List;

@TargetApi(21)
public class ComponentManuallyISO extends ComponentData {
    private static final String TAG = "ComponentManuallyISO";
    private ComponentDataItem[] mFullItems;
    private ComponentDataItem[] mVideoFullItems;

    public ComponentManuallyISO(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    private List<ComponentDataItem> createItems(int i, CameraCapabilities cameraCapabilities) {
        ArrayList arrayList = new ArrayList();
        if (cameraCapabilities == null) {
            Log.w(TAG, "initItems: CameraCapabilities is null!!!");
            return arrayList;
        }
        ComponentDataItem[] videoFullItems = i == 180 ? getVideoFullItems() : getFullItems();
        arrayList.add(videoFullItems[0]);
        Range<Integer> isoRange = cameraCapabilities.getIsoRange();
        if (isoRange != null) {
            int intValue = isoRange.getLower().intValue();
            int intValue2 = isoRange.getUpper().intValue();
            for (int i2 = 1; i2 < videoFullItems.length; i2++) {
                ComponentDataItem componentDataItem = videoFullItems[i2];
                int parseInt = Integer.parseInt(componentDataItem.mValue);
                if (i2 == 0 || (intValue <= parseInt && parseInt <= intValue2)) {
                    arrayList.add(componentDataItem);
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
            this.mFullItems = new ComponentDataItem[]{new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_auto_abbr, "0"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso50, "50"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso64, "64"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso80, "80"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso100, "100"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso125, "125"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso160, "160"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso200, ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso250, "250"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso320, "320"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso400, "400"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso500, "500"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso640, "640"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso800, "800"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso1000, "1000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso1250, "1250"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso1600, "1600"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso2000, "2000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso2500, "2500"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso3200, "3200"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso4000, "4000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso5000, "5000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso6400, "6400")};
        } else {
            this.mFullItems = new ComponentDataItem[]{new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_auto_abbr, "0"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso100, "100"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso200, ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso400, "400"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso800, "800"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso1600, "1600"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso3200, "3200")};
        }
        return this.mFullItems;
    }

    private ComponentDataItem[] getVideoFullItems() {
        ComponentDataItem[] componentDataItemArr = this.mVideoFullItems;
        if (componentDataItemArr != null) {
            return componentDataItemArr;
        }
        this.mVideoFullItems = new ComponentDataItem[]{new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_auto_abbr, "0"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso50, "50"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso64, "64"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso80, "80"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso100, "100"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso125, "125"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso160, "160"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso200, ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso250, "250"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso320, "320"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso400, "400"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso500, "500"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso640, "640"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso800, "800"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso1000, "1000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso1250, "1250"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso1600, "1600"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso2000, "2000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso2500, "2500"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso3200, "3200"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso4000, "4000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso5000, "5000"), new ComponentDataItem(-1, -1, (int) R.string.pref_camera_iso_entry_iso6400, "6400")};
        return this.mVideoFullItems;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.ComponentData
    public boolean checkValueValid(int i, String str) {
        return Util.isStringValueContained(str, (DataRepository.dataItemFeature().bf() || DataRepository.dataItemFeature().c_27845_0x0003()) ? R.array.pref_camera_iso_entryvalues_new : R.array.pref_camera_iso_entryvalues);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        String componentValue = super.getComponentValue(i);
        List<ComponentDataItem> items = getItems();
        return (items == null || items.isEmpty()) ? getDefaultValue(i) : items.stream().filter(new a(componentValue)).findAny().isPresent() ? componentValue : getDefaultValue(i);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_iso_title_abbr;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 167 ? CameraSettings.KEY_QC_PRO_VIDEO_ISO : CameraSettings.KEY_QC_ISO;
    }

    public void reInit(int i, CameraCapabilities cameraCapabilities) {
        ((ComponentData) this).mItems = Collections.unmodifiableList(createItems(i, cameraCapabilities));
        if (!((ComponentData) this).mItems.isEmpty()) {
            if (!((ComponentData) this).mItems.stream().filter(new b(super.getComponentValue(i))).findAny().isPresent()) {
                setComponentValue(i, getDefaultValue(i));
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.ComponentData
    public void resetComponentValue(int i) {
        setComponentValue(i, getDefaultValue(i));
    }
}
