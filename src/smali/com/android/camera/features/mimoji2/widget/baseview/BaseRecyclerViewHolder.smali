.class public abstract Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;
.super Landroid/support/v7/widget/RecyclerView$ViewHolder;
.source "BaseRecyclerViewHolder.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "<T:",
        "Ljava/lang/Object;",
        ">",
        "Landroid/support/v7/widget/RecyclerView$ViewHolder;"
    }
.end annotation


# direct methods
.method public constructor <init>(Landroid/view/View;)V
    .locals 0
    .param p1    # Landroid/view/View;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    invoke-direct {p0, p1}, Landroid/support/v7/widget/RecyclerView$ViewHolder;-><init>(Landroid/view/View;)V

    return-void
.end method

.method static synthetic a(Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;Ljava/lang/Object;ILandroid/view/View;)V
    .locals 0

    invoke-interface {p0, p1, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;->OnRecyclerItemClickListener(Ljava/lang/Object;I)V

    return-void
.end method


# virtual methods
.method public setClickListener(Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;Ljava/lang/Object;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener<",
            "TT;>;TT;I)V"
        }
    .end annotation

    iget-object p0, p0, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    new-instance v0, Lcom/android/camera/features/mimoji2/widget/baseview/a;

    invoke-direct {v0, p1, p2, p3}, Lcom/android/camera/features/mimoji2/widget/baseview/a;-><init>(Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;Ljava/lang/Object;I)V

    invoke-virtual {p0, v0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    return-void
.end method

.method public abstract setData(Ljava/lang/Object;I)V
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TT;I)V"
        }
    .end annotation
.end method
