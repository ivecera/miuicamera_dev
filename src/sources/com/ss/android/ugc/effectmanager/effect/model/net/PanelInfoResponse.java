package com.ss.android.ugc.effectmanager.effect.model.net;

import com.ss.android.ugc.effectmanager.common.model.BaseNetResponse;
import com.ss.android.ugc.effectmanager.effect.model.PanelInfoModel;

public class PanelInfoResponse extends BaseNetResponse {
    private PanelInfoModel data;

    public boolean checkValue() {
        return this.data != null;
    }

    public PanelInfoModel getData() {
        return this.data;
    }

    public void setData(PanelInfoModel panelInfoModel) {
        this.data = panelInfoModel;
    }
}
