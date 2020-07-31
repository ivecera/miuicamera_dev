.class public abstract Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;
.super Lcom/android/camera/fragment/beauty/BaseBeautyFragment;
.source "FragmentBaseWatermark.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark$EffectItemPadding;
    }
.end annotation


# static fields
.field public static final FRAGMENT_INFO:I = 0xffff

.field private static final TAG:Ljava/lang/String; = "FragmentBaseWatermark"


# instance fields
.field private mCurrentMode:I

.field private mEffectItemPadding:Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark$EffectItemPadding;

.field private mHolderWidth:I

.field private mLayoutManager:Landroid/support/v7/widget/LinearLayoutManager;

.field private mRecyclerView:Landroid/support/v7/widget/RecyclerView;

.field private mTotalWidth:I

.field private mWatermarkAdapter:Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/beauty/BaseBeautyFragment;-><init>()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mLayoutManager:Landroid/support/v7/widget/LinearLayoutManager;

    return-void
.end method

.method private initView(Landroid/view/View;)V
    .locals 5

    const v0, 0x7f09020d

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/support/v7/widget/RecyclerView;

    iput-object p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->getWatermarkList()Ljava/util/List;

    move-result-object p1

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    new-instance v1, Landroid/support/v7/widget/LinearLayoutManager;

    invoke-direct {v1, v0}, Landroid/support/v7/widget/LinearLayoutManager;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mLayoutManager:Landroid/support/v7/widget/LinearLayoutManager;

    iget-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mLayoutManager:Landroid/support/v7/widget/LinearLayoutManager;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/support/v7/widget/LinearLayoutManager;->setOrientation(I)V

    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result v1

    :goto_0
    if-ge v2, v1, :cond_1

    invoke-interface {p1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    invoke-virtual {v3}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getKey()Ljava/lang/String;

    move-result-object v3

    sget-object v4, Lcom/android/camera/aiwatermark/util/WatermarkConstant;->SELECT_KEY:Ljava/lang/String;

    invoke-static {v3, v4}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v3

    if-eqz v3, :cond_0

    goto :goto_1

    :cond_0
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_1
    const/4 v2, -0x1

    :goto_1
    new-instance v1, Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;

    iget-object v3, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mLayoutManager:Landroid/support/v7/widget/LinearLayoutManager;

    invoke-direct {v1, v0, v3, v2, p1}, Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;-><init>(Landroid/content/Context;Landroid/support/v7/widget/LinearLayoutManager;ILjava/util/List;)V

    iput-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mWatermarkAdapter:Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;

    iget-object p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    iget-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mLayoutManager:Landroid/support/v7/widget/LinearLayoutManager;

    invoke-virtual {p1, v1}, Landroid/support/v7/widget/RecyclerView;->setLayoutManager(Landroid/support/v7/widget/RecyclerView$LayoutManager;)V

    iget-object p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    iget-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mWatermarkAdapter:Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;

    invoke-virtual {p1, v1}, Landroid/support/v7/widget/RecyclerView;->setAdapter(Landroid/support/v7/widget/RecyclerView$Adapter;)V

    iget-object p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mEffectItemPadding:Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark$EffectItemPadding;

    if-nez p1, :cond_2

    new-instance p1, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark$EffectItemPadding;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {p1, v1}, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark$EffectItemPadding;-><init>(Landroid/content/Context;)V

    iput-object p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mEffectItemPadding:Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark$EffectItemPadding;

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    iget-object v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mEffectItemPadding:Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark$EffectItemPadding;

    invoke-virtual {p1, v1}, Landroid/support/v7/widget/RecyclerView;->addItemDecoration(Landroid/support/v7/widget/RecyclerView$ItemDecoration;)V

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object p1

    iget p1, p1, Landroid/util/DisplayMetrics;->widthPixels:I

    iput p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mTotalWidth:I

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v0, 0x7f070289

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mHolderWidth:I

    if-ltz v2, :cond_3

    invoke-direct {p0, v2}, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->setItemInCenter(I)V

    :cond_3
    new-instance p1, Lcom/android/camera/fragment/DefaultItemAnimator;

    invoke-direct {p1}, Lcom/android/camera/fragment/DefaultItemAnimator;-><init>()V

    const-wide/16 v0, 0x96

    invoke-virtual {p1, v0, v1}, Landroid/support/v7/widget/RecyclerView$ItemAnimator;->setChangeDuration(J)V

    invoke-virtual {p1, v0, v1}, Landroid/support/v7/widget/RecyclerView$ItemAnimator;->setMoveDuration(J)V

    invoke-virtual {p1, v0, v1}, Landroid/support/v7/widget/RecyclerView$ItemAnimator;->setAddDuration(J)V

    iget-object p0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {p0, p1}, Landroid/support/v7/widget/RecyclerView;->setItemAnimator(Landroid/support/v7/widget/RecyclerView$ItemAnimator;)V

    return-void
.end method

.method private setItemInCenter(I)V
    .locals 2

    iget v0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mTotalWidth:I

    div-int/lit8 v0, v0, 0x2

    iget v1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mHolderWidth:I

    div-int/lit8 v1, v1, 0x2

    sub-int/2addr v0, v1

    iget-object p0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mLayoutManager:Landroid/support/v7/widget/LinearLayoutManager;

    invoke-virtual {p0, p1, v0}, Landroid/support/v7/widget/LinearLayoutManager;->scrollToPositionWithOffset(II)V

    return-void
.end method


# virtual methods
.method protected getAnimateView()Landroid/view/View;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mRecyclerView:Landroid/support/v7/widget/RecyclerView;

    return-object p0
.end method

.method public abstract getWatermarkList()Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/android/camera/aiwatermark/data/WatermarkItem;",
            ">;"
        }
    .end annotation
.end method

.method public onClick(Landroid/view/View;)V
    .locals 0

    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 1
    .param p1    # Landroid/view/LayoutInflater;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Landroid/view/ViewGroup;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param
    .param p3    # Landroid/os/Bundle;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object p3

    invoke-virtual {p3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result p3

    iput p3, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mCurrentMode:I

    const p3, 0x7f0b0061

    const/4 v0, 0x0

    invoke-virtual {p1, p3, p2, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->initView(Landroid/view/View;)V

    return-object p1
.end method

.method protected onViewCreatedAndVisibleToUser(Z)V
    .locals 6

    invoke-super {p0, p1}, Lcom/android/camera/fragment/beauty/BaseBeautyFragment;->onViewCreatedAndVisibleToUser(Z)V

    if-nez p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mWatermarkAdapter:Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;

    if-eqz p1, :cond_2

    invoke-virtual {p1}, Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;->getSelectedIndex()I

    move-result p1

    if-ltz p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mWatermarkAdapter:Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;

    invoke-virtual {p1}, Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;->getItems()Ljava/util/List;

    move-result-object p1

    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result v0

    const/4 v1, -0x1

    const/4 v2, 0x0

    move v3, v2

    :goto_0
    if-ge v3, v0, :cond_1

    invoke-interface {p1, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    invoke-virtual {v4}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getKey()Ljava/lang/String;

    move-result-object v4

    sget-object v5, Lcom/android/camera/aiwatermark/util/WatermarkConstant;->SELECT_KEY:Ljava/lang/String;

    invoke-static {v4, v5}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v4

    if-eqz v4, :cond_0

    move v1, v3

    goto :goto_1

    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_1
    :goto_1
    iget-object p0, p0, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;->mWatermarkAdapter:Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;

    invoke-virtual {p0, v1, v2}, Lcom/android/camera/fragment/aiwatermark/adapter/WatermarkAdapter;->onSelected(IZ)V

    :cond_2
    return-void
.end method
