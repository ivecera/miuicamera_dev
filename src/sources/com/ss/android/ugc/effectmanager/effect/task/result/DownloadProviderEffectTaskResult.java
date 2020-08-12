package com.ss.android.ugc.effectmanager.effect.task.result;

import com.ss.android.ugc.effectmanager.common.task.BaseTaskResult;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.effect.model.ProviderEffect;

public class DownloadProviderEffectTaskResult extends BaseTaskResult {
    private ProviderEffect effect;
    private ExceptionResult exception;

    public DownloadProviderEffectTaskResult(ProviderEffect providerEffect, ExceptionResult exceptionResult) {
        this.effect = providerEffect;
        this.exception = exceptionResult;
    }

    public ProviderEffect getEffect() {
        return this.effect;
    }

    public ExceptionResult getException() {
        return this.exception;
    }

    public void setEffect(ProviderEffect providerEffect) {
        this.effect = providerEffect;
    }

    public void setException(ExceptionResult exceptionResult) {
        this.exception = exceptionResult;
    }
}
