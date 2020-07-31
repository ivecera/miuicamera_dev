.class public abstract Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;
.super Landroid/support/v7/widget/RecyclerView$Adapter;
.source "BaseRecyclerAdapter.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "<T:",
        "Ljava/lang/Object;",
        ">",
        "Landroid/support/v7/widget/RecyclerView$Adapter<",
        "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;",
        ">;"
    }
.end annotation


# static fields
.field public static final TAG:Ljava/lang/String; = "BaseRecyclerAdapter"


# instance fields
.field private mDdataList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "TT;>;"
        }
    .end annotation
.end field

.field private onRecyclerItemClickListener:Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener<",
            "TT;>;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "TT;>;)V"
        }
    .end annotation

    invoke-direct {p0}, Landroid/support/v7/widget/RecyclerView$Adapter;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    return-void
.end method


# virtual methods
.method public declared-synchronized addData(Ljava/lang/Object;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TT;)V"
        }
    .end annotation

    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    if-nez v0, :cond_0

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-interface {v1, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    invoke-virtual {p0, v0}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyItemInserted(I)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    :cond_1
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized addData(Ljava/lang/Object;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TT;I)V"
        }
    .end annotation

    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-le p2, v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-interface {v0, p2, p1}, Ljava/util/List;->add(ILjava/lang/Object;)V

    invoke-virtual {p0, p2}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyItemInserted(I)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit p0

    return-void

    :cond_1
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public getDataList()Ljava/util/List;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "TT;>;"
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    return-object p0
.end method

.method public getItemCount()I
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    if-nez p0, :cond_0

    const/4 p0, 0x0

    goto :goto_0

    :cond_0
    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result p0

    :goto_0
    return p0
.end method

.method public bridge synthetic onBindViewHolder(Landroid/support/v7/widget/RecyclerView$ViewHolder;I)V
    .locals 0
    .param p1    # Landroid/support/v7/widget/RecyclerView$ViewHolder;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    check-cast p1, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->onBindViewHolder(Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;I)V

    return-void
.end method

.method public onBindViewHolder(Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;I)V
    .locals 3
    .param p1    # Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    if-eqz v0, :cond_2

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    add-int/lit8 v0, v0, -0x1

    if-le p2, v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-interface {v0, p2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    if-nez v0, :cond_1

    sget-object v1, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->TAG:Ljava/lang/String;

    const-string v2, "data null error"

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_1
    invoke-virtual {p1, v0, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;->setData(Ljava/lang/Object;I)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->onRecyclerItemClickListener:Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;

    if-eqz p0, :cond_2

    invoke-virtual {p1, p0, v0, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;->setClickListener(Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;Ljava/lang/Object;I)V

    :cond_2
    :goto_0
    return-void
.end method

.method protected abstract onCreateBaseRecyclerViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;
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
            "TT;>;"
        }
    .end annotation
.end method

.method public bridge synthetic onCreateViewHolder(Landroid/view/ViewGroup;I)Landroid/support/v7/widget/RecyclerView$ViewHolder;
    .locals 0
    .param p1    # Landroid/view/ViewGroup;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;

    move-result-object p0

    return-object p0
.end method

.method public onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;
    .locals 0
    .param p1    # Landroid/view/ViewGroup;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->onCreateBaseRecyclerViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;

    move-result-object p0

    return-object p0
.end method

.method public declared-synchronized removeData(I)V
    .locals 1

    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    if-eqz v0, :cond_1

    if-ltz p1, :cond_1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    add-int/lit8 v0, v0, -0x1

    if-le p1, v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    invoke-virtual {p0, p1}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyItemRemoved(I)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit p0

    return-void

    :cond_1
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized setDataList(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "TT;>;)V"
        }
    .end annotation

    monitor-enter p0

    :try_start_0
    iput-object p1, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-virtual {p0}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyDataSetChanged()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public setOnRecyclerItemClickListener(Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener<",
            "TT;>;)V"
        }
    .end annotation

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->onRecyclerItemClickListener:Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;

    return-void
.end method

.method public declared-synchronized updateData(ILjava/lang/Object;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(ITT;)V"
        }
    .end annotation

    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    if-nez v0, :cond_1

    if-nez p1, :cond_0

    invoke-virtual {p0, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->addData(Ljava/lang/Object;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    :cond_0
    monitor-exit p0

    return-void

    :cond_1
    if-ltz p1, :cond_4

    :try_start_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-le p1, v0, :cond_2

    goto :goto_0

    :cond_2
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-ne p1, v0, :cond_3

    invoke-virtual {p0, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->addData(Ljava/lang/Object;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return-void

    :cond_3
    :try_start_2
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->mDdataList:Ljava/util/List;

    invoke-interface {v0, p1, p2}, Ljava/util/List;->set(ILjava/lang/Object;)Ljava/lang/Object;

    invoke-virtual {p0, p1}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyItemChanged(I)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    monitor-exit p0

    return-void

    :cond_4
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method
