.class public Lcom/mi/config/b;
.super Ljava/lang/Object;
.source "Device.java"


# static fields
.field public static final Au:Z

.field public static final Bu:Z

.field public static final Cu:Z

.field public static final Du:Z

.field public static final Eu:Z

.field public static final Fu:Z

.field public static final Gu:Z

.field public static final Hu:Z

.field public static final Iu:Z

.field public static final Ju:Z

.field public static final Ku:Z

.field public static final Lu:Z

.field public static final Mu:Z

.field public static final Nu:Z

.field public static final Ou:Z

.field public static final Pu:Z

.field public static final Qu:Z

.field public static final Ru:Z

.field public static final Su:Z

.field public static final Tu:Z

.field public static final Uu:Z

.field public static final Vu:Z

.field public static final Wu:Z

.field public static final Xu:Z

.field public static final Yu:Z

.field public static final Zu:Z

.field public static final _u:Z

.field public static final bv:Z

.field public static final cv:Z

.field public static final dv:Z

.field public static final ev:Z

.field public static final fv:Z

.field public static final gv:Z

.field public static final hv:Z

.field public static final jv:Z

.field public static final kv:Z

.field public static final lg:Z

.field public static final lv:Z

.field public static final mv:Z

.field public static final nv:Z

.field public static final og:Z

.field public static final ov:Z

.field public static final pv:Z

.field public static final qv:Z

.field public static final rv:Z

.field public static final sg:Z

.field private static final sv:I = 0x1

.field private static final tv:I = 0x4

.field private static final uv:I = 0x8

.field public static final vu:Ljava/lang/String;

.field private static vv:Ljava/util/ArrayList; = null
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field public static final wu:Ljava/lang/String; = "qcom"

.field private static final wv:[Ljava/lang/String;

.field public static final xu:Ljava/lang/String; = "mediatek"

.field private static final xv:Ljava/lang/String; = "ro.boot.hwversion"

.field private static final yu:I = 0x64

.field private static final yv:Ljava/util/concurrent/atomic/AtomicReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/concurrent/atomic/AtomicReference<",
            "Ljava/util/Optional<",
            "Ljava/lang/Boolean;",
            ">;>;"
        }
    .end annotation
.end field

.field public static final zu:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    sput-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    sput-object v0, Lcom/mi/config/b;->zu:Ljava/lang/String;

    sget-boolean v0, Ld/d/a;->kg:Z

    sput-boolean v0, Lcom/mi/config/b;->lg:Z

    sget-boolean v0, Ld/d/a;->og:Z

    sput-boolean v0, Lcom/mi/config/b;->og:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v1, "cancro"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x1

    const/4 v2, 0x0

    if-eqz v0, :cond_0

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v3, "MI 3"

    invoke-virtual {v0, v3}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    move v0, v1

    goto :goto_0

    :cond_0
    move v0, v2

    :goto_0
    sput-boolean v0, Lcom/mi/config/b;->Au:Z

    sget-boolean v0, Lcom/mi/config/b;->Au:Z

    sput-boolean v0, Lcom/mi/config/b;->Bu:Z

    sget-boolean v0, Ld/d/a;->yg:Z

    if-eqz v0, :cond_1

    sget-boolean v0, Ld/d/a;->vg:Z

    if-nez v0, :cond_1

    sget-boolean v0, Ld/d/a;->wg:Z

    if-nez v0, :cond_1

    move v0, v1

    goto :goto_1

    :cond_1
    move v0, v2

    :goto_1
    sput-boolean v0, Lcom/mi/config/b;->Cu:Z

    sget-boolean v0, Ld/d/a;->wg:Z

    sput-boolean v0, Lcom/mi/config/b;->Du:Z

    sget-boolean v0, Ld/d/a;->xg:Z

    sput-boolean v0, Lcom/mi/config/b;->Eu:Z

    sget-boolean v0, Ld/d/a;->vg:Z

    sput-boolean v0, Lcom/mi/config/b;->Fu:Z

    sget-boolean v0, Ld/d/a;->zg:Z

    sput-boolean v0, Lcom/mi/config/b;->Gu:Z

    sget-boolean v0, Ld/d/a;->mg:Z

    sput-boolean v0, Lcom/mi/config/b;->Hu:Z

    sget-boolean v0, Ld/d/a;->qg:Z

    sput-boolean v0, Lcom/mi/config/b;->Iu:Z

    sget-boolean v0, Ld/d/a;->rg:Z

    sput-boolean v0, Lcom/mi/config/b;->Ju:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "leo"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Ku:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "lithium"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Lu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "chiron"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Mu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "beryllium"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Nu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "violet"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Ou:Z

    const-string v0, "is_hongmi"

    invoke-static {v0, v2}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Pu:Z

    const-string v0, "is_xiaomi"

    invoke-static {v0, v2}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->sg:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "polaris"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Qu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "sirius"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Ru:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "dipper"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Su:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "andromeda"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Tu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "perseus"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Uu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "cepheus"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Vu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "grus"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Wu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "begonia"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Xu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "phoenix"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_3

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "phoenixin"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    goto :goto_2

    :cond_2
    move v0, v2

    goto :goto_3

    :cond_3
    :goto_2
    move v0, v1

    :goto_3
    sput-boolean v0, Lcom/mi/config/b;->Yu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "begoniain"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->Zu:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "ginkgo"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->_u:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "pyxis"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->bv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "vela"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->cv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "laurus"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->dv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "laurel_sprout"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->ev:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "tucana"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->fv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    const-string v0, "03"

    const-string v3, "persist.camera.rearMain.vendorID"

    invoke-static {v3, v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    move v0, v1

    goto :goto_4

    :cond_4
    move v0, v2

    :goto_4
    sput-boolean v0, Lcom/mi/config/b;->gv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "umi"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->hv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "cmi"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->jv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "lmi"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_6

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "lmiin"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_5

    goto :goto_5

    :cond_5
    move v0, v2

    goto :goto_6

    :cond_6
    :goto_5
    move v0, v1

    :goto_6
    sput-boolean v0, Lcom/mi/config/b;->kv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "lmipro"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_8

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "lmiinpro"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_7

    goto :goto_7

    :cond_7
    move v0, v2

    goto :goto_8

    :cond_8
    :goto_7
    move v0, v1

    :goto_8
    sput-boolean v0, Lcom/mi/config/b;->lv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "draco"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->mv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "picasso"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_a

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v3, "picassoin"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_9

    goto :goto_9

    :cond_9
    move v1, v2

    :cond_a
    :goto_9
    sput-boolean v1, Lcom/mi/config/b;->nv:Z

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v1, "crux"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/mi/config/b;->ov:Z

    sget-boolean v0, Ld/d/a;->bh:Z

    sput-boolean v0, Lcom/mi/config/b;->pv:Z

    sget-boolean v0, Ld/d/a;->hh:Z

    sput-boolean v0, Lcom/mi/config/b;->qv:Z

    sget-boolean v0, Ld/d/a;->Xg:Z

    sput-boolean v0, Lcom/mi/config/b;->rv:Z

    const-string v0, "KR"

    const-string v1, "JP"

    filled-new-array {v0, v1}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/mi/config/b;->wv:[Ljava/lang/String;

    new-instance v0, Ljava/util/concurrent/atomic/AtomicReference;

    invoke-static {}, Ljava/util/Optional;->empty()Ljava/util/Optional;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/util/concurrent/atomic/AtomicReference;-><init>(Ljava/lang/Object;)V

    sput-object v0, Lcom/mi/config/b;->yv:Ljava/util/concurrent/atomic/AtomicReference;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static Al()Z
    .locals 2

    const-string v0, "support_3d_face_beauty"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Bl()Z
    .locals 2

    const-string v0, "support_camera_role"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Cl()Z
    .locals 2

    const-string v0, "support_camera_dynamic_light_spot"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Dl()Z
    .locals 2

    const-string v0, "support_front_beauty_mfnr"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static El()Z
    .locals 2

    const-string v0, "support_front_flash"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Fl()Z
    .locals 2

    const-string v0, "support_camera_gradienter"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static G(Z)Z
    .locals 3

    const-string v0, "ro.miui.customized.region"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "fr_sfr"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/4 v2, 0x0

    if-nez v1, :cond_2

    const-string v1, "fr_orange"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    goto :goto_0

    :cond_0
    const-string v1, "es_vodafone"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/mi/config/b;->getCountry()Ljava/lang/String;

    move-result-object v0

    const-string v1, "NL"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    return v2

    :cond_1
    return p0

    :cond_2
    :goto_0
    return v2
.end method

.method public static Gl()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public static Hl()Z
    .locals 2

    const-string v0, "support_mi_face_beauty"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Il()Z
    .locals 1

    invoke-static {}, Lcom/mi/config/b;->Al()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {}, Lcom/mi/config/b;->Hl()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static Jl()Z
    .locals 2

    const-string v0, "support_psensor_pocket_mode"

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Kl()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->Wc()Z

    move-result v0

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Pu:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static Ll()Z
    .locals 2

    const-string v0, "support_screen_light"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Ml()Z
    .locals 2

    const/4 v0, 0x0

    const-string v1, "camera_supported_asd"

    invoke-static {v1, v0}, Lcom/mi/config/d;->getInteger(Ljava/lang/String;I)I

    move-result v1

    and-int/lit8 v1, v1, 0xd

    if-eqz v1, :cond_0

    const/4 v0, 0x1

    :cond_0
    return v0
.end method

.method public static Nl()Z
    .locals 2

    sget-boolean v0, Ld/d/a;->lh:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    const-string v0, "support_camera_age_detection"

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    :cond_0
    return v1
.end method

.method public static Ol()Z
    .locals 2

    const-string v0, "support_camera_aohdr"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Pl()Z
    .locals 3

    const/4 v0, 0x0

    const-string v1, "camera_supported_asd"

    invoke-static {v1, v0}, Lcom/mi/config/d;->getInteger(Ljava/lang/String;I)I

    move-result v1

    const/4 v2, 0x1

    and-int/2addr v1, v2

    if-eqz v1, :cond_0

    move v0, v2

    :cond_0
    return v0
.end method

.method public static Qk()Z
    .locals 2

    sget-boolean v0, Lcom/mi/config/b;->qv:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    const-string v0, "support_camera_boost_brightness"

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    :cond_0
    return v1
.end method

.method public static Ql()Z
    .locals 2

    const/4 v0, 0x0

    const-string v1, "camera_supported_asd"

    invoke-static {v1, v0}, Lcom/mi/config/d;->getInteger(Ljava/lang/String;I)I

    move-result v1

    and-int/lit8 v1, v1, 0x4

    if-eqz v1, :cond_0

    const/4 v0, 0x1

    :cond_0
    return v0
.end method

.method public static Rk()Z
    .locals 2

    const-string v0, "enable_algorithm_in_file_suffix"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Rl()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method static Sk()Ljava/lang/String;
    .locals 3

    const/4 v0, 0x1

    const-string v1, "ro.boot.camera.config"

    invoke-static {v1, v0}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v1

    if-eqz v1, :cond_1

    const-string v2, ""

    if-eq v1, v0, :cond_0

    :cond_0
    return-object v2

    :cond_1
    const-string v0, "_pro"

    return-object v0
.end method

.method public static Sl()Z
    .locals 2

    const-string v0, "support_chroma_flash"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Te()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->Te()Z

    move-result v0

    return v0
.end method

.method public static Tk()Ljava/util/ArrayList;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    sget-object v0, Lcom/mi/config/b;->vv:Ljava/util/ArrayList;

    if-nez v0, :cond_0

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    sput-object v0, Lcom/mi/config/b;->vv:Ljava/util/ArrayList;

    const-string v0, "fp_nav_event_name_list"

    invoke-static {v0}, Lcom/mi/config/d;->getStringArray(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_0

    sget-object v1, Lcom/mi/config/b;->vv:Ljava/util/ArrayList;

    invoke-static {v1, v0}, Ljava/util/Collections;->addAll(Ljava/util/Collection;[Ljava/lang/Object;)Z

    :cond_0
    sget-object v0, Lcom/mi/config/b;->vv:Ljava/util/ArrayList;

    return-object v0
.end method

.method public static Tl()Z
    .locals 2

    const-string v0, "support_edge_handgrip"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Uk()Ljava/lang/String;
    .locals 3

    invoke-static {}, Lcom/mi/config/b;->cl()Z

    move-result v0

    if-eqz v0, :cond_0

    const-string v0, "_l"

    return-object v0

    :cond_0
    invoke-static {}, Lcom/mi/config/b;->ol()Z

    move-result v0

    if-eqz v0, :cond_1

    const-string v0, "_in"

    return-object v0

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    const/4 v1, 0x0

    const-string v2, "c_0x18"

    invoke-virtual {v0, v2, v1}, Lcom/android/camera/data/data/DataItemBase;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    const-string v1, ""

    if-nez v0, :cond_2

    return-object v1

    :cond_2
    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v2, "BROWN EDITION"

    invoke-virtual {v0, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_b

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v2, "Explorer"

    invoke-virtual {v0, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_3

    goto :goto_1

    :cond_3
    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v2, "ROY"

    invoke-virtual {v0, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_4

    const-string v0, "_b"

    return-object v0

    :cond_4
    invoke-static {}, Lcom/mi/config/b;->dl()Z

    move-result v0

    if-eqz v0, :cond_5

    const-string v0, "_s"

    return-object v0

    :cond_5
    invoke-static {}, Lcom/mi/config/b;->jl()Z

    move-result v0

    const-string v2, "_global"

    if-nez v0, :cond_a

    invoke-static {}, Lcom/mi/config/b;->il()Z

    move-result v0

    if-nez v0, :cond_a

    invoke-static {}, Lcom/mi/config/b;->kl()Z

    move-result v0

    if-eqz v0, :cond_6

    goto :goto_0

    :cond_6
    invoke-static {}, Lcom/mi/config/b;->ul()Z

    move-result v0

    if-eqz v0, :cond_7

    const-string v0, "_premium"

    return-object v0

    :cond_7
    invoke-static {}, Lcom/mi/config/b;->ll()Z

    move-result v0

    if-eqz v0, :cond_9

    invoke-static {}, Lcom/mi/config/b;->vl()Z

    move-result v0

    if-eqz v0, :cond_8

    const-string v0, "_global_pro"

    return-object v0

    :cond_8
    return-object v2

    :cond_9
    return-object v1

    :cond_a
    :goto_0
    return-object v2

    :cond_b
    :goto_1
    const-string v0, "_a"

    return-object v0
.end method

.method public static Ul()Z
    .locals 2

    const-string v0, "support_camera_face_info_water_mark"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Vk()I
    .locals 2

    sget v0, Lcom/android/camera/AutoLockManager;->HIBERNATION_TIMEOUT:I

    const-string v1, "camera_hibernation_timeout_in_minutes"

    invoke-static {v1, v0}, Lcom/mi/config/d;->getInteger(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public static Vl()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method private static W(Ljava/lang/String;)Z
    .locals 5

    sget-object v0, Lcom/mi/config/b;->wv:[Ljava/lang/String;

    array-length v1, v0

    const/4 v2, 0x0

    move v3, v2

    :goto_0
    if-ge v3, v1, :cond_1

    aget-object v4, v0, v3

    invoke-static {p0, v4}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v4

    if-eqz v4, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_1
    return v2
.end method

.method public static Wk()Z
    .locals 2

    const-string v0, "sensor_has_latency"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Wl()Z
    .locals 2

    const-string v0, "support_camera_record_location"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Xk()Z
    .locals 2

    sget v0, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v0, v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v1, v1

    div-float/2addr v0, v1

    const/high16 v1, 0x40000000    # 2.0f

    cmpl-float v0, v0, v1

    const/4 v1, 0x0

    if-ltz v0, :cond_0

    const-string v0, "is_18x9_ratio_screen"

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    :cond_0
    return v1
.end method

.method public static Xl()Z
    .locals 2

    const-string v0, "support_camera_burst_shoot"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static Yk()Z
    .locals 2

    invoke-static {}, Lcom/mi/config/b;->lo()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->gd()Z

    move-result v0

    if-nez v0, :cond_1

    return v1

    :cond_1
    invoke-static {}, Lcom/mi/config/b;->Tk()Ljava/util/ArrayList;

    move-result-object v0

    if-eqz v0, :cond_2

    invoke-static {}, Lcom/mi/config/b;->Tk()Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_2

    const/4 v0, 0x1

    return v0

    :cond_2
    return v1
.end method

.method public static Yl()Z
    .locals 2

    sget-boolean v0, Ld/d/a;->lh:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    const-string v0, "support_camera_magic_mirror"

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    :cond_0
    return v1
.end method

.method public static Zk()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->ze()Z

    move-result v0

    if-nez v0, :cond_1

    sget-boolean v0, Lcom/mi/config/b;->Yu:Z

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static Zl()Z
    .locals 2

    const-string v0, "support_camera_manual_function"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static _k()Z
    .locals 1

    sget-boolean v0, Ld/d/a;->lh:Z

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/mi/config/b;->getCountry()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mi/config/b;->W(Ljava/lang/String;)Z

    move-result v0

    return v0

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public static _l()Z
    .locals 2

    invoke-static {}, Lcom/mi/config/b;->cl()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    :cond_0
    const-string v0, "support_camera_movie_solid"

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static al()Z
    .locals 2

    const-string v0, "is_camera_app_water_mark"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static am()Z
    .locals 1

    invoke-static {}, Lcom/mi/config/b;->_k()Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    return v0
.end method

.method public static bl()Z
    .locals 2

    const-string v0, "ro.boot.hwversion"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    sget-boolean v1, Lcom/mi/config/b;->ov:Z

    if-eqz v1, :cond_1

    const-string v1, "7.1.7"

    invoke-static {v0, v1}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    const-string v1, "7.2.0"

    invoke-static {v0, v1}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_1

    :cond_0
    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static bm()Z
    .locals 2

    const-string v0, "support_camera_new_style_time_water_mark"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static cl()Z
    .locals 3

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v1, "onc"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    const-string v0, "ro.boot.hwversion"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_0

    const/16 v2, 0x32

    invoke-virtual {v0, v1}, Ljava/lang/String;->charAt(I)C

    move-result v0

    if-ne v2, v0, :cond_0

    const/4 v1, 0x1

    :cond_0
    return v1
.end method

.method public static cm()Z
    .locals 2

    const-string v0, "support_object_track"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static dl()Z
    .locals 2

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v1, "lavender"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const-string v0, "ro.boot.hwc"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "India_48_5"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static dm()Z
    .locals 2

    const-string v0, "support_camera_peaking_mf"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static el()Z
    .locals 1

    sget-boolean v0, Lcom/mi/config/b;->Lu:Z

    if-nez v0, :cond_1

    sget-boolean v0, Lcom/mi/config/b;->Mu:Z

    if-nez v0, :cond_1

    sget-boolean v0, Lcom/mi/config/b;->Qu:Z

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static em()Z
    .locals 2

    sget-boolean v0, Ld/d/a;->lh:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    const-string v0, "support_camera_quick_snap"

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    :cond_0
    return v1
.end method

.method public static fl()Z
    .locals 1

    invoke-static {}, Lcom/mi/config/b;->hl()Z

    move-result v0

    if-nez v0, :cond_1

    sget-boolean v0, Lcom/mi/config/b;->bv:Z

    if-nez v0, :cond_1

    sget-boolean v0, Lcom/mi/config/b;->Wu:Z

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->ze()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static fm()Z
    .locals 2

    const-string v0, "support_dual_sd_card"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static getBurstShootCount()I
    .locals 2

    const-string v0, "burst_shoot_count"

    const/16 v1, 0x64

    invoke-static {v0, v1}, Lcom/mi/config/d;->getInteger(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public static getCountry()Ljava/lang/String;
    .locals 2

    sget-object v0, Lcom/android/camera/Util;->sRegion:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    return-object v0

    :cond_0
    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Locale;->getCountry()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static gl()Z
    .locals 2

    sget-boolean v0, Lcom/mi/config/b;->Fu:Z

    const/4 v1, 0x1

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Hu:Z

    if-nez v0, :cond_0

    sget-boolean v0, Ld/d/a;->Jg:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Au:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Gu:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Cu:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Du:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Eu:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->lg:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->og:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Bu:Z

    if-nez v0, :cond_0

    const-string v0, "is_front_video_quality_1080p"

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_0
    return v1
.end method

.method public static gm()Z
    .locals 2

    const-string v0, "support_camera_shader_effect"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static hl()Z
    .locals 1

    sget-boolean v0, Lcom/mi/config/b;->Uu:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Ld/d/a;->lh:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static hm()Z
    .locals 2

    const-string v0, "support_camera_skin_beauty"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static il()Z
    .locals 2

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v1, "davinci"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-boolean v0, Ld/d/a;->lh:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static im()Z
    .locals 2

    const-string v0, "is_support_stereo"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static isMTKPlatform()Z
    .locals 4

    sget-object v0, Lcom/mi/config/b;->yv:Ljava/util/concurrent/atomic/AtomicReference;

    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Optional;

    invoke-virtual {v0}, Ljava/util/Optional;->isPresent()Z

    move-result v0

    if-nez v0, :cond_1

    sget-object v0, Lcom/mi/config/b;->yv:Ljava/util/concurrent/atomic/AtomicReference;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lcom/mi/config/b;->yv:Ljava/util/concurrent/atomic/AtomicReference;

    invoke-virtual {v1}, Ljava/util/concurrent/atomic/AtomicReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Optional;

    invoke-virtual {v1}, Ljava/util/Optional;->isPresent()Z

    move-result v1

    if-nez v1, :cond_0

    const-string v1, "vendor"

    invoke-static {v1}, Lcom/mi/config/d;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    sget-object v2, Lcom/mi/config/b;->yv:Ljava/util/concurrent/atomic/AtomicReference;

    const-string v3, "mediatek"

    invoke-virtual {v3, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    invoke-static {v1}, Ljava/util/Optional;->of(Ljava/lang/Object;)Ljava/util/Optional;

    move-result-object v1

    invoke-virtual {v2, v1}, Ljava/util/concurrent/atomic/AtomicReference;->set(Ljava/lang/Object;)V

    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    :cond_1
    :goto_0
    sget-object v0, Lcom/mi/config/b;->yv:Ljava/util/concurrent/atomic/AtomicReference;

    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Optional;

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Boolean;

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    return v0
.end method

.method public static isSupportSuperResolution()Z
    .locals 2

    const-string v0, "support_super_resolution"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static isSupportedOpticalZoom()Z
    .locals 2

    const-string v0, "is_support_optical_zoom"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static jl()Z
    .locals 2

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v1, "raphael"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-boolean v0, Ld/d/a;->lh:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static jm()Z
    .locals 2

    const-string v0, "support_camera_tilt_shift"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static kl()Z
    .locals 1

    sget-boolean v0, Lcom/mi/config/b;->bv:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Ld/d/a;->lh:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static km()Z
    .locals 2

    const-string v0, "support_camera_water_mark"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method private static ko()Z
    .locals 2

    const-string v0, "ro.hardware.fp.fod"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static ll()Z
    .locals 1

    sget-boolean v0, Lcom/mi/config/b;->fv:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Ld/d/a;->lh:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static lm()Z
    .locals 2

    const-string v0, "support_camera_torch_capture"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method private static lo()Z
    .locals 2

    const/4 v0, 0x0

    const-string v1, "front_fingerprint_sensor"

    invoke-static {v1, v0}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v1

    if-nez v1, :cond_0

    invoke-static {}, Lcom/mi/config/b;->ko()Z

    move-result v1

    if-eqz v1, :cond_1

    :cond_0
    const/4 v0, 0x1

    :cond_1
    return v0
.end method

.method public static ml()Z
    .locals 2

    const-string v0, "is_hal_does_caf_when_flash_on"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static mm()Z
    .locals 2

    const-string v0, "support_camera_video_pause"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static nl()Z
    .locals 2

    const-string v0, "is_camera_hold_blur_background"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static nm()Z
    .locals 2

    const-string v0, "support_camera_4k_quality"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static ol()Z
    .locals 2

    sget-boolean v0, Lcom/mi/config/b;->Nu:Z

    if-eqz v0, :cond_0

    const-string v0, "ro.boot.hwc"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "India"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static om()Z
    .locals 2

    const-string v0, "is_surface_size_limit"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static pd()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->pd()Z

    move-result v0

    return v0
.end method

.method public static pl()Z
    .locals 2

    const-string v0, "is_camera_isp_rotated"

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static pm()Z
    .locals 1

    sget-boolean v0, Lcom/mi/config/b;->sg:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Pu:Z

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static ql()Z
    .locals 1

    sget-boolean v0, Lcom/mi/config/b;->kv:Z

    if-nez v0, :cond_1

    sget-boolean v0, Lcom/mi/config/b;->lv:Z

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static qm()Z
    .locals 2

    sget-boolean v0, Lcom/mi/config/b;->Fu:Z

    const/4 v1, 0x1

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Hu:Z

    if-nez v0, :cond_0

    sget-boolean v0, Ld/d/a;->Jg:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Au:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Gu:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Cu:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Du:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Eu:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->lg:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->og:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Bu:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->Iu:Z

    if-nez v0, :cond_0

    const-string v0, "is_video_snapshot_size_limit"

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_0
    return v1
.end method

.method public static rl()Z
    .locals 2

    const-string v0, "is_camera_lower_qrscan_frequency"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static rm()Z
    .locals 2

    const-string v0, "support_picture_watermark"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static sl()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public static sm()Z
    .locals 2

    const-string v0, "support_realtime_manual_exposure_time"

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static tl()Z
    .locals 2

    const-string v0, "is_pad"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static ul()Z
    .locals 2

    sget-object v0, Lcom/mi/config/b;->vu:Ljava/lang/String;

    const-string v1, "raphael"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v1, "Premium Edition"

    invoke-virtual {v0, v1}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static vl()Z
    .locals 1

    sget-boolean v0, Lcom/mi/config/b;->fv:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Lcom/mi/config/b;->gv:Z

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static wl()Z
    .locals 2

    const-string v0, "vendor"

    invoke-static {v0}, Lcom/mi/config/d;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "qcom"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public static xl()Z
    .locals 2

    const-string v0, "is_rgb888_egl_prefer"

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static yl()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public static zl()Z
    .locals 2

    const-string v0, "is_camera_preview_with_subthread_looper"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mi/config/d;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method
