.class Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$6;
.super Ljava/lang/Object;
.source "MimojiAvatarEngine2Impl.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->onPreviewFrame(Landroid/media/Image;Lcom/android/camera2/Camera2Proxy;I)Z
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

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$6;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v0, :cond_1

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$6;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getIsNoFaceResult()Z

    move-result v1

    if-eqz v1, :cond_0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$6;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->isNeedShowAvatar()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    const v1, 0x7f0f0175

    invoke-interface {v0, p0, v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertMimojiFaceDetect(ZI)V

    :cond_1
    return-void
.end method
