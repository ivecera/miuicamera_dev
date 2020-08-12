package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.Collections;
import java.util.List;

public class ComponentConfigSlowMotion extends ComponentData {
    public static final String DATA_CONFIG_NEW_SLOW_MOTION_120 = "slow_motion_120";
    public static final String DATA_CONFIG_NEW_SLOW_MOTION_240 = "slow_motion_240";
    public static final String DATA_CONFIG_NEW_SLOW_MOTION_960 = "slow_motion_960";
    private String[] mSlowMotionMode = new String[0];

    public ComponentConfigSlowMotion(DataItemConfig dataItemConfig, int i) {
        super(dataItemConfig);
        reInit(i);
    }

    public int getContentDesc() {
        String componentValue = getComponentValue(172);
        return DATA_CONFIG_NEW_SLOW_MOTION_120.equals(componentValue) ? R.string.accessibility_camera_video_960fps_120 : DATA_CONFIG_NEW_SLOW_MOTION_240.equals(componentValue) ? R.string.accessibility_camera_video_960fps_240 : R.string.accessibility_camera_video_960fps_960;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        String[] strArr = this.mSlowMotionMode;
        return strArr.length == 0 ? "" : strArr[0];
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    public int getImageResource() {
        String componentValue = getComponentValue(172);
        return DATA_CONFIG_NEW_SLOW_MOTION_120.equals(componentValue) ? R.drawable.ic_new_video_960fps_120 : DATA_CONFIG_NEW_SLOW_MOTION_240.equals(componentValue) ? R.drawable.ic_new_video_960fps_240 : R.drawable.ic_new_video_960fps_960;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return Collections.emptyList();
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return DataItemConfig.DATA_CONFIG_NEW_SLOW_MOTION_KEY;
    }

    public String getNextValue(int i) {
        String componentValue = getComponentValue(i);
        int length = this.mSlowMotionMode.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (TextUtils.equals(this.mSlowMotionMode[i2], componentValue)) {
                return this.mSlowMotionMode[(i2 + 1) % length];
            }
        }
        return getDefaultValue(i);
    }

    public String[] getSupportedFpsOptions() {
        return this.mSlowMotionMode;
    }

    public boolean isSlowMotionFps120() {
        return DATA_CONFIG_NEW_SLOW_MOTION_120.equals(getComponentValue(172));
    }

    public boolean isSlowMotionFps960() {
        return DATA_CONFIG_NEW_SLOW_MOTION_960.equals(getComponentValue(172));
    }

    public void reInit(int i) {
        if (i == 0) {
            if (DataRepository.dataItemFeature().Zd()) {
                this.mSlowMotionMode = new String[]{DATA_CONFIG_NEW_SLOW_MOTION_960, DATA_CONFIG_NEW_SLOW_MOTION_120, DATA_CONFIG_NEW_SLOW_MOTION_240};
            } else if (DataRepository.dataItemFeature().Yd()) {
                this.mSlowMotionMode = new String[]{DATA_CONFIG_NEW_SLOW_MOTION_120, DATA_CONFIG_NEW_SLOW_MOTION_240};
            } else if (DataRepository.dataItemFeature().ce()) {
                this.mSlowMotionMode = new String[]{DATA_CONFIG_NEW_SLOW_MOTION_120};
            } else {
                this.mSlowMotionMode = new String[0];
            }
        } else if (DataRepository.dataItemFeature().ae()) {
            this.mSlowMotionMode = new String[]{DATA_CONFIG_NEW_SLOW_MOTION_120};
        } else {
            this.mSlowMotionMode = new String[0];
        }
    }

    public void setVideoNewSlowMotionFPS(String str) {
        setComponentValue(172, str);
    }
}
