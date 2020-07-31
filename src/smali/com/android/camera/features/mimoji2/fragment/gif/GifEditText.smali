.class public Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;
.super Landroid/widget/EditText;
.source "GifEditText.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;,
        Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$ItfTextChanged;
    }
.end annotation


# instance fields
.field private mContext:Landroid/content/Context;

.field private mIsOverLimit:Z

.field private mItfTextChanged:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$ItfTextChanged;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0

    invoke-direct {p0, p1}, Landroid/widget/EditText;-><init>(Landroid/content/Context;)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->mContext:Landroid/content/Context;

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->initEditTextParam()V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    invoke-direct {p0, p1, p2}, Landroid/widget/EditText;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->mContext:Landroid/content/Context;

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->initEditTextParam()V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Landroid/widget/EditText;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->mContext:Landroid/content/Context;

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->initEditTextParam()V

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;)Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$ItfTextChanged;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->mItfTextChanged:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$ItfTextChanged;

    return-object p0
.end method

.method static synthetic access$100(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->mIsOverLimit:Z

    return p0
.end method

.method static synthetic access$102(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->mIsOverLimit:Z

    return p1
.end method

.method private initEditTextParam()V
    .locals 4

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Landroid/widget/EditText;->setSingleLine(Z)V

    const/high16 v1, 0x20000

    invoke-virtual {p0, v1}, Landroid/widget/EditText;->setInputType(I)V

    invoke-virtual {p0}, Landroid/widget/EditText;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f06005a

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    invoke-virtual {p0, v1}, Landroid/widget/EditText;->setBackgroundColor(I)V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->mContext:Landroid/content/Context;

    invoke-static {v1}, Lcom/android/camera/Util;->getFZMiaoWuJWTypeface(Landroid/content/Context;)Landroid/graphics/Typeface;

    move-result-object v1

    invoke-virtual {p0, v1}, Landroid/widget/EditText;->setTypeface(Landroid/graphics/Typeface;)V

    const/4 v1, 0x2

    invoke-virtual {p0, v1}, Landroid/widget/EditText;->setMaxLines(I)V

    invoke-virtual {p0, v0}, Landroid/widget/EditText;->setIncludeFontPadding(Z)V

    invoke-virtual {p0, v0}, Landroid/widget/EditText;->setHorizontallyScrolling(Z)V

    const/4 v1, 0x1

    new-array v1, v1, [Landroid/text/InputFilter;

    new-instance v2, Landroid/text/InputFilter$LengthFilter;

    const/16 v3, 0x32

    invoke-direct {v2, v3}, Landroid/text/InputFilter$LengthFilter;-><init>(I)V

    aput-object v2, v1, v0

    invoke-virtual {p0, v1}, Landroid/widget/EditText;->setFilters([Landroid/text/InputFilter;)V

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$1;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$1;-><init>(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;)V

    invoke-virtual {p0, v0}, Landroid/widget/EditText;->addTextChangedListener(Landroid/text/TextWatcher;)V

    return-void
.end method


# virtual methods
.method public addTextChangedListener(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$ItfTextChanged;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->mItfTextChanged:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$ItfTextChanged;

    return-void
.end method

.method public getMaxGifHeight()Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;
    .locals 10

    invoke-virtual {p0}, Landroid/widget/EditText;->getLayout()Landroid/text/Layout;

    move-result-object v0

    invoke-virtual {p0}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    new-instance v2, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;

    invoke-direct {v2, p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;-><init>(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;)V

    invoke-virtual {p0}, Landroid/widget/EditText;->getLineCount()I

    move-result v3

    const/4 v4, 0x1

    const/4 v5, 0x2

    if-le v3, v5, :cond_0

    iput-boolean v4, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;->mIsOverLimit:Z

    :cond_0
    const/4 v3, 0x0

    move v5, v3

    move v6, v5

    :goto_0
    invoke-virtual {p0}, Landroid/widget/EditText;->getLineCount()I

    move-result v7

    if-ge v5, v7, :cond_2

    if-gt v5, v4, :cond_2

    invoke-virtual {v0, v5}, Landroid/text/Layout;->getLineEnd(I)I

    move-result v7

    invoke-virtual {v1, v6, v7}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v6

    invoke-static {v2}, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->access$200(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    if-ne v5, v4, :cond_1

    invoke-virtual {v2, v3}, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->setSingleLine(Z)V

    invoke-static {v2}, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->access$300(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, "\n"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_1
    invoke-static {v2}, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->access$300(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    add-int/lit8 v5, v5, 0x1

    move v6, v7

    goto :goto_0

    :cond_2
    return-object v2
.end method

.method protected onMeasure(II)V
    .locals 0

    invoke-super {p0, p1, p2}, Landroid/widget/EditText;->onMeasure(II)V

    return-void
.end method
