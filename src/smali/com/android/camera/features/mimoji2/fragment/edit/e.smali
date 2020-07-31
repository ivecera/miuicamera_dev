.class public final synthetic Lcom/android/camera/features/mimoji2/fragment/edit/e;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

.field private final synthetic Li:I


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/e;->Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    iput p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/e;->Li:I

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/e;->Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    iget p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/e;->Li:I

    invoke-virtual {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->l(I)V

    return-void
.end method
