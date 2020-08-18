package com.android.camera.features.mimoji2.fragment.edit;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.android.camera.features.mimoji2.bean.MimojiLevelBean2;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.utils.BitmapUtils2;
import com.android.camera.features.mimoji2.utils.ClickCheck2;
import com.android.camera.features.mimoji2.utils.ConfigInfoThumUtils2;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.storage.Storage;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.AvatarEngine;
import com.arcsoft.avatar.emoticon.AvatarEmoManager;
import com.arcsoft.avatar.emoticon.EmoInfo;
import com.arcsoft.avatar.util.AvatarConfigUtils;
import java.io.File;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class MimojiThumbnailRenderThread2 extends Thread {
    private static float[] BACKGROUND_COLOR = {0.1098f, 0.1176f, 0.1254f, 1.0f};
    private static final int MSG_AVATAR_INIT = 32;
    public static final int MSG_DRAW_REQUESTED = 16;
    private static final int MSG_QUIT_REQUESTED = 64;
    private static final int MSG_RESET_DATA = 96;
    private static final int MSG_SET_CONFIG = 80;
    private static final int MSG_START_EMO_PICTURE = 128;
    private static final int MSG_START_EMO_VIDEO = 112;
    private static final int MSG_UPDATE_THUMB = 48;
    /* access modifiers changed from: private */
    public static final String TAG = "MimojiThumbnailRenderThread2";
    private AvatarEngine mAvatarForEdit;
    private ConfigInfoThumUtils2 mConfigInfoThumUtils2;
    private Context mContext;
    /* access modifiers changed from: private */
    public AtomicInteger mCountEmotGif = new AtomicInteger();
    private String mCurrentConfigPath;
    private EGLWrapper2 mEGLWrapper2;
    private volatile boolean mEglContextPrepared = false;
    /* access modifiers changed from: private */
    public AvatarEmoManager mEmoManager;
    private RenderHandler mHandler;
    private final int mHeight;
    private volatile boolean mIsEmoticonForThumbnail = false;
    private volatile boolean mIsRendering = false;
    private final Object mLock = new Object();
    private volatile boolean mReady = false;
    private volatile int mRequestDraw;
    private volatile boolean mRequestRelease = false;
    private volatile boolean mRestStopRenderThumbnail = false;
    private volatile boolean mStopRenderThumbnail = false;
    private Handler mUpdateHandler;
    private final int mWidth;

    public static class RenderHandler extends Handler {
        private final WeakReference<MimojiThumbnailRenderThread2> mRenderThread;

        private RenderHandler(MimojiThumbnailRenderThread2 mimojiThumbnailRenderThread2) {
            this.mRenderThread = new WeakReference<>(mimojiThumbnailRenderThread2);
        }

        public void handleMessage(Message message) {
            MimojiThumbnailRenderThread2 mimojiThumbnailRenderThread2 = this.mRenderThread.get();
            if (mimojiThumbnailRenderThread2 != null) {
                int i = message.what;
                if (i == 16) {
                    mimojiThumbnailRenderThread2.doDraw(((Boolean) message.obj).booleanValue());
                } else if (i == 32) {
                    boolean unused = mimojiThumbnailRenderThread2.doInit((String) message.obj);
                } else if (i == 48) {
                    mimojiThumbnailRenderThread2.doUpdate();
                } else if (i == 64) {
                    mimojiThumbnailRenderThread2.doQuit();
                } else if (i == 80) {
                    mimojiThumbnailRenderThread2.doSetConfig((AvatarConfig.ASAvatarConfigInfo) message.obj);
                } else if (i == 96) {
                    mimojiThumbnailRenderThread2.doReset();
                } else if (i == 112) {
                    mimojiThumbnailRenderThread2.doEmoVideo((ArrayList) message.obj);
                } else if (i == 128) {
                    mimojiThumbnailRenderThread2.doEmoPicture((ArrayList) message.obj);
                }
            }
        }
    }

    public MimojiThumbnailRenderThread2(String str, int i, int i2, Context context) {
        super(str);
        this.mWidth = i;
        this.mHeight = i2;
        this.mContext = context;
    }

    /* access modifiers changed from: private */
    public void doDraw(boolean z) {
        boolean z2;
        if (this.mRequestRelease || !this.mEglContextPrepared) {
            return;
        }
        if (this.mIsEmoticonForThumbnail) {
            getEmoticonThumbnail();
            return;
        }
        synchronized (this.mLock) {
            z2 = this.mRequestDraw > 0;
            if (z2) {
                this.mRequestDraw--;
            }
        }
        if (z2) {
            drawThumbnail(z);
        }
    }

    /* access modifiers changed from: private */
    public void doEmoPicture(ArrayList<EmoInfo> arrayList) {
        FileUtils.delDir(MimojiHelper2.EMOTICON_JPEG_CACHE_DIR);
        FileUtils.makeNoMediaDir(MimojiHelper2.EMOTICON_JPEG_CACHE_DIR);
        if (this.mAvatarForEdit == null) {
            Log.e(TAG, "mimoji void doDraw[reset]  mAvatarForEdit null");
            return;
        }
        initEmoticon();
        this.mEmoManager.stopRecording();
        MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
        int i = 0;
        while (i < arrayList.size()) {
            EmoInfo emoInfo = arrayList.get(i);
            try {
                ByteBuffer renderImageData = this.mEmoManager.renderImageData(emoInfo, emoInfo.getEmoImageSize().getWidth(), emoInfo.getEmoImageSize().getHeight());
                StringBuilder sb = new StringBuilder();
                sb.append(Storage.DIRECTORY);
                sb.append(File.separator);
                sb.append(FileUtils.createtFileName("MIMOJI_GIF_" + emoInfo.getEmoName() + "_", "jpg"));
                String sb2 = sb.toString();
                if (renderImageData != null) {
                    BitmapUtils2.saveARGBToFile(sb2, emoInfo.getEmoImageSize().getWidth(), emoInfo.getEmoImageSize().getHeight(), renderImageData);
                }
                if (mimojiEmoticon != null) {
                    mimojiEmoticon.updateEmoticonPictureProgress(sb2, emoInfo, i == arrayList.size() - 1);
                }
            } catch (Exception e2) {
                Log.e(TAG, "mimoji void getEmoticonPicture[] " + e2.getMessage());
                if (i == arrayList.size() - 1) {
                    mimojiEmoticon.coverEmoticonError();
                }
            }
            i++;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x002f  */
    public void doEmoVideo(ArrayList<EmoInfo> arrayList) {
        initEmoticon();
        this.mEmoManager.stopRecording();
        this.mCountEmotGif.set(arrayList.size());
        FileUtils.delDir(MimojiHelper2.EMOTICON_MP4_CACHE_DIR);
        FileUtils.makeNoMediaDir(MimojiHelper2.EMOTICON_MP4_CACHE_DIR);
        FileUtils.delDir(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
        FileUtils.makeNoMediaDir(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
        Iterator<EmoInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            EmoInfo next = it.next();
            String str = MimojiHelper2.EMOTICON_MP4_CACHE_DIR + next.getEmoName() + ".mp4";
            Log.d(TAG, " videoPath :" + str);
            this.mEmoManager.startRecording(str, 0, next.getEmoImageSize().getWidth(), next.getEmoImageSize().getHeight(), 10000000, "video/avc");
            this.mEmoManager.emoProcess(next);
            AvatarEmoManager avatarEmoManager = this.mEmoManager;
            if (avatarEmoManager == null || avatarEmoManager.isRelease()) {
                Log.e("release_avatar", "-> for break ---");
                if (this.mCountEmotGif.get() != 0) {
                    this.mCountEmotGif.set(0);
                    MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
                    if (mimojiEmoticon != null) {
                        mimojiEmoticon.coverEmoticonError();
                        return;
                    }
                    return;
                }
                return;
            }
            while (it.hasNext()) {
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean doInit(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "mimoji void doInit[configPath] null");
            return false;
        }
        Log.d(TAG, "init mAvatarForEdit");
        ClickCheck2.getInstance().setForceDisabled(true);
        this.mRequestRelease = true;
        if (this.mAvatarForEdit == null) {
            this.mAvatarForEdit = new AvatarEngine();
            this.mAvatarForEdit.init(AvatarEngineManager2.TRACK_DATA, AvatarEngineManager2.FACE_MODEL);
            this.mAvatarForEdit.setRenderScene(false, 0.85f);
        }
        this.mAvatarForEdit.setTemplatePath(AvatarEngineManager2.PersonTemplatePath);
        this.mAvatarForEdit.loadConfig(str);
        this.mCurrentConfigPath = str;
        this.mConfigInfoThumUtils2 = new ConfigInfoThumUtils2();
        this.mRequestRelease = false;
        doUpdate();
        return true;
    }

    /* access modifiers changed from: private */
    public void doQuit() {
        if (!this.mRequestRelease) {
            this.mRequestRelease = true;
            release();
            Looper.myLooper().quit();
        }
    }

    /* access modifiers changed from: private */
    public void doReset() {
        this.mStopRenderThumbnail = false;
        if (this.mAvatarForEdit != null) {
            draw(true);
        }
    }

    /* access modifiers changed from: private */
    public void doSetConfig(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo) {
        AvatarEngine avatarEngine = this.mAvatarForEdit;
        if (avatarEngine != null) {
            avatarEngine.setConfig(aSAvatarConfigInfo);
        }
    }

    /* access modifiers changed from: private */
    public void doUpdate() {
        draw(false);
    }

    private void getEmoticonThumbnail() {
        if (this.mAvatarForEdit == null) {
            Log.e(TAG, "mimoji void doDraw[reset]  mAvatarForEdit null");
            return;
        }
        initEmoticon();
        ArrayList<EmoInfo> emoList = this.mEmoManager.getEmoList();
        int i = 0;
        boolean z = false;
        while (true) {
            if (i < emoList.size()) {
                EmoInfo emoInfo = emoList.get(i);
                boolean renderEmoThumb = this.mEmoManager.renderEmoThumb(emoInfo, AvatarEngineManager2.CONFIG_EMO_THUM_SIZE.getWidth(), AvatarEngineManager2.CONFIG_EMO_THUM_SIZE.getHeight());
                if (!renderEmoThumb) {
                    z = renderEmoThumb;
                    break;
                }
                MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
                if (mimojiEmoticon != null) {
                    mimojiEmoticon.updateEmoticonThumbnailProgress(i, emoInfo);
                }
                i++;
                z = renderEmoThumb;
            } else {
                break;
            }
        }
        this.mIsEmoticonForThumbnail = false;
        if (!z) {
            Log.d(TAG, "mimoji void doDraw[reset] mEmoManager.renderEmoThumb fail");
            MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon2 = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
            if (mimojiEmoticon2 != null) {
                mimojiEmoticon2.coverEmoticonError();
            }
        }
    }

    private void prepare() {
        this.mEGLWrapper2 = new EGLWrapper2(this.mWidth, this.mHeight);
        this.mEGLWrapper2.makeCurrent();
    }

    private void release() {
        AvatarEngine avatarEngine = this.mAvatarForEdit;
        if (avatarEngine != null) {
            avatarEngine.releaseRender();
            this.mAvatarForEdit.unInit();
            this.mAvatarForEdit.destroy();
            this.mAvatarForEdit = null;
        }
        EGLWrapper2 eGLWrapper2 = this.mEGLWrapper2;
        if (eGLWrapper2 != null) {
            eGLWrapper2.release();
            this.mEGLWrapper2 = null;
        }
        AvatarEmoManager avatarEmoManager = this.mEmoManager;
        if (avatarEmoManager != null) {
            avatarEmoManager.release();
            this.mEmoManager = null;
        }
    }

    private void resetConfig(ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList) {
        AvatarEngine avatarEngine = this.mAvatarForEdit;
        if (avatarEngine == null) {
            doInit(this.mCurrentConfigPath);
            Log.e(TAG, "mimoji  resetConfig mAvatarForEdit null");
            return;
        }
        this.mConfigInfoThumUtils2.reset(avatarEngine, AvatarEngineManager2.getInstance().getASAvatarConfigValue());
        int i = 0;
        int currentConfigIdWithType = AvatarConfigUtils.getCurrentConfigIdWithType(arrayList.get(0).configType, AvatarEngineManager2.getInstance().getASAvatarConfigValue());
        if (currentConfigIdWithType != -1) {
            i = currentConfigIdWithType;
        }
        AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo = null;
        Iterator<AvatarConfig.ASAvatarConfigInfo> it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            AvatarConfig.ASAvatarConfigInfo next = it.next();
            if (next.configID == i) {
                aSAvatarConfigInfo = next;
                break;
            }
        }
        if (aSAvatarConfigInfo != null) {
            this.mAvatarForEdit.setConfig(aSAvatarConfigInfo);
        }
    }

    public void draw(boolean z) {
        if (this.mHandler != null) {
            synchronized (this.mLock) {
                if (!this.mRequestRelease) {
                    if (this.mEglContextPrepared) {
                        this.mRequestDraw++;
                        Message obtainMessage = this.mHandler.obtainMessage();
                        obtainMessage.what = 16;
                        obtainMessage.obj = Boolean.valueOf(z);
                        this.mHandler.sendMessage(obtainMessage);
                    }
                }
            }
        }
    }

    public void drawForEmoticonPicture(List<EmoInfo> list) {
        if (this.mHandler != null) {
            initEmoticon();
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.what = 128;
            obtainMessage.obj = list;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void drawForEmoticonThumbnail() {
        quitEmoticonVideo();
        this.mIsEmoticonForThumbnail = true;
        draw(false);
    }

    public void drawThumbnail(boolean z) {
        if (!TextUtils.isEmpty(this.mCurrentConfigPath)) {
            AvatarEngine avatarEngine = this.mAvatarForEdit;
            if (avatarEngine == null) {
                doInit(this.mCurrentConfigPath);
                Log.i(TAG, "mimoji  drawThumbnail mAvatarForEdit null");
                return;
            }
            if (z) {
                avatarEngine.loadConfig(this.mCurrentConfigPath);
                AvatarEngineManager2.getInstance().resetData();
            }
            this.mIsRendering = true;
            int selectType = AvatarEngineManager2.getInstance().getSelectType();
            Log.i(TAG, "select  Type : " + selectType);
            CopyOnWriteArrayList<MimojiLevelBean2> subConfigList = AvatarEngineManager2.getInstance().getSubConfigList(this.mContext, selectType);
            Log.i(TAG, "mimojiLevelBean2s.size   :" + subConfigList.size());
            for (int i = 0; i < subConfigList.size(); i++) {
                MimojiLevelBean2 mimojiLevelBean2 = subConfigList.get(i);
                if (mimojiLevelBean2 != null) {
                    Log.i(TAG, "tempMimojiLevelBeans2 mConfigTypeName : " + mimojiLevelBean2.mConfigTypeName);
                    ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList = mimojiLevelBean2.mThumnails;
                    if (arrayList == null) {
                        continue;
                    } else {
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo = arrayList.get(i2);
                            if (aSAvatarConfigInfo == null) {
                                Log.i(TAG, "asainfo is null   curIndex : " + i2);
                            } else {
                                this.mConfigInfoThumUtils2.renderThumb(this.mAvatarForEdit, aSAvatarConfigInfo, AvatarEngineManager2.getInstance().getASAvatarConfigValue().gender, BACKGROUND_COLOR);
                                Message obtainMessage = this.mUpdateHandler.obtainMessage();
                                if (this.mRestStopRenderThumbnail) {
                                    this.mStopRenderThumbnail = false;
                                    this.mRestStopRenderThumbnail = false;
                                    this.mIsRendering = false;
                                    AvatarEngineManager2.getInstance().resetData();
                                    AvatarEngineManager2.getInstance().setTypeNeedUpdate(selectType, false);
                                    resetConfig(arrayList);
                                    draw(true);
                                    return;
                                } else if (this.mStopRenderThumbnail) {
                                    this.mStopRenderThumbnail = false;
                                    this.mIsRendering = false;
                                    resetConfig(arrayList);
                                    AvatarEngineManager2.getInstance().setTypeNeedUpdate(selectType, true);
                                    obtainMessage.what = 6;
                                    this.mUpdateHandler.sendMessage(obtainMessage);
                                    return;
                                } else {
                                    obtainMessage.what = 5;
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("OUTER", i);
                                    bundle.putInt("INNER", i2);
                                    bundle.putInt("TYPE", selectType);
                                    obtainMessage.obj = bundle;
                                    Handler handler = this.mUpdateHandler;
                                    if (handler != null) {
                                        handler.sendMessage(obtainMessage);
                                    }
                                }
                            }
                        }
                        resetConfig(arrayList);
                    }
                }
            }
            AvatarEngineManager2.getInstance().setTypeNeedUpdate(selectType, false);
            ClickCheck2.getInstance().setForceDisabled(false);
            this.mIsRendering = false;
        }
    }

    public RenderHandler getHandler() {
        synchronized (this.mLock) {
            if (!this.mReady) {
                throw new IllegalStateException("render thread is not ready yet");
            }
        }
        return this.mHandler;
    }

    public boolean getIsRendering() {
        return this.mIsRendering;
    }

    public void initAvatar(String str) {
        RenderHandler renderHandler = this.mHandler;
        if (renderHandler != null) {
            Message obtainMessage = renderHandler.obtainMessage();
            obtainMessage.what = 32;
            obtainMessage.obj = str;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void initEmoticon() {
        if (this.mAvatarForEdit != null) {
            AvatarEmoManager avatarEmoManager = this.mEmoManager;
            if (avatarEmoManager == null || avatarEmoManager.isRelease()) {
                this.mEmoManager = new AvatarEmoManager(this.mAvatarForEdit, AvatarEngineManager2.GifTemplatePath, AvatarEngineManager2.getInstance().getASAvatarConfigValue().configFaceColorID, new AvatarEmoManager.AvatarEmoResCallback() {
                    /* class com.android.camera.features.mimoji2.fragment.edit.MimojiThumbnailRenderThread2.AnonymousClass1 */

                    @Override // com.arcsoft.avatar.emoticon.AvatarEmoManager.AvatarEmoResCallback
                    public void onFrameRefresh(EmoInfo.EmoExtraInfo emoExtraInfo) {
                        String access$100 = MimojiThumbnailRenderThread2.TAG;
                        Log.d(access$100, "onFrameRefresh emoExtraInfo : " + emoExtraInfo.index);
                        if (MimojiThumbnailRenderThread2.this.mEmoManager != null && !MimojiThumbnailRenderThread2.this.mEmoManager.isRelease()) {
                            MimojiThumbnailRenderThread2.this.mEmoManager.emoGLRender(emoExtraInfo);
                        }
                    }

                    @Override // com.arcsoft.avatar.emoticon.AvatarEmoManager.AvatarEmoResCallback
                    public void onMakeMediaEnd() {
                        String access$100 = MimojiThumbnailRenderThread2.TAG;
                        Log.d(access$100, "onMakeMediaEnd 时间: " + System.currentTimeMillis());
                        if (MimojiThumbnailRenderThread2.this.mEmoManager == null || MimojiThumbnailRenderThread2.this.mEmoManager.isRelease()) {
                            Log.e("release_avatar", "-> for break ---");
                            return;
                        }
                        MimojiThumbnailRenderThread2.this.mEmoManager.stopRecording();
                        int decrementAndGet = MimojiThumbnailRenderThread2.this.mCountEmotGif.decrementAndGet();
                        MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
                        if (mimojiEmoticon != null) {
                            mimojiEmoticon.updateEmoticonGifProgress(decrementAndGet);
                        }
                    }
                });
            }
            this.mEmoManager.setFaceColorId(AvatarEngineManager2.getInstance().getASAvatarConfigValue().configFaceColorID);
            AvatarEngineManager2.getInstance().setEmoManager(this.mEmoManager);
        }
    }

    public void quit() {
        RenderHandler renderHandler = this.mHandler;
        if (renderHandler != null) {
            renderHandler.obtainMessage(64).sendToTarget();
        }
    }

    public void quitEmoticonVideo() {
        AvatarEmoManager avatarEmoManager = this.mEmoManager;
        if (avatarEmoManager != null) {
            avatarEmoManager.release();
        }
        this.mEmoManager = null;
    }

    public void recordForEmoticonVideo(List<EmoInfo> list) {
        if (this.mHandler != null) {
            initEmoticon();
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.what = 112;
            obtainMessage.obj = list;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void reset() {
        if (this.mHandler != null) {
            setStopRender(true);
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.what = 96;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void run() {
        Looper.prepare();
        this.mHandler = new RenderHandler();
        Log.d(TAG, "prepare render thread: E");
        try {
            this.mEglContextPrepared = false;
            prepare();
            this.mEglContextPrepared = true;
        } catch (Exception e2) {
            Log.d(TAG, "FATAL: failed to prepare render thread", e2);
            release();
        }
        synchronized (this.mLock) {
            this.mReady = true;
            this.mLock.notify();
        }
        Looper.loop();
        synchronized (this.mLock) {
            this.mReady = false;
            this.mHandler = null;
        }
        Log.d(TAG, "prepare render thread: X");
    }

    public void setConfig(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo) {
        RenderHandler renderHandler = this.mHandler;
        if (renderHandler != null) {
            Message obtainMessage = renderHandler.obtainMessage();
            obtainMessage.what = 80;
            obtainMessage.obj = aSAvatarConfigInfo;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void setResetStopRender(boolean z) {
        if (this.mIsRendering) {
            this.mRestStopRenderThumbnail = z;
        }
    }

    public void setStopRender(boolean z) {
        if (this.mIsRendering) {
            this.mStopRenderThumbnail = z;
        }
    }

    public void setUpdateHandler(Handler handler) {
        this.mUpdateHandler = handler;
    }

    public void waitUntilReady() {
        synchronized (this.mLock) {
            if (!this.mReady) {
                try {
                    this.mLock.wait();
                } catch (InterruptedException e2) {
                    String str = TAG;
                    Log.e(str, "waitUntilReady() interrupted: " + e2);
                }
            }
        }
    }
}
