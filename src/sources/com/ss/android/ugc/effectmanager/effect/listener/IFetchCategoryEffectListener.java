package com.ss.android.ugc.effectmanager.effect.listener;

import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.effect.model.CategoryEffectModel;

public interface IFetchCategoryEffectListener {
    void onFail(ExceptionResult exceptionResult);

    void onSuccess(CategoryEffectModel categoryEffectModel);
}
