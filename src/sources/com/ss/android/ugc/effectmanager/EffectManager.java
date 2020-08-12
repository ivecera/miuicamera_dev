package com.ss.android.ugc.effectmanager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.ErrorConstants;
import com.ss.android.ugc.effectmanager.common.Preconditions;
import com.ss.android.ugc.effectmanager.common.SimpleThreadFactory;
import com.ss.android.ugc.effectmanager.common.TaskManager;
import com.ss.android.ugc.effectmanager.common.cache.FileCache;
import com.ss.android.ugc.effectmanager.common.listener.ICache;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.utils.EffectCacheKeyGenerator;
import com.ss.android.ugc.effectmanager.common.utils.EffectUtils;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.listener.ICheckChannelListener;
import com.ss.android.ugc.effectmanager.effect.listener.IDownloadProviderEffectListener;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchCategoryEffectListener;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectChannelListener;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectListListener;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectListener;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchFavoriteList;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchPanelInfoListener;
import com.ss.android.ugc.effectmanager.effect.listener.IFetchProviderEffect;
import com.ss.android.ugc.effectmanager.effect.listener.IIsTagNeedUpdatedListener;
import com.ss.android.ugc.effectmanager.effect.listener.IModFavoriteList;
import com.ss.android.ugc.effectmanager.effect.listener.IUpdateTagListener;
import com.ss.android.ugc.effectmanager.effect.model.Effect;
import com.ss.android.ugc.effectmanager.effect.model.EffectCategoryResponse;
import com.ss.android.ugc.effectmanager.effect.model.EffectChannelResponse;
import com.ss.android.ugc.effectmanager.effect.model.ProviderEffect;
import com.ss.android.ugc.effectmanager.effect.repository.EffectChannelRepository;
import com.ss.android.ugc.effectmanager.effect.repository.EffectRepository;
import com.ss.android.ugc.effectmanager.effect.repository.EffectStore;
import com.ss.android.ugc.effectmanager.effect.repository.FavoriteRepository;
import com.ss.android.ugc.effectmanager.effect.repository.UpdateTagRepository;
import com.ss.android.ugc.effectmanager.link.LinkSelector;
import com.ss.android.ugc.effectmanager.link.model.configuration.LinkSelectorConfiguration;
import com.ss.android.ugc.effectmanager.link.model.host.Host;
import com.ss.android.ugc.effectmanager.network.interceptor.LinkSelectorInterceptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

public class EffectManager {
    private static final String SDK_TAG = "EffectManager";
    ICache mCache;
    EffectChannelRepository mEffectChannelRepository;
    EffectContext mEffectContext;
    EffectRepository mEffectRepository;
    EffectStore mEffectStore;
    FavoriteRepository mFavoriteRepository;
    boolean mInited = false;
    LinkSelector mLinkSelector;
    UpdateTagRepository mUpdateTagRepository;

    private boolean checkConfiguration(EffectConfiguration effectConfiguration) {
        if (effectConfiguration == null) {
            Log.e(SDK_TAG, ErrorConstants.LOG_CONFIGURATION_NOT_SET);
        } else if (effectConfiguration.getLinkSelectorConfiguration().getOriginHosts() == null || effectConfiguration.getLinkSelectorConfiguration().getOriginHosts().isEmpty()) {
            Log.e(SDK_TAG, ErrorConstants.LOG_HOST_NOT_SET);
        } else if (effectConfiguration.getLinkSelectorConfiguration().getContext() == null) {
            Log.e(SDK_TAG, ErrorConstants.LOG_CONTEXT_NOT_SET);
        } else if (effectConfiguration.getJsonConverter() == null) {
            Log.e(SDK_TAG, ErrorConstants.LOG_JSON_CONCERT_NOT_SET);
        } else if (effectConfiguration.getEffectNetWorker() == null) {
            Log.e(SDK_TAG, ErrorConstants.LOG_NET_WORKER_NOT_SET);
        } else if (effectConfiguration.getEffectDir() != null && effectConfiguration.getEffectDir().exists()) {
            return true;
        } else {
            Log.e(SDK_TAG, ErrorConstants.LOG_CACHE_DIR_NOT_SET);
        }
        return false;
    }

    private void checkUpdate(String str, @Nullable String str2, int i, ICheckChannelListener iCheckChannelListener) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setCheckChannelListener(currentTaskID, iCheckChannelListener);
            this.mEffectChannelRepository.checkUpdate(str, str2, i, currentTaskID);
        } else if (iCheckChannelListener != null) {
            iCheckChannelListener.checkChannelFailed(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    /* access modifiers changed from: private */
    public EffectChannelResponse divideEffectList(EffectChannelResponse effectChannelResponse, List<Effect> list) {
        effectChannelResponse.setAllCategoryEffects(list);
        for (EffectCategoryResponse effectCategoryResponse : effectChannelResponse.getCategoryResponseList()) {
            ArrayList arrayList = new ArrayList();
            for (Effect effect : effectCategoryResponse.getTotalEffects()) {
                if (list.contains(effect)) {
                    arrayList.add(effect);
                }
            }
            effectCategoryResponse.setTotalEffects(arrayList);
        }
        return effectChannelResponse;
    }

    /* access modifiers changed from: private */
    public void downloadEffectList(List<Effect> list, IFetchEffectListListener iFetchEffectListListener) {
        if (this.mEffectContext != null && this.mEffectRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchEffectListListener(currentTaskID, iFetchEffectListListener);
            this.mEffectRepository.fetchEffectList(list, currentTaskID);
        } else if (iFetchEffectListListener != null) {
            iFetchEffectListListener.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    /* access modifiers changed from: private */
    public List<Effect> getNeedDownloadEffectList(List<Effect> list) {
        ArrayList arrayList = new ArrayList();
        List<Effect> currentDownloadingEffectList = this.mEffectStore.getCurrentDownloadingEffectList();
        for (Effect effect : list) {
            if (!currentDownloadingEffectList.contains(effect)) {
                arrayList.add(effect);
            }
        }
        return arrayList;
    }

    private void initCache() {
        if (this.mEffectContext.getEffectConfiguration().getCache() == null) {
            this.mCache = new FileCache(this.mEffectContext.getEffectConfiguration());
            this.mEffectContext.getEffectConfiguration().setCache(this.mCache);
            return;
        }
        this.mCache = this.mEffectContext.getEffectConfiguration().getCache();
    }

    private void initRepository() {
        this.mEffectStore = new EffectStore(this.mEffectContext.getEffectConfiguration());
        this.mEffectChannelRepository = new EffectChannelRepository(this.mEffectContext);
        this.mEffectRepository = new EffectRepository(this.mEffectContext);
        this.mFavoriteRepository = new FavoriteRepository(this.mEffectContext);
        this.mEffectChannelRepository.setOnEffectListListener(new EffectChannelRepository.EffectListListener() {
            /* class com.ss.android.ugc.effectmanager.EffectManager.AnonymousClass1 */

            @Override // com.ss.android.ugc.effectmanager.effect.repository.EffectChannelRepository.EffectListListener
            public void updateEffectChannel(String str, EffectChannelResponse effectChannelResponse, int i, ExceptionResult exceptionResult) {
                EffectManager.this.mEffectStore.updateEffectChannel(str, effectChannelResponse, i, exceptionResult);
            }
        });
        this.mEffectRepository.setListener(new EffectRepository.EffectListener() {
            /* class com.ss.android.ugc.effectmanager.EffectManager.AnonymousClass2 */

            @Override // com.ss.android.ugc.effectmanager.effect.repository.EffectRepository.EffectListener
            public void updateEffectListStatus(String str, List<Effect> list, ExceptionResult exceptionResult) {
                EffectManager.this.mEffectStore.updateEffectListDownloadStatus(str, list, exceptionResult);
            }

            @Override // com.ss.android.ugc.effectmanager.effect.repository.EffectRepository.EffectListener
            public void updateEffectStatus(String str, Effect effect, int i, ExceptionResult exceptionResult) {
                EffectManager.this.mEffectStore.updateEffectDownloadStatus(str, effect, i, exceptionResult);
            }
        });
        this.mUpdateTagRepository = new UpdateTagRepository(this.mEffectContext);
    }

    private void initTaskManager() {
        TaskManager taskManager = new TaskManager();
        taskManager.init(new TaskManager.TaskManagerConfig().setExecutor(Executors.newCachedThreadPool(new SimpleThreadFactory(SDK_TAG, true))).setEffectContext(this.mEffectContext));
        LinkSelectorInterceptor linkSelectorInterceptor = new LinkSelectorInterceptor(this.mLinkSelector);
        linkSelectorInterceptor.enable(true);
        taskManager.addInterception(EffectConstants.LINK_SELECTOR, linkSelectorInterceptor);
        this.mEffectContext.getEffectConfiguration().setTaskManager(taskManager);
    }

    private void linkSelectorStart() {
        this.mLinkSelector.startOptHosts();
    }

    private boolean needLinkSelector(EffectConfiguration effectConfiguration) {
        LinkSelectorConfiguration linkSelectorConfiguration;
        return effectConfiguration != null && (linkSelectorConfiguration = effectConfiguration.getLinkSelectorConfiguration()) != null && linkSelectorConfiguration.getOriginHosts().size() > 1 && linkSelectorConfiguration.isNetworkChangeMonitor();
    }

    public void checkCategoryIsUpdate(String str, @NonNull String str2, ICheckChannelListener iCheckChannelListener) {
        checkUpdate(str, str2, 1, iCheckChannelListener);
    }

    public void checkPanelIsUpdate(String str, ICheckChannelListener iCheckChannelListener) {
        checkUpdate(str, null, 2, iCheckChannelListener);
    }

    public void checkedEffectListUpdate(String str, ICheckChannelListener iCheckChannelListener) {
        checkUpdate(str, null, 0, iCheckChannelListener);
    }

    public void clearCache(String str) {
        this.mCache.removePattern(EffectCacheKeyGenerator.generatePattern(str));
    }

    public void clearEffects() {
        this.mCache.clear();
    }

    public void clearVersion(String str) {
        ICache iCache = this.mCache;
        iCache.remove("effect_version" + str);
    }

    public void deleteEffect(Effect effect) {
        if (effect != null) {
            this.mCache.remove(effect.getId());
            ICache iCache = this.mCache;
            iCache.remove(effect.getId() + ".zip");
        }
    }

    public void destroy() {
        EffectContext effectContext;
        if (this.mInited && (effectContext = this.mEffectContext) != null) {
            effectContext.getEffectConfiguration().getTaskManager().destroy();
            this.mEffectContext.getEffectConfiguration().getListenerManger().destroy();
            this.mLinkSelector.destroy();
            this.mInited = false;
        }
    }

    public void downloadProviderEffect(@NonNull ProviderEffect providerEffect, IDownloadProviderEffectListener iDownloadProviderEffectListener) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setDownloadProviderEffectListener(currentTaskID, iDownloadProviderEffectListener);
            this.mEffectRepository.downloadProviderEffectList(providerEffect, currentTaskID);
        } else if (iDownloadProviderEffectListener != null) {
            iDownloadProviderEffectListener.onFail(providerEffect, new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchCategoryEffect(String str, String str2, int i, int i2, int i3, String str3, IFetchCategoryEffectListener iFetchCategoryEffectListener) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchCategoryEffectListener(currentTaskID, iFetchCategoryEffectListener);
            this.mEffectChannelRepository.fetchCategoryEffect(str, currentTaskID, str2, i, i2, i3, str3, false);
        } else if (iFetchCategoryEffectListener != null) {
            iFetchCategoryEffectListener.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchCategoryEffectFromCache(String str, String str2, int i, int i2, int i3, String str3, IFetchCategoryEffectListener iFetchCategoryEffectListener) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchCategoryEffectListener(currentTaskID, iFetchCategoryEffectListener);
            this.mEffectChannelRepository.fetchCategoryEffect(str, currentTaskID, str2, i, i2, i3, str3, true);
        } else if (iFetchCategoryEffectListener != null) {
            iFetchCategoryEffectListener.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchEffect(Effect effect, IFetchEffectListener iFetchEffectListener) {
        if (this.mEffectContext != null && this.mEffectRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchEffectListener(currentTaskID, iFetchEffectListener);
            if (!isEffectDownloading(effect)) {
                this.mEffectRepository.fetchEffect(effect, currentTaskID);
            }
        } else if (iFetchEffectListener != null) {
            iFetchEffectListener.onFail(effect, new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchEffect(String str, IFetchEffectListener iFetchEffectListener) {
        fetchEffectWithDownload(str, null, iFetchEffectListener);
    }

    public void fetchEffectList(String str, final boolean z, final IFetchEffectChannelListener iFetchEffectChannelListener) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            AnonymousClass3 r0 = new IFetchEffectChannelListener() {
                /* class com.ss.android.ugc.effectmanager.EffectManager.AnonymousClass3 */

                @Override // com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectChannelListener
                public void onFail(ExceptionResult exceptionResult) {
                    IFetchEffectChannelListener iFetchEffectChannelListener = iFetchEffectChannelListener;
                    if (iFetchEffectChannelListener != null) {
                        iFetchEffectChannelListener.onFail(exceptionResult);
                    }
                }

                @Override // com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectChannelListener
                public void onSuccess(final EffectChannelResponse effectChannelResponse) {
                    if (z) {
                        final String generatePanelKey = EffectCacheKeyGenerator.generatePanelKey(EffectManager.this.mEffectContext.getEffectConfiguration().getChannel(), effectChannelResponse.getPanel());
                        final String queryToString = EffectManager.this.mCache.queryToString(generatePanelKey);
                        EffectManager.this.mCache.remove(generatePanelKey);
                        EffectManager.this.downloadEffectList(EffectManager.this.getNeedDownloadEffectList(effectChannelResponse.getAllCategoryEffects()), new IFetchEffectListListener() {
                            /* class com.ss.android.ugc.effectmanager.EffectManager.AnonymousClass3.AnonymousClass1 */

                            @Override // com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectListListener
                            public void onFail(ExceptionResult exceptionResult) {
                                IFetchEffectChannelListener iFetchEffectChannelListener = iFetchEffectChannelListener;
                                if (iFetchEffectChannelListener != null) {
                                    iFetchEffectChannelListener.onFail(exceptionResult);
                                }
                            }

                            @Override // com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectListListener
                            public void onSuccess(List<Effect> list) {
                                EffectManager effectManager = EffectManager.this;
                                EffectChannelResponse effectChannelResponse = effectChannelResponse;
                                EffectChannelResponse unused = effectManager.divideEffectList(effectChannelResponse, list);
                                IFetchEffectChannelListener iFetchEffectChannelListener = iFetchEffectChannelListener;
                                if (iFetchEffectChannelListener != null) {
                                    iFetchEffectChannelListener.onSuccess(effectChannelResponse);
                                }
                                EffectManager.this.mCache.save(generatePanelKey, queryToString);
                            }
                        });
                        return;
                    }
                    IFetchEffectChannelListener iFetchEffectChannelListener = iFetchEffectChannelListener;
                    if (iFetchEffectChannelListener != null) {
                        iFetchEffectChannelListener.onSuccess(effectChannelResponse);
                    }
                }
            };
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchEffectChannelListener(currentTaskID, r0);
            if (!TextUtils.isEmpty(str)) {
                this.mEffectChannelRepository.fetchList(str, currentTaskID, false);
            } else {
                this.mEffectChannelRepository.fetchList("default", currentTaskID, false);
            }
        } else if (iFetchEffectChannelListener != null) {
            iFetchEffectChannelListener.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchEffectList(List<String> list, IFetchEffectListListener iFetchEffectListListener) {
        fetchEffectList(list, true, null, iFetchEffectListListener);
    }

    public void fetchEffectList(List<String> list, boolean z, IFetchEffectListListener iFetchEffectListListener) {
        fetchEffectList(list, z, null, iFetchEffectListListener);
    }

    public void fetchEffectList(List<String> list, final boolean z, Map<String, String> map, final IFetchEffectListListener iFetchEffectListListener) {
        if (this.mEffectContext != null && this.mEffectRepository != null) {
            AnonymousClass5 r0 = new IFetchEffectListListener() {
                /* class com.ss.android.ugc.effectmanager.EffectManager.AnonymousClass5 */

                @Override // com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectListListener
                public void onFail(ExceptionResult exceptionResult) {
                    iFetchEffectListListener.onFail(exceptionResult);
                }

                @Override // com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectListListener
                public void onSuccess(List<Effect> list) {
                    if (z) {
                        EffectManager.this.downloadEffectList(list, iFetchEffectListListener);
                    } else {
                        iFetchEffectListListener.onSuccess(list);
                    }
                }
            };
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchEffectListListener(currentTaskID, r0);
            this.mEffectRepository.fetchEffectListById(list, currentTaskID, map);
        } else if (iFetchEffectListListener != null) {
            iFetchEffectListListener.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchEffectListFromCache(String str, IFetchEffectChannelListener iFetchEffectChannelListener) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchEffectChannelListener(currentTaskID, iFetchEffectChannelListener);
            if (!TextUtils.isEmpty(str)) {
                this.mEffectChannelRepository.fetchList(str, currentTaskID, true);
            } else {
                this.mEffectChannelRepository.fetchList("default", currentTaskID, true);
            }
        } else if (iFetchEffectChannelListener != null) {
            iFetchEffectChannelListener.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchEffectWithDownload(String str, Map<String, String> map, final IFetchEffectListener iFetchEffectListener) {
        if (this.mEffectContext != null) {
            AnonymousClass4 r0 = new IFetchEffectListListener() {
                /* class com.ss.android.ugc.effectmanager.EffectManager.AnonymousClass4 */

                @Override // com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectListListener
                public void onFail(ExceptionResult exceptionResult) {
                    iFetchEffectListener.onFail(null, exceptionResult);
                }

                @Override // com.ss.android.ugc.effectmanager.effect.listener.IFetchEffectListListener
                public void onSuccess(List<Effect> list) {
                    if (!list.isEmpty()) {
                        iFetchEffectListener.onSuccess(list.get(0));
                    } else {
                        iFetchEffectListener.onFail(null, new ExceptionResult(1));
                    }
                }
            };
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            fetchEffectList(arrayList, true, map, r0);
        } else if (iFetchEffectListener != null) {
            iFetchEffectListener.onFail(null, new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchExistEffectList(String str, IFetchEffectChannelListener iFetchEffectChannelListener) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchEffectChannelListener(currentTaskID, iFetchEffectChannelListener);
            if (!TextUtils.isEmpty(str)) {
                this.mEffectChannelRepository.fetchExistEffectList(str, currentTaskID);
            } else {
                this.mEffectChannelRepository.fetchExistEffectList("default", currentTaskID);
            }
        } else if (iFetchEffectChannelListener != null) {
            iFetchEffectChannelListener.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchFavoriteList(String str, IFetchFavoriteList iFetchFavoriteList) {
        if (this.mEffectContext != null && this.mFavoriteRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchFavoriteListListener(currentTaskID, iFetchFavoriteList);
            this.mFavoriteRepository.fetchFavoriteList(str, currentTaskID);
        } else if (iFetchFavoriteList != null) {
            iFetchFavoriteList.onFailed(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchPanelInfo(String str, boolean z, String str2, int i, int i2, IFetchPanelInfoListener iFetchPanelInfoListener) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchPanelInfoListener(currentTaskID, iFetchPanelInfoListener);
            this.mEffectChannelRepository.fetchPanelInfo(str, currentTaskID, z, str2, i, i2, false, iFetchPanelInfoListener);
        } else if (iFetchPanelInfoListener != null) {
            iFetchPanelInfoListener.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchPanelInfoFromCache(String str, boolean z, String str2, int i, int i2, IFetchPanelInfoListener iFetchPanelInfoListener) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchPanelInfoListener(currentTaskID, iFetchPanelInfoListener);
            this.mEffectChannelRepository.fetchPanelInfo(str, currentTaskID, z, str2, i, i2, true, iFetchPanelInfoListener);
        } else if (iFetchPanelInfoListener != null) {
            iFetchPanelInfoListener.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void fetchProviderEffect(@Nullable String str, boolean z, int i, int i2, IFetchProviderEffect iFetchProviderEffect) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchProviderEffectListener(currentTaskID, iFetchProviderEffect);
            this.mEffectChannelRepository.fetchProviderEffectList(str, i, i2, currentTaskID);
        } else if (iFetchProviderEffect != null) {
            iFetchProviderEffect.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public EffectChannelResponse getCurrentEffectChannel() {
        EffectStore effectStore = this.mEffectStore;
        if (effectStore == null) {
            return null;
        }
        return effectStore.getCurrentEffectChannel();
    }

    /* access modifiers changed from: package-private */
    public String getCurrentTaskID() {
        return UUID.randomUUID().toString();
    }

    public boolean init(EffectConfiguration effectConfiguration) {
        if (needLinkSelector(effectConfiguration)) {
            Preconditions.checkUiThread();
        }
        if (!checkConfiguration(effectConfiguration)) {
            return false;
        }
        this.mEffectContext = new EffectContext(effectConfiguration);
        this.mLinkSelector = this.mEffectContext.getLinkSelector();
        initTaskManager();
        initRepository();
        initCache();
        this.mEffectContext.getEffectConfiguration().getEffectNetWorker().setLinkSelector(this.mLinkSelector);
        this.mInited = true;
        if (!this.mLinkSelector.isLazy()) {
            linkSelectorStart();
        }
        return true;
    }

    public boolean isEffectDownloaded(Effect effect) {
        return this.mEffectStore != null && EffectUtils.isEffectValid(effect) && this.mEffectStore.isDownloaded(effect);
    }

    public boolean isEffectDownloading(Effect effect) {
        return this.mEffectStore != null && EffectUtils.isEffectValid(effect) && this.mEffectStore.isDownloading(effect);
    }

    public void isTagUpdated(String str, String str2, IIsTagNeedUpdatedListener iIsTagNeedUpdatedListener) {
        if (this.mUpdateTagRepository == null) {
            iIsTagNeedUpdatedListener.onTagNeedNotUpdate();
            return;
        }
        this.mUpdateTagRepository.isTagUpdated(getCurrentTaskID(), str, str2, iIsTagNeedUpdatedListener);
    }

    public void modifyFavoriteList(String str, String str2, Boolean bool, IModFavoriteList iModFavoriteList) {
        if (this.mEffectContext != null && this.mFavoriteRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setModFavoriteListener(currentTaskID, iModFavoriteList);
            this.mFavoriteRepository.modFavoriteList(str, str2, bool, currentTaskID);
        } else if (iModFavoriteList != null) {
            iModFavoriteList.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void modifyFavoriteList(String str, List<String> list, Boolean bool, IModFavoriteList iModFavoriteList) {
        if (this.mEffectContext != null && this.mFavoriteRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setModFavoriteListener(currentTaskID, iModFavoriteList);
            this.mFavoriteRepository.modFavoriteList(str, list, bool, currentTaskID);
        } else if (iModFavoriteList != null) {
            iModFavoriteList.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void removeListener() {
        EffectContext effectContext = this.mEffectContext;
        if (effectContext != null) {
            effectContext.getEffectConfiguration().getListenerManger().destroy();
        }
    }

    public void searchProviderEffect(@NonNull String str, @Nullable String str2, int i, int i2, boolean z, IFetchProviderEffect iFetchProviderEffect) {
        if (this.mEffectContext != null && this.mEffectChannelRepository != null) {
            String currentTaskID = getCurrentTaskID();
            this.mEffectContext.getEffectConfiguration().getListenerManger().setFetchProviderEffectListener(currentTaskID, iFetchProviderEffect);
            this.mEffectChannelRepository.searchProviderEffectList(str, str2, i, i2, currentTaskID);
        } else if (iFetchProviderEffect != null) {
            iFetchProviderEffect.onFail(new ExceptionResult(new IllegalStateException("请先初始化")));
        }
    }

    public void updateDeviceId(String str) {
        this.mEffectContext.getEffectConfiguration().setDeviceId(str);
    }

    public void updateHosts(List<Host> list, boolean z) {
        LinkSelector linkSelector = this.mLinkSelector;
        if (linkSelector != null) {
            linkSelector.updateHosts(list, z);
        }
    }

    public void updateTag(String str, String str2, IUpdateTagListener iUpdateTagListener) {
        if (this.mUpdateTagRepository != null) {
            this.mUpdateTagRepository.updateTag(getCurrentTaskID(), str, str2, iUpdateTagListener);
        } else if (iUpdateTagListener != null) {
            iUpdateTagListener.onFinally();
        }
    }
}
