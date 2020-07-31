.class public Lcom/android/camera/aiwatermark/data/TimeWatermark;
.super Lcom/android/camera/aiwatermark/data/AbstractWatermarkData;
.source "TimeWatermark.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/aiwatermark/data/AbstractWatermarkData;-><init>()V

    return-void
.end method


# virtual methods
.method public getForAI()Ljava/util/ArrayList;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/aiwatermark/data/WatermarkItem;",
            ">;"
        }
    .end annotation

    const/16 v0, 0x9

    invoke-virtual {p0, v0}, Lcom/android/camera/aiwatermark/data/AbstractWatermarkData;->getWatermarkByType(I)Ljava/util/ArrayList;

    move-result-object p0

    return-object p0
.end method

.method public getForManual()Ljava/util/ArrayList;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/aiwatermark/data/WatermarkItem;",
            ">;"
        }
    .end annotation

    const/16 v0, 0x9

    invoke-virtual {p0, v0}, Lcom/android/camera/aiwatermark/data/AbstractWatermarkData;->getWatermarkByType(I)Ljava/util/ArrayList;

    move-result-object p0

    return-object p0
.end method
