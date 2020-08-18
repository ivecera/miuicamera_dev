package com.ss.android.ugc.effectmanager.effect.listener;

import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.effect.model.PanelInfoModel;

public interface IFetchPanelInfoListener {
    void onFail(ExceptionResult exceptionResult);

    void onSuccess(PanelInfoModel panelInfoModel);
}
