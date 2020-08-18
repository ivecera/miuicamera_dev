package com.ss.android.ugc.effectmanager.effect.task.result;

import com.ss.android.ugc.effectmanager.common.task.BaseTaskResult;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.effect.model.ProviderEffectModel;

public class ProviderEffectTaskResult extends BaseTaskResult {
    private ProviderEffectModel effectListResponse;
    private ExceptionResult exception;

    public ProviderEffectTaskResult(ProviderEffectModel providerEffectModel, ExceptionResult exceptionResult) {
        this.effectListResponse = providerEffectModel;
        this.exception = exceptionResult;
    }

    public ProviderEffectModel getEffectListResponse() {
        return this.effectListResponse;
    }

    public ExceptionResult getException() {
        return this.exception;
    }

    public void setEffectListResponse(ProviderEffectModel providerEffectModel) {
        this.effectListResponse = providerEffectModel;
    }

    public void setException(ExceptionResult exceptionResult) {
        this.exception = exceptionResult;
    }
}
