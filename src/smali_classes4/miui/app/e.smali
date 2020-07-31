.class Lmiui/app/e;
.super Landroid/os/Handler;
.source "ProgressDialog.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lmiui/app/f;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lmiui/app/f;


# direct methods
.method constructor <init>(Lmiui/app/f;)V
    .locals 0

    iput-object p1, p0, Lmiui/app/e;->this$0:Lmiui/app/f;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 5

    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    iget-object p1, p0, Lmiui/app/e;->this$0:Lmiui/app/f;

    invoke-static {p1}, Lmiui/app/f;->b(Lmiui/app/f;)Landroid/widget/TextView;

    move-result-object p1

    iget-object v0, p0, Lmiui/app/e;->this$0:Lmiui/app/f;

    invoke-static {v0}, Lmiui/app/f;->a(Lmiui/app/f;)Ljava/lang/CharSequence;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lmiui/app/e;->this$0:Lmiui/app/f;

    invoke-static {p1}, Lmiui/app/f;->c(Lmiui/app/f;)Ljava/text/NumberFormat;

    move-result-object p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lmiui/app/e;->this$0:Lmiui/app/f;

    invoke-static {p1}, Lmiui/app/f;->d(Lmiui/app/f;)Landroid/widget/TextView;

    move-result-object p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lmiui/app/e;->this$0:Lmiui/app/f;

    invoke-static {p1}, Lmiui/app/f;->e(Lmiui/app/f;)Landroid/widget/ProgressBar;

    move-result-object p1

    invoke-virtual {p1}, Landroid/widget/ProgressBar;->getProgress()I

    move-result p1

    iget-object v0, p0, Lmiui/app/e;->this$0:Lmiui/app/f;

    invoke-static {v0}, Lmiui/app/f;->e(Lmiui/app/f;)Landroid/widget/ProgressBar;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/ProgressBar;->getMax()I

    move-result v0

    int-to-double v1, p1

    int-to-double v3, v0

    div-double/2addr v1, v3

    new-instance p1, Landroid/text/SpannableStringBuilder;

    invoke-direct {p1}, Landroid/text/SpannableStringBuilder;-><init>()V

    iget-object v0, p0, Lmiui/app/e;->this$0:Lmiui/app/f;

    invoke-static {v0}, Lmiui/app/f;->c(Lmiui/app/f;)Ljava/text/NumberFormat;

    move-result-object v0

    invoke-virtual {v0, v1, v2}, Ljava/text/NumberFormat;->format(D)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/text/SpannableStringBuilder;->append(Ljava/lang/CharSequence;)Landroid/text/SpannableStringBuilder;

    iget-object v1, p0, Lmiui/app/e;->this$0:Lmiui/app/f;

    invoke-virtual {v1}, Landroid/app/Dialog;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f06004f

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    new-instance v2, Landroid/text/style/ForegroundColorSpan;

    invoke-direct {v2, v1}, Landroid/text/style/ForegroundColorSpan;-><init>(I)V

    const/4 v1, 0x0

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v0

    const/16 v3, 0x22

    invoke-virtual {p1, v2, v1, v0, v3}, Landroid/text/SpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    iget-object p0, p0, Lmiui/app/e;->this$0:Lmiui/app/f;

    invoke-static {p0}, Lmiui/app/f;->d(Lmiui/app/f;)Landroid/widget/TextView;

    move-result-object p0

    invoke-virtual {p0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_0
    return-void
.end method
