.class public Lmiui/app/d;
.super Landroid/app/Dialog;
.source "AlertDialog.java"

# interfaces
.implements Landroid/content/DialogInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lmiui/app/d$a;
    }
.end annotation


# static fields
.field public static final ba:I = 0x2

.field public static final ca:I = 0x3

.field public static final da:I = 0x4

.field public static final ea:I = 0x5

.field public static final fa:I = 0x6

.field public static final ga:I = 0x7


# instance fields
.field private mAlert:Lmiui/app/c;


# direct methods
.method protected constructor <init>(Landroid/content/Context;)V
    .locals 1

    const/4 v0, 0x0

    invoke-static {p1, v0}, Lmiui/app/d;->resolveDialogTheme(Landroid/content/Context;I)I

    move-result v0

    invoke-direct {p0, p1, v0}, Lmiui/app/d;-><init>(Landroid/content/Context;I)V

    return-void
.end method

.method protected constructor <init>(Landroid/content/Context;I)V
    .locals 1

    invoke-static {p1, p2}, Lmiui/app/d;->resolveDialogTheme(Landroid/content/Context;I)I

    move-result p2

    invoke-direct {p0, p1, p2}, Landroid/app/Dialog;-><init>(Landroid/content/Context;I)V

    new-instance p2, Lmiui/app/c;

    invoke-virtual {p0}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    move-result-object v0

    invoke-direct {p2, p1, p0, v0}, Lmiui/app/c;-><init>(Landroid/content/Context;Landroid/content/DialogInterface;Landroid/view/Window;)V

    iput-object p2, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    return-void
.end method

.method static synthetic a(Lmiui/app/d;)Lmiui/app/c;
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    return-object p0
.end method

.method static resolveDialogTheme(Landroid/content/Context;I)I
    .locals 2

    const/4 v0, 0x2

    if-eq p1, v0, :cond_2

    const/4 v0, 0x3

    if-eq p1, v0, :cond_1

    const/high16 v0, 0x1000000

    if-lt p1, v0, :cond_0

    goto :goto_0

    :cond_0
    new-instance p1, Landroid/util/TypedValue;

    invoke-direct {p1}, Landroid/util/TypedValue;-><init>()V

    invoke-virtual {p0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object p0

    const v0, 0x1010309

    const/4 v1, 0x1

    invoke-virtual {p0, v0, p1, v1}, Landroid/content/res/Resources$Theme;->resolveAttribute(ILandroid/util/TypedValue;Z)Z

    iget p1, p1, Landroid/util/TypedValue;->resourceId:I

    goto :goto_0

    :cond_1
    const p1, 0x1030410

    goto :goto_0

    :cond_2
    const p1, 0x1030409

    :goto_0
    return p1
.end method


# virtual methods
.method public getButton(I)Landroid/widget/Button;
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0, p1}, Lmiui/app/c;->getButton(I)Landroid/widget/Button;

    move-result-object p0

    return-object p0
.end method

.method public getListView()Landroid/widget/ListView;
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0}, Lmiui/app/c;->getListView()Landroid/widget/ListView;

    move-result-object p0

    return-object p0
.end method

.method public ia()[Z
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0}, Lmiui/app/c;->ia()[Z

    move-result-object p0

    return-object p0
.end method

.method public isChecked()Z
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0}, Lmiui/app/c;->isChecked()Z

    move-result p0

    return p0
.end method

.method public ja()Landroid/widget/TextView;
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0}, Lmiui/app/c;->ja()Landroid/widget/TextView;

    move-result-object p0

    return-object p0
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 0

    invoke-super {p0, p1}, Landroid/app/Dialog;->onCreate(Landroid/os/Bundle;)V

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0}, Lmiui/app/c;->installContent()V

    return-void
.end method

.method public onKeyDown(ILandroid/view/KeyEvent;)Z
    .locals 1

    iget-object v0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {v0, p1, p2}, Lmiui/app/c;->onKeyDown(ILandroid/view/KeyEvent;)Z

    move-result v0

    if-nez v0, :cond_1

    invoke-super {p0, p1, p2}, Landroid/app/Dialog;->onKeyDown(ILandroid/view/KeyEvent;)Z

    move-result p0

    if-eqz p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method public onKeyUp(ILandroid/view/KeyEvent;)Z
    .locals 1

    iget-object v0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {v0, p1, p2}, Lmiui/app/c;->onKeyUp(ILandroid/view/KeyEvent;)Z

    move-result v0

    if-nez v0, :cond_1

    invoke-super {p0, p1, p2}, Landroid/app/Dialog;->onKeyUp(ILandroid/view/KeyEvent;)Z

    move-result p0

    if-eqz p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method public setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)V
    .locals 1

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    const/4 v0, 0x0

    invoke-virtual {p0, p1, p2, p3, v0}, Lmiui/app/c;->setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;Landroid/os/Message;)V

    return-void
.end method

.method public setButton(ILjava/lang/CharSequence;Landroid/os/Message;)V
    .locals 1

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    const/4 v0, 0x0

    invoke-virtual {p0, p1, p2, v0, p3}, Lmiui/app/c;->setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;Landroid/os/Message;)V

    return-void
.end method

.method public setCustomTitle(Landroid/view/View;)V
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0, p1}, Lmiui/app/c;->setCustomTitle(Landroid/view/View;)V

    return-void
.end method

.method public setIcon(I)V
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0, p1}, Lmiui/app/c;->setIcon(I)V

    return-void
.end method

.method public setIcon(Landroid/graphics/drawable/Drawable;)V
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0, p1}, Lmiui/app/c;->setIcon(Landroid/graphics/drawable/Drawable;)V

    return-void
.end method

.method public setIconAttribute(I)V
    .locals 0

    return-void
.end method

.method public setMessage(Ljava/lang/CharSequence;)V
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0, p1}, Lmiui/app/c;->setMessage(Ljava/lang/CharSequence;)V

    return-void
.end method

.method public setTitle(Ljava/lang/CharSequence;)V
    .locals 0

    invoke-super {p0, p1}, Landroid/app/Dialog;->setTitle(Ljava/lang/CharSequence;)V

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0, p1}, Lmiui/app/c;->setTitle(Ljava/lang/CharSequence;)V

    return-void
.end method

.method public setView(Landroid/view/View;)V
    .locals 0

    iget-object p0, p0, Lmiui/app/d;->mAlert:Lmiui/app/c;

    invoke-virtual {p0, p1}, Lmiui/app/c;->setView(Landroid/view/View;)V

    return-void
.end method
