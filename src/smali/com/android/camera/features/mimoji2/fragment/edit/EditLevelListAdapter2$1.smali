.class Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$1;
.super Ljava/lang/Object;
.source "EditLevelListAdapter2.java"

# interfaces
.implements Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;->onBindViewHolder(Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ViewHolder;I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener<",
        "Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

.field final synthetic val$mimojiThumbnailAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;

.field final synthetic val$outerPosition:I


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;I)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$1;->val$mimojiThumbnailAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;

    iput p3, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$1;->val$outerPosition:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public OnRecyclerItemClickListener(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;I)V
    .locals 1

    invoke-static {}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->getInstance()Lcom/android/camera/features/mimoji2/utils/ClickCheck2;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/utils/ClickCheck2;->checkClickable()Z

    move-result p1

    if-nez p1, :cond_0

    return-void

    :cond_0
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$1;->val$mimojiThumbnailAdapter:Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;

    iget p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$1;->val$outerPosition:I

    invoke-static {p1, v0, p0, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;->access$000(Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;Lcom/android/camera/features/mimoji2/fragment/edit/MimojiThumbnailRecyclerAdapter2;II)V

    return-void
.end method

.method public bridge synthetic OnRecyclerItemClickListener(Ljava/lang/Object;I)V
    .locals 0

    check-cast p1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$1;->OnRecyclerItemClickListener(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigInfo;I)V

    return-void
.end method
