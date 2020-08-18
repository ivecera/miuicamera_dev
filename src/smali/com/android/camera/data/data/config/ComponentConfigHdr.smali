.class public Lcom/android/camera/data/data/config/ComponentConfigHdr;
.super Lcom/android/camera/data/data/ComponentData;
.source "ComponentConfigHdr.java"


# static fields
.field public static final HDR_UI_STATUS_AUTO:I = 0x2

.field public static final HDR_UI_STATUS_OFF:I = 0x0

.field public static final HDR_UI_STATUS_ON:I = 0x1

.field public static final HDR_VALUE_AUTO:Ljava/lang/String; = "auto"

.field public static final HDR_VALUE_LIVE:Ljava/lang/String; = "live"

.field public static final HDR_VALUE_NORMAL:Ljava/lang/String; = "normal"

.field public static final HDR_VALUE_OFF:Ljava/lang/String; = "off"

.field public static final HDR_VALUE_ON:Ljava/lang/String; = "on"


# instance fields
.field private mAutoSupported:Z

.field private mIsClosed:Z

.field private mSupportHdrCheckerWhenOn:Z


# direct methods
.method public constructor <init>(Lcom/android/camera/data/data/config/DataItemConfig;)V
    .locals 4

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/ComponentData;-><init>(Lcom/android/camera/data/data/DataItemBase;)V

    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    iput-object p1, p0, Lcom/android/camera/data/data/ComponentData;->mItems:Ljava/util/List;

    iget-object p1, p0, Lcom/android/camera/data/data/ComponentData;->mItems:Ljava/util/List;

    new-instance v0, Lcom/android/camera/data/data/ComponentDataItem;

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDROffRes()I

    move-result v1

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDROffRes()I

    move-result p0

    const v2, 0x7f0f02ce

    const-string v3, "off"

    invoke-direct {v0, v1, p0, v2, v3}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p1, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    return-void
.end method

.method private getConfigHDRAutoRes()I
    .locals 0

    const p0, 0x7f0801a5

    return p0
.end method

.method private getConfigHDRLiveRes()I
    .locals 0

    const p0, 0x7f0801a6

    return p0
.end method

.method private getConfigHDRNormalRes()I
    .locals 0

    const p0, 0x7f0801a7

    return p0
.end method

.method private getConfigHDROffRes()I
    .locals 0

    const p0, 0x7f0801a8

    return p0
.end method

.method public static getHdrUIStatus(Ljava/lang/String;)I
    .locals 1

    const-string v0, "on"

    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2

    const-string v0, "normal"

    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const-string v0, "auto"

    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_1

    const/4 p0, 0x2

    return p0

    :cond_1
    const/4 p0, 0x0

    return p0

    :cond_2
    :goto_0
    const/4 p0, 0x1

    return p0
.end method


# virtual methods
.method public clearClosed()V
    .locals 1

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mIsClosed:Z

    return-void
.end method

.method public getComponentValue(I)Ljava/lang/String;
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->isClosed()Z

    move-result v0

    const-string v1, "off"

    if-eqz v0, :cond_0

    return-object v1

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/data/data/ComponentData;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_1

    return-object v1

    :cond_1
    const/16 v0, 0xab

    if-ne v0, p1, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isBackCamera()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->c_9006_0x0007()Z

    move-result v0

    if-eqz v0, :cond_2

    const-string p0, "auto"

    return-object p0

    :cond_2
    invoke-super {p0, p1}, Lcom/android/camera/data/data/ComponentData;->getComponentValue(I)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public getDefaultValue(I)Ljava/lang/String;
    .locals 8

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->isClosed()Z

    move-result v0

    const-string v1, "off"

    if-eqz v0, :cond_0

    return-object v1

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/data/data/ComponentData;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_1

    return-object v1

    :cond_1
    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_2

    return-object v1

    :cond_2
    const/16 v0, 0xab

    const-string v2, "auto"

    if-ne v0, p1, :cond_3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isBackCamera()Z

    move-result p1

    if-eqz p1, :cond_3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->c_9006_0x0007()Z

    move-result p1

    if-eqz p1, :cond_3

    return-object v2

    :cond_3
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->h_d_v()Ljava/lang/String;

    move-result-object p1

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v3, 0x7f0f03c1

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v3

    if-eqz v3, :cond_4

    move-object p1, v0

    :cond_4
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_d

    const/4 v0, -0x1

    invoke-virtual {p1}, Ljava/lang/String;->hashCode()I

    move-result v3

    const/16 v4, 0xddf

    const-string v5, "on"

    const/4 v6, 0x2

    const/4 v7, 0x1

    if-eq v3, v4, :cond_7

    const v4, 0x1ad6f

    if-eq v3, v4, :cond_6

    const v4, 0x2dddaf

    if-eq v3, v4, :cond_5

    goto :goto_0

    :cond_5
    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_8

    const/4 v0, 0x0

    goto :goto_0

    :cond_6
    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_8

    move v0, v6

    goto :goto_0

    :cond_7
    invoke-virtual {p1, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_8

    move v0, v7

    :cond_8
    :goto_0
    if-eqz v0, :cond_b

    if-eq v0, v7, :cond_a

    if-eq v0, v6, :cond_9

    goto :goto_1

    :cond_9
    return-object v1

    :cond_a
    return-object v5

    :cond_b
    iget-boolean p0, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mAutoSupported:Z

    if-eqz p0, :cond_c

    move-object v1, v2

    :cond_c
    return-object v1

    :cond_d
    :goto_1
    iget-boolean p0, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mAutoSupported:Z

    if-eqz p0, :cond_e

    return-object v2

    :cond_e
    return-object v1
.end method

.method public getDisplayTitleString()I
    .locals 0

    const p0, 0x7f0f02cf

    return p0
.end method

.method public getItems()Ljava/util/List;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/android/camera/data/data/ComponentDataItem;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/data/data/ComponentData;->mItems:Ljava/util/List;

    return-object p0
.end method

.method public getKey(I)Ljava/lang/String;
    .locals 0

    const/16 p0, 0xa0

    if-eq p1, p0, :cond_2

    const/16 p0, 0xa2

    if-eq p1, p0, :cond_1

    const/16 p0, 0xa9

    if-eq p1, p0, :cond_1

    const/16 p0, 0xab

    if-eq p1, p0, :cond_0

    const/16 p0, 0xb4

    if-eq p1, p0, :cond_1

    const-string p0, "pref_camera_hdr_key"

    return-object p0

    :cond_0
    const-string p0, "pref_portrait_hdr_key"

    return-object p0

    :cond_1
    const-string p0, "pref_video_hdr_key"

    return-object p0

    :cond_2
    new-instance p0, Ljava/lang/RuntimeException;

    const-string p1, "unspecified hdr"

    invoke-direct {p0, p1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public getPersistValue(I)Ljava/lang/String;
    .locals 0

    invoke-super {p0, p1}, Lcom/android/camera/data/data/ComponentData;->getComponentValue(I)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public getValueSelectedDrawableIgnoreClose(I)I
    .locals 1

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    move-result-object p1

    const-string v0, "off"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDROffRes()I

    move-result p0

    return p0

    :cond_0
    const-string v0, "auto"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRAutoRes()I

    move-result p0

    return p0

    :cond_1
    const-string v0, "normal"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRNormalRes()I

    move-result p0

    return p0

    :cond_2
    const-string v0, "live"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRLiveRes()I

    move-result p0

    return p0

    :cond_3
    const-string v0, "on"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_4

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRNormalRes()I

    move-result p0

    return p0

    :cond_4
    const/4 p0, -0x1

    return p0
.end method

.method public getValueSelectedStringIdIgnoreClose(I)I
    .locals 1

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    move-result-object p0

    const-string p1, "off"

    invoke-virtual {p1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_0

    const p0, 0x7f0f0030

    return p0

    :cond_0
    const-string p1, "auto"

    invoke-virtual {p1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    const p0, 0x7f0f002d

    return p0

    :cond_1
    const-string p1, "normal"

    invoke-virtual {p1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    const v0, 0x7f0f0031

    if-eqz p1, :cond_2

    return v0

    :cond_2
    const-string p1, "live"

    invoke-virtual {p1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_3

    const p0, 0x7f0f002e

    return p0

    :cond_3
    const-string p1, "on"

    invoke-virtual {p1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_4

    return v0

    :cond_4
    const/4 p0, -0x1

    return p0
.end method

.method public isClosed()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mIsClosed:Z

    return p0
.end method

.method public isHdrOnWithChecker(Ljava/lang/String;)Z
    .locals 1

    const-string v0, "on"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    const-string v0, "normal"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    return p0

    :cond_1
    :goto_0
    iget-boolean p0, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mSupportHdrCheckerWhenOn:Z

    return p0
.end method

.method public isSupportAutoHdr()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mAutoSupported:Z

    return p0
.end method

.method public reInit(IILcom/android/camera2/CameraCapabilities;I)V
    .locals 6

    new-instance p2, Ljava/util/ArrayList;

    invoke-direct {p2}, Ljava/util/ArrayList;-><init>()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mAutoSupported:Z

    iput-boolean v0, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mSupportHdrCheckerWhenOn:Z

    invoke-virtual {p3}, Lcom/android/camera2/CameraCapabilities;->isSupportHdr()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    const/16 v0, 0xa5

    const-string v1, "auto"

    const v2, 0x7f0f02cb

    const-string v3, "off"

    const v4, 0x7f0f02ce

    const/4 v5, 0x1

    if-eq p1, v0, :cond_2

    const/16 v0, 0xa7

    if-eq p1, v0, :cond_8

    const/16 v0, 0xa9

    if-eq p1, v0, :cond_8

    const/16 v0, 0xab

    if-eq p1, v0, :cond_1

    const/16 v0, 0xb4

    if-eq p1, v0, :cond_3

    packed-switch p1, :pswitch_data_0

    goto/16 :goto_2

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->c_9006_0x0007()Z

    move-result p1

    if-eqz p1, :cond_8

    invoke-static {}, Lcom/android/camera/CameraSettings;->isBackCamera()Z

    move-result p1

    if-eqz p1, :cond_8

    new-instance p1, Lcom/android/camera/data/data/ComponentDataItem;

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDROffRes()I

    move-result p4

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDROffRes()I

    move-result v0

    invoke-direct {p1, p4, v0, v4, v3}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p2, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    invoke-virtual {p3}, Lcom/android/camera2/CameraCapabilities;->isSupportAutoHdr()Z

    move-result p1

    if-eqz p1, :cond_8

    iput-boolean v5, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mAutoSupported:Z

    new-instance p1, Lcom/android/camera/data/data/ComponentDataItem;

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRAutoRes()I

    move-result p3

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRAutoRes()I

    move-result p4

    invoke-direct {p1, p3, p4, v2, v1}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p2, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto/16 :goto_2

    :cond_2
    :pswitch_0
    invoke-virtual {p3}, Lcom/android/camera2/CameraCapabilities;->isSupportLightTripartite()Z

    move-result p1

    if-eqz p1, :cond_3

    if-eqz p4, :cond_3

    goto/16 :goto_2

    :cond_3
    new-instance p1, Lcom/android/camera/data/data/ComponentDataItem;

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDROffRes()I

    move-result p4

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDROffRes()I

    move-result v0

    invoke-direct {p1, p4, v0, v4, v3}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p2, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    invoke-virtual {p3}, Lcom/android/camera2/CameraCapabilities;->isSupportAutoHdr()Z

    move-result p1

    if-eqz p1, :cond_4

    iput-boolean v5, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mAutoSupported:Z

    new-instance p1, Lcom/android/camera/data/data/ComponentDataItem;

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRAutoRes()I

    move-result p4

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRAutoRes()I

    move-result v0

    invoke-direct {p1, p4, v0, v2, v1}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p2, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_4
    sget-boolean p1, Lcom/mi/config/b;->lg:Z

    const-string p4, "normal"

    if-nez p1, :cond_7

    invoke-static {}, Lcom/mi/config/b;->Ol()Z

    move-result p1

    if-nez p1, :cond_5

    goto :goto_0

    :cond_5
    sget-boolean p1, Lcom/mi/config/b;->og:Z

    if-nez p1, :cond_6

    new-instance p1, Lcom/android/camera/data/data/ComponentDataItem;

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRNormalRes()I

    move-result v0

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRNormalRes()I

    move-result v1

    const v2, 0x7f0f02cd

    invoke-direct {p1, v0, v1, v2, p4}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p2, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_6
    new-instance p1, Lcom/android/camera/data/data/ComponentDataItem;

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRLiveRes()I

    move-result p4

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRLiveRes()I

    move-result v0

    const v1, 0x7f0f02cc

    const-string v2, "live"

    invoke-direct {p1, p4, v0, v1, v2}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p2, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_1

    :cond_7
    :goto_0
    new-instance p1, Lcom/android/camera/data/data/ComponentDataItem;

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRNormalRes()I

    move-result v0

    invoke-direct {p0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getConfigHDRNormalRes()I

    move-result v1

    const v2, 0x7f0f03de

    invoke-direct {p1, v0, v1, v2, p4}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p2, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :goto_1
    invoke-virtual {p3}, Lcom/android/camera2/CameraCapabilities;->isSupportHdrCheckerStatus()Z

    move-result p1

    if-eqz p1, :cond_8

    iput-boolean v5, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mSupportHdrCheckerWhenOn:Z

    :cond_8
    :goto_2
    :pswitch_1
    invoke-static {p2}, Ljava/util/Collections;->unmodifiableList(Ljava/util/List;)Ljava/util/List;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/ComponentData;->mItems:Ljava/util/List;

    return-void

    nop

    :pswitch_data_0
    .packed-switch 0xa1
        :pswitch_1
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public setClosed(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/data/data/config/ComponentConfigHdr;->mIsClosed:Z

    return-void
.end method

.method public setComponentValue(ILjava/lang/String;)V
    .locals 1

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->setClosed(Z)V

    invoke-super {p0, p1, p2}, Lcom/android/camera/data/data/ComponentData;->setComponentValue(ILjava/lang/String;)V

    return-void
.end method
