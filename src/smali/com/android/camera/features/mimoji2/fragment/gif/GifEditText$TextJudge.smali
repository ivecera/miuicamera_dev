.class Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;
.super Ljava/lang/Object;
.source "GifEditText.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "TextJudge"
.end annotation


# instance fields
.field private isSingleLine:Z

.field private textNative:Ljava/lang/StringBuilder;

.field private textShow:Ljava/lang/StringBuilder;

.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;)V
    .locals 1

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->isSingleLine:Z

    new-instance p1, Ljava/lang/StringBuilder;

    const/16 v0, 0x20

    invoke-direct {p1, v0}, Ljava/lang/StringBuilder;-><init>(I)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->textShow:Ljava/lang/StringBuilder;

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1, v0}, Ljava/lang/StringBuilder;-><init>(I)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->textNative:Ljava/lang/StringBuilder;

    return-void
.end method

.method static synthetic access$200(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;)Ljava/lang/StringBuilder;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->textShow:Ljava/lang/StringBuilder;

    return-object p0
.end method

.method static synthetic access$300(Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;)Ljava/lang/StringBuilder;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->textNative:Ljava/lang/StringBuilder;

    return-object p0
.end method


# virtual methods
.method public getTextNative()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->textNative:Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public getTextShow()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->textShow:Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public isSingleLine()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->isSingleLine:Z

    return p0
.end method

.method public setSingleLine(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifEditText$TextJudge;->isSingleLine:Z

    return-void
.end method
