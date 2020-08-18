package com.ss.android.ugc.effectmanager.effect.task.task;

import android.os.Handler;
import android.text.TextUtils;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.EffectRequest;
import com.ss.android.ugc.effectmanager.common.ErrorConstants;
import com.ss.android.ugc.effectmanager.common.listener.IJsonConverter;
import com.ss.android.ugc.effectmanager.common.model.NetException;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.task.NormalTask;
import com.ss.android.ugc.effectmanager.common.utils.NetworkUtils;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.model.Effect;
import com.ss.android.ugc.effectmanager.effect.model.net.FetchFavoriteListResponse;
import com.ss.android.ugc.effectmanager.effect.task.result.FetchFavoriteListTaskResult;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class FetchFavoriteListTask extends NormalTask {
    private EffectConfiguration mConfiguration = this.mEffectContext.getEffectConfiguration();
    private int mCurCnt;
    private EffectContext mEffectContext;
    private IJsonConverter mJsonConverter = this.mEffectContext.getEffectConfiguration().getJsonConverter();
    private String mPanel;

    public FetchFavoriteListTask(EffectContext effectContext, String str, String str2, Handler handler) {
        super(handler, str2, EffectConstants.NETWORK);
        this.mEffectContext = effectContext;
        this.mPanel = str;
        this.mCurCnt = this.mConfiguration.getRetryCount();
    }

    private EffectRequest buildRequest() {
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(this.mConfiguration.getAccessKey())) {
            hashMap.put(EffectConfiguration.KEY_ACCESS_KEY, this.mConfiguration.getAccessKey());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getDeviceId())) {
            hashMap.put("device_id", this.mConfiguration.getDeviceId());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getDeviceType())) {
            hashMap.put(EffectConfiguration.KEY_DEVICE_TYPE, this.mConfiguration.getDeviceType());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getPlatform())) {
            hashMap.put(EffectConfiguration.KEY_DEVICE_PLATFORM, this.mConfiguration.getPlatform());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getRegion())) {
            hashMap.put("region", this.mConfiguration.getRegion());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getSdkVersion())) {
            hashMap.put(EffectConfiguration.KEY_SDK_VERSION, this.mConfiguration.getSdkVersion());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getAppVersion())) {
            hashMap.put("app_version", this.mConfiguration.getAppVersion());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getChannel())) {
            hashMap.put("channel", this.mConfiguration.getChannel());
        }
        if (!TextUtils.isEmpty(this.mPanel)) {
            hashMap.put(EffectConfiguration.KEY_PANEL, this.mPanel);
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getAppID())) {
            hashMap.put(EffectConfiguration.KEY_APP_ID, this.mConfiguration.getAppID());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getAppLanguage())) {
            hashMap.put(EffectConfiguration.KEY_APP_LANGUAGE, this.mConfiguration.getAppLanguage());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getSysLanguage())) {
            hashMap.put(EffectConfiguration.KEY_SYS_LANGUAGE, this.mConfiguration.getSysLanguage());
        }
        return new EffectRequest("GET", NetworkUtils.buildRequestUrl(hashMap, this.mEffectContext.getLinkSelector().getBestHostUrl() + this.mConfiguration.getApiAdress() + EffectConstants.ROUTE_FAVORITE_LIST));
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask
    public void execute() {
        EffectRequest buildRequest = buildRequest();
        int i = 0;
        while (i < this.mCurCnt) {
            try {
                FetchFavoriteListResponse fetchFavoriteListResponse = (FetchFavoriteListResponse) this.mConfiguration.getEffectNetWorker().execute(buildRequest, this.mJsonConverter, FetchFavoriteListResponse.class);
                if (fetchFavoriteListResponse == null || !fetchFavoriteListResponse.checkValued()) {
                    throw new NetException(Integer.valueOf((int) ErrorConstants.CODE_DOWNLOAD_ERROR), ErrorConstants.EXCEPTION_DOWNLOAD_ERROR);
                }
                List<Effect> effects = fetchFavoriteListResponse.getEffects();
                for (Effect effect : effects) {
                    if (TextUtils.isEmpty(effect.getZipPath()) || TextUtils.isEmpty(effect.getUnzipPath())) {
                        effect.setZipPath(this.mConfiguration.getEffectDir() + File.separator + effect.getId() + ".zip");
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mConfiguration.getEffectDir());
                        sb.append(File.separator);
                        sb.append(effect.getId());
                        effect.setUnzipPath(sb.toString());
                    }
                }
                sendMessage(41, new FetchFavoriteListTaskResult(effects, fetchFavoriteListResponse.getType()));
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (i == this.mCurCnt - 1) {
                    sendMessage(41, new FetchFavoriteListTaskResult(new ExceptionResult(e2)));
                }
                i++;
            }
        }
    }
}
