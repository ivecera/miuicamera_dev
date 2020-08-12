package com.ss.android.ugc.effectmanager.effect.bridge;

import com.ss.android.ugc.effectmanager.effect.sync.SyncTask;
import com.ss.android.ugc.effectmanager.effect.task.result.EffectTaskResult;

public interface EffectFetcher {
    SyncTask<EffectTaskResult> fetchEffect(EffectFetcherArguments effectFetcherArguments);
}
