.class public Lcom/android/camera/fragment/aiwatermark/FragmentSpotsWatermark;
.super Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;
.source "FragmentSpotsWatermark.java"


# static fields
.field public static final FRAGMENT_INFO:I = 0x10001


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/aiwatermark/FragmentBaseWatermark;-><init>()V

    return-void
.end method


# virtual methods
.method public getWatermarkList()Ljava/util/List;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/android/camera/aiwatermark/data/WatermarkItem;",
            ">;"
        }
    .end annotation

    new-instance p0, Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;

    invoke-direct {p0}, Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;-><init>()V

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/ScenicSpotsWatermark;->getForManual()Ljava/util/ArrayList;

    move-result-object p0

    return-object p0
.end method
