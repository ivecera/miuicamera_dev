.class Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;
.super Ljava/lang/Object;
.source "MimojiAvatarEngine2Impl.java"

# interfaces
.implements Lcom/arcsoft/avatar/RecordModule$MediaResultCallback;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onCaptureResult(Ljava/nio/ByteBuffer;)V
    .locals 2

    invoke-static {}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$200()Ljava/lang/String;

    move-result-object v0

    const-string v1, "onCapture Result"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$1300(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Landroid/os/Handler;

    move-result-object v0

    new-instance v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5$2;

    invoke-direct {v1, p0, p1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5$2;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;Ljava/nio/ByteBuffer;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method public onVideoResult(Z)V
    .locals 4

    invoke-static {}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$200()Ljava/lang/String;

    move-result-object v0

    const-string v1, "stop video record callback"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$700(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Ljava/io/FileOutputStream;

    move-result-object v0

    if-eqz v0, :cond_0

    :try_start_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$700(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Ljava/io/FileOutputStream;

    move-result-object v0

    invoke-virtual {v0}, Ljava/io/FileOutputStream;->close()V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    invoke-static {}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$200()Ljava/lang/String;

    move-result-object v1

    const-string v2, "fail to close file stream"

    invoke-static {v1, v2, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$702(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Ljava/io/FileOutputStream;)Ljava/io/FileOutputStream;

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$800(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Ljava/util/concurrent/CountDownLatch;

    move-result-object v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$800(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Ljava/util/concurrent/CountDownLatch;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/concurrent/CountDownLatch;->countDown()V

    :cond_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$902(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0, v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$1002(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Z)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->isShowGifOperate()Z

    move-result v0

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$1100(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Landroid/os/Handler;

    move-result-object v0

    new-instance v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5$1;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5$1;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$400(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)J

    move-result-wide v0

    const-wide/16 v2, 0x3e8

    cmp-long v0, v0, v2

    if-gez v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$400(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)J

    move-result-wide v0

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-eqz v0, :cond_2

    return-void

    :cond_2
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa0

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    if-eqz v0, :cond_3

    const/16 v1, 0x14

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->delegateEvent(I)V

    :cond_3
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xfb

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$MimojiGifEditor;

    if-eqz v0, :cond_5

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getVideoCache()Ljava/lang/String;

    move-result-object p0

    invoke-interface {v0, p0, p1}, Lcom/android/camera/protocol/ModeProtocol$MimojiGifEditor;->completeVideoRecord(Ljava/lang/String;Z)V

    goto :goto_1

    :cond_4
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xfc

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;

    if-eqz p1, :cond_5

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getVideoCache()Ljava/lang/String;

    move-result-object p0

    invoke-interface {p1, p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;->combineVideoAudio(Ljava/lang/String;)V

    :cond_5
    :goto_1
    return-void
.end method
