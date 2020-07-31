.class public Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;
.super Lcom/android/camera/fragment/BaseFragment;
.source "FragmentMimojiFullScreen.java"

# interfaces
.implements Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiFullScreenProtocol;
.implements Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;
.implements Landroid/view/View$OnClickListener;


# static fields
.field public static final FRAGMENT_INFO:I = 0xfa4

.field private static final TAG:Ljava/lang/String; = "MIMOJIFullScreen"


# instance fields
.field private mBottomActionView:Landroid/view/ViewGroup;

.field private mBottomLayout:Landroid/view/ViewGroup;

.field private mBottomTimbreLayout:Landroid/view/ViewGroup;

.field private mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

.field private mCancelProgress:Landroid/widget/ProgressBar;

.field private mCombineProgress:Landroid/widget/ProgressBar;

.field private mConcatProgress:Landroid/widget/ProgressBar;

.field private mExitDialog:Landroid/app/AlertDialog;

.field private mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

.field private mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

.field private mLiveViewLayout:Landroid/view/View;

.field private mLiveViewStub:Landroid/view/ViewStub;

.field private mMimojiChangeTimbreBtn:Landroid/widget/ImageView;

.field private mMimojiChangeTimbreLayout:Landroid/widget/FrameLayout;

.field private mPreviewBack:Landroid/widget/ImageView;

.field private mPreviewCombine:Landroid/widget/ImageView;

.field private mPreviewCoverBitmap:Landroid/graphics/Bitmap;

.field private mPreviewCoverView:Landroid/widget/ImageView;

.field private mPreviewLayout:Landroid/widget/FrameLayout;

.field private mPreviewShare:Landroid/widget/ImageView;

.field private mPreviewStart:Landroid/widget/ImageView;

.field private mPreviewTextureView:Lcom/android/camera/ui/TextureVideoView;

.field private mSavedPath:Ljava/lang/String;

.field private mShareAdapter:Lcom/android/camera/fragment/fullscreen/ShareAdapter;

.field private mShareCancel:Landroid/view/View;

.field private mShareLayout:Landroid/view/ViewGroup;

.field private mShareProgress:Landroid/widget/ProgressBar;

.field private mShareRecyclerView:Landroid/support/v7/widget/RecyclerView;

.field private mTimeView:Landroid/widget/TextView;


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/BaseFragment;-><init>()V

    return-void
.end method

.method static synthetic access$002(Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;Landroid/app/AlertDialog;)Landroid/app/AlertDialog;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mExitDialog:Landroid/app/AlertDialog;

    return-object p1
.end method

.method private checkAndShare()Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    invoke-static {v0}, Lcom/android/camera/module/impl/component/FileUtils;->checkFileConsist(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    :try_start_0
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->showShareSheet()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    const/4 p0, 0x1

    return p0

    :catch_0
    :cond_0
    return v1
.end method

.method private getFragmentMiMoji()Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;
    .locals 3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    const/4 v1, 0x0

    const-string v2, "MIMOJIFullScreen"

    if-nez v0, :cond_0

    const-string p0, "getFragmentMiMoji(): fragment is null"

    invoke-static {v2, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-object v1

    :cond_0
    invoke-virtual {v0}, Landroid/support/v4/app/Fragment;->isAdded()Z

    move-result v0

    if-nez v0, :cond_1

    const-string p0, "getFragmentMiMoji(): fragment is not added yet"

    invoke-static {v2, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-object v1

    :cond_1
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    return-object p0
.end method

.method private getShareIntent()Landroid/content/Intent;
    .locals 4

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    invoke-static {v0}, Lcom/android/camera/module/impl/component/FileUtils;->checkFileConsist(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    goto :goto_0

    :cond_0
    move-object v0, v1

    :goto_0
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_1

    return-object v1

    :cond_1
    const/4 v1, 0x2

    invoke-static {v1, v0}, Lcom/android/camera/Util;->genContentValues(ILjava/lang/String;)Landroid/content/ContentValues;

    move-result-object v2

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object p0

    check-cast p0, Lcom/android/camera/ActivityBase;

    invoke-interface {p0}, Lcom/android/camera/AppController;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object p0

    const/4 v3, 0x0

    invoke-virtual {p0, v0, v2, v3}, Lcom/android/camera/storage/ImageSaver;->addVideoSync(Ljava/lang/String;Landroid/content/ContentValues;Z)Landroid/net/Uri;

    move-result-object p0

    new-instance v0, Landroid/content/Intent;

    const-string v2, "android.intent.action.SEND"

    invoke-direct {v0, v2}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    const-string v2, "android.intent.extra.STREAM"

    invoke-virtual {v0, v2, p0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    invoke-static {v1}, Lcom/android/camera/Util;->convertOutputFormatToMimeType(I)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Landroid/content/Intent;->setType(Ljava/lang/String;)Landroid/content/Intent;

    const/4 p0, 0x1

    invoke-virtual {v0, p0}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    return-object v0
.end method

.method private hideShareSheet()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareLayout:Landroid/view/ViewGroup;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->getVisibility()I

    move-result v0

    if-nez v0, :cond_0

    new-instance v0, Lcom/android/camera/animation/type/SlideOutOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareLayout:Landroid/view/ViewGroup;

    const/16 v1, 0x50

    invoke-direct {v0, p0, v1}, Lcom/android/camera/animation/type/SlideOutOnSubscribe;-><init>(Landroid/view/View;I)V

    new-instance p0, Ld/h/a/x;

    invoke-direct {p0}, Ld/h/a/x;-><init>()V

    invoke-virtual {v0, p0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setInterpolator(Landroid/view/animation/Interpolator;)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p0

    const/16 v0, 0xc8

    invoke-virtual {p0, v0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setDurationTime(I)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p0

    invoke-static {p0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :cond_0
    return-void
.end method

.method private hideTimbreLayout()V
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMimojiPanelState(I)V

    new-instance v0, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreLayout:Landroid/widget/FrameLayout;

    invoke-direct {v0, v1}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-virtual {v0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->targetGone()Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object v0

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-virtual {v0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreBtn:Landroid/widget/ImageView;

    const/4 v0, 0x1

    invoke-static {p0, v0}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    return-void
.end method

.method private initLiveView(Landroid/view/View;)V
    .locals 3

    const v0, 0x7f0900b5

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/FrameLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewLayout:Landroid/widget/FrameLayout;

    const v0, 0x7f09006b

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mBottomTimbreLayout:Landroid/view/ViewGroup;

    const v0, 0x7f0901ae

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/ui/TextureVideoView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewTextureView:Lcom/android/camera/ui/TextureVideoView;

    const v0, 0x7f09002d

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreBtn:Landroid/widget/ImageView;

    const v0, 0x7f09006a

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/FrameLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreLayout:Landroid/widget/FrameLayout;

    const v0, 0x7f090086

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCoverView:Landroid/widget/ImageView;

    const v0, 0x7f0900af

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ProgressBar;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mConcatProgress:Landroid/widget/ProgressBar;

    const v0, 0x7f0900bd

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ProgressBar;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCombineProgress:Landroid/widget/ProgressBar;

    const v0, 0x7f0900c4

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ProgressBar;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareProgress:Landroid/widget/ProgressBar;

    const v0, 0x7f0900ae

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ProgressBar;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCancelProgress:Landroid/widget/ProgressBar;

    const v0, 0x7f0900b7

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mTimeView:Landroid/widget/TextView;

    const v0, 0x7f0900b9

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/ui/CameraSnapView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getMimojiRecordState()I

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-ne v0, v2, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

    const/16 v2, 0xae

    invoke-virtual {v0, v2, v1, v1}, Lcom/android/camera/ui/CameraSnapView;->setParameters(IZZ)V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, v2, v1, v1}, Lcom/android/camera/ui/CameraSnapView;->setParameters(IZZ)V

    :goto_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

    invoke-virtual {v0}, Lcom/android/camera/ui/CameraSnapView;->hideRoundPaintItem()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

    invoke-virtual {v0, v1}, Lcom/android/camera/ui/CameraSnapView;->setSnapClickEnable(Z)V

    const v0, 0x7f0900b8

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCombine:Landroid/widget/ImageView;

    const v0, 0x7f0900b2

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewBack:Landroid/widget/ImageView;

    const v0, 0x7f0900ba

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewShare:Landroid/widget/ImageView;

    const v0, 0x7f0900b6

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    const v0, 0x7f0900c1

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareLayout:Landroid/view/ViewGroup;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareLayout:Landroid/view/ViewGroup;

    const v2, 0x7f0900c2

    invoke-virtual {v0, v2}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/support/v7/widget/RecyclerView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareRecyclerView:Landroid/support/v7/widget/RecyclerView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareRecyclerView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setFocusable(Z)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareLayout:Landroid/view/ViewGroup;

    const v1, 0x7f0900be

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareCancel:Landroid/view/View;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareCancel:Landroid/view/View;

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewLayout:Landroid/widget/FrameLayout;

    invoke-virtual {v0, p0}, Landroid/widget/FrameLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCombine:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewBack:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewShare:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreBtn:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0900b3

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/FrameLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mBottomActionView:Landroid/view/ViewGroup;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mBottomActionView:Landroid/view/ViewGroup;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    invoke-static {}, Lcom/android/camera/Util;->getBottomHeight()I

    move-result v1

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mBottomActionView:Landroid/view/ViewGroup;

    invoke-virtual {v0, p0}, Landroid/view/ViewGroup;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0900b4

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/RelativeLayout;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mBottomLayout:Landroid/view/ViewGroup;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mBottomLayout:Landroid/view/ViewGroup;

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p1

    check-cast p1, Landroid/view/ViewGroup$MarginLayoutParams;

    sget v0, Lcom/android/camera/Util;->sBottomBarHeight:I

    int-to-float v0, v0

    const v1, 0x3f333333    # 0.7f

    mul-float/2addr v0, v1

    invoke-static {v0}, Ljava/lang/Math;->round(F)I

    move-result v0

    iput v0, p1, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    sget v0, Lcom/android/camera/Util;->sBottomMargin:I

    iput v0, p1, Landroid/view/ViewGroup$MarginLayoutParams;->bottomMargin:I

    sget v0, Lcom/android/camera/Util;->sBottomBarHeight:I

    int-to-float v0, v0

    const v1, 0x3e99999a    # 0.3f

    mul-float/2addr v0, v1

    invoke-static {v0}, Ljava/lang/Math;->round(F)I

    move-result v0

    iput v0, p1, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mTimeView:Landroid/widget/TextView;

    invoke-virtual {p1}, Landroid/widget/TextView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p1

    check-cast p1, Landroid/view/ViewGroup$MarginLayoutParams;

    sget v0, Lcom/android/camera/Util;->sBottomBarHeight:I

    int-to-float v0, v0

    mul-float/2addr v0, v1

    invoke-static {v0}, Ljava/lang/Math;->round(F)I

    move-result v0

    iput v0, p1, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mBottomActionView:Landroid/view/ViewGroup;

    const p1, 0x7f06002c

    invoke-virtual {p0, p1}, Landroid/view/ViewGroup;->setBackgroundResource(I)V

    return-void
.end method

.method private onPreviewResume()V
    .locals 2

    const-string v0, "MIMOJIFullScreen"

    const-string v1, "mimoji void onPreviewResume[]"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCoverBitmap:Landroid/graphics/Bitmap;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mConcatProgress:Landroid/widget/ProgressBar;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->getViewIsVisible(Landroid/view/View;)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewLayout:Landroid/widget/FrameLayout;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCoverView:Landroid/widget/ImageView;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCoverView:Landroid/widget/ImageView;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCoverBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    :goto_0
    return-void
.end method

.method private pausePlay()V
    .locals 2

    const-string v0, "MIMOJIFullScreen"

    const-string v1, "mimoji void pausePlay[]"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xfc

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-interface {v0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;->pausePlay()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    const/4 v0, 0x1

    invoke-static {p0, v0}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    :cond_1
    return-void
.end method

.method private removeTimbreLayout()V
    .locals 3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMimojiPanelState(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/android/camera/fragment/BaseFragment;->unRegisterProtocol()V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getChildFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->getFragmentTag()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-virtual {v2}, Lcom/android/camera/fragment/BaseFragment;->getFragmentTag()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/fragment/FragmentUtils;->removeFragmentByTag(Landroid/support/v4/app/FragmentManager;Ljava/lang/String;)Z

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    :cond_0
    return-void
.end method

.method private resumePlay()V
    .locals 3

    const-string v0, "MIMOJIFullScreen"

    const-string v1, "mimoji void resumePlay[]"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xfc

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;

    if-eqz v1, :cond_1

    invoke-interface {v1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;->resumePlay()Z

    move-result v1

    if-eqz v1, :cond_0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    const/4 v0, 0x0

    invoke-static {p0, v0}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    goto :goto_0

    :cond_0
    const-string p0, "mimoji void resumePlay fail"

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_1
    :goto_0
    return-void
.end method

.method private shareMore()V
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->getShareIntent()Landroid/content/Intent;

    move-result-object v0

    const v1, 0x7f0f013f

    invoke-virtual {p0, v1}, Landroid/support/v4/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/content/Intent;->createChooser(Landroid/content/Intent;Ljava/lang/CharSequence;)Landroid/content/Intent;

    move-result-object v0

    :try_start_0
    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-virtual {p0, v0}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V
    :try_end_0
    .catch Landroid/content/ActivityNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p0

    const-string v0, "MIMOJIFullScreen"

    const-string v1, "failed to share video shareMore "

    invoke-static {v0, v1, p0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_0
    return-void
.end method

.method private showExitConfirm()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewBack:Landroid/widget/ImageView;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCancelProgress:Landroid/widget/ProgressBar;

    const/16 v2, 0x8

    invoke-virtual {v0, v2}, Landroid/widget/ProgressBar;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mExitDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_0

    return-void

    :cond_0
    const-string v0, "exit"

    invoke-static {v0}, Lcom/android/camera/statistic/CameraStatUtils;->trackLiveClick(Ljava/lang/String;)V

    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v0, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const v2, 0x7f0f013e

    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setMessage(I)Landroid/app/AlertDialog$Builder;

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    const v1, 0x7f0f013d

    new-instance v2, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen$1;

    invoke-direct {v2, p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen$1;-><init>(Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;)V

    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    const v1, 0x7f0f0445

    new-instance v2, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen$2;

    invoke-direct {v2, p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen$2;-><init>(Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;)V

    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mExitDialog:Landroid/app/AlertDialog;

    return-void
.end method

.method private showShareSheet()V
    .locals 13

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->getShareIntent()Landroid/content/Intent;

    move-result-object v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    return-void

    :cond_0
    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v1

    const/high16 v2, 0x10000

    invoke-virtual {v1, v0, v2}, Landroid/content/pm/PackageManager;->queryIntentActivities(Landroid/content/Intent;I)Ljava/util/List;

    move-result-object v0

    const-string v1, "MIMOJIFullScreen"

    if-eqz v0, :cond_c

    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v2

    if-eqz v2, :cond_1

    goto/16 :goto_7

    :cond_1
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    sget-object v3, Lcom/android/camera/constant/ShareConstant;->DEFAULT_SHARE_LIST:[Ljava/lang/String;

    array-length v3, v3

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_2
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    const/4 v5, 0x0

    if-eqz v4, :cond_5

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/content/pm/ResolveInfo;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v6

    if-ne v6, v3, :cond_3

    goto :goto_2

    :cond_3
    move v8, v5

    :goto_1
    if-ge v8, v3, :cond_2

    sget-object v5, Lcom/android/camera/constant/ShareConstant;->DEFAULT_SHARE_LIST:[Ljava/lang/String;

    aget-object v5, v5, v8

    iget-object v6, v4, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    iget-object v6, v6, Landroid/content/pm/ActivityInfo;->name:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_4

    new-instance v5, Lcom/android/camera/fragment/fullscreen/ShareInfo;

    iget-object v6, v4, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    iget-object v9, v6, Landroid/content/pm/ActivityInfo;->packageName:Ljava/lang/String;

    iget-object v4, v4, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    iget-object v10, v4, Landroid/content/pm/ActivityInfo;->name:Ljava/lang/String;

    sget-object v4, Lcom/android/camera/constant/ShareConstant;->DEFAULT_SHARE_LIST_ICON:[I

    aget v11, v4, v8

    sget-object v4, Lcom/android/camera/constant/ShareConstant;->DEFAULT_SHARE_LIST_NAME:[I

    aget v12, v4, v8

    move-object v7, v5

    invoke-direct/range {v7 .. v12}, Lcom/android/camera/fragment/fullscreen/ShareInfo;-><init>(ILjava/lang/String;Ljava/lang/String;II)V

    invoke-interface {v2, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    :cond_4
    add-int/lit8 v8, v8, 0x1

    goto :goto_1

    :cond_5
    :goto_2
    invoke-interface {v2}, Ljava/util/List;->isEmpty()Z

    move-result v0

    const/4 v4, 0x1

    if-eqz v0, :cond_6

    const-string v0, "no default share entry"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :goto_3
    move v0, v4

    goto :goto_4

    :cond_6
    invoke-interface {v2, v5}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/fragment/fullscreen/ShareInfo;

    iget v0, v0, Lcom/android/camera/fragment/fullscreen/ShareInfo;->index:I

    if-le v0, v4, :cond_7

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v0

    const/4 v6, 0x2

    if-ge v0, v6, :cond_7

    const-string v0, "not match default share strategy"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_3

    :cond_7
    move v0, v5

    :goto_4
    if-eqz v0, :cond_8

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->shareMore()V

    return-void

    :cond_8
    new-instance v0, Lcom/android/camera/fragment/fullscreen/ShareInfo;

    add-int/lit8 v7, v3, 0x1

    const v10, 0x7f080167

    const v11, 0x7f0f0038

    const-string v8, "com.android.camera"

    const-string v9, "more"

    move-object v6, v0

    invoke-direct/range {v6 .. v11}, Lcom/android/camera/fragment/fullscreen/ShareInfo;-><init>(ILjava/lang/String;Ljava/lang/String;II)V

    invoke-interface {v2, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen$3;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen$3;-><init>(Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;)V

    invoke-static {v2, v0}, Ljava/util/Collections;->sort(Ljava/util/List;Ljava/util/Comparator;)V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f07011d

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    sub-int v3, v1, v0

    int-to-float v3, v3

    const/high16 v4, 0x40b00000    # 5.5f

    div-float/2addr v3, v4

    float-to-int v3, v3

    mul-int/lit8 v4, v0, 0x2

    sub-int/2addr v1, v4

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v4

    div-int/2addr v1, v4

    invoke-static {v1, v3}, Ljava/lang/Math;->max(II)I

    move-result v1

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareAdapter:Lcom/android/camera/fragment/fullscreen/ShareAdapter;

    if-eqz v3, :cond_a

    invoke-virtual {v3}, Lcom/android/camera/fragment/fullscreen/ShareAdapter;->getItemCount()I

    move-result v3

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v4

    if-eq v3, v4, :cond_9

    goto :goto_5

    :cond_9
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareAdapter:Lcom/android/camera/fragment/fullscreen/ShareAdapter;

    invoke-virtual {v0, v2}, Lcom/android/camera/fragment/fullscreen/ShareAdapter;->setShareInfoList(Ljava/util/List;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareAdapter:Lcom/android/camera/fragment/fullscreen/ShareAdapter;

    invoke-virtual {v0}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    goto :goto_6

    :cond_a
    :goto_5
    new-instance v3, Lcom/android/camera/fragment/fullscreen/ShareAdapter;

    invoke-direct {v3, v2, p0, v1}, Lcom/android/camera/fragment/fullscreen/ShareAdapter;-><init>(Ljava/util/List;Landroid/view/View$OnClickListener;I)V

    iput-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareAdapter:Lcom/android/camera/fragment/fullscreen/ShareAdapter;

    new-instance v1, Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v2

    const-string v3, "FullScreen"

    invoke-direct {v1, v2, v3}, Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    invoke-virtual {v1, v5}, Landroid/support/v7/widget/LinearLayoutManager;->setOrientation(I)V

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareRecyclerView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {v2, v1}, Landroid/support/v7/widget/RecyclerView;->setLayoutManager(Landroid/support/v7/widget/RecyclerView$LayoutManager;)V

    new-instance v1, Lcom/android/camera/fragment/RecyclerAdapterWrapper;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareAdapter:Lcom/android/camera/fragment/fullscreen/ShareAdapter;

    invoke-direct {v1, v2}, Lcom/android/camera/fragment/RecyclerAdapterWrapper;-><init>(Landroid/support/v7/widget/RecyclerView$Adapter;)V

    new-instance v2, Landroid/widget/Space;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-direct {v2, v3}, Landroid/widget/Space;-><init>(Landroid/content/Context;)V

    invoke-virtual {v2, v0}, Landroid/widget/Space;->setMinimumWidth(I)V

    invoke-virtual {v1, v2}, Lcom/android/camera/fragment/RecyclerAdapterWrapper;->addHeader(Landroid/view/View;)V

    new-instance v2, Landroid/widget/Space;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-direct {v2, v3}, Landroid/widget/Space;-><init>(Landroid/content/Context;)V

    invoke-virtual {v2, v0}, Landroid/widget/Space;->setMinimumWidth(I)V

    invoke-virtual {v1, v2}, Lcom/android/camera/fragment/RecyclerAdapterWrapper;->addFooter(Landroid/view/View;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareRecyclerView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {v0, v1}, Landroid/support/v7/widget/RecyclerView;->setAdapter(Landroid/support/v7/widget/RecyclerView$Adapter;)V

    :goto_6
    sget v0, Lcom/android/camera/Util;->sNavigationBarHeight:I

    if-lez v0, :cond_b

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareLayout:Landroid/view/ViewGroup;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    sget v1, Lcom/android/camera/Util;->sNavigationBarHeight:I

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->bottomMargin:I

    :cond_b
    new-instance v0, Lcom/android/camera/animation/type/SlideInOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareLayout:Landroid/view/ViewGroup;

    const/16 v1, 0x50

    invoke-direct {v0, p0, v1}, Lcom/android/camera/animation/type/SlideInOnSubscribe;-><init>(Landroid/view/View;I)V

    new-instance p0, Ld/h/a/z;

    invoke-direct {p0}, Ld/h/a/z;-><init>()V

    invoke-virtual {v0, p0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setInterpolator(Landroid/view/animation/Interpolator;)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p0

    const/16 v0, 0xc8

    invoke-virtual {p0, v0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setDurationTime(I)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p0

    invoke-static {p0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    return-void

    :cond_c
    :goto_7
    const-string p0, "no IntentActivities"

    invoke-static {v1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private showTimbreLayout()V
    .locals 6

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMimojiPanelState(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreLayout:Landroid/widget/FrameLayout;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    if-nez v0, :cond_0

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-direct {v0}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;-><init>()V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-virtual {v0}, Lcom/android/camera/fragment/BaseFragment;->registerProtocol()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    invoke-virtual {v0, v2}, Lcom/android/camera/fragment/BaseFragment;->setDegree(I)V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getChildFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object v0

    const v2, 0x7f09006a

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->getFragmentTag()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v5, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-virtual {v5}, Lcom/android/camera/fragment/BaseFragment;->getFragmentTag()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v0, v2, v3, v4}, Lcom/android/camera/fragment/FragmentUtils;->addFragmentWithTag(Landroid/support/v4/app/FragmentManager;ILandroid/support/v4/app/Fragment;Ljava/lang/String;)V

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->refreshMimojiList()I

    new-instance v0, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-virtual {v2}, Landroid/support/v4/app/Fragment;->getView()Landroid/view/View;

    move-result-object v2

    invoke-direct {v0, v2}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-virtual {v0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :goto_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreBtn:Landroid/widget/ImageView;

    const/4 v2, 0x0

    invoke-static {v0, v2}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-virtual {p0, v1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->setIsNeedClickHide(Z)V

    return-void
.end method

.method private startCombine()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    invoke-static {v0}, Lcom/android/camera/module/impl/component/FileUtils;->checkFileConsist(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    :try_start_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/android/camera/storage/Storage;->DIRECTORY:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v1, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "MIMOJI_"

    const-string v2, "mp4"

    invoke-static {v1, v2}, Lcom/android/camera/module/impl/component/FileUtils;->createtFileName(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/io/File;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    invoke-direct {v1, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    new-instance v2, Ljava/io/File;

    invoke-direct {v2, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {v1, v2}, Lcom/android/camera/module/impl/component/FileUtils;->copyFile(Ljava/io/File;Ljava/io/File;)V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineSuccess()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    :goto_0
    return-void
.end method

.method private startPlay()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mConcatProgress:Landroid/widget/ProgressBar;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    invoke-static {p0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xfc

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-interface {p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;->startPlay()V

    return-void
.end method

.method private startShare(Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;)V
    .locals 1

    new-instance v0, Landroid/content/ComponentName;

    invoke-direct {v0, p1, p2}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    new-instance p1, Landroid/content/Intent;

    const-string p2, "android.intent.action.SEND"

    invoke-direct {p1, p2}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    invoke-virtual {p1, v0}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    const-string p2, "android.intent.extra.STREAM"

    invoke-virtual {p1, p2, p3}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    const/4 p2, 0x2

    invoke-static {p2}, Lcom/android/camera/Util;->convertOutputFormatToMimeType(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/content/Intent;->setType(Ljava/lang/String;)Landroid/content/Intent;

    const/4 p2, 0x1

    invoke-virtual {p1, p2}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    const p2, 0x7f0f013f

    invoke-virtual {p0, p2}, Landroid/support/v4/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-static {p1, p2}, Landroid/content/Intent;->createChooser(Landroid/content/Intent;Ljava/lang/CharSequence;)Landroid/content/Intent;

    move-result-object p1

    :try_start_0
    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-virtual {p0, p1}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V
    :try_end_0
    .catch Landroid/content/ActivityNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p0

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string p2, "failed to share video "

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string p2, "MIMOJIFullScreen"

    invoke-static {p2, p1, p0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_0
    return-void
.end method


# virtual methods
.method public concatResult(Ljava/lang/String;)V
    .locals 2

    const-string v0, "MIMOJIFullScreen"

    const-string v1, "mimoji void concatResult[]"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/a;

    invoke-direct {v1, p0, p1}, Lcom/android/camera/features/mimoji2/fragment/a;-><init>(Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method

.method public getFragmentInto()I
    .locals 0

    const/16 p0, 0xfa4

    return p0
.end method

.method protected getLayoutResourceId()I
    .locals 0

    const p0, 0x7f0b0045

    return p0
.end method

.method public getMimojiVideoSavePath()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    return-object p0
.end method

.method protected initView(Landroid/view/View;)V
    .locals 1

    const v0, 0x7f090103

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/view/ViewStub;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mLiveViewStub:Landroid/view/ViewStub;

    return-void
.end method

.method public isMimojiRecordPreviewShowing()Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mLiveViewLayout:Landroid/view/View;

    invoke-static {p0}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->getViewIsVisible(Landroid/view/View;)Z

    move-result p0

    return p0
.end method

.method public synthetic l(Ljava/lang/String;)V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->canProvide()Z

    move-result v0

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewLayout:Landroid/widget/FrameLayout;

    if-nez v0, :cond_0

    goto/16 :goto_3

    :cond_0
    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    invoke-static {v0}, Lcom/android/camera/Util;->getPreviewRect(Landroid/content/Context;)Landroid/graphics/Rect;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewLayout:Landroid/widget/FrameLayout;

    invoke-virtual {v1}, Landroid/widget/FrameLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v1

    check-cast v1, Landroid/widget/FrameLayout$LayoutParams;

    iget v2, v0, Landroid/graphics/Rect;->top:I

    iput v2, v1, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    invoke-virtual {v0}, Landroid/graphics/Rect;->height()I

    move-result v0

    iput v0, v1, Landroid/widget/FrameLayout$LayoutParams;->height:I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewLayout:Landroid/widget/FrameLayout;

    invoke-virtual {v0, v1}, Landroid/widget/FrameLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewLayout:Landroid/widget/FrameLayout;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mBottomTimbreLayout:Landroid/view/ViewGroup;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewTextureView:Lcom/android/camera/ui/TextureVideoView;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreBtn:Landroid/widget/ImageView;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreBtn:Landroid/widget/ImageView;

    const/4 v2, 0x0

    if-eqz v0, :cond_3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getCurrentMimojiTimbreInfo()Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;

    move-result-object v0

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    move v1, v2

    :goto_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreBtn:Landroid/widget/ImageView;

    if-eqz v1, :cond_2

    const v1, 0x7f080187

    goto :goto_1

    :cond_2
    const v1, 0x7f080186

    :goto_1
    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    :cond_3
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xfc

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;

    if-eqz v0, :cond_4

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewTextureView:Lcom/android/camera/ui/TextureVideoView;

    invoke-interface {v0, v1, p1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;->init(Lcom/android/camera/ui/TextureVideoView;Ljava/lang/String;)Z

    move-result v2

    :cond_4
    if-eqz v2, :cond_5

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->startPlay()V

    goto :goto_2

    :cond_5
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    :goto_2
    return-void

    :cond_6
    :goto_3
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    return-void
.end method

.method public notifyAfterFrameAvailable(I)V
    .locals 2

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->notifyAfterFrameAvailable(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    if-nez p1, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->Bd()Z

    move-result p1

    if-eqz p1, :cond_0

    new-instance p1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-direct {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-virtual {p1}, Lcom/android/camera/fragment/BaseFragment;->registerProtocol()V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    invoke-virtual {p1, v0}, Lcom/android/camera/fragment/BaseFragment;->setDegree(I)V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getChildFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object p1

    const v0, 0x7f09006e

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->getFragmentTag()Ljava/lang/String;

    move-result-object v1

    invoke-static {p1, v0, p0, v1}, Lcom/android/camera/fragment/FragmentUtils;->addFragmentWithTag(Landroid/support/v4/app/FragmentManager;ILandroid/support/v4/app/Fragment;Ljava/lang/String;)V

    :cond_0
    return-void
.end method

.method public notifyDataChanged(II)V
    .locals 1

    invoke-super {p0, p1, p2}, Lcom/android/camera/fragment/BaseFragment;->notifyDataChanged(II)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    if-nez p1, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->Bd()Z

    move-result p1

    if-eqz p1, :cond_0

    new-instance p1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-direct {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-virtual {p1}, Lcom/android/camera/fragment/BaseFragment;->registerProtocol()V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    iget p2, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    invoke-virtual {p1, p2}, Lcom/android/camera/fragment/BaseFragment;->setDegree(I)V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getChildFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object p1

    const p2, 0x7f09006e

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiEdit2:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->getFragmentTag()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, p2, p0, v0}, Lcom/android/camera/fragment/FragmentUtils;->addFragmentWithTag(Landroid/support/v4/app/FragmentManager;ILandroid/support/v4/app/Fragment;Ljava/lang/String;)V

    :cond_0
    return-void
.end method

.method public onBackEvent(I)Z
    .locals 3

    const/4 v0, 0x0

    const/4 v1, 0x1

    if-eq p1, v1, :cond_0

    return v0

    :cond_0
    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mLiveViewLayout:Landroid/view/View;

    invoke-static {v2}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->getViewIsVisible(Landroid/view/View;)Z

    move-result v2

    if-eqz v2, :cond_2

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareLayout:Landroid/view/ViewGroup;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->getViewIsVisible(Landroid/view/View;)Z

    move-result p1

    if-eqz p1, :cond_1

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->hideShareSheet()V

    goto :goto_0

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->showExitConfirm()V

    :goto_0
    return v1

    :cond_2
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->getFragmentMiMoji()Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    move-result-object p0

    if-eqz p0, :cond_3

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->onBackEvent(I)Z

    move-result p0

    return p0

    :cond_3
    return v0
.end method

.method public onClick(Landroid/view/View;)V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mConcatProgress:Landroid/widget/ProgressBar;

    invoke-virtual {v0}, Landroid/widget/ProgressBar;->getVisibility()I

    move-result v0

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCombineProgress:Landroid/widget/ProgressBar;

    invoke-virtual {v0}, Landroid/widget/ProgressBar;->getVisibility()I

    move-result v0

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareProgress:Landroid/widget/ProgressBar;

    invoke-virtual {v0}, Landroid/widget/ProgressBar;->getVisibility()I

    move-result v0

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCancelProgress:Landroid/widget/ProgressBar;

    invoke-virtual {v0}, Landroid/widget/ProgressBar;->getVisibility()I

    move-result v0

    if-nez v0, :cond_0

    goto/16 :goto_0

    :cond_0
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const/4 v1, 0x0

    const/16 v2, 0x8

    sparse-switch v0, :sswitch_data_0

    goto/16 :goto_0

    :sswitch_0
    invoke-virtual {p1}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/fragment/fullscreen/ShareInfo;

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->hideShareSheet()V

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "live_preview_shareTo_"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p1, Lcom/android/camera/fragment/fullscreen/ShareInfo;->index:I

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/android/camera/statistic/CameraStatUtils;->trackLiveClick(Ljava/lang/String;)V

    iget-object v0, p1, Lcom/android/camera/fragment/fullscreen/ShareInfo;->className:Ljava/lang/String;

    const-string v2, "more"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->shareMore()V

    goto/16 :goto_0

    :cond_1
    const/4 v0, 0x0

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    invoke-static {v2}, Lcom/android/camera/module/impl/component/FileUtils;->checkFileConsist(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    :cond_2
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_3

    const/4 v2, 0x2

    :try_start_0
    invoke-static {v2, v0}, Lcom/android/camera/Util;->genContentValues(ILjava/lang/String;)Landroid/content/ContentValues;

    move-result-object v2

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v3

    check-cast v3, Lcom/android/camera/ActivityBase;

    invoke-interface {v3}, Lcom/android/camera/AppController;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v3

    invoke-virtual {v3, v0, v2, v1}, Lcom/android/camera/storage/ImageSaver;->addVideoSync(Ljava/lang/String;Landroid/content/ContentValues;Z)Landroid/net/Uri;

    move-result-object v0

    iget-object v1, p1, Lcom/android/camera/fragment/fullscreen/ShareInfo;->packageName:Ljava/lang/String;

    iget-object p1, p1, Lcom/android/camera/fragment/fullscreen/ShareInfo;->className:Ljava/lang/String;

    invoke-direct {p0, v1, p1, v0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->startShare(Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto/16 :goto_0

    :catch_0
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    goto/16 :goto_0

    :cond_3
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    goto/16 :goto_0

    :sswitch_1
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->hideShareSheet()V

    goto :goto_0

    :sswitch_2
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mConcatProgress:Landroid/widget/ProgressBar;

    invoke-virtual {p1}, Landroid/widget/ProgressBar;->getVisibility()I

    move-result p1

    const-string v0, "MIMOJIFullScreen"

    if-nez p1, :cond_4

    const-string p0, "concat not finished, skip share~"

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_4
    const-string p1, "live_share"

    invoke-static {p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackLiveClick(Ljava/lang/String;)V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->checkAndShare()Z

    move-result p1

    if-nez p1, :cond_6

    const-string p1, "uri null"

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    goto :goto_0

    :sswitch_3
    const-string p1, "live_save"

    invoke-static {p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackLiveClick(Ljava/lang/String;)V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->pausePlay()V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    invoke-virtual {p1, v2}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCombine:Landroid/widget/ImageView;

    invoke-virtual {p1, v2}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCombineProgress:Landroid/widget/ProgressBar;

    const/4 v0, 0x1

    invoke-static {p1, v0}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->startCombine()V

    goto :goto_0

    :sswitch_4
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->hideShareSheet()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->startPlay()V

    goto :goto_0

    :sswitch_5
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mFragmentMimojiBottomList:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    if-eqz p1, :cond_5

    invoke-virtual {p1}, Landroid/support/v4/app/Fragment;->getView()Landroid/view/View;

    move-result-object p1

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->getViewIsVisible(Landroid/view/View;)Z

    move-result p1

    if-eqz p1, :cond_5

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->hideTimbreLayout()V

    return-void

    :cond_5
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->pausePlay()V

    goto :goto_0

    :sswitch_6
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCancelProgress:Landroid/widget/ProgressBar;

    invoke-virtual {p1, v1}, Landroid/widget/ProgressBar;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewBack:Landroid/widget/ImageView;

    invoke-virtual {p1, v2}, Landroid/widget/ImageView;->setVisibility(I)V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->showExitConfirm()V

    goto :goto_0

    :sswitch_7
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->showTimbreLayout()V

    :cond_6
    :goto_0
    :sswitch_8
    return-void

    :sswitch_data_0
    .sparse-switch
        0x7f09002d -> :sswitch_7
        0x7f0900b2 -> :sswitch_6
        0x7f0900b3 -> :sswitch_8
        0x7f0900b5 -> :sswitch_5
        0x7f0900b6 -> :sswitch_4
        0x7f0900b8 -> :sswitch_3
        0x7f0900b9 -> :sswitch_3
        0x7f0900ba -> :sswitch_2
        0x7f0900be -> :sswitch_1
        0x7f0900c0 -> :sswitch_0
    .end sparse-switch
.end method

.method public onCombineError()V
    .locals 2

    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v1, 0xb8

    if-ne v0, v1, :cond_3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiCreate()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const-string v0, "MIMOJIFullScreen"

    const-string v1, "onCombineError"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->quitMimojiRecordPreview()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v1, 0xa1

    invoke-virtual {p0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$CameraAction;

    if-eqz p0, :cond_1

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->onReviewCancelClicked()V

    goto :goto_0

    :cond_1
    const-string p0, "mimoji void onCombineError[] cameraAction null"

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v1, 0xd4

    invoke-virtual {p0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$RecordState;

    if-eqz p0, :cond_2

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$RecordState;->onFinish()V

    goto :goto_0

    :cond_2
    const-string p0, "mimoji void onCombineError[] recordState null"

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_3
    :goto_0
    return-void
.end method

.method public onCombineSuccess()V
    .locals 2

    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v1, 0xb8

    if-ne v0, v1, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiCreate()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const-string v0, "MIMOJIFullScreen"

    const-string v1, "combineSuccess"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->quitMimojiRecordPreview()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xa1

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$CameraAction;

    if-eqz p0, :cond_1

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->onReviewDoneClicked()V

    :cond_1
    :goto_0
    return-void
.end method

.method public onMimojiSaveToLocalFinished(Landroid/net/Uri;)V
    .locals 1

    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "MIMOJI onMimojiSaveToLocalFinished"

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p1, "MIMOJIFullScreen"

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onPause()V
    .locals 0

    invoke-super {p0}, Landroid/support/v4/app/Fragment;->onPause()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->pausePlay()V

    return-void
.end method

.method public onResume()V
    .locals 0

    invoke-super {p0}, Lcom/android/camera/fragment/BaseFragment;->onResume()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onPreviewResume()V

    return-void
.end method

.method public onStop()V
    .locals 0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    invoke-super {p0}, Lcom/android/camera/fragment/BaseFragment;->onStop()V

    return-void
.end method

.method public provideAnimateElement(ILjava/util/List;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I",
            "Ljava/util/List<",
            "Lio/reactivex/Completable;",
            ">;I)V"
        }
    .end annotation

    invoke-super {p0, p1, p2, p3}, Lcom/android/camera/fragment/BaseFragment;->provideAnimateElement(ILjava/util/List;I)V

    const/4 p1, 0x3

    if-ne p3, p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mExitDialog:Landroid/app/AlertDialog;

    if-eqz p1, :cond_0

    invoke-virtual {p1}, Landroid/app/AlertDialog;->dismiss()V

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mExitDialog:Landroid/app/AlertDialog;

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    :cond_1
    return-void
.end method

.method protected provideEnterAnimation(I)Landroid/view/animation/Animation;
    .locals 0

    const/4 p0, 0x0

    return-object p0
.end method

.method protected provideExitAnimation(I)Landroid/view/animation/Animation;
    .locals 0

    const/4 p0, 0x0

    return-object p0
.end method

.method public provideRotateItem(Ljava/util/List;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Landroid/view/View;",
            ">;I)V"
        }
    .end annotation

    invoke-super {p0, p1, p2}, Lcom/android/camera/fragment/BaseFragment;->provideRotateItem(Ljava/util/List;I)V

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mLiveViewLayout:Landroid/view/View;

    if-eqz p2, :cond_0

    invoke-virtual {p2}, Landroid/view/View;->getVisibility()I

    move-result p2

    if-nez p2, :cond_0

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCombine:Landroid/widget/ImageView;

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewBack:Landroid/widget/ImageView;

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewShare:Landroid/widget/ImageView;

    invoke-interface {p1, p0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_0
    return-void
.end method

.method public quitMimojiRecordPreview()V
    .locals 2

    const-string v0, "MIMOJIFullScreen"

    const-string v1, "quitMimojiRecordPreview "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->setPreviewCover(Landroid/graphics/Bitmap;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mConcatProgress:Landroid/widget/ProgressBar;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCombineProgress:Landroid/widget/ProgressBar;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareProgress:Landroid/widget/ProgressBar;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mLiveViewLayout:Landroid/view/View;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->removeTimbreLayout()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xfc

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;

    if-eqz p0, :cond_0

    invoke-interface {p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;->onDestory()V

    :cond_0
    return-void
.end method

.method protected register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    const/16 v0, 0xf9

    invoke-interface {p1, v0, p0}, Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->registerBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    return-void
.end method

.method public setPreviewCover(Landroid/graphics/Bitmap;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCoverBitmap:Landroid/graphics/Bitmap;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->isRecycled()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCoverBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->recycle()V

    :cond_0
    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCoverBitmap:Landroid/graphics/Bitmap;

    return-void
.end method

.method public startMimojiRecordPreview()V
    .locals 4

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mSavedPath:Ljava/lang/String;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMimojiPanelState(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mLiveViewLayout:Landroid/view/View;

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mLiveViewStub:Landroid/view/ViewStub;

    invoke-virtual {v0}, Landroid/view/ViewStub;->inflate()Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mLiveViewLayout:Landroid/view/View;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mLiveViewLayout:Landroid/view/View;

    invoke-direct {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->initLiveView(Landroid/view/View;)V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewLayout:Landroid/widget/FrameLayout;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCombineProgress:Landroid/widget/ProgressBar;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareProgress:Landroid/widget/ProgressBar;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCancelProgress:Landroid/widget/ProgressBar;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mShareLayout:Landroid/view/ViewGroup;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mBottomTimbreLayout:Landroid/view/ViewGroup;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreLayout:Landroid/widget/FrameLayout;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mMimojiChangeTimbreBtn:Landroid/widget/ImageView;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mLiveViewLayout:Landroid/view/View;

    const/4 v2, 0x1

    invoke-static {v0, v2}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    int-to-float v3, v3

    invoke-static {v0, v3}, Landroid/support/v4/view/ViewCompat;->setRotation(Landroid/view/View;F)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    int-to-float v3, v3

    invoke-static {v0, v3}, Landroid/support/v4/view/ViewCompat;->setRotation(Landroid/view/View;F)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCombine:Landroid/widget/ImageView;

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    int-to-float v3, v3

    invoke-static {v0, v3}, Landroid/support/v4/view/ViewCompat;->setRotation(Landroid/view/View;F)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewBack:Landroid/widget/ImageView;

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    int-to-float v3, v3

    invoke-static {v0, v3}, Landroid/support/v4/view/ViewCompat;->setRotation(Landroid/view/View;F)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewShare:Landroid/widget/ImageView;

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    int-to-float v3, v3

    invoke-static {v0, v3}, Landroid/support/v4/view/ViewCompat;->setRotation(Landroid/view/View;F)V

    new-instance v0, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

    invoke-direct {v0, v3}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-virtual {v0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    new-instance v0, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewCombine:Landroid/widget/ImageView;

    invoke-direct {v0, v3}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-virtual {v0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    new-instance v0, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewBack:Landroid/widget/ImageView;

    invoke-direct {v0, v3}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-virtual {v0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    new-instance v0, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    invoke-direct {v0, v3}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-virtual {v0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    check-cast v0, Lcom/android/camera/ActivityBase;

    invoke-virtual {v0}, Lcom/android/camera/ActivityBase;->startFromKeyguard()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewShare:Landroid/widget/ImageView;

    const/16 v3, 0x8

    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_0

    :cond_1
    new-instance v0, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewShare:Landroid/widget/ImageView;

    invoke-direct {v0, v3}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-virtual {v0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :goto_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v3, 0xf6

    invoke-virtual {v0, v3}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;

    if-eqz v0, :cond_2

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mTimeView:Landroid/widget/TextView;

    invoke-interface {v0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;->getTimeValue()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v3, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mTimeView:Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    :cond_2
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mPreviewStart:Landroid/widget/ImageView;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mConcatProgress:Landroid/widget/ProgressBar;

    invoke-static {p0, v2}, Lcom/android/camera/features/mimoji2/utils/MimojiViewUtil;->setViewVisible(Landroid/view/View;Z)Z

    return-void
.end method

.method public startMimojiRecordSaving()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->mCameraSnapView:Lcom/android/camera/ui/CameraSnapView;

    invoke-virtual {p0}, Lcom/android/camera/ui/CameraSnapView;->performClick()Z

    return-void
.end method

.method protected unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    const/16 v0, 0xf9

    invoke-interface {p1, v0, p0}, Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->removeTimbreLayout()V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->unRegisterBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    return-void
.end method
