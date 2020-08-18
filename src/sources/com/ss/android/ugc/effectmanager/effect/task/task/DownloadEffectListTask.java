package com.ss.android.ugc.effectmanager.effect.task.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.WeakHandler;
import com.ss.android.ugc.effectmanager.common.listener.ICache;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.task.NormalTask;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.model.Effect;
import com.ss.android.ugc.effectmanager.effect.task.result.EffectListTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.result.EffectTaskResult;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DownloadEffectListTask extends NormalTask implements WeakHandler.IHandler {
    private List<Effect> downloadedList;
    private Queue<Effect> effectList;
    private ICache mCache;
    private Handler mChildrenTaskHandler;
    private EffectConfiguration mConfiguration = this.mEffectContext.getEffectConfiguration();
    private EffectContext mEffectContext;

    public DownloadEffectListTask(EffectContext effectContext, List<Effect> list, String str, Handler handler) {
        super(handler, str, EffectConstants.NETWORK);
        this.mEffectContext = effectContext;
        this.mCache = effectContext.getEffectConfiguration().getCache();
        this.effectList = new ArrayDeque(list);
        this.downloadedList = new ArrayList();
    }

    /* access modifiers changed from: private */
    public void downloadEffect(Effect effect) {
        if (TextUtils.isEmpty(effect.getZipPath()) || TextUtils.isEmpty(effect.getUnzipPath())) {
            effect.setZipPath(this.mConfiguration.getEffectDir() + File.separator + effect.getId() + ".zip");
            StringBuilder sb = new StringBuilder();
            sb.append(this.mConfiguration.getEffectDir());
            sb.append(File.separator);
            sb.append(effect.getId());
            effect.setUnzipPath(sb.toString());
        }
        if (!this.mCache.has(effect.getId())) {
            try {
                new DownloadEffectTask(effect, this.mEffectContext, ((NormalTask) this).taskId, this.mChildrenTaskHandler).execute();
            } catch (RuntimeException e2) {
                throw e2;
            }
        } else {
            EffectTaskResult effectTaskResult = new EffectTaskResult(effect, null);
            Message obtainMessage = this.mChildrenTaskHandler.obtainMessage(15);
            obtainMessage.obj = effectTaskResult;
            obtainMessage.sendToTarget();
        }
    }

    /* access modifiers changed from: package-private */
    public void enqueueEffect(final Effect effect) {
        this.mChildrenTaskHandler.post(new Runnable() {
            /* class com.ss.android.ugc.effectmanager.effect.task.task.DownloadEffectListTask.AnonymousClass1 */

            public void run() {
                DownloadEffectListTask.this.downloadEffect(effect);
            }
        });
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask
    public void execute() {
        Looper myLooper = Looper.myLooper();
        if (myLooper == null) {
            Looper.prepare();
            myLooper = Looper.myLooper();
        }
        try {
            this.mChildrenTaskHandler = new WeakHandler(myLooper, this);
            Effect poll = this.effectList.poll();
            if (poll != null) {
                enqueueEffect(poll);
                Looper.loop();
            }
        } catch (Throwable th) {
            myLooper.quit();
            throw th;
        }
        myLooper.quit();
    }

    @Override // com.ss.android.ugc.effectmanager.common.WeakHandler.IHandler
    public void handleMsg(Message message) {
        if (message.what == 15) {
            EffectTaskResult effectTaskResult = (EffectTaskResult) message.obj;
            Effect effect = effectTaskResult.getEffect();
            ExceptionResult exception = effectTaskResult.getException();
            if (exception != null) {
                sendMessage(17, new EffectListTaskResult(this.downloadedList, exception));
                Looper.myLooper().quit();
                return;
            }
            this.downloadedList.add(effect);
            if (!this.effectList.isEmpty()) {
                enqueueEffect(this.effectList.poll());
                return;
            }
            sendMessage(17, new EffectListTaskResult(this.downloadedList, null));
            Looper.myLooper().quit();
        }
    }
}
