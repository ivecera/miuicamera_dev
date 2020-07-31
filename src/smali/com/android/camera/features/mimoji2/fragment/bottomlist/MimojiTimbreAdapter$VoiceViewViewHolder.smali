.class Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;
.super Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;
.source "MimojiTimbreAdapter.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "VoiceViewViewHolder"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder<",
        "Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;",
        ">;"
    }
.end annotation


# instance fields
.field imageView:Landroid/widget/ImageView;

.field longSelectedView:Landroid/widget/ImageView;

.field mSelectItemView:Landroid/widget/ImageView;


# direct methods
.method public constructor <init>(Landroid/view/View;)V
    .locals 1
    .param p1    # Landroid/view/View;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;-><init>(Landroid/view/View;)V

    const v0, 0x7f0900fc

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->imageView:Landroid/widget/ImageView;

    const v0, 0x7f0900fd

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->mSelectItemView:Landroid/widget/ImageView;

    const v0, 0x7f090100

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ImageView;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->longSelectedView:Landroid/widget/ImageView;

    return-void
.end method


# virtual methods
.method public setData(Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;I)V
    .locals 2

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->longSelectedView:Landroid/widget/ImageView;

    const/16 v0, 0x8

    invoke-virtual {p2, v0}, Landroid/widget/ImageView;->setVisibility(I)V

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;->getTimbreId()I

    move-result p2

    if-lez p2, :cond_2

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;->getResourceId()I

    move-result p2

    if-lez p2, :cond_0

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->imageView:Landroid/widget/ImageView;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;->getResourceId()I

    move-result v1

    invoke-virtual {p2, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    goto :goto_0

    :cond_0
    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->imageView:Landroid/widget/ImageView;

    const v1, 0x7f080037

    invoke-virtual {p2, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    :goto_0
    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;->isSelected()Z

    move-result p1

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->mSelectItemView:Landroid/widget/ImageView;

    const/4 p2, 0x0

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->mSelectItemView:Landroid/widget/ImageView;

    iget-object p0, p0, Landroid/support/v7/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const p2, 0x7f080026

    invoke-virtual {p0, p2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p0

    invoke-virtual {p1, p0}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    :cond_1
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->mSelectItemView:Landroid/widget/ImageView;

    invoke-virtual {p0, v0}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_1

    :cond_2
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->mSelectItemView:Landroid/widget/ImageView;

    invoke-virtual {p0, v0}, Landroid/widget/ImageView;->setVisibility(I)V

    :goto_1
    return-void
.end method

.method public bridge synthetic setData(Ljava/lang/Object;I)V
    .locals 0

    check-cast p1, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$VoiceViewViewHolder;->setData(Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;I)V

    return-void
.end method
