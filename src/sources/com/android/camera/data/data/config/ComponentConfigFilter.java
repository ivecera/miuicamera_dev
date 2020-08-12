package com.android.camera.data.data.config;

import android.util.SparseBooleanArray;
import com.android.camera.R;
import com.android.camera.constant.ModeConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.effect.FilterInfo;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ComponentConfigFilter extends ComponentData {
    private SparseBooleanArray mIsClosed;

    public ComponentConfigFilter(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    public void clearFilterSelected(DataProvider.ProviderEditor providerEditor) {
        if (providerEditor != null) {
            int[] allModes = ModeConstant.getAllModes();
            for (int i : allModes) {
                providerEditor.putString(getKey(i), getDefaultValue(i));
            }
        }
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        Log.d("ComponentConfigFilter", "getComponentValue: isClosed(mode) = " + isClosed(i) + ", mode = " + i);
        return isClosed(i) ? String.valueOf(FilterInfo.FILTER_ID_NONE) : i == 183 ? DataRepository.dataItemLive().getMiLiveFilterId() : super.getComponentValue(i);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return (i == 162 || i == 180) ? String.valueOf(0) : String.valueOf(FilterInfo.FILTER_ID_NONE);
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_coloreffect_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_camera_shader_coloreffect_key";
    }

    public boolean isClosed(int i) {
        if (this.mIsClosed == null) {
            this.mIsClosed = new SparseBooleanArray();
        }
        if (i == 165) {
            i = 163;
        }
        return this.mIsClosed.get(i);
    }

    public void mapToItems(ArrayList<FilterInfo> arrayList, int i) {
        ComponentDataItem componentDataItem;
        ((ComponentData) this).mItems = new ArrayList(arrayList.size());
        boolean supportColorRentention = DataRepository.dataItemRunning().getComponentRunningShine().supportColorRentention();
        Iterator<FilterInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            FilterInfo next = it.next();
            if (162 != i && 180 != i) {
                componentDataItem = new ComponentDataItem(next.getIconResId(), next.getIconResId(), next.getNameResId(), String.valueOf(next.getId()));
            } else if (next.getTagUniqueFilterId() != 200 || supportColorRentention) {
                componentDataItem = new ComponentDataItem(next.getIconResId(), next.getIconResId(), next.getNameResId(), String.valueOf(next.getTagUniqueFilterId()));
            }
            ((ComponentData) this).mItems.add(componentDataItem);
        }
    }

    public void setClosed(boolean z, int i) {
        Log.d("ComponentConfigFilter", "setClosed: mode = " + i + ", close = " + z);
        if (this.mIsClosed == null) {
            this.mIsClosed = new SparseBooleanArray();
        }
        if (i == 165) {
            i = 163;
        }
        this.mIsClosed.put(i, z);
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        if (i == 183) {
            DataRepository.dataItemLive().setMiLiveFilterId(str);
        } else {
            super.setComponentValue(i, str);
        }
    }
}
