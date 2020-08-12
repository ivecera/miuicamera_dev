package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentConfigAi extends ComponentData {
    private boolean mIsClosed;

    public ComponentConfigAi(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    public void clearClosed() {
        this.mIsClosed = false;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_camera_ai_scene_mode_key";
    }

    public boolean isAiSceneOn(int i) {
        if (!isEmpty() && !isClosed()) {
            return ((ComponentData) this).mParentDataItem.getBoolean(getKey(i), DataRepository.dataItemFeature().ub());
        }
        return false;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public List<ComponentDataItem> reInit(int i, int i2, CameraCapabilities cameraCapabilities, int i3) {
        ArrayList arrayList = new ArrayList();
        if (i == 163 || i == 165) {
            if (!cameraCapabilities.isSupportLightTripartite() || i3 == 0) {
                if (i2 == 0) {
                    if (DataRepository.dataItemFeature().s_b_a_OR_T()) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_ai_scene_off, (int) R.drawable.ic_new_ai_scene_on, (int) R.string.accessibility_ai_scene_on, "on"));
                    }
                } else if (DataRepository.dataItemFeature().s_f_a()) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_ai_scene_off, (int) R.drawable.ic_new_ai_scene_on, (int) R.string.accessibility_ai_scene_on, "on"));
                }
            }
        } else if (i == 171) {
            if (i2 == 0) {
                if (DataRepository.dataItemFeature().s_p_a()) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_ai_scene_off, (int) R.drawable.ic_new_ai_scene_on, (int) R.string.accessibility_ai_scene_on, "on"));
                }
            } else if (DataRepository.dataItemFeature().s_f_a()) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_ai_scene_off, (int) R.drawable.ic_new_ai_scene_on, (int) R.string.accessibility_ai_scene_on, "on"));
            }
        }
        ((ComponentData) this).mItems = Collections.unmodifiableList(arrayList);
        return ((ComponentData) this).mItems;
    }

    public void setAiScene(int i, boolean z) {
        if (!isEmpty()) {
            setClosed(false);
            ((ComponentData) this).mParentDataItem.editor().putBoolean(getKey(i), z).apply();
        }
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }
}
