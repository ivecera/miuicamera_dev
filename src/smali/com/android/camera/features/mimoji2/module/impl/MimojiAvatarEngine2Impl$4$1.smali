.class Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4$1;
.super Ljava/lang/Object;
.source "MimojiAvatarEngine2Impl.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4$1;->this$1:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4$1;->this$1:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$300(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/RecordModule;

    move-result-object p0

    invoke-virtual {p0}, Lcom/arcsoft/avatar/RecordModule;->stopRecording()V

    return-void
.end method
