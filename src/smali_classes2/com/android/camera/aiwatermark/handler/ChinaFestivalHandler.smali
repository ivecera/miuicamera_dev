.class public Lcom/android/camera/aiwatermark/handler/ChinaFestivalHandler;
.super Lcom/android/camera/aiwatermark/handler/FestivalHandler;
.source "ChinaFestivalHandler.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "ChinaFestivalHandler"


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(Z)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/aiwatermark/handler/FestivalHandler;-><init>(Z)V

    return-void
.end method

.method private findFestivalWatermark(Ljava/lang/String;I)Lcom/android/camera/aiwatermark/data/WatermarkItem;
    .locals 2

    new-instance p0, Lcom/android/camera/aiwatermark/data/FestivalWatermark;

    invoke-direct {p0}, Lcom/android/camera/aiwatermark/data/FestivalWatermark;-><init>()V

    invoke-virtual {p0, p2}, Lcom/android/camera/aiwatermark/data/FestivalWatermark;->getFestivalWatermark(I)Ljava/util/ArrayList;

    move-result-object p0

    sget-object p2, Lcom/android/camera/aiwatermark/handler/ChinaFestivalHandler;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "list.size() = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/util/ArrayList;->size()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p2, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result p2

    if-eqz p2, :cond_1

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object p2

    check-cast p2, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    invoke-virtual {p2}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getKey()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-object p2

    :cond_1
    const/4 p0, 0x0

    return-object p0
.end method

.method private getChinaDate()Ljava/lang/String;
    .locals 0

    invoke-static {}, Lcom/android/camera/aiwatermark/algo/ChinaDateConvertor;->today()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private getDate()Ljava/lang/String;
    .locals 0

    invoke-static {}, Lcom/android/camera/aiwatermark/algo/ChinaDateConvertor;->getDate()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method


# virtual methods
.method protected findWatermark()Lcom/android/camera/aiwatermark/data/WatermarkItem;
    .locals 4

    invoke-static {}, Lcom/android/camera/aiwatermark/algo/ChinaDateConvertor;->isChineseEve()Z

    move-result v0

    if-eqz v0, :cond_0

    const-string v0, "0100"

    goto :goto_0

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/aiwatermark/handler/ChinaFestivalHandler;->getChinaDate()Ljava/lang/String;

    move-result-object v0

    :goto_0
    sget-object v1, Lcom/android/camera/aiwatermark/handler/ChinaFestivalHandler;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "ChineseDate = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v1, 0x1

    invoke-direct {p0, v0, v1}, Lcom/android/camera/aiwatermark/handler/ChinaFestivalHandler;->findFestivalWatermark(Ljava/lang/String;I)Lcom/android/camera/aiwatermark/data/WatermarkItem;

    move-result-object v0

    if-eqz v0, :cond_1

    return-object v0

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/aiwatermark/handler/ChinaFestivalHandler;->getDate()Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x0

    invoke-direct {p0, v0, v1}, Lcom/android/camera/aiwatermark/handler/ChinaFestivalHandler;->findFestivalWatermark(Ljava/lang/String;I)Lcom/android/camera/aiwatermark/data/WatermarkItem;

    move-result-object p0

    if-eqz p0, :cond_2

    return-object p0

    :cond_2
    sget-object p0, Lcom/android/camera/aiwatermark/handler/ChinaFestivalHandler;->TAG:Ljava/lang/String;

    const-string v0, "Festival watermark null"

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p0, 0x0

    return-object p0
.end method
