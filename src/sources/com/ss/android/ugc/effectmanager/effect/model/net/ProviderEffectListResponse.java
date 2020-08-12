package com.ss.android.ugc.effectmanager.effect.model.net;

import com.ss.android.ugc.effectmanager.common.model.BaseNetResponse;
import com.ss.android.ugc.effectmanager.effect.model.ProviderEffectModel;

public class ProviderEffectListResponse extends BaseNetResponse {
    private ProviderEffectModel data;

    public boolean checkValue() {
        return this.data != null;
    }

    public ProviderEffectModel getData() {
        return this.data;
    }

    public void setData(ProviderEffectModel providerEffectModel) {
        this.data = providerEffectModel;
    }
}
