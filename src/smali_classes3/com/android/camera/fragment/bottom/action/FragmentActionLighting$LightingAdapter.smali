.class Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;
.super Landroid/support/v7/widget/RecyclerView$Adapter;
.source "FragmentActionLighting.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/fragment/bottom/action/FragmentActionLighting;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "LightingAdapter"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/support/v7/widget/RecyclerView$Adapter<",
        "Lcom/android/camera/fragment/CommonRecyclerViewHolder;",
        ">;"
    }
.end annotation


# instance fields
.field private mBgAlpha:I

.field private mBgNormal:Landroid/graphics/drawable/Drawable;

.field private mBgSelected:Landroid/graphics/drawable/Drawable;

.field private mComponentRunningLighting:Lcom/android/camera/data/data/runing/ComponentRunningLighting;

.field private mContent:[Ljava/lang/String;

.field private mCount:I

.field private mCurrentMode:I

.field private mMargin:I

.field private mOnClickListener:Landroid/view/View$OnClickListener;


# direct methods
.method public constructor <init>(Landroid/content/Context;ILandroid/view/View$OnClickListener;Lcom/android/camera/data/data/runing/ComponentRunningLighting;)V
    .locals 1

    invoke-direct {p0}, Landroid/support/v7/widget/RecyclerView$Adapter;-><init>()V

    const/16 v0, 0xff

    iput v0, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mBgAlpha:I

    iput p2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mCurrentMode:I

    iput-object p3, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mOnClickListener:Landroid/view/View$OnClickListener;

    iput-object p4, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mComponentRunningLighting:Lcom/android/camera/data/data/runing/ComponentRunningLighting;

    iget-object p2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mComponentRunningLighting:Lcom/android/camera/data/data/runing/ComponentRunningLighting;

    invoke-virtual {p2}, Lcom/android/camera/data/data/runing/ComponentRunningLighting;->getItems()Ljava/util/List;

    move-result-object p2

    invoke-interface {p2}, Ljava/util/List;->size()I

    move-result p2

    iput p2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mCount:I

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p2

    const p3, 0x7f07010b

    invoke-virtual {p2, p3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p2

    iput p2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mMargin:I

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p2

    const p3, 0x7f080021

    invoke-virtual {p2, p3}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    iput-object p2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mBgNormal:Landroid/graphics/drawable/Drawable;

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p2

    const p3, 0x7f080022

    invoke-virtual {p2, p3}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    iput-object p2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mBgSelected:Landroid/graphics/drawable/Drawable;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->updateContent(Landroid/content/Context;)V

    return-void
.end method

.method private updateContent(Landroid/content/Context;)V
    .locals 3

    invoke-static {}, Lcom/android/camera/Util;->isAccessible()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-static {}, Lcom/android/camera/Util;->isSetContentDesc()Z

    move-result v0

    if-eqz v0, :cond_1

    :cond_0
    iget v0, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mCount:I

    new-array v0, v0, [Ljava/lang/String;

    iput-object v0, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mContent:[Ljava/lang/String;

    const/4 v0, 0x0

    :goto_0
    iget v1, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mCount:I

    if-ge v0, v1, :cond_1

    iget-object v1, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mComponentRunningLighting:Lcom/android/camera/data/data/runing/ComponentRunningLighting;

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningLighting;->getItems()Ljava/util/List;

    move-result-object v1

    invoke-interface {v1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/data/data/ComponentDataItem;

    iget-object v2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mContent:[Ljava/lang/String;

    iget v1, v1, Lcom/android/camera/data/data/ComponentDataItem;->mDisplayNameRes:I

    invoke-virtual {p1, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v1

    aput-object v1, v2, v0

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    return-void
.end method


# virtual methods
.method public getItemCount()I
    .locals 0

    iget p0, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mCount:I

    return p0
.end method

.method public bridge synthetic onBindViewHolder(Landroid/support/v7/widget/RecyclerView$ViewHolder;I)V
    .locals 0

    check-cast p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->onBindViewHolder(Lcom/android/camera/fragment/CommonRecyclerViewHolder;I)V

    return-void
.end method

.method public onBindViewHolder(Lcom/android/camera/fragment/CommonRecyclerViewHolder;I)V
    .locals 5

    iget-object v0, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    const v1, 0x7f0900a5

    invoke-virtual {p1, v1}, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->getView(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Lcom/android/camera/ui/ColorImageView;

    const v2, 0x7f0900a6

    invoke-virtual {p1, v2}, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->getView(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Lcom/android/camera/ui/ColorImageView;

    iget-object v2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mComponentRunningLighting:Lcom/android/camera/data/data/runing/ComponentRunningLighting;

    iget v3, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mCurrentMode:I

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/ComponentData;->getComponentValue(I)Ljava/lang/String;

    move-result-object v2

    iget-object v3, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mComponentRunningLighting:Lcom/android/camera/data/data/runing/ComponentRunningLighting;

    invoke-virtual {v3}, Lcom/android/camera/data/data/runing/ComponentRunningLighting;->getItems()Ljava/util/List;

    move-result-object v3

    invoke-interface {v3, p2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/camera/data/data/ComponentDataItem;

    invoke-static {}, Lcom/android/camera/Util;->isAccessible()Z

    move-result v4

    if-nez v4, :cond_0

    invoke-static {}, Lcom/android/camera/Util;->isSetContentDesc()Z

    move-result v4

    if-eqz v4, :cond_1

    :cond_0
    iget-object v4, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mContent:[Ljava/lang/String;

    aget-object v4, v4, p2

    invoke-virtual {p1, v4}, Landroid/widget/ImageView;->setContentDescription(Ljava/lang/CharSequence;)V

    :cond_1
    iget-object v4, v3, Lcom/android/camera/data/data/ComponentDataItem;->mValue:Ljava/lang/String;

    invoke-virtual {v2, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2

    iget-object v2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mBgSelected:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v1, v2}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    iget v2, v3, Lcom/android/camera/data/data/ComponentDataItem;->mIconSelectedRes:I

    invoke-virtual {p1, v2}, Landroid/widget/ImageView;->setImageResource(I)V

    goto :goto_0

    :cond_2
    iget-object v2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mBgNormal:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v1, v2}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    iget v2, v3, Lcom/android/camera/data/data/ComponentDataItem;->mIconRes:I

    invoke-virtual {p1, v2}, Landroid/widget/ImageView;->setImageResource(I)V

    :goto_0
    iget p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mMargin:I

    invoke-virtual {v0, p1}, Landroid/view/ViewGroup$MarginLayoutParams;->setMarginStart(I)V

    invoke-virtual {p0}, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->getItemCount()I

    move-result p1

    add-int/lit8 p1, p1, -0x1

    if-ge p2, p1, :cond_3

    const/4 p1, 0x0

    invoke-virtual {v0, p1}, Landroid/view/ViewGroup$MarginLayoutParams;->setMarginEnd(I)V

    goto :goto_1

    :cond_3
    iget p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mMargin:I

    invoke-virtual {v0, p1}, Landroid/view/ViewGroup$MarginLayoutParams;->setMarginEnd(I)V

    :goto_1
    iget-object p0, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mOnClickListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v1, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    invoke-virtual {v1, p0}, Landroid/widget/ImageView;->setTag(Ljava/lang/Object;)V

    return-void
.end method

.method public bridge synthetic onCreateViewHolder(Landroid/view/ViewGroup;I)Landroid/support/v7/widget/RecyclerView$ViewHolder;
    .locals 0

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/fragment/CommonRecyclerViewHolder;

    move-result-object p0

    return-object p0
.end method

.method public onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/fragment/CommonRecyclerViewHolder;
    .locals 1

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const p2, 0x7f0b0031

    const/4 v0, 0x0

    invoke-virtual {p0, p2, p1, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p0

    new-instance p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;

    invoke-direct {p1, p0}, Lcom/android/camera/fragment/CommonRecyclerViewHolder;-><init>(Landroid/view/View;)V

    return-object p1
.end method

.method public setBgMode(Z)V
    .locals 0

    if-eqz p1, :cond_0

    const/16 p1, 0x66

    iput p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mBgAlpha:I

    goto :goto_0

    :cond_0
    const/16 p1, 0xff

    iput p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mBgAlpha:I

    :goto_0
    iget-object p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mBgNormal:Landroid/graphics/drawable/Drawable;

    iget p0, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mBgAlpha:I

    invoke-virtual {p1, p0}, Landroid/graphics/drawable/Drawable;->setAlpha(I)V

    return-void
.end method

.method public updateLightingData(Landroid/content/Context;Lcom/android/camera/data/data/runing/ComponentRunningLighting;)V
    .locals 0

    iput-object p2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mComponentRunningLighting:Lcom/android/camera/data/data/runing/ComponentRunningLighting;

    iget-object p2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mComponentRunningLighting:Lcom/android/camera/data/data/runing/ComponentRunningLighting;

    invoke-virtual {p2}, Lcom/android/camera/data/data/runing/ComponentRunningLighting;->getItems()Ljava/util/List;

    move-result-object p2

    invoke-interface {p2}, Ljava/util/List;->size()I

    move-result p2

    iput p2, p0, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->mCount:I

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/bottom/action/FragmentActionLighting$LightingAdapter;->updateContent(Landroid/content/Context;)V

    invoke-virtual {p0}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    return-void
.end method
