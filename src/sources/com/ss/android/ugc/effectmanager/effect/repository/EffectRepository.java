package com.ss.android.ugc.effectmanager.effect.repository;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.WeakHandler;
import com.ss.android.ugc.effectmanager.common.task.BaseTask;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.listener.IDownloadProviderEffectListener;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectListener;
import com.ss.android.ugc.effectmanager.effect.model.Effect;
import com.ss.android.ugc.effectmanager.effect.model.ProviderEffect;
import com.ss.android.ugc.effectmanager.effect.task.result.DownloadProviderEffectTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.result.EffectListTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.result.EffectTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.task.DownloadEffectListByIdsTask;
import com.ss.android.ugc.effectmanager.effect.task.task.DownloadEffectListTask;
import com.ss.android.ugc.effectmanager.effect.task.task.DownloadEffectTask;
import com.ss.android.ugc.effectmanager.effect.task.task.DownloadProviderEffectTask;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EffectRepository implements WeakHandler.IHandler {
    private EffectConfiguration mConfiguration;
    private EffectContext mEffectContext;
    private Handler mHandler;
    private EffectListener mListener;
    private HashMap<Effect, BaseTask> mTaskMap = new HashMap<>();

    public interface EffectListener {
        void updateEffectListStatus(String str, List<Effect> list, ExceptionResult exceptionResult);

        void updateEffectStatus(String str, Effect effect, int i, ExceptionResult exceptionResult);
    }

    public EffectRepository(EffectContext effectContext) {
        this.mEffectContext = effectContext;
        this.mConfiguration = this.mEffectContext.getEffectConfiguration();
        this.mHandler = new WeakHandler(this);
    }

    public void cancelDownloadEffect(Effect effect) {
        if (this.mTaskMap.containsKey(effect)) {
            this.mTaskMap.get(effect).cancel();
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            this.mTaskMap.remove(effect);
            EffectListener effectListener = this.mListener;
            if (effectListener != null) {
                effectListener.updateEffectStatus("", effect, 22, null);
            }
        }
    }

    public void downloadProviderEffectList(@NonNull ProviderEffect providerEffect, String str) {
        this.mConfiguration.getTaskManager().commit(new DownloadProviderEffectTask(this.mEffectContext, str, providerEffect, this.mHandler));
    }

    public void fetchEffect(Effect effect, String str) {
        DownloadEffectTask downloadEffectTask = new DownloadEffectTask(effect, this.mEffectContext, str, this.mHandler);
        this.mListener.updateEffectStatus("", effect, 21, null);
        this.mTaskMap.put(effect, downloadEffectTask);
        this.mConfiguration.getTaskManager().commit(downloadEffectTask);
    }

    public void fetchEffectList(List<Effect> list, String str) {
        for (Effect effect : list) {
            this.mListener.updateEffectStatus("", effect, 21, null);
        }
        this.mConfiguration.getTaskManager().commit(new DownloadEffectListTask(this.mEffectContext, list, str, this.mHandler));
    }

    public void fetchEffectListById(List<String> list, String str) {
        this.mConfiguration.getTaskManager().commit(new DownloadEffectListByIdsTask(this.mEffectContext, list, this.mHandler, str));
    }

    public void fetchEffectListById(List<String> list, String str, Map<String, String> map) {
        this.mConfiguration.getTaskManager().commit(new DownloadEffectListByIdsTask(this.mEffectContext, list, this.mHandler, str, map));
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    @Override // com.ss.android.ugc.effectmanager.common.WeakHandler.IHandler
    public void handleMsg(Message message) {
        if (this.mListener != null) {
            if (message.what == 15) {
                Object obj = message.obj;
                if (obj instanceof EffectTaskResult) {
                    EffectTaskResult effectTaskResult = (EffectTaskResult) obj;
                    Effect effect = effectTaskResult.getEffect();
                    ExceptionResult exception = effectTaskResult.getException();
                    if (exception == null) {
                        this.mListener.updateEffectStatus(effectTaskResult.getTaskID(), effect, 20, null);
                    } else {
                        this.mListener.updateEffectStatus(effectTaskResult.getTaskID(), effect, 26, exception);
                    }
                    this.mTaskMap.remove(effect);
                }
            }
            if (message.what == 17) {
                Object obj2 = message.obj;
                if (obj2 instanceof EffectListTaskResult) {
                    EffectListTaskResult effectListTaskResult = (EffectListTaskResult) obj2;
                    ExceptionResult exception2 = effectListTaskResult.getException();
                    if (exception2 == null) {
                        this.mListener.updateEffectListStatus(effectListTaskResult.getTaskID(), effectListTaskResult.getEffectList(), null);
                    } else {
                        this.mListener.updateEffectListStatus(effectListTaskResult.getTaskID(), effectListTaskResult.getEffectList(), exception2);
                    }
                }
            }
            if (message.what == 19) {
                Object obj3 = message.obj;
                if (obj3 instanceof DownloadProviderEffectTaskResult) {
                    DownloadProviderEffectTaskResult downloadProviderEffectTaskResult = (DownloadProviderEffectTaskResult) obj3;
                    ExceptionResult exception3 = downloadProviderEffectTaskResult.getException();
                    IDownloadProviderEffectListener downloadProviderEffectListener = this.mConfiguration.getListenerManger().getDownloadProviderEffectListener(downloadProviderEffectTaskResult.getTaskID());
                    if (downloadProviderEffectListener != null) {
                        if (exception3 == null) {
                            downloadProviderEffectListener.onSuccess(downloadProviderEffectTaskResult.getEffect());
                        } else {
                            downloadProviderEffectListener.onFail(downloadProviderEffectTaskResult.getEffect(), downloadProviderEffectTaskResult.getException());
                        }
                    }
                }
            }
            if (message.what == 42) {
                Object obj4 = message.obj;
                if (obj4 instanceof EffectTaskResult) {
                    EffectTaskResult effectTaskResult2 = (EffectTaskResult) obj4;
                    IFetchEffectListener fetchEffectListener = this.mConfiguration.getListenerManger().getFetchEffectListener(effectTaskResult2.getTaskID());
                    if (fetchEffectListener != null) {
                        fetchEffectListener.onStart(effectTaskResult2.getEffect());
                    }
                }
            }
        }
    }

    public void setListener(EffectListener effectListener) {
        this.mListener = effectListener;
    }
}
