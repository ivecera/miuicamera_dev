.class Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon$1;
.super Landroid/support/v7/widget/RecyclerView$ItemDecoration;
.source "FragmentMimojiEmoticon.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->initView(Landroid/view/View;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final margin:I

.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;)V
    .locals 1

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    invoke-direct {p0}, Landroid/support/v7/widget/RecyclerView$ItemDecoration;-><init>()V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    invoke-virtual {p1}, Landroid/support/v4/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v0, 0x7f070159

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon$1;->margin:I

    return-void
.end method


# virtual methods
.method public getItemOffsets(Landroid/graphics/Rect;Landroid/view/View;Landroid/support/v7/widget/RecyclerView;Landroid/support/v7/widget/RecyclerView$State;)V
    .locals 0

    invoke-virtual {p3, p2}, Landroid/support/v7/widget/RecyclerView;->getChildAdapterPosition(Landroid/view/View;)I

    move-result p2

    iget-object p3, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    invoke-static {p3}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->access$000(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;)Z

    move-result p3

    const/4 p4, 0x0

    if-eqz p3, :cond_0

    rem-int/lit8 p2, p2, 0x2

    if-nez p2, :cond_1

    iget p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon$1;->margin:I

    invoke-virtual {p1, p0, p4, p4, p4}, Landroid/graphics/Rect;->set(IIII)V

    goto :goto_0

    :cond_0
    rem-int/lit8 p2, p2, 0x2

    if-nez p2, :cond_1

    iget p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon$1;->margin:I

    invoke-virtual {p1, p4, p4, p0, p4}, Landroid/graphics/Rect;->set(IIII)V

    :cond_1
    :goto_0
    return-void
.end method
