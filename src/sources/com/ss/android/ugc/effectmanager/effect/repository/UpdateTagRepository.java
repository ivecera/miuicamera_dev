package com.ss.android.ugc.effectmanager.effect.repository;

import android.os.Handler;
import android.os.Message;
import com.ss.android.ugc.effectmanager.common.WeakHandler;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.utils.LogUtils;
import com.ss.android.ugc.effectmanager.common.utils.ValueConvertUtil;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.listener.IIsTagNeedUpdatedListener;
import com.ss.android.ugc.effectmanager.effect.listener.IReadUpdateTagListener;
import com.ss.android.ugc.effectmanager.effect.listener.IUpdateTagListener;
import com.ss.android.ugc.effectmanager.effect.listener.IWriteUpdateTagListener;
import com.ss.android.ugc.effectmanager.effect.task.result.ReadTagTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.result.WriteTagTaskResult;
import com.ss.android.ugc.effectmanager.effect.task.task.ReadUpdateTagTask;
import com.ss.android.ugc.effectmanager.effect.task.task.WriteUpdateTagTask;
import java.util.HashMap;

public class UpdateTagRepository implements WeakHandler.IHandler {
    private final String TAG = "UpdateTagRepository";
    private EffectContext mEffectContext;
    private Handler mHandler;
    private HashMap<String, String> mTagsCachedMap;

    public UpdateTagRepository(EffectContext effectContext) {
        this.mEffectContext = effectContext;
        this.mHandler = new WeakHandler(this);
    }

    /* access modifiers changed from: private */
    public void checkedTagInHashMap(String str, String str2, IIsTagNeedUpdatedListener iIsTagNeedUpdatedListener) {
        HashMap<String, String> hashMap = this.mTagsCachedMap;
        if (hashMap == null) {
            iIsTagNeedUpdatedListener.onTagNeedUpdate();
        } else if (!hashMap.containsKey(str)) {
            iIsTagNeedUpdatedListener.onTagNeedUpdate();
        } else if (ValueConvertUtil.ConvertStringToLong(str2, -1) > ValueConvertUtil.ConvertStringToLong(this.mTagsCachedMap.get(str), -1)) {
            iIsTagNeedUpdatedListener.onTagNeedUpdate();
        } else {
            iIsTagNeedUpdatedListener.onTagNeedNotUpdate();
        }
    }

    /* access modifiers changed from: private */
    public void requestWriteTask(String str, String str2, String str3, final IUpdateTagListener iUpdateTagListener) {
        if (this.mEffectContext != null) {
            if (this.mTagsCachedMap == null) {
                iUpdateTagListener.onFinally();
            }
            this.mTagsCachedMap.put(str2, str3);
            this.mEffectContext.getEffectConfiguration().getListenerManger().setWriteUpdateTagListener(str, new IWriteUpdateTagListener() {
                /* class com.ss.android.ugc.effectmanager.effect.repository.UpdateTagRepository.AnonymousClass1 */

                @Override // com.ss.android.ugc.effectmanager.effect.listener.IWriteUpdateTagListener
                public void onFinally() {
                    IUpdateTagListener iUpdateTagListener = iUpdateTagListener;
                    if (iUpdateTagListener != null) {
                        iUpdateTagListener.onFinally();
                    }
                }
            });
            this.mEffectContext.getEffectConfiguration().getTaskManager().commit(new WriteUpdateTagTask(this.mHandler, this.mEffectContext, str, this.mTagsCachedMap));
        } else if (iUpdateTagListener != null) {
            iUpdateTagListener.onFinally();
        }
    }

    @Override // com.ss.android.ugc.effectmanager.common.WeakHandler.IHandler
    public void handleMsg(Message message) {
        int i = message.what;
        if (i == 51) {
            Object obj = message.obj;
            if (obj instanceof WriteTagTaskResult) {
                WriteTagTaskResult writeTagTaskResult = (WriteTagTaskResult) obj;
                EffectContext effectContext = this.mEffectContext;
                if (effectContext != null) {
                    IWriteUpdateTagListener writeUpdateTagListener = effectContext.getEffectConfiguration().getListenerManger().getWriteUpdateTagListener(writeTagTaskResult.getTaskID());
                    if (writeUpdateTagListener != null) {
                        writeUpdateTagListener.onFinally();
                    }
                    this.mEffectContext.getEffectConfiguration().getListenerManger().removeWriteUpdateTagListener(writeTagTaskResult.getTaskID());
                }
            }
        } else if (i != 52) {
            LogUtils.e("UpdateTagRepository", "未知错误");
        } else {
            Object obj2 = message.obj;
            if (obj2 instanceof ReadTagTaskResult) {
                ReadTagTaskResult readTagTaskResult = (ReadTagTaskResult) obj2;
                EffectContext effectContext2 = this.mEffectContext;
                if (effectContext2 != null) {
                    IReadUpdateTagListener readUpdateTagListener = effectContext2.getEffectConfiguration().getListenerManger().getReadUpdateTagListener(readTagTaskResult.getTaskID());
                    if (this.mTagsCachedMap == null) {
                        this.mTagsCachedMap = new HashMap<>();
                    }
                    if (readTagTaskResult.getException() == null) {
                        this.mTagsCachedMap.putAll(readTagTaskResult.getTagsCachedMap());
                        if (readUpdateTagListener != null) {
                            readUpdateTagListener.onSuccess();
                        }
                    } else if (readUpdateTagListener != null) {
                        readUpdateTagListener.onFailed(readTagTaskResult.getException());
                    }
                    if (readUpdateTagListener != null) {
                        readUpdateTagListener.onFinally();
                    }
                    this.mEffectContext.getEffectConfiguration().getListenerManger().removeReadUpdateTagListener(readTagTaskResult.getTaskID());
                }
            }
        }
    }

    public void isTagUpdated(String str, final String str2, final String str3, final IIsTagNeedUpdatedListener iIsTagNeedUpdatedListener) {
        if (this.mTagsCachedMap == null) {
            EffectContext effectContext = this.mEffectContext;
            if (effectContext != null) {
                effectContext.getEffectConfiguration().getListenerManger().setReadUpdateTagListener(str, new IReadUpdateTagListener() {
                    /* class com.ss.android.ugc.effectmanager.effect.repository.UpdateTagRepository.AnonymousClass3 */

                    @Override // com.ss.android.ugc.effectmanager.effect.listener.IReadUpdateTagListener
                    public void onFailed(ExceptionResult exceptionResult) {
                        iIsTagNeedUpdatedListener.onTagNeedUpdate();
                    }

                    @Override // com.ss.android.ugc.effectmanager.effect.listener.IReadUpdateTagListener
                    public void onFinally() {
                    }

                    @Override // com.ss.android.ugc.effectmanager.effect.listener.IReadUpdateTagListener
                    public void onSuccess() {
                        UpdateTagRepository.this.checkedTagInHashMap(str2, str3, iIsTagNeedUpdatedListener);
                    }
                });
                this.mEffectContext.getEffectConfiguration().getTaskManager().commit(new ReadUpdateTagTask(this.mHandler, this.mEffectContext, str, str2, str3));
            } else if (iIsTagNeedUpdatedListener != null) {
                iIsTagNeedUpdatedListener.onTagNeedNotUpdate();
            }
        } else {
            checkedTagInHashMap(str2, str3, iIsTagNeedUpdatedListener);
        }
    }

    public void updateTag(final String str, final String str2, final String str3, final IUpdateTagListener iUpdateTagListener) {
        if (this.mTagsCachedMap == null) {
            EffectContext effectContext = this.mEffectContext;
            if (effectContext != null) {
                effectContext.getEffectConfiguration().getListenerManger().setReadUpdateTagListener(str, new IReadUpdateTagListener() {
                    /* class com.ss.android.ugc.effectmanager.effect.repository.UpdateTagRepository.AnonymousClass2 */

                    @Override // com.ss.android.ugc.effectmanager.effect.listener.IReadUpdateTagListener
                    public void onFailed(ExceptionResult exceptionResult) {
                    }

                    @Override // com.ss.android.ugc.effectmanager.effect.listener.IReadUpdateTagListener
                    public void onFinally() {
                        UpdateTagRepository.this.requestWriteTask(str, str2, str3, iUpdateTagListener);
                    }

                    @Override // com.ss.android.ugc.effectmanager.effect.listener.IReadUpdateTagListener
                    public void onSuccess() {
                    }
                });
                this.mEffectContext.getEffectConfiguration().getTaskManager().commit(new ReadUpdateTagTask(this.mHandler, this.mEffectContext, str, str2, str3));
            } else if (iUpdateTagListener != null) {
                iUpdateTagListener.onFinally();
            }
        } else {
            requestWriteTask(str, str2, str3, iUpdateTagListener);
        }
    }
}
