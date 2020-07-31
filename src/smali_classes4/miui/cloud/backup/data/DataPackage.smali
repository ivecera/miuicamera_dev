.class public Lmiui/cloud/backup/data/DataPackage;
.super Ljava/lang/Object;
.source "DataPackage.java"

# interfaces
.implements Landroid/os/Parcelable;


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lmiui/cloud/backup/data/DataPackage;",
            ">;"
        }
    .end annotation
.end field

.field public static final KEY_VERSION:Ljava/lang/String; = "version"

.field public static final Yx:Ljava/lang/String; = "data_package"


# instance fields
.field private final Wx:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Lmiui/cloud/backup/data/SettingItem<",
            "*>;>;"
        }
    .end annotation
.end field

.field private final Xx:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Landroid/os/ParcelFileDescriptor;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 1

    new-instance v0, Lmiui/cloud/backup/data/a;

    invoke-direct {v0}, Lmiui/cloud/backup/data/a;-><init>()V

    sput-object v0, Lmiui/cloud/backup/data/DataPackage;->CREATOR:Landroid/os/Parcelable$Creator;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lmiui/cloud/backup/data/DataPackage;->Wx:Ljava/util/Map;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lmiui/cloud/backup/data/DataPackage;->Xx:Ljava/util/Map;

    return-void
.end method

.method static synthetic a(Landroid/os/Bundle;)Lmiui/cloud/backup/data/DataPackage;
    .locals 0

    invoke-static {p0}, Lmiui/cloud/backup/data/DataPackage;->e(Landroid/os/Bundle;)Lmiui/cloud/backup/data/DataPackage;

    move-result-object p0

    return-object p0
.end method

.method public static c(Landroid/os/Bundle;)Lmiui/cloud/backup/data/DataPackage;
    .locals 1

    invoke-virtual {p0}, Landroid/os/Bundle;->clone()Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/os/Bundle;

    const-class v0, Lmiui/cloud/backup/data/SettingItem;

    invoke-virtual {v0}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v0

    invoke-virtual {p0, v0}, Landroid/os/Bundle;->setClassLoader(Ljava/lang/ClassLoader;)V

    const-string v0, "data_package"

    invoke-virtual {p0, v0}, Landroid/os/Bundle;->getBundle(Ljava/lang/String;)Landroid/os/Bundle;

    move-result-object p0

    invoke-static {p0}, Lmiui/cloud/backup/data/DataPackage;->e(Landroid/os/Bundle;)Lmiui/cloud/backup/data/DataPackage;

    move-result-object p0

    return-object p0
.end method

.method public static d(Landroid/os/Bundle;)I
    .locals 1

    const-string v0, "version"

    invoke-virtual {p0, v0}, Landroid/os/Bundle;->getInt(Ljava/lang/String;)I

    move-result p0

    return p0
.end method

.method private static e(Landroid/os/Bundle;)Lmiui/cloud/backup/data/DataPackage;
    .locals 6

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return-object p0

    :cond_0
    const-class v0, Lmiui/cloud/backup/data/SettingItem;

    invoke-virtual {v0}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v0

    invoke-virtual {p0, v0}, Landroid/os/Bundle;->setClassLoader(Ljava/lang/ClassLoader;)V

    new-instance v0, Lmiui/cloud/backup/data/DataPackage;

    invoke-direct {v0}, Lmiui/cloud/backup/data/DataPackage;-><init>()V

    invoke-virtual {p0}, Landroid/os/Bundle;->keySet()Ljava/util/Set;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_1
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_3

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-virtual {p0, v2}, Landroid/os/Bundle;->getParcelable(Ljava/lang/String;)Landroid/os/Parcelable;

    move-result-object v3

    instance-of v4, v3, Lmiui/cloud/backup/data/SettingItem;

    if-eqz v4, :cond_2

    iget-object v4, v0, Lmiui/cloud/backup/data/DataPackage;->Wx:Ljava/util/Map;

    move-object v5, v3

    check-cast v5, Lmiui/cloud/backup/data/SettingItem;

    invoke-interface {v4, v2, v5}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_2
    instance-of v4, v3, Landroid/os/ParcelFileDescriptor;

    if-eqz v4, :cond_1

    iget-object v4, v0, Lmiui/cloud/backup/data/DataPackage;->Xx:Ljava/util/Map;

    check-cast v3, Landroid/os/ParcelFileDescriptor;

    invoke-interface {v4, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    :cond_3
    return-object v0
.end method

.method private po()Landroid/os/Bundle;
    .locals 4

    new-instance v0, Landroid/os/Bundle;

    invoke-direct {v0}, Landroid/os/Bundle;-><init>()V

    iget-object v1, p0, Lmiui/cloud/backup/data/DataPackage;->Wx:Ljava/util/Map;

    invoke-interface {v1}, Ljava/util/Map;->entrySet()Ljava/util/Set;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map$Entry;

    invoke-interface {v2}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    invoke-interface {v2}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lmiui/cloud/backup/data/SettingItem;

    invoke-virtual {v0, v3, v2}, Landroid/os/Bundle;->putParcelable(Ljava/lang/String;Landroid/os/Parcelable;)V

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lmiui/cloud/backup/data/DataPackage;->Xx:Ljava/util/Map;

    invoke-interface {p0}, Ljava/util/Map;->entrySet()Ljava/util/Set;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_1
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map$Entry;

    invoke-interface {v1}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-interface {v1}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/os/ParcelFileDescriptor;

    invoke-virtual {v0, v2, v1}, Landroid/os/Bundle;->putParcelable(Ljava/lang/String;Landroid/os/Parcelable;)V

    goto :goto_1

    :cond_1
    return-object v0
.end method


# virtual methods
.method public G(Ljava/lang/String;)Landroid/os/ParcelFileDescriptor;
    .locals 0

    iget-object p0, p0, Lmiui/cloud/backup/data/DataPackage;->Xx:Ljava/util/Map;

    invoke-interface {p0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/os/ParcelFileDescriptor;

    return-object p0
.end method

.method public a(Ljava/lang/String;Ljava/io/File;)V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/FileNotFoundException;
        }
    .end annotation

    iget-object p0, p0, Lmiui/cloud/backup/data/DataPackage;->Xx:Ljava/util/Map;

    const/high16 v0, 0x10000000

    invoke-static {p2, v0}, Landroid/os/ParcelFileDescriptor;->open(Ljava/io/File;I)Landroid/os/ParcelFileDescriptor;

    move-result-object p2

    invoke-interface {p0, p1, p2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method public a(Ljava/lang/String;Lmiui/cloud/backup/data/SettingItem;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Lmiui/cloud/backup/data/SettingItem<",
            "*>;)V"
        }
    .end annotation

    iget-object p0, p0, Lmiui/cloud/backup/data/DataPackage;->Wx:Ljava/util/Map;

    invoke-interface {p0, p1, p2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method public b(Landroid/os/Bundle;)V
    .locals 1

    invoke-direct {p0}, Lmiui/cloud/backup/data/DataPackage;->po()Landroid/os/Bundle;

    move-result-object p0

    const-string v0, "data_package"

    invoke-virtual {p1, v0, p0}, Landroid/os/Bundle;->putBundle(Ljava/lang/String;Landroid/os/Bundle;)V

    return-void
.end method

.method public b(Ljava/lang/String;Lorg/json/JSONObject;)V
    .locals 1

    new-instance v0, Lmiui/cloud/backup/data/KeyJsonSettingItem;

    invoke-direct {v0}, Lmiui/cloud/backup/data/KeyJsonSettingItem;-><init>()V

    iput-object p1, v0, Lmiui/cloud/backup/data/SettingItem;->key:Ljava/lang/String;

    invoke-virtual {v0, p2}, Lmiui/cloud/backup/data/SettingItem;->setValue(Ljava/lang/Object;)V

    iget-object p0, p0, Lmiui/cloud/backup/data/DataPackage;->Wx:Ljava/util/Map;

    invoke-interface {p0, p1, v0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method public describeContents()I
    .locals 0

    const/4 p0, 0x0

    return p0
.end method

.method public get(Ljava/lang/String;)Lmiui/cloud/backup/data/SettingItem;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Lmiui/cloud/backup/data/SettingItem<",
            "*>;"
        }
    .end annotation

    iget-object p0, p0, Lmiui/cloud/backup/data/DataPackage;->Wx:Ljava/util/Map;

    invoke-interface {p0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lmiui/cloud/backup/data/SettingItem;

    return-object p0
.end method

.method public o(Ljava/lang/String;Ljava/lang/String;)V
    .locals 1

    new-instance v0, Lmiui/cloud/backup/data/KeyStringSettingItem;

    invoke-direct {v0}, Lmiui/cloud/backup/data/KeyStringSettingItem;-><init>()V

    iput-object p1, v0, Lmiui/cloud/backup/data/SettingItem;->key:Ljava/lang/String;

    invoke-virtual {v0, p2}, Lmiui/cloud/backup/data/SettingItem;->setValue(Ljava/lang/Object;)V

    iget-object p0, p0, Lmiui/cloud/backup/data/DataPackage;->Wx:Ljava/util/Map;

    invoke-interface {p0, p1, v0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method public wm()Ljava/util/Map;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Lmiui/cloud/backup/data/SettingItem<",
            "*>;>;"
        }
    .end annotation

    iget-object p0, p0, Lmiui/cloud/backup/data/DataPackage;->Wx:Ljava/util/Map;

    return-object p0
.end method

.method public writeToParcel(Landroid/os/Parcel;I)V
    .locals 0

    invoke-direct {p0}, Lmiui/cloud/backup/data/DataPackage;->po()Landroid/os/Bundle;

    move-result-object p0

    invoke-virtual {p1, p0}, Landroid/os/Parcel;->writeBundle(Landroid/os/Bundle;)V

    return-void
.end method

.method public xm()Ljava/util/Map;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Landroid/os/ParcelFileDescriptor;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lmiui/cloud/backup/data/DataPackage;->Xx:Ljava/util/Map;

    return-object p0
.end method
