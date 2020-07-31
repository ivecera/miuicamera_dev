.class public Lmiui/cloud/backup/data/KeyStringSettingItem;
.super Lmiui/cloud/backup/data/SettingItem;
.source "KeyStringSettingItem.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lmiui/cloud/backup/data/SettingItem<",
        "Ljava/lang/String;",
        ">;"
    }
.end annotation


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lmiui/cloud/backup/data/KeyStringSettingItem;",
            ">;"
        }
    .end annotation
.end field

.field public static final TYPE:Ljava/lang/String; = "string"


# direct methods
.method static constructor <clinit>()V
    .locals 1

    new-instance v0, Lmiui/cloud/backup/data/d;

    invoke-direct {v0}, Lmiui/cloud/backup/data/d;-><init>()V

    sput-object v0, Lmiui/cloud/backup/data/KeyStringSettingItem;->CREATOR:Landroid/os/Parcelable$Creator;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lmiui/cloud/backup/data/SettingItem;-><init>()V

    return-void
.end method


# virtual methods
.method protected Bm()Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0}, Lmiui/cloud/backup/data/SettingItem;->getValue()Ljava/lang/Object;

    move-result-object p0

    return-object p0
.end method

.method protected bridge synthetic H(Ljava/lang/String;)Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/KeyStringSettingItem;->H(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method protected H(Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    return-object p1
.end method

.method protected I(Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    return-object p1
.end method

.method protected c(Lorg/json/JSONObject;)V
    .locals 1

    const-string v0, "value"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/SettingItem;->setValue(Ljava/lang/Object;)V

    return-void
.end method

.method protected getType()Ljava/lang/String;
    .locals 0

    const-string p0, "string"

    return-object p0
.end method

.method protected bridge synthetic s(Ljava/lang/Object;)Ljava/lang/String;
    .locals 0

    check-cast p1, Ljava/lang/String;

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/KeyStringSettingItem;->I(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method
