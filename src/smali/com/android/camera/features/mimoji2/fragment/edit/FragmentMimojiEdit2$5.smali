.class Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;
.super Ljava/lang/Object;
.source "FragmentMimojiEdit2.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->showAlertDialog(I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

.field final synthetic val$backType:I


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;I)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    iput p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;->val$backType:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 3

    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;->val$backType:I

    const/4 p2, 0x0

    const/4 v0, 0x1

    if-ne p1, v0, :cond_0

    move p1, v0

    goto :goto_0

    :cond_0
    move p1, p2

    :goto_0
    if-nez p1, :cond_2

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-static {v1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$1100(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Z

    move-result v1

    if-eqz v1, :cond_2

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-static {v1}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$500(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Lcom/arcsoft/avatar/AvatarEngine;

    move-result-object v1

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-static {v2}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$1200(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Z

    move-result v2

    if-eqz v2, :cond_1

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-static {v2}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$1300(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)Ljava/lang/String;

    move-result-object v2

    goto :goto_1

    :cond_1
    sget-object v2, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TempOriginalConfigPath:Ljava/lang/String;

    :goto_1
    invoke-virtual {v1, v2}, Lcom/arcsoft/avatar/AvatarEngine;->loadConfig(Ljava/lang/String;)V

    :cond_2
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-virtual {v1, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->goBack(ZZ)V

    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;->val$backType:I

    const-string v1, "preview_mid"

    if-eq p1, v0, :cond_6

    const/4 v0, 0x3

    if-eq p1, v0, :cond_5

    const/4 v0, 0x4

    if-eq p1, v0, :cond_4

    const/4 v0, 0x5

    if-eq p1, v0, :cond_3

    goto :goto_2

    :cond_3
    const-string p1, "edit_cancel"

    invoke-static {p1, v1}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiClick(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2

    :cond_4
    const-string p1, "edit_soft_back"

    const-string v0, "edit"

    invoke-static {p1, v0}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiClick(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2

    :cond_5
    const-string p1, "preview_mid_soft_back"

    invoke-static {p1, v1}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiClick(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2

    :cond_6
    const-string p1, "mimoji_click_preview_mid_back"

    invoke-static {p1, v1}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiClick(Ljava/lang/String;Ljava/lang/String;)V

    :goto_2
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2$5;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    invoke-static {p0, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->access$1402(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;Z)Z

    return-void
.end method
