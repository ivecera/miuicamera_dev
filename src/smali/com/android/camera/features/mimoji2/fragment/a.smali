.class public final synthetic Lcom/android/camera/features/mimoji2/fragment/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;

.field private final synthetic Li:Ljava/lang/String;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;Ljava/lang/String;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/a;->Hi:Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/a;->Li:Ljava/lang/String;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/a;->Hi:Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/a;->Li:Ljava/lang/String;

    invoke-virtual {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/FragmentMimojiFullScreen;->l(Ljava/lang/String;)V

    return-void
.end method
