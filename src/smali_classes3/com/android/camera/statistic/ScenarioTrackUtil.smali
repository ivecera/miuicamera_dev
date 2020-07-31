.class public Lcom/android/camera/statistic/ScenarioTrackUtil;
.super Ljava/lang/Object;
.source "ScenarioTrackUtil.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "ScenarioTrackUtil"

.field public static final sCaptureTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

.field public static final sLaunchTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

.field public static final sShotToGalleryTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

.field public static final sShotToViewTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

.field public static final sStartVideoRecordTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

.field public static final sStopVideoRecordTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

.field private static final sSwitchCameraTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

.field public static final sSwitchModeTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    new-instance v0, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    const-string v1, "CameraLaunchTime"

    invoke-direct {v0, v1}, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;-><init>(Ljava/lang/String;)V

    sput-object v0, Lcom/android/camera/statistic/ScenarioTrackUtil;->sLaunchTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    new-instance v0, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    const-string v1, "CaptureTime"

    invoke-direct {v0, v1}, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;-><init>(Ljava/lang/String;)V

    sput-object v0, Lcom/android/camera/statistic/ScenarioTrackUtil;->sCaptureTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    new-instance v0, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    const-string v1, "ShotToGallery"

    invoke-direct {v0, v1}, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;-><init>(Ljava/lang/String;)V

    sput-object v0, Lcom/android/camera/statistic/ScenarioTrackUtil;->sShotToGalleryTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    new-instance v0, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    const-string v1, "ShotToView"

    invoke-direct {v0, v1}, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;-><init>(Ljava/lang/String;)V

    sput-object v0, Lcom/android/camera/statistic/ScenarioTrackUtil;->sShotToViewTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    new-instance v0, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    const-string v1, "SwitchCameraTime"

    invoke-direct {v0, v1}, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;-><init>(Ljava/lang/String;)V

    sput-object v0, Lcom/android/camera/statistic/ScenarioTrackUtil;->sSwitchCameraTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    new-instance v0, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    const-string v1, "SwitchModeTime"

    invoke-direct {v0, v1}, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;-><init>(Ljava/lang/String;)V

    sput-object v0, Lcom/android/camera/statistic/ScenarioTrackUtil;->sSwitchModeTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    new-instance v0, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    const-string v1, "StartVideoRecordTime"

    invoke-direct {v0, v1}, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;-><init>(Ljava/lang/String;)V

    sput-object v0, Lcom/android/camera/statistic/ScenarioTrackUtil;->sStartVideoRecordTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    new-instance v0, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    const-string v1, "StopVideoRecordTime"

    invoke-direct {v0, v1}, Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;-><init>(Ljava/lang/String;)V

    sput-object v0, Lcom/android/camera/statistic/ScenarioTrackUtil;->sStopVideoRecordTimeScenario:Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static trackAppLunchTimeEnd(Ljava/util/Map;Landroid/content/Context;)V
    .locals 0
    .param p0    # Ljava/util/Map;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param

    return-void
.end method

.method public static trackAppLunchTimeStart(Z)V
    .locals 0
    .param p0    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method

.method public static trackCaptureTimeEnd()V
    .locals 0

    return-void
.end method

.method public static trackCaptureTimeStart(ZI)V
    .locals 0
    .param p0    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # I
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method

.method public static trackScenarioAbort(Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;)V
    .locals 0
    .param p0    # Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method

.method public static trackScenarioAbort(Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;Ljava/lang/String;)V
    .locals 0
    .param p0    # Lcom/android/camera/statistic/ScenarioTrackUtil$CameraEventScenario;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # Ljava/lang/String;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param

    return-void
.end method

.method public static trackShotToGalleryEnd(ZJ)V
    .locals 0
    .param p0    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # J
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method

.method public static trackShotToGalleryStart(ZIJ)V
    .locals 0
    .param p0    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # I
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # J
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method

.method public static trackShotToViewEnd(ZJ)V
    .locals 0
    .param p0    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # J
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method

.method public static trackShotToViewStart(ZIJ)V
    .locals 0
    .param p0    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # I
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # J
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method

.method public static trackStartVideoRecordEnd()V
    .locals 0

    return-void
.end method

.method public static trackStartVideoRecordStart(Ljava/lang/String;Z)V
    .locals 0
    .param p0    # Ljava/lang/String;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method

.method public static trackStopVideoRecordEnd()V
    .locals 0

    return-void
.end method

.method public static trackStopVideoRecordStart(Ljava/lang/String;Z)V
    .locals 0
    .param p0    # Ljava/lang/String;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method

.method public static trackSwitchCameraEnd()V
    .locals 0

    return-void
.end method

.method public static trackSwitchCameraStart(ZZI)V
    .locals 0
    .param p0    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # I
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method

.method public static trackSwitchModeEnd()V
    .locals 0

    return-void
.end method

.method public static trackSwitchModeStart(IIZ)V
    .locals 0
    .param p0    # I
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # I
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Z
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    return-void
.end method
