package com.android.camera.data.data.setting;

import android.content.Context;
import com.android.camera.R;
import com.android.camera.data.data.ComponentMultiple;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.data.data.TypeItem;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;

public class ComponentSettingMultipleAdvance extends ComponentMultiple {
    public <D extends DataItemBase> ComponentSettingMultipleAdvance(D d2) {
        super(d2);
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.camera_settings_category_development;
    }

    @Override // com.android.camera.data.data.ComponentMultiple
    public void initTypeElements(Context context, int i, CameraCapabilities cameraCapabilities, int i2) {
        TypeItem typeItem;
        String string = context.getString(getDisplayTitleString());
        if (b.Yk()) {
            insert(new TypeItem((int) R.string.pref_fingerprint_capture_title, string, "pref_fingerprint_capture_key", Boolean.TRUE));
        }
        if (i == 162) {
            typeItem = new TypeItem((int) R.string.pref_camera_volumekey_function_title, string, "pref_video_volumekey_function_key", context.getString(R.string.pref_camera_volumekey_function_entry_shutter));
            typeItem.setEntryArrayRes(R.array.pref_video_volumekey_function_entries).setValueArrayRes(R.array.pref_video_volumekey_function_entryvalues);
        } else if (i == 174 || i == 183) {
            typeItem = new TypeItem((int) R.string.pref_camera_volumekey_function_title, string, "pref_live_volumekey_function_key", context.getString(R.string.pref_camera_volumekey_function_entry_zoom));
            typeItem.setEntryArrayRes(R.array.pref_live_volumekey_function_entries).setValueArrayRes(R.array.pref_live_volumekey_function_entryvalues);
        } else {
            typeItem = new TypeItem((int) R.string.pref_camera_volumekey_function_title, string, "pref_camera_volumekey_function_key", context.getString(R.string.pref_camera_volumekey_function_entry_timer));
            typeItem.setEntryArrayRes(R.array.pref_camera_volumekey_function_entries).setValueArrayRes(R.array.pref_camera_volumekey_function_entryvalues);
        }
        insert(typeItem);
    }
}
