package com.ss.android.ugc.effectmanager.effect.task.task;

import android.os.Handler;
import android.text.TextUtils;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.EffectRequest;
import com.ss.android.ugc.effectmanager.common.ErrorConstants;
import com.ss.android.ugc.effectmanager.common.exception.StatusCodeException;
import com.ss.android.ugc.effectmanager.common.listener.ICache;
import com.ss.android.ugc.effectmanager.common.listener.IJsonConverter;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.task.NormalTask;
import com.ss.android.ugc.effectmanager.common.utils.EffectCacheKeyGenerator;
import com.ss.android.ugc.effectmanager.common.utils.NetworkUtils;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.model.PanelInfoModel;
import com.ss.android.ugc.effectmanager.effect.model.net.PanelInfoResponse;
import com.ss.android.ugc.effectmanager.effect.task.result.FetchPanelInfoTaskResult;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchPanelInfoTask extends NormalTask {
    private String category;
    private int count;
    private int cursor;
    private EffectConfiguration mConfiguration = this.mEffectContext.getEffectConfiguration();
    private int mCurCnt = this.mConfiguration.getRetryCount();
    private EffectContext mEffectContext;
    private ICache mFileCache = this.mConfiguration.getCache();
    private IJsonConverter mJsonConverter = this.mConfiguration.getJsonConverter();
    private String mRemoteIp;
    private String mRequestedUrl;
    private String mSelectedHost;
    private String panel;
    private boolean withCategoryEffect;

    public FetchPanelInfoTask(EffectContext effectContext, String str, String str2, boolean z, String str3, int i, int i2, Handler handler) {
        super(handler, str2, EffectConstants.NETWORK);
        this.panel = str;
        this.withCategoryEffect = z;
        this.category = str3;
        this.count = i;
        this.cursor = i2;
        this.mEffectContext = effectContext;
    }

    private EffectRequest buildEffectListRequest() {
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(this.mConfiguration.getAccessKey())) {
            hashMap.put(EffectConfiguration.KEY_ACCESS_KEY, this.mConfiguration.getAccessKey());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getAppVersion())) {
            hashMap.put("app_version", this.mConfiguration.getAppVersion());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getSdkVersion())) {
            hashMap.put(EffectConfiguration.KEY_SDK_VERSION, this.mConfiguration.getSdkVersion());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getChannel())) {
            hashMap.put("channel", this.mConfiguration.getChannel());
        }
        if (!TextUtils.isEmpty(this.panel)) {
            hashMap.put(EffectConfiguration.KEY_PANEL, this.panel);
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getPlatform())) {
            hashMap.put(EffectConfiguration.KEY_DEVICE_PLATFORM, this.mConfiguration.getPlatform());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getDeviceId())) {
            hashMap.put("device_id", this.mConfiguration.getDeviceId());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getRegion())) {
            hashMap.put("region", this.mConfiguration.getRegion());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getDeviceType())) {
            hashMap.put(EffectConfiguration.KEY_DEVICE_TYPE, this.mConfiguration.getDeviceType());
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
        if (!TextUtils.isEmpty(this.mConfiguration.getLongitude())) {
            hashMap.put("longitude", this.mConfiguration.getLongitude());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getLatitude())) {
            hashMap.put("latitude", this.mConfiguration.getLatitude());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getCityCode())) {
            hashMap.put(EffectConfiguration.KEY_CITY_CODE, this.mConfiguration.getCityCode());
        }
        if (this.withCategoryEffect) {
            hashMap.put(EffectConfiguration.KEY_WITH_CATEGORY_EFFECTS, String.valueOf(true));
            hashMap.put(EffectConfiguration.KEY_CATEGORY, this.category);
            hashMap.put(EffectConfiguration.KEY_CURSOR, String.valueOf(this.cursor));
            hashMap.put("count", String.valueOf(this.count));
        }
        this.mSelectedHost = this.mEffectContext.getLinkSelector().getBestHostUrl();
        String buildRequestUrl = NetworkUtils.buildRequestUrl(hashMap, this.mSelectedHost + this.mConfiguration.getApiAdress() + EffectConstants.ROUTE_PANEL_INFO);
        this.mRequestedUrl = buildRequestUrl;
        try {
            this.mRemoteIp = InetAddress.getByName(new URL(buildRequestUrl).getHost()).getHostAddress();
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
        } catch (MalformedURLException e3) {
            e3.printStackTrace();
        }
        return new EffectRequest("GET", buildRequestUrl);
    }

    private void saveEffectList(PanelInfoResponse panelInfoResponse) {
        this.mFileCache.save(EffectCacheKeyGenerator.generatePanelInfoKey(this.mConfiguration.getChannel(), this.panel), this.mJsonConverter.convertObjToJson(panelInfoResponse));
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("version", panelInfoResponse.getData().getVersion());
            this.mFileCache.save(EffectCacheKeyGenerator.generatePanelInfoVersionKey(this.panel), jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask
    public void execute() {
        EffectRequest buildEffectListRequest = buildEffectListRequest();
        while (true) {
            int i = this.mCurCnt;
            this.mCurCnt = i - 1;
            if (i != 0) {
                try {
                    if (isCanceled()) {
                        ExceptionResult exceptionResult = new ExceptionResult((int) ErrorConstants.CODE_CANCEL_DOWNLOAD);
                        exceptionResult.setTrackParams(this.mRequestedUrl, this.mSelectedHost, this.mRemoteIp);
                        sendMessage(22, new FetchPanelInfoTaskResult(null, exceptionResult));
                        return;
                    }
                    PanelInfoResponse panelInfoResponse = (PanelInfoResponse) this.mConfiguration.getEffectNetWorker().execute(buildEffectListRequest, this.mJsonConverter, PanelInfoResponse.class);
                    if (panelInfoResponse.checkValue()) {
                        PanelInfoModel data = panelInfoResponse.getData();
                        saveEffectList(panelInfoResponse);
                        sendMessage(22, new FetchPanelInfoTaskResult(data, null));
                        return;
                    } else if (this.mCurCnt == 0) {
                        ExceptionResult exceptionResult2 = new ExceptionResult((int) ErrorConstants.CODE_DOWNLOAD_ERROR);
                        exceptionResult2.setTrackParams(this.mRequestedUrl, this.mSelectedHost, this.mRemoteIp);
                        sendMessage(22, new FetchPanelInfoTaskResult(null, exceptionResult2));
                        return;
                    }
                } catch (Exception e2) {
                    if (this.mCurCnt == 0 || (e2 instanceof StatusCodeException)) {
                        sendMessage(22, new FetchPanelInfoTaskResult(null, new ExceptionResult(e2)));
                    }
                }
            } else {
                return;
            }
        }
        sendMessage(22, new FetchPanelInfoTaskResult(null, new ExceptionResult(e2)));
    }
}
