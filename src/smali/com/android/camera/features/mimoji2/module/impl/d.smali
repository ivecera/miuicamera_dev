.class public final synthetic Lcom/android/camera/features/mimoji2/module/impl/d;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

.field private final synthetic Li:Ljava/lang/String;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Ljava/lang/String;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/d;->Hi:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/d;->Li:Ljava/lang/String;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/d;->Hi:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/d;->Li:Ljava/lang/String;

    invoke-virtual {v0, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->r(Ljava/lang/String;)V

    return-void
.end method
