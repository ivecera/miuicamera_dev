.class public Lcom/android/camera/module/loader/FunctionParseHistogramStats;
.super Ljava/lang/Object;
.source "FunctionParseHistogramStats.java"

# interfaces
.implements Lio/reactivex/functions/Function;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lio/reactivex/functions/Function<",
        "Landroid/hardware/camera2/CaptureResult;",
        "[I>;"
    }
.end annotation


# instance fields
.field private mStats:[I

.field private mainContentProtocolWeakReference:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/camera/module/loader/FunctionParseHistogramStats;->mainContentProtocolWeakReference:Ljava/lang/ref/WeakReference;

    const/4 p1, 0x0

    new-array p1, p1, [I

    iput-object p1, p0, Lcom/android/camera/module/loader/FunctionParseHistogramStats;->mStats:[I

    return-void
.end method


# virtual methods
.method public bridge synthetic apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    check-cast p1, Landroid/hardware/camera2/CaptureResult;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/loader/FunctionParseHistogramStats;->apply(Landroid/hardware/camera2/CaptureResult;)[I

    move-result-object p0

    return-object p0
.end method

.method public apply(Landroid/hardware/camera2/CaptureResult;)[I
    .locals 5
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/module/loader/FunctionParseHistogramStats;->mainContentProtocolWeakReference:Ljava/lang/ref/WeakReference;

    if-nez v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/module/loader/FunctionParseHistogramStats;->mStats:[I

    return-object p0

    :cond_0
    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    if-nez v0, :cond_1

    iget-object p0, p0, Lcom/android/camera/module/loader/FunctionParseHistogramStats;->mStats:[I

    return-object p0

    :cond_1
    invoke-static {p1}, Lcom/android/camera2/CaptureResultParser;->getHistogramStats(Landroid/hardware/camera2/CaptureResult;)[I

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/module/loader/FunctionParseHistogramStats;->mStats:[I

    iget-object p1, p0, Lcom/android/camera/module/loader/FunctionParseHistogramStats;->mStats:[I

    if-eqz p1, :cond_4

    const/16 p1, 0x100

    new-array v1, p1, [I

    const/4 v2, 0x0

    :goto_0
    if-ge v2, p1, :cond_3

    iget-object v3, p0, Lcom/android/camera/module/loader/FunctionParseHistogramStats;->mStats:[I

    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v4

    if-eqz v4, :cond_2

    move v4, v2

    goto :goto_1

    :cond_2
    mul-int/lit8 v4, v2, 0x3

    :goto_1
    aget v3, v3, v4

    aput v3, v1, v2

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_3
    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->updateHistogramStatsData([I)V

    :cond_4
    iget-object p0, p0, Lcom/android/camera/module/loader/FunctionParseHistogramStats;->mStats:[I

    return-object p0
.end method
