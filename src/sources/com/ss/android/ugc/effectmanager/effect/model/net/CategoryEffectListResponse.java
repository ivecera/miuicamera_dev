package com.ss.android.ugc.effectmanager.effect.model.net;

import com.ss.android.ugc.effectmanager.common.model.BaseNetResponse;
import com.ss.android.ugc.effectmanager.effect.model.CategoryPageModel;

public class CategoryEffectListResponse extends BaseNetResponse {
    private CategoryPageModel data;

    public boolean checkValue() {
        CategoryPageModel categoryPageModel = this.data;
        return (categoryPageModel == null || categoryPageModel.getCategoryEffects() == null) ? false : true;
    }

    public CategoryPageModel getData() {
        return this.data;
    }

    public void setData(CategoryPageModel categoryPageModel) {
        this.data = categoryPageModel;
    }
}
