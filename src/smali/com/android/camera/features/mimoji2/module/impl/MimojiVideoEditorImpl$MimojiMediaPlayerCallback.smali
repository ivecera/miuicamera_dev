.class Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;
.super Ljava/lang/Object;
.source "MimojiVideoEditorImpl.java"

# interfaces
.implements Lcom/android/camera/ui/TextureVideoView$MediaPlayerCallback;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "MimojiMediaPlayerCallback"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;


# direct methods
.method private constructor <init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$1;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;)V

    return-void
.end method


# virtual methods
.method public onBufferingUpdate(Landroid/media/MediaPlayer;I)V
    .locals 0

    return-void
.end method

.method public onCompletion(Landroid/media/MediaPlayer;)V
    .locals 0

    return-void
.end method

.method public onError(Landroid/media/MediaPlayer;II)Z
    .locals 0

    invoke-static {}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->access$100()Ljava/lang/String;

    move-result-object p0

    const-string p1, "mimoji boolean onError[mp, what, extra]"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p1, 0xf9

    invoke-virtual {p0, p1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiFullScreenProtocol;

    if-eqz p0, :cond_0

    invoke-interface {p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiFullScreenProtocol;->onCombineError()V

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->access$100()Ljava/lang/String;

    move-result-object p0

    const-string p1, "mimoji void combineVideoAudio[savePath] null"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    const/4 p0, 0x0

    return p0
.end method

.method public onInfo(Landroid/media/MediaPlayer;II)Z
    .locals 0

    const/4 p0, 0x0

    return p0
.end method

.method public onPrepared(Landroid/media/MediaPlayer;)V
    .locals 0

    return-void
.end method

.method public onSurfaceReady(Landroid/view/Surface;)V
    .locals 2

    invoke-static {}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->access$100()Ljava/lang/String;

    move-result-object v0

    const-string v1, "mimoji void onSurfaceReady[surface]"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->access$400(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl$MimojiMediaPlayerCallback;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;

    invoke-static {p0, p1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->access$500(Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;Landroid/view/Surface;)V

    :cond_0
    return-void
.end method

.method public onVideoSizeChanged(Landroid/media/MediaPlayer;II)V
    .locals 0

    return-void
.end method
