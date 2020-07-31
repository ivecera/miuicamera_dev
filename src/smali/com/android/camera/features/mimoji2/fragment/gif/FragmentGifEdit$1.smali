.class Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit$1;
.super Ljava/lang/Object;
.source "FragmentGifEdit.java"

# interfaces
.implements Lcom/xiaomi/Video2GifEditer/MediaProcess$Callback;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->initGifMediaPlayer()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public OnConvertProgress(I)V
    .locals 2

    const/16 v0, 0x64

    if-ne p1, v0, :cond_1

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->isShare()Z

    move-result p1

    if-eqz p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->doShare()V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->access$000(Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;)Landroid/content/Context;

    move-result-object p1

    const/4 v0, 0x0

    const-string v1, "Video convert GIF is finished!"

    invoke-static {p1, v1, v0}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object p1

    invoke-virtual {p1}, Landroid/widget/Toast;->show()V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;

    const/4 p1, 0x6

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/gif/FragmentGifEdit;->onBackEvent(I)Z

    :cond_1
    :goto_0
    return-void
.end method
