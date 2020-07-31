.class public Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;
.super Ljava/lang/Object;
.source "MimojiVideoEditorImpl.java"

# interfaces
.implements Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "MimojiVideoEditorImpl"


# instance fields
.field private mComposeFile:Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

.field private mContext:Landroid/content/Context;

.field private mEncodeHeight:I

.field private mEncodeWidth:I

.field private mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

.field private mMimojiMediaPlayerCallback:Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;

.field private mNeedPrepare:Z

.field private mOrientation:I

.field private mSourceID:J

.field private mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

.field private mVideoSavePath:Ljava/lang/String;

.field private mVoiceChangeFilterID:J

.field private mWaitingResultSurfaceTexture:Z


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-string v0, "vvc++_shared"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    const-string v0, "mimoji_video2gif"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    return-void
.end method

.method private constructor <init>(Lcom/android/camera/ActivityBase;)V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mNeedPrepare:Z

    const/16 v0, 0x780

    iput v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mEncodeWidth:I

    const/16 v0, 0x438

    iput v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mEncodeHeight:I

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mSourceID:J

    iput-wide v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVoiceChangeFilterID:J

    invoke-virtual {p1}, Lcom/android/camera/ActivityBase;->getCameraAppImpl()Lcom/android/camera/CameraAppImpl;

    move-result-object p1

    invoke-virtual {p1}, Landroid/app/Application;->getApplicationContext()Landroid/content/Context;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mContext:Landroid/content/Context;

    return-void
.end method

.method static synthetic a(ILjava/util/List;I)V
    .locals 2

    const/16 v0, 0x64

    if-ne p2, v0, :cond_0

    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result p1

    add-int/lit8 p1, p1, -0x1

    if-ne p0, p1, :cond_1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p1, 0xfa

    invoke-virtual {p0, p1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2$MimojiEmoticon;

    if-eqz p0, :cond_1

    invoke-interface {p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2$MimojiEmoticon;->coverEmoticonSuccess()V

    goto :goto_0

    :cond_0
    sget-object p1, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "mimoji void video2gif[]  Video  "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p0, " convert GIF progress : "

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p0, "%"

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_1
    :goto_0
    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "progress value: "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method static synthetic access$100()Ljava/lang/String;
    .locals 1

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$200(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;Ljava/lang/String;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->onSuccess(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$300(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->onFail()V

    return-void
.end method

.method static synthetic access$400(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mWaitingResultSurfaceTexture:Z

    return p0
.end method

.method static synthetic access$500(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;Landroid/view/Surface;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->startPlay(Landroid/view/Surface;)V

    return-void
.end method

.method public static create(Lcom/android/camera/ActivityBase;)Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;
    .locals 1

    new-instance v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;-><init>(Lcom/android/camera/ActivityBase;)V

    return-object v0
.end method

.method private onFail()V
    .locals 1

    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    const-string v0, "mimoji void onFail[]"

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object p0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->VIDEO_DEAL_CACHE_FILE:Ljava/lang/String;

    invoke-static {p0}, Lcom/android/camera/module/impl/component/FileUtils;->deleteFile(Ljava/lang/String;)Z

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xf9

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiFullScreenProtocol;

    if-eqz p0, :cond_0

    invoke-interface {p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiFullScreenProtocol;->onCombineError()V

    goto :goto_0

    :cond_0
    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    const-string v0, "mimoji void onFail null"

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    return-void
.end method

.method private onSuccess(Ljava/lang/String;)V
    .locals 1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xf9

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiFullScreenProtocol;

    if-eqz p0, :cond_0

    invoke-interface {p0, p1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiFullScreenProtocol;->concatResult(Ljava/lang/String;)V

    goto :goto_0

    :cond_0
    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    const-string p1, "mimoji void cnSuccess[savePath] null"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    return-void
.end method

.method private prepareComposeFile()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    if-nez v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->prepareEffectGraph()V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mComposeFile:Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

    if-nez v0, :cond_1

    new-instance v0, Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    invoke-direct {v0, v1}, Lcom/xiaomi/Video2GifEditer/MediaComposeFile;-><init>(Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;)V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mComposeFile:Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

    :cond_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mComposeFile:Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

    iget v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mEncodeWidth:I

    iget p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mEncodeHeight:I

    const/high16 v2, 0x1400000

    const/16 v3, 0x1e

    invoke-virtual {v0, v1, p0, v2, v3}, Lcom/xiaomi/Video2GifEditer/MediaComposeFile;->ConstructMediaComposeFile(IIII)Z

    return-void
.end method

.method private prepareEffectGraph()V
    .locals 9

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mContext:Landroid/content/Context;

    const/16 v1, 0x7b

    invoke-static {v0, v1}, Lcom/xiaomi/MediaRecord/SystemUtil;->Init(Landroid/content/Context;I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    if-nez v0, :cond_0

    new-instance v0, Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    invoke-direct {v0}, Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;-><init>()V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    invoke-virtual {v0}, Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;->ConstructMediaEffectGraph()V

    iget-wide v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVoiceChangeFilterID:J

    const-wide/16 v7, 0x0

    cmp-long v0, v2, v7

    if-eqz v0, :cond_1

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    iget-wide v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mSourceID:J

    const/4 v6, 0x0

    invoke-virtual/range {v1 .. v6}, Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;->RemoveEffect(JJZ)Z

    iput-wide v7, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVoiceChangeFilterID:J

    :cond_1
    return-void
.end method

.method private startPlay(Landroid/view/Surface;)V
    .locals 2

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    const-string v1, "mimoji void startPlay[surface]"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-nez p1, :cond_0

    return-void

    :cond_0
    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mWaitingResultSurfaceTexture:Z

    iget p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mOrientation:I

    if-eqz p1, :cond_3

    const/16 v0, 0xb4

    if-ne p1, v0, :cond_1

    goto :goto_1

    :cond_1
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    const/4 v0, 0x6

    invoke-virtual {p1, v0}, Lcom/android/camera/ui/TextureVideoView;->setScaleType(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    iget v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mOrientation:I

    const/16 v1, 0x10e

    if-ne v0, v1, :cond_2

    const/4 v0, 0x0

    goto :goto_0

    :cond_2
    const/high16 v0, 0x43340000    # 180.0f

    :goto_0
    invoke-virtual {p1, v0}, Landroid/view/TextureView;->setRotation(F)V

    goto :goto_2

    :cond_3
    :goto_1
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    const/4 v0, 0x3

    invoke-virtual {p1, v0}, Lcom/android/camera/ui/TextureVideoView;->setScaleType(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    iget v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mOrientation:I

    int-to-float v0, v0

    invoke-virtual {p1, v0}, Landroid/view/TextureView;->setRotation(F)V

    :goto_2
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Lcom/android/camera/ui/TextureVideoView;->setLoop(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    invoke-virtual {p1, v0}, Lcom/android/camera/ui/TextureVideoView;->setClearSurface(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVideoSavePath:Ljava/lang/String;

    invoke-virtual {p1, v0}, Lcom/android/camera/ui/TextureVideoView;->setVideoPath(Ljava/lang/String;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    iget v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mEncodeWidth:I

    iget v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mEncodeHeight:I

    invoke-virtual {p1, v0, v1}, Lcom/android/camera/ui/TextureVideoView;->setVideoSpecifiedSize(II)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    invoke-virtual {p0}, Lcom/android/camera/ui/TextureVideoView;->start()V

    return-void
.end method


# virtual methods
.method public changeTimbre()V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xf9

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiFullScreenProtocol;

    if-eqz v0, :cond_0

    invoke-interface {v0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiFullScreenProtocol;->startMimojiRecordPreview()V

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->pausePlay()Z

    sget-object v0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->VIDEO_DEAL_CACHE_FILE:Ljava/lang/String;

    invoke-static {v0}, Lcom/android/camera/module/impl/component/FileUtils;->deleteFile(Ljava/lang/String;)Z

    sget-object v0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->VIDEO_NORMAL_CACHE_FILE:Ljava/lang/String;

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->combineVideoAudio(Ljava/lang/String;)V

    return-void
.end method

.method public combineVideoAudio(Ljava/lang/String;)V
    .locals 10

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mimoji void combineVideoAudio[savePath]"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->prepareEffectGraph()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    const/4 v1, 0x0

    invoke-virtual {v0, p1, v1}, Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;->AddVideoSource(Ljava/lang/String;Z)J

    move-result-wide v0

    iput-wide v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mSourceID:J

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getCurrentMimojiTimbreInfo()Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;

    move-result-object v0

    if-eqz v0, :cond_2

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    const-string v1, "mimoji void startPlay[surface]  timbre start"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-wide v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVoiceChangeFilterID:J

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-nez v0, :cond_0

    sget-object v0, Lcom/xiaomi/Video2GifEditer/EffectType;->VoiceChangeFilter:Lcom/xiaomi/Video2GifEditer/EffectType;

    invoke-static {v0}, Lcom/xiaomi/Video2GifEditer/MediaEffect;->CreateEffect(Lcom/xiaomi/Video2GifEditer/EffectType;)J

    move-result-wide v0

    iput-wide v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVoiceChangeFilterID:J

    iget-wide v5, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVoiceChangeFilterID:J

    cmp-long v0, v5, v2

    if-eqz v0, :cond_0

    iget-object v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    iget-wide v7, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mSourceID:J

    const/4 v9, 0x0

    invoke-virtual/range {v4 .. v9}, Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;->AddEffect(JJZ)Z

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getCurrentMimojiTimbreInfo()Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;->getTimbreId()I

    move-result p1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, ""

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v1, "mode"

    invoke-interface {v0, v1, p1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    sget-object p1, Lcom/xiaomi/Video2GifEditer/EffectType;->VoiceChangeFilter:Lcom/xiaomi/Video2GifEditer/EffectType;

    iget-wide v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVoiceChangeFilterID:J

    invoke-static {p1, v4, v5, v0}, Lcom/xiaomi/Video2GifEditer/MediaEffect;->SetParamsForEffect(Lcom/xiaomi/Video2GifEditer/EffectType;JLjava/util/Map;)Z

    :cond_0
    iget-wide v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVoiceChangeFilterID:J

    cmp-long p1, v0, v2

    if-nez p1, :cond_1

    sget-object p1, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    const-string v0, "mimoji void startPlay[surface] mVoiceChangeFilterID==0 timbre init fail"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->onFail()V

    return-void

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->prepareComposeFile()V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mComposeFile:Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

    new-instance v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$1;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$1;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;)V

    invoke-virtual {p1, v0}, Lcom/xiaomi/Video2GifEditer/MediaComposeFile;->SetComposeNotify(Lcom/xiaomi/Video2GifEditer/EffectNotifier;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mComposeFile:Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

    sget-object v0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->VIDEO_DEAL_CACHE_FILE:Ljava/lang/String;

    invoke-virtual {p1, v0}, Lcom/xiaomi/Video2GifEditer/MediaComposeFile;->SetComposeFileName(Ljava/lang/String;)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mComposeFile:Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

    invoke-virtual {p0}, Lcom/xiaomi/Video2GifEditer/MediaComposeFile;->BeginComposeFile()V

    goto :goto_0

    :cond_2
    sget-object p1, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->VIDEO_NORMAL_CACHE_FILE:Ljava/lang/String;

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->onSuccess(Ljava/lang/String;)V

    :goto_0
    return-void
.end method

.method public init(Lcom/android/camera/ui/TextureVideoView;Ljava/lang/String;)Z
    .locals 3

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mimoji videoeditor init videoUri "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mWaitingResultSurfaceTexture:Z

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVideoSavePath:Ljava/lang/String;

    new-instance p1, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;

    const/4 p2, 0x0

    invoke-direct {p1, p0, p2}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$1;)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMimojiMediaPlayerCallback:Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMimojiMediaPlayerCallback:Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;

    invoke-virtual {p1, p0}, Lcom/android/camera/ui/TextureVideoView;->setMediaPlayerCallback(Lcom/android/camera/ui/TextureVideoView$MediaPlayerCallback;)V

    return v0
.end method

.method public isPlaying()Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/ui/TextureVideoView;->isPlaying()Z

    move-result p0

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public onDestory()V
    .locals 2

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    const-string v1, "mimoji void onDestory[]"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/android/camera/ui/TextureVideoView;->stop()V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mComposeFile:Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Lcom/xiaomi/Video2GifEditer/MediaComposeFile;->CancelComposeFile()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mComposeFile:Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

    invoke-virtual {v0}, Lcom/xiaomi/Video2GifEditer/MediaComposeFile;->DestructMediaComposeFile()V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mComposeFile:Lcom/xiaomi/Video2GifEditer/MediaComposeFile;

    :cond_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    if-eqz v0, :cond_2

    invoke-virtual {v0}, Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;->DestructMediaEffectGraph()V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mMediaEffectGraph:Lcom/xiaomi/Video2GifEditer/MediaEffectGraph;

    invoke-static {}, Lcom/xiaomi/MediaRecord/SystemUtil;->UnInit()V

    :cond_2
    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mSourceID:J

    iput-wide v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVoiceChangeFilterID:J

    return-void
.end method

.method public pausePlay()Z
    .locals 2

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    const-string v1, "mimoji void pausePlay[]"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->isPlaying()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/ui/TextureVideoView;->pause()V

    const/4 p0, 0x1

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public registerProtocol()V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xfc

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method

.method public resumePlay()Z
    .locals 2

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    const-string v1, "mimoji void resumePlay[]"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/ui/TextureVideoView;->resume()V

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public setRecordParameter(III)V
    .locals 2

    const/4 v0, 0x0

    invoke-static {v0, p3}, Ljava/lang/Math;->max(II)I

    move-result p3

    iput p3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mOrientation:I

    sget-object p3, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "setRecordParameter:  "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, " | "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mOrientation:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p3, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mEncodeWidth:I

    iput p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mEncodeHeight:I

    return-void
.end method

.method public startPlay()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    if-eqz v0, :cond_2

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mVideoSavePath:Ljava/lang/String;

    if-nez v1, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/ui/TextureVideoView;->getPreviewSurface()Landroid/view/Surface;

    move-result-object v0

    if-nez v0, :cond_1

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mWaitingResultSurfaceTexture:Z

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    invoke-virtual {p0}, Lcom/android/camera/ui/TextureVideoView;->start()V

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->mTextureVideoView:Lcom/android/camera/ui/TextureVideoView;

    invoke-virtual {v0}, Lcom/android/camera/ui/TextureVideoView;->getPreviewSurface()Landroid/view/Surface;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->startPlay(Landroid/view/Surface;)V

    :cond_2
    :goto_0
    return-void
.end method

.method public unRegisterProtocol()V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xfc

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->onDestory()V

    return-void
.end method

.method public video2gif(Ljava/util/List;)V
    .locals 18
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/arcsoft/avatar/emoticon/EmoInfo;",
            ">;)V"
        }
    .end annotation

    move-object/from16 v0, p1

    const/16 v1, 0xfa

    if-eqz v0, :cond_3

    invoke-interface/range {p1 .. p1}, Ljava/util/List;->size()I

    move-result v2

    if-nez v2, :cond_0

    goto/16 :goto_1

    :cond_0
    const/4 v2, 0x0

    :goto_0
    invoke-interface/range {p1 .. p1}, Ljava/util/List;->size()I

    move-result v3

    if-ge v2, v3, :cond_2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v4, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_MP4_CACHE_DIR:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/arcsoft/avatar/emoticon/EmoInfo;

    invoke-virtual {v4}, Lcom/arcsoft/avatar/emoticon/EmoInfo;->getEmoName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, ".mp4"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v4, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_GIF_CACHE_DIR:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/arcsoft/avatar/emoticon/EmoInfo;

    invoke-virtual {v4}, Lcom/arcsoft/avatar/emoticon/EmoInfo;->getEmoName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, ".gif"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    sget-object v3, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_GIF_CACHE_DIR:Ljava/lang/String;

    invoke-static {v3}, Lcom/android/camera/module/impl/component/FileUtils;->makeDir(Ljava/lang/String;)Z

    sget-object v3, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, " source :"

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, "\n target : "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const-wide/16 v6, 0x7d0

    const/4 v9, 0x1

    const/16 v10, 0x14

    const/16 v11, 0x5dc

    const-wide/16 v12, 0x0

    const-wide/16 v14, 0x1388

    new-instance v3, Lcom/android/camera/features/mimoji2/module/impl/h;

    invoke-direct {v3, v2, v0}, Lcom/android/camera/features/mimoji2/module/impl/h;-><init>(ILjava/util/List;)V

    const/high16 v16, 0x3f800000    # 1.0f

    move-object/from16 v17, v3

    invoke-static/range {v5 .. v17}, Lcom/xiaomi/Video2GifEditer/MediaProcess;->Convert(Ljava/lang/String;JLjava/lang/String;ZIIJJFLcom/xiaomi/Video2GifEditer/MediaProcess$Callback;)I

    move-result v3

    if-eqz v3, :cond_1

    sget-object v3, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->TAG:Ljava/lang/String;

    const-string v4, "mimoji void video2gif[] cover fail"

    invoke-static {v3, v4}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v3

    invoke-virtual {v3, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v3

    check-cast v3, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2$MimojiEmoticon;

    if-eqz v3, :cond_1

    invoke-interface {v3}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2$MimojiEmoticon;->coverEmoticonError()V

    :cond_1
    add-int/lit8 v2, v2, 0x1

    goto/16 :goto_0

    :cond_2
    return-void

    :cond_3
    :goto_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2$MimojiEmoticon;

    if-eqz v0, :cond_4

    invoke-interface {v0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2$MimojiEmoticon;->coverEmoticonSuccess()V

    :cond_4
    return-void
.end method
