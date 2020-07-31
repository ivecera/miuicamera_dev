.class Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$8;
.super Landroid/os/Handler;
.source "MimojiAvatarEngine2Impl.java"


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
.method constructor <init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Landroid/os/Looper;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$8;->this$0:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 0

    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    iget p0, p1, Landroid/os/Message;->what:I

    if-eqz p0, :cond_0

    const/4 p1, 0x1

    if-eq p0, p1, :cond_0

    const/4 p1, 0x2

    if-eq p0, p1, :cond_0

    const/4 p1, 0x3

    :cond_0
    return-void
.end method
