.class public Lcom/android/camera/aiwatermark/data/FestivalWatermark;
.super Lcom/android/camera/aiwatermark/data/AbstractWatermarkData;
.source "FestivalWatermark.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/aiwatermark/data/AbstractWatermarkData;-><init>()V

    return-void
.end method

.method private getChinaTraditionWM()Ljava/util/ArrayList;
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/aiwatermark/data/WatermarkItem;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    new-instance v0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const/16 v1, 0xc

    const/4 v2, 0x1

    const/4 v3, 0x2

    const-string v4, "0101"

    invoke-direct {v0, v4, v3, v2, v1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;III)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const-string v4, "0505"

    invoke-direct {v0, v4, v3, v2, v1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;III)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const-string v4, "0815"

    invoke-direct {v0, v4, v3, v2, v1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;III)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const-string v4, "0100"

    invoke-direct {v0, v4, v3, v2, v1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;III)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const-string v4, "0707"

    invoke-direct {v0, v4, v3, v2, v1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;III)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const-string v4, "1208"

    invoke-direct {v0, v4, v3, v2, v1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;III)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    return-object p0
.end method

.method private getInternationalFestivalWM()Ljava/util/ArrayList;
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/aiwatermark/data/WatermarkItem;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    new-instance v0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const/16 v1, 0xc

    const/4 v2, 0x1

    const/4 v3, 0x2

    const-string v4, "0101"

    invoke-direct {v0, v4, v3, v2, v1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;III)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const-string v4, "0214"

    invoke-direct {v0, v4, v3, v2, v1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;III)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const-string v4, "0601"

    invoke-direct {v0, v4, v3, v2, v1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;III)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const-string v4, "1225"

    invoke-direct {v0, v4, v3, v2, v1}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;III)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    return-object p0
.end method


# virtual methods
.method public getFestivalWatermark(I)Ljava/util/ArrayList;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/aiwatermark/data/WatermarkItem;",
            ">;"
        }
    .end annotation

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    if-eqz p1, :cond_1

    const/4 v1, 0x1

    if-eq p1, v1, :cond_0

    const/4 p0, 0x2

    goto :goto_0

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/aiwatermark/data/FestivalWatermark;->getChinaTraditionWM()Ljava/util/ArrayList;

    move-result-object v0

    goto :goto_0

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/aiwatermark/data/FestivalWatermark;->getInternationalFestivalWM()Ljava/util/ArrayList;

    move-result-object v0

    :goto_0
    return-object v0
.end method

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

    const/4 v0, 0x6

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

    const/4 v0, 0x2

    invoke-virtual {p0, v0}, Lcom/android/camera/aiwatermark/data/AbstractWatermarkData;->getWatermarkByType(I)Ljava/util/ArrayList;

    move-result-object p0

    return-object p0
.end method
