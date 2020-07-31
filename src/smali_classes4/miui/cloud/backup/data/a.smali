.class Lmiui/cloud/backup/data/a;
.super Ljava/lang/Object;
.source "DataPackage.java"

# interfaces
.implements Landroid/os/Parcelable$Creator;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiui/cloud/backup/data/DataPackage;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Landroid/os/Parcelable$Creator<",
        "Lmiui/cloud/backup/data/DataPackage;",
        ">;"
    }
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public bridge synthetic createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/a;->createFromParcel(Landroid/os/Parcel;)Lmiui/cloud/backup/data/DataPackage;

    move-result-object p0

    return-object p0
.end method

.method public createFromParcel(Landroid/os/Parcel;)Lmiui/cloud/backup/data/DataPackage;
    .locals 0

    invoke-virtual {p1}, Landroid/os/Parcel;->readBundle()Landroid/os/Bundle;

    move-result-object p0

    invoke-static {p0}, Lmiui/cloud/backup/data/DataPackage;->a(Landroid/os/Bundle;)Lmiui/cloud/backup/data/DataPackage;

    move-result-object p0

    return-object p0
.end method

.method public bridge synthetic newArray(I)[Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/a;->newArray(I)[Lmiui/cloud/backup/data/DataPackage;

    move-result-object p0

    return-object p0
.end method

.method public newArray(I)[Lmiui/cloud/backup/data/DataPackage;
    .locals 0

    new-array p0, p1, [Lmiui/cloud/backup/data/DataPackage;

    return-object p0
.end method
