package com.android.camera.data.data.setting;

import android.content.Context;
import com.android.camera.R;
import com.android.camera.data.data.ComponentMultiple;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.data.data.TypeItem;
import com.android.camera2.CameraCapabilities;

public class ComponentSettingMultipleWatermark extends ComponentMultiple {
    public <D extends DataItemBase> ComponentSettingMultipleWatermark(D d2) {
        super(d2);
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_watermark_title;
    }

    @Override // com.android.camera.data.data.ComponentMultiple
    public void initTypeElements(Context context, int i, CameraCapabilities cameraCapabilities, int i2) {
        String string = context.getString(getDisplayTitleString());
        insert(new TypeItem((int) R.string.pref_camera_watermark_title, string, "pref_time_watermark_key", Boolean.FALSE), new TypeItem((int) R.string.pref_camera_watermark_title, string, "pref_dualcamera_watermark_key", Boolean.TRUE), new TypeItem((int) R.string.pref_camera_watermark_title, string, "user_define_watermark_key", context.getString(R.string.device_watermark_default_text)));
    }
}
