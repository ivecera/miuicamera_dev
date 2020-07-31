.class Lmiui/app/a;
.super Landroid/widget/ArrayAdapter;
.source "AlertControllerWrapper.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lmiui/app/c$a;->a(Landroid/widget/ListView;I)Landroid/widget/ListAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/widget/ArrayAdapter<",
        "Ljava/lang/CharSequence;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic Uh:Landroid/widget/ListView;

.field final synthetic this$0:Lmiui/app/c$a;


# direct methods
.method constructor <init>(Lmiui/app/c$a;Landroid/content/Context;II[Ljava/lang/CharSequence;Landroid/widget/ListView;)V
    .locals 0

    iput-object p1, p0, Lmiui/app/a;->this$0:Lmiui/app/c$a;

    iput-object p6, p0, Lmiui/app/a;->Uh:Landroid/widget/ListView;

    invoke-direct {p0, p2, p3, p4, p5}, Landroid/widget/ArrayAdapter;-><init>(Landroid/content/Context;II[Ljava/lang/Object;)V

    return-void
.end method


# virtual methods
.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 1

    invoke-super {p0, p1, p2, p3}, Landroid/widget/ArrayAdapter;->getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p2

    iget-object p3, p0, Lmiui/app/a;->this$0:Lmiui/app/c$a;

    iget-boolean v0, p3, Lmiui/app/c$a;->Ti:Z

    if-nez v0, :cond_0

    iget-object p3, p3, Lcom/android/internal/app/AlertController$AlertParams;->mCheckedItems:[Z

    if-eqz p3, :cond_0

    aget-boolean p3, p3, p1

    if-eqz p3, :cond_0

    iget-object p0, p0, Lmiui/app/a;->Uh:Landroid/widget/ListView;

    const/4 p3, 0x1

    invoke-virtual {p0, p1, p3}, Landroid/widget/ListView;->setItemChecked(IZ)V

    :cond_0
    return-object p2
.end method
