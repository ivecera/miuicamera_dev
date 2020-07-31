.class public Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;
.super Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;
.source "MimojiThumbnailRecyclerAdapter2.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2$ThumbnailViewViewHolder;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter<",
        "Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;",
        ">;"
    }
.end annotation


# instance fields
.field private mContext:Landroid/content/Context;

.field private mSelectIndex:F


# direct methods
.method public constructor <init>(Landroid/content/Context;I)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;-><init>(Ljava/util/ArrayList;)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;->mContext:Landroid/content/Context;

    invoke-static {p2}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;->getSelectItem(I)F

    move-result p1

    iput p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;->mSelectIndex:F

    return-void
.end method

.method public constructor <init>(Ljava/util/ArrayList;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/ArrayList<",
            "Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;",
            ">;)V"
        }
    .end annotation

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;-><init>(Ljava/util/List;)V

    const/high16 p1, -0x40800000    # -1.0f

    iput p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;->mSelectIndex:F

    return-void
.end method

.method public static getSelectItem(I)F
    .locals 1

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInnerConfigSelectIndex(I)F

    move-result p0

    return p0
.end method


# virtual methods
.method protected onCreateBaseRecyclerViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;
    .locals 1
    .param p1    # Landroid/view/ViewGroup;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/view/ViewGroup;",
            "I)",
            "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder<",
            "Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;",
            ">;"
        }
    .end annotation

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const p2, 0x7f0b006f

    const/4 v0, 0x0

    invoke-virtual {p0, p2, p1, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p0

    new-instance p1, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2$ThumbnailViewViewHolder;

    invoke-direct {p1, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2$ThumbnailViewViewHolder;-><init>(Landroid/view/View;)V

    return-object p1
.end method

.method public setSelectItem(II)V
    .locals 0

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    int-to-float p2, p2

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setInnerConfigSelectIndex(IF)V

    return-void
.end method
