.class public Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;
.super Lcom/android/camera/fragment/BaseFragment;
.source "FragmentMimojiEdit2.java"

# interfaces
.implements Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;
.implements Landroid/view/View$OnClickListener;
.implements Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;


# static fields
.field private static final EDIT_ABANDON:I = 0x4

.field private static final EDIT_ABANDON_CAPTURE:I = 0x3

.field private static final EDIT_BACK:I = 0x1

.field private static final EDIT_CANCEL:I = 0x5

.field public static final EDIT_STATE_CREATE:I = 0x2

.field private static final EDIT_STATE_CREATE_EDITED:I = 0x3

.field public static final EDIT_STATE_POP:I = 0x4

.field private static final EDIT_STATE_POP_EDITED:I = 0x5

.field public static final EDIT_STATE_SAVE:I = 0x6

.field private static final FRAGMENT_INFO:I = 0xfa2

.field public static final TAG:Ljava/lang/String; = "FragmentMimojiEdit2"


# instance fields
.field private fromTag:I

.field private mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

.field private mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

.field private mBackTextView:Landroid/widget/TextView;

.field private mConfirmTextView:Landroid/widget/TextView;

.field private mContext:Landroid/content/Context;

.field private mCreateEmoticonTextView:Landroid/widget/TextView;

.field private mCurrentAlertDialog:Landroid/app/AlertDialog;

.field private mCurrentConfigPath:Ljava/lang/String;

.field private mCurrentTopPannelState:I

.field private mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

.field private mEditState:Z

.field private mEnterFromMimoji:Z

.field private mHandler:Landroid/os/Handler;
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "HandlerLeak"
        }
    .end annotation
.end field

.field private mIsShowDialog:Z

.field private mIsStartEdit:Z

.field private mLevelRecyleView:Landroid/support/v7/widget/RecyclerView;

.field private mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

.field private mMimojiEditViewLayout:Landroid/view/View;

.field private mMimojiPageChangeAnimManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;

.field private mMimojiTypeAdapter:Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter<",
            "Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;",
            ">;"
        }
    .end annotation
.end field

.field private mMimojiTypeSelectView:Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectHorizontalView;

.field private mMimojiTypeView:Landroid/widget/TextView;

.field private mOperateSelectLayout:Landroid/widget/LinearLayout;

.field private mPopSaveDeletePath:Ljava/lang/String;

.field private mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

.field private mRlAllEditContent:Landroid/widget/LinearLayout;

.field private mRlMainLayout:Landroid/widget/RelativeLayout;

.field private mRlNavigationlayout:Landroid/widget/RelativeLayout;

.field private mSaveFinishTextView:Landroid/widget/TextView;

.field private volatile mSetupCompleted:Z


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/fragment/BaseFragment;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditState:Z

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    const-string v1, ""

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentConfigPath:Ljava/lang/String;

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mPopSaveDeletePath:Ljava/lang/String;

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsShowDialog:Z

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mSetupCompleted:Z

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$4;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$4;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mHandler:Landroid/os/Handler;

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Landroid/view/View;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditViewLayout:Landroid/view/View;

    return-object p0
.end method

.method static synthetic access$100(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    return-object p0
.end method

.method static synthetic access$1000(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    return-object p0
.end method

.method static synthetic access$1100(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsStartEdit:Z

    return p0
.end method

.method static synthetic access$1200(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    return p0
.end method

.method static synthetic access$1300(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentConfigPath:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$1402(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsShowDialog:Z

    return p1
.end method

.method static synthetic access$200(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiPageChangeAnimManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;

    return-object p0
.end method

.method static synthetic access$302(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditState:Z

    return p1
.end method

.method static synthetic access$400(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->fromTag:I

    return p0
.end method

.method static synthetic access$500(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Lcom/arcsoft/avatar/AvatarEngine;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    return-object p0
.end method

.method static synthetic access$600(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    return p0
.end method

.method static synthetic access$700(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mPopSaveDeletePath:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$800(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    return-object p0
.end method

.method static synthetic access$900(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Landroid/content/Context;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mContext:Landroid/content/Context;

    return-object p0
.end method

.method private initConfigList()V
    .locals 8

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    iget-boolean v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentConfigPath:Ljava/lang/String;

    goto :goto_0

    :cond_0
    sget-object v1, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TempOriginalConfigPath:Ljava/lang/String;

    :goto_0
    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->initAvatar(Ljava/lang/String;)V

    new-instance v0, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;

    invoke-direct {v0}, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;-><init>()V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {v1, v0}, Lcom/arcsoft/avatar/AvatarEngine;->getConfigValue(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;)V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    invoke-virtual {v1, v0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setASAvatarConfigValue(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getASAvatarConfigValue()Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;

    move-result-object v1

    iget v1, v1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;->gender:I

    invoke-virtual {v0, v1}, Lcom/arcsoft/avatar/AvatarEngine;->getSupportConfigType(I)Ljava/util/ArrayList;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    invoke-virtual {v1, v0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setConfigTypeList(Ljava/util/ArrayList;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mLevelRecyleView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {v0}, Landroid/support/v7/widget/RecyclerView;->getAdapter()Landroid/support/v7/widget/RecyclerView$Adapter;

    move-result-object v0

    const/4 v1, 0x0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    if-nez v0, :cond_1

    goto :goto_1

    :cond_1
    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;->setLevelDatas(Ljava/util/List;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    invoke-virtual {v0}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    goto :goto_2

    :cond_2
    :goto_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    if-nez v0, :cond_3

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mContext:Landroid/content/Context;

    new-instance v3, Lcom/android/camera/features/mimoji2/fragment/edit/a;

    invoke-direct {v3, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/a;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)V

    invoke-direct {v0, v2, v3}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;-><init>(Landroid/content/Context;Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ItfGvOnItemClickListener;)V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    :cond_3
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mLevelRecyleView:Landroid/support/v7/widget/RecyclerView;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    invoke-virtual {v0, v2}, Landroid/support/v7/widget/RecyclerView;->setAdapter(Landroid/support/v7/widget/RecyclerView$Adapter;)V

    :goto_2
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    const/4 v2, 0x1

    invoke-virtual {v0, v2}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;->setIsColorNeedNotify(Z)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeAdapter:Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter;

    if-nez v0, :cond_4

    new-instance v0, Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter;

    invoke-direct {v0, v1}, Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter;-><init>(Ljava/util/ArrayList;)V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeAdapter:Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeAdapter:Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter;

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/edit/b;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/b;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)V

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter;->setOnSelectListener(Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter$OnSelectListener;)V

    :cond_4
    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getConfigTypeList()Ljava/util/ArrayList;

    move-result-object v0

    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_3
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_7

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->queryAvatar()Lcom/arcsoft/avatar/AvatarEngine;

    move-result-object v4

    iget v5, v3, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;->configType:I

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v6

    invoke-virtual {v6}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getASAvatarConfigValue()Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;

    move-result-object v6

    iget v6, v6, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;->gender:I

    invoke-virtual {v4, v5, v6}, Lcom/arcsoft/avatar/AvatarEngine;->getConfig(II)Ljava/util/ArrayList;

    move-result-object v4

    sget-object v5, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "putConfigList:"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v7, v3, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;->configTypeDesc:Ljava/lang/String;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v7, ":"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v7, v3, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;->configType:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v5

    iget v6, v3, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;->configType:I

    invoke-virtual {v5, v6, v4}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->putConfigList(ILjava/util/ArrayList;)V

    iget v4, v3, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;->configType:I

    invoke-static {v4}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->filterTypeTitle(I)Z

    move-result v4

    if-eqz v4, :cond_5

    goto :goto_3

    :cond_5
    new-instance v4, Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;

    invoke-direct {v4}, Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;-><init>()V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v5

    iget v6, v3, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;->configType:I

    invoke-static {v5, v6}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->replaceTabTitle(Landroid/content/Context;I)Ljava/lang/String;

    move-result-object v5

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, ""

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Lcom/android/camera/features/mimoji2/widget/autoselectview/SelectItemBean;->setText(Ljava/lang/String;)V

    iget-object v5, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeView:Landroid/widget/TextView;

    invoke-virtual {v5}, Landroid/widget/TextView;->getPaint()Landroid/text/TextPaint;

    move-result-object v5

    invoke-virtual {v4}, Lcom/android/camera/features/mimoji2/widget/autoselectview/SelectItemBean;->getText()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/text/TextPaint;->measureText(Ljava/lang/String;)F

    move-result v5

    invoke-virtual {v4, v5}, Lcom/android/camera/features/mimoji2/widget/autoselectview/SelectItemBean;->setCurLength(F)V

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-lez v5, :cond_6

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v5

    sub-int/2addr v5, v2

    invoke-virtual {v1, v5}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;

    invoke-virtual {v5}, Lcom/android/camera/features/mimoji2/widget/autoselectview/SelectItemBean;->getCurTotalLength()F

    move-result v5

    invoke-virtual {v4}, Lcom/android/camera/features/mimoji2/widget/autoselectview/SelectItemBean;->getCurLength()F

    move-result v6

    add-float/2addr v5, v6

    goto :goto_4

    :cond_6
    invoke-virtual {v4}, Lcom/android/camera/features/mimoji2/widget/autoselectview/SelectItemBean;->getCurLength()F

    move-result v5

    :goto_4
    invoke-virtual {v4, v5}, Lcom/android/camera/features/mimoji2/widget/autoselectview/SelectItemBean;->setCurTotalLength(F)V

    const/4 v5, 0x0

    invoke-virtual {v4, v5}, Lcom/android/camera/features/mimoji2/widget/autoselectview/SelectItemBean;->setAlpha(I)V

    invoke-virtual {v4, v3}, Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;->setASAvatarConfigType(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;)V

    invoke-virtual {v1, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto/16 :goto_3

    :cond_7
    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-nez v0, :cond_8

    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    const-string v0, " initConfigList() size 0 error"

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_8
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeAdapter:Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter;

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter;->setDataList(Ljava/util/ArrayList;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeSelectView:Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectHorizontalView;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeAdapter:Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter;

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectHorizontalView;->setAdapter(Landroid/support/v7/widget/RecyclerView$Adapter;)V

    const/16 v0, 0xc9

    iget v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->fromTag:I

    if-ne v0, v1, :cond_9

    const/4 v0, 0x4

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->updateTitleState(I)V

    goto :goto_5

    :cond_9
    const/4 v0, 0x2

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->updateTitleState(I)V

    :goto_5
    return-void
.end method

.method private initMimojiEdit(Landroid/view/View;)V
    .locals 4

    const v0, 0x7f090152

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlMainLayout:Landroid/widget/RelativeLayout;

    const v0, 0x7f090162

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlNavigationlayout:Landroid/widget/RelativeLayout;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlNavigationlayout:Landroid/widget/RelativeLayout;

    invoke-virtual {v0, p0}, Landroid/widget/RelativeLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f090150

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout;

    invoke-virtual {v0, p0}, Landroid/widget/RelativeLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0900ce

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlAllEditContent:Landroid/widget/LinearLayout;

    const v0, 0x7f0901a2

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCreateEmoticonTextView:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCreateEmoticonTextView:Landroid/widget/TextView;

    invoke-virtual {v0, p0}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0901aa

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mSaveFinishTextView:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mSaveFinishTextView:Landroid/widget/TextView;

    invoke-virtual {v0, p0}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f09019f

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {v0, p0}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f09002c

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {v0, p0}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0900fa

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->setHandler(Landroid/os/Handler;)V

    const v0, 0x7f090120

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mOperateSelectLayout:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mOperateSelectLayout:Landroid/widget/LinearLayout;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeView:Landroid/widget/TextView;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    const v2, 0x7f0b006d

    const/4 v3, 0x0

    invoke-virtual {v0, v2, v3, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object v0

    const v2, 0x7f0901ad

    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeView:Landroid/widget/TextView;

    :cond_0
    const v0, 0x7f090104

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectHorizontalView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeSelectView:Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectHorizontalView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiTypeSelectView:Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectHorizontalView;

    invoke-virtual {v0}, Landroid/support/v7/widget/RecyclerView;->getItemAnimator()Landroid/support/v7/widget/RecyclerView$ItemAnimator;

    move-result-object v0

    const-wide/16 v2, 0x0

    invoke-virtual {v0, v2, v3}, Landroid/support/v7/widget/RecyclerView$ItemAnimator;->setChangeDuration(J)V

    const v0, 0x7f090041

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/support/v7/widget/RecyclerView;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mLevelRecyleView:Landroid/support/v7/widget/RecyclerView;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mLevelRecyleView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {p1, v1}, Landroid/view/ViewGroup;->setFocusable(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mLevelRecyleView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {p1}, Landroid/support/v7/widget/RecyclerView;->getLayoutManager()Landroid/support/v7/widget/RecyclerView$LayoutManager;

    move-result-object p1

    if-nez p1, :cond_1

    new-instance p1, Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mContext:Landroid/content/Context;

    const-string v1, "color_level"

    invoke-direct {p1, v0, v1}, Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Landroid/support/v7/widget/LinearLayoutManager;->setOrientation(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mLevelRecyleView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {v0, p1}, Landroid/support/v7/widget/RecyclerView;->setLayoutManager(Landroid/support/v7/widget/RecyclerView$LayoutManager;)V

    :cond_1
    new-instance p1, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mContext:Landroid/content/Context;

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$2;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$2;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)V

    invoke-direct {p1, v0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;-><init>(Landroid/content/Context;Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ItfGvOnItemClickListener;)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mLevelRecyleView:Landroid/support/v7/widget/RecyclerView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    invoke-virtual {p1, v0}, Landroid/support/v7/widget/RecyclerView;->setAdapter(Landroid/support/v7/widget/RecyclerView$Adapter;)V

    new-instance p1, Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;

    invoke-direct {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiPageChangeAnimManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiPageChangeAnimManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlAllEditContent:Landroid/widget/LinearLayout;

    const/4 v2, 0x2

    invoke-virtual {p1, v0, v1, p0, v2}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;->initView(Landroid/content/Context;Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;Landroid/widget/LinearLayout;I)V

    return-void
.end method

.method private resetData()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x6

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x10

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->resetData()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;->setIsColorNeedNotify(Z)V

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v0

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mContext:Landroid/content/Context;

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getSelectType()I

    move-result v3

    invoke-virtual {v0, v2, v3}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getSubConfigList(Landroid/content/Context;I)Ljava/util/concurrent/CopyOnWriteArrayList;

    move-result-object v0

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    invoke-virtual {v2, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;->setLevelDatas(Ljava/util/List;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->getIsRendering()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->setResetStopRender(Z)V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->draw(Z)V

    :goto_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    invoke-virtual {v0}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    sget-object v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "resetData   mEnterFromMimoji :"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    iget-boolean v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    if-eqz v1, :cond_1

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentConfigPath:Ljava/lang/String;

    goto :goto_1

    :cond_1
    sget-object p0, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TempOriginalConfigPath:Ljava/lang/String;

    :goto_1
    invoke-virtual {v0, p0}, Lcom/arcsoft/avatar/AvatarEngine;->loadConfig(Ljava/lang/String;)V

    return-void
.end method

.method private setupAvatar()V
    .locals 5

    sget-object v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    const-string v1, "setup avatar"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mSetupCompleted:Z

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->queryAvatar()Lcom/arcsoft/avatar/AvatarEngine;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    sget-object v2, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->PersonTemplatePath:Ljava/lang/String;

    invoke-virtual {v1, v2}, Lcom/arcsoft/avatar/AvatarEngine;->loadColorValue(Ljava/lang/String;)I

    iget-boolean v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    sget-object v2, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->PersonTemplatePath:Ljava/lang/String;

    invoke-virtual {v1, v2}, Lcom/arcsoft/avatar/AvatarEngine;->setTemplatePath(Ljava/lang/String;)V

    :cond_0
    new-instance v1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;

    invoke-direct {v1}, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;-><init>()V

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {v2, v1}, Lcom/arcsoft/avatar/AvatarEngine;->getConfigValue(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;)V

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    invoke-virtual {v2, v1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setASAvatarConfigValue(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;)V

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    invoke-virtual {v2, v1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setASAvatarConfigValueDefault(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;)V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    const v2, 0x3f59999a    # 0.85f

    invoke-virtual {v1, v0, v2}, Lcom/arcsoft/avatar/AvatarEngine;->setRenderScene(ZF)V

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mContext:Landroid/content/Context;

    const/16 v3, 0x1f4

    const-string v4, "MimojiEdit"

    invoke-direct {v1, v4, v3, v3, v2}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;-><init>(Ljava/lang/String;IILandroid/content/Context;)V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {v1}, Ljava/lang/Thread;->start()V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->waitUntilReady()V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mHandler:Landroid/os/Handler;

    invoke-virtual {v1, v2}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->setUpdateHandler(Landroid/os/Handler;)V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    if-eqz v1, :cond_1

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {v1, v2}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;->setRenderThread(Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;)V

    :cond_1
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->initUpdatePara()V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    sget-object v2, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TempOriginalConfigPath:Ljava/lang/String;

    invoke-virtual {v1, v2}, Lcom/arcsoft/avatar/AvatarEngine;->saveConfig(Ljava/lang/String;)I

    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mSetupCompleted:Z

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    invoke-virtual {v1, v0}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->setIsStopRenderForce(Z)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->setStopRender(Z)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xf6

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;

    if-eqz p0, :cond_2

    invoke-interface {p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;->onMimojiInitFinish()V

    :cond_2
    return-void
.end method

.method private showAlertDialog(I)V
    .locals 4

    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsShowDialog:Z

    if-eqz v0, :cond_0

    return-void

    :cond_0
    const/4 v0, 0x1

    const/4 v1, -0x1

    if-eq p1, v0, :cond_3

    const/4 v2, 0x3

    if-eq p1, v2, :cond_2

    const/4 v2, 0x4

    if-eq p1, v2, :cond_1

    const/4 v2, 0x5

    if-eq p1, v2, :cond_1

    move v2, v1

    goto :goto_0

    :cond_1
    const v2, 0x7f0f018a

    goto :goto_0

    :cond_2
    const v2, 0x7f0f018b

    goto :goto_0

    :cond_3
    const v2, 0x7f0f018d

    :goto_0
    if-ne v2, v1, :cond_4

    return-void

    :cond_4
    new-instance v1, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v3

    invoke-direct {v1, v3}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    const v2, 0x7f0f0177

    new-instance v3, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;

    invoke-direct {v3, p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;I)V

    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    const p1, 0x7f0f016e

    new-instance v2, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$6;

    invoke-direct {v2, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$6;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)V

    invoke-virtual {v1, p1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsShowDialog:Z

    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentAlertDialog:Landroid/app/AlertDialog;

    return-void
.end method


# virtual methods
.method public synthetic Ea()V
    .locals 2

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditState:Z

    iget v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->fromTag:I

    const/16 v1, 0xcb

    if-ne v0, v1, :cond_0

    const/4 v0, 0x3

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->updateTitleState(I)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiPageChangeAnimManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;

    const/4 v0, 0x4

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;->resetLayoutPosition(I)V

    goto :goto_0

    :cond_0
    const/4 v0, 0x5

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->updateTitleState(I)V

    :goto_0
    return-void
.end method

.method public synthetic Fa()V
    .locals 2

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa0

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    if-eqz v0, :cond_0

    const/16 v1, 0x13

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->delegateEvent(I)V

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMode(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditViewLayout:Landroid/view/View;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mOperateSelectLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlMainLayout:Landroid/widget/RelativeLayout;

    invoke-virtual {v0, v1}, Landroid/widget/RelativeLayout;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlNavigationlayout:Landroid/widget/RelativeLayout;

    invoke-virtual {v0, v1}, Landroid/widget/RelativeLayout;->setVisibility(I)V

    :cond_1
    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->clear()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsStartEdit:Z

    return-void
.end method

.method public synthetic a(Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;I)V
    .locals 3

    sget-object v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onSelectListener position  : "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {v0, p2}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiPageChangeAnimManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;

    invoke-virtual {p2}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;->updateLayoutPosition()V

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditLevelListAdapter2:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    if-eqz p2, :cond_0

    const/4 v0, 0x1

    invoke-virtual {p2, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;->setIsColorNeedNotify(Z)V

    :cond_0
    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;->getASAvatarConfigType()Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;

    move-result-object p1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p2

    const/16 v0, 0xf7

    invoke-virtual {p2, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p2

    check-cast p2, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz p2, :cond_1

    if-eqz p1, :cond_1

    iget p1, p1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;->configType:I

    invoke-interface {p2, p1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->onTypeConfigSelect(I)V

    :cond_1
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mLevelRecyleView:Landroid/support/v7/widget/RecyclerView;

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Landroid/support/v7/widget/RecyclerView;->scrollToPosition(I)V

    return-void
.end method

.method public createEmoticonPicture(Ljava/util/List;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/arcsoft/avatar/emoticon/EmoInfo;",
            ">;)V"
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Ljava/lang/Thread;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->drawForEmoticonPicture(Ljava/util/List;)V

    return-void

    :cond_1
    :goto_0
    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    const-string p1, "createEmoticonVideo mRenderThread null "

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public createEmoticonThumbnail()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Ljava/lang/Thread;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->drawForEmoticonThumbnail()V

    return-void

    :cond_1
    :goto_0
    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    const-string v0, "createEmoticonVideo mRenderThread null "

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public createEmoticonVideo(Ljava/util/List;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/arcsoft/avatar/emoticon/EmoInfo;",
            ">;)V"
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Ljava/lang/Thread;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->recordForEmoticonVideo(Ljava/util/List;)V

    return-void

    :cond_1
    :goto_0
    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    const-string p1, "createEmoticonVideo mRenderThread null "

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public directlyEnterEditMode(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;I)V
    .locals 3

    sget-object v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "configPath = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentConfigPath:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mPackPath:Ljava/lang/String;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mPopSaveDeletePath:Ljava/lang/String;

    iget-object p1, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentConfigPath:Ljava/lang/String;

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsStartEdit:Z

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    const/4 v1, 0x6

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMode(I)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa2

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    if-eqz v0, :cond_0

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;->forceSwitchFront()Z

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xf6

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;

    if-eqz v0, :cond_1

    invoke-interface {v0, p1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;->setDisableSingleTapUp(Z)V

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    const/4 v1, 0x2

    new-array v1, v1, [I

    fill-array-data v1, :array_0

    invoke-interface {v0, p1, v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->disableMenuItem(Z[I)V

    invoke-virtual {p0, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->startMimojiEdit(I)V

    return-void

    :array_0
    .array-data 4
        0xc5
        0xc1
    .end array-data
.end method

.method public getFragmentInto()I
    .locals 0

    const/16 p0, 0xfa2

    return p0
.end method

.method protected getLayoutResourceId()I
    .locals 0

    const p0, 0x7f0b0042

    return p0
.end method

.method public goBack(ZZ)V
    .locals 2

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->clear()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->releaseRender()V

    :cond_0
    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xf6

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;

    if-eqz v0, :cond_1

    xor-int/lit8 v1, p1, 0x1

    invoke-interface {v0, p2, v1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;->backToPreview(ZZ)V

    if-eqz p1, :cond_1

    invoke-interface {v0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;->onMimojiCreate()V

    :cond_1
    if-eqz p2, :cond_2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 p2, 0xf8

    invoke-virtual {p1, p2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;

    if-eqz p1, :cond_2

    invoke-interface {p1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;->refreshMimojiList()I

    :cond_2
    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsStartEdit:Z

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditViewLayout:Landroid/view/View;

    const/16 p2, 0x8

    if-eqz p1, :cond_3

    invoke-virtual {p1, p2}, Landroid/view/View;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mOperateSelectLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1, p2}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlAllEditContent:Landroid/widget/LinearLayout;

    invoke-virtual {p1, p2}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_3
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    invoke-virtual {p1, p2}, Landroid/view/TextureView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->quit()V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getFragmentManager()Landroid/support/v4/app/FragmentManager;

    move-result-object p0

    sget-object p1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    invoke-static {p0, p1}, Lcom/android/camera/fragment/FragmentUtils;->removeFragmentByTag(Landroid/support/v4/app/FragmentManager;Ljava/lang/String;)Z

    return-void
.end method

.method protected initView(Landroid/view/View;)V
    .locals 0

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mContext:Landroid/content/Context;

    return-void
.end method

.method public onBackEvent(I)Z
    .locals 3

    const/4 v0, 0x0

    const/4 v1, 0x1

    if-eq p1, v1, :cond_0

    goto :goto_0

    :cond_0
    iget-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsStartEdit:Z

    if-eqz p1, :cond_1

    const/4 p1, 0x4

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->showAlertDialog(I)V

    return v1

    :cond_1
    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->fromTag:I

    const/16 v2, 0xcb

    if-ne p1, v2, :cond_2

    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    const/4 v2, 0x2

    if-ne p1, v2, :cond_2

    invoke-direct {p0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->showAlertDialog(I)V

    return v1

    :cond_2
    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    const/4 v2, 0x6

    if-ne p1, v2, :cond_3

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->goBack(ZZ)V

    return v1

    :cond_3
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditViewLayout:Landroid/view/View;

    if-eqz p1, :cond_4

    invoke-virtual {p1}, Landroid/view/View;->getVisibility()I

    move-result p1

    const/16 v2, 0x8

    if-eq p1, v2, :cond_4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiEdit()Z

    move-result p1

    if-eqz p1, :cond_4

    const/4 p1, 0x3

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->showAlertDialog(I)V

    return v1

    :cond_4
    :goto_0
    return v0
.end method

.method public onClick(Landroid/view/View;)V
    .locals 4

    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mSetupCompleted:Z

    if-eqz v0, :cond_5

    invoke-static {}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->getInstance()Lcom/android/camera/features/mimoji2/utils/ClickCheck2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->checkClickable()Z

    move-result v0

    if-nez v0, :cond_0

    goto/16 :goto_0

    :cond_0
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result p1

    const/4 v0, 0x0

    const/4 v1, 0x1

    sparse-switch p1, :sswitch_data_0

    goto/16 :goto_0

    :sswitch_0
    invoke-virtual {p0, v0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->goBack(ZZ)V

    goto/16 :goto_0

    :sswitch_1
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->showEmoticon()V

    goto :goto_0

    :sswitch_2
    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->fromTag:I

    const/16 v2, 0xc9

    if-ne p1, v2, :cond_1

    const/4 p1, 0x5

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->showAlertDialog(I)V

    goto :goto_0

    :cond_1
    const/16 v2, 0xcb

    const/4 v3, 0x2

    if-ne p1, v2, :cond_2

    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    if-ne p1, v3, :cond_2

    invoke-direct {p0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->showAlertDialog(I)V

    goto :goto_0

    :cond_2
    iget-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditState:Z

    if-eqz p1, :cond_5

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEditState:Z

    invoke-virtual {p0, v3}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->updateTitleState(I)V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->resetData()V

    const-string p0, "edit_reset"

    const-string p1, "edit"

    invoke-static {p0, p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiClick(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    :sswitch_3
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    invoke-virtual {p1, v1}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->setSaveConfigThum(Z)V

    iget-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsStartEdit:Z

    if-eqz p1, :cond_4

    new-instance p1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;

    invoke-direct {p1}, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;-><init>()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {v0, p1}, Lcom/arcsoft/avatar/AvatarEngine;->getConfigValue(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;)V

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getMimojiConfigValue(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;)Ljava/util/Map;

    move-result-object p1

    const-string v0, "attr_event_name"

    const-string v1, "click"

    invoke-interface {p1, v0, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    const-string v0, "key_edit_save"

    const-string v1, "attr_mimoji_edit_count"

    if-eqz p0, :cond_3

    const-string p0, "second"

    invoke-interface {p1, v1, p0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    invoke-static {v0, p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiSavePara(Ljava/lang/String;Ljava/util/Map;)V

    goto :goto_0

    :cond_3
    const-string p0, "first"

    invoke-interface {p1, v1, p0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    invoke-static {v0, p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiSavePara(Ljava/lang/String;Ljava/util/Map;)V

    goto :goto_0

    :cond_4
    const-string p0, "preview_mid_save"

    const-string p1, "preview_mid"

    invoke-static {p0, p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiClick(Ljava/lang/String;Ljava/lang/String;)V

    :cond_5
    :goto_0
    return-void

    :sswitch_data_0
    .sparse-switch
        0x7f09002c -> :sswitch_3
        0x7f09019f -> :sswitch_2
        0x7f0901a2 -> :sswitch_1
        0x7f0901aa -> :sswitch_0
    .end sparse-switch
.end method

.method public onDestroy()V
    .locals 0

    invoke-super {p0}, Landroid/support/v4/app/Fragment;->onDestroy()V

    return-void
.end method

.method public onDeviceRotationChange(I)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    if-eqz p0, :cond_0

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->onDeviceRotationChange(I)V

    :cond_0
    return-void
.end method

.method public onResume()V
    .locals 2

    invoke-static {}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->getInstance()Lcom/android/camera/features/mimoji2/utils/ClickCheck2;

    move-result-object v0

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->setForceDisabled(Z)V

    invoke-super {p0}, Lcom/android/camera/fragment/BaseFragment;->onResume()V

    return-void
.end method

.method public onStop()V
    .locals 2

    invoke-static {}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->getInstance()Lcom/android/camera/features/mimoji2/utils/ClickCheck2;

    move-result-object v0

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->setForceDisabled(Z)V

    invoke-super {p0}, Lcom/android/camera/fragment/BaseFragment;->onStop()V

    return-void
.end method

.method public onTypeConfigSelect(I)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setIsColorSelected(Z)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    invoke-virtual {v0, p1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setSelectType(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->getIsRendering()Z

    move-result p1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mHandler:Landroid/os/Handler;

    invoke-virtual {p1}, Landroid/os/Handler;->obtainMessage()Landroid/os/Message;

    move-result-object p1

    const/4 v0, 0x6

    iput v0, p1, Landroid/os/Message;->what:I

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mHandler:Landroid/os/Handler;

    invoke-virtual {p0, p1}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    const/4 p1, 0x1

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->setStopRender(Z)V

    :goto_0
    return-void
.end method

.method public provideAnimateElement(ILjava/util/List;I)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I",
            "Ljava/util/List<",
            "Lio/reactivex/Completable;",
            ">;I)V"
        }
    .end annotation

    invoke-super {p0, p1, p2, p3}, Lcom/android/camera/fragment/BaseFragment;->provideAnimateElement(ILjava/util/List;I)V

    sget-object p1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "provideAnimateElement, animateInElements"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string p2, "resetType = "

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditViewLayout:Landroid/view/View;

    if-eqz p1, :cond_1

    invoke-virtual {p1}, Landroid/view/View;->getVisibility()I

    move-result p1

    if-nez p1, :cond_1

    const/4 p1, 0x3

    if-ne p3, p1, :cond_1

    sget-object p1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    const-string p2, "mimoji edit timeout"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p1, 0x0

    invoke-virtual {p0, p1, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->goBack(ZZ)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p2

    invoke-virtual {p2}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object p2

    invoke-virtual {p2}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->reset()V

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentAlertDialog:Landroid/app/AlertDialog;

    if-eqz p2, :cond_0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsShowDialog:Z

    invoke-virtual {p2}, Landroid/app/AlertDialog;->dismiss()V

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentAlertDialog:Landroid/app/AlertDialog;

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 p2, 0xa0

    invoke-virtual {p1, p2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->getAnimationComposite()Lcom/android/camera/animation/AnimationComposite;

    move-result-object p1

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->getFragmentInto()I

    move-result p0

    invoke-virtual {p1, p0}, Lcom/android/camera/animation/AnimationComposite;->remove(I)V

    :cond_1
    return-void
.end method

.method public quitCoverEmoticon()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->quitEmoticonVideo()V

    :cond_0
    return-void
.end method

.method protected register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->registerBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xf7

    invoke-virtual {p1, v0, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method

.method public releaseRender()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->setIsStopRenderForce(Z)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$3;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$3;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)V

    invoke-virtual {v0, v1}, Lcom/android/camera/ui/GLTextureView;->queueEvent(Ljava/lang/Runnable;)V

    return-void
.end method

.method public requestRender(Z)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    if-eqz v0, :cond_0

    invoke-virtual {v0, p1}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->setStopRender(Z)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    invoke-virtual {p0}, Lcom/android/camera/ui/GLTextureView;->requestRender()V

    :cond_0
    return-void
.end method

.method public resetClickEnable(Z)V
    .locals 0

    invoke-static {}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->getInstance()Lcom/android/camera/features/mimoji2/utils/ClickCheck2;

    move-result-object p0

    xor-int/lit8 p1, p1, 0x1

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->setForceDisabled(Z)V

    return-void
.end method

.method public resetConfig()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    if-nez v0, :cond_0

    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    const-string v0, "resetConfig view NULL, UI need init "

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->setStopRender(Z)V

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatarEngineManager2:Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->queryAvatar()Lcom/arcsoft/avatar/AvatarEngine;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    iget-boolean v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsStartEdit:Z

    if-eqz v1, :cond_1

    sget-object v1, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TempEditConfigPath:Ljava/lang/String;

    goto :goto_0

    :cond_1
    sget-object v1, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TempOriginalConfigPath:Ljava/lang/String;

    :goto_0
    invoke-virtual {v0, v1}, Lcom/arcsoft/avatar/AvatarEngine;->loadConfig(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    const v1, 0x3f59999a    # 0.85f

    const/4 v2, 0x0

    invoke-virtual {v0, v2, v1}, Lcom/arcsoft/avatar/AvatarEngine;->setRenderScene(ZF)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    invoke-virtual {v0, v2}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->setIsStopRenderForce(Z)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    invoke-virtual {p0, v2}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->setStopRender(Z)V

    return-void
.end method

.method public showEmoticon()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mContext:Landroid/content/Context;

    if-nez v0, :cond_0

    return-void

    :cond_0
    check-cast v0, Landroid/app/Activity;

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/edit/c;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/c;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)V

    invoke-virtual {v0, v1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method

.method public startMimojiEdit(I)V
    .locals 4

    sget-object v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "startMimojiEdit\uff1a"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mSetupCompleted:Z

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditViewLayout:Landroid/view/View;

    if-nez v1, :cond_0

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getView()Landroid/view/View;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditViewLayout:Landroid/view/View;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditViewLayout:Landroid/view/View;

    invoke-direct {p0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->initMimojiEdit(Landroid/view/View;)V

    :cond_0
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mLevelRecyleView:Landroid/support/v7/widget/RecyclerView;

    if-eqz v1, :cond_2

    invoke-virtual {v1}, Landroid/view/ViewGroup;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v1

    check-cast v1, Landroid/widget/LinearLayout$LayoutParams;

    if-eqz v1, :cond_2

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-static {v2}, Lcom/android/camera/Util;->isFullScreenNavBarHidden(Landroid/content/Context;)Z

    move-result v2

    if-nez v2, :cond_1

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x7f07014a

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v2

    iput v2, v1, Landroid/widget/LinearLayout$LayoutParams;->bottomMargin:I

    goto :goto_0

    :cond_1
    iput v0, v1, Landroid/widget/LinearLayout$LayoutParams;->bottomMargin:I

    :cond_2
    :goto_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xa0

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->getAnimationComposite()Lcom/android/camera/animation/AnimationComposite;

    move-result-object v1

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->getFragmentInto()I

    move-result v2

    invoke-virtual {v1, v2, p0}, Lcom/android/camera/animation/AnimationComposite;->put(ILcom/android/camera/animation/AnimationDelegate$AnimationResource;)V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlMainLayout:Landroid/widget/RelativeLayout;

    invoke-virtual {v1, v0}, Landroid/widget/RelativeLayout;->setVisibility(I)V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlNavigationlayout:Landroid/widget/RelativeLayout;

    invoke-virtual {v1, v0}, Landroid/widget/RelativeLayout;->setVisibility(I)V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditViewLayout:Landroid/view/View;

    invoke-virtual {v1, v0}, Landroid/view/View;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;->setStopRender(Z)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Landroid/view/TextureView;->setVisibility(I)V

    iput p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->fromTag:I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditViewLayout:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getViewTreeObserver()Landroid/view/ViewTreeObserver;

    move-result-object v0

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$1;

    invoke-direct {v1, p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$1;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;I)V

    invoke-virtual {v0, v1}, Landroid/view/ViewTreeObserver;->addOnGlobalLayoutListener(Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;)V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->setupAvatar()V

    const/16 v0, 0xcc

    if-eq p1, v0, :cond_3

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->initConfigList()V

    goto :goto_2

    :cond_3
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRenderThread:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;

    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mEnterFromMimoji:Z

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentConfigPath:Ljava/lang/String;

    goto :goto_1

    :cond_4
    sget-object v0, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TempOriginalConfigPath:Ljava/lang/String;

    :goto_1
    invoke-virtual {p1, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRenderThread2;->initAvatar(Ljava/lang/String;)V

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->showEmoticon()V

    :goto_2
    return-void
.end method

.method protected unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->unRegisterBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xf7

    invoke-virtual {p1, v0, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object p1

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMode(I)V

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mIsStartEdit:Z

    return-void
.end method

.method public updateTitleState(I)V
    .locals 6

    const/4 v0, 0x2

    const v1, 0x7f0f01aa

    const v2, 0x7f060066

    const/4 v3, 0x1

    const/16 v4, 0x8

    const/4 v5, 0x0

    if-eq p1, v0, :cond_7

    const/4 v0, 0x3

    if-eq p1, v0, :cond_5

    const/4 v0, 0x4

    if-eq p1, v0, :cond_3

    const/4 v0, 0x5

    if-eq p1, v0, :cond_1

    const/4 v0, 0x6

    if-eq p1, v0, :cond_0

    goto/16 :goto_0

    :cond_0
    iput v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v4}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v4}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiPageChangeAnimManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mMimojiEditGLTextureView2:Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlAllEditContent:Landroid/widget/LinearLayout;

    invoke-virtual {p1, v1, v2, v3, v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiPageChangeAnimManager2;->initView(Landroid/content/Context;Lcom/android/camera/features/mimoji2/widget/MimojiEditGLTextureView2;Landroid/widget/LinearLayout;I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlAllEditContent:Landroid/widget/LinearLayout;

    invoke-virtual {p1, v4}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mOperateSelectLayout:Landroid/widget/LinearLayout;

    if-eqz p0, :cond_9

    invoke-virtual {p0, v5}, Landroid/widget/LinearLayout;->setVisibility(I)V

    goto/16 :goto_0

    :cond_1
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mOperateSelectLayout:Landroid/widget/LinearLayout;

    if-eqz p1, :cond_2

    invoke-virtual {p1, v4}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_2
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p0, v3}, Landroid/widget/TextView;->setClickable(Z)V

    goto/16 :goto_0

    :cond_3
    iput v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlAllEditContent:Landroid/widget/LinearLayout;

    invoke-virtual {p1, v5}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mOperateSelectLayout:Landroid/widget/LinearLayout;

    if-eqz p1, :cond_4

    invoke-virtual {p1, v4}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_4
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v5}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v5}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v3}, Landroid/widget/TextView;->setClickable(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v2, 0x7f0f016e

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f060068

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p0, v5}, Landroid/widget/TextView;->setClickable(Z)V

    goto/16 :goto_0

    :cond_5
    iput v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlAllEditContent:Landroid/widget/LinearLayout;

    invoke-virtual {p1, v5}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mOperateSelectLayout:Landroid/widget/LinearLayout;

    if-eqz p1, :cond_6

    invoke-virtual {p1, v4}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_6
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v5}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v5}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v3}, Landroid/widget/TextView;->setClickable(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v3}, Landroid/widget/TextView;->setClickable(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v3}, Landroid/widget/TextView;->setClickable(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v0, 0x7f0f01a9

    invoke-virtual {p0, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p1, p0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    :cond_7
    iput v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mCurrentTopPannelState:I

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mRlAllEditContent:Landroid/widget/LinearLayout;

    invoke-virtual {p1, v5}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mOperateSelectLayout:Landroid/widget/LinearLayout;

    if-eqz p1, :cond_8

    invoke-virtual {p1, v4}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_8
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v5}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v4, 0x7f0f01a7

    invoke-virtual {v0, v4}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mBackTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v3}, Landroid/widget/TextView;->setClickable(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v5}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->mConfirmTextView:Landroid/widget/TextView;

    invoke-virtual {p0, v3}, Landroid/widget/TextView;->setClickable(Z)V

    :cond_9
    :goto_0
    return-void
.end method
