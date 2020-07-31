.class public Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;
.super Ljava/lang/Object;
.source "GifViewPresenter.java"

# interfaces
.implements Landroid/view/View$OnClickListener;
.implements Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$ItfTextChanged;
.implements Lcom/android/camera/protocol/ModeProtocol$MimojiGifEditor;


# static fields
.field public static final GIF_EDIT_SHOW:I = 0x12f

.field public static final Gif_EDIT_HIDE:I = 0xca

.field public static final TAG:Ljava/lang/String; = "FragmentGifEdit"


# instance fields
.field private fragmentManager:Landroid/support/v4/app/FragmentManager;

.field public mContext:Landroid/content/Context;

.field private mEntryDrag:Z

.field private mEtGifWords:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;

.field private mFlEtContainer:Landroid/widget/FrameLayout;

.field private mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

.field private mGifViewLayout:Landroid/view/View;

.field public mIsAccelerateOpen:Z

.field public mIsClearBgOpen:Z

.field public mIsContainEmoji:Z

.field public mIsReverseOpen:Z

.field private mIsShowClearDrawable:Z

.field private mIvBack:Landroid/widget/ImageView;

.field private mIvClear:Landroid/widget/ImageView;

.field private mIvConfirm:Landroid/widget/ImageView;

.field private mLlAccelerate:Landroid/widget/LinearLayout;

.field private mLlAccelerate2:Landroid/widget/LinearLayout;

.field private mLlClearBg:Landroid/widget/LinearLayout;

.field private mLlClearBgOut:Landroid/widget/LinearLayout;

.field private mLlReverse:Landroid/widget/LinearLayout;

.field private mLlReverse2:Landroid/widget/LinearLayout;

.field private mLlStroke:Landroid/widget/LinearLayout;

.field private mLlThreeButton:Landroid/widget/LinearLayout;

.field private mLlTwoButton:Landroid/widget/LinearLayout;

.field private mRlEtContainer:Landroid/widget/RelativeLayout;

.field private mRlGifBottomOperate:Landroid/widget/RelativeLayout;

.field private mTextJudge:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;

.field private mTvGifWords:Lcom/android/camera/features/mimoji2/fragment/gif/DragTextView;

.field mbottomMargin:I

.field mleftMargin:I

.field mrightMargin:I

.field mtopMargin:I

.field private rlGifConsume:Landroid/widget/RelativeLayout;


# direct methods
.method constructor <init>(Landroid/content/Context;Landroid/support/v4/app/FragmentManager;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsClearBgOpen:Z

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsContainEmoji:Z

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsAccelerateOpen:Z

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsReverseOpen:Z

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mEntryDrag:Z

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->fragmentManager:Landroid/support/v4/app/FragmentManager;

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsShowClearDrawable:Z

    return p0
.end method

.method static synthetic access$002(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsShowClearDrawable:Z

    return p1
.end method

.method static synthetic access$100(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;Z)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->setBgStyle(Z)V

    return-void
.end method

.method static synthetic access$200(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;Z)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->setClearIconVisible(Z)V

    return-void
.end method

.method private clearEditState()V
    .locals 1

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mEtGifWords:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;

    const-string v0, ""

    invoke-virtual {p0, v0}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    return-void
.end method

.method private doAccelerateGif()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsAccelerateOpen:Z

    invoke-virtual {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->EnableSpeedFilter(Z)V

    return-void
.end method

.method private doClearGifBackgroud()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsClearBgOpen:Z

    invoke-virtual {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->EnableVideoSegmentFilter(Z)V

    return-void
.end method

.method private doReverseGif()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsReverseOpen:Z

    invoke-virtual {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->EnableReverseFilter(Z)V

    return-void
.end method

.method private gifBack()V
    .locals 1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xa0

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    if-eqz p0, :cond_0

    const/16 v0, 0x14

    invoke-interface {p0, v0}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->delegateEvent(I)V

    :cond_0
    return-void
.end method

.method private initGifView(Landroid/view/View;)V
    .locals 2

    const v0, 0x7f090153

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mRlGifBottomOperate:Landroid/widget/RelativeLayout;

    const v0, 0x7f09014e

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mRlEtContainer:Landroid/widget/RelativeLayout;

    const v0, 0x7f090151

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->rlGifConsume:Landroid/widget/RelativeLayout;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->rlGifConsume:Landroid/widget/RelativeLayout;

    invoke-virtual {v0, p0}, Landroid/widget/RelativeLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->rlGifConsume:Landroid/widget/RelativeLayout;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/RelativeLayout;->setVisibility(I)V

    const v0, 0x7f0900d2

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlThreeButton:Landroid/widget/LinearLayout;

    const v0, 0x7f0900d3

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlTwoButton:Landroid/widget/LinearLayout;

    const v0, 0x7f0900d0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlClearBg:Landroid/widget/LinearLayout;

    const v0, 0x7f0900d1

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlClearBgOut:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlClearBg:Landroid/widget/LinearLayout;

    invoke-virtual {v0, p0}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0900cb

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlAccelerate:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlAccelerate:Landroid/widget/LinearLayout;

    invoke-virtual {v0, p0}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0900cc

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlAccelerate2:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlAccelerate2:Landroid/widget/LinearLayout;

    invoke-virtual {v0, p0}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0900d5

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlReverse:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlReverse:Landroid/widget/LinearLayout;

    invoke-virtual {v0, p0}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0900d6

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlReverse2:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlReverse2:Landroid/widget/LinearLayout;

    invoke-virtual {v0, p0}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f090098

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIvBack:Landroid/widget/ImageView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIvBack:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f090099

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIvConfirm:Landroid/widget/ImageView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIvConfirm:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f090097

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIvClear:Landroid/widget/ImageView;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIvClear:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f09005b

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mEtGifWords:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mEtGifWords:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;

    invoke-virtual {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->addTextChangedListener(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$ItfTextChanged;)V

    const v0, 0x7f090069

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/FrameLayout;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mFlEtContainer:Landroid/widget/FrameLayout;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mFlEtContainer:Landroid/widget/FrameLayout;

    invoke-virtual {v0}, Landroid/widget/FrameLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout$LayoutParams;

    const/16 v1, 0xe

    invoke-virtual {v0, v1}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mFlEtContainer:Landroid/widget/FrameLayout;

    invoke-virtual {v1, v0}, Landroid/widget/FrameLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    const v0, 0x7f0900d7

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/LinearLayout;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlStroke:Landroid/widget/LinearLayout;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mEtGifWords:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter$1;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter$1;-><init>(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;)V

    invoke-virtual {p1, v0}, Landroid/widget/EditText;->addTextChangedListener(Landroid/text/TextWatcher;)V

    return-void
.end method

.method private setBgStyle(Z)V
    .locals 1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlStroke:Landroid/widget/LinearLayout;

    if-eqz p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    const v0, 0x7f0802e5

    invoke-static {p0, v0}, Landroid/support/v4/content/ContextCompat;->getDrawable(Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;

    move-result-object p0

    invoke-virtual {p1, p0}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlStroke:Landroid/widget/LinearLayout;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    const v0, 0x7f0802e6

    invoke-static {p0, v0}, Landroid/support/v4/content/ContextCompat;->getDrawable(Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;

    move-result-object p0

    invoke-virtual {p1, p0}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :goto_0
    return-void
.end method

.method private setClearIconVisible(Z)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIvClear:Landroid/widget/ImageView;

    if-eqz p1, :cond_0

    const/4 p1, 0x0

    goto :goto_0

    :cond_0
    const/4 p1, 0x4

    :goto_0
    invoke-virtual {p0, p1}, Landroid/widget/ImageView;->setVisibility(I)V

    return-void
.end method


# virtual methods
.method public afterTextChanged(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mTextJudge:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;

    return-void
.end method

.method public completeVideoRecord(Ljava/lang/String;Z)V
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "completeVideoRecord:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p1, "isContainEmoji:"

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "FragmentGifEdit"

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    iput-boolean p2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsContainEmoji:Z

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->updateOperateState()V

    return-void
.end method

.method public gifConfirmVideo2Gif()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mTextJudge:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->setSubtitleTextString(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->startVideo2Gif()V

    return-void
.end method

.method public initView(Landroid/view/View;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->initGifView(Landroid/view/View;)V

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 0

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result p1

    sparse-switch p1, :sswitch_data_0

    goto :goto_0

    :sswitch_0
    iget-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsReverseOpen:Z

    xor-int/lit8 p1, p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsReverseOpen:Z

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->updateOperateState()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->doReverseGif()V

    goto :goto_0

    :sswitch_1
    iget-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsClearBgOpen:Z

    xor-int/lit8 p1, p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsClearBgOpen:Z

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->updateOperateState()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->doClearGifBackgroud()V

    goto :goto_0

    :sswitch_2
    iget-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsAccelerateOpen:Z

    xor-int/lit8 p1, p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsAccelerateOpen:Z

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->updateOperateState()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->doAccelerateGif()V

    goto :goto_0

    :sswitch_3
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->gifConfirmVideo2Gif()V

    goto :goto_0

    :sswitch_4
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->gifBack()V

    goto :goto_0

    :sswitch_5
    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->clearEditState()V

    :goto_0
    return-void

    nop

    :sswitch_data_0
    .sparse-switch
        0x7f090097 -> :sswitch_5
        0x7f090098 -> :sswitch_4
        0x7f090099 -> :sswitch_3
        0x7f0900cb -> :sswitch_2
        0x7f0900cc -> :sswitch_2
        0x7f0900d0 -> :sswitch_1
        0x7f0900d5 -> :sswitch_0
        0x7f0900d6 -> :sswitch_0
    .end sparse-switch
.end method

.method public operateGifPannelVisibleState(I)V
    .locals 1

    const/16 v0, 0xca

    if-eq p1, v0, :cond_1

    const/16 v0, 0x12f

    if-eq p1, v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mRlGifBottomOperate:Landroid/widget/RelativeLayout;

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Landroid/widget/RelativeLayout;->setVisibility(I)V

    goto :goto_0

    :cond_1
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mRlGifBottomOperate:Landroid/widget/RelativeLayout;

    const/16 p1, 0x8

    invoke-virtual {p0, p1}, Landroid/widget/RelativeLayout;->setVisibility(I)V

    :goto_0
    return-void
.end method

.method public reconfigPreviewRadio(I)V
    .locals 0

    return-void
.end method

.method public registerProtocol()V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xfb

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method

.method public release(Z)V
    .locals 0

    return-void
.end method

.method public setGifMediaPlayer(Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mGifMediaPlayer:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    return-void
.end method

.method public unRegisterProtocol()V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xfb

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method

.method public updateOperateState()V
    .locals 5

    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsAccelerateOpen:Z

    const v1, 0x7f08008b

    const v2, 0x7f08008a

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlAccelerate:Landroid/widget/LinearLayout;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3, v1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlAccelerate2:Landroid/widget/LinearLayout;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3, v1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlAccelerate:Landroid/widget/LinearLayout;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlAccelerate2:Landroid/widget/LinearLayout;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :goto_0
    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsContainEmoji:Z

    const/16 v3, 0x8

    const/4 v4, 0x0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlThreeButton:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlTwoButton:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v4}, Landroid/widget/LinearLayout;->setVisibility(I)V

    goto :goto_1

    :cond_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlThreeButton:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v4}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlTwoButton:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsClearBgOpen:Z

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlClearBg:Landroid/widget/LinearLayout;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3, v1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    :cond_2
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlClearBg:Landroid/widget/LinearLayout;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :goto_1
    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mIsReverseOpen:Z

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlReverse:Landroid/widget/LinearLayout;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlReverse2:Landroid/widget/LinearLayout;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_2

    :cond_3
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlReverse:Landroid/widget/LinearLayout;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mLlReverse2:Landroid/widget/LinearLayout;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :goto_2
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->mRlGifBottomOperate:Landroid/widget/RelativeLayout;

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->invalidate()V

    return-void
.end method
