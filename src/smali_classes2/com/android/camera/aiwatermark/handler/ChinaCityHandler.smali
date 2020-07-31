.class public Lcom/android/camera/aiwatermark/handler/ChinaCityHandler;
.super Lcom/android/camera/aiwatermark/handler/CityHandler;
.source "ChinaCityHandler.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "ChinaCityHandler"


# instance fields
.field private mContext:Landroid/content/Context;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(ZLandroid/content/Context;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/aiwatermark/handler/CityHandler;-><init>(Z)V

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/android/camera/aiwatermark/handler/ChinaCityHandler;->mContext:Landroid/content/Context;

    iput-object p2, p0, Lcom/android/camera/aiwatermark/handler/ChinaCityHandler;->mContext:Landroid/content/Context;

    return-void
.end method


# virtual methods
.method public getAddress(DD)Ljava/lang/String;
    .locals 6

    new-instance v0, Landroid/location/Geocoder;

    iget-object p0, p0, Lcom/android/camera/aiwatermark/handler/ChinaCityHandler;->mContext:Landroid/content/Context;

    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-direct {v0, p0, v1}, Landroid/location/Geocoder;-><init>(Landroid/content/Context;Ljava/util/Locale;)V

    const/4 v5, 0x1

    const/4 p0, 0x0

    move-wide v1, p1

    move-wide v3, p3

    :try_start_0
    invoke-virtual/range {v0 .. v5}, Landroid/location/Geocoder;->getFromLocation(DDI)Ljava/util/List;

    move-result-object p1

    sget-object p2, Lcom/android/camera/aiwatermark/handler/ChinaCityHandler;->TAG:Ljava/lang/String;

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string p4, "list="

    invoke-virtual {p3, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result p4

    invoke-virtual {p3, p4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p3

    invoke-static {p2, p3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-eqz p1, :cond_0

    invoke-interface {p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :goto_0
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result p2

    if-eqz p2, :cond_0

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object p2

    check-cast p2, Landroid/location/Address;

    sget-object p3, Lcom/android/camera/aiwatermark/handler/ChinaCityHandler;->TAG:Ljava/lang/String;

    new-instance p4, Ljava/lang/StringBuilder;

    invoke-direct {p4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "address="

    invoke-virtual {p4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Landroid/location/Address;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p4

    invoke-static {p3, p4}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p2}, Landroid/location/Address;->getLocality()Ljava/lang/String;

    move-result-object p0
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    sget-object p1, Lcom/android/camera/aiwatermark/handler/ChinaCityHandler;->TAG:Ljava/lang/String;

    const-string p2, "[getAddress] io exception"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    return-object p0
.end method

.method public getCityWatermarkList()Ljava/util/ArrayList;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/aiwatermark/data/WatermarkItem;",
            ">;"
        }
    .end annotation

    new-instance p0, Lcom/android/camera/aiwatermark/data/CityWatermark;

    invoke-direct {p0}, Lcom/android/camera/aiwatermark/data/CityWatermark;-><init>()V

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/CityWatermark;->getForAI()Ljava/util/ArrayList;

    move-result-object p0

    return-object p0
.end method
