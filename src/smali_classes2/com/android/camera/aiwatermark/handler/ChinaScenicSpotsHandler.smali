.class public Lcom/android/camera/aiwatermark/handler/ChinaScenicSpotsHandler;
.super Lcom/android/camera/aiwatermark/handler/ScenicSpotsHandler;
.source "ChinaScenicSpotsHandler.java"


# instance fields
.field private mWatermark:Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;


# direct methods
.method public constructor <init>(Z)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/aiwatermark/handler/ScenicSpotsHandler;-><init>(Z)V

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/android/camera/aiwatermark/handler/ChinaScenicSpotsHandler;->mWatermark:Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;

    new-instance p1, Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;

    invoke-direct {p1}, Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;-><init>()V

    iput-object p1, p0, Lcom/android/camera/aiwatermark/handler/ChinaScenicSpotsHandler;->mWatermark:Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;

    return-void
.end method


# virtual methods
.method protected getRegionMap()Ljava/util/HashMap;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Lcom/android/camera/aiwatermark/data/Region;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/aiwatermark/handler/ChinaScenicSpotsHandler;->mWatermark:Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;->getRegionMap(I)Ljava/util/HashMap;

    move-result-object p0

    return-object p0
.end method

.method protected getWatermarkList()Ljava/util/ArrayList;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/aiwatermark/data/WatermarkItem;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/aiwatermark/handler/ChinaScenicSpotsHandler;->mWatermark:Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;->getForAI()Ljava/util/ArrayList;

    move-result-object p0

    return-object p0
.end method
