.class Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$1;
.super Ljava/lang/Object;
.source "FragmentMimojiEdit2.java"

# interfaces
.implements Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->startMimojiEdit(I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

.field final synthetic val$from:I


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;I)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    iput p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$1;->val$from:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onGlobalLayout()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$000(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/View;->getViewTreeObserver()Landroid/view/ViewTreeObserver;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/ViewTreeObserver;->removeOnGlobalLayoutListener(Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$100(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/TextureView;->setVisibility(I)V

    iget v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$1;->val$from:I

    const/16 v1, 0xc9

    if-ne v0, v1, :cond_0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-static {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$200(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;

    move-result-object p0

    const/4 v0, 0x4

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;->resetLayoutPosition(I)V

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-static {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$200(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;

    move-result-object p0

    const/4 v0, 0x2

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;->resetLayoutPosition(I)V

    :goto_0
    return-void
.end method
