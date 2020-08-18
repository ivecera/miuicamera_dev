package com.ss.android.ugc.effectmanager.effect.listener;

import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.effect.model.ProviderEffect;

public interface IDownloadProviderEffectListener {
    void onFail(ProviderEffect providerEffect, ExceptionResult exceptionResult);

    void onSuccess(ProviderEffect providerEffect);
}
