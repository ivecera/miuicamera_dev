package com.ss.android.ugc.effectmanager.effect.repository;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.WeakHandler;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.listener.ICheckChannelListener;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchCategoryEffectListener;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchPanelInfoListener;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchProviderEffect;
import com.ss.android.ugc.effectmanager.effect.model.EffectChannelResponse;
import com.ss.android.ugc.effectmanager.effect.task.result.EffectChannelTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.result.EffectCheckUpdateResult;
import com.ss.android.ugc.effectmanager.effect.task.result.FetchCategoryEffectTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.result.FetchPanelInfoTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.result.ProviderEffectTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.task.CheckUpdateTask;
import com.ss.android.ugc.effectmanager.effect.task.task.FetchCategoryEffectCacheTask;
import com.ss.android.ugc.effectmanager.effect.task.task.FetchCategoryEffectTask;
import com.ss.android.ugc.effectmanager.effect.task.task.FetchEffectChannelCacheTask;
import com.ss.android.ugc.effectmanager.effect.task.task.FetchEffectChannelTask;
import com.ss.android.ugc.effectmanager.effect.task.task.FetchExistEffectListTask;
import com.ss.android.ugc.effectmanager.effect.task.task.FetchPanelInfoCacheTask;
import com.ss.android.ugc.effectmanager.effect.task.task.FetchPanelInfoTask;
import com.ss.android.ugc.effectmanager.effect.task.task.FetchProviderEffectTask;
import com.ss.android.ugc.effectmanager.effect.task.task.SearchProviderEffectTask;

public class EffectChannelRepository implements WeakHandler.IHandler {
    private EffectConfiguration mConfiguration = this.mEffectContext.getEffectConfiguration();
    private EffectContext mEffectContext;
    private EffectListListener mEffectListListener;
    private Handler mHandler = new WeakHandler(this);

    public interface EffectListListener {
        void updateEffectChannel(String str, EffectChannelResponse effectChannelResponse, int i, ExceptionResult exceptionResult);
    }

    public EffectChannelRepository(EffectContext effectContext) {
        this.mEffectContext = effectContext;
    }

    public void checkUpdate(String str, String str2, int i, String str3) {
        this.mConfiguration.getTaskManager().commit(new CheckUpdateTask(this.mEffectContext, str3, this.mHandler, str, str2, i));
    }

    public void fetchCategoryEffect(String str, String str2, String str3, int i, int i2, int i3, String str4, boolean z) {
        this.mConfiguration.getTaskManager().commit(z ? new FetchCategoryEffectCacheTask(this.mEffectContext, str, str2, str3, i, i2, i3, str4, this.mHandler) : new FetchCategoryEffectTask(this.mEffectContext, str, str2, str3, i, i2, i3, str4, this.mHandler));
    }

    public void fetchExistEffectList(@NonNull String str, String str2) {
        this.mConfiguration.getTaskManager().commit(new FetchExistEffectListTask(str, str2, this.mEffectContext, this.mHandler));
    }

    public void fetchList(String str, String str2, boolean z) {
        this.mConfiguration.getTaskManager().commit(z ? new FetchEffectChannelCacheTask(this.mEffectContext, str, str2, this.mHandler, false) : new FetchEffectChannelTask(this.mEffectContext, str, str2, this.mHandler));
    }

    public void fetchPanelInfo(String str, String str2, boolean z, String str3, int i, int i2, boolean z2, IFetchPanelInfoListener iFetchPanelInfoListener) {
        this.mConfiguration.getTaskManager().commit(z2 ? new FetchPanelInfoCacheTask(this.mEffectContext, str, str2, this.mHandler) : new FetchPanelInfoTask(this.mEffectContext, str, str2, z, str3, i, i2, this.mHandler));
    }

    public void fetchProviderEffectList(@Nullable String str, int i, int i2, String str2) {
        this.mConfiguration.getTaskManager().commit(new FetchProviderEffectTask(this.mEffectContext, str2, str, i, i2, this.mHandler));
    }

    @Override // com.ss.android.ugc.effectmanager.common.WeakHandler.IHandler
    public void handleMsg(Message message) {
        if (this.mEffectListListener != null) {
            if (message.what == 14) {
                Object obj = message.obj;
                if (obj instanceof EffectChannelTaskResult) {
                    EffectChannelTaskResult effectChannelTaskResult = (EffectChannelTaskResult) obj;
                    ExceptionResult exception = effectChannelTaskResult.getException();
                    if (exception == null) {
                        this.mEffectListListener.updateEffectChannel(effectChannelTaskResult.getTaskID(), effectChannelTaskResult.getEffectChannels(), 23, null);
                    } else {
                        this.mEffectListListener.updateEffectChannel(effectChannelTaskResult.getTaskID(), effectChannelTaskResult.getEffectChannels(), 27, exception);
                    }
                }
            }
            if (message.what == 22) {
                Object obj2 = message.obj;
                if (obj2 instanceof FetchPanelInfoTaskResult) {
                    FetchPanelInfoTaskResult fetchPanelInfoTaskResult = (FetchPanelInfoTaskResult) obj2;
                    IFetchPanelInfoListener fetchPanelInfoListener = this.mConfiguration.getListenerManger().getFetchPanelInfoListener(fetchPanelInfoTaskResult.getTaskID());
                    ExceptionResult exception2 = fetchPanelInfoTaskResult.getException();
                    if (exception2 == null) {
                        fetchPanelInfoListener.onSuccess(fetchPanelInfoTaskResult.getPanelInfoModel());
                    } else {
                        fetchPanelInfoListener.onFail(exception2);
                    }
                }
            }
            if (message.what == 18) {
                Object obj3 = message.obj;
                if (obj3 instanceof ProviderEffectTaskResult) {
                    ProviderEffectTaskResult providerEffectTaskResult = (ProviderEffectTaskResult) obj3;
                    ExceptionResult exception3 = providerEffectTaskResult.getException();
                    IFetchProviderEffect fetchProviderEffectListener = this.mConfiguration.getListenerManger().getFetchProviderEffectListener(providerEffectTaskResult.getTaskID());
                    if (fetchProviderEffectListener != null) {
                        if (exception3 == null) {
                            fetchProviderEffectListener.onSuccess(providerEffectTaskResult.getEffectListResponse());
                        } else {
                            fetchProviderEffectListener.onFail(providerEffectTaskResult.getException());
                        }
                    }
                }
            }
            if (message.what == 21) {
                Object obj4 = message.obj;
                if (obj4 instanceof FetchCategoryEffectTaskResult) {
                    FetchCategoryEffectTaskResult fetchCategoryEffectTaskResult = (FetchCategoryEffectTaskResult) obj4;
                    ExceptionResult exception4 = fetchCategoryEffectTaskResult.getException();
                    IFetchCategoryEffectListener fetchCategoryEffectListener = this.mConfiguration.getListenerManger().getFetchCategoryEffectListener(fetchCategoryEffectTaskResult.getTaskID());
                    if (fetchCategoryEffectListener != null) {
                        if (exception4 == null) {
                            fetchCategoryEffectListener.onSuccess(fetchCategoryEffectTaskResult.getEffectChannels());
                        } else {
                            fetchCategoryEffectListener.onFail(exception4);
                        }
                    }
                }
            }
            if (message.what == 13) {
                Object obj5 = message.obj;
                if (obj5 instanceof EffectCheckUpdateResult) {
                    EffectCheckUpdateResult effectCheckUpdateResult = (EffectCheckUpdateResult) obj5;
                    ExceptionResult exception5 = effectCheckUpdateResult.getException();
                    ICheckChannelListener checkChannelListener = this.mConfiguration.getListenerManger().getCheckChannelListener(effectCheckUpdateResult.getTaskID());
                    if (checkChannelListener == null) {
                        return;
                    }
                    if (exception5 == null) {
                        checkChannelListener.checkChannelSuccess(effectCheckUpdateResult.isUpdate());
                    } else {
                        checkChannelListener.checkChannelFailed(exception5);
                    }
                }
            }
        }
    }

    public void searchProviderEffectList(@NonNull String str, @Nullable String str2, int i, int i2, String str3) {
        this.mConfiguration.getTaskManager().commit(new SearchProviderEffectTask(this.mEffectContext, str3, str, str2, i, i2, this.mHandler));
    }

    public void setOnEffectListListener(EffectListListener effectListListener) {
        this.mEffectListListener = effectListListener;
    }
}
