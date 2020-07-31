.class Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter$1;
.super Ljava/lang/Object;
.source "GifViewPresenter.java"

# interfaces
.implements Landroid/text/TextWatcher;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->initGifView(Landroid/view/View;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public afterTextChanged(Landroid/text/Editable;)V
    .locals 0

    return-void
.end method

.method public beforeTextChanged(Ljava/lang/CharSequence;III)V
    .locals 0

    return-void
.end method

.method public onTextChanged(Ljava/lang/CharSequence;III)V
    .locals 0

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    invoke-interface {p1}, Ljava/lang/CharSequence;->length()I

    move-result p1

    if-lez p1, :cond_0

    const/4 p1, 0x1

    goto :goto_0

    :cond_0
    const/4 p1, 0x0

    :goto_0
    invoke-static {p2, p1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->access$002(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;Z)Z

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->access$000(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;)Z

    move-result p2

    invoke-static {p1, p2}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->access$100(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;Z)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter$1;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;

    invoke-static {p0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->access$000(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;)Z

    move-result p1

    invoke-static {p0, p1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;->access$200(Lcom/android/camera/features/mimoji2/fragment/gif/GifViewPresenter;Z)V

    return-void
.end method
