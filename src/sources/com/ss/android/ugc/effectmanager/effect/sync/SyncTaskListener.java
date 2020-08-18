package com.ss.android.ugc.effectmanager.effect.sync;

import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;

public interface SyncTaskListener<T> {
    void onFailed(SyncTask<T> syncTask, ExceptionResult exceptionResult);

    void onFinally(SyncTask<T> syncTask);

    void onResponse(SyncTask<T> syncTask, T t);

    void onStart(SyncTask<T> syncTask);
}
