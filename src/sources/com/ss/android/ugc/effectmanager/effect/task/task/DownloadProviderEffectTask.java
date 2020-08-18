package com.ss.android.ugc.effectmanager.effect.task.task;

import android.accounts.NetworkErrorException;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.EffectRequest;
import com.ss.android.ugc.effectmanager.common.ErrorConstants;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.task.NormalTask;
import com.ss.android.ugc.effectmanager.common.utils.EffectUtils;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.model.ProviderEffect;
import com.ss.android.ugc.effectmanager.effect.task.result.DownloadProviderEffectTaskResult;
import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class DownloadProviderEffectTask extends NormalTask {
    private EffectConfiguration mConfiguration;
    private String mDownLoadUrl;
    private ProviderEffect mEffect;
    private EffectContext mEffectContext;
    private String mRemoteIp;
    private String mRequestedUrl;
    private int mRetryCount;

    public DownloadProviderEffectTask(EffectContext effectContext, String str, @NonNull ProviderEffect providerEffect, Handler handler) {
        super(handler, str, EffectConstants.NETWORK);
        this.mEffect = providerEffect;
        this.mEffectContext = effectContext;
        this.mConfiguration = effectContext.getEffectConfiguration();
        this.mRetryCount = effectContext.getEffectConfiguration().getRetryCount();
        this.mDownLoadUrl = EffectUtils.getUrl(providerEffect);
    }

    private ProviderEffect download(String str, String str2) throws Exception {
        InputStream execute = this.mEffectContext.getEffectConfiguration().getEffectNetWorker().execute(new EffectRequest("GET", str));
        if (execute != null) {
            EffectUtils.convertStreamToFile(execute, str2);
            return this.mEffect;
        }
        throw new NetworkErrorException(ErrorConstants.EXCEPTION_DOWNLOAD_ERROR);
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask, com.ss.android.ugc.effectmanager.common.task.NormalTask
    public void cancel() {
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask
    public void execute() {
        int i = this.mRetryCount;
        int i2 = 0;
        while (i2 < i) {
            if (isCanceled()) {
                sendMessage(19, new DownloadProviderEffectTaskResult(this.mEffect, new ExceptionResult((int) ErrorConstants.CODE_CANCEL_DOWNLOAD)));
                return;
            }
            try {
                if (TextUtils.isEmpty(this.mEffect.getPath())) {
                    ProviderEffect providerEffect = this.mEffect;
                    providerEffect.setPath(this.mConfiguration.getEffectDir() + File.separator + this.mEffect.getId() + ".gif");
                }
                this.mRequestedUrl = this.mDownLoadUrl;
                try {
                    this.mRemoteIp = InetAddress.getByName(new URL(this.mRequestedUrl).getHost()).getHostAddress();
                } catch (UnknownHostException e2) {
                    e2.printStackTrace();
                } catch (MalformedURLException e3) {
                    e3.printStackTrace();
                }
                ProviderEffect download = download(this.mDownLoadUrl, this.mEffect.getPath());
                if (download != null) {
                    sendMessage(19, new DownloadProviderEffectTaskResult(download, null));
                    return;
                }
                i2++;
            } catch (Exception e4) {
                if (i2 == i - 1) {
                    e4.printStackTrace();
                    ExceptionResult exceptionResult = new ExceptionResult(e4);
                    exceptionResult.setTrackParams(this.mRequestedUrl, "", this.mRemoteIp);
                    sendMessage(19, new DownloadProviderEffectTaskResult(this.mEffect, exceptionResult));
                    return;
                }
            }
        }
    }
}
