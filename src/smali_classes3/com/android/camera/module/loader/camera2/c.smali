.class public final synthetic Lcom/android/camera/module/loader/camera2/c;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lcom/android/camera2/Camera2Proxy$CaptureBusyCallback;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/module/loader/camera2/Camera2OpenManager;

.field private final synthetic Li:Z


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/loader/camera2/Camera2OpenManager;Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/c;->Hi:Lcom/android/camera/module/loader/camera2/Camera2OpenManager;

    iput-boolean p2, p0, Lcom/android/camera/module/loader/camera2/c;->Li:Z

    return-void
.end method


# virtual methods
.method public final onCaptureCompleted(Z)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/c;->Hi:Lcom/android/camera/module/loader/camera2/Camera2OpenManager;

    iget-boolean p0, p0, Lcom/android/camera/module/loader/camera2/c;->Li:Z

    invoke-virtual {v0, p0, p1}, Lcom/android/camera/module/loader/camera2/Camera2OpenManager;->c(ZZ)V

    return-void
.end method
