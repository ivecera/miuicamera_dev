package com.ss.android.ugc.effectmanager.effect.task.task;

import android.os.Handler;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.task.NormalTask;
import com.ss.android.ugc.effectmanager.common.utils.EffectUtils;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.bridge.EffectFetcherArguments;
import com.ss.android.ugc.effectmanager.effect.model.Effect;
import com.ss.android.ugc.effectmanager.effect.sync.SyncTask;
import com.ss.android.ugc.effectmanager.effect.sync.SyncTaskListener;
import com.ss.android.ugc.effectmanager.effect.task.result.EffectTaskResult;
import java.util.List;

public class DownloadEffectTask extends NormalTask {
    private EffectConfiguration mConfiguration;
    private int mCurCnt;
    private List<String> mDownLoadUrl = EffectUtils.getUrl(this.mEffect.getFileUrl());
    /* access modifiers changed from: private */
    public Effect mEffect;
    private EffectContext mEffectContext;

    public DownloadEffectTask(Effect effect, EffectContext effectContext, String str, Handler handler) {
        super(handler, str, EffectConstants.NETWORK);
        this.mEffect = effect;
        this.mEffectContext = effectContext;
        this.mConfiguration = effectContext.getEffectConfiguration();
        this.mCurCnt = effectContext.getEffectConfiguration().getRetryCount();
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask
    public void execute() {
        SyncTask<EffectTaskResult> fetchEffect = this.mEffectContext.getEffectConfiguration().getEffectFetcher().fetchEffect(new EffectFetcherArguments(this.mEffect, this.mDownLoadUrl, this.mConfiguration.getEffectDir().getPath()));
        fetchEffect.setListener(new SyncTaskListener<EffectTaskResult>() {
            /* class com.ss.android.ugc.effectmanager.effect.task.task.DownloadEffectTask.AnonymousClass1 */

            @Override // com.ss.android.ugc.effectmanager.effect.sync.SyncTaskListener
            public void onFailed(SyncTask<EffectTaskResult> syncTask, ExceptionResult exceptionResult) {
                DownloadEffectTask downloadEffectTask = DownloadEffectTask.this;
                downloadEffectTask.sendMessage(15, new EffectTaskResult(downloadEffectTask.mEffect, exceptionResult));
            }

            @Override // com.ss.android.ugc.effectmanager.effect.sync.SyncTaskListener
            public void onFinally(SyncTask<EffectTaskResult> syncTask) {
            }

            public void onResponse(SyncTask<EffectTaskResult> syncTask, EffectTaskResult effectTaskResult) {
                DownloadEffectTask.this.sendMessage(15, new EffectTaskResult(effectTaskResult.getEffect(), null));
            }

            @Override // com.ss.android.ugc.effectmanager.effect.sync.SyncTaskListener
            public void onStart(SyncTask<EffectTaskResult> syncTask) {
                DownloadEffectTask downloadEffectTask = DownloadEffectTask.this;
                downloadEffectTask.sendMessage(42, new EffectTaskResult(downloadEffectTask.mEffect, null));
            }
        });
        fetchEffect.execute();
    }
}
