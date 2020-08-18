package com.ss.android.ugc.effectmanager.effect.listener;

import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.effect.model.ProviderEffectModel;

public interface IFetchProviderEffect {
    void onFail(ExceptionResult exceptionResult);

    void onSuccess(ProviderEffectModel providerEffectModel);
}
