package com.ss.android.ugc.effectmanager.effect.repository;

import android.os.Handler;
import android.os.Message;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.WeakHandler;
import com.ss.android.ugc.effectmanager.common.utils.LogUtils;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchFavoriteList;
import com.ss.android.ugc.effectmanager.effect.listener.IModFavoriteList;
import com.ss.android.ugc.effectmanager.effect.task.result.FavoriteTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.result.FetchFavoriteListTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.task.FavoriteTask;
import com.ss.android.ugc.effectmanager.effect.task.task.FetchFavoriteListTask;
import java.util.List;

public class FavoriteRepository implements WeakHandler.IHandler {
    private String TAG = "FavoriteRepository";
    private EffectConfiguration mConfiguration;
    private EffectContext mEffectContext;
    private Handler mHandler;

    public FavoriteRepository(EffectContext effectContext) {
        this.mEffectContext = effectContext;
        this.mConfiguration = this.mEffectContext.getEffectConfiguration();
        this.mHandler = new WeakHandler(this);
    }

    public void fetchFavoriteList(String str, String str2) {
        this.mConfiguration.getTaskManager().commit(new FetchFavoriteListTask(this.mEffectContext, str, str2, this.mHandler));
    }

    @Override // com.ss.android.ugc.effectmanager.common.WeakHandler.IHandler
    public void handleMsg(Message message) {
        int i = message.what;
        if (i == 40) {
            Object obj = message.obj;
            if (obj instanceof FavoriteTaskResult) {
                FavoriteTaskResult favoriteTaskResult = (FavoriteTaskResult) obj;
                IModFavoriteList modFavoriteListListener = this.mConfiguration.getListenerManger().getModFavoriteListListener(favoriteTaskResult.getTaskID());
                if (modFavoriteListListener == null) {
                    return;
                }
                if (favoriteTaskResult.isSuccess()) {
                    modFavoriteListListener.onSuccess(favoriteTaskResult.getEffectIds());
                } else {
                    modFavoriteListListener.onFail(favoriteTaskResult.getException());
                }
            }
        } else if (i != 41) {
            LogUtils.e(this.TAG, "未知错误");
        } else {
            Object obj2 = message.obj;
            if (obj2 instanceof FetchFavoriteListTaskResult) {
                FetchFavoriteListTaskResult fetchFavoriteListTaskResult = (FetchFavoriteListTaskResult) obj2;
                IFetchFavoriteList fetchFavoriteListListener = this.mConfiguration.getListenerManger().getFetchFavoriteListListener(fetchFavoriteListTaskResult.getTaskID());
                if (fetchFavoriteListListener == null) {
                    return;
                }
                if (fetchFavoriteListTaskResult.getException() == null) {
                    fetchFavoriteListListener.onSuccess(fetchFavoriteListTaskResult.getEffects(), fetchFavoriteListTaskResult.getType());
                } else {
                    fetchFavoriteListListener.onFailed(fetchFavoriteListTaskResult.getException());
                }
            }
        }
    }

    public void modFavoriteList(String str, String str2, Boolean bool, String str3) {
        this.mConfiguration.getTaskManager().commit(new FavoriteTask(this.mEffectContext, str, str3, this.mHandler, str2, bool.booleanValue()));
    }

    public void modFavoriteList(String str, List<String> list, Boolean bool, String str2) {
        this.mConfiguration.getTaskManager().commit(new FavoriteTask(this.mEffectContext, str, str2, this.mHandler, list, bool.booleanValue()));
    }
}
