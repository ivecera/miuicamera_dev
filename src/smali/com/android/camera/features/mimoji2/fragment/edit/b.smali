.class public final synthetic Lcom/android/camera/features/mimoji2/fragment/edit/b;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lcom/android/camera/features/mimoji2/widget/autoselectview/AutoSelectAdapter$OnSelectListener;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/b;->Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    return-void
.end method


# virtual methods
.method public final onSelectListener(Ljava/lang/Object;I)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/b;->Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;

    check-cast p1, Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEdit2;->a(Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;I)V

    return-void
.end method
