package com.android.camera.data.data.setting;

import android.content.Context;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentMultiple;
import com.android.camera.data.data.DataItemBase;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.List;

public class DataItemSettings {
    private List<ComponentMultiple> componentMultipleList;

    public void create(Context context, int i, CameraCapabilities cameraCapabilities, int i2) {
        this.componentMultipleList = new ArrayList();
        ComponentSettingMultipleCommon componentSettingMultipleCommon = new ComponentSettingMultipleCommon(DataRepository.dataItemGlobal());
        ComponentSettingMultipleCapture componentSettingMultipleCapture = new ComponentSettingMultipleCapture((DataItemBase) DataRepository.provider().dataConfig(i2));
        ComponentSettingMultipleAdvance componentSettingMultipleAdvance = new ComponentSettingMultipleAdvance(DataRepository.dataItemGlobal());
        componentSettingMultipleCommon.initTypeElements(context, i, cameraCapabilities, i2);
        componentSettingMultipleCapture.initTypeElements(context, i, cameraCapabilities, i2);
        componentSettingMultipleAdvance.initTypeElements(context, i, cameraCapabilities, i2);
        if (!componentSettingMultipleCommon.isEmpty()) {
            this.componentMultipleList.add(componentSettingMultipleCommon);
        }
        if (!componentSettingMultipleCapture.isEmpty()) {
            this.componentMultipleList.add(componentSettingMultipleCapture);
        }
        if (!componentSettingMultipleAdvance.isEmpty()) {
            this.componentMultipleList.add(componentSettingMultipleAdvance);
        }
    }
}
