.class Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;
.super Ljava/lang/Object;
.source "MimojiAvatarEngine2Impl.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->release()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

.field final synthetic val$hashCode:I


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;I)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    iput p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->val$hashCode:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$000(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Ljava/lang/Object;

    move-result-object v0

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$100(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/AvatarEngine;

    move-result-object v1

    if-eqz v1, :cond_1

    invoke-static {}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$200()Ljava/lang/String;

    move-result-object v1

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "avatar destroy | "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->val$hashCode:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$100(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/AvatarEngine;

    move-result-object v1

    sget-object v2, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TempEditConfigPath:Ljava/lang/String;

    invoke-virtual {v1, v2}, Lcom/arcsoft/avatar/AvatarEngine;->saveConfig(Ljava/lang/String;)I

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$100(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/AvatarEngine;

    move-result-object v1

    invoke-virtual {v1}, Lcom/arcsoft/avatar/AvatarEngine;->destroyOutlineEngine()I

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$100(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/AvatarEngine;

    move-result-object v1

    invoke-virtual {v1}, Lcom/arcsoft/avatar/AvatarEngine;->unInit()V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$300(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/RecordModule;

    move-result-object v1

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$300(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/RecordModule;

    move-result-object v1

    invoke-virtual {v1}, Lcom/arcsoft/avatar/RecordModule;->resetExtraScene()V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-static {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->access$300(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/RecordModule;

    move-result-object p0

    invoke-virtual {p0}, Lcom/arcsoft/avatar/RecordModule;->unInit()V

    :cond_0
    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->release()V

    :cond_1
    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method
