.class Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5$2;
.super Ljava/lang/Object;
.source "MimojiAvatarEngine2Impl.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->onCaptureResult(Ljava/nio/ByteBuffer;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;

.field final synthetic val$byteBuffer:Ljava/nio/ByteBuffer;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;Ljava/nio/ByteBuffer;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5$2;->this$1:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5$2;->val$byteBuffer:Ljava/nio/ByteBuffer;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5$2;->this$1:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;

    iget-object v0, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5$2;->val$byteBuffer:Ljava/nio/ByteBuffer;

    invoke-static {v0, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$1200(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Ljava/nio/ByteBuffer;)V

    return-void
.end method
