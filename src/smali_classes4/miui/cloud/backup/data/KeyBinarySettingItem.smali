.class public Lmiui/cloud/backup/data/KeyBinarySettingItem;
.super Lmiui/cloud/backup/data/SettingItem;
.source "KeyBinarySettingItem.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lmiui/cloud/backup/data/SettingItem<",
        "[B>;"
    }
.end annotation


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lmiui/cloud/backup/data/KeyBinarySettingItem;",
            ">;"
        }
    .end annotation
.end field

.field public static final TYPE:Ljava/lang/String; = "binary"


# direct methods
.method static constructor <clinit>()V
    .locals 1

    new-instance v0, Lmiui/cloud/backup/data/b;

    invoke-direct {v0}, Lmiui/cloud/backup/data/b;-><init>()V

    sput-object v0, Lmiui/cloud/backup/data/KeyBinarySettingItem;->CREATOR:Landroid/os/Parcelable$Creator;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lmiui/cloud/backup/data/SettingItem;-><init>()V

    return-void
.end method


# virtual methods
.method protected Bm()Ljava/lang/Object;
    .locals 1

    invoke-virtual {p0}, Lmiui/cloud/backup/data/SettingItem;->getValue()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [B

    invoke-virtual {p0, v0}, Lmiui/cloud/backup/data/KeyBinarySettingItem;->k([B)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method protected bridge synthetic H(Ljava/lang/String;)Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/KeyBinarySettingItem;->H(Ljava/lang/String;)[B

    move-result-object p0

    return-object p0
.end method

.method protected H(Ljava/lang/String;)[B
    .locals 0

    const/4 p0, 0x2

    invoke-static {p1, p0}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object p0

    return-object p0
.end method

.method protected c(Lorg/json/JSONObject;)V
    .locals 1

    const-string v0, "value"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/KeyBinarySettingItem;->H(Ljava/lang/String;)[B

    move-result-object p1

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/SettingItem;->setValue(Ljava/lang/Object;)V

    return-void
.end method

.method protected getType()Ljava/lang/String;
    .locals 0

    const-string p0, "binary"

    return-object p0
.end method

.method protected k([B)Ljava/lang/String;
    .locals 0

    const/4 p0, 0x2

    invoke-static {p1, p0}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method protected bridge synthetic s(Ljava/lang/Object;)Ljava/lang/String;
    .locals 0

    check-cast p1, [B

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/KeyBinarySettingItem;->k([B)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method
