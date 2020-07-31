.class Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$EmoticonViewViewHolder;
.super Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;
.source "MimojiEmoticonAdapter.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "EmoticonViewViewHolder"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder<",
        "Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;",
        ">;"
    }
.end annotation


# instance fields
.field imageMimojiEmoticon:Landroid/widget/ImageView;

.field imageMimojiSelect:Landroid/widget/ImageView;


# direct methods
.method public constructor <init>(Landroid/view/View;)V
    .locals 1
    .param p1    # Landroid/view/View;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;-><init>(Landroid/view/View;)V

    const v0, 0x7f090084

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$EmoticonViewViewHolder;->imageMimojiEmoticon:Landroid/widget/ImageView;

    const v0, 0x7f090085

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ImageView;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$EmoticonViewViewHolder;->imageMimojiSelect:Landroid/widget/ImageView;

    return-void
.end method


# virtual methods
.method public setData(Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;I)V
    .locals 1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->getBitmap()Landroid/graphics/Bitmap;

    move-result-object p2

    if-eqz p2, :cond_0

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$EmoticonViewViewHolder;->imageMimojiEmoticon:Landroid/widget/ImageView;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->getBitmap()Landroid/graphics/Bitmap;

    move-result-object v0

    invoke-virtual {p2, v0}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$EmoticonViewViewHolder;->imageMimojiSelect:Landroid/widget/ImageView;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;->isSelected()Z

    move-result p1

    if-eqz p1, :cond_1

    const p1, 0x7f080189

    goto :goto_0

    :cond_1
    const p1, 0x7f08018a

    :goto_0
    invoke-virtual {p0, p1}, Landroid/widget/ImageView;->setImageResource(I)V

    return-void
.end method

.method public bridge synthetic setData(Ljava/lang/Object;I)V
    .locals 0

    check-cast p1, Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/MimojiEmoticonAdapter$EmoticonViewViewHolder;->setData(Lcom/android/camera/features/mimoji2/bean/MimojiEmoticonInfo;I)V

    return-void
.end method
