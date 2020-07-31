.class public final synthetic Lcom/android/camera/module/loader/camera2/d;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lio/reactivex/functions/Function;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/d;->Hi:Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;

    return-void
.end method


# virtual methods
.method public final apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/d;->Hi:Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;

    check-cast p1, Ljava/lang/Throwable;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->i(Ljava/lang/Throwable;)Lio/reactivex/ObservableSource;

    move-result-object p0

    return-object p0
.end method
