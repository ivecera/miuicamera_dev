.class Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;
.super Ljava/lang/Object;
.source "ColorListAdapter2.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->onBindViewHolder(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

.field final synthetic val$argbEvaluator:Landroid/animation/ArgbEvaluator;

.field final synthetic val$avatarConfigInfo:Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;

.field final synthetic val$circleImageView:Lcom/android/camera/ui/CircleImageView;

.field final synthetic val$position:I


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;ILandroid/animation/ArgbEvaluator;Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;Lcom/android/camera/ui/CircleImageView;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    iput p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$position:I

    iput-object p3, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$argbEvaluator:Landroid/animation/ArgbEvaluator;

    iput-object p4, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$avatarConfigInfo:Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;

    iput-object p5, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$circleImageView:Lcom/android/camera/ui/CircleImageView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4

    invoke-static {}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->getInstance()Lcom/android/camera/features/mimoji2/utils/ClickCheck2;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->checkClickable()Z

    move-result p1

    if-nez p1, :cond_0

    return-void

    :cond_0
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    iget v0, p1, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mLastPosion:I

    iget v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$position:I

    if-eq v0, v1, :cond_8

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$argbEvaluator:Landroid/animation/ArgbEvaluator;

    if-eqz v0, :cond_8

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->access$000(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Landroid/support/v7/widget/RecyclerView;

    move-result-object p1

    if-nez p1, :cond_1

    goto/16 :goto_3

    :cond_1
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$avatarConfigInfo:Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;

    iget p1, p1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;->configType:I

    const/4 v0, 0x2

    if-ne p1, v0, :cond_2

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object p1

    const/16 v0, 0x13

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$avatarConfigInfo:Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;

    iget v1, v1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;->configID:I

    int-to-float v1, v1

    invoke-virtual {p1, v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setInnerConfigSelectIndex(IF)V

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object p1

    const/16 v0, 0xf

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$avatarConfigInfo:Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;

    iget v1, v1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;->configID:I

    int-to-float v1, v1

    invoke-virtual {p1, v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setInnerConfigSelectIndex(IF)V

    :cond_2
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->access$100(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    move-result-object p1

    const/4 v0, 0x0

    const/4 v1, 0x1

    if-eqz p1, :cond_6

    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$position:I

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    invoke-static {v2}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->access$100(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    move-result-object v2

    invoke-virtual {v2}, Landroid/support/v7/widget/LinearLayoutManager;->findFirstVisibleItemPosition()I

    move-result v2

    if-eq p1, v2, :cond_5

    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$position:I

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    invoke-static {v2}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->access$100(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    move-result-object v2

    invoke-virtual {v2}, Landroid/support/v7/widget/LinearLayoutManager;->findFirstCompletelyVisibleItemPosition()I

    move-result v2

    if-ne p1, v2, :cond_3

    goto :goto_0

    :cond_3
    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$position:I

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    invoke-static {v2}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->access$100(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    move-result-object v2

    invoke-virtual {v2}, Landroid/support/v7/widget/LinearLayoutManager;->findLastVisibleItemPosition()I

    move-result v2

    if-eq p1, v2, :cond_4

    iget p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$position:I

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    invoke-static {v2}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->access$100(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    move-result-object v2

    invoke-virtual {v2}, Landroid/support/v7/widget/LinearLayoutManager;->findLastCompletelyVisibleItemPosition()I

    move-result v2

    if-ne p1, v2, :cond_6

    :cond_4
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->access$000(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Landroid/support/v7/widget/RecyclerView;

    move-result-object p1

    iget v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$position:I

    add-int/2addr v2, v1

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    invoke-virtual {v3}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->getItemCount()I

    move-result v3

    sub-int/2addr v3, v1

    invoke-static {v2, v3}, Ljava/lang/Math;->min(II)I

    move-result v2

    invoke-virtual {p1, v2}, Landroid/support/v7/widget/RecyclerView;->scrollToPosition(I)V

    goto :goto_1

    :cond_5
    :goto_0
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->access$000(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Landroid/support/v7/widget/RecyclerView;

    move-result-object p1

    iget v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$position:I

    sub-int/2addr v2, v1

    invoke-static {v0, v2}, Ljava/lang/Math;->max(II)I

    move-result v2

    invoke-virtual {p1, v2}, Landroid/support/v7/widget/RecyclerView;->scrollToPosition(I)V

    :cond_6
    :goto_1
    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object p1

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$avatarConfigInfo:Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;

    iget v3, v2, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;->configType:I

    iget v2, v2, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;->configID:I

    int-to-float v2, v2

    invoke-virtual {p1, v3, v2}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setInnerConfigSelectIndex(IF)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->access$000(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Landroid/support/v7/widget/RecyclerView;

    move-result-object p1

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    iget v2, v2, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mLastPosion:I

    invoke-virtual {p1, v2}, Landroid/support/v7/widget/RecyclerView;->findViewHolderForAdapterPosition(I)Landroid/support/v7/widget/RecyclerView$ViewHolder;

    move-result-object p1

    check-cast p1, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;

    if-eqz p1, :cond_7

    iget-object p1, p1, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;->ivColor:Lcom/android/camera/ui/CircleImageView;

    invoke-virtual {p1, v0}, Lcom/android/camera/ui/CircleImageView;->updateView(Z)V

    goto :goto_2

    :cond_7
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    iget v0, p1, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mLastPosion:I

    invoke-virtual {p1, v0}, Landroid/support/v7/widget/RecyclerView$Adapter;->notifyItemChanged(I)V

    :goto_2
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$circleImageView:Lcom/android/camera/ui/CircleImageView;

    invoke-virtual {p1, v1}, Lcom/android/camera/ui/CircleImageView;->updateView(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;

    iget v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$position:I

    iput v0, p1, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mLastPosion:I

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->access$200(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Lcom/android/camera/features/mimoji2/fragment/edit/AvatarConfigItemClick2;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$avatarConfigInfo:Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;

    iget p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;->val$position:I

    invoke-interface {p1, v0, v1, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/AvatarConfigItemClick2;->onConfigItemClick(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;ZI)V

    :cond_8
    :goto_3
    return-void
.end method
