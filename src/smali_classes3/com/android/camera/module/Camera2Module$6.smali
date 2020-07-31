.class Lcom/android/camera/module/Camera2Module$6;
.super Ljava/lang/Object;
.source "Camera2Module.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/module/Camera2Module;->onSuperNightChanged(Z)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/module/Camera2Module;

.field final synthetic val$isSuperNight:Z


# direct methods
.method constructor <init>(Lcom/android/camera/module/Camera2Module;Z)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module$6;->this$0:Lcom/android/camera/module/Camera2Module;

    iput-boolean p2, p0, Lcom/android/camera/module/Camera2Module$6;->val$isSuperNight:Z

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module$6;->this$0:Lcom/android/camera/module/Camera2Module;

    iget-boolean p0, p0, Lcom/android/camera/module/Camera2Module$6;->val$isSuperNight:Z

    invoke-static {v0, p0}, Lcom/android/camera/module/Camera2Module;->access$300(Lcom/android/camera/module/Camera2Module;Z)V

    return-void
.end method
