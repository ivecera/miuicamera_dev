package com.ss.android.ugc.effectmanager.effect.task.result;

import com.ss.android.ugc.effectmanager.common.task.BaseTaskResult;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.effect.model.CategoryEffectModel;

public class FetchCategoryEffectTaskResult extends BaseTaskResult {
    private CategoryEffectModel categoryEffectModel;
    private ExceptionResult exception;

    public FetchCategoryEffectTaskResult(CategoryEffectModel categoryEffectModel2, ExceptionResult exceptionResult) {
        this.categoryEffectModel = categoryEffectModel2;
        this.exception = exceptionResult;
    }

    public CategoryEffectModel getEffectChannels() {
        return this.categoryEffectModel;
    }

    public ExceptionResult getException() {
        return this.exception;
    }

    public void setEffectChannels(CategoryEffectModel categoryEffectModel2) {
        this.categoryEffectModel = categoryEffectModel2;
    }

    public void setException(ExceptionResult exceptionResult) {
        this.exception = exceptionResult;
    }
}
