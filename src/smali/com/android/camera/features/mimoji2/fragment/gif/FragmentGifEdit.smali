.class public Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;
.super Lcom/android/camera/fragment/BaseFragment;
.source "FragmentGifEdit.java"

# interfaces
.implements Landroid/view/View$OnClickListener;
.implements Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;
.implements Lcom/android/camera/protocol/ModeProtocol$MimojiGifEditor;


# static fields
.field public static final GIF_EDIT_SHOW:I = 0x12f

.field public static final Gif_EDIT_HIDE:I = 0xca

.field public static final REQUEST_CODE_SHARE_GIF:I = 0x130

.field public static final TAG:Ljava/lang/String; = "FragmentGifEdit"


# instance fields
.field private mContext:Landroid/content/Context;

.field private mFitTextureView:Lcom/android/camera/features/mimoji2/fragment/gif/MiFitTextureView;

.field private mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

.field private mGifViewLayout:Landroid/view/View;

.field private mGifViewPresenter:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

.field private mIsContainEmoji:Z

.field private mIsShare:Z

.field private mIvShare:Landroid/widget/ImageView;

.field private mLlTextureContainer:Landroid/widget/FrameLayout;

.field private mVideoPath:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/BaseFragment;-><init>()V

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;)Landroid/content/Context;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mContext:Landroid/content/Context;

    return-object p0
.end method

.method private initGifMediaPlayer()V
    .locals 3

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifViewPresenter:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->setGifMediaPlayer(Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifViewPresenter:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mVideoPath:Ljava/lang/String;

    iget-boolean v2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mIsContainEmoji:Z

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->completeVideoRecord(Ljava/lang/String;Z)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mFitTextureView:Lcom/android/camera/features/mimoji2/fragment/gif/MiFitTextureView;

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->setFitTextureView(Lcom/android/camera/features/mimoji2/fragment/gif/MiFitTextureView;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mVideoPath:Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mVideoPath:Ljava/lang/String;

    new-instance v2, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit$1;

    invoke-direct {v2, p0}, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit$1;-><init>(Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;)V

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->setVideoUrl(Ljava/lang/String;Lcom/xiaomi/Video2GifEditer/MediaProcess$Callback;)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->PlayCameraRecord()V

    :cond_0
    return-void
.end method


# virtual methods
.method public completeVideoRecord(Ljava/lang/String;Z)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mVideoPath:Ljava/lang/String;

    iput-boolean p2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mIsContainEmoji:Z

    return-void
.end method

.method public doShare()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->getVideoInfo()Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->getGifUri()Landroid/net/Uri;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {p0, v1}, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->setIsShare(Z)V

    new-instance v1, Landroid/content/Intent;

    invoke-direct {v1}, Landroid/content/Intent;-><init>()V

    const-string v2, "android.intent.action.SEND"

    invoke-virtual {v1, v2}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    const-string v2, "image/gif"

    invoke-virtual {v1, v2}, Landroid/content/Intent;->setType(Ljava/lang/String;)Landroid/content/Intent;

    const-string v2, "android.intent.extra.STREAM"

    invoke-virtual {v1, v2, v0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    const/4 v0, 0x1

    invoke-virtual {v1, v0}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    const-string v0, "share gif"

    invoke-static {v1, v0}, Landroid/content/Intent;->createChooser(Landroid/content/Intent;Ljava/lang/CharSequence;)Landroid/content/Intent;

    move-result-object v0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mContext:Landroid/content/Context;

    invoke-virtual {p0, v0}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V

    return-void
.end method

.method public getFragmentInto()I
    .locals 0

    const/16 p0, 0xfa5

    return p0
.end method

.method protected getLayoutResourceId()I
    .locals 0

    const p0, 0x7f0b002c

    return p0
.end method

.method protected initView(Landroid/view/View;)V
    .locals 4

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifViewLayout:Landroid/view/View;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v1

    iget v1, v1, Landroid/util/DisplayMetrics;->widthPixels:I

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v2

    iget v2, v2, Landroid/util/DisplayMetrics;->heightPixels:I

    iput v2, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    invoke-static {}, Lcom/android/camera/Util;->isContentViewExtendToTopEdges()Z

    move-result v2

    if-eqz v2, :cond_0

    sget v2, Lcom/android/camera/Util;->sStatusBarHeight:I

    goto :goto_0

    :cond_0
    const/4 v2, 0x0

    :goto_0
    iput v2, v0, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object v3

    invoke-direct {v0, v2, v3}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;-><init>(Landroid/content/Context;Landroid/support/v4/app/FragmentManager;)V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifViewPresenter:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifViewPresenter:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifViewLayout:Landroid/view/View;

    invoke-virtual {v0, v2}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->initView(Landroid/view/View;)V

    const v0, 0x7f09009a

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mIvShare:Landroid/widget/ImageView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mIvShare:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f090076

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/fragment/gif/MiFitTextureView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mFitTextureView:Lcom/android/camera/features/mimoji2/fragment/gif/MiFitTextureView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mFitTextureView:Lcom/android/camera/features/mimoji2/fragment/gif/MiFitTextureView;

    invoke-virtual {v0}, Landroid/view/TextureView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->width:I

    const v0, 0x7f0900d4

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/FrameLayout;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mLlTextureContainer:Landroid/widget/FrameLayout;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mLlTextureContainer:Landroid/widget/FrameLayout;

    invoke-virtual {p1}, Landroid/widget/FrameLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p1

    check-cast p1, Landroid/view/ViewGroup$MarginLayoutParams;

    iput v1, p1, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iput v1, p1, Landroid/view/ViewGroup$MarginLayoutParams;->width:I

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->initGifMediaPlayer()V

    return-void
.end method

.method public isShare()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mIsShare:Z

    return p0
.end method

.method public onActivityResult(IILandroid/content/Intent;)V
    .locals 0

    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string p3, "gif onActivityResult  resultCode:"

    invoke-virtual {p0, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p2, "  requestcode: "

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p2, "FragmentGifEdit"

    invoke-static {p2, p0}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    const/16 p0, 0x130

    return-void
.end method

.method public onBackEvent(I)Z
    .locals 0

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p1, 0xa0

    invoke-virtual {p0, p1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    if-eqz p0, :cond_0

    const/16 p1, 0x14

    invoke-interface {p0, p1}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->delegateEvent(I)V

    :cond_0
    const/4 p0, 0x1

    return p0
.end method

.method public onClick(Landroid/view/View;)V
    .locals 1

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result p1

    const v0, 0x7f09009a

    if-eq p1, v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p1, 0x1

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->setIsShare(Z)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifViewPresenter:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->gifConfirmVideo2Gif()V

    :goto_0
    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 1
    .param p2    # Landroid/view/ViewGroup;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation

    const p3, 0x7f0b002c

    const/4 v0, 0x0

    invoke-virtual {p1, p3, p2, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->initView(Landroid/view/View;)V

    return-object p1
.end method

.method public onDestroy()V
    .locals 0

    invoke-super {p0}, Landroid/support/v4/app/Fragment;->onDestroy()V

    return-void
.end method

.method public onDestroyView()V
    .locals 0

    invoke-super {p0}, Lcom/android/camera/fragment/BaseFragment;->onDestroyView()V

    return-void
.end method

.method public onPause()V
    .locals 0

    invoke-super {p0}, Landroid/support/v4/app/Fragment;->onPause()V

    return-void
.end method

.method public operateGifPannelVisibleState(I)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mGifViewPresenter:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->operateGifPannelVisibleState(I)V

    return-void
.end method

.method public reconfigPreviewRadio(I)V
    .locals 0

    return-void
.end method

.method protected register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->registerBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xfb

    invoke-virtual {p1, v0, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method

.method public setIsShare(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->mIsShare:Z

    return-void
.end method

.method protected unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->unRegisterBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xfb

    invoke-virtual {p1, v0, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method
