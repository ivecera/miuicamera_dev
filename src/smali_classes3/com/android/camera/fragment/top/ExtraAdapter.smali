.class public Lcom/android/camera/fragment/top/ExtraAdapter;
.super Landroid/support/v7/widget/RecyclerView$Adapter;
.source "ExtraAdapter.java"

# interfaces
.implements Landroid/view/View$OnTouchListener;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/support/v7/widget/RecyclerView$Adapter<",
        "Lcom/android/camera/fragment/CommonRecyclerViewHolder;",
        ">;",
        "Landroid/view/View$OnTouchListener;"
    }
.end annotation


# instance fields
.field private mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

.field private mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

.field private mDegree:I

.field private mImageNormalColor:I

.field private mItemHeight:I

.field private mOnClickListener:Landroid/view/View$OnClickListener;

.field private mSelectedColor:I

.field private mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

.field private mTAG:I

.field private mTextNormalColor:I

.field private mUnableClickColor:I


# direct methods
.method public constructor <init>(Lcom/android/camera/data/data/config/SupportedConfigs;Landroid/view/View$OnClickListener;I)V
    .locals 1

    invoke-direct {p0}, Landroid/support/v7/widget/RecyclerView$Adapter;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mTAG:I

    iput-object p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    iput-object p2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mOnClickListener:Landroid/view/View$OnClickListener;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    const p1, -0x4c000001

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mTextNormalColor:I

    const p1, -0x141415

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mImageNormalColor:I

    const p1, -0xe66d19

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSelectedColor:I

    const p1, 0x47ffffff

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mUnableClickColor:I

    iput p3, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mItemHeight:I

    return-void
.end method


# virtual methods
.method public getItemCount()I
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    invoke-virtual {p0}, Lcom/android/camera/data/data/config/SupportedConfigs;->getLength()I

    move-result p0

    return p0
.end method

.method public bridge synthetic onBindViewHolder(Landroid/support/v7/widget/RecyclerView$ViewHolder;I)V
    .locals 0

    check-cast p1, Lcom/android/camera/fragment/CommonRecyclerViewHolder;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/top/ExtraAdapter;->onBindViewHolder(Lcom/android/camera/fragment/CommonRecyclerViewHolder;I)V

    return-void
.end method

.method public onBindViewHolder(Lcom/android/camera/fragment/CommonRecyclerViewHolder;I)V
    .locals 12

    iget-object v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    invoke-virtual {v0, p2}, Lcom/android/camera/data/data/config/SupportedConfigs;->getConfig(I)I

    move-result p2

    iget-object v0, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    iget v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mItemHeight:I

    invoke-virtual {v0, v1}, Landroid/view/View;->setMinimumHeight(I)V

    iget-object v0, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    iget-object v0, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mOnClickListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v0

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xa0

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    const/16 v3, 0xce

    const/4 v4, -0x1

    const/4 v5, 0x1

    const/4 v6, 0x0

    const/4 v7, 0x0

    if-eq p2, v3, :cond_14

    const/16 v3, 0xd5

    if-eq p2, v3, :cond_13

    const/16 v3, 0xd8

    const v8, 0xfff3

    const v9, 0x7f09001d

    if-eq p2, v3, :cond_11

    const/16 v3, 0xff

    if-eq p2, v3, :cond_10

    const/16 v3, 0xe1

    if-eq p2, v3, :cond_f

    const/16 v3, 0xe2

    if-eq p2, v3, :cond_e

    const/16 v3, 0x104

    if-eq p2, v3, :cond_d

    const/16 v3, 0x105

    if-eq p2, v3, :cond_c

    const-string v3, "4x3"

    packed-switch p2, :pswitch_data_0

    packed-switch p2, :pswitch_data_1

    packed-switch p2, :pswitch_data_2

    packed-switch p2, :pswitch_data_3

    packed-switch p2, :pswitch_data_4

    move v0, v4

    move v1, v0

    move v8, v5

    move v2, v6

    move v3, v2

    goto/16 :goto_14

    :pswitch_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAutoZoom()Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;->isEnabled(I)Z

    move-result v0

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;->getResIcon(Z)I

    move-result v2

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningAutoZoom;->getResText()I

    move-result v1

    goto/16 :goto_6

    :pswitch_1
    const v0, 0x7f0800fa

    const v1, 0x7f0f0111

    iget-object v2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v3, "pref_hand_gesture"

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v2

    goto/16 :goto_9

    :pswitch_2
    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isCinematicAspectRatioEnabled(I)Z

    move-result v0

    const v1, 0x7f0800ce

    const v2, 0x7f0f01bf

    :goto_0
    move v3, v1

    move v1, v2

    goto/16 :goto_7

    :pswitch_3
    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    if-eqz v0, :cond_0

    const v0, 0x7f0800e7

    const v1, 0x7f0f03bc

    goto/16 :goto_10

    :cond_0
    const v0, 0x7f0800e6

    const v1, 0x7f0f0228

    goto/16 :goto_10

    :pswitch_4
    const v0, 0x7f080115

    const v1, 0x7f0f00b0

    iget-object v2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v3, "pref_camera_super_resolution_key"

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v2

    goto/16 :goto_9

    :pswitch_5
    const v0, 0x7f0800ed

    const v1, 0x7f0f0270

    invoke-static {}, Lcom/android/camera/CameraSettings;->isDualCameraWaterMarkOpen()Z

    move-result v2

    goto/16 :goto_9

    :pswitch_6
    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigBeauty()Lcom/android/camera/data/data/config/ComponentConfigBeauty;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigBeauty;->isSwitchOn(I)Z

    move-result v2

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/ComponentData;->getValueSelectedDrawable(I)I

    move-result v3

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/ComponentData;->getValueDisplayString(I)I

    move-result v0

    move v1, v0

    goto/16 :goto_a

    :pswitch_7
    const v0, 0x7f0800f6

    const v1, 0x7f0f0355

    iget-object v2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v3, "pref_camera_show_gender_age_key"

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v2

    goto/16 :goto_9

    :pswitch_8
    const v1, 0x7f0f032b

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigRaw()Lcom/android/camera/data/data/config/ComponentConfigRaw;

    move-result-object v2

    invoke-virtual {v2, v0}, Lcom/android/camera/data/data/config/ComponentConfigRaw;->isSwitchOn(I)Z

    move-result v0

    if-eqz v0, :cond_1

    const v2, 0x7f0801d5

    goto/16 :goto_6

    :cond_1
    const v2, 0x7f0801d4

    goto/16 :goto_6

    :pswitch_9
    const v0, 0x7f080101

    const v1, 0x7f0f0314

    iget-object v2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v3, "pref_camera_magic_mirror_key"

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v2

    goto/16 :goto_9

    :pswitch_a
    const v0, 0x7f0800f9

    const v1, 0x7f0f00a8

    iget-object v2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v3, "pref_camera_groupshot_mode_key"

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v2

    goto/16 :goto_9

    :pswitch_b
    const v0, 0x7f080104

    const v1, 0x7f0f00ac

    iget-object v2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v3, "pref_camera_scenemode_setting_key"

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v2

    goto/16 :goto_9

    :pswitch_c
    const v0, 0x7f0800f1

    const v1, 0x7f0f040b

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isFastMotionModule()Z

    move-result v2

    goto/16 :goto_9

    :pswitch_d
    const v0, 0x7f080100

    const v1, 0x7f0f00aa

    iget-object v2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v3, "pref_camera_ubifocus_key"

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v2

    goto/16 :goto_9

    :pswitch_e
    const v0, 0x7f0800fb

    const v1, 0x7f0f00a9

    iget-object v2, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v3, "pref_camera_hand_night_key"

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v2

    goto/16 :goto_9

    :pswitch_f
    const v2, 0x7f080110

    const v3, 0x7f0f00af

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGradienterOn()Z

    move-result v10

    if-eqz v1, :cond_2

    invoke-interface {v1, v9}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->getActiveFragment(I)I

    move-result v1

    if-ne v1, v8, :cond_2

    goto :goto_1

    :cond_2
    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isAutoZoomEnabled(I)Z

    move-result v0

    if-eqz v0, :cond_3

    move v1, v3

    move v0, v4

    move v8, v5

    move v3, v2

    goto/16 :goto_11

    :cond_3
    :goto_1
    move v1, v3

    move v0, v4

    move v8, v5

    move v3, v2

    move v2, v10

    goto/16 :goto_14

    :pswitch_10
    iget-object v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningTiltValue()Lcom/android/camera/data/data/runing/ComponentRunningTiltValue;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string v3, "pref_camera_tilt_shift_mode"

    invoke-virtual {v1, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_4

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/ComponentData;->getValueDisplayString(I)I

    move-result v3

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/ComponentData;->getValueSelectedDrawable(I)I

    move-result v0

    move v2, v1

    move v1, v3

    move v8, v5

    move v3, v0

    goto/16 :goto_8

    :cond_4
    const v0, 0x7f0f00b1

    const v2, 0x7f080116

    move v3, v2

    move v8, v5

    move v2, v1

    goto/16 :goto_f

    :pswitch_11
    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAIWatermark()Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    move-result-object v1

    const v2, 0x7f0f0051

    iget-object v8, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v8}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigRatio()Lcom/android/camera/data/data/config/ComponentConfigRatio;

    move-result-object v8

    invoke-static {}, Lcom/android/camera/CameraSettings;->getCameraId()I

    move-result v9

    const/16 v10, 0x15

    if-eq v10, v9, :cond_6

    const/16 v10, 0x16

    if-ne v10, v9, :cond_5

    goto :goto_2

    :cond_5
    move v9, v6

    goto :goto_3

    :cond_6
    :goto_2
    move v9, v5

    :goto_3
    invoke-virtual {v8, v0}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0, v3}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_8

    if-eqz v9, :cond_7

    goto :goto_4

    :cond_7
    move v0, v5

    goto :goto_5

    :cond_8
    :goto_4
    iget-object v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAIWatermark()Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    move-result-object v0

    invoke-virtual {v0, v6}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->setAIWatermarkEnable(Z)V

    move v0, v6

    :goto_5
    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getAIWatermarkEnable()Z

    move-result v3

    invoke-virtual {v1, v3}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getResIcon(Z)I

    move-result v1

    move v8, v0

    move v0, v4

    move v11, v3

    move v3, v1

    move v1, v2

    move v2, v11

    goto/16 :goto_14

    :pswitch_12
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getmComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->isEnabled(I)Z

    move-result v0

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getResIcon(Z)I

    move-result v2

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getDisplayTitleString()I

    move-result v1

    goto :goto_6

    :pswitch_13
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDocument()Lcom/android/camera/data/data/runing/ComponentRunningDocument;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningDocument;->isEnabled(I)Z

    move-result v0

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningDocument;->getResIcon(Z)I

    move-result v2

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningDocument;->getResText()I

    move-result v1

    goto :goto_6

    :pswitch_14
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningSubtitle()Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;->isEnabled(I)Z

    move-result v0

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;->getResIcon(Z)I

    move-result v2

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;->getResText()I

    move-result v1

    :goto_6
    move v3, v2

    :goto_7
    move v8, v5

    move v2, v0

    :goto_8
    move v0, v4

    goto/16 :goto_14

    :pswitch_15
    const v0, 0x7f080103

    const v1, 0x7f0f033f

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v2

    const-string v3, "pref_camera_referenceline_key"

    invoke-virtual {v2, v3, v6}, Lcom/android/camera/data/data/DataItemBase;->getBoolean(Ljava/lang/String;Z)Z

    move-result v2

    :goto_9
    move v3, v0

    :goto_a
    move v0, v4

    goto/16 :goto_d

    :pswitch_16
    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigRatio()Lcom/android/camera/data/data/config/ComponentConfigRatio;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/ComponentData;->getValueSelectedDrawable(I)I

    move-result v2

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->getDisplayTitleString()I

    move-result v8

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/ComponentData;->getValueDisplayString(I)I

    move-result v9

    const/16 v10, 0xa3

    if-eq v0, v10, :cond_9

    const/16 v10, 0xa5

    if-ne v0, v10, :cond_a

    :cond_9
    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->getComponentValue(I)Ljava/lang/String;

    move-result-object v1

    invoke-static {v1, v3}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_a

    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAIWatermark()Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getAIWatermarkEnable()Z

    move-result v1

    if-eqz v1, :cond_a

    move v1, v6

    goto :goto_b

    :cond_a
    move v1, v5

    :goto_b
    invoke-static {}, Lcom/android/camera/CameraSettings;->isGifOn()Z

    move-result v3

    if-eqz v3, :cond_b

    const/16 v3, 0xb8

    if-ne v0, v3, :cond_b

    const v0, 0x7f0800da

    move v1, v6

    goto :goto_c

    :cond_b
    move v0, v2

    :goto_c
    move v3, v0

    move v2, v6

    move v0, v9

    move v6, v5

    move v11, v8

    move v8, v1

    move v1, v11

    goto/16 :goto_14

    :pswitch_17
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentUltraPixel()Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getMenuDrawable()I

    move-result v1

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getMenuString()Ljava/lang/String;

    move-result-object v7

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    move v2, v0

    move v3, v1

    move v0, v4

    move v1, v0

    :goto_d
    move v8, v5

    goto/16 :goto_14

    :pswitch_18
    iget-object v1, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v1, p0}, Landroid/view/View;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigVideoQuality()Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->getValueSelectedDrawable(I)I

    move-result v2

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->getDisplayTitleString()I

    move-result v3

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->disableUpdate()Z

    move-result v8

    xor-int/2addr v8, v5

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->getValueDisplayString(I)I

    move-result v0

    :goto_e
    move v1, v3

    move v3, v2

    move v2, v6

    move v6, v5

    goto/16 :goto_14

    :cond_c
    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isProVideoHistogramOpen(I)Z

    move-result v0

    const v1, 0x7f0800fc

    const v2, 0x7f0f01df

    goto/16 :goto_0

    :cond_d
    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isProVideoLogOpen(I)Z

    move-result v0

    const v1, 0x7f080120

    const v2, 0x7f0f0163

    goto/16 :goto_0

    :cond_e
    iget-object v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningTimer()Lcom/android/camera/data/data/runing/ComponentRunningTimer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningTimer;->isSwitchOn()Z

    move-result v1

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/ComponentData;->getValueSelectedDrawable(I)I

    move-result v3

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/ComponentData;->getValueDisplayString(I)I

    move-result v0

    move v2, v1

    move v8, v5

    :goto_f
    move v1, v0

    goto/16 :goto_8

    :cond_f
    const v0, 0x7f0800f0

    const v1, 0x7f0f00ad

    :goto_10
    move v3, v0

    move v0, v4

    move v8, v5

    :goto_11
    move v2, v6

    goto :goto_14

    :cond_10
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningMacroMode()Lcom/android/camera/data/data/config/ComponentRunningMacroMode;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentRunningMacroMode;->isSwitchOn(I)Z

    move-result v0

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/config/ComponentRunningMacroMode;->getResIcon(Z)I

    move-result v2

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentRunningMacroMode;->getResText()I

    move-result v1

    goto/16 :goto_6

    :cond_11
    if-eqz v1, :cond_12

    invoke-interface {v1, v9}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->getActiveFragment(I)I

    move-result v0

    if-ne v0, v8, :cond_12

    move v0, v5

    goto :goto_12

    :cond_12
    move v0, v6

    :goto_12
    const v1, 0x7f080123

    const v2, 0x7f0f0493

    goto/16 :goto_0

    :cond_13
    iget-object v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDataItemConfig:Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigSlowMotionQuality()Lcom/android/camera/data/data/config/ComponentConfigSlowMotionQuality;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/ComponentData;->getValueSelectedDrawable(I)I

    move-result v2

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentConfigSlowMotionQuality;->getDisplayTitleString()I

    move-result v3

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentConfigSlowMotionQuality;->disableUpdate()Z

    move-result v8

    xor-int/2addr v8, v5

    invoke-virtual {v1, v0}, Lcom/android/camera/data/data/ComponentData;->getValueDisplayString(I)I

    move-result v0

    goto/16 :goto_e

    :cond_14
    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveShotOn()Z

    move-result v0

    if-eqz v0, :cond_15

    const v1, 0x7f080194

    goto :goto_13

    :cond_15
    const v1, 0x7f080192

    :goto_13
    const v2, 0x7f0f0080

    goto/16 :goto_0

    :goto_14
    const v9, 0x7f09005e

    invoke-virtual {p1, v9}, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->getView(I)Landroid/view/View;

    move-result-object v9

    check-cast v9, Landroid/widget/TextView;

    const v10, 0x7f09005d

    invoke-virtual {p1, v10}, Lcom/android/camera/fragment/CommonRecyclerViewHolder;->getView(I)Landroid/view/View;

    move-result-object v10

    check-cast v10, Lcom/android/camera/ui/ColorImageView;

    invoke-virtual {v9, v5}, Landroid/widget/TextView;->setSelected(Z)V

    if-eq v1, v4, :cond_16

    invoke-virtual {v9, v1}, Landroid/widget/TextView;->setText(I)V

    goto :goto_15

    :cond_16
    invoke-virtual {v9, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :goto_15
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v9}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v5

    invoke-virtual {v1, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    if-eq v0, v4, :cond_17

    iget-object v4, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v4}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    invoke-virtual {v4, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_17
    if-eqz v2, :cond_18

    iget-object v0, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v4, 0x7f0f0039

    invoke-virtual {v0, v4}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_16

    :cond_18
    if-nez v6, :cond_19

    iget-object v0, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v4, 0x7f0f001f

    invoke-virtual {v0, v4}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_19
    :goto_16
    iget-object v0, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v0, v1}, Landroid/view/View;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object v0, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v0, v8}, Landroid/view/View;->setEnabled(Z)V

    if-eqz v8, :cond_1b

    if-eqz v2, :cond_1a

    iget v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSelectedColor:I

    goto :goto_17

    :cond_1a
    iget v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mTextNormalColor:I

    goto :goto_17

    :cond_1b
    iget v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mUnableClickColor:I

    :goto_17
    if-eqz v2, :cond_1c

    iget v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mSelectedColor:I

    goto :goto_18

    :cond_1c
    iget v1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mImageNormalColor:I

    :goto_18
    invoke-virtual {v9, v0}, Landroid/widget/TextView;->setTextColor(I)V

    sparse-switch v3, :sswitch_data_0

    invoke-virtual {v10, v1}, Lcom/android/camera/ui/ColorImageView;->setColor(I)V

    goto :goto_19

    :sswitch_0
    if-nez v8, :cond_1d

    iget v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mUnableClickColor:I

    invoke-virtual {v10, v0}, Lcom/android/camera/ui/ColorImageView;->setColor(I)V

    goto :goto_19

    :cond_1d
    invoke-virtual {v10, v1}, Lcom/android/camera/ui/ColorImageView;->setColor(I)V

    :goto_19
    invoke-virtual {v10, v3}, Landroid/widget/ImageView;->setImageResource(I)V

    invoke-static {}, Lcom/android/camera/Util;->isAccessible()Z

    move-result v0

    if-nez v0, :cond_1e

    invoke-static {}, Lcom/android/camera/Util;->isSetContentDesc()Z

    move-result v0

    if-eqz v0, :cond_1f

    :cond_1e
    iget v0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mTAG:I

    if-ne v0, p2, :cond_1f

    iget-object p2, p1, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    new-instance v0, Lcom/android/camera/fragment/top/ExtraAdapter$1;

    invoke-direct {v0, p0, p1}, Lcom/android/camera/fragment/top/ExtraAdapter$1;-><init>(Lcom/android/camera/fragment/top/ExtraAdapter;Lcom/android/camera/fragment/CommonRecyclerViewHolder;)V

    const-wide/16 p0, 0x64

    invoke-virtual {p2, v0, p0, p1}, Landroid/view/View;->postDelayed(Ljava/lang/Runnable;J)Z

    :cond_1f
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0xd0
        :pswitch_18
        :pswitch_17
        :pswitch_16
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0xdb
        :pswitch_15
        :pswitch_14
        :pswitch_13
        :pswitch_12
        :pswitch_11
    .end packed-switch

    :pswitch_data_2
    .packed-switch 0xe4
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
    .end packed-switch

    :pswitch_data_3
    .packed-switch 0xe9
        :pswitch_c
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
    .end packed-switch

    :pswitch_data_4
    .packed-switch 0xfb
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :sswitch_data_0
    .sparse-switch
        0x7f080013 -> :sswitch_0
        0x7f080014 -> :sswitch_0
        0x7f0800d9 -> :sswitch_0
        0x7f0800da -> :sswitch_0
        0x7f0800db -> :sswitch_0
        0x7f0800dd -> :sswitch_0
        0x7f0800e2 -> :sswitch_0
        0x7f0800f5 -> :sswitch_0
        0x7f080110 -> :sswitch_0
    .end sparse-switch
.end method

.method public bridge synthetic onCreateViewHolder(Landroid/view/ViewGroup;I)Landroid/support/v7/widget/RecyclerView$ViewHolder;
    .locals 0

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/top/ExtraAdapter;->onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/fragment/CommonRecyclerViewHolder;

    move-result-object p0

    return-object p0
.end method

.method public onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/fragment/CommonRecyclerViewHolder;
    .locals 2

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-static {p2}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p2

    const v0, 0x7f0b0055

    const/4 v1, 0x0

    invoke-virtual {p2, v0, p1, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    iget p0, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDegree:I

    if-eqz p0, :cond_0

    int-to-float p0, p0

    invoke-static {p1, p0}, Landroid/support/v4/view/ViewCompat;->setRotation(Landroid/view/View;F)V

    :cond_0
    new-instance p0, Lcom/android/camera/fragment/CommonRecyclerViewHolder;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/CommonRecyclerViewHolder;-><init>(Landroid/view/View;)V

    return-object p0
.end method

.method public onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 1

    const p0, 0x7f09005d

    invoke-virtual {p1, p0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p0

    const/4 p1, 0x0

    if-nez p0, :cond_0

    return p1

    :cond_0
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result p2

    if-eqz p2, :cond_2

    const/4 v0, 0x1

    if-eq p2, v0, :cond_1

    const/4 v0, 0x2

    if-eq p2, v0, :cond_3

    const/4 v0, 0x3

    if-eq p2, v0, :cond_1

    goto :goto_0

    :cond_1
    const/high16 p2, 0x3f800000    # 1.0f

    invoke-virtual {p0, p2}, Landroid/view/View;->setScaleX(F)V

    invoke-virtual {p0, p2}, Landroid/view/View;->setScaleY(F)V

    :goto_0
    return p1

    :cond_2
    const p2, 0x3f6e147b    # 0.93f

    invoke-virtual {p0, p2}, Landroid/view/View;->setScaleX(F)V

    invoke-virtual {p0, p2}, Landroid/view/View;->setScaleY(F)V

    :cond_3
    return p1
.end method

.method public setNewDegree(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mDegree:I

    return-void
.end method

.method public setOnClictTag(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/fragment/top/ExtraAdapter;->mTAG:I

    return-void
.end method
