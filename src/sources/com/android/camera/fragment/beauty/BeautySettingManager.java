package com.android.camera.fragment.beauty;

import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.TypeElementsBeauty;
import java.util.HashMap;
import java.util.Map;

public class BeautySettingManager {
    private static final String TAG = "BeautySettingManager";
    private HashMap<String, IBeautySettingBusiness> mBeautySettingBusinessArray = new HashMap<>();
    @ComponentRunningShine.ShineType
    private String mBeautyType;

    private IBeautySettingBusiness updateBeautySettingBusiness(@ComponentRunningShine.ShineType String str, TypeElementsBeauty typeElementsBeauty, Map<String, IBeautySettingBusiness> map) {
        if (map.get(str) != null) {
            return map.get(str);
        }
        BeautySettingBusiness beautySettingBusiness = new BeautySettingBusiness(str, typeElementsBeauty);
        map.put(this.mBeautyType, beautySettingBusiness);
        return beautySettingBusiness;
    }

    public IBeautySettingBusiness constructAndGetSetting(@ComponentRunningShine.ShineType String str, TypeElementsBeauty typeElementsBeauty) {
        this.mBeautyType = str;
        IBeautySettingBusiness updateBeautySettingBusiness = updateBeautySettingBusiness(str, typeElementsBeauty, this.mBeautySettingBusinessArray);
        updateBeautySettingBusiness.updateExtraTable();
        return updateBeautySettingBusiness;
    }
}
