.class public Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;
.super Lcom/android/camera/data/data/ComponentData;
.source "ComponentRunningAIWatermark.java"


# static fields
.field private static mCurrentType:Ljava/lang/String;


# instance fields
.field private mIWatermarkEnable:Z

.field private mIsClosed:Z

.field private mList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/data/data/ComponentDataItem;",
            ">;"
        }
    .end annotation
.end field

.field private mWatermarkItem:Lcom/android/camera/aiwatermark/data/WatermarkItem;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mCurrentType:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Lcom/android/camera/data/data/runing/DataItemRunning;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/ComponentData;-><init>(Lcom/android/camera/data/data/DataItemBase;)V

    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    iput-object p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mList:Ljava/util/ArrayList;

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mIsClosed:Z

    iput-boolean p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mIWatermarkEnable:Z

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mWatermarkItem:Lcom/android/camera/aiwatermark/data/WatermarkItem;

    return-void
.end method

.method public constructor <init>(Lcom/android/camera/data/data/runing/DataItemRunning;Ljava/util/ArrayList;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/camera/data/data/runing/DataItemRunning;",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/data/data/ComponentDataItem;",
            ">;)V"
        }
    .end annotation

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;-><init>(Lcom/android/camera/data/data/runing/DataItemRunning;)V

    iput-object p2, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mList:Ljava/util/ArrayList;

    return-void
.end method


# virtual methods
.method public getAIWatermarkEnable()Z
    .locals 1

    const/16 v0, 0xa0

    invoke-virtual {p0, v0}, Lcom/android/camera/data/data/ComponentData;->getComponentValue(I)Ljava/lang/String;

    move-result-object p0

    :try_start_0
    invoke-static {p0}, Ljava/lang/Boolean;->valueOf(Ljava/lang/String;)Ljava/lang/Boolean;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return p0

    :catch_0
    const/4 p0, 0x0

    return p0
.end method

.method public getAIWatermarkEnable(I)Z
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getAIWatermarkEnable()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->supportTopConfigEntry(I)Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public getCurrentType()Ljava/lang/String;
    .locals 0

    sget-object p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mCurrentType:Ljava/lang/String;

    return-object p0
.end method

.method public getDefaultValue(I)Ljava/lang/String;
    .locals 0
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    const-string p0, "off"

    return-object p0
.end method

.method public getDisplayTitleString()I
    .locals 0

    const/4 p0, 0x0

    return p0
.end method

.method public getIWatermarkEnable()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mIWatermarkEnable:Z

    return p0
.end method

.method public getItems()Ljava/util/List;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/android/camera/data/data/ComponentDataItem;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mList:Ljava/util/ArrayList;

    return-object p0
.end method

.method public getKey(I)Ljava/lang/String;
    .locals 0

    const-string p0, "pref_watermark_key"

    return-object p0
.end method

.method public getResIcon(Z)I
    .locals 0

    if-eqz p1, :cond_0

    const p0, 0x7f080014

    return p0

    :cond_0
    const p0, 0x7f080013

    return p0
.end method

.method public getWatermarkItem()Lcom/android/camera/aiwatermark/data/WatermarkItem;
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getAIWatermarkEnable()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mWatermarkItem:Lcom/android/camera/aiwatermark/data/WatermarkItem;

    return-object p0

    :cond_0
    const/4 p0, 0x0

    return-object p0
.end method

.method public isClosed()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mIsClosed:Z

    return p0
.end method

.method public needActive()Z
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getAIWatermarkEnable()Z

    move-result v0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getIWatermarkEnable()Z

    move-result p0

    if-eqz v0, :cond_0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public resetAIWatermark(Z)V
    .locals 0

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->setAIWatermarkEnable(Z)V

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->setIWatermarkEnable(Z)V

    const-string p1, "ai_trigger"

    sput-object p1, Lcom/android/camera/aiwatermark/util/WatermarkConstant;->SELECT_KEY:Ljava/lang/String;

    const/4 p1, 0x0

    invoke-static {p1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object p1

    sput-object p1, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mCurrentType:Ljava/lang/String;

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mIsClosed:Z

    return-void
.end method

.method public setAIWatermarkEnable(Z)V
    .locals 1

    invoke-static {p1}, Ljava/lang/String;->valueOf(Z)Ljava/lang/String;

    move-result-object p1

    const/16 v0, 0xa0

    invoke-virtual {p0, v0, p1}, Lcom/android/camera/data/data/ComponentData;->setComponentValue(ILjava/lang/String;)V

    return-void
.end method

.method public setClosed(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mIsClosed:Z

    return-void
.end method

.method public setCurrentType(Ljava/lang/String;)V
    .locals 0

    sput-object p1, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mCurrentType:Ljava/lang/String;

    return-void
.end method

.method public setHasMove(Z)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mWatermarkItem:Lcom/android/camera/aiwatermark/data/WatermarkItem;

    if-eqz p0, :cond_0

    invoke-virtual {p0, p1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->setHasMove(Z)V

    :cond_0
    return-void
.end method

.method public setIWatermarkEnable(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mIWatermarkEnable:Z

    return-void
.end method

.method public supportTopConfigEntry(I)Z
    .locals 0

    const/16 p0, 0xa3

    if-eq p1, p0, :cond_0

    const/4 p0, 0x0

    goto :goto_0

    :cond_0
    const/4 p0, 0x1

    :goto_0
    return p0
.end method

.method public updateLocation([ILandroid/graphics/Rect;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mWatermarkItem:Lcom/android/camera/aiwatermark/data/WatermarkItem;

    if-eqz v0, :cond_0

    invoke-virtual {v0, p1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->updateCoordinate([I)V

    iget-object p0, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mWatermarkItem:Lcom/android/camera/aiwatermark/data/WatermarkItem;

    invoke-virtual {p0, p2}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->setLimitArea(Landroid/graphics/Rect;)V

    :cond_0
    return-void
.end method

.method public updateWatermarkItem(Lcom/android/camera/aiwatermark/data/WatermarkItem;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->mWatermarkItem:Lcom/android/camera/aiwatermark/data/WatermarkItem;

    return-void
.end method
