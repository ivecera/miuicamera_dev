.class Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$3;
.super Ljava/lang/Object;
.source "FragmentMimojiBottomList.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->showAlertDialog()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$3;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 1

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$3;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->access$100(Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;)Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;

    move-result-object p1

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$3;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->access$100(Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;)Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;

    move-result-object p1

    iget-object p1, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mPackPath:Ljava/lang/String;

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p1

    if-nez p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$3;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->access$100(Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;)Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;

    move-result-object p1

    iget-object p1, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mPackPath:Ljava/lang/String;

    invoke-static {p1}, Lcom/android/camera/module/impl/component/FileUtils;->deleteFile(Ljava/lang/String;)Z

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$3;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->access$000(Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;)Lcom/android/camera/features/mimoji2/widget/helper/BubbleEditMimojiPresenter2;

    move-result-object p1

    const/4 p2, 0x0

    const/4 v0, -0x2

    invoke-virtual {p1, v0, v0, p2}, Lcom/android/camera/features/mimoji2/widget/helper/BubbleEditMimojiPresenter2;->processBubbleAni(IILandroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$3;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->access$200(Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;)Landroid/view/View;

    move-result-object p1

    const/4 p2, 0x0

    invoke-virtual {p1, p2}, Landroid/view/View;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$3;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->access$300(Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;)Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiCreateItemAdapter2;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiCreateItemAdapter2;->updateSelect()V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$3;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->filelistToMinojiInfo()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 p2, 0xf6

    invoke-virtual {p1, p2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;

    if-eqz p1, :cond_0

    invoke-interface {p1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;->onMimojiDeleted()V

    :cond_0
    const-string p1, "mimoji_click_delete"

    const-string p2, "delete"

    invoke-static {p1, p2}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiClick(Ljava/lang/String;Ljava/lang/String;)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$3;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-static {p0}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->access$400(Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;)Ljava/util/List;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result p0

    add-int/lit8 p0, p0, -0x4

    invoke-static {p0}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiCount(Ljava/lang/String;)V

    :cond_1
    return-void
.end method
