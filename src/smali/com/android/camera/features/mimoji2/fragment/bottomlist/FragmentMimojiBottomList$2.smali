.class Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$2;
.super Landroid/support/v7/widget/RecyclerView$OnScrollListener;
.source "FragmentMimojiBottomList.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->initAvatarList()V
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

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$2;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-direct {p0}, Landroid/support/v7/widget/RecyclerView$OnScrollListener;-><init>()V

    return-void
.end method


# virtual methods
.method public onScrollStateChanged(Landroid/support/v7/widget/RecyclerView;I)V
    .locals 0

    invoke-super {p0, p1, p2}, Landroid/support/v7/widget/RecyclerView$OnScrollListener;->onScrollStateChanged(Landroid/support/v7/widget/RecyclerView;I)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList$2;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    invoke-static {p0}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->access$000(Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;)Lcom/android/camera/features/mimoji2/widget/helper/BubbleEditMimojiPresenter2;

    move-result-object p0

    const/4 p1, -0x2

    const/4 p2, 0x0

    invoke-virtual {p0, p1, p1, p2}, Lcom/android/camera/features/mimoji2/widget/helper/BubbleEditMimojiPresenter2;->processBubbleAni(IILandroid/view/View;)V

    return-void
.end method
