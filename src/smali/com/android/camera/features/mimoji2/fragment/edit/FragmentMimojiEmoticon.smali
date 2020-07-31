.class public Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;
.super Lcom/android/camera/fragment/BaseFragment;
.source "FragmentMimojiEmoticon.java"

# interfaces
.implements Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2$MimojiEmoticon;
.implements Landroid/view/View$OnClickListener;
.implements Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;


# static fields
.field private static final FRAGMENT_INFO:I = 0xfa3

.field public static final TAG:Ljava/lang/String; = "FragmentMimojiEmoticon"

.field private static final THUMBNAIL_TOTAL_COUNT:I = 0x6


# instance fields
.field private mEmoticonRecyclerView:Landroid/support/v7/widget/RecyclerView;

.field private mIsNeedShare:Z

.field private mIsRTL:Z

.field private mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

.field private mProgressDialog:Lmiui/app/f;

.field private mSaveDialog:Landroid/app/AlertDialog;

.field private mSelectBtn:Landroid/widget/ImageView;

.field private mSelectedEmoInfoList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/arcsoft/avatar/emoticon/EmoInfo;",
            ">;"
        }
    .end annotation
.end field

.field private mShareEmoInfoList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Landroid/net/Uri;",
            ">;"
        }
    .end annotation
.end field

.field private mThumbnailPaint:Landroid/graphics/Paint;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/BaseFragment;-><init>()V

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mShareEmoInfoList:Ljava/util/ArrayList;

    return-void
.end method

.method static synthetic Ga()V
    .locals 3

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
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xf7

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    invoke-interface {v0, v1, v1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->goBack(ZZ)V

    goto :goto_0

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v2, 0xf6

    invoke-virtual {v0, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;

    if-eqz v0, :cond_2

    const/4 v2, 0x1

    invoke-interface {v0, v1, v2}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;->backToPreview(ZZ)V

    :cond_2
    :goto_0
    return-void
.end method

.method static synthetic a([ZLandroid/content/DialogInterface;IZ)V
    .locals 0

    const/4 p1, 0x0

    aput-boolean p3, p0, p1

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mIsRTL:Z

    return p0
.end method

.method private checkInitThumbnaiFinish()Z
    .locals 1

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getItemCount()I

    move-result p0

    const/4 v0, 0x6

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private deleteEmoticonCache()V
    .locals 0

    sget-object p0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_MP4_CACHE_DIR:Ljava/lang/String;

    invoke-static {p0}, Lcom/android/camera/module/impl/component/FileUtils;->deleteFile(Ljava/lang/String;)Z

    sget-object p0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_GIF_CACHE_DIR:Ljava/lang/String;

    invoke-static {p0}, Lcom/android/camera/module/impl/component/FileUtils;->deleteFile(Ljava/lang/String;)Z

    sget-object p0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_JPEG_CACHE_DIR:Ljava/lang/String;

    invoke-static {p0}, Lcom/android/camera/module/impl/component/FileUtils;->deleteFile(Ljava/lang/String;)Z

    return-void
.end method

.method private dissmissDialog()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mProgressDialog:Lmiui/app/f;

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/app/Dialog;->dismiss()V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mProgressDialog:Lmiui/app/f;

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSaveDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSaveDialog:Landroid/app/AlertDialog;

    :cond_1
    return-void
.end method

.method private getEmoticonThumbnail()V
    .locals 1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xf7

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz p0, :cond_0

    invoke-interface {p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->createEmoticonThumbnail()V

    goto :goto_0

    :cond_0
    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    const-string v0, "mimoji void initEmoticon[] null"

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    return-void
.end method

.method private saveEmoticonGif(Z)V
    .locals 7

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mIsNeedShare:Z

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    if-eqz v1, :cond_0

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getItemCount()I

    move-result v1

    if-nez v1, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->backToPreview()V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    const-string p1, "\u72b6\u6001\u5f02\u5e38"

    invoke-static {p0, p1}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    return-void

    :cond_0
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_1
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_2

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;

    invoke-virtual {v2}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->isSelected()Z

    move-result v3

    if-eqz v3, :cond_1

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v2}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->getEmoInfo()Lcom/arcsoft/avatar/emoticon/EmoInfo;

    move-result-object v2

    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    :cond_2
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    if-nez v1, :cond_3

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    const-string p1, "\u8bf7\u9009\u62e9\u81f3\u5c11\u4e00\u4e2a\u8868\u60c5"

    invoke-static {p0, p1}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    return-void

    :cond_3
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    new-instance v2, Ljava/io/File;

    sget-object v3, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_GIF_CACHE_DIR:Ljava/lang/String;

    invoke-direct {v2, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v3

    if-eqz v3, :cond_6

    invoke-virtual {v2}, Ljava/io/File;->isDirectory()Z

    move-result v3

    if-eqz v3, :cond_6

    invoke-virtual {v2}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v2

    array-length v3, v2

    :goto_1
    if-ge v0, v3, :cond_6

    aget-object v4, v2, v0

    invoke-virtual {v4}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Lcom/android/camera/module/impl/component/FileUtils;->getFileName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v5

    :cond_4
    :goto_2
    invoke-interface {v5}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_5

    invoke-interface {v5}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Lcom/arcsoft/avatar/emoticon/EmoInfo;

    invoke-virtual {v6}, Lcom/arcsoft/avatar/emoticon/EmoInfo;->getEmoName()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v6, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_4

    invoke-interface {v5}, Ljava/util/Iterator;->remove()V

    goto :goto_2

    :cond_5
    add-int/lit8 v0, v0, 0x1

    goto :goto_1

    :cond_6
    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-nez v0, :cond_7

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->coverEmoticonSuccess()V

    return-void

    :cond_7
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v2, 0xf7

    invoke-virtual {v0, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz v0, :cond_9

    const-string v2, "\u5f00\u59cb\u751f\u6210\u8868\u60c5\u5305"

    invoke-direct {p0, v2}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->showProgressDialog(Ljava/lang/String;)V

    if-nez p1, :cond_8

    invoke-interface {v0, v1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->createEmoticonVideo(Ljava/util/List;)V

    goto :goto_3

    :cond_8
    invoke-interface {v0, v1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->createEmoticonPicture(Ljava/util/List;)V

    goto :goto_3

    :cond_9
    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    const-string p1, "mimoji void saveEmoticonGif[] null"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :goto_3
    return-void
.end method

.method private shareEmoticonGif()V
    .locals 7

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mShareEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mIsNeedShare:Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getItemCount()I

    move-result v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->backToPreview()V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    const-string v0, "\u72b6\u6001\u5f02\u5e38"

    invoke-static {p0, v0}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_1
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->isSelected()Z

    move-result v2

    if-eqz v2, :cond_1

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->getEmoInfo()Lcom/arcsoft/avatar/emoticon/EmoInfo;

    move-result-object v1

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    :cond_2
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-nez v0, :cond_3

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    const-string v0, "\u8bf7\u9009\u62e9\u81f3\u5c11\u4e00\u4e2a\u8868\u60c5"

    invoke-static {p0, v0}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    return-void

    :cond_3
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    new-instance v1, Ljava/io/File;

    sget-object v2, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_GIF_CACHE_DIR:Ljava/lang/String;

    invoke-direct {v1, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v2

    if-eqz v2, :cond_6

    invoke-virtual {v1}, Ljava/io/File;->isDirectory()Z

    move-result v2

    if-eqz v2, :cond_6

    invoke-virtual {v1}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v1

    array-length v2, v1

    const/4 v3, 0x0

    :goto_1
    if-ge v3, v2, :cond_6

    aget-object v4, v1, v3

    invoke-virtual {v4}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Lcom/android/camera/module/impl/component/FileUtils;->getFileName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v5

    :cond_4
    :goto_2
    invoke-interface {v5}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_5

    invoke-interface {v5}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Lcom/arcsoft/avatar/emoticon/EmoInfo;

    invoke-virtual {v6}, Lcom/arcsoft/avatar/emoticon/EmoInfo;->getEmoName()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v6, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_4

    invoke-interface {v5}, Ljava/util/Iterator;->remove()V

    goto :goto_2

    :cond_5
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    :cond_6
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v1

    if-nez v1, :cond_7

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->coverEmoticonSuccess()V

    return-void

    :cond_7
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xf7

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz v1, :cond_8

    const-string v2, "\u5f00\u59cb\u751f\u6210\u8868\u60c5\u5305"

    invoke-direct {p0, v2}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->showProgressDialog(Ljava/lang/String;)V

    invoke-interface {v1, v0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->createEmoticonVideo(Ljava/util/List;)V

    :cond_8
    return-void
.end method

.method private showProgressDialog(Ljava/lang/String;)V
    .locals 2

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->dissmissDialog()V

    return-void

    :cond_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mProgressDialog:Lmiui/app/f;

    if-nez v0, :cond_2

    new-instance v0, Lmiui/app/f;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v1

    invoke-direct {v0, v1}, Lmiui/app/f;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mProgressDialog:Lmiui/app/f;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mProgressDialog:Lmiui/app/f;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/app/Dialog;->setCancelable(Z)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mProgressDialog:Lmiui/app/f;

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/edit/d;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/d;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;)V

    invoke-virtual {v0, v1}, Landroid/app/Dialog;->setOnKeyListener(Landroid/content/DialogInterface$OnKeyListener;)V

    :cond_2
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mProgressDialog:Lmiui/app/f;

    invoke-virtual {v0, p1}, Lmiui/app/f;->setMessage(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mProgressDialog:Lmiui/app/f;

    if-eqz p1, :cond_3

    invoke-virtual {p1}, Landroid/app/Dialog;->isShowing()Z

    move-result p1

    if-nez p1, :cond_3

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mProgressDialog:Lmiui/app/f;

    invoke-virtual {p0}, Landroid/app/Dialog;->show()V

    :cond_3
    return-void
.end method

.method private showSaveDialog()V
    .locals 4

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->dissmissDialog()V

    const/4 v0, 0x1

    new-array v1, v0, [Z

    const/4 v2, 0x0

    aput-boolean v0, v1, v2

    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v3

    invoke-direct {v0, v3}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const-string v3, "\u4fdd\u5b58\u8868\u60c5\u5305"

    invoke-virtual {v0, v3}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    const-string v2, "\u540c\u65f6\u4fdd\u5b58\u4e3a\u56fe\u7247"

    filled-new-array {v2}, [Ljava/lang/String;

    move-result-object v2

    new-instance v3, Lcom/android/camera/features/mimoji2/fragment/edit/m;

    invoke-direct {v3, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/m;-><init>([Z)V

    invoke-virtual {v0, v2, v1, v3}, Landroid/app/AlertDialog$Builder;->setMultiChoiceItems([Ljava/lang/CharSequence;[ZLandroid/content/DialogInterface$OnMultiChoiceClickListener;)Landroid/app/AlertDialog$Builder;

    new-instance v2, Lcom/android/camera/features/mimoji2/fragment/edit/i;

    invoke-direct {v2, p0, v1}, Lcom/android/camera/features/mimoji2/fragment/edit/i;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;[Z)V

    const-string v1, "\u4fdd\u5b58"

    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    const v1, 0x7f0f016e

    new-instance v2, Lcom/android/camera/features/mimoji2/fragment/edit/h;

    invoke-direct {v2, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/h;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;)V

    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSaveDialog:Landroid/app/AlertDialog;

    return-void
.end method


# virtual methods
.method public synthetic Ha()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->dissmissDialog()V

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object p0

    const-string v0, "\u8868\u60c5\u5305\u751f\u6210\u5931\u8d25"

    invoke-static {p0, v0}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    return-void
.end method

.method public synthetic a(Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;I)V
    .locals 1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->isSelected()Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    invoke-virtual {p1, v0}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->setSelected(Z)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->setSelectState(Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;I)V

    return-void
.end method

.method public synthetic a(Lcom/arcsoft/avatar/emoticon/EmoInfo;I)V
    .locals 6

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mThumbnailPaint:Landroid/graphics/Paint;

    const/4 v1, 0x1

    if-nez v0, :cond_0

    new-instance v0, Landroid/graphics/Paint;

    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mThumbnailPaint:Landroid/graphics/Paint;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mThumbnailPaint:Landroid/graphics/Paint;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    :cond_0
    sget-object v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    const-string v2, "mimoji void updateEmoticonThumbnailProgress[num, emoInfo]"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    invoke-virtual {p1}, Lcom/arcsoft/avatar/emoticon/EmoInfo;->getThumbnailData()[B

    move-result-object v3

    if-eqz v3, :cond_2

    sget-object v3, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->CONFIG_EMO_THUM_SIZE:Landroid/util/Size;

    invoke-virtual {v3}, Landroid/util/Size;->getWidth()I

    move-result v3

    sget-object v4, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->CONFIG_EMO_THUM_SIZE:Landroid/util/Size;

    invoke-virtual {v4}, Landroid/util/Size;->getHeight()I

    move-result v4

    sget-object v5, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {v3, v4, v5}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v3

    invoke-virtual {p1}, Lcom/arcsoft/avatar/emoticon/EmoInfo;->getThumbnailData()[B

    move-result-object v4

    invoke-static {v4}, Ljava/nio/ByteBuffer;->wrap([B)Ljava/nio/ByteBuffer;

    move-result-object v4

    invoke-virtual {v3, v4}, Landroid/graphics/Bitmap;->copyPixelsFromBuffer(Ljava/nio/Buffer;)V

    if-eqz v3, :cond_1

    const/high16 p2, 0x41a00000    # 20.0f

    invoke-static {v3, p2}, Lcom/android/camera/Util;->getRoundedCornerBitmap(Landroid/graphics/Bitmap;F)Landroid/graphics/Bitmap;

    move-result-object v0

    goto :goto_0

    :cond_1
    sget-object v3, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p2, " , "

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lcom/arcsoft/avatar/emoticon/EmoInfo;->getEmoName()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {v3, p2}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_2
    :goto_0
    if-eqz v0, :cond_5

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->isRecycled()Z

    move-result p2

    if-eqz p2, :cond_3

    goto :goto_1

    :cond_3
    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    if-nez p2, :cond_4

    return-void

    :cond_4
    new-instance p2, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;

    invoke-direct {p2, p1, v0}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;-><init>(Lcom/arcsoft/avatar/emoticon/EmoInfo;Landroid/graphics/Bitmap;)V

    invoke-virtual {p2, v1}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->setSelected(Z)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    invoke-virtual {p0, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->addData(Ljava/lang/Object;)V

    return-void

    :cond_5
    :goto_1
    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    const-string p1, "mimoji thumbnail null"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public synthetic a([ZLandroid/content/DialogInterface;I)V
    .locals 0

    const/4 p2, 0x0

    aget-boolean p1, p1, p2

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->saveEmoticonGif(Z)V

    return-void
.end method

.method public synthetic b(Landroid/content/DialogInterface;ILandroid/view/KeyEvent;)Z
    .locals 0

    const/4 p1, 0x1

    const/4 p3, 0x4

    if-ne p2, p3, :cond_1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p2

    const/16 p3, 0xf7

    invoke-virtual {p2, p3}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p2

    check-cast p2, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz p2, :cond_0

    invoke-interface {p2}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->quitCoverEmoticon()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->dissmissDialog()V

    goto :goto_0

    :cond_0
    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    const-string p2, "mimoji void saveEmoticonGif[] null"

    invoke-static {p0, p2}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_1
    :goto_0
    return p1
.end method

.method public backToPreview()V
    .locals 1

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object p0

    sget-object v0, Lcom/android/camera/features/mimoji2/fragment/edit/k;->INSTANCE:Lcom/android/camera/features/mimoji2/fragment/edit/k;

    invoke-virtual {p0, v0}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method

.method public coverEmoticonError()V
    .locals 2

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/edit/g;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/g;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;)V

    invoke-virtual {v0, v1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_0
    return-void
.end method

.method public coverEmoticonSuccess()V
    .locals 16

    move-object/from16 v1, p0

    const-string v2, "Current video URI: "

    new-instance v0, Ljava/io/File;

    sget-object v3, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_GIF_CACHE_DIR:Ljava/lang/String;

    invoke-direct {v0, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v3

    if-eqz v3, :cond_b

    invoke-virtual {v0}, Ljava/io/File;->isDirectory()Z

    move-result v3

    if-nez v3, :cond_0

    goto/16 :goto_9

    :cond_0
    iget-boolean v3, v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mIsNeedShare:Z

    const-string v4, "gif"

    const-string v5, "_"

    const-string v6, "MIMOJI_GIF_"

    const/4 v7, 0x0

    const/16 v8, 0x1f4

    if-eqz v3, :cond_7

    invoke-virtual {v0}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v3

    array-length v9, v3

    move v10, v7

    :goto_0
    if-ge v10, v9, :cond_4

    aget-object v11, v3, v10

    invoke-virtual {v11}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/android/camera/module/impl/component/FileUtils;->getFileName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v12

    iget-object v0, v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v13

    :goto_1
    invoke-interface {v13}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_3

    invoke-interface {v13}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/arcsoft/avatar/emoticon/EmoInfo;

    invoke-virtual {v0}, Lcom/arcsoft/avatar/emoticon/EmoInfo;->getEmoName()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, v12}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    const/4 v14, 0x0

    :try_start_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v15, Lcom/android/camera/storage/Storage;->DIRECTORY:Ljava/lang/String;

    invoke-virtual {v0, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v15, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v0, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v15, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v15, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v15, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v15, v4}, Lcom/android/camera/module/impl/component/FileUtils;->createtFileName(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v15

    invoke-virtual {v0, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    new-instance v15, Ljava/io/File;

    invoke-direct {v15, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {v11, v15}, Lcom/android/camera/module/impl/component/FileUtils;->copyFile(Ljava/io/File;Ljava/io/File;)V

    invoke-virtual/range {p0 .. p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v15

    check-cast v15, Lcom/android/camera/ActivityBase;

    invoke-interface {v15}, Lcom/android/camera/AppController;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v15

    invoke-virtual {v15, v0, v8, v8}, Lcom/android/camera/storage/ImageSaver;->addGifSync(Ljava/lang/String;II)Landroid/net/Uri;

    move-result-object v14

    sget-object v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "mimoji void shareEmoticonGif[] f.getAbsolutePath() : "

    invoke-virtual {v15, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v15, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v8, " \n  "

    invoke-virtual {v15, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v15, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v8, "   "

    invoke-virtual {v15, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v15, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v0, v8}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-eqz v14, :cond_1

    iget-object v0, v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mShareEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0, v14}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    :cond_1
    sget-object v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    goto :goto_2

    :catchall_0
    move-exception v0

    goto :goto_3

    :catch_0
    move-exception v0

    :try_start_1
    sget-object v8, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    const-string v15, "failed to add video to media store"

    invoke-static {v8, v15, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    sget-object v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    :goto_2
    invoke-virtual {v8, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v0, v8}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_4

    :goto_3
    sget-object v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    throw v0

    :cond_2
    :goto_4
    const/16 v8, 0x1f4

    goto/16 :goto_1

    :cond_3
    add-int/lit8 v10, v10, 0x1

    const/16 v8, 0x1f4

    goto/16 :goto_0

    :cond_4
    iget-object v0, v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mShareEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-nez v0, :cond_5

    invoke-virtual/range {p0 .. p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "\u5206\u4eab\u6587\u4ef6\u4e0d\u5b58\u5728"

    invoke-static {v0, v1}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    return-void

    :cond_5
    iget-object v0, v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mShareEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    const-string v2, "image/gif"

    const-string v3, "android.intent.extra.STREAM"

    const/4 v4, 0x1

    if-ne v0, v4, :cond_6

    new-instance v0, Landroid/content/Intent;

    const-string v4, "android.intent.action.SEND"

    invoke-direct {v0, v4}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    iget-object v4, v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mShareEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v4, v7}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/os/Parcelable;

    invoke-virtual {v0, v3, v4}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    invoke-virtual {v0, v2}, Landroid/content/Intent;->setType(Ljava/lang/String;)Landroid/content/Intent;

    const-string v2, "mimoji\u6587\u4ef6\u5206\u4eab"

    invoke-static {v0, v2}, Landroid/content/Intent;->createChooser(Landroid/content/Intent;Ljava/lang/CharSequence;)Landroid/content/Intent;

    move-result-object v0

    invoke-virtual {v1, v0}, Landroid/support/v4/app/Fragment;->startActivity(Landroid/content/Intent;)V

    goto/16 :goto_8

    :cond_6
    new-instance v0, Landroid/content/Intent;

    const-string v4, "android.intent.action.SEND_MULTIPLE"

    invoke-direct {v0, v4}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    iget-object v4, v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mShareEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0, v3, v4}, Landroid/content/Intent;->putParcelableArrayListExtra(Ljava/lang/String;Ljava/util/ArrayList;)Landroid/content/Intent;

    invoke-virtual {v0, v2}, Landroid/content/Intent;->setType(Ljava/lang/String;)Landroid/content/Intent;

    const-string v2, "mimoji\u591a\u6587\u4ef6\u5206\u4eab"

    invoke-static {v0, v2}, Landroid/content/Intent;->createChooser(Landroid/content/Intent;Ljava/lang/CharSequence;)Landroid/content/Intent;

    move-result-object v0

    invoke-virtual {v1, v0}, Landroid/support/v4/app/Fragment;->startActivity(Landroid/content/Intent;)V

    goto/16 :goto_8

    :cond_7
    invoke-virtual {v0}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v2

    array-length v3, v2

    :goto_5
    if-ge v7, v3, :cond_a

    aget-object v8, v2, v7

    invoke-virtual {v8}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/android/camera/module/impl/component/FileUtils;->getFileName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    iget-object v0, v1, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v10

    :goto_6
    invoke-interface {v10}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_9

    invoke-interface {v10}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/arcsoft/avatar/emoticon/EmoInfo;

    invoke-virtual {v0}, Lcom/arcsoft/avatar/emoticon/EmoInfo;->getEmoName()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_8

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v11, Lcom/android/camera/storage/Storage;->DIRECTORY:Ljava/lang/String;

    invoke-virtual {v0, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v11, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v0, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v11, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v11, v4}, Lcom/android/camera/module/impl/component/FileUtils;->createtFileName(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v0, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    :try_start_2
    new-instance v11, Ljava/io/File;

    invoke-direct {v11, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {v8, v11}, Lcom/android/camera/module/impl/component/FileUtils;->copyFile(Ljava/io/File;Ljava/io/File;)V

    invoke-virtual/range {p0 .. p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v11

    check-cast v11, Lcom/android/camera/ActivityBase;

    invoke-interface {v11}, Lcom/android/camera/AppController;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v11
    :try_end_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_2

    const/16 v12, 0x1f4

    :try_start_3
    invoke-virtual {v11, v0, v12, v12}, Lcom/android/camera/storage/ImageSaver;->addGif(Ljava/lang/String;II)V
    :try_end_3
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_1

    goto :goto_6

    :catch_1
    move-exception v0

    goto :goto_7

    :catch_2
    move-exception v0

    const/16 v12, 0x1f4

    :goto_7
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_6

    :cond_8
    const/16 v12, 0x1f4

    goto :goto_6

    :cond_9
    const/16 v12, 0x1f4

    add-int/lit8 v7, v7, 0x1

    goto :goto_5

    :cond_a
    invoke-virtual/range {p0 .. p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    const-string v2, "\u4fdd\u5b58\u6210\u529f"

    invoke-static {v0, v2}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    :goto_8
    invoke-direct/range {p0 .. p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->deleteEmoticonCache()V

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->backToPreview()V

    return-void

    :cond_b
    :goto_9
    invoke-virtual/range {p0 .. p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v2, "gif\u3000\u7f13\u5b58\u5931\u6548"

    invoke-static {v0, v2}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->backToPreview()V

    return-void
.end method

.method public synthetic f(Landroid/content/DialogInterface;I)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->dissmissDialog()V

    return-void
.end method

.method public getFragmentInto()I
    .locals 0

    const/16 p0, 0xfa3

    return p0
.end method

.method protected getLayoutResourceId()I
    .locals 0

    const p0, 0x7f0b0043

    return p0
.end method

.method protected initView(Landroid/view/View;)V
    .locals 2

    const v0, 0x7f09002e

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f090030

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f09002b

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f09002f

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectBtn:Landroid/widget/ImageView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectBtn:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f090161

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/support/v7/widget/RecyclerView;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mEmoticonRecyclerView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p1

    invoke-static {p1}, Lcom/android/camera/Util;->isLayoutRTL(Landroid/content/Context;)Z

    move-result p1

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mIsRTL:Z

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->deleteEmoticonCache()V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    if-nez p1, :cond_0

    new-instance p1, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    const/4 v0, 0x0

    invoke-direct {p1, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;-><init>(Ljava/util/List;)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    new-instance p1, Lcom/android/camera/features/mimoji2/widget/baseview/BaseNoScrollGridLayoutManager;

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    const/4 v1, 0x2

    invoke-direct {p1, v0, v1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseNoScrollGridLayoutManager;-><init>(Landroid/content/Context;I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mEmoticonRecyclerView:Landroid/support/v7/widget/RecyclerView;

    invoke-virtual {v0, p1}, Landroid/support/v7/widget/RecyclerView;->setLayoutManager(Landroid/support/v7/widget/RecyclerView$LayoutManager;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mEmoticonRecyclerView:Landroid/support/v7/widget/RecyclerView;

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon$1;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon$1;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;)V

    invoke-virtual {p1, v0}, Landroid/support/v7/widget/RecyclerView;->addItemDecoration(Landroid/support/v7/widget/RecyclerView$ItemDecoration;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mEmoticonRecyclerView:Landroid/support/v7/widget/RecyclerView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    invoke-virtual {p1, v0}, Landroid/support/v7/widget/RecyclerView;->setAdapter(Landroid/support/v7/widget/RecyclerView$Adapter;)V

    :cond_0
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/edit/j;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/j;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;)V

    invoke-virtual {p1, v0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->setOnRecyclerItemClickListener(Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/edit/f;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/f;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;)V

    invoke-virtual {p1, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->setOnAllSelectStateChangeListener(Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$OnAllSelectStateChangeListener;)V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->getEmoticonThumbnail()V

    return-void
.end method

.method public synthetic l(I)V
    .locals 2

    if-eqz p1, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u6b63\u5728\u52aa\u529b\u751f\u6210\u8868\u60c5\u5305\uff0c\u5269\u4f59 "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, " \u4e2a..."

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->showProgressDialog(Ljava/lang/String;)V

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xfc

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;

    if-eqz p1, :cond_1

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-interface {p1, p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;->video2gif(Ljava/util/List;)V

    :cond_1
    :goto_0
    return-void
.end method

.method public synthetic o(Z)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectBtn:Landroid/widget/ImageView;

    if-eqz p1, :cond_0

    const p1, 0x7f080019

    goto :goto_0

    :cond_0
    const p1, 0x7f08001a

    :goto_0
    invoke-virtual {p0, p1}, Landroid/widget/ImageView;->setImageResource(I)V

    return-void
.end method

.method public onBackEvent(I)Z
    .locals 1

    const/4 v0, 0x1

    if-ne p1, v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->backToPreview()V

    return v0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public onClick(Landroid/view/View;)V
    .locals 0

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result p1

    packed-switch p1, :pswitch_data_0

    :pswitch_0
    goto :goto_0

    :pswitch_1
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->checkInitThumbnaiFinish()Z

    move-result p1

    if-nez p1, :cond_0

    return-void

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->shareEmoticonGif()V

    goto :goto_0

    :pswitch_2
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->checkInitThumbnaiFinish()Z

    move-result p1

    if-nez p1, :cond_1

    return-void

    :cond_1
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->getIsAllSelected()Z

    move-result p1

    if-eqz p1, :cond_2

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->clearState()V

    goto :goto_0

    :cond_2
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;->selectAll()V

    goto :goto_0

    :pswitch_3
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->checkInitThumbnaiFinish()Z

    move-result p1

    if-nez p1, :cond_3

    return-void

    :cond_3
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->showSaveDialog()V

    goto :goto_0

    :pswitch_4
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->backToPreview()V

    :goto_0
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x7f09002b
        :pswitch_4
        :pswitch_0
        :pswitch_0
        :pswitch_3
        :pswitch_2
        :pswitch_1
    .end packed-switch
.end method

.method public onStop()V
    .locals 0

    invoke-super {p0}, Lcom/android/camera/fragment/BaseFragment;->onStop()V

    return-void
.end method

.method public provideAnimateElement(ILjava/util/List;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I",
            "Ljava/util/List<",
            "Lio/reactivex/Completable;",
            ">;I)V"
        }
    .end annotation

    invoke-super {p0, p1, p2, p3}, Lcom/android/camera/fragment/BaseFragment;->provideAnimateElement(ILjava/util/List;I)V

    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "provideAnimateElement, animateInElements"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string p2, "resetType = "

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method protected register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->registerBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xfa

    invoke-virtual {p1, v0, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method

.method public release()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getItemCount()I

    move-result v0

    if-lez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    check-cast v0, Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;

    invoke-virtual {v2}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->getBitmap()Landroid/graphics/Bitmap;

    move-result-object v3

    if-eqz v3, :cond_0

    invoke-virtual {v2}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->getBitmap()Landroid/graphics/Bitmap;

    move-result-object v3

    invoke-virtual {v3}, Landroid/graphics/Bitmap;->isRecycled()Z

    move-result v3

    if-nez v3, :cond_0

    invoke-virtual {v2}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->getBitmap()Landroid/graphics/Bitmap;

    move-result-object v3

    invoke-virtual {v3}, Landroid/graphics/Bitmap;->recycle()V

    :cond_0
    invoke-virtual {v2, v1}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->setBitmap(Landroid/graphics/Bitmap;)V

    goto :goto_0

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->dissmissDialog()V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mMimojiEmoticonAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object p0

    invoke-virtual {p0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setEmoManager(Lcom/arcsoft/avatar/emoticon/AvatarEmoManager;)V

    return-void
.end method

.method protected unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->unRegisterBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object p1

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMode(I)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xfa

    invoke-virtual {p1, v0, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->release()V

    return-void
.end method

.method public updateEmoticonGifProgress(I)V
    .locals 2

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    check-cast v0, Landroid/app/Activity;

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/edit/e;

    invoke-direct {v1, p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/e;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;I)V

    invoke-virtual {v0, v1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_0
    return-void
.end method

.method public updateEmoticonPictureProgress(Ljava/lang/String;Lcom/arcsoft/avatar/emoticon/EmoInfo;Z)V
    .locals 3

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object p2

    if-nez p2, :cond_0

    return-void

    :cond_0
    sget-object p2, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "mimoji void updateEmoticonPictureProgress[path, emoInfo, isFinal]: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-eqz p3, :cond_5

    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->mSelectedEmoInfoList:Ljava/util/ArrayList;

    invoke-virtual {p1, p0}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    new-instance p0, Ljava/io/File;

    sget-object p2, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_GIF_CACHE_DIR:Ljava/lang/String;

    invoke-direct {p0, p2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {p0}, Ljava/io/File;->exists()Z

    move-result p2

    if-eqz p2, :cond_3

    invoke-virtual {p0}, Ljava/io/File;->isDirectory()Z

    move-result p2

    if-eqz p2, :cond_3

    invoke-virtual {p0}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object p0

    array-length p2, p0

    const/4 p3, 0x0

    :goto_0
    if-ge p3, p2, :cond_3

    aget-object v0, p0, p3

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/android/camera/module/impl/component/FileUtils;->getFileName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_1
    :goto_1
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_2

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/arcsoft/avatar/emoticon/EmoInfo;

    invoke-virtual {v2}, Lcom/arcsoft/avatar/emoticon/EmoInfo;->getEmoName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    invoke-interface {v1}, Ljava/util/Iterator;->remove()V

    goto :goto_1

    :cond_2
    add-int/lit8 p3, p3, 0x1

    goto :goto_0

    :cond_3
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p2, 0xf7

    invoke-virtual {p0, p2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz p0, :cond_4

    invoke-interface {p0, p1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->createEmoticonVideo(Ljava/util/List;)V

    goto :goto_2

    :cond_4
    sget-object p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->TAG:Ljava/lang/String;

    const-string p1, "mimoji void saveEmoticonGif[] null"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_5
    :goto_2
    return-void
.end method

.method public updateEmoticonThumbnailProgress(ILcom/arcsoft/avatar/emoticon/EmoInfo;)V
    .locals 2

    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Landroid/support/v4/app/Fragment;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    new-instance v1, Lcom/android/camera/features/mimoji2/fragment/edit/l;

    invoke-direct {v1, p0, p2, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/l;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;Lcom/arcsoft/avatar/emoticon/EmoInfo;I)V

    invoke-virtual {v0, v1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method
