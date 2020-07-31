.class public Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;
.super Landroid/support/v7/widget/RecyclerView$ViewHolder;
.source "ColorListAdapter2.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "ViewHolder"
.end annotation


# instance fields
.field ivColor:Lcom/android/camera/ui/CircleImageView;

.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;


# direct methods
.method public constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;Landroid/view/View;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    invoke-direct {p0, p2}, Landroid/support/v7/widget/RecyclerView$ViewHolder;-><init>(Landroid/view/View;)V

    const p1, 0x7f090096

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Lcom/android/camera/ui/CircleImageView;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;->ivColor:Lcom/android/camera/ui/CircleImageView;

    return-void
.end method
