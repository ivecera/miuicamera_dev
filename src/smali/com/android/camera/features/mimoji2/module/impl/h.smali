.class public final synthetic Lcom/android/camera/features/mimoji2/module/impl/h;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lcom/xiaomi/Video2GifEditer/MediaProcess$Callback;


# instance fields
.field private final synthetic Hi:I

.field private final synthetic Li:Ljava/util/List;


# direct methods
.method public synthetic constructor <init>(ILjava/util/List;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lcom/android/camera/features/mimoji2/module/impl/h;->Hi:I

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/h;->Li:Ljava/util/List;

    return-void
.end method


# virtual methods
.method public final OnConvertProgress(I)V
    .locals 1

    iget v0, p0, Lcom/android/camera/features/mimoji2/module/impl/h;->Hi:I

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/h;->Li:Ljava/util/List;

    invoke-static {v0, p0, p1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiVideoEditorImpl;->a(ILjava/util/List;I)V

    return-void
.end method
