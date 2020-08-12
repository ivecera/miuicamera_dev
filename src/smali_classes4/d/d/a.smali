.class public Ld/d/a;
.super Landroid/os/Build;
.source "Build.java"


# static fields
.field public static final Ag:Z

.field public static final Bg:Z

.field public static final Cg:Z

.field public static final Dg:Z

.field public static final Eg:Z

.field public static final Fg:Z

.field public static final Gg:Z

.field public static final Hg:Z

.field public static final IS_DEBUGGABLE:Z

.field public static final Ig:Z

.field public static final Jg:Z

.field public static final Kg:Z

.field public static final Lg:Z

.field public static final Mg:Z

.field public static final Ng:Z

.field public static final Og:Z

.field public static final Pg:Z

.field public static final Qg:Z

.field public static final Rg:Z

.field public static final Sg:Z

.field public static final Tg:Z

.field public static final Ug:Z

.field public static final Vg:Z

.field public static final Wg:Z

.field public static final Xg:Z

.field public static final Yg:Z

.field public static final Zg:Z

.field public static final _g:Z

.field public static final bh:Z

.field public static final dh:Z

.field public static final eh:Z

.field public static final fh:Z

.field public static final gh:Z

.field private static final hg:Ljava/lang/String; = "\\d+.\\d+.\\d+(-internal)?"

.field public static final hh:Z

.field private static final ig:Ljava/lang/String; = "([A-Z]{3}|[A-Z]{7})\\d+.\\d+"

.field public static final ih:Z

.field public static final jg:Z

.field public static final jh:Z

.field public static final kg:Z

.field public static final kh:Z

.field public static final lg:Z

.field public static final lh:Z

.field public static final mg:Z

.field public static final mh:Z

.field public static final ng:Z

.field public static final nh:Z

.field public static final og:Z

.field public static final oh:Ljava/lang/String; = "persist.sys.user_mode"

.field public static final pg:Z

.field public static final ph:I = 0x0

.field public static final qg:Z

.field public static final qh:I = 0x1

.field public static final rg:Z

.field public static final rh:Ljava/lang/String;

.field public static final sg:Z

.field public static final sh:Z

.field public static final tg:Z

.field public static final th:Z

.field public static final ug:Z

.field public static final vg:Z

.field public static final wg:Z

.field public static final xg:Z

.field public static final yg:Z

.field public static final zg:Z


# direct methods
.method static constructor <clinit>()V
    .locals 10

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v1, "mione"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-nez v0, :cond_1

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "mione_plus"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    move v0, v1

    goto :goto_1

    :cond_1
    :goto_0
    move v0, v2

    :goto_1
    sput-boolean v0, Ld/d/a;->jg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "aries"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_3

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "taurus"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_3

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "taurus_td"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    goto :goto_2

    :cond_2
    move v0, v1

    goto :goto_3

    :cond_3
    :goto_2
    move v0, v2

    :goto_3
    sput-boolean v0, Ld/d/a;->kg:Z

    sget-boolean v0, Ld/d/a;->kg:Z

    sput-boolean v0, Ld/d/a;->lg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "lte26007"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->mg:Z

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v3, "MI 1S"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_5

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v3, "MI 1SC"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    goto :goto_4

    :cond_4
    move v0, v1

    goto :goto_5

    :cond_5
    :goto_4
    move v0, v2

    :goto_5
    sput-boolean v0, Ld/d/a;->ng:Z

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v3, "MI 2A"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_7

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v3, "MI 2A TD"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_6

    goto :goto_6

    :cond_6
    move v0, v1

    goto :goto_7

    :cond_7
    :goto_6
    move v0, v2

    :goto_7
    sput-boolean v0, Ld/d/a;->og:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "cancro"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_8

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v4, "MI 3"

    invoke-virtual {v0, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_8

    move v0, v2

    goto :goto_8

    :cond_8
    move v0, v1

    :goto_8
    sput-boolean v0, Ld/d/a;->pg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_9

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v3, "MI 4"

    invoke-virtual {v0, v3}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_9

    move v0, v2

    goto :goto_9

    :cond_9
    move v0, v1

    :goto_9
    sput-boolean v0, Ld/d/a;->qg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "virgo"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->rg:Z

    sget-boolean v0, Ld/d/a;->jg:Z

    if-nez v0, :cond_b

    sget-boolean v0, Ld/d/a;->pg:Z

    if-nez v0, :cond_b

    sget-boolean v0, Ld/d/a;->qg:Z

    if-nez v0, :cond_b

    sget-boolean v0, Ld/d/a;->rg:Z

    if-eqz v0, :cond_a

    goto :goto_a

    :cond_a
    move v0, v1

    goto :goto_b

    :cond_b
    :goto_a
    move v0, v2

    :goto_b
    sput-boolean v0, Ld/d/a;->sg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "mocha"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->tg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "flo"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->ug:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "armani"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->vg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "HM2014011"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_d

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "HM2014012"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_c

    goto :goto_c

    :cond_c
    move v0, v1

    goto :goto_d

    :cond_d
    :goto_c
    move v0, v2

    :goto_d
    sput-boolean v0, Ld/d/a;->wg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "HM2014501"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->xg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v3, "HM2013022"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_f

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "HM2013023"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_f

    sget-boolean v0, Ld/d/a;->vg:Z

    if-nez v0, :cond_f

    sget-boolean v0, Ld/d/a;->wg:Z

    if-eqz v0, :cond_e

    goto :goto_e

    :cond_e
    move v0, v1

    goto :goto_f

    :cond_f
    :goto_e
    move v0, v2

    :goto_f
    sput-boolean v0, Ld/d/a;->yg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "lcsh92_wet_jb9"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_11

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "lcsh92_wet_tdd"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_10

    goto :goto_10

    :cond_10
    move v0, v1

    goto :goto_11

    :cond_11
    :goto_10
    move v0, v2

    :goto_11
    sput-boolean v0, Ld/d/a;->zg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "dior"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->Ag:Z

    sget-boolean v0, Ld/d/a;->Ag:Z

    if-eqz v0, :cond_12

    const-string v0, "ro.boot.modem"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v4, "LTETD"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_12

    move v0, v2

    goto :goto_12

    :cond_12
    move v0, v1

    :goto_12
    sput-boolean v0, Ld/d/a;->Bg:Z

    sget-boolean v0, Ld/d/a;->Ag:Z

    if-eqz v0, :cond_13

    const-string v0, "ro.boot.modem"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v4, "LTEW"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_13

    move v0, v2

    goto :goto_13

    :cond_13
    move v0, v1

    :goto_13
    sput-boolean v0, Ld/d/a;->Cg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "HM2014811"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->Dg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "HM2014812"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_15

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "HM2014821"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_14

    goto :goto_14

    :cond_14
    move v0, v1

    goto :goto_15

    :cond_15
    :goto_14
    move v0, v2

    :goto_15
    sput-boolean v0, Ld/d/a;->Eg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "HM2014813"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_17

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "HM2014112"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_16

    goto :goto_16

    :cond_16
    move v0, v1

    goto :goto_17

    :cond_17
    :goto_16
    move v0, v2

    :goto_17
    sput-boolean v0, Ld/d/a;->Fg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "HM2014818"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->Gg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "HM2014817"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->Hg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "HM2014819"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->Ig:Z

    sget-boolean v0, Ld/d/a;->Dg:Z

    if-nez v0, :cond_19

    sget-boolean v0, Ld/d/a;->Eg:Z

    if-nez v0, :cond_19

    sget-boolean v0, Ld/d/a;->Fg:Z

    if-nez v0, :cond_19

    sget-boolean v0, Ld/d/a;->Gg:Z

    if-nez v0, :cond_19

    sget-boolean v0, Ld/d/a;->Hg:Z

    if-nez v0, :cond_19

    sget-boolean v0, Ld/d/a;->Ig:Z

    if-eqz v0, :cond_18

    goto :goto_18

    :cond_18
    move v0, v1

    goto :goto_19

    :cond_19
    :goto_18
    move v0, v2

    :goto_19
    sput-boolean v0, Ld/d/a;->Jg:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v4, "gucci"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->Kg:Z

    sget-boolean v0, Ld/d/a;->Kg:Z

    const-string v4, "persist.sys.modem"

    const-string v5, "cm"

    if-eqz v0, :cond_1a

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v5, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1a

    move v0, v2

    goto :goto_1a

    :cond_1a
    move v0, v1

    :goto_1a
    sput-boolean v0, Ld/d/a;->Lg:Z

    sget-boolean v0, Ld/d/a;->Kg:Z

    const-string v6, "cu"

    if-eqz v0, :cond_1b

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v6, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1b

    move v0, v2

    goto :goto_1b

    :cond_1b
    move v0, v1

    :goto_1b
    sput-boolean v0, Ld/d/a;->Mg:Z

    sget-boolean v0, Ld/d/a;->Kg:Z

    const-string v7, "ct"

    if-eqz v0, :cond_1c

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v7, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1c

    move v0, v2

    goto :goto_1c

    :cond_1c
    move v0, v1

    :goto_1c
    sput-boolean v0, Ld/d/a;->Ng:Z

    sget-boolean v0, Ld/d/a;->jg:Z

    if-eqz v0, :cond_1d

    invoke-static {}, Ld/d/a;->Om()Z

    move-result v0

    if-eqz v0, :cond_1d

    move v0, v2

    goto :goto_1d

    :cond_1d
    move v0, v1

    :goto_1d
    sput-boolean v0, Ld/d/a;->Og:Z

    sget-boolean v0, Ld/d/a;->qg:Z

    const-string v4, "persist.radio.modem"

    if-eqz v0, :cond_1e

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v8, "CDMA"

    invoke-virtual {v8, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1e

    move v0, v2

    goto :goto_1e

    :cond_1e
    move v0, v1

    :goto_1e
    sput-boolean v0, Ld/d/a;->Pg:Z

    sget-boolean v0, Ld/d/a;->qg:Z

    if-eqz v0, :cond_1f

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v8, "LTE-CMCC"

    invoke-virtual {v8, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1f

    move v0, v2

    goto :goto_1f

    :cond_1f
    move v0, v1

    :goto_1f
    sput-boolean v0, Ld/d/a;->Qg:Z

    sget-boolean v0, Ld/d/a;->qg:Z

    if-eqz v0, :cond_20

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v8, "LTE-CU"

    invoke-virtual {v8, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_20

    move v0, v2

    goto :goto_20

    :cond_20
    move v0, v1

    :goto_20
    sput-boolean v0, Ld/d/a;->Rg:Z

    sget-boolean v0, Ld/d/a;->qg:Z

    if-eqz v0, :cond_21

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v8, "LTE-CT"

    invoke-virtual {v8, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_21

    move v0, v2

    goto :goto_21

    :cond_21
    move v0, v1

    :goto_21
    sput-boolean v0, Ld/d/a;->Sg:Z

    sget-boolean v0, Ld/d/a;->qg:Z

    if-eqz v0, :cond_22

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v8, "LTE-India"

    invoke-virtual {v8, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_22

    move v0, v2

    goto :goto_22

    :cond_22
    move v0, v1

    :goto_22
    sput-boolean v0, Ld/d/a;->Tg:Z

    sget-boolean v0, Ld/d/a;->qg:Z

    if-eqz v0, :cond_23

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v4, "LTE-SEAsa"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_23

    move v0, v2

    goto :goto_23

    :cond_23
    move v0, v1

    :goto_23
    sput-boolean v0, Ld/d/a;->Ug:Z

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->Vg:Z

    const-string v0, "ro.carrier.name"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v6, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    sput-boolean v3, Ld/d/a;->Wg:Z

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v5, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    const-string v4, "ro.miui.cust_variant"

    if-eqz v3, :cond_25

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    const-string v8, "cn_chinamobile"

    invoke-virtual {v8, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_24

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    const-string v8, "cn_cta"

    invoke-virtual {v8, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_25

    :cond_24
    move v3, v2

    goto :goto_24

    :cond_25
    move v3, v1

    :goto_24
    sput-boolean v3, Ld/d/a;->Xg:Z

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v5, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_26

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    const-string v4, "cn_cmcooperation"

    invoke-virtual {v4, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_26

    move v3, v2

    goto :goto_25

    :cond_26
    move v3, v1

    :goto_25
    sput-boolean v3, Ld/d/a;->Yg:Z

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v7, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->Zg:Z

    sget-object v0, Landroid/os/Build$VERSION;->INCREMENTAL:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_27

    sget-object v0, Landroid/os/Build$VERSION;->INCREMENTAL:Ljava/lang/String;

    const-string v3, "\\d+.\\d+.\\d+(-internal)?"

    invoke-virtual {v0, v3}, Ljava/lang/String;->matches(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_27

    move v0, v2

    goto :goto_26

    :cond_27
    move v0, v1

    :goto_26
    sput-boolean v0, Ld/d/a;->_g:Z

    sget-object v0, Landroid/os/Build;->TYPE:Ljava/lang/String;

    const-string v3, "user"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_28

    sget-boolean v0, Ld/d/a;->_g:Z

    if-nez v0, :cond_28

    move v0, v2

    goto :goto_27

    :cond_28
    move v0, v1

    :goto_27
    sput-boolean v0, Ld/d/a;->bh:Z

    sget-boolean v0, Ld/d/a;->_g:Z

    if-nez v0, :cond_2a

    sget-boolean v0, Ld/d/a;->bh:Z

    if-eqz v0, :cond_29

    goto :goto_28

    :cond_29
    move v0, v1

    goto :goto_29

    :cond_2a
    :goto_28
    move v0, v2

    :goto_29
    sput-boolean v0, Ld/d/a;->dh:Z

    const-string v0, "ro.product.mod_device"

    const-string v3, ""

    invoke-static {v0, v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    const-string v8, "_alpha"

    invoke-virtual {v4, v8}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v4

    sput-boolean v4, Ld/d/a;->eh:Z

    const-string v4, "ro.miui.cts"

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    const-string v8, "1"

    invoke-virtual {v8, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    xor-int/2addr v4, v2

    const-string v9, "persist.sys.miui_optimization"

    invoke-static {v9, v4}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v4

    xor-int/2addr v4, v2

    sput-boolean v4, Ld/d/a;->fh:Z

    const-string v4, "ro.miui.cta"

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v8, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    sput-boolean v4, Ld/d/a;->gh:Z

    const-string v4, "ro.cust.test"

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v5, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    sput-boolean v5, Ld/d/a;->hh:Z

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v6, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    sput-boolean v5, Ld/d/a;->ih:Z

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v7, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    sput-boolean v4, Ld/d/a;->jh:Z

    const-string v4, "persist.sys.func_limit_switch"

    invoke-static {v4}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v8, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    sput-boolean v4, Ld/d/a;->kh:Z

    const-string v4, "ro.boot.hwc"

    invoke-static {v4, v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    const-string v5, "GLOBAL"

    invoke-virtual {v4, v5}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v4

    if-nez v4, :cond_2c

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v4

    invoke-virtual {v4}, Lcom/mi/config/a;->c_0x44()Z

    move-result v4

    if-eqz v4, :cond_2b

    goto :goto_2a

    :cond_2b
    move v4, v1

    goto :goto_2b

    :cond_2c
    :goto_2a
    move v4, v2

    :goto_2b
    sput-boolean v4, Ld/d/a;->lh:Z

    invoke-static {v0, v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v4, "_global"

    invoke-virtual {v0, v4}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->mh:Z

    invoke-static {}, Ld/d/a;->Pm()Z

    move-result v0

    sput-boolean v0, Ld/d/a;->nh:Z

    invoke-static {}, Ld/d/a;->Nm()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Ld/d/a;->rh:Ljava/lang/String;

    const-string v0, "ro.debuggable"

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v0

    if-ne v0, v2, :cond_2d

    goto :goto_2c

    :cond_2d
    move v2, v1

    :goto_2c
    sput-boolean v2, Ld/d/a;->IS_DEBUGGABLE:Z

    const-string v0, "ro.miui.has_cust_partition"

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->sh:Z

    const-string v0, "ro.miui.cust_device"

    invoke-static {v0, v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "_pro"

    invoke-virtual {v0, v1}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v0

    sput-boolean v0, Ld/d/a;->th:Z

    return-void
.end method

.method protected constructor <init>()V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/InstantiationException;
        }
    .end annotation

    invoke-direct {p0}, Landroid/os/Build;-><init>()V

    new-instance p0, Ljava/lang/InstantiationException;

    const-string v0, "Cannot instantiate utility class"

    invoke-direct {p0, v0}, Ljava/lang/InstantiationException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public static Ca()Ljava/lang/String;
    .locals 2

    sget-boolean v0, Ld/d/a;->lh:Z

    const-string v1, "ro.miui.cust_variant"

    if-nez v0, :cond_0

    const-string v0, "cn"

    invoke-static {v1, v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0

    :cond_0
    const-string v0, "hk"

    invoke-static {v1, v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static Da()I
    .locals 2

    const-string v0, "persist.sys.user_mode"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public static F(Landroid/content/Context;)Z
    .locals 1

    const-string p0, "support_torch"

    const/4 v0, 0x1

    invoke-static {p0, v0}, Ld/g/a;->getBoolean(Ljava/lang/String;Z)Z

    move-result p0

    return p0
.end method

.method private static Nm()Ljava/lang/String;
    .locals 5

    const-string v0, ""

    const-string v1, "ro.miui.userdata_version"

    invoke-static {v1, v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const-string v0, "Unavailable"

    return-object v0

    :cond_0
    sget-boolean v2, Ld/d/a;->lh:Z

    if-eqz v2, :cond_1

    const-string v2, "global"

    goto :goto_0

    :cond_1
    const-string v2, "cn"

    :goto_0
    const-string v3, "ro.carrier.name"

    invoke-static {v3, v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "_"

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    :cond_2
    const/4 v0, 0x3

    new-array v0, v0, [Ljava/lang/Object;

    const/4 v4, 0x0

    aput-object v1, v0, v4

    const/4 v1, 0x1

    aput-object v2, v0, v1

    const/4 v1, 0x2

    aput-object v3, v0, v1

    const-string v1, "%s(%s%s)"

    invoke-static {v1, v0}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method private static Om()Z
    .locals 2

    const-string v0, "ro.soc.name"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "msm8660"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    const-string v1, "unkown"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

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

.method private static Pm()Z
    .locals 2

    const-string v0, "ro.build.characteristics"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "tablet"

    invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    return v0
.end method

.method public static a(Landroid/content/Context;I)V
    .locals 0

    invoke-static {p1}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object p0

    const-string p1, "persist.sys.user_mode"

    invoke-static {p1, p0}, Landroid/os/SystemProperties;->set(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method public static getRegion()Ljava/lang/String;
    .locals 2

    const-string v0, "ro.miui.region"

    const-string v1, "CN"

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static k(Ljava/lang/String;)Z
    .locals 1

    invoke-static {}, Ld/d/a;->getRegion()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method
