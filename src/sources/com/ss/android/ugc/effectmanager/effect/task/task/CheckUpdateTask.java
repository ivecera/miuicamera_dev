package com.ss.android.ugc.effectmanager.effect.task.task;

import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.JsonReader;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.EffectRequest;
import com.ss.android.ugc.effectmanager.common.ErrorConstants;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.task.NormalTask;
import com.ss.android.ugc.effectmanager.common.utils.CloseUtil;
import com.ss.android.ugc.effectmanager.common.utils.EffectCacheKeyGenerator;
import com.ss.android.ugc.effectmanager.common.utils.NetworkUtils;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.model.net.EffectCheckUpdateResponse;
import com.ss.android.ugc.effectmanager.effect.task.result.EffectCheckUpdateResult;
import com.ss.android.ugc.effectmanager.link.LinkSelector;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CheckUpdateTask extends NormalTask {
    private static final String APP_VERSION = "app_version";
    public static final int CHECK_CATEGORY_UPDATE = 1;
    public static final int CHECK_PANEL_UPDATE = 0;
    public static final int CHECK_PANEL_UPDATE_PAGE = 2;
    private static final String VERSION = "version";
    private String mCategory;
    private int mCheckType;
    private EffectConfiguration mConfiguration = this.mEffectContext.getEffectConfiguration();
    private EffectContext mEffectContext;
    private String mPanel;
    private String mVersion;

    public CheckUpdateTask(EffectContext effectContext, String str, Handler handler, String str2, String str3, int i) {
        super(handler, str, EffectConstants.NETWORK);
        this.mEffectContext = effectContext;
        this.mPanel = str2;
        this.mCategory = str3;
        this.mCheckType = i;
    }

    private EffectRequest buildRequest() {
        HashMap hashMap = new HashMap();
        LinkSelector linkSelector = this.mEffectContext.getLinkSelector();
        boolean z = false;
        if (linkSelector != null) {
            SharedPreferences sharedPreferences = linkSelector.getContext().getSharedPreferences("version", 0);
            z = !sharedPreferences.getString("app_version", "").equals(this.mConfiguration.getAppVersion());
            if (z) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("app_version", this.mConfiguration.getAppVersion());
                edit.commit();
            }
        }
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
        hashMap.put(EffectConfiguration.KEY_PANEL, this.mPanel);
        int i = this.mCheckType;
        String str = EffectConstants.ROUTE_CHECK_UPDATE_PAGE;
        if (i == 1) {
            hashMap.put(EffectConfiguration.KEY_CATEGORY, this.mCategory);
            str = EffectConstants.ROUTE_CATEGORY_CHECK_UPDATE;
        }
        if (z) {
            hashMap.put("version", "");
        } else {
            hashMap.put("version", this.mVersion);
        }
        return new EffectRequest("GET", NetworkUtils.buildRequestUrl(hashMap, this.mEffectContext.getLinkSelector().getBestHostUrl() + this.mConfiguration.getApiAdress() + str));
    }

    private boolean checkedChannelCache() {
        String str;
        JsonReader jsonReader;
        int i = this.mCheckType;
        if (i == 1) {
            str = EffectCacheKeyGenerator.generateCategoryVersionKey(this.mPanel, this.mCategory);
        } else if (i != 2) {
            str = "effect_version" + this.mPanel;
        } else {
            str = EffectCacheKeyGenerator.generatePanelInfoVersionKey(this.mPanel);
        }
        InputStream queryToStream = this.mConfiguration.getCache().queryToStream(str);
        if (queryToStream == null) {
            return false;
        }
        try {
            jsonReader = new JsonReader(new InputStreamReader(queryToStream));
            try {
                jsonReader.beginObject();
                while (true) {
                    if (!jsonReader.hasNext()) {
                        break;
                    }
                    String nextName = jsonReader.nextName();
                    char c2 = 65535;
                    if (nextName.hashCode() == 351608024) {
                        if (nextName.equals("version")) {
                            c2 = 0;
                        }
                    }
                    if (c2 == 0) {
                        this.mVersion = jsonReader.nextString();
                        break;
                    }
                    jsonReader.nextString();
                }
                CloseUtil.close(jsonReader);
                return true;
            } catch (IOException unused) {
                CloseUtil.close(jsonReader);
                return false;
            } catch (Throwable th) {
                th = th;
                CloseUtil.close(jsonReader);
                throw th;
            }
        } catch (IOException unused2) {
            jsonReader = null;
            CloseUtil.close(jsonReader);
            return false;
        } catch (Throwable th2) {
            th = th2;
            jsonReader = null;
            CloseUtil.close(jsonReader);
            throw th;
        }
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask
    public void execute() {
        if (checkedChannelCache()) {
            EffectRequest buildRequest = buildRequest();
            if (isCanceled()) {
                sendMessage(13, new EffectCheckUpdateResult(false, new ExceptionResult((int) ErrorConstants.CODE_CANCEL_DOWNLOAD)));
            }
            try {
                EffectCheckUpdateResponse effectCheckUpdateResponse = (EffectCheckUpdateResponse) this.mConfiguration.getEffectNetWorker().execute(buildRequest, this.mConfiguration.getJsonConverter(), EffectCheckUpdateResponse.class);
                if (effectCheckUpdateResponse != null) {
                    sendMessage(13, new EffectCheckUpdateResult(effectCheckUpdateResponse.isUpdated(), null));
                } else {
                    sendMessage(13, new EffectCheckUpdateResult(false, new ExceptionResult((int) ErrorConstants.CODE_DOWNLOAD_ERROR)));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                sendMessage(13, new EffectCheckUpdateResult(false, new ExceptionResult(e2)));
            }
        } else {
            sendMessage(13, new EffectCheckUpdateResult(true, null));
        }
    }
}
