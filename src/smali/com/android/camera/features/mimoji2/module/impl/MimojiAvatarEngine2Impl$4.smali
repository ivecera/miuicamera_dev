.class Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;
.super Ljava/lang/Object;
.source "MimojiAvatarEngine2Impl.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->onRecordStop(Z)V
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

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$300(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/RecordModule;

    move-result-object v0

    if-eqz v0, :cond_0

    new-instance v0, Ljava/lang/Thread;

    new-instance v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4$1;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4$1;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;)V

    invoke-direct {v0, v1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    :cond_0
    return-void
.end method
