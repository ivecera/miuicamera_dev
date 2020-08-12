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
import com.ss.android.ugc.effectmanager.effect.model.net.CategoryEffectListResponse;
import com.ss.android.ugc.effectmanager.effect.task.result.FetchCategoryEffectTaskResult;
import java.io.InputStream;

public class FetchCategoryEffectCacheTask extends NormalTask {
    private String category;
    private int count;
    private int cursor;
    private EffectConfiguration mConfiguration = this.mEffectContext.getEffectConfiguration();
    private EffectContext mEffectContext;
    private ICache mFileCache = this.mConfiguration.getCache();
    private IJsonConverter mJsonConverter = this.mConfiguration.getJsonConverter();
    private String panel;
    private int sortingPosition;
    private String version;

    public FetchCategoryEffectCacheTask(EffectContext effectContext, String str, String str2, String str3, int i, int i2, int i3, String str4, Handler handler) {
        super(handler, str2, EffectConstants.NETWORK);
        this.panel = str;
        this.category = str3;
        this.count = i;
        this.cursor = i2;
        this.sortingPosition = i3;
        this.version = str4;
        this.mEffectContext = effectContext;
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask
    public void execute() {
        InputStream queryToStream = this.mFileCache.queryToStream(EffectCacheKeyGenerator.generateCategoryEffectKey(this.panel, this.category, this.count, this.cursor, this.sortingPosition));
        if (queryToStream == null) {
            sendMessage(21, new FetchCategoryEffectTaskResult(null, new ExceptionResult((int) ErrorConstants.CODE_INVALID_EFFECT_CACHE)));
            return;
        }
        CategoryEffectListResponse categoryEffectListResponse = (CategoryEffectListResponse) this.mJsonConverter.convertJsonToObj(queryToStream, CategoryEffectListResponse.class);
        if (categoryEffectListResponse == null || !categoryEffectListResponse.checkValue()) {
            sendMessage(21, new FetchCategoryEffectTaskResult(null, new ExceptionResult((int) ErrorConstants.CODE_INVALID_EFFECT_CACHE)));
        } else {
            sendMessage(21, new FetchCategoryEffectTaskResult(categoryEffectListResponse.getData().getCategoryEffects(), null));
        }
    }
}
