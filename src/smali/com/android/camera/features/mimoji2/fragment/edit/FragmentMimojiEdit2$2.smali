.class Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$2;
.super Ljava/lang/Object;
.source "FragmentMimojiEdit2.java"

# interfaces
.implements Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ItfGvOnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->initMimojiEdit(Landroid/view/View;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$2;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public notifyUIChanged()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$2;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$302(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$2;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$400(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)I

    move-result v0

    const/16 v1, 0xcb

    if-ne v0, v1, :cond_0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$2;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    const/4 v0, 0x3

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->updateTitleState(I)V

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$2;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    const/4 v0, 0x5

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->updateTitleState(I)V

    :goto_0
    return-void
.end method
