.class public Lmiui/app/c;
.super Lcom/android/internal/app/AlertController;
.source "AlertControllerWrapper.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lmiui/app/c$a;
    }
.end annotation


# instance fields
.field Wi:Lcom/android/internal/app/AlertController;


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/content/DialogInterface;Landroid/view/Window;)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Lcom/android/internal/app/AlertController;-><init>(Landroid/content/Context;Landroid/content/DialogInterface;Landroid/view/Window;)V

    invoke-static {p1, p2, p3}, Lcom/android/internal/app/AlertController;->create(Landroid/content/Context;Landroid/content/DialogInterface;Landroid/view/Window;)Lcom/android/internal/app/AlertController;

    move-result-object p1

    iput-object p1, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    return-void
.end method


# virtual methods
.method public a(ZLjava/lang/CharSequence;)V
    .locals 0

    return-void
.end method

.method public getButton(I)Landroid/widget/Button;
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0, p1}, Lcom/android/internal/app/AlertController;->getButton(I)Landroid/widget/Button;

    move-result-object p0

    return-object p0
.end method

.method public getListView()Landroid/widget/ListView;
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0}, Lcom/android/internal/app/AlertController;->getListView()Landroid/widget/ListView;

    move-result-object p0

    return-object p0
.end method

.method public ia()[Z
    .locals 0

    const/4 p0, 0x0

    new-array p0, p0, [Z

    return-object p0
.end method

.method public installContent()V
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0}, Lcom/android/internal/app/AlertController;->installContent()V

    return-void
.end method

.method public isChecked()Z
    .locals 0

    const/4 p0, 0x0

    return p0
.end method

.method public ja()Landroid/widget/TextView;
    .locals 0

    const/4 p0, 0x0

    return-object p0
.end method

.method public onKeyDown(ILandroid/view/KeyEvent;)Z
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0, p1, p2}, Lcom/android/internal/app/AlertController;->onKeyDown(ILandroid/view/KeyEvent;)Z

    move-result p0

    return p0
.end method

.method public onKeyUp(ILandroid/view/KeyEvent;)Z
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0, p1, p2}, Lcom/android/internal/app/AlertController;->onKeyUp(ILandroid/view/KeyEvent;)Z

    move-result p0

    return p0
.end method

.method public setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;Landroid/os/Message;)V
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0, p1, p2, p3, p4}, Lcom/android/internal/app/AlertController;->setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;Landroid/os/Message;)V

    return-void
.end method

.method public setCustomTitle(Landroid/view/View;)V
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0, p1}, Lcom/android/internal/app/AlertController;->setCustomTitle(Landroid/view/View;)V

    return-void
.end method

.method public setIcon(I)V
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0, p1}, Lcom/android/internal/app/AlertController;->setIcon(I)V

    return-void
.end method

.method public setIcon(Landroid/graphics/drawable/Drawable;)V
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0, p1}, Lcom/android/internal/app/AlertController;->setIcon(Landroid/graphics/drawable/Drawable;)V

    return-void
.end method

.method public setInverseBackgroundForced(Z)V
    .locals 0

    return-void
.end method

.method public setMessage(Ljava/lang/CharSequence;)V
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0, p1}, Lcom/android/internal/app/AlertController;->setMessage(Ljava/lang/CharSequence;)V

    return-void
.end method

.method public setTitle(Ljava/lang/CharSequence;)V
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0, p1}, Lcom/android/internal/app/AlertController;->setTitle(Ljava/lang/CharSequence;)V

    return-void
.end method

.method public setView(Landroid/view/View;)V
    .locals 0

    iget-object p0, p0, Lmiui/app/c;->Wi:Lcom/android/internal/app/AlertController;

    invoke-virtual {p0, p1}, Lcom/android/internal/app/AlertController;->setView(Landroid/view/View;)V

    return-void
.end method
