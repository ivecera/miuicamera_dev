.class public Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;
.super Lcom/android/camera/fragment/BaseFragment;
.source "FragmentWatermark.java"

# interfaces
.implements Lcom/android/camera/protocol/ModeProtocol$WatermarkProtocol;
.implements Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;


# static fields
.field public static final FRAGMENT_INFO:I = 0xfffe

.field private static final TAG:Ljava/lang/String; = "FragmentWatermark"


# instance fields
.field private mComponentAIWatermark:Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

.field private mCurrentState:I

.field private mPagerAdapter:Lcom/android/camera/fragment/BaseFragmentPagerAdapter;

.field private mViewPager:Lcom/android/camera/ui/NoScrollViewPager;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/BaseFragment;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mCurrentState:I

    return-void
.end method

.method static synthetic b(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 0

    const/4 p0, 0x1

    return p0
.end method

.method private checkAIWatermark()V
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAIWatermark()Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->needActive()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->resetFragment()V

    :cond_0
    return-void
.end method

.method private initViewPager()V
    .locals 5

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iget-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mComponentAIWatermark:Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getItems()Ljava/util/List;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    const/4 v3, 0x2

    if-eqz v2, :cond_5

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/camera/data/data/ComponentDataItem;

    iget-object v2, v2, Lcom/android/camera/data/data/ComponentDataItem;->mValue:Ljava/lang/String;

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v2

    if-eqz v2, :cond_4

    const/4 v4, 0x1

    if-eq v2, v4, :cond_3

    if-eq v2, v3, :cond_2

    const/4 v3, 0x3

    if-eq v2, v3, :cond_1

    const/4 v3, 0x4

    if-eq v2, v3, :cond_0

    goto :goto_0

    :cond_0
    new-instance v2, Lcom/android/camera/fragment/aiwatermark/FragmentCityWatermark;

    invoke-direct {v2}, Lcom/android/camera/fragment/aiwatermark/FragmentCityWatermark;-><init>()V

    invoke-interface {v0, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    :cond_1
    new-instance v2, Lcom/android/camera/fragment/aiwatermark/FragmentASDWatermark;

    invoke-direct {v2}, Lcom/android/camera/fragment/aiwatermark/FragmentASDWatermark;-><init>()V

    invoke-interface {v0, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    :cond_2
    new-instance v2, Lcom/android/camera/fragment/aiwatermark/FragmentFestivalWatermark;

    invoke-direct {v2}, Lcom/android/camera/fragment/aiwatermark/FragmentFestivalWatermark;-><init>()V

    invoke-interface {v0, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    :cond_3
    new-instance v2, Lcom/android/camera/fragment/aiwatermark/FragmentSpotsWatermark;

    invoke-direct {v2}, Lcom/android/camera/fragment/aiwatermark/FragmentSpotsWatermark;-><init>()V

    invoke-interface {v0, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    :cond_4
    new-instance v2, Lcom/android/camera/fragment/aiwatermark/FragmentGenWatermark;

    invoke-direct {v2}, Lcom/android/camera/fragment/aiwatermark/FragmentGenWatermark;-><init>()V

    invoke-interface {v0, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    :cond_5
    new-instance v1, Lcom/android/camera/fragment/BaseFragmentPagerAdapter;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getChildFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object v2

    invoke-direct {v1, v2, v0}, Lcom/android/camera/fragment/BaseFragmentPagerAdapter;-><init>(Landroid/support/v4/app/FragmentManager;Ljava/util/List;)V

    iput-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mPagerAdapter:Lcom/android/camera/fragment/BaseFragmentPagerAdapter;

    iget-object v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mViewPager:Lcom/android/camera/ui/NoScrollViewPager;

    iget-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mPagerAdapter:Lcom/android/camera/fragment/BaseFragmentPagerAdapter;

    invoke-virtual {v0, v1}, Landroid/support/v4/view/ViewPager;->setAdapter(Landroid/support/v4/view/PagerAdapter;)V

    iget-object v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mViewPager:Lcom/android/camera/ui/NoScrollViewPager;

    invoke-virtual {v0, v3}, Landroid/support/v4/view/ViewPager;->setOffscreenPageLimit(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mViewPager:Lcom/android/camera/ui/NoScrollViewPager;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setFocusable(Z)V

    iget-object p0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mViewPager:Lcom/android/camera/ui/NoScrollViewPager;

    sget-object v0, Lcom/android/camera/fragment/aiwatermark/b;->INSTANCE:Lcom/android/camera/fragment/aiwatermark/b;

    invoke-virtual {p0, v0}, Landroid/view/ViewGroup;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    return-void
.end method

.method private initWatermarkType()V
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAIWatermark()Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mComponentAIWatermark:Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    const/4 v0, 0x1

    iput v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mCurrentState:I

    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->initViewPager()V

    iget-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mComponentAIWatermark:Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getCurrentType()Ljava/lang/String;

    move-result-object v1

    invoke-direct {p0, v1, v0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->initWatermarkType(Ljava/lang/String;Z)V

    invoke-direct {p0, v1}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->setViewPagerPageByType(Ljava/lang/String;)V

    return-void
.end method

.method private initWatermarkType(Ljava/lang/String;Z)V
    .locals 0

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p2

    if-eqz p2, :cond_0

    return-void

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mComponentAIWatermark:Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->setCurrentType(Ljava/lang/String;)V

    return-void
.end method

.method private onDismissFinished()V
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->resetFragment()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa1

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$CameraAction;

    if-eqz v0, :cond_0

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->isDoingAction()Z

    move-result v1

    if-nez v1, :cond_0

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->isRecording()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->resetTips()V

    :cond_0
    return-void
.end method

.method private resetFragment()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mViewPager:Lcom/android/camera/ui/NoScrollViewPager;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/support/v4/view/ViewPager;->setAdapter(Landroid/support/v4/view/PagerAdapter;)V

    iget-object v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mPagerAdapter:Lcom/android/camera/fragment/BaseFragmentPagerAdapter;

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getChildFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object v2

    invoke-virtual {v0, v2}, Lcom/android/camera/fragment/BaseFragmentPagerAdapter;->recycleFragmentList(Landroid/support/v4/app/FragmentManager;)V

    iput-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mPagerAdapter:Lcom/android/camera/fragment/BaseFragmentPagerAdapter;

    :cond_0
    return-void
.end method

.method private resetTips()V
    .locals 0

    return-void
.end method

.method private setViewPagerPageByType(Ljava/lang/String;)V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mComponentAIWatermark:Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getItems()Ljava/util/List;

    move-result-object v0

    const/4 v1, 0x0

    move v2, v1

    :goto_0
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v3

    if-ge v2, v3, :cond_1

    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/camera/data/data/ComponentDataItem;

    iget-object v3, v3, Lcom/android/camera/data/data/ComponentDataItem;->mValue:Ljava/lang/String;

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_0

    iget-object p0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mViewPager:Lcom/android/camera/ui/NoScrollViewPager;

    invoke-virtual {p0, v2, v1}, Landroid/support/v4/view/ViewPager;->setCurrentItem(IZ)V

    goto :goto_1

    :cond_0
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_1
    :goto_1
    return-void
.end method

.method private setViewPagerSelect(I)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mComponentAIWatermark:Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getItems()Ljava/util/List;

    iget-object p0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mViewPager:Lcom/android/camera/ui/NoScrollViewPager;

    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0}, Landroid/support/v4/view/ViewPager;->setCurrentItem(IZ)V

    return-void
.end method


# virtual methods
.method public synthetic Ka()V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->onDismissFinished()V

    return-void
.end method

.method public synthetic La()V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->onDismissFinished()V

    return-void
.end method

.method public dismiss(I)Z
    .locals 5

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getView()Landroid/view/View;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mCurrentState:I

    const/4 v2, -0x1

    const/4 v3, 0x0

    if-eq v1, v2, :cond_4

    if-nez v0, :cond_0

    goto/16 :goto_1

    :cond_0
    iput v2, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mCurrentState:I

    const/4 v1, 0x1

    if-eq p1, v1, :cond_3

    const/4 v2, 0x2

    const/16 v4, 0xc5

    if-eq p1, v2, :cond_2

    const/4 v2, 0x3

    if-eq p1, v2, :cond_1

    goto/16 :goto_0

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    invoke-virtual {p1, v4}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$BottomMenuProtocol;

    invoke-interface {p1, v3}, Lcom/android/camera/protocol/ModeProtocol$BottomMenuProtocol;->onSwitchCameraActionMenu(I)V

    new-instance p1, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;

    invoke-direct {p1, v0}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;-><init>(Landroid/view/View;)V

    const/16 v0, 0xfa

    invoke-virtual {p1, v0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setDurationTime(I)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p1

    new-instance v0, Landroid/view/animation/DecelerateInterpolator;

    invoke-direct {v0}, Landroid/view/animation/DecelerateInterpolator;-><init>()V

    invoke-virtual {p1, v0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setInterpolator(Landroid/view/animation/Interpolator;)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p1

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p1

    new-instance v0, Lcom/android/camera/fragment/aiwatermark/a;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/aiwatermark/a;-><init>(Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;)V

    invoke-virtual {p1, v0}, Lio/reactivex/Completable;->subscribe(Lio/reactivex/functions/Action;)Lio/reactivex/disposables/Disposable;

    goto :goto_0

    :cond_2
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    invoke-virtual {p1, v4}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$BottomMenuProtocol;

    invoke-interface {p1, v3}, Lcom/android/camera/protocol/ModeProtocol$BottomMenuProtocol;->onSwitchCameraActionMenu(I)V

    new-instance p1, Lcom/android/camera/animation/type/SlideOutOnSubscribe;

    const/16 v2, 0x50

    invoke-direct {p1, v0, v2}, Lcom/android/camera/animation/type/SlideOutOnSubscribe;-><init>(Landroid/view/View;I)V

    const/16 v0, 0x8c

    invoke-virtual {p1, v0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setDurationTime(I)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p1

    new-instance v0, Ld/h/a/A;

    invoke-direct {v0}, Ld/h/a/A;-><init>()V

    invoke-virtual {p1, v0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setInterpolator(Landroid/view/animation/Interpolator;)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p1

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p1

    new-instance v0, Lcom/android/camera/fragment/aiwatermark/c;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/aiwatermark/c;-><init>(Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;)V

    invoke-virtual {p1, v0}, Lio/reactivex/Completable;->subscribe(Lio/reactivex/functions/Action;)Lio/reactivex/disposables/Disposable;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p1, 0xaf

    invoke-virtual {p0, p1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    invoke-interface {p0, v1}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->directShowOrHideLeftTipImage(Z)V

    const-string p0, "ai_watermark_list_hide"

    invoke-static {p0}, Lcom/android/camera/statistic/CameraStatUtils;->trackAIWatermarkClick(Ljava/lang/String;)V

    goto :goto_0

    :cond_3
    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->resetFragment()V

    const/4 p0, 0x4

    invoke-virtual {v0, p0}, Landroid/view/View;->setVisibility(I)V

    :goto_0
    return v1

    :cond_4
    :goto_1
    return v3
.end method

.method public getFragmentInto()I
    .locals 0

    const p0, 0xfffe

    return p0
.end method

.method protected getLayoutResourceId()I
    .locals 0

    const p0, 0x7f0b000d

    return p0
.end method

.method protected initView(Landroid/view/View;)V
    .locals 1

    const v0, 0x7f090019

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Lcom/android/camera/ui/NoScrollViewPager;

    iput-object p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mViewPager:Lcom/android/camera/ui/NoScrollViewPager;

    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->initWatermarkType()V

    return-void
.end method

.method public isWatermarkPanelShow()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mCurrentState:I

    const/4 v0, 0x1

    if-ne p0, v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public onBackEvent(I)Z
    .locals 2

    const/4 v0, 0x3

    if-eq p1, v0, :cond_1

    const/4 v0, 0x4

    const/4 v1, 0x1

    if-eq p1, v0, :cond_0

    const/4 p1, 0x2

    goto :goto_0

    :cond_0
    move p1, v1

    :goto_0
    iget-object v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mComponentAIWatermark:Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->setClosed(Z)V

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->dismiss(I)Z

    move-result p0

    return p0

    :cond_1
    const/4 p0, 0x0

    return p0
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

    iget p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mCurrentState:I

    const/4 p2, -0x1

    if-ne p1, p2, :cond_0

    return-void

    :cond_0
    const/4 p1, 0x5

    if-ne p3, p1, :cond_1

    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->checkAIWatermark()V

    :cond_1
    const/4 p1, 0x4

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->onBackEvent(I)Z

    return-void
.end method

.method protected register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->registerBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    const/16 v0, 0xfd

    invoke-interface {p1, v0, p0}, Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method

.method public show()V
    .locals 2

    iget v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->mCurrentState:I

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    return-void

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->initWatermarkType()V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getView()Landroid/view/View;

    move-result-object p0

    new-instance v0, Lcom/android/camera/animation/type/SlideInOnSubscribe;

    const/16 v1, 0x50

    invoke-direct {v0, p0, v1}, Lcom/android/camera/animation/type/SlideInOnSubscribe;-><init>(Landroid/view/View;I)V

    const/16 p0, 0x118

    invoke-virtual {v0, p0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setDurationTime(I)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p0

    new-instance v0, Ld/h/a/C;

    invoke-direct {v0}, Ld/h/a/C;-><init>()V

    invoke-virtual {p0, v0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setInterpolator(Landroid/view/animation/Interpolator;)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p0

    invoke-static {p0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    return-void
.end method

.method public switchType(Ljava/lang/String;Z)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->initWatermarkType(Ljava/lang/String;Z)V

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->setViewPagerPageByType(Ljava/lang/String;)V

    return-void
.end method

.method protected unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->unRegisterBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    const/16 v0, 0xfd

    invoke-interface {p1, v0, p0}, Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method
