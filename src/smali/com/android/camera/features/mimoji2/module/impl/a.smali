.class public final synthetic Lcom/android/camera/features/mimoji2/module/impl/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

.field private final synthetic Li:I

.field private final synthetic Mi:I

.field private final synthetic Ni:I

.field private final synthetic Oi:I

.field private final synthetic Pi:Z


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;IIIIZ)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Hi:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    iput p2, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Li:I

    iput p3, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Mi:I

    iput p4, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Ni:I

    iput p5, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Oi:I

    iput-boolean p6, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Pi:Z

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 6

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Hi:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    iget v1, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Li:I

    iget v2, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Mi:I

    iget v3, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Ni:I

    iget v4, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Oi:I

    iget-boolean v5, p0, Lcom/android/camera/features/mimoji2/module/impl/a;->Pi:Z

    invoke-virtual/range {v0 .. v5}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->b(IIIIZ)V

    return-void
.end method
