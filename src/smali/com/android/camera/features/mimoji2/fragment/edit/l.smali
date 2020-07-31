.class public final synthetic Lcom/android/camera/features/mimoji2/fragment/edit/l;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

.field private final synthetic Li:Lcom/arcsoft/avatar/emoticon/EmoInfo;

.field private final synthetic Mi:I


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;Lcom/arcsoft/avatar/emoticon/EmoInfo;I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/l;->Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/l;->Li:Lcom/arcsoft/avatar/emoticon/EmoInfo;

    iput p3, p0, Lcom/android/camera/features/mimoji2/fragment/edit/l;->Mi:I

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/l;->Hi:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/l;->Li:Lcom/arcsoft/avatar/emoticon/EmoInfo;

    iget p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/l;->Mi:I

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->a(Lcom/arcsoft/avatar/emoticon/EmoInfo;I)V

    return-void
.end method
