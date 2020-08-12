package com.ss.android.ugc.effectmanager.effect.task.task;

import android.os.Handler;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.ErrorConstants;
import com.ss.android.ugc.effectmanager.common.listener.ICache;
import com.ss.android.ugc.effectmanager.common.listener.IJsonConverter;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.task.NormalTask;
import com.ss.android.ugc.effectmanager.common.utils.EffectCacheKeyGenerator;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.model.net.PanelInfoResponse;
import com.ss.android.ugc.effectmanager.effect.task.result.FetchPanelInfoTaskResult;
import java.io.InputStream;

public class FetchPanelInfoCacheTask extends NormalTask {
    private EffectConfiguration mConfiguration = this.mEffectContext.getEffectConfiguration();
    private EffectContext mEffectContext;
    private ICache mFileCache = this.mConfiguration.getCache();
    private IJsonConverter mJsonConverter = this.mConfiguration.getJsonConverter();
    private String panel;

    public FetchPanelInfoCacheTask(EffectContext effectContext, String str, String str2, Handler handler) {
        super(handler, str2, EffectConstants.NETWORK);
        this.panel = str;
        this.mEffectContext = effectContext;
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask
    public void execute() {
        InputStream queryToStream = this.mFileCache.queryToStream(EffectCacheKeyGenerator.generatePanelInfoKey(this.mConfiguration.getChannel(), this.panel));
        if (queryToStream == null) {
            sendMessage(22, new FetchPanelInfoTaskResult(null, new ExceptionResult((int) ErrorConstants.CODE_INVALID_EFFECT_CACHE)));
            return;
        }
        PanelInfoResponse panelInfoResponse = (PanelInfoResponse) this.mJsonConverter.convertJsonToObj(queryToStream, PanelInfoResponse.class);
        if (panelInfoResponse == null || !panelInfoResponse.checkValue()) {
            sendMessage(22, new FetchPanelInfoTaskResult(null, new ExceptionResult((int) ErrorConstants.CODE_INVALID_EFFECT_CACHE)));
        } else {
            sendMessage(22, new FetchPanelInfoTaskResult(panelInfoResponse.getData(), null));
        }
    }
}
