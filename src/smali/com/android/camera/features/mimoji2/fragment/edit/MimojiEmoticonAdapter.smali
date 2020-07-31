.class public Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;
.super Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;
.source "MimojiEmoticonAdapter.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$OnAllSelectStateChangeListener;,
        Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$EmoticonViewViewHolder;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter<",
        "Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;",
        ">;"
    }
.end annotation


# instance fields
.field private mIsAllSelected:Ljava/util/concurrent/atomic/AtomicBoolean;

.field private onAllSelectStateChangeListener:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$OnAllSelectStateChangeListener;


# direct methods
.method public constructor <init>(Ljava/util/List;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;",
            ">;)V"
        }
    .end annotation

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;-><init>(Ljava/util/List;)V

    new-instance p1, Ljava/util/concurrent/atomic/AtomicBoolean;

    const/4 v0, 0x0

    invoke-direct {p1, v0}, Ljava/util/concurrent/atomic/AtomicBoolean;-><init>(Z)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->mIsAllSelected:Ljava/util/concurrent/atomic/AtomicBoolean;

    return-void
.end method

.method private checkAllSelectedState()V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_2

    const/4 v0, 0x1

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_1

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;

    invoke-virtual {v2}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->isSelected()Z

    move-result v2

    if-nez v2, :cond_0

    const/4 v0, 0x0

    :cond_1
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->mIsAllSelected:Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-virtual {v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->get()Z

    move-result v1

    if-eq v1, v0, :cond_2

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->mIsAllSelected:Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-virtual {v1, v0}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->onAllSelectStateChangeListener:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$OnAllSelectStateChangeListener;

    if-eqz p0, :cond_2

    invoke-interface {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$OnAllSelectStateChangeListener;->onAllSelectStateChange(Z)V

    :cond_2
    return-void
.end method


# virtual methods
.method public clearState()V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->setSelected(Z)V

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->checkAllSelectedState()V

    :cond_1
    return-void
.end method

.method public getIsAllSelected()Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->mIsAllSelected:Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-virtual {p0}, Ljava/util/concurrent/atomic/AtomicBoolean;->get()Z

    move-result p0

    return p0
.end method

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
            "Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;",
            ">;"
        }
    .end annotation

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const p2, 0x7f0b0044

    const/4 v0, 0x0

    invoke-virtual {p0, p2, p1, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p0

    new-instance p1, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$EmoticonViewViewHolder;

    invoke-direct {p1, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$EmoticonViewViewHolder;-><init>(Landroid/view/View;)V

    return-object p1
.end method

.method public selectAll()V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->setSelected(Z)V

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->checkAllSelectedState()V

    :cond_1
    return-void
.end method

.method public setOnAllSelectStateChangeListener(Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$OnAllSelectStateChangeListener;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->onAllSelectStateChangeListener:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$OnAllSelectStateChangeListener;

    return-void
.end method

.method public setSelectState(Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;I)V
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getItemCount()I

    move-result v0

    if-ge p2, v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0, p2, p1}, Ljava/util/List;->set(ILjava/lang/Object;)Ljava/lang/Object;

    invoke-virtual {p0, p2}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyItemChanged(I)V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->checkAllSelectedState()V

    :cond_0
    return-void
.end method
