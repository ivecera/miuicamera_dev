package com.ss.android.ugc.effectmanager.effect.task.result;

import com.ss.android.ugc.effectmanager.common.task.BaseTaskResult;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.effect.model.PanelInfoModel;

public class FetchPanelInfoTaskResult extends BaseTaskResult {
    private ExceptionResult exception;
    private PanelInfoModel panelInfoModel;

    public FetchPanelInfoTaskResult(PanelInfoModel panelInfoModel2, ExceptionResult exceptionResult) {
        this.panelInfoModel = panelInfoModel2;
        this.exception = exceptionResult;
    }

    public ExceptionResult getException() {
        return this.exception;
    }

    public PanelInfoModel getPanelInfoModel() {
        return this.panelInfoModel;
    }

    public void setException(ExceptionResult exceptionResult) {
        this.exception = exceptionResult;
    }

    public void setPanelInfoModel(PanelInfoModel panelInfoModel2) {
        this.panelInfoModel = panelInfoModel2;
    }
}
