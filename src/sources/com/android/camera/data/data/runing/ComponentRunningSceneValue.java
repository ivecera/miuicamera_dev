package com.android.camera.data.data.runing;

import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.List;

public class ComponentRunningSceneValue extends ComponentData {
    public ComponentRunningSceneValue(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private List<ComponentDataItem> initItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_scene_mode_auto_normal, (int) R.drawable.ic_scene_mode_auto_normal, (int) R.string.pref_camera_scenemode_entry_auto, "0"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_scene_mode_portrait_normal, (int) R.drawable.ic_scene_mode_portrait_normal, (int) R.string.pref_camera_scenemode_entry_portrait, "3"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_scene_mode_landscape_normal, (int) R.drawable.ic_scene_mode_landscape_normal, (int) R.string.pref_camera_scenemode_entry_landscape, "4"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_scene_mode_sports_normal, (int) R.drawable.ic_scene_mode_sports_normal, (int) R.string.pref_camera_scenemode_entry_sports, "13"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_scene_mode_night_normal, (int) R.drawable.ic_scene_mode_night_normal, (int) R.string.pref_camera_scenemode_entry_night, "5"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_scene_mode_night_portrait_normal, (int) R.drawable.ic_scene_mode_night_portrait_normal, (int) R.string.pref_camera_scenemode_entry_night_portrait, "6"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_scene_mode_beach_normal, (int) R.drawable.ic_scene_mode_beach_normal, (int) R.string.pref_camera_scenemode_entry_beach, "8"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_scene_mode_snow_normal, (int) R.drawable.ic_scene_mode_snow_normal, (int) R.string.pref_camera_scenemode_entry_snow, "9"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_scene_mode_sunset_normal, (int) R.drawable.ic_scene_mode_sunset_normal, (int) R.string.pref_camera_scenemode_entry_sunset, "10"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_scene_mode_fireworks_normal, (int) R.drawable.ic_scene_mode_fireworks_normal, (int) R.string.pref_camera_scenemode_entry_fireworks, "12"));
        return arrayList;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_scenemode_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        if (((ComponentData) this).mItems == null) {
            ((ComponentData) this).mItems = initItems();
        }
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_camera_scenemode_key";
    }
}
