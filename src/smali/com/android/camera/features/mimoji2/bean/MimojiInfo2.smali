.class public Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;
.super Ljava/lang/Object;
.source "MimojiInfo2.java"

# interfaces
.implements Ljava/lang/Comparable;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Ljava/lang/Comparable<",
        "Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;",
        ">;"
    }
.end annotation


# instance fields
.field public mAvatarTemplatePath:Ljava/lang/String;

.field public mConfigPath:Ljava/lang/String;

.field public mData:[B

.field public mDirectoryName:J

.field public mPackPath:Ljava/lang/String;

.field public mThumbnailUrl:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public compareTo(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;)I
    .locals 3

    iget-wide v0, p0, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mDirectoryName:J

    iget-wide p0, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mDirectoryName:J

    cmp-long v2, v0, p0

    if-lez v2, :cond_0

    const/4 p0, -0x1

    return p0

    :cond_0
    cmp-long p0, v0, p0

    if-gez p0, :cond_1

    const/4 p0, 0x1

    return p0

    :cond_1
    const/4 p0, 0x0

    return p0
.end method

.method public bridge synthetic compareTo(Ljava/lang/Object;)I
    .locals 0

    check-cast p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->compareTo(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;)I

    move-result p0

    return p0
.end method
