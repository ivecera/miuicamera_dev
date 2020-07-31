.class Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen$1;
.super Ljava/lang/Object;
.source "FragmentMimojiFullScreen.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->showExitConfirm()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 0

    const-string p1, "live_exit"

    invoke-static {p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackLiveClick(Ljava/lang/String;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;

    const/4 p2, 0x0

    invoke-static {p1, p2}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->access$002(Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;Landroid/app/AlertDialog;)Landroid/app/AlertDialog;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 p2, 0xf6

    invoke-virtual {p1, p2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;

    if-eqz p1, :cond_0

    const/4 p2, 0x1

    invoke-interface {p1, p2}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;->deleteMimojiCache(I)Z

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->onCombineError()V

    return-void
.end method
