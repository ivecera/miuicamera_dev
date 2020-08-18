package com.ss.android.ugc.effectmanager.effect.sync;

import android.support.annotation.NonNull;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;

public class SyncTask<T> {
    private boolean mCanceled;
    private SyncTaskListener<T> mSyncTaskListener;

    public void cancel() {
        this.mCanceled = true;
    }

    public void execute() {
    }

    public boolean isCanceled() {
        return this.mCanceled;
    }

    public void onFailed(SyncTask<T> syncTask, ExceptionResult exceptionResult) {
        SyncTaskListener<T> syncTaskListener = this.mSyncTaskListener;
        if (syncTaskListener != null) {
            syncTaskListener.onFailed(syncTask, exceptionResult);
        }
    }

    public void onFinally(SyncTask<T> syncTask) {
        SyncTaskListener<T> syncTaskListener = this.mSyncTaskListener;
        if (syncTaskListener != null) {
            syncTaskListener.onFinally(syncTask);
        }
    }

    public void onResponse(SyncTask<T> syncTask, T t) {
        SyncTaskListener<T> syncTaskListener = this.mSyncTaskListener;
        if (syncTaskListener != null) {
            syncTaskListener.onResponse(syncTask, t);
        }
    }

    public void onStart(SyncTask<T> syncTask) {
        SyncTaskListener<T> syncTaskListener = this.mSyncTaskListener;
        if (syncTaskListener != null) {
            syncTaskListener.onStart(syncTask);
        }
    }

    public void setListener(@NonNull SyncTaskListener<T> syncTaskListener) {
        this.mSyncTaskListener = syncTaskListener;
    }
}
