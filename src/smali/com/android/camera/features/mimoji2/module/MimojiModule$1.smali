.class Lcom/android/camera/features/mimoji2/module/MimojiModule$1;
.super Ljava/lang/Object;
.source "MimojiModule.java"

# interfaces
.implements Lio/reactivex/FlowableOnSubscribe;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/module/MimojiModule;->initMetaParser()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lio/reactivex/FlowableOnSubscribe<",
        "Landroid/hardware/camera2/CaptureResult;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/module/MimojiModule;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/module/MimojiModule;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/MimojiModule$1;->this$0:Lcom/android/camera/features/mimoji2/module/MimojiModule;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public subscribe(Lio/reactivex/FlowableEmitter;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lio/reactivex/FlowableEmitter<",
            "Landroid/hardware/camera2/CaptureResult;",
            ">;)V"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/MimojiModule$1;->this$0:Lcom/android/camera/features/mimoji2/module/MimojiModule;

    invoke-static {p0, p1}, Lcom/android/camera/features/mimoji2/module/MimojiModule;->access$1302(Lcom/android/camera/features/mimoji2/module/MimojiModule;Lio/reactivex/FlowableEmitter;)Lio/reactivex/FlowableEmitter;

    return-void
.end method
