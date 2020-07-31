.class public final synthetic Lcom/android/camera/features/mimoji2/fragment/edit/i;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

.field private final synthetic Li:[Z


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;[Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/i;->Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/i;->Li:[Z

    return-void
.end method


# virtual methods
.method public final onClick(Landroid/content/DialogInterface;I)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/i;->Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/i;->Li:[Z

    invoke-virtual {v0, p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->a([ZLandroid/content/DialogInterface;I)V

    return-void
.end method
