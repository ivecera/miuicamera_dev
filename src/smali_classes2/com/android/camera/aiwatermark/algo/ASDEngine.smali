.class public Lcom/android/camera/aiwatermark/algo/ASDEngine;
.super Ljava/lang/Object;
.source "ASDEngine.java"


# direct methods
.method private constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static final spots2ASDKey(I)Ljava/lang/String;
    .locals 2

    const-string v0, "negative"

    if-eqz p0, :cond_6

    const/16 v1, 0xd

    if-eq p0, v1, :cond_5

    const/16 v1, 0x19

    if-eq p0, v1, :cond_4

    const/4 v1, 0x2

    if-eq p0, v1, :cond_3

    const/4 v1, 0x3

    if-eq p0, v1, :cond_2

    const/16 v1, 0x1b

    if-eq p0, v1, :cond_1

    const/16 v1, 0x1c

    if-eq p0, v1, :cond_0

    packed-switch p0, :pswitch_data_0

    packed-switch p0, :pswitch_data_1

    packed-switch p0, :pswitch_data_2

    packed-switch p0, :pswitch_data_3

    packed-switch p0, :pswitch_data_4

    goto/16 :goto_0

    :pswitch_0
    const-string v0, "tutuk"

    goto :goto_0

    :pswitch_1
    const-string v0, "coconut_tree"

    goto :goto_0

    :pswitch_2
    const-string v0, "monkey"

    goto :goto_0

    :pswitch_3
    const-string v0, "underwater"

    goto :goto_0

    :pswitch_4
    const-string v0, "beach"

    goto :goto_0

    :pswitch_5
    const-string v0, "temple"

    goto :goto_0

    :pswitch_6
    const-string v0, "motorcycle"

    goto :goto_0

    :pswitch_7
    const-string v0, "overcast"

    goto :goto_0

    :pswitch_8
    const-string v0, "cloudy"

    goto :goto_0

    :pswitch_9
    const-string v0, "city"

    goto :goto_0

    :pswitch_a
    const-string v0, "fleshiness"

    goto :goto_0

    :pswitch_b
    const-string v0, "leafs"

    goto :goto_0

    :pswitch_c
    const-string v0, "grassplot"

    goto :goto_0

    :pswitch_d
    const-string v0, "snow"

    goto :goto_0

    :pswitch_e
    const-string v0, "nightscape"

    goto :goto_0

    :pswitch_f
    const-string v0, "plants"

    goto :goto_0

    :pswitch_10
    const-string v0, "dog"

    goto :goto_0

    :pswitch_11
    const-string v0, "cat"

    goto :goto_0

    :pswitch_12
    const-string v0, "sunrise_sunset"

    goto :goto_0

    :pswitch_13
    const-string v0, "bluesky"

    goto :goto_0

    :cond_0
    const-string v0, "cow"

    goto :goto_0

    :cond_1
    const-string v0, "buddha"

    goto :goto_0

    :cond_2
    const-string v0, "delicacy"

    goto :goto_0

    :cond_3
    const-string v0, "flower"

    goto :goto_0

    :cond_4
    const-string v0, "person"

    goto :goto_0

    :cond_5
    const-string v0, "autumn"

    :cond_6
    :goto_0
    return-object v0

    nop

    :pswitch_data_0
    .packed-switch 0x5
        :pswitch_13
        :pswitch_12
        :pswitch_11
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x10
        :pswitch_c
        :pswitch_b
        :pswitch_a
    .end packed-switch

    :pswitch_data_2
    .packed-switch 0x14
        :pswitch_9
        :pswitch_8
        :pswitch_7
    .end packed-switch

    :pswitch_data_3
    .packed-switch 0x1e
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
    .end packed-switch

    :pswitch_data_4
    .packed-switch 0x2c
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
