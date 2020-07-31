.class public Lmiui/app/c$a;
.super Lcom/android/internal/app/AlertController$AlertParams;
.source "AlertControllerWrapper.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiui/app/c;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "a"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lmiui/app/c$a$a;
    }
.end annotation


# instance fields
.field public Qi:Landroid/content/DialogInterface$OnClickListener;

.field public Ri:Landroid/content/DialogInterface$OnShowListener;

.field public Si:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lmiui/app/c$a$a;",
            ">;"
        }
    .end annotation
.end field

.field public Ti:Z

.field public Ui:Z

.field public Vi:Ljava/lang/CharSequence;

.field public mOnDismissListener:Landroid/content/DialogInterface$OnDismissListener;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/internal/app/AlertController$AlertParams;-><init>(Landroid/content/Context;)V

    return-void
.end method

.method private T(I)Landroid/widget/ListAdapter;
    .locals 7

    iget-object v3, p0, Lcom/android/internal/app/AlertController$AlertParams;->mCursor:Landroid/database/Cursor;

    const v0, 0x1020014

    if-nez v3, :cond_1

    iget-object v1, p0, Lcom/android/internal/app/AlertController$AlertParams;->mAdapter:Landroid/widget/ListAdapter;

    if-eqz v1, :cond_0

    goto :goto_0

    :cond_0
    new-instance v1, Landroid/widget/ArrayAdapter;

    iget-object v2, p0, Lcom/android/internal/app/AlertController$AlertParams;->mContext:Landroid/content/Context;

    iget-object p0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mItems:[Ljava/lang/CharSequence;

    invoke-direct {v1, v2, p1, v0, p0}, Landroid/widget/ArrayAdapter;-><init>(Landroid/content/Context;II[Ljava/lang/Object;)V

    goto :goto_0

    :cond_1
    new-instance v6, Landroid/widget/SimpleCursorAdapter;

    iget-object v1, p0, Lcom/android/internal/app/AlertController$AlertParams;->mContext:Landroid/content/Context;

    const/4 v2, 0x1

    new-array v4, v2, [Ljava/lang/String;

    iget-object p0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mLabelColumn:Ljava/lang/String;

    const/4 v5, 0x0

    aput-object p0, v4, v5

    new-array p0, v2, [I

    aput v0, p0, v5

    move-object v0, v6

    move v2, p1

    move-object v5, p0

    invoke-direct/range {v0 .. v5}, Landroid/widget/SimpleCursorAdapter;-><init>(Landroid/content/Context;ILandroid/database/Cursor;[Ljava/lang/String;[I)V

    move-object v1, v6

    :goto_0
    return-object v1
.end method

.method private a(Landroid/widget/ListView;I)Landroid/widget/ListAdapter;
    .locals 8

    iget-object v3, p0, Lcom/android/internal/app/AlertController$AlertParams;->mCursor:Landroid/database/Cursor;

    if-nez v3, :cond_1

    iget-boolean v0, p0, Lmiui/app/c$a;->Ti:Z

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mAdapter:Landroid/widget/ListAdapter;

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    new-instance v0, Lmiui/app/a;

    iget-object v3, p0, Lcom/android/internal/app/AlertController$AlertParams;->mContext:Landroid/content/Context;

    const v5, 0x1020014

    iget-object v6, p0, Lcom/android/internal/app/AlertController$AlertParams;->mItems:[Ljava/lang/CharSequence;

    move-object v1, v0

    move-object v2, p0

    move v4, p2

    move-object v7, p1

    invoke-direct/range {v1 .. v7}, Lmiui/app/a;-><init>(Lmiui/app/c$a;Landroid/content/Context;II[Ljava/lang/CharSequence;Landroid/widget/ListView;)V

    goto :goto_0

    :cond_1
    new-instance v7, Lmiui/app/b;

    iget-object v2, p0, Lcom/android/internal/app/AlertController$AlertParams;->mContext:Landroid/content/Context;

    const/4 v4, 0x0

    move-object v0, v7

    move-object v1, p0

    move-object v5, p1

    move v6, p2

    invoke-direct/range {v0 .. v6}, Lmiui/app/b;-><init>(Lmiui/app/c$a;Landroid/content/Context;Landroid/database/Cursor;ZLandroid/widget/ListView;I)V

    :goto_0
    return-object v0
.end method

.method private a(Lcom/android/internal/app/AlertController;)V
    .locals 0

    return-void
.end method


# virtual methods
.method public apply(Lcom/android/internal/app/AlertController;)V
    .locals 4

    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mCustomTitleView:Landroid/view/View;

    if-eqz v0, :cond_0

    invoke-virtual {p1, v0}, Lcom/android/internal/app/AlertController;->setCustomTitle(Landroid/view/View;)V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mTitle:Ljava/lang/CharSequence;

    if-eqz v0, :cond_1

    invoke-virtual {p1, v0}, Lcom/android/internal/app/AlertController;->setTitle(Ljava/lang/CharSequence;)V

    :cond_1
    :goto_0
    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mIcon:Landroid/graphics/drawable/Drawable;

    if-eqz v0, :cond_2

    invoke-virtual {p1, v0}, Lcom/android/internal/app/AlertController;->setIcon(Landroid/graphics/drawable/Drawable;)V

    :cond_2
    iget v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mIconId:I

    if-eqz v0, :cond_3

    invoke-virtual {p1, v0}, Lcom/android/internal/app/AlertController;->setIcon(I)V

    :cond_3
    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mMessage:Ljava/lang/CharSequence;

    if-eqz v0, :cond_4

    invoke-virtual {p1, v0}, Lcom/android/internal/app/AlertController;->setMessage(Ljava/lang/CharSequence;)V

    :cond_4
    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mPositiveButtonText:Ljava/lang/CharSequence;

    const/4 v1, 0x0

    if-eqz v0, :cond_5

    const/4 v2, -0x1

    iget-object v3, p0, Lcom/android/internal/app/AlertController$AlertParams;->mPositiveButtonListener:Landroid/content/DialogInterface$OnClickListener;

    invoke-virtual {p1, v2, v0, v3, v1}, Lcom/android/internal/app/AlertController;->setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;Landroid/os/Message;)V

    :cond_5
    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mNegativeButtonText:Ljava/lang/CharSequence;

    if-eqz v0, :cond_6

    const/4 v2, -0x2

    iget-object v3, p0, Lcom/android/internal/app/AlertController$AlertParams;->mNegativeButtonListener:Landroid/content/DialogInterface$OnClickListener;

    invoke-virtual {p1, v2, v0, v3, v1}, Lcom/android/internal/app/AlertController;->setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;Landroid/os/Message;)V

    :cond_6
    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mNeutralButtonText:Ljava/lang/CharSequence;

    if-eqz v0, :cond_7

    const/4 v2, -0x3

    iget-object v3, p0, Lcom/android/internal/app/AlertController$AlertParams;->mNeutralButtonListener:Landroid/content/DialogInterface$OnClickListener;

    invoke-virtual {p1, v2, v0, v3, v1}, Lcom/android/internal/app/AlertController;->setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;Landroid/os/Message;)V

    :cond_7
    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mItems:[Ljava/lang/CharSequence;

    if-nez v0, :cond_8

    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mCursor:Landroid/database/Cursor;

    if-nez v0, :cond_8

    iget-object v0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mAdapter:Landroid/widget/ListAdapter;

    if-eqz v0, :cond_9

    :cond_8
    invoke-direct {p0, p1}, Lmiui/app/c$a;->a(Lcom/android/internal/app/AlertController;)V

    :cond_9
    iget-object p0, p0, Lcom/android/internal/app/AlertController$AlertParams;->mView:Landroid/view/View;

    if-eqz p0, :cond_a

    invoke-virtual {p1, p0}, Lcom/android/internal/app/AlertController;->setView(Landroid/view/View;)V

    :cond_a
    return-void
.end method
