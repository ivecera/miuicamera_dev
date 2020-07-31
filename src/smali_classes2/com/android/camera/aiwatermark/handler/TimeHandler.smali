.class public Lcom/android/camera/aiwatermark/handler/TimeHandler;
.super Lcom/android/camera/aiwatermark/handler/AbstractHandler;
.source "TimeHandler.java"


# direct methods
.method public constructor <init>(Z)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/aiwatermark/handler/AbstractHandler;-><init>(Z)V

    return-void
.end method


# virtual methods
.method protected findWatermark()Lcom/android/camera/aiwatermark/data/WatermarkItem;
    .locals 7

    new-instance p0, Ljava/text/SimpleDateFormat;

    const-string v0, "yyyy-MM-dd HH:mm:ss"

    invoke-direct {p0, v0}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    new-instance v0, Ljava/util/Date;

    invoke-direct {v0}, Ljava/util/Date;-><init>()V

    invoke-virtual {p0, v0}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v4

    new-instance p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const-string v2, "time"

    const/16 v3, 0x9

    const/16 v5, 0x30

    const/4 v6, 0x1

    move-object v1, p0

    invoke-direct/range {v1 .. v6}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;ILjava/lang/String;II)V

    return-object p0
.end method

.method public getDayOfWeek()I
    .locals 1

    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object p0

    const/4 v0, 0x7

    invoke-virtual {p0, v0}, Ljava/util/Calendar;->get(I)I

    move-result p0

    add-int/lit8 p0, p0, -0x1

    return p0
.end method
