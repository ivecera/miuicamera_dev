.class public Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;
.super Ljava/lang/Object;
.source "ParallelSnapshotManager.java"


# annotations
.annotation build Landroid/annotation/SuppressLint;
    value = {
        "MissingPermission"
    }
.end annotation

.annotation build Landroid/annotation/TargetApi;
    value = 0x15
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$CaptureSessionStateCallback;,
        Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$ManagerState;
    }
.end annotation


# static fields
.field private static final CAMERA_ID_VIRTUAL_PARALLEL:I = 0x66

.field private static final CAMERA_OPEN_OR_CLOSE_TIMEOUT:J = 0x1388L

.field private static final MANAGER_MSG_CLOSE_CAMERA:I = 0x2

.field private static final MANAGER_MSG_CLOSE_FINISH:I = 0x3

.field private static final MANAGER_MSG_CLOSE_SESSION:I = 0x6

.field private static final MANAGER_MSG_CREATE_SESSION:I = 0x5

.field private static final MANAGER_MSG_OPEN_FINISH:I = 0x4

.field private static final MANAGER_MSG_REQUEST_CAMERA:I = 0x1

.field private static final MANAGER_STATE_IDLE:I = 0x1

.field private static final MANAGER_STATE_PENDING_CREATE_SESSION:I = 0x5

.field private static final MANAGER_STATE_WAITING_CLOSE:I = 0x2

.field private static final MANAGER_STATE_WAITING_CREATE_SESSION:I = 0x4

.field private static final MANAGER_STATE_WAITING_OPEN:I = 0x3

.field public static final REASON_DISCONNECTED:I = 0x1

.field private static final TAG:Ljava/lang/String; = "ParallelSnapshotManager"

.field private static sInstance:Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;


# instance fields
.field private mCameraCaptureSession:Landroid/hardware/camera2/CameraCaptureSession;

.field private mCameraDevice:Landroid/hardware/camera2/CameraDevice;

.field private mCameraHandler:Landroid/os/Handler;

.field private mCameraManager:Landroid/hardware/camera2/CameraManager;

.field private final mCameraOpenCallback:Landroid/hardware/camera2/CameraDevice$StateCallback;

.field private mCameraResultEmitter:Lio/reactivex/ObservableEmitter;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lio/reactivex/ObservableEmitter<",
            "Lcom/android/camera/module/loader/camera2/Camera2Result;",
            ">;"
        }
    .end annotation
.end field

.field private mCameraResultObservable:Lio/reactivex/observables/ConnectableObservable;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lio/reactivex/observables/ConnectableObservable<",
            "Lcom/android/camera/module/loader/camera2/Camera2Result;",
            ">;"
        }
    .end annotation
.end field

.field private final mCaptureLock:Ljava/lang/Object;

.field private mCurrentPreviewCameraID:I

.field private mCurrentState:I

.field private mCurrentSurfaceList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Landroid/view/Surface;",
            ">;"
        }
    .end annotation
.end field

.field private final mEmitterLock:Ljava/lang/Object;

.field private mIsCameraOpened:Z

.field private final mMaxQueueSize:I

.field private mPendingSurfaceList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Landroid/view/Surface;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method private constructor <init>()V
    .locals 4

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mEmitterLock:Ljava/lang/Object;

    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCaptureLock:Ljava/lang/Object;

    const/4 v0, 0x1

    iput v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentState:I

    new-instance v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$2;

    invoke-direct {v0, p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$2;-><init>(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;)V

    iput-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraOpenCallback:Landroid/hardware/camera2/CameraDevice$StateCallback;

    new-instance v0, Landroid/os/HandlerThread;

    const-string v1, "VT Camera Handler Thread"

    invoke-direct {v0, v1}, Landroid/os/HandlerThread;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Landroid/os/HandlerThread;->start()V

    new-instance v1, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$1;

    invoke-virtual {v0}, Landroid/os/HandlerThread;->getLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-direct {v1, p0, v0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$1;-><init>(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;Landroid/os/Looper;)V

    iput-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "camera"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/hardware/camera2/CameraManager;

    iput-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraManager:Landroid/hardware/camera2/CameraManager;

    invoke-static {}, Landroid/os/Process;->getTotalMemory()J

    move-result-wide v0

    const-wide/32 v2, 0x40000000

    div-long/2addr v0, v2

    const-wide/16 v2, 0x6

    cmp-long v0, v0, v2

    if-gez v0, :cond_0

    const/4 v0, 0x3

    iput v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mMaxQueueSize:I

    goto :goto_0

    :cond_0
    const/4 v0, 0x5

    iput v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mMaxQueueSize:I

    :goto_0
    return-void
.end method

.method private abandonOpenObservableIfExists()V
    .locals 4

    sget-object v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string v1, "abandonOpenObservableIfExists: E"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mEmitterLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "abandonOpenObservableIfExists: start mCameraResultEmitter = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    invoke-interface {v1}, Lio/reactivex/ObservableEmitter;->isDisposed()Z

    move-result v1

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    const/4 v2, 0x3

    invoke-static {v2}, Lcom/android/camera/module/loader/camera2/Camera2Result;->create(I)Lcom/android/camera/module/loader/camera2/Camera2Result;

    move-result-object v2

    const/16 v3, 0xe1

    invoke-virtual {v2, v3}, Lcom/android/camera/module/loader/camera2/Camera2Result;->setCameraError(I)Lcom/android/camera/module/loader/camera2/Camera2Result;

    move-result-object v2

    invoke-interface {v1, v2}, Lio/reactivex/Emitter;->onNext(Ljava/lang/Object;)V

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    invoke-interface {v1}, Lio/reactivex/Emitter;->onComplete()V

    const/4 v1, 0x0

    iput-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    :cond_0
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    sget-object p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string v0, "abandonOpenObservableIfExists: X"

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :catchall_0
    move-exception p0

    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw p0
.end method

.method static synthetic access$000(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;Landroid/os/Message;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->onMessage(Landroid/os/Message;)V

    return-void
.end method

.method static synthetic access$100()Ljava/lang/String;
    .locals 1

    sget-object v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1002(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;Ljava/util/List;)Ljava/util/List;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    return-object p1
.end method

.method static synthetic access$1102(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;Ljava/util/List;)Ljava/util/List;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mPendingSurfaceList:Ljava/util/List;

    return-object p1
.end method

.method static synthetic access$200(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;)Ljava/lang/Object;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCaptureLock:Ljava/lang/Object;

    return-object p0
.end method

.method static synthetic access$302(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;Landroid/hardware/camera2/CameraCaptureSession;)Landroid/hardware/camera2/CameraCaptureSession;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraCaptureSession:Landroid/hardware/camera2/CameraCaptureSession;

    return-object p1
.end method

.method static synthetic access$400(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;I)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->setManagerState(I)V

    return-void
.end method

.method static synthetic access$500(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;)Landroid/os/Handler;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    return-object p0
.end method

.method static synthetic access$600(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;ILjava/lang/String;)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->onCameraOpenFailed(ILjava/lang/String;)V

    return-void
.end method

.method static synthetic access$700(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;)Landroid/hardware/camera2/CameraDevice;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    return-object p0
.end method

.method static synthetic access$702(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;Landroid/hardware/camera2/CameraDevice;)Landroid/hardware/camera2/CameraDevice;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    return-object p1
.end method

.method static synthetic access$800(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->removeAllAppMessages()V

    return-void
.end method

.method static synthetic access$902(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mIsCameraOpened:Z

    return p1
.end method

.method private attachInObservable(Lio/reactivex/Observer;)Z
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lio/reactivex/Observer<",
            "Lcom/android/camera/module/loader/camera2/Camera2Result;",
            ">;)Z"
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mEmitterLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    invoke-interface {v1}, Lio/reactivex/ObservableEmitter;->isDisposed()Z

    move-result v1

    if-eqz v1, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultObservable:Lio/reactivex/observables/ConnectableObservable;

    invoke-virtual {p0, p1}, Lio/reactivex/Observable;->subscribe(Lio/reactivex/Observer;)V

    const/4 p0, 0x1

    goto :goto_1

    :cond_1
    :goto_0
    new-instance v1, Lcom/android/camera/module/loader/camera2/e;

    invoke-direct {v1, p0}, Lcom/android/camera/module/loader/camera2/e;-><init>(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;)V

    invoke-static {v1}, Lio/reactivex/Observable;->create(Lio/reactivex/ObservableOnSubscribe;)Lio/reactivex/Observable;

    move-result-object v1

    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->getCameraOpTimeout()J

    move-result-wide v2

    sget-object v4, Ljava/util/concurrent/TimeUnit;->MILLISECONDS:Ljava/util/concurrent/TimeUnit;

    invoke-virtual {v1, v2, v3, v4}, Lio/reactivex/Observable;->timeout(JLjava/util/concurrent/TimeUnit;)Lio/reactivex/Observable;

    move-result-object v1

    new-instance v2, Lcom/android/camera/module/loader/camera2/d;

    invoke-direct {v2, p0}, Lcom/android/camera/module/loader/camera2/d;-><init>(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;)V

    invoke-virtual {v1, v2}, Lio/reactivex/Observable;->onErrorResumeNext(Lio/reactivex/functions/Function;)Lio/reactivex/Observable;

    move-result-object v1

    sget-object v2, Lcom/android/camera/constant/GlobalConstant;->sCameraSetupScheduler:Lio/reactivex/Scheduler;

    invoke-virtual {v1, v2}, Lio/reactivex/Observable;->observeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Observable;

    move-result-object v1

    invoke-virtual {v1}, Lio/reactivex/Observable;->publish()Lio/reactivex/observables/ConnectableObservable;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultObservable:Lio/reactivex/observables/ConnectableObservable;

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultObservable:Lio/reactivex/observables/ConnectableObservable;

    invoke-virtual {v1, p1}, Lio/reactivex/Observable;->subscribe(Lio/reactivex/Observer;)V

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultObservable:Lio/reactivex/observables/ConnectableObservable;

    invoke-virtual {p0}, Lio/reactivex/observables/ConnectableObservable;->connect()Lio/reactivex/disposables/Disposable;

    const/4 p0, 0x0

    :goto_1
    monitor-exit v0

    return p0

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method private getCameraOpTimeout()J
    .locals 6

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    const-wide/16 v0, 0x1388

    if-eqz p0, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->getExposureTime()J

    move-result-wide v2

    const-wide/32 v4, 0xf4240

    div-long/2addr v2, v4

    add-long/2addr v0, v2

    :cond_0
    return-wide v0
.end method

.method public static declared-synchronized getInstance()Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;
    .locals 2

    const-class v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->sInstance:Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;

    if-nez v1, :cond_0

    new-instance v1, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;

    invoke-direct {v1}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;-><init>()V

    sput-object v1, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->sInstance:Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;

    :cond_0
    sget-object v1, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->sInstance:Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit v0

    return-object v1

    :catchall_0
    move-exception v1

    monitor-exit v0

    throw v1
.end method

.method private getManagerState()I
    .locals 0
    .annotation build Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$ManagerState;
    .end annotation

    iget p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentState:I

    return p0
.end method

.method private onCameraOpenFailed(ILjava/lang/String;)V
    .locals 3

    sget-object v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onCameraOpenFailed: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " msg:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {v0, p2}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p2, 0x1

    invoke-direct {p0, p2}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->setManagerState(I)V

    iget-object p2, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mEmitterLock:Ljava/lang/Object;

    monitor-enter p2

    :try_start_0
    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    const/4 v1, 0x3

    invoke-static {v1}, Lcom/android/camera/module/loader/camera2/Camera2Result;->create(I)Lcom/android/camera/module/loader/camera2/Camera2Result;

    move-result-object v1

    invoke-virtual {v1, p1}, Lcom/android/camera/module/loader/camera2/Camera2Result;->setCameraError(I)Lcom/android/camera/module/loader/camera2/Camera2Result;

    move-result-object p1

    invoke-interface {v0, p1}, Lio/reactivex/Emitter;->onNext(Ljava/lang/Object;)V

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    invoke-interface {p0}, Lio/reactivex/Emitter;->onComplete()V

    :cond_0
    monitor-exit p2

    return-void

    :catchall_0
    move-exception p0

    monitor-exit p2
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method private onCameraOpenSuccess()V
    .locals 1

    const/4 v0, 0x1

    invoke-direct {p0, v0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->setManagerState(I)V

    sget-object p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string v0, "onCameraOpenSuccess: E"

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private onMessage(Landroid/os/Message;)V
    .locals 7

    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x3

    const/4 v2, 0x2

    const/4 v3, 0x5

    const/4 v4, 0x1

    const/4 v5, 0x0

    packed-switch v0, :pswitch_data_0

    goto/16 :goto_1

    :pswitch_0
    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->getManagerState()I

    move-result p1

    if-eq p1, v4, :cond_0

    sget-object p1, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "manager state"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->getManagerState()I

    move-result p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCaptureLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraCaptureSession:Landroid/hardware/camera2/CameraCaptureSession;

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraCaptureSession:Landroid/hardware/camera2/CameraCaptureSession;

    invoke-virtual {p1}, Landroid/hardware/camera2/CameraCaptureSession;->close()V

    iput-object v5, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraCaptureSession:Landroid/hardware/camera2/CameraCaptureSession;

    iput-object v5, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    :cond_1
    iget-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mPendingSurfaceList:Ljava/util/List;

    if-eqz p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mPendingSurfaceList:Ljava/util/List;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->createCaptureSession(Ljava/util/List;)V

    iput-object v5, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mPendingSurfaceList:Ljava/util/List;

    :cond_2
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    sget-object p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string p1, "CaptureSession close"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    :catchall_0
    move-exception p0

    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw p0

    :pswitch_1
    sget-object v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string v6, "create CaptureSession >>>>>>"

    invoke-static {v0, v6}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->getManagerState()I

    move-result v0

    if-eq v0, v4, :cond_3

    sget-object v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "manager state"

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->getManagerState()I

    move-result v4

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->getManagerState()I

    move-result v0

    if-ne v0, v1, :cond_9

    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Ljava/util/List;

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mPendingSurfaceList:Ljava/util/List;

    invoke-direct {p0, v3}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->setManagerState(I)V

    sget-object p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string p1, "waiting camera open finsi to recreate session"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    :cond_3
    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCaptureLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_2
    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    if-eqz v1, :cond_4

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    iget-object v3, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v3, Ljava/util/List;

    invoke-interface {v1, v3}, Ljava/util/List;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_4

    sget-object p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string p1, "the same surface, skip"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    monitor-exit v0

    goto/16 :goto_1

    :cond_4
    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraCaptureSession:Landroid/hardware/camera2/CameraCaptureSession;

    if-eqz v1, :cond_5

    sget-object v1, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string v2, "cameraCaptureSession is not null"

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Ljava/util/List;

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mPendingSurfaceList:Ljava/util/List;

    monitor-exit v0

    goto/16 :goto_1

    :cond_5
    const/4 v1, 0x4

    invoke-direct {p0, v1}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->setManagerState(I)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    :try_start_3
    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    invoke-virtual {v1, v2}, Landroid/hardware/camera2/CameraDevice;->createCaptureRequest(I)Landroid/hardware/camera2/CaptureRequest$Builder;

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    iget-object v2, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v2, Ljava/util/List;

    new-instance v3, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$CaptureSessionStateCallback;

    invoke-direct {v3, p0, v5}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$CaptureSessionStateCallback;-><init>(Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$1;)V

    iget-object v4, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    invoke-virtual {v1, v2, v3, v4}, Landroid/hardware/camera2/CameraDevice;->createCaptureSession(Ljava/util/List;Landroid/hardware/camera2/CameraCaptureSession$StateCallback;Landroid/os/Handler;)V
    :try_end_3
    .catch Landroid/hardware/camera2/CameraAccessException; {:try_start_3 .. :try_end_3} :catch_0
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    goto :goto_0

    :catch_0
    move-exception v1

    :try_start_4
    invoke-virtual {v1}, Landroid/hardware/camera2/CameraAccessException;->printStackTrace()V

    :goto_0
    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Ljava/util/List;

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    monitor-exit v0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    sget-object p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string p1, "createSession<<"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    :catchall_1
    move-exception p0

    :try_start_5
    monitor-exit v0
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_1

    throw p0

    :pswitch_2
    sget-object v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string v1, "open finish"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v0, Landroid/hardware/camera2/CameraDevice;

    iput-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->getManagerState()I

    move-result v0

    if-ne v0, v3, :cond_6

    sget-object v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string v1, "try to recreate session"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0, v4}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->setManagerState(I)V

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mPendingSurfaceList:Ljava/util/List;

    invoke-virtual {p0, v0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->createCaptureSession(Ljava/util/List;)V

    iput-object v5, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mPendingSurfaceList:Ljava/util/List;

    :cond_6
    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Landroid/hardware/camera2/CameraDevice;

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    invoke-direct {p0, v4}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->setManagerState(I)V

    goto/16 :goto_1

    :pswitch_3
    sget-object p1, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string v0, "close finish"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0, v4}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->setManagerState(I)V

    iput-object v5, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    iput-object v5, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraCaptureSession:Landroid/hardware/camera2/CameraCaptureSession;

    iput-object v5, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    goto/16 :goto_1

    :pswitch_4
    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->getManagerState()I

    move-result v0

    if-eq v0, v4, :cond_7

    sget-object v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "not idle, break on msg.what "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget p1, p1, Landroid/os/Message;->what:I

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, ", mCurrentState "

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentState:I

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    :cond_7
    invoke-direct {p0, v2}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->setManagerState(I)V

    iget p1, p1, Landroid/os/Message;->arg1:I

    sget-object v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "force close start reason "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    invoke-virtual {p1}, Landroid/hardware/camera2/CameraDevice;->close()V

    iput-object v5, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    iput-object v5, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraCaptureSession:Landroid/hardware/camera2/CameraCaptureSession;

    iput-object v5, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    goto :goto_1

    :pswitch_5
    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->getManagerState()I

    move-result p1

    if-eq p1, v4, :cond_8

    goto :goto_1

    :cond_8
    :try_start_6
    sget-object p1, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string v0, "open start"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0, v1}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->setManagerState(I)V

    iget-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraManager:Landroid/hardware/camera2/CameraManager;

    const/16 v0, 0x66

    invoke-static {v0}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraOpenCallback:Landroid/hardware/camera2/CameraDevice$StateCallback;

    iget-object v2, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    invoke-virtual {p1, v0, v1, v2}, Landroid/hardware/camera2/CameraManager;->openCamera(Ljava/lang/String;Landroid/hardware/camera2/CameraDevice$StateCallback;Landroid/os/Handler;)V
    :try_end_6
    .catch Landroid/hardware/camera2/CameraAccessException; {:try_start_6 .. :try_end_6} :catch_1
    .catch Ljava/lang/IllegalArgumentException; {:try_start_6 .. :try_end_6} :catch_1
    .catch Ljava/lang/SecurityException; {:try_start_6 .. :try_end_6} :catch_1

    goto :goto_1

    :catch_1
    move-exception p1

    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    const/16 v0, 0xe6

    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v1

    invoke-direct {p0, v0, v1}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->onCameraOpenFailed(ILjava/lang/String;)V

    sget-object p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string v0, "openCamera: failed to open camera 102"

    invoke-static {p0, v0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :cond_9
    :goto_1
    return-void

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private removeAllAppMessages()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    const/4 v0, 0x2

    invoke-virtual {p0, v0}, Landroid/os/Handler;->removeMessages(I)V

    return-void
.end method

.method private setManagerState(I)V
    .locals 0
    .param p1    # I
        .annotation build Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager$ManagerState;
        .end annotation
    .end param

    iput p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentState:I

    return-void
.end method


# virtual methods
.method public closeCamera()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCaptureLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-boolean v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mIsCameraOpened:Z

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    const/4 v2, 0x2

    invoke-virtual {v1, v2}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    const/4 v1, 0x0

    iput-boolean v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mIsCameraOpened:Z

    :cond_0
    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public closeCaptureSession(Ljava/util/List;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Landroid/view/Surface;",
            ">;)V"
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCaptureLock:Ljava/lang/Object;

    monitor-enter v0

    if-eqz p1, :cond_0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    invoke-interface {p1, v1}, Ljava/util/List;->containsAll(Ljava/util/Collection;)Z

    move-result p1

    if-eqz p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    const/4 p1, 0x6

    invoke-virtual {p0, p1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    :cond_0
    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public createCaptureSession(Ljava/util/List;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Landroid/view/Surface;",
            ">;)V"
        }
    .end annotation

    invoke-static {}, Landroid/os/Message;->obtain()Landroid/os/Message;

    move-result-object v0

    iput-object p1, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    const/4 p1, 0x5

    iput p1, v0, Landroid/os/Message;->what:I

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    invoke-virtual {p0, v0}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public synthetic e(Lio/reactivex/ObservableEmitter;)V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraResultEmitter:Lio/reactivex/ObservableEmitter;

    return-void
.end method

.method public getCameraDevice()Landroid/hardware/camera2/CameraDevice;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCaptureLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraDevice:Landroid/hardware/camera2/CameraDevice;

    monitor-exit v0

    return-object p0

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public getCaptureSession()Landroid/hardware/camera2/CameraCaptureSession;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCaptureLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraCaptureSession:Landroid/hardware/camera2/CameraCaptureSession;

    monitor-exit v0

    return-object p0

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public getCaptureSurface(I)Landroid/view/Surface;
    .locals 2

    const/4 v0, 0x1

    if-eq p1, v0, :cond_1

    const/4 v1, 0x2

    if-eq p1, v1, :cond_0

    const/4 p0, 0x0

    return-object p0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    invoke-interface {p0, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/view/Surface;

    return-object p0

    :cond_1
    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentSurfaceList:Ljava/util/List;

    const/4 p1, 0x0

    invoke-interface {p0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/view/Surface;

    return-object p0
.end method

.method public getMaxQueueSize()I
    .locals 0

    iget p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mMaxQueueSize:I

    return p0
.end method

.method public synthetic i(Ljava/lang/Throwable;)Lio/reactivex/ObservableSource;
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    sget-object v0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Exception occurs in camera open or close: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    invoke-virtual {p0}, Landroid/os/Handler;->getLooper()Landroid/os/Looper;

    move-result-object p0

    invoke-virtual {p0}, Landroid/os/Looper;->getQueue()Landroid/os/MessageQueue;

    move-result-object p0

    invoke-virtual {p0}, Landroid/os/MessageQueue;->isPolling()Z

    move-result p0

    if-nez p0, :cond_0

    sget-object p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->TAG:Ljava/lang/String;

    const-string p1, "CameraHandlerThread is being stuck..."

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    const/4 p0, 0x3

    invoke-static {p0}, Lcom/android/camera/module/loader/camera2/Camera2Result;->create(I)Lcom/android/camera/module/loader/camera2/Camera2Result;

    move-result-object p0

    const/16 p1, 0xec

    invoke-virtual {p0, p1}, Lcom/android/camera/module/loader/camera2/Camera2Result;->setCameraError(I)Lcom/android/camera/module/loader/camera2/Camera2Result;

    move-result-object p0

    invoke-static {p0}, Lio/reactivex/Observable;->just(Ljava/lang/Object;)Lio/reactivex/Observable;

    move-result-object p0

    return-object p0
.end method

.method public isParallelCameraDeviceConfiged(Lcom/android/camera2/CameraCapabilities;)Z
    .locals 0

    if-eqz p1, :cond_0

    invoke-virtual {p1}, Lcom/android/camera2/CameraCapabilities;->isSupportPallelCameraDevice()Z

    move-result p0

    return p0

    :cond_0
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object p1

    iget p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentPreviewCameraID:I

    invoke-virtual {p1, p0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getCapabilities(I)Lcom/android/camera2/CameraCapabilities;

    move-result-object p0

    if-eqz p0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera2/CameraCapabilities;->isSupportPallelCameraDevice()Z

    move-result p0

    return p0

    :cond_1
    const/4 p0, 0x0

    return p0
.end method

.method public isParallelSessionReady()Z
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mPendingSurfaceList:Ljava/util/List;

    if-nez v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraCaptureSession:Landroid/hardware/camera2/CameraCaptureSession;

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public openCamera(ILio/reactivex/Observer;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I",
            "Lio/reactivex/Observer<",
            "Lcom/android/camera/module/loader/camera2/Camera2Result;",
            ">;)V"
        }
    .end annotation

    iput p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCurrentPreviewCameraID:I

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->isParallelCameraDeviceConfiged(Lcom/android/camera2/CameraCapabilities;)Z

    move-result p1

    if-nez p1, :cond_0

    return-void

    :cond_0
    iget-object p1, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCaptureLock:Ljava/lang/Object;

    monitor-enter p1

    :try_start_0
    iget-boolean v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mIsCameraOpened:Z

    if-nez v0, :cond_1

    invoke-direct {p0, p2}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->attachInObservable(Lio/reactivex/Observer;)Z

    iget-object p2, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    const/4 v0, 0x1

    invoke-virtual {p2, v0}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    iput-boolean v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mIsCameraOpened:Z

    :cond_1
    monitor-exit p1

    return-void

    :catchall_0
    move-exception p0

    monitor-exit p1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public release()V
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->abandonOpenObservableIfExists()V

    iget-object v0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/ParallelSnapshotManager;->mCameraHandler:Landroid/os/Handler;

    const/4 v0, 0x2

    invoke-virtual {p0, v0}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v0

    invoke-virtual {p0, v0}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method
