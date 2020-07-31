.class public Lcom/android/camera/aiwatermark/handler/ChinaTimeHandler;
.super Lcom/android/camera/aiwatermark/handler/TimeHandler;
.source "ChinaTimeHandler.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "ChinaTimeHandler"

.field private static final TEMPLATE:Ljava/lang/String; = "yyyy-MM-dd HH:mm"

.field private static final WEEK:[Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 7

    const-string v0, "\u661f\u671f\u65e5"

    const-string v1, "\u661f\u671f\u4e00"

    const-string v2, "\u661f\u671f\u4e8c"

    const-string v3, "\u661f\u671f\u4e09"

    const-string v4, "\u661f\u671f\u56db"

    const-string v5, "\u661f\u671f\u4e94"

    const-string v6, "\u661f\u671f\u516d"

    filled-new-array/range {v0 .. v6}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/android/camera/aiwatermark/handler/ChinaTimeHandler;->WEEK:[Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Z)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/aiwatermark/handler/TimeHandler;-><init>(Z)V

    return-void
.end method


# virtual methods
.method protected findWatermark()Lcom/android/camera/aiwatermark/data/WatermarkItem;
    .locals 8

    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v1, "yyyy-MM-dd HH:mm"

    invoke-direct {v0, v1}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    new-instance v1, Ljava/lang/StringBuilder;

    new-instance v2, Ljava/util/Date;

    invoke-direct {v2}, Ljava/util/Date;-><init>()V

    invoke-virtual {v0, v2}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v0, " "

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v0, Lcom/android/camera/aiwatermark/handler/ChinaTimeHandler;->WEEK:[Ljava/lang/String;

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/handler/TimeHandler;->getDayOfWeek()I

    move-result p0

    aget-object p0, v0, p0

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    sget-object p0, Lcom/android/camera/aiwatermark/handler/ChinaTimeHandler;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "ChinaTime watermarkText = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    new-instance p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    const-string v3, "time"

    const/16 v4, 0x9

    const/4 v6, -0x1

    const/16 v7, 0x30

    move-object v2, p0

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;ILjava/lang/String;II)V

    return-object p0
.end method
