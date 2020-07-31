.class public Lmiui/cloud/backup/data/KeyJsonSettingItem;
.super Lmiui/cloud/backup/data/SettingItem;
.source "KeyJsonSettingItem.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lmiui/cloud/backup/data/SettingItem<",
        "Lorg/json/JSONObject;",
        ">;"
    }
.end annotation


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lmiui/cloud/backup/data/KeyJsonSettingItem;",
            ">;"
        }
    .end annotation
.end field

.field public static final TYPE:Ljava/lang/String; = "json"


# direct methods
.method static constructor <clinit>()V
    .locals 1

    new-instance v0, Lmiui/cloud/backup/data/c;

    invoke-direct {v0}, Lmiui/cloud/backup/data/c;-><init>()V

    sput-object v0, Lmiui/cloud/backup/data/KeyJsonSettingItem;->CREATOR:Landroid/os/Parcelable$Creator;

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

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/KeyJsonSettingItem;->H(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object p0

    return-object p0
.end method

.method protected H(Ljava/lang/String;)Lorg/json/JSONObject;
    .locals 1

    :try_start_0
    new-instance p0, Lorg/json/JSONObject;

    invoke-direct {p0, p1}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V
    :try_end_0
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p0

    :catch_0
    move-exception p0

    const-string p1, "SettingsBackup"

    const-string v0, "JSONException occorred when stringToValue()"

    invoke-static {p1, v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    const/4 p0, 0x0

    return-object p0
.end method

.method protected c(Lorg/json/JSONObject;)V
    .locals 1

    const-string v0, "value"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object p1

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/SettingItem;->setValue(Ljava/lang/Object;)V

    return-void
.end method

.method protected d(Lorg/json/JSONObject;)Ljava/lang/String;
    .locals 0

    invoke-virtual {p1}, Lorg/json/JSONObject;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method protected getType()Ljava/lang/String;
    .locals 0

    const-string p0, "json"

    return-object p0
.end method

.method protected bridge synthetic s(Ljava/lang/Object;)Ljava/lang/String;
    .locals 0

    check-cast p1, Lorg/json/JSONObject;

    invoke-virtual {p0, p1}, Lmiui/cloud/backup/data/KeyJsonSettingItem;->d(Lorg/json/JSONObject;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method
