package com.android.camera.data.data.setting;

import android.content.Context;
import com.android.camera.R;
import com.android.camera.data.data.ComponentMultiple;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.data.data.TypeItem;
import com.android.camera2.CameraCapabilities;

public class ComponentSettingMultipleCommon extends ComponentMultiple {
    public <D extends DataItemBase> ComponentSettingMultipleCommon(D d2) {
        super(d2);
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_settings;
    }

    @Override // com.android.camera.data.data.ComponentMultiple
    public void initTypeElements(Context context, int i, CameraCapabilities cameraCapabilities, int i2) {
        String string = context.getString(getDisplayTitleString());
        insert(new TypeItem((int) R.string.pref_camera_recordlocation_title, string, "pref_camera_recordlocation_key", Boolean.TRUE), new TypeItem((int) R.string.pref_camera_sound_title, string, "pref_camerasound_key", Boolean.TRUE));
    }
}
